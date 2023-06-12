package app.meeka.presentation.rest;


import app.meeka.application.UserHomePageApplicationService;
import app.meeka.application.UserLoginApplicationService;
import app.meeka.application.command.CreateUserCommand;
import app.meeka.application.result.Result;
import app.meeka.domain.exception.InvalidCodeException;
import app.meeka.domain.exception.InvalidUserInfoException;
import app.meeka.domain.exception.UserNotFoundException;
import app.meeka.presentation.rest.request.CreateUserRequest;
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
    public Result userLogin(@RequestBody CreateUserRequest userRequest) throws InvalidCodeException, InvalidUserInfoException {
        CreateUserCommand command = new CreateUserCommand(userRequest.email(), userRequest.code());
        return userLoginApplicationService.userLoginWithCode(command);
    }

    @PostMapping("/code")
    public Result sendCode(@RequestParam("email") String email) throws InvalidUserInfoException {
        return userLoginApplicationService.sendCodeByEmail(email);
    }

    @PostMapping("/logout")
    public Result userLogout(@RequestParam("token") String token) {
        return userLoginApplicationService.logout(token);
    }

    @GetMapping("/{id}")
    public Result getUserInformation(@PathVariable("id") Long id) throws UserNotFoundException {
        return userHomePageApplicationService.getUserInformation(id);
    }
}
