package app.meeka.domain.event;

import org.springframework.context.ApplicationEvent;

public class PostPublishedEvent extends ApplicationEvent {

    public PostPublishedEvent(Object source) {
        super(source);
    }
}
