package app.meeka.presentation.rest;


import app.meeka.application.UserLoginApplicationService;
import app.meeka.application.command.CreatePostCommand;
import app.meeka.domain.exception.InvalidPostInfoException;
import app.meeka.presentation.rest.request.CreatePostRequest;
import app.meeka.presentation.rest.response.PostCreatedResponse;
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
    
}
