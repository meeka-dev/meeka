package app.meeka.presentation.rest;

import app.meeka.application.PersonalCenterApplicationService;
import app.meeka.application.result.Result;
import app.meeka.domain.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/me")
public class PersonalCenterRestController {

    private final PersonalCenterApplicationService personalCenterApplicationService;

    public PersonalCenterRestController(PersonalCenterApplicationService personalCenterApplicationService) {
        this.personalCenterApplicationService = personalCenterApplicationService;
    }

    @GetMapping("/details")
    public Result PersonalDetails() throws UserNotFoundException, ClassNotFoundException {
        return personalCenterApplicationService.getUserHolder();
    }

    @GetMapping("/follows")
    public Result getFollows() {
        return personalCenterApplicationService.getFollows();
    }

    @PostMapping("/follow")
    public Result follow(@RequestParam("id") Long id, @RequestParam("isFollow") Boolean isFollow) {
        return personalCenterApplicationService.followOrUnfollow(id, isFollow);
    }

    @GetMapping("/isFollow")
    public Result isFollow(@RequestParam("id") Long id) {
        return personalCenterApplicationService.isFollow(id);
    }

    @GetMapping("/fans")
    public Result getFans() {
        return personalCenterApplicationService.getFans();
    }

    @GetMapping("/commonFollows")
    public Result getCommonFollows(@RequestParam("id") Long id) {
        return personalCenterApplicationService.getCommonFollows(id);
    }
}

