package app.meeka.application;


import app.meeka.application.result.PersonalCenterResult;
import app.meeka.application.result.Result;
import app.meeka.domain.exception.UserNotFoundException;
import app.meeka.domain.model.User;
import app.meeka.domain.repository.UserRepository;
import app.meeka.utils.UserHolder;
import org.springframework.stereotype.Service;

@Service
public class UserHomePageApplicationService {

    private final UserRepository userRepository;

    public UserHomePageApplicationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //keypoint:获取指定用户信息
    public Result getUserInformation(Long id) throws UserNotFoundException {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(UserHolder.getUser().getId()));
        PersonalCenterResult personalCenterResult = new PersonalCenterResult(
                user.getNikeName(),
                user.getIcon(),
                user.getFans(),
                user.getFollowee(),
                user.getGender(),
                user.getCity(),
                user.getBirthday(),
                user.getIntroduce()
        );
        return Result.Success(personalCenterResult);
    }
}
