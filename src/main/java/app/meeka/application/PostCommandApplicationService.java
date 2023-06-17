package app.meeka.application;

import app.meeka.application.command.CreatePostCommand;
import app.meeka.application.command.DeletePostCommand;
import app.meeka.application.command.EditPostInfoCommand;
import app.meeka.application.command.UserBasicCommand;
import app.meeka.application.result.PostCreatedResult;
import app.meeka.application.result.PostResult;
import app.meeka.core.context.UserHolder;
import app.meeka.domain.event.PostPublishedEvent;
import app.meeka.domain.exception.InvalidPostInfoException;
import app.meeka.domain.exception.UserNotLoginException;
import app.meeka.domain.model.post.Post;
import app.meeka.domain.model.post.PostInfo;
import app.meeka.domain.model.user.User;
import app.meeka.domain.repository.PostRepository;
import app.meeka.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static app.meeka.core.cache.RedisConstants.POST_BROWSE_KEY;
import static app.meeka.core.cache.RedisConstants.POST_LIKED_KEY;

@Service
@Transactional
public class PostCommandApplicationService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private final ApplicationEventPublisher applicationEventPublisher;

    public PostCommandApplicationService(PostRepository postRepository, UserRepository userRepository, StringRedisTemplate stringRedisTemplate, ApplicationEventPublisher applicationEventPublisher) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.stringRedisTemplate = stringRedisTemplate;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // keypoint: 新建post并推送postId给粉丝
    public PostCreatedResult createPost(CreatePostCommand command) throws InvalidPostInfoException {
        var post = new Post(new PostInfo(command.authorId(), command.cover(), command.title(), command.content()));
        var savedPost = postRepository.save(post);
        applicationEventPublisher.publishEvent(new PostPublishedEvent(post.getId()));
        return new PostCreatedResult(savedPost.getId());
    }

    // keypoint: 根据作者id分页查询post(携带完整信息，作者信息，浏览量，点赞,发布时间)
    public List<PostResult> getPostByAuthorId(Long authorId, Pageable pageable) {
        String nickName = userRepository.findUserById(authorId).getNikeName();
        String icon = userRepository.findUserById(authorId).getIcon();
        return postRepository.findByAuthorId(authorId, pageable)
                .stream()
                .map(post -> new PostResult(
                                post.getAuthorId(),
                                nickName,
                                icon,
                                post.getTitle(),
                                post.getCover(),
                                post.getContent(),
                                post.getFavours(),
                                post.getBrowse(),
                                isLiked(post.getId()),
                                Duration.between(post.getCreatedAt(), OffsetDateTime.now()).toDays() < 1
                                        ?
                                        Duration.between(post.getCreatedAt(), OffsetDateTime.now())
                                        :
                                        Duration.between(OffsetDateTime.parse("0000-01-01T00:00:00-8:00"), post.getCreatedAt())
                        )
                ).toList();
    }

    // keypoint: 删除post
    public void deletePost(DeletePostCommand command) {
        postRepository.deleteById(command.postId());
    }

    // keypoint: 点赞post
    public void likePost(Long postId) throws UserNotLoginException {
        UserBasicCommand user = UserHolder.getUser();
        if (user == null) {
            throw new UserNotLoginException();
        }
        Double score = stringRedisTemplate
                .opsForZSet()
                .score(POST_LIKED_KEY + postId, user.getId().toString()
                );
        Post post = postRepository.findPostById(postId);
        if (score == null) {
            post.updateFavours(false);
            postRepository.save(post);
            stringRedisTemplate
                    .opsForZSet()
                    .add(POST_LIKED_KEY + postId, user.getId().toString(), System.currentTimeMillis());
        } else {
            post.updateFavours(true);
            postRepository.save(post);
            stringRedisTemplate.opsForZSet().remove(POST_LIKED_KEY + postId, user.getId().toString());
        }
    }

    // keypoint: 当前用户是否已点赞
    public boolean isLiked(Long postId) {
        UserBasicCommand user = UserHolder.getUser();
        if (user == null) {
            return false;
        }
        Double score = stringRedisTemplate
                .opsForZSet()
                .score(POST_LIKED_KEY + postId, user.getId().toString()
                );
        return score != null;
    }

    // keypoint: 根据postId查询具体post并统计浏览量
    public PostResult getPostByPostId(Long postId) {
        Post post = postRepository.findPostById(postId);
        User user = userRepository.findUserById(post.getAuthorId());
        Boolean isBrowse = stringRedisTemplate
                .opsForValue()
                .setIfAbsent(POST_BROWSE_KEY + user.getId() + postId, "true", 6L, TimeUnit.HOURS);
        if (Boolean.TRUE.equals(isBrowse)) {
            post.updateBrowse();
        }
        return new PostResult(
                user.getId(),
                user.getNikeName(),
                user.getIcon(),
                post.getTitle(),
                post.getCover(),
                post.getContent(),
                post.getFavours(),
                post.getBrowse(),
                isLiked(post.getId()),
                Duration.between(post.getCreatedAt(), OffsetDateTime.now()).toDays() < 1
                        ?
                        Duration.between(post.getCreatedAt(), OffsetDateTime.now())
                        :
                        Duration.between(OffsetDateTime.parse("0000-01-01T00:00:00-8:00"), post.getCreatedAt())
        );

    }

    // keypoint: 编辑post
    public void editPostInfo(EditPostInfoCommand command) {
        Post post = postRepository.findPostById(command.postId());
        post.setTitle(command.editedPostInfo().title());
        post.setCover(command.editedPostInfo().cover());
        post.setContent(command.editedPostInfo().content());
        postRepository.save(post);
    }
}
