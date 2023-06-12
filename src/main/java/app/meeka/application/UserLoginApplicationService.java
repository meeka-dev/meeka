package app.meeka.application;


import app.meeka.application.command.CreateUserCommand;
import app.meeka.application.command.UserBasicCommand;
import app.meeka.application.result.UserLoginResult;
import app.meeka.domain.exception.InvalidCodeException;
import app.meeka.domain.exception.InvalidUserInfoException;
import app.meeka.domain.model.User;
import app.meeka.domain.model.user.UserInfo;
import app.meeka.domain.repository.UserRepository;
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

import static app.meeka.core.cache.RedisConstants.LOGIN_CODE_KEY;
import static app.meeka.core.cache.RedisConstants.LOGIN_CODE_TTL;
import static app.meeka.core.cache.RedisConstants.LOGIN_USER_KEY;
import static app.meeka.core.cache.RedisConstants.LOGIN_USER_TTL;
import static app.meeka.core.mail.MailSender.LOGIN_CODE_INFORMATION;
import static app.meeka.core.mail.MailSender.LOGIN_CODE_MESSAGE;
import static app.meeka.core.mail.MailSender.LOGIN_CODE_TITLE;
import static app.meeka.core.mail.MailSender.sendMail;
import static java.util.concurrent.TimeUnit.MINUTES;

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
    public UserLoginResult userLoginWithCode(CreateUserCommand userCommand) throws InvalidUserInfoException, InvalidCodeException {
        User newUser = new User(new UserInfo(userCommand.email()));
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + userCommand.email());
        String code = userCommand.code();
        if (cacheCode == null || !cacheCode.equals(code)) {
            throw new InvalidCodeException();
        }
        if (!userRepository.existsByEmail(newUser.getEmail())) {
            userRepository.save(newUser);
        }
        User user = userRepository.findByEmail(newUser.getEmail());
        String token = UUID.randomUUID().toString(true);
        UserBasicCommand userBasicCommand = new UserBasicCommand(user.getId(), user.getNikeName(), user.getIcon());
        // hashMap储存user信息
        Map<String, Object> userMap = BeanUtil.beanToMap(userBasicCommand, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((filedName, filedValue) -> filedValue.toString())
        );
        stringRedisTemplate.opsForHash().putAll(LOGIN_USER_KEY + token, userMap);
        stringRedisTemplate.expire(LOGIN_USER_KEY + token, LOGIN_USER_TTL, MINUTES);
        return new UserLoginResult(token);
    }

    // keypoint: 邮箱发送登录验证码
    public void sendCodeByEmail(String email) throws InvalidUserInfoException {
        User user = new User(new UserInfo(email));
        String code = RandomUtil.randomNumbers(6);
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + email, code, LOGIN_CODE_TTL, MINUTES);
        sendMail(email, LOGIN_CODE_MESSAGE + code + LOGIN_CODE_INFORMATION, LOGIN_CODE_TITLE);
    }

    public void logout(String token) {
        System.out.println(token);
        stringRedisTemplate.opsForHash().delete(LOGIN_USER_KEY + token, "id");
    }

}
