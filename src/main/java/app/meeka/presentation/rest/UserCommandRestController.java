package app.meeka.presentation.rest;


import app.meeka.application.UserLoginApplicationService;
import app.meeka.application.command.CreateUserCommand;
import app.meeka.application.result.Result;
import app.meeka.domain.exception.InvalidLoginCodeException;
import app.meeka.domain.exception.InvalidUserInfoException;
import app.meeka.presentation.rest.request.CreateUserRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserCommandRestController {

    private final UserLoginApplicationService userLoginApplicationService;

    public UserCommandRestController(UserLoginApplicationService userLoginApplicationService) {
        this.userLoginApplicationService = userLoginApplicationService;
    }

    @PostMapping("/login")
    public Result userLogin(@RequestBody CreateUserRequest userRequest) throws InvalidLoginCodeException, InvalidUserInfoException {
        var command=new CreateUserCommand(userRequest.email(),userRequest.code());
        return userLoginApplicationService.userLoginWithCode(command);
    }

    @PostMapping("/code")
    public Result sendCode(@RequestBody String email) throws InvalidLoginCodeException, InvalidUserInfoException {
        return userLoginApplicationService.sendCodeByEmail(email);
    }
}
