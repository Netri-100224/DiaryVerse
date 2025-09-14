package net.engineeringdigest.journalApp.Scheduler;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



import net.engineeringdigest.journalApp.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmailScheduler {

    @Autowired
    private EmailService emailService;

    @Value("${email.scheduler.enabled:false}")
    private boolean isEnabled;

    @Scheduled(cron = "0 0 8 * * SUN")
    public void sendWeeklyQuoteEmail() {
        if (!isEnabled) {
            log.info("📭 Email scheduler is disabled.");
            return;
        }

        String testEmail = "Username";
        log.info("⏰ Triggering weekly quote email to {}", testEmail);

        emailService.sendWeeklyMotivation(testEmail);
    }
}
