package app.meeka.presentation.rest;

import app.meeka.application.SetUpApplicationService;
import app.meeka.application.command.CreateUserCommand;
import app.meeka.application.result.Result;
import app.meeka.domain.exception.InvalidCodeException;
import app.meeka.domain.exception.InvalidUserInfoException;
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
    public Result createPassword(@RequestParam("password") String password) {
        return setUpApplicationService.createPassword(password);
    }

    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody CreateUserRequest userRequest, @RequestParam("newPassword") String newPassword) throws InvalidCodeException {
        CreateUserCommand command = new CreateUserCommand(userRequest.email(), userRequest.code());
        return setUpApplicationService.updatePassword(command, newPassword);
    }

    @PostMapping("/updateBirthday")
    public Result updateBirthday(@RequestParam("birthday") String birthdayStr) {
        return setUpApplicationService.updateBirthday(birthdayStr);
    }

    @PostMapping("/updateGender")
    public Result updateGender(@RequestParam("gender") String gender) throws InvalidUserInfoException {
        return setUpApplicationService.updateGender(gender);
    }

    @PostMapping("/updateCity")
    public Result updateCity(@RequestParam("city") String city) {
        return setUpApplicationService.updateCity(city);
    }

    @PostMapping("/updateNickName")
    public Result updateNickName(@RequestParam("nickName") String nikeName) {
        return setUpApplicationService.updateNickName(nikeName);
    }

    @PostMapping("/updateIntroduce")
    public Result updateIntroduce(@RequestParam("introduce") String introduce) {
        return setUpApplicationService.updateIntroduce(introduce);
    }

    @PostMapping("/updatePasswordCode")
    public Result updatePasswordCode(@RequestParam("email") String email) {
        return setUpApplicationService.updatePasswordCode(email);
    }

    @PostMapping("/updateIcon")
    public Result updateIcon(@RequestParam("icon") String icon) {
        return setUpApplicationService.updateIcon(icon);
    }
}
