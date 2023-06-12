package app.meeka.presentation.rest;

import app.meeka.application.SetUpApplicationService;
import app.meeka.application.command.CreateUserCommand;
import app.meeka.domain.exception.*;
import app.meeka.presentation.rest.request.CreateUserRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/setUp")
public class SetUpRestController {

    private final SetUpApplicationService setUpApplicationService;

    public SetUpRestController(SetUpApplicationService setUpApplicationService) {
        this.setUpApplicationService = setUpApplicationService;
    }

    @PostMapping("/createPassword")
    public void createPassword(@RequestParam("password") String password) throws InvalidPasswordFormatException {
        setUpApplicationService.createPassword(password);
    }

    @PostMapping("/updatePassword")
    public void updatePassword(@RequestBody CreateUserRequest userRequest, @RequestParam("newPassword") String newPassword) throws InvalidCodeException, MismatchedEmailBindingException, InvalidPasswordFormatException {
        CreateUserCommand command = new CreateUserCommand(userRequest.email(), userRequest.code());
        setUpApplicationService.updatePassword(command, newPassword);
    }

    @PostMapping("/updateBirthday")
    public void updateBirthday(@RequestParam("birthday") String birthdayStr) {
        setUpApplicationService.updateBirthday(birthdayStr);
    }

    @PostMapping("/updateGender")
    public void updateGender(@RequestParam("gender") String gender) throws InvalidUserInfoException {
        setUpApplicationService.updateGender(gender);
    }

    @PostMapping("/updateCity")
    public void updateCity(@RequestParam("city") String city) {
        setUpApplicationService.updateCity(city);
    }

    @PostMapping("/updateNickName")
    public void updateNickName(@RequestParam("nickName") String nikeName) throws InvalidUsernameFormatException {
        setUpApplicationService.updateNickName(nikeName);
    }

    @PostMapping("/updateIntroduce")
    public void updateIntroduce(@RequestParam("introduce") String introduce) {
        setUpApplicationService.updateIntroduce(introduce);
    }

    @PostMapping("/updatePasswordCode")
    public void updatePasswordCode(@RequestParam("email") String email) {
        setUpApplicationService.updatePasswordCode(email);
    }

    @PostMapping("/updateIcon")
    public void updateIcon(@RequestParam("icon") String icon) {
        setUpApplicationService.updateIcon(icon);
    }
}
