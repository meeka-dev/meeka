package app.meeka.application;


import app.meeka.application.result.PersonalCenterResult;
import app.meeka.application.result.Result;
import app.meeka.domain.exception.UserNotFoundException;
import app.meeka.domain.model.User;
import app.meeka.domain.repository.UserRepository;
import app.meeka.utils.UserHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonalCenterApplicationService {

    private final UserRepository userRepository;

    public PersonalCenterApplicationService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
