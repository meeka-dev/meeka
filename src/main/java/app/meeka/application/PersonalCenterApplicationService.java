package app.meeka.application;


import app.meeka.application.command.UserHolderCommand;
import app.meeka.application.result.PersonalCenterResult;
import app.meeka.application.result.Result;
import app.meeka.domain.model.User;
import app.meeka.domain.repository.UserRepository;
import app.meeka.utils.UserHolder;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonalCenterApplicationService {

    private final UserRepository userRepository;

    public PersonalCenterApplicationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //keypoint：返回当前登录用户的详细个人信息
    public Result getUserHolder(){
//        UserHolderCommand userHolderCommand = UserHolder.getUser();
        Optional<User> user = userRepository.findById(UserHolder.getUser().getId());
        PersonalCenterResult personalCenterResult = BeanUtil.copyProperties(user, PersonalCenterResult.class);
        return Result.Success(personalCenterResult);
    }
}
