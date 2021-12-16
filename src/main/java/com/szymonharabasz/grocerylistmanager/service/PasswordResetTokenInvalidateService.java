package com.szymonharabasz.grocerylistmanager.service;

import com.szymonharabasz.grocerylistmanager.domain.User;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import java.util.logging.Logger;

@Singleton
public class PasswordResetTokenInvalidateService {
    Logger logger = Logger.getLogger(PasswordResetTokenInvalidateService.class.getName());

    @Resource
    private TimerService timerService;

    @Inject
    private UserService userService;

    public void sendPasswordReset(@ObservesAsync UserTokenWrapper userTokenWrapper) {
        String passwordResetTokenHash = userTokenWrapper.getToken();
        timerService.createTimer(30 * 60 * 1000, userTokenWrapper);
    }

    @Timeout
    public void invalidatePasswordResetRequest(Timer timer) {
        if (timer.getInfo() instanceof UserTokenWrapper) {
            UserTokenWrapper userTokenWrapper = (UserTokenWrapper) timer.getInfo();
            User user = userTokenWrapper.getUser();
            user.setPasswordResetTokenHash(null);
            userService.save(user);
            logger.info("Timeout: invalidating password reset token for user " + userTokenWrapper.getUser().getName());
        }
    }
}
