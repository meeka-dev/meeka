package app.meeka.presentation.rest.request;

public record CreateUserRequest (
        String email,
        String code
){
}
