package app.meeka.application;


import app.meeka.application.command.UserHolderCommand;
import app.meeka.application.result.PersonalCenterResult;
import app.meeka.application.result.Result;
import app.meeka.domain.exception.UserNotFoundException;
import app.meeka.domain.model.Follow;
import app.meeka.domain.model.User;
import app.meeka.domain.repository.FollowRepository;
import app.meeka.domain.repository.UserRepository;
import app.meeka.utils.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import static app.meeka.utils.RedisConstants.FOLLOW_KEY;

@Service
public class PersonalCenterApplicationService {

    private StringRedisTemplate stringRedisTemplate;

    private final UserRepository userRepository;

    private final FollowRepository followRepository;

    public PersonalCenterApplicationService(UserRepository userRepository, FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }

    //keypoint：返回当前登录用户的详细个人信息
    public Result getUserHolder() throws UserNotFoundException, ClassNotFoundException {
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
    public Result followOrUnfollow(Long followUserId,Boolean isFollow){
        UserHolderCommand userHolderCommand=UserHolder.getUser();
        if (userHolderCommand == null) {
            return Result.defeat("未登录！");
        }
        Long userId=userHolderCommand.getId();
        if (!isFollow){
            //关注
            Follow follow=new Follow(userId,followUserId);
            Follow save = followRepository.save(follow);
            if (follow.equals(save)){
                stringRedisTemplate.opsForSet().add(FOLLOW_KEY+userId,followUserId.toString());
            }
        }else {
            //取关
            boolean isSuccess = followRepository.deleteByFollowIdAndUserId(followUserId, userId);
            if (isSuccess){
                stringRedisTemplate.opsForSet().remove(FOLLOW_KEY+userId,followUserId.toString());
            }
        }
        return Result.Success();
    }

    //keypoint: 是否已关注
    public Result isFollow(Long followUserId){
        UserHolderCommand userHolderCommand=UserHolder.getUser();
        if (userHolderCommand == null) {
            return Result.defeat("未登录！");
        }
        boolean isFollow = followRepository.existsByFollowIdAndUserId(followUserId, userHolderCommand.getId());
        return Result.Success(isFollow);
    }

    //todo: 获取关注列表
    public Result getFollows(){
        return Result.Success();
    }
}










