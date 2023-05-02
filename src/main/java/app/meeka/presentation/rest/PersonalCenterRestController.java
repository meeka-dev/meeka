package app.meeka.presentation.rest;

import app.meeka.application.PersonalCenterApplicationService;
import app.meeka.application.result.Result;
import app.meeka.domain.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/me")
public class PersonalCenterRestController {

    private final PersonalCenterApplicationService personalCenterApplicationService;

    public PersonalCenterRestController(PersonalCenterApplicationService personalCenterApplicationService) {
        this.personalCenterApplicationService = personalCenterApplicationService;
    }

    @PostMapping("/details")
    public Result PersonalDetails() throws UserNotFoundException, ClassNotFoundException {
        return personalCenterApplicationService.getUserHolder();
    }
}

