package app.meeka.presentation.rest;

import app.meeka.application.PersonalCenterApplicationService;
import app.meeka.application.command.UserBasicCommand;
import app.meeka.application.result.PersonalCenterResult;
import app.meeka.application.result.UserListResult;
import app.meeka.domain.exception.InvalidFollowOperationException;
import app.meeka.domain.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/me")
public class PersonalCenterRestController {

    private final PersonalCenterApplicationService personalCenterApplicationService;

    public PersonalCenterRestController(PersonalCenterApplicationService personalCenterApplicationService) {
        this.personalCenterApplicationService = personalCenterApplicationService;
    }

    @GetMapping("/details")
    public PersonalCenterResult PersonalDetails() throws UserNotFoundException {
        return personalCenterApplicationService.getUserHolder();
    }

    @GetMapping("/follows")
    public UserListResult getFollows() {
        return personalCenterApplicationService.getFollows();
    }

    @PostMapping("/follow")
    public void toggleFollowState(@RequestParam("id") Long id) throws InvalidFollowOperationException {
        personalCenterApplicationService.toggleFollowState(id);
    }

    @GetMapping("/fans")
    public List<UserBasicCommand> getFans() {
        return personalCenterApplicationService.getFans();
    }

    @GetMapping("/commonFollows")
    public List<UserBasicCommand> getCommonFollows(@RequestParam("id") Long id) {
        return personalCenterApplicationService.getCommonFollows(id);
    }
}

