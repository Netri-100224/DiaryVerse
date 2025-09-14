package net.engineeringdigest.journalApp.Service;


import net.engineeringdigest.journalApp.Entity.JournalEntity;
import net.engineeringdigest.journalApp.Entity.UserEntry;
import net.engineeringdigest.journalApp.Repository.JournalEntryRepo;
import net.engineeringdigest.journalApp.Repository.UserRepo;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Component
public abstract class UserService {

    @Autowired
        private UserRepo userRepo;
    private static final PasswordEncoder passencoder = new BCryptPasswordEncoder();


    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
        public boolean saveNewEntry (UserEntry userentry ){

            try {
                userentry.setPasswd(passencoder.encode(userentry.getPasswd()));
                userentry.setRoles(Arrays.asList("User"));
                userRepo.save(userentry);
                return true;
            } catch (Exception e) {
                logger.info("User cannot be entered");
                return false;
            }

        }

    public void saveEntry (UserEntry userentry ){
        userRepo.save(userentry);

    }
        public List<UserEntry> getAll(){
            return userRepo.findAll();
        }

   // public Optional<UserEntry> findById(ObjectId id){
     //   return userRepo.findById(id);
       // }
        //public void deleteById(ObjectId id){
          //  userRepo.deleteById(id);
        //}

        public UserEntry findByUsername(String username){
            return userRepo.findByUsername(username);
        }


    public abstract int sendMotivationalEmails();


//    @Autowired
//    private PasswordEncoder passwordEncoder;

//    public void hashAllPlainTextPasswords() {
//        List<UserEntry> users = userRepo.findAll();
//        for (UserEntry user : users) {
//            String passwd = user.getPasswd();
//            if (!passwd.startsWith("$2a$")) {  // Simple check: if not already BCrypt
//                user.setPasswd(passwordEncoder.encode(passwd));
//                userRepo.save(user);
//            }
//        }

    }


