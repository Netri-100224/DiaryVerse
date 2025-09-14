package net.engineeringdigest.journalApp.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private QuoteService quoteService;


    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);
            javaMailSender.send(mail);
        } catch (Exception e) {
            log.error("Exception while sendEmail ", e);
        }
    }
    public String sendWeeklyMotivation(String to) {
        String quote = quoteService.getRandomQuote();
        String subject = "🌟 Your Daily Motivation";
        String body = "Hello,\n\nHere’s your motivational quote for today:\n\n" + quote + "\n\nKeep journaling! 📝";

        sendEmail(to, subject, body);
    return body;}
}
