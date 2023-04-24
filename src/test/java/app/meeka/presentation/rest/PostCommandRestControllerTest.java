package app.meeka.presentation.rest;

import app.meeka.application.PostCommandApplicationService;
import app.meeka.application.result.PostCreatedResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static app.meeka.presentation.rest.Requests.aCreatePostRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = { PostCommandRestController.class })
class PostCommandRestControllerTest {

    @MockBean
    private PostCommandApplicationService postCommandApplicationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_return_ok_when_create_post_via_rest_api() throws Exception {
        var postId = 1L;
        given(postCommandApplicationService.createPost(any())).willReturn(new PostCreatedResult(postId));
        mockMvc
                .perform(
                        post("/post/create-post")
                                .contentType(APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsBytes(aCreatePostRequest()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(postId))
        ;
    }
}