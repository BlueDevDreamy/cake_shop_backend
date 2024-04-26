package com.andjela.diplomski.listener;

import com.andjela.diplomski.events.OnRegistrationCompleteEvent;
import com.andjela.diplomski.service.UserRegistrationService;
import com.andjela.diplomski.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private UserRegistrationService userRegistrationService;

    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
        log.info(event.toString());
//        userRegistrationService.confirmRegistration(event);
    }
}
