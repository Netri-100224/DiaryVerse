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

    /**
     * This method runs every sunday at 8:00 AM server time.
     * Use cron format: second, minute, hour, day of month, month, day(s) of week
     */
    @Scheduled(cron = "0 0 8 * * SUN")
    public void sendDailyQuoteEmail() {
        String testEmail = "fe8345537ebfa6";
        log.info("⏰ Triggering weekly quote email to {}", testEmail);

        emailService.sendWeeklyMotivation(testEmail);
    }
}
