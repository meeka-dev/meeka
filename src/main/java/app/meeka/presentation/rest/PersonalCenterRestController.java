package app.meeka.presentation.rest;

import app.meeka.application.PersonalCenterApplicationService;
import app.meeka.application.result.Result;
import app.meeka.domain.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/follows")
    public Result getFollows(){
        return personalCenterApplicationService.getFollows();
    }

    @PostMapping("/follow/{id}/{isFollow}")
    public Result follow(@PathVariable("id") Long id,@PathVariable("isFollow") Boolean isFollow){
        return personalCenterApplicationService.followOrUnfollow(id,isFollow);
    }

    @PostMapping("/isFollow/{id}")
    public Result isFollow(@PathVariable("id") Long id){
        return personalCenterApplicationService.isFollow(id);
    }
}

