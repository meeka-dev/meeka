package app.meeka.application;

import app.meeka.application.command.UserBasicCommand;
import app.meeka.application.result.PersonalCenterResult;
import app.meeka.application.result.UserFollowedResult;
import app.meeka.application.result.UserListResult;
import app.meeka.core.context.UserHolder;
import app.meeka.domain.exception.InvalidFollowOperationException;
import app.meeka.domain.exception.UserNotFoundException;
import app.meeka.domain.model.user.Follow;
import app.meeka.domain.model.user.User;
import app.meeka.domain.repository.FollowRepository;
import app.meeka.domain.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static app.meeka.core.cache.RedisConstants.FOLLOW_KEY;
import static app.meeka.core.cache.RedisConstants.LOGIN_USER_KEY;
import static java.util.Collections.emptyList;

@Service
public class PersonalCenterApplicationService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final UserRepository userRepository;

    private final FollowRepository followRepository;

    public PersonalCenterApplicationService(UserRepository userRepository, FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }

    // keypoint：返回当前登录用户的详细个人信息
    public PersonalCenterResult getUserHolder() throws UserNotFoundException {
        User user = userRepository
                .findById(UserHolder.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException(UserHolder.getUser().getId()));
        return new PersonalCenterResult(
                user.getNikeName(),
                user.getIcon(),
                user.getFans(),
                user.getFollowee(),
                user.getGender(),
                user.getCity(),
                user.getBirthday(),
                user.getIntroduce()
        );
    }

    //keypoint: 关注/取关
    public void toggleFollowState(Long followingUserId) throws InvalidFollowOperationException {
        var currentUser = UserHolder.getUser();
        if (Objects.equals(followingUserId, currentUser.getId())) throw new InvalidFollowOperationException();
        var followState = isFollow(currentUser.getId(), followingUserId).followed();
        if (!followState) {
            Follow follow = new Follow(currentUser.getId(), followingUserId);
            Follow save = followRepository.save(follow);
            Optional<User> userOptional = userRepository.findById(currentUser.getId());
            Optional<User> userOptionalBeingFollowed = userRepository.findById(followingUserId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                User userBeingFollowed = userOptionalBeingFollowed.orElseThrow();
                user.updateFollow();
                userBeingFollowed.updateFans();
                userRepository.save(user);
                userRepository.save(userBeingFollowed);
            }
            if (follow.equals(save)) {
                stringRedisTemplate.opsForSet().add(FOLLOW_KEY + currentUser.getId(), followingUserId.toString());
            }
        } else {
            boolean isSuccess = followRepository.deleteByFollowUserIdAndUserId(followingUserId, currentUser.getId());
            if (isSuccess)
                stringRedisTemplate.opsForSet().remove(FOLLOW_KEY + currentUser.getId(), followingUserId.toString());
        }
    }

    // keypoint: 是否已关注
    public UserFollowedResult isFollow(Long userId, Long followingUserId) {
        boolean followed = followRepository.existsByFollowUserIdAndUserId(userId, followingUserId);
        return new UserFollowedResult(followingUserId, followed);
    }

    // keypoint: 获取关注列表
    public UserListResult getFollows() {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        List<Long> followIds = followRepository
                .getFollowsByUserId(userBasicCommand.getId())
                .stream()
                .map(Follow::getFollowId)
                .toList();
        List<UserBasicCommand> follows = userRepository
                .findAllById(followIds)
                .stream()
                .map(user -> new UserBasicCommand(user.getId(), user.getNikeName(), user.getIcon()))
                .collect(Collectors.toList());
        return new UserListResult(follows);

    }

    // keypoint: 获取粉丝列表
    public List<UserBasicCommand> getFans() {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        List<Long> fansIds = followRepository
                .getFollowsByFollowUserId(userBasicCommand.getId())
                .stream()
                .map(Follow::getUserId)
                .toList();
        return userRepository
                .findAllById(fansIds)
                .stream()
                .map(user -> new UserBasicCommand(user.getId(), user.getNikeName(), user.getIcon()))
                .collect(Collectors.toList());
    }

    // keyPoint: 获取共同关注
    public List<UserBasicCommand> getCommonFollows(Long userId) {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        // 求交集
        Set<String> intersect = stringRedisTemplate.opsForSet()
                .intersect(LOGIN_USER_KEY + userBasicCommand.getId(), LOGIN_USER_KEY + userId);
        if (intersect == null || intersect.isEmpty()) return emptyList();
        List<Long> commonFollowIds = intersect.stream().map(Long::valueOf).toList();
        return userRepository
                .findAllById(commonFollowIds)
                .stream()
                .map(user -> new UserBasicCommand(user.getId(), user.getNikeName(), user.getIcon()))
                .toList();
    }
}

