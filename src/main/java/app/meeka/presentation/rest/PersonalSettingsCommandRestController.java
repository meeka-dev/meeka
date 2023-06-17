package app.meeka.presentation.rest;

import app.meeka.application.PersonalSettingsApplicationService;
import app.meeka.application.command.CreateUserCommand;
import app.meeka.domain.exception.*;
import app.meeka.presentation.rest.request.CreateUserRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/setUp")
public class PersonalSettingsCommandRestController {

    private final PersonalSettingsApplicationService personalSettingsApplicationService;

    public PersonalSettingsCommandRestController(PersonalSettingsApplicationService personalSettingsApplicationService) {
        this.personalSettingsApplicationService = personalSettingsApplicationService;
    }

    @PostMapping("/createPassword")
    public void createPassword(@RequestParam("password") String password) throws InvalidPasswordFormatException {
        personalSettingsApplicationService.createPassword(password);
    }

    @PostMapping("/updatePassword")
    public void updatePassword(@RequestBody CreateUserRequest userRequest, @RequestParam("newPassword") String newPassword) throws InvalidCodeException, MismatchedEmailBindingException, InvalidPasswordFormatException {
        CreateUserCommand command = new CreateUserCommand(userRequest.email(), userRequest.code());
        personalSettingsApplicationService.updatePassword(command, newPassword);
    }

    @PostMapping("/updateBirthday")
    public void updateBirthday(@RequestParam("birthday") String birthdayStr) {
        personalSettingsApplicationService.updateBirthday(birthdayStr);
    }

    @PostMapping("/updateGender")
    public void updateGender(@RequestParam("gender") String gender) throws InvalidUserInfoException {
        personalSettingsApplicationService.updateGender(gender);
    }

    @PostMapping("/updateCity")
    public void updateCity(@RequestParam("city") String city) {
        personalSettingsApplicationService.updateCity(city);
    }

    @PostMapping("/updateNickName")
    public void updateNickName(@RequestParam("nickName") String nikeName) throws InvalidUsernameFormatException {
        personalSettingsApplicationService.updateNickName(nikeName);
    }

    @PostMapping("/updateIntroduce")
    public void updateIntroduce(@RequestParam("introduce") String introduce) {
        personalSettingsApplicationService.updateIntroduce(introduce);
    }

    @PostMapping("/updatePasswordCode")
    public void updatePasswordCode(@RequestParam("email") String email) {
        personalSettingsApplicationService.updatePasswordCode(email);
    }

    @PostMapping("/updateIcon")
    public void updateIcon(@RequestParam("icon") String icon) {
        personalSettingsApplicationService.updateIcon(icon);
    }
}
