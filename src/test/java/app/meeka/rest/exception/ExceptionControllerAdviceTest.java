package app.meeka.rest.exception;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import static app.meeka.rest.exception.ExceptionControllerAdvice.DEFAULT_BUSINESS_EXCEPTION_MESSAGE_TEMPLATE;
import static app.meeka.rest.exception.ExceptionControllerAdvice.SYSTEM_EXCEPTION_MESSAGE;
import static java.util.Collections.emptyList;
import static java.util.Locale.CHINESE;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.JAPANESE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StubController.class)
class ExceptionControllerAdviceTest {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @MethodSource("paramsProviderForBusinessExceptionHandlerTest")
    void should_return_error_info_when_controller_throw_a_business_exception(
            String errorCode,
            List<String> args,
            Locale locale,
            String expectedMessage
    ) throws Exception {
        var request = post("/stub/throw-biz-error/{errorCode}", errorCode).locale(locale);
        args.forEach(arg -> request.queryParam("args", arg));
        mockMvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(errorCode))
                .andExpect(jsonPath("$.errorMessage").value(expectedMessage))
        ;
    }

    private static Stream<Arguments> paramsProviderForBusinessExceptionHandlerTest() {
        // the test data setup according to such files: `test/resources/messages/test-exception*.properties`
        return Stream.of(
                Arguments.of("TEST_000001", emptyList(), ENGLISH, "An exception message for test."),
                Arguments.of("TEST_000001", emptyList(), CHINESE, "一条测试用异常消息。"),
                Arguments.of("TEST_000001", emptyList(), JAPANESE, "An exception message for test."),
                Arguments.of("TEST_000002", List.of("foo", "bar"), ENGLISH, "An exception message for test, with args: foo, bar."),
                Arguments.of("TEST_000002", List.of("foo", "bar"), CHINESE, "一条测试用异常消息，附带参数：foo，bar。"),
                Arguments.of("TEST_000002", List.of("foo", "bar"), JAPANESE, "An exception message for test, with args: foo, bar."),
                Arguments.of("TEST_000003", emptyList(), ENGLISH, "An exception message for test, english only."),
                Arguments.of("TEST_000003", emptyList(), CHINESE, "An exception message for test, english only."),
                Arguments.of("TEST_000003", emptyList(), JAPANESE, "An exception message for test, english only."),
                Arguments.of("TEST_000004", emptyList(), ENGLISH, DEFAULT_BUSINESS_EXCEPTION_MESSAGE_TEMPLATE.formatted("TEST_000004", ENGLISH)),
                Arguments.of("TEST_000004", emptyList(), CHINESE, DEFAULT_BUSINESS_EXCEPTION_MESSAGE_TEMPLATE.formatted("TEST_000004", CHINESE)),
                Arguments.of("TEST_000004", emptyList(), JAPANESE, DEFAULT_BUSINESS_EXCEPTION_MESSAGE_TEMPLATE.formatted("TEST_000004", JAPANESE))
        );
    }

    @ParameterizedTest
    @MethodSource("paramsProviderForSystemExceptionHandlerTest")
    void should_return_error_info_when_controller_throw_a_system_exception(
            String errorCode,
            String description,
            String expectedMessage
    ) throws Exception {
        mockMvc
                .perform(post("/stub/throw-sys-error/{errorCode}", errorCode).queryParam("description", description))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errorCode").value(errorCode))
                .andExpect(jsonPath("$.errorMessage").value(expectedMessage))
        ;
    }

    private static Stream<Arguments> paramsProviderForSystemExceptionHandlerTest() {
        return Stream.of(
                Arguments.of("TEST_SYS_0000001", "A test system error description.", SYSTEM_EXCEPTION_MESSAGE),
                Arguments.of("TEST_SYS_0000002", "A test system error description.", SYSTEM_EXCEPTION_MESSAGE)
        );
    }
}
