package net.engineeringdigest.journalApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.journalApp.DTO.UserDTO;
import net.engineeringdigest.journalApp.Entity.UserEntry;
import net.engineeringdigest.journalApp.Service.UserDetailsServiceImpl;
import net.engineeringdigest.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/public")
@Tag(name = "Public APIs")
public class PublicController {
    @Autowired
    private UserService userSer;
  @Autowired
   UserDetailsServiceImpl userdeservimpl;

    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/healthcheck")
public String healthcheck(){
  return "OK" ;
}


    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account with email, username, and password. No authentication required."
    )
    @PostMapping("/signup")
    public ResponseEntity<String> create_entry(@RequestBody UserDTO user) {
        UserEntry newUser = UserEntry.builder()
                .username(user.getUserName())
                .email(user.getEmail())
                .passwd(user.getPassword())
                .motivationalQuotes(user.isMotivationalQuotes()) //This property is boolean
                .build();

        userSer.saveNewEntry(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
    }

    @Operation(
            summary = "User Login",
            description = "Authenticates the user using Basic Auth. Returns 200 OK if credentials are valid."
    )
    @GetMapping("/login")
    public ResponseEntity<String> login() {
        // If the user reaches here, it means Basic Auth succeeded.
        return ResponseEntity.ok("Login successful");
    }

    }



//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody UserEntry myentry){
//        //userdeservimpl.loadUserByUsername(myentry);
//    }

    ///SAME WAY RESPONSE ENTITY CAN BE CREATED FOR ALL REQUESTS to
    ///migrate all passwords from plain text to encrypted form.
//    @PostMapping("/migrate-passwords")
//    public ResponseEntity<String> migratePasswords() {
//        userSer.hashAllPlainTextPasswords();
//        return ResponseEntity.ok("Password migration completed.");
//    }




