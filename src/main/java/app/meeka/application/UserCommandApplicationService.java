package app.meeka.application;

import app.meeka.application.command.CreateUserCommand;
import app.meeka.application.command.UserHolderCommand;
import app.meeka.application.result.UserLoginResult;
import app.meeka.core.mail.MailSender;
import app.meeka.domain.exception.InvalidLoginCodeException;
import app.meeka.domain.exception.InvalidUserInfoException;
import app.meeka.domain.model.user.User;
import app.meeka.domain.model.user.UserInfo;
import app.meeka.domain.repository.UserRepository;
import cn.hutool.core.bean.copier.CopyOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static app.meeka.core.cache.RedisConstants.LOGIN_CODE_KEY;
import static app.meeka.core.cache.RedisConstants.LOGIN_CODE_TTL;
import static app.meeka.core.cache.RedisConstants.LOGIN_USER_KEY;
import static app.meeka.core.cache.RedisConstants.LOGIN_USER_TTL;
import static app.meeka.core.mail.MailSender.CODE_INFORMATION;
import static app.meeka.core.mail.MailSender.CODE_MESSAGE;
import static cn.hutool.core.bean.BeanUtil.beanToMap;
import static cn.hutool.core.bean.BeanUtil.copyProperties;
import static cn.hutool.core.lang.UUID.randomUUID;
import static cn.hutool.core.util.RandomUtil.randomNumbers;
import static java.util.concurrent.TimeUnit.MINUTES;

@Service
@Transactional
public class UserCommandApplicationService {

    private final UserRepository userRepository;
    private final StringRedisTemplate stringRedisTemplate;

    public UserCommandApplicationService(UserRepository userRepository, StringRedisTemplate stringRedisTemplate) {
        this.userRepository = userRepository;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public UserLoginResult userLoginWithCode(CreateUserCommand userCommand) throws InvalidUserInfoException, InvalidLoginCodeException {
        User newUser = new User(new UserInfo(userCommand.email()));
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + userCommand.email());
        String code = userCommand.code();
        if (cacheCode == null || !cacheCode.equals(code)) throw new InvalidLoginCodeException();
        if (!userRepository.existsByEmail(newUser.getEmail())) userRepository.save(newUser);
        User user = userRepository.findByEmail(newUser.getEmail());
        String token = randomUUID().toString(true);
        UserHolderCommand userHolderCommand = copyProperties(user, UserHolderCommand.class);
        Map<String, Object> userMap = beanToMap(userHolderCommand, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((filedName, filedValue) -> filedValue.toString())
        );
        stringRedisTemplate.opsForHash().putAll(LOGIN_USER_KEY + token, userMap);
        stringRedisTemplate.expire(LOGIN_USER_KEY + token, LOGIN_USER_TTL, MINUTES);
        return new UserLoginResult(token);
    }

    public void sendCodeByEmail(String email) {
        String code = randomNumbers(6);
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + email, code, LOGIN_CODE_TTL, MINUTES);
        MailSender.sendMail(email, CODE_MESSAGE + code + CODE_INFORMATION, CODE_MESSAGE);
    }

    public void logout(String token) {
        stringRedisTemplate.opsForHash().delete(LOGIN_USER_KEY + token, "id");
    }
}