package net.engineeringdigest.journalApp.Tests;

import net.engineeringdigest.journalApp.Service.EmailService;
import net.engineeringdigest.journalApp.Service.QuoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {
    @Autowired
    private EmailService emser;

    @Autowired
    private QuoteService qser;

    @Test
    void testSendMail(){

        String quote = "Hi, today's quote: " + qser.getRandomQuote() +"/n"+" Stay motivated, Stay Journaling !!";  // get the quote
        emser.sendEmail("{Username}", "Testing mail to give quotes to users", quote);

    }

}
