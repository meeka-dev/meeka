package app.meeka.application;


import app.meeka.application.command.CreateUserCommand;
import app.meeka.application.command.UserHolderCommand;
import app.meeka.application.result.Result;
import app.meeka.domain.exception.InvalidLoginCodeException;
import app.meeka.domain.exception.InvalidUserInfoException;
import app.meeka.domain.model.User;
import app.meeka.domain.model.UserInfo;
import app.meeka.domain.repository.UserRepository;
import app.meeka.utils.MailUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static app.meeka.utils.MailUtils.CODE_INFORMATION;
import static app.meeka.utils.MailUtils.CODE_MESSAGE;
import static app.meeka.utils.RedisConstants.*;

@Service
@Transactional
public class UserLoginApplicationService {

    private final UserRepository userRepository;

    public UserLoginApplicationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Resource
    private StringRedisTemplate stringRedisTemplate;


// keypoint: 邮箱验证码注册&登录
    public Result userLoginWithCode(CreateUserCommand userCommand) throws InvalidUserInfoException, InvalidLoginCodeException {
        var user=new User(new UserInfo(userCommand.email()));
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY+userCommand.email());
        String code=userCommand.code();
        if (cacheCode==null||!cacheCode.equals(code)){
            throw new InvalidLoginCodeException();
        }
        if (!userRepository.existsByEmail(user.getEmail())){
            userRepository.save(user);
        }
        String token = UUID.randomUUID().toString(true);
        UserHolderCommand userHolderCommand = BeanUtil.copyProperties(user, UserHolderCommand.class);
        //hashMap储存user信息
        Map<String,Object> userMap = BeanUtil.beanToMap(userHolderCommand,new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((filedName,filedValue)->filedValue)
        );
        stringRedisTemplate.opsForHash().putAll(LOGIN_USER_KEY+token,userMap);
        stringRedisTemplate.expire(LOGIN_USER_KEY+token,LOGIN_USER_TTL,TimeUnit.MINUTES);
        return Result.Success(token);
    }
//keypoint: 邮箱发送验证码
    public Result sendCodeByEmail(String email) throws InvalidUserInfoException {
        var user=new User(new UserInfo(email));
        String code= RandomUtil.randomNumbers(6);
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY+email,code,LOGIN_CODE_TTL, TimeUnit.MINUTES);
        MailUtils.sendMail(email,CODE_MESSAGE+code+CODE_INFORMATION,CODE_MESSAGE);
        return Result.Success("发送成功");
    }
//keypoint: 登出
    public Result logout(String token){
        stringRedisTemplate.opsForHash().delete(LOGIN_USER_KEY+token);
        return Result.Success("已登出");
    }
}
