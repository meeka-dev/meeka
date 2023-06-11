package app.meeka.presentation.rest;

import app.meeka.application.UserCommandApplicationService;
import app.meeka.application.command.CreateUserCommand;
import app.meeka.application.result.UserLoginResult;
import app.meeka.domain.exception.InvalidLoginCodeException;
import app.meeka.domain.exception.InvalidUserInfoException;
import app.meeka.presentation.rest.request.CreateUserRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserCommandRestController {

    private final UserCommandApplicationService userLoginApplicationService;

    public UserCommandRestController(UserCommandApplicationService userLoginApplicationService) {
        this.userLoginApplicationService = userLoginApplicationService;
    }

    @PostMapping("/login")
    public UserLoginResult userLogin(@RequestBody CreateUserRequest request) throws InvalidLoginCodeException, InvalidUserInfoException {
        var command = new CreateUserCommand(request.email(), request.code());
        return userLoginApplicationService.userLoginWithCode(command);
    }

    @PostMapping("/code")
    public void sendCode(@RequestParam("email") String email) {
        userLoginApplicationService.sendCodeByEmail(email);
    }

    @PostMapping("/logout")
    public void userLogout(@RequestParam("token") String token) {
        userLoginApplicationService.logout(token);
    }
}
