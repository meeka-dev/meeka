package app.meeka.application;


import app.meeka.application.command.UserBasicCommand;
import app.meeka.application.result.PersonalCenterResult;
import app.meeka.application.result.Result;
import app.meeka.domain.exception.UserNotFoundException;
import app.meeka.domain.model.Follow;
import app.meeka.domain.model.User;
import app.meeka.domain.repository.FollowRepository;
import app.meeka.domain.repository.UserRepository;
import app.meeka.utils.UserHolder;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static app.meeka.utils.RedisConstants.FOLLOW_KEY;
import static app.meeka.utils.RedisConstants.LOGIN_USER_KEY;


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

    //keypoint：返回当前登录用户的详细个人信息
    public Result getUserHolder() throws UserNotFoundException {
        User user = userRepository
                .findById(UserHolder.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException(UserHolder.getUser().getId()));
        PersonalCenterResult personalCenterResult = new PersonalCenterResult(
                user.getNikeName(),
                user.getIcon(),
                user.getFans(),
                user.getFollowee(),
                user.getGender(),
                user.getCity(),
                user.getIntroduce()
        );
        return Result.Success(personalCenterResult);
    }

    //keypoint: 关注和取关用户
    public Result followOrUnfollow(Long followUserId, Boolean isFollow) {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        if (userBasicCommand == null) {
            return Result.defeat("未登录！");
        }
        Long userId = userBasicCommand.getId();
        if (!isFollow) {
            //关注
            Follow follow = new Follow(userId, followUserId);
            Follow save = followRepository.save(follow);
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.updateFollow();
                userRepository.save(user);
            }
            if (follow.equals(save)) {
                stringRedisTemplate.opsForSet().add(FOLLOW_KEY + userId, followUserId.toString());
            }
        }else {
            //取关
            boolean isSuccess = followRepository.deleteByFollowUserIdAndUserId(followUserId, userId);
            if (isSuccess){
                stringRedisTemplate.opsForSet().remove(FOLLOW_KEY+userId,followUserId.toString());
            }
        }
        return Result.Success();
    }

    //keypoint: 是否已关注
    public Result isFollow(Long followUserId) {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        if (userBasicCommand == null) {
            return Result.defeat("未登录！");
        }
        boolean isFollow = followRepository.existsByFollowUserIdAndUserId(followUserId, userBasicCommand.getId());
        return Result.Success(isFollow);
    }

    //keypoint: 获取关注列表
    public Result getFollows() {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        List<Long> followIds = followRepository
                .getFollowsByUserId(userBasicCommand.getId())
                .stream()
                .map(Follow::getId)
                .toList();
        List<UserBasicCommand> follows = userRepository
                .findAllById(followIds)
                .stream()
                .map(user -> new UserBasicCommand(user.getId(), user.getNikeName(), user.getIcon()))
                .collect(Collectors.toList());
        return Result.Success(follows);
    }

    //keypoint: 获取粉丝列表
    public Result getFans() {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        List<Long> fansIds = followRepository
                .getFollowsByFollowUserId(userBasicCommand.getId())
                .stream()
                .map(Follow::getId)
                .toList();
        List<UserBasicCommand> fans = userRepository
                .findAllById(fansIds)
                .stream()
                .map(user -> new UserBasicCommand(user.getId(), user.getNikeName(), user.getIcon()))
                .collect(Collectors.toList());
        return Result.Success(fans);
    }

    //keyPoint: 获取共同关注
    public Result getCommonFollows(Long userId) {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        //求交集
        Set<String> intersect = stringRedisTemplate.opsForSet()
                .intersect(LOGIN_USER_KEY + userBasicCommand.getId(), LOGIN_USER_KEY + userId);
        if (intersect == null || intersect.isEmpty()) {
            return Result.Success(Collections.emptyList());
        }
        List<Long> commonFollowIds = intersect.stream().map(Long::valueOf).toList();
        List<User> commonFollows = userRepository.findAllById(commonFollowIds);
        return Result.Success(commonFollows);
    }
}










