package net.engineeringdigest.journalApp.Service;

import net.engineeringdigest.journalApp.Entity.UserEntry;
import net.engineeringdigest.journalApp.Repository.UserRepo;
import net.engineeringdigest.journalApp.Repository.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserDetailsServiceImpl extends UserService implements UserDetailsService  {

    @Autowired
    private UserRepo userrepo;
    @Autowired
    private UserRepoImpl userRepoImpl;

    @Autowired
    private EmailService emailService;

    @Autowired
    QuoteService quoteService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        UserEntry user = userrepo.findByUsername(username);
            if (user != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPasswd())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();

        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }

    @Override
    public int sendMotivationalEmails() {
        List<UserEntry> userEntry = userRepoImpl.getUserForSA();
        quoteService.refreshQuote();
        String quote = quoteService.getRandomQuote();
        int count = 0;

        for (UserEntry user : userEntry) {
            if (user.getEmail() != null && !user.getEmail().isEmpty() ){
                emailService.sendWeeklyMotivation(user.getEmail());
                count++;}

            }
       return count; }
    }