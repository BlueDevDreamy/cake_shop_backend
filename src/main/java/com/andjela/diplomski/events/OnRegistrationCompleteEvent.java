package com.andjela.diplomski.events;

import com.andjela.diplomski.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private User user;

    public OnRegistrationCompleteEvent(
            User user, String appUrl) {
        super(user);

        this.user = user;
        this.appUrl = appUrl;
    }

    @Override
    public String toString() {
        return "OnRegistrationCompleteEvent{" +
                "user=" + user +
                '}';
    }
}
