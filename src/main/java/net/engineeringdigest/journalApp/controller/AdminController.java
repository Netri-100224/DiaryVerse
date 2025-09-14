package net.engineeringdigest.journalApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.journalApp.Entity.UserEntry;
import net.engineeringdigest.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin APIs")
public class AdminController {

@Autowired
private UserService userService;

@GetMapping("/users")
public ResponseEntity<?> getAllUsers(){
    List<UserEntry> allusers = userService.getAll();
    if(allusers!= null && !allusers.isEmpty())
    {
        return new ResponseEntity<>(allusers, HttpStatus.OK);
    }
    return new ResponseEntity<>(allusers,HttpStatus.NOT_FOUND);
}

    @Operation(
            summary = "Send motivational quotes to users",
            description = "Admin-only endpoint to send motivational quote emails to users who opted in."
    )
    @PostMapping("/send-motivational-emails")
    public ResponseEntity<String> sendMotivationalEmailsManually() {

        int sentCount = userService.sendMotivationalEmails();

        if (sentCount > 0) {
            return ResponseEntity.ok(" Motivational emails sent to " + sentCount + " users.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(" No users found with valid email and motivationalQuotes = true.");
        }
    }
}



