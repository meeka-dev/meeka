package app.meeka.presentation.rest;


import app.meeka.application.UserHomePageApplicationService;
import app.meeka.application.UserLoginApplicationService;
import app.meeka.application.command.CreateUserCommand;
import app.meeka.application.command.LoginPasswordCommand;
import app.meeka.application.result.PersonalCenterResult;
import app.meeka.application.result.UserLoginResult;
import app.meeka.domain.exception.InvalidCodeException;
import app.meeka.domain.exception.InvalidUserInfoException;
import app.meeka.domain.exception.PasswordErrException;
import app.meeka.domain.exception.UserNotFoundException;
import app.meeka.presentation.rest.request.CreateUserRequest;
import app.meeka.presentation.rest.request.LoginPasswordRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserCommandRestController {

    private final UserLoginApplicationService userLoginApplicationService;

    private final UserHomePageApplicationService userHomePageApplicationService;

    public UserCommandRestController(UserLoginApplicationService userLoginApplicationService, UserHomePageApplicationService userHomePageApplicationService) {
        this.userLoginApplicationService = userLoginApplicationService;
        this.userHomePageApplicationService = userHomePageApplicationService;
    }

    @PostMapping("/login")
    public UserLoginResult userLogin(@RequestBody CreateUserRequest userRequest) throws InvalidCodeException, InvalidUserInfoException {
        CreateUserCommand command = new CreateUserCommand(userRequest.email(), userRequest.code());
        return userLoginApplicationService.userLoginWithCode(command);
    }

    @PostMapping("/loginWithPassword")
    public UserLoginResult userLoginWithPassword(@RequestBody LoginPasswordRequest loginPasswordRequest) throws PasswordErrException {
        LoginPasswordCommand command = new LoginPasswordCommand(loginPasswordRequest.email(), loginPasswordRequest.password());
        return userLoginApplicationService.userLoginWithPassword(command);
    }

    @PostMapping("/code")
    public void sendCode(@RequestParam("email") String email) throws InvalidUserInfoException {
        userLoginApplicationService.sendCodeByEmail(email);
    }

    @PostMapping("/logout")
    public void userLogout(@RequestParam("token") String token) {
        userLoginApplicationService.logout(token);
    }

    @GetMapping("/{id}")
    public PersonalCenterResult getUserInformation(@PathVariable("id") Long id) throws UserNotFoundException {
        return userHomePageApplicationService.getUserInformation(id);
    }
}
