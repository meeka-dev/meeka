package app.meeka.application;


import app.meeka.application.command.CreateUserCommand;
import app.meeka.application.result.UserCreatedResult;
import app.meeka.domain.exception.InvalidUserInfoException;
import app.meeka.domain.repository.UserRepository;
import cn.hutool.core.util.RandomUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static app.meeka.domain.model.UserInfo.PHONE_REGEX;
import static java.util.Objects.nonNull;

@Service
public class UserLoginApplicationService {

    private final UserRepository userRepository;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public UserLoginApplicationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
//todo 验证码注册&登录
    public UserCreatedResult userLoginWithCode(CreateUserCommand userCommand) throws InvalidUserInfoException {
        return new UserCreatedResult(1L);
    }
//keypoint 手机号发送验证码
    public boolean sendCodeByPhone(String phone){
        String LOGIN_CODE_KEY = "login:code:";
        Long LOGIN_CODE_TTL = 5L;
        if (!nonNull(phone)&& Pattern.matches(phone,PHONE_REGEX)){
            return false;
        }
        String code= RandomUtil.randomNumbers(6);
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY+phone,code,LOGIN_CODE_TTL, TimeUnit.MINUTES);
        //todo 发送验证码
        return true;
    }
}
