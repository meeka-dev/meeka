package app.meeka.application;


import app.meeka.application.command.CreateUserCommand;
import app.meeka.application.command.UserBasicCommand;
import app.meeka.core.context.UserHolder;
import app.meeka.core.mail.MailSender;
import app.meeka.domain.exception.InvalidCodeException;
import app.meeka.domain.exception.InvalidPasswordFormatException;
import app.meeka.domain.exception.InvalidUserInfoException;
import app.meeka.domain.exception.InvalidUsernameFormatException;
import app.meeka.domain.exception.MismatchedEmailBindingException;
import app.meeka.domain.model.User;
import app.meeka.domain.repository.UserRepository;
import cn.hutool.core.util.RandomUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static app.meeka.core.cache.RedisConstants.UPDATE_PASSWORD_CODE_KEY;
import static app.meeka.core.cache.RedisConstants.UPDATE_PASSWORD_CODE_TTL;
import static app.meeka.core.mail.MailSender.UPDATE_PASSWORD_CODE_INFORMATION;
import static app.meeka.core.mail.MailSender.UPDATE_PASSWORD_CODE_MESSAGE;
import static app.meeka.core.mail.MailSender.UPDATE_PASSWORD_CODE_TITLE;
import static java.util.Objects.isNull;
import static java.util.concurrent.TimeUnit.MINUTES;

@Service
public class SetUpApplicationService {

    private final UserRepository userRepository;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public SetUpApplicationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createPassword(String password) throws InvalidPasswordFormatException {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        User user = userRepository.findUserById(userBasicCommand.getId());
        if (user.getPassword().equals("")) {
            if (user.updatePassword(password)) {
                userRepository.save(user);
            } else {
                throw new InvalidPasswordFormatException();
            }
        }
    }

    // keypoint: 修改密码
    public void updatePassword(CreateUserCommand userCommand, String newPassword)
            throws InvalidCodeException, InvalidPasswordFormatException, MismatchedEmailBindingException {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        User user = userRepository.findByIdAndEmail(userBasicCommand.getId(), userCommand.email());
        if (isNull(user)) {
            String cacheCode = stringRedisTemplate.opsForValue().get(UPDATE_PASSWORD_CODE_KEY + userCommand.email());
            String code = userCommand.code();
            if (isNull(cacheCode) || !cacheCode.equals(code)) throw new InvalidCodeException();
            if (user.updatePassword(newPassword)) {
                userRepository.save(user);
            } else {
                throw new InvalidPasswordFormatException();
            }
        } else {
            throw new MismatchedEmailBindingException();
        }
    }

    // keypoint: 邮箱发送修改密码验证码
    public void updatePasswordCode(String email) {
        String code = RandomUtil.randomNumbers(6);
        stringRedisTemplate.opsForValue().set(
                UPDATE_PASSWORD_CODE_KEY + email,
                code,
                UPDATE_PASSWORD_CODE_TTL, MINUTES
        );
        MailSender.sendMail(
                email,
                UPDATE_PASSWORD_CODE_MESSAGE + code + UPDATE_PASSWORD_CODE_INFORMATION,
                UPDATE_PASSWORD_CODE_TITLE
        );
    }

    // keypoint: 编辑用户名
    public void updateNickName(String NickName) throws InvalidUsernameFormatException {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        Optional<User> userOptional = userRepository.findById(userBasicCommand.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.updateNickName(NickName)) {
                userRepository.save(user);
            } else {
                throw new InvalidUsernameFormatException();
            }
        }
    }

    // keypoint: 编辑头像
    public void updateIcon(String icon) {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        Optional<User> userOptional = userRepository.findById(userBasicCommand.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.updateIcon(icon);
            userRepository.save(user);
        }
    }

    // keypoint: 编辑生日
    public void updateBirthday(String birthdayStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(birthdayStr, formatter);
        OffsetDateTime birthday = localDate.atStartOfDay().atOffset(ZoneOffset.ofHours(8));
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        Optional<User> userOptional = userRepository.findById(userBasicCommand.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.updateBirthday(birthday);
            userRepository.save(user);
        }
    }

    // keypoint: 编辑城市
    public void updateCity(String city) {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        Optional<User> userOptional = userRepository.findById(userBasicCommand.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.updateCity(city);
            userRepository.save(user);
        }
    }

    // keypoint: 编辑性别
    public void updateGender(String gender) throws InvalidUserInfoException {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        Optional<User> userOptional = userRepository.findById(userBasicCommand.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.updateGender(gender);
            userRepository.save(user);
        }
    }

    // keypoint: 编辑个人介绍
    public void updateIntroduce(String introduce) {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        Optional<User> userOptional = userRepository.findById(userBasicCommand.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.updateIntroduce(introduce);
            userRepository.save(user);
        }
    }
}
