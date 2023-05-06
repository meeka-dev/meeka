package app.meeka.application;


import app.meeka.application.command.CreateUserCommand;
import app.meeka.application.command.UserBasicCommand;
import app.meeka.application.result.Result;
import app.meeka.domain.exception.InvalidCodeException;
import app.meeka.domain.exception.InvalidUserInfoException;
import app.meeka.domain.model.User;
import app.meeka.domain.repository.UserRepository;
import app.meeka.utils.MailUtils;
import app.meeka.utils.UserHolder;
import cn.hutool.core.util.RandomUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

import static app.meeka.utils.MailUtils.*;
import static app.meeka.utils.RedisConstants.UPDATE_PASSWORD_CODE_KEY;
import static app.meeka.utils.RedisConstants.UPDATE_PASSWORD_CODE_TTL;
import static java.util.concurrent.TimeUnit.MINUTES;

@Service
public class SetUpApplicationService {

    private final UserRepository userRepository;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public SetUpApplicationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //keypoint: 创建密码
    public Result createPassword(String password) {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        User user = userRepository.findUserById(userBasicCommand.getId());
        if (user.getPassword().equals("")) {

            if (user.updatePassword(password)) {
                userRepository.save(user);
            } else {
                return Result.defeat("密码格式错误!");
            }
        } else {
            return Result.defeat("已设置密码!");
        }
        return Result.Success("密码设置成功!");
    }

    //keypoint: 修改密码
    public Result updatePassword(CreateUserCommand userCommand, String newPassword) throws InvalidCodeException {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        User user = userRepository.findByIdAndEmail(userBasicCommand.getId(), userCommand.email());
        if (user != null) {
            String cacheCode = stringRedisTemplate.opsForValue().get(UPDATE_PASSWORD_CODE_KEY + userCommand.email());
            String code = userCommand.code();
            if (cacheCode == null || !cacheCode.equals(code)) {
                throw new InvalidCodeException();
            }
            if (user.updatePassword(newPassword)) {
                userRepository.save(user);
            } else {
                return Result.defeat("密码格式错误!");
            }
        } else {
            return Result.defeat("邮箱与账户不一致!");
        }
        return Result.Success("修改成功!");
    }

    //keypoint: 邮箱发送修改密码验证码
    public Result updatePasswordCode(String email) {
        String code = RandomUtil.randomNumbers(6);
        stringRedisTemplate.opsForValue().set(
                UPDATE_PASSWORD_CODE_KEY + email,
                code,
                UPDATE_PASSWORD_CODE_TTL, MINUTES
        );
        MailUtils.sendMail(
                email,
                UPDATE_PASSWORD_CODE_MESSAGE + code + UPDATE_PASSWORD_CODE_INFORMATION,
                UPDATE_PASSWORD_CODE_TITLE
        );
        return Result.Success("已发送!");
    }

    //keypoint: 编辑用户名
    public Result updateNickName(String NickName) {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        Optional<User> userOptional = userRepository.findById(userBasicCommand.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.updateNickName(NickName)) {
                userRepository.save(user);
            } else {
                return Result.defeat("用户名格式错误!");
            }
        }
        return Result.Success("修改成功!");
    }

    //keypoint: 编辑头像
    public Result updateIcon(String icon) {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        Optional<User> userOptional = userRepository.findById(userBasicCommand.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.updateIcon(icon);
            userRepository.save(user);
        }
        return Result.Success("更换成功!");
    }

    //keypoint: 编辑生日
    // TODO: 2023/5/6 日期格式化 
    public Result updateBirthday(OffsetDateTime birthday) {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        Optional<User> userOptional = userRepository.findById(userBasicCommand.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.updateBirthday(birthday);
            userRepository.save(user);
        }
        return Result.Success("编辑成功!");
    }

    //keypoint: 编辑城市
    public Result updateCity(String city) {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        Optional<User> userOptional = userRepository.findById(userBasicCommand.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.updateCity(city);
            userRepository.save(user);
        }
        return Result.Success("编辑成功!");
    }

    //keypoint: 编辑性别
    public Result updateGender(String gender) throws InvalidUserInfoException {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        Optional<User> userOptional = userRepository.findById(userBasicCommand.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.updateGender(gender);
            userRepository.save(user);
        }
        return Result.Success("编辑成功!");
    }

    //keypoint: 编辑个人介绍
    public Result updateIntroduce(String introduce) {
        UserBasicCommand userBasicCommand = UserHolder.getUser();
        Optional<User> userOptional = userRepository.findById(userBasicCommand.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.updateIntroduce(introduce);
            userRepository.save(user);
        }
        return Result.Success("编辑成功!");
    }

}
