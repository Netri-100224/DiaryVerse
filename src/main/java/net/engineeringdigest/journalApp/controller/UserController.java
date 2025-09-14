package net.engineeringdigest.journalApp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.journalApp.Entity.JournalEntity;
import net.engineeringdigest.journalApp.Entity.UserEntry;
import net.engineeringdigest.journalApp.ExternalAPI.WeatherResponse;
import net.engineeringdigest.journalApp.Repository.UserRepo;
import net.engineeringdigest.journalApp.Service.JournalEntryService;
import net.engineeringdigest.journalApp.Service.UserService;
import net.engineeringdigest.journalApp.Service.WeatherService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
@Tag(name = "User APIs")
public class UserController {
    @Autowired
    private WeatherService weser;

    @Autowired
    private UserService userSer;


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
//
//    @GetMapping
//    List<UserEntry> getAll() {
//        return userSer.getAll();
//    }


//        @PutMapping ("/{username}")
//        public ResponseEntity<?> putMappingByID(@RequestBody UserEntry userentry,@PathVariable String username )
//        {
//            UserEntry userInDb = userSer.findByUsername(username);
//            if(userInDb != null){
//                userInDb.setUsername(userentry.getUsername()); //we are taking username and
//                                                               // password from request and saving the user entry in our DB.
//                userInDb.setPasswd(userentry.getPasswd());
//                userSer.saveEntry(userInDb);
//            }
//
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//
//        }

    @PutMapping("/update")
    public ResponseEntity<?> putMappingByID(@RequestBody UserEntry userentry) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserEntry userInDb = userSer.findByUsername(username);
        if (userInDb != null) {
            userInDb.setUsername(userentry.getUsername()); //we are taking username and
            // password from request and saving the user entry in our DB.
            if (userentry.getPasswd() != null && !userentry.getPasswd().isEmpty()) {
                userInDb.setPasswd(passwordEncoder.encode(userentry.getPasswd())); // Encrypt password here
            }
            userInDb.setEmail(userentry.getEmail());
            userInDb.setMotivationalQuotes(userentry.isMotivationalQuotes());

            userSer.saveEntry(userInDb);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);


    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserById() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userRepo.deleteByUsername(auth.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/home")
    public ResponseEntity<?> greeting() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weres = weser.getweather("Delhi");
        String greeting = "";
        if (weres != null) {
            greeting = ", Weather feels like " + weres.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi " + auth.getName() + greeting, HttpStatus.OK);

    }
}



