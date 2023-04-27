package app.meeka.presentation.rest;


import app.meeka.application.UserLoginApplicationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserCommandRestController {

    private final UserLoginApplicationService userLoginApplicationService;

    public UserCommandRestController(UserLoginApplicationService userLoginApplicationService) {
        this.userLoginApplicationService = userLoginApplicationService;
    }

}
