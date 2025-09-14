package net.engineeringdigest.journalApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.journalApp.DTO.JournalDTO;
import net.engineeringdigest.journalApp.Entity.JournalEntity;
import net.engineeringdigest.journalApp.Entity.UserEntry;
import net.engineeringdigest.journalApp.Service.JournalEntryService;
import net.engineeringdigest.journalApp.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/Journal")
@Tag(name= "Journal APIs")
public class JournalController_For_Users {

    @Autowired
    private JournalEntryService journalEntrySer;

    @Autowired
    private UserService userservice;


    @Operation(
            summary = "Get all journal entries of authenticated user",
            description = "Retrieves all journal entries belonging to the currently authenticated user."
    )
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllJournalEntriesOfUsers(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserEntry user = userservice.findByUsername(username);
        List<JournalEntity> all = user.getJe();
        if(all!=null && !all.isEmpty())
        {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(all, HttpStatus.NOT_FOUND);
    }


    @Operation(
            summary = "Create a new journal entry for authenticated user",
            description = "Creates a new journal entry linked to the currently authenticated user."
    )
    @PostMapping("/add")
    public ResponseEntity<JournalEntity> create_entry(@RequestBody JournalEntity myentry )
    {
  try {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      String username = auth.getName();
      journalEntrySer.saveEntry(myentry, username);
      return new ResponseEntity<>(myentry, HttpStatus.CREATED);
  }
  catch (Exception e){
      return new ResponseEntity<>(myentry, HttpStatus.BAD_REQUEST);
    }

    }

    @Operation(
            summary = "Get a specific journal entry by ID for authenticated user",
            description = "Fetches a journal entry by its ID only if it belongs to the currently authenticated user."
    )
    @GetMapping("/ID/{myID}")
    public ResponseEntity<?> getEntryById(@PathVariable ObjectId myID)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserEntry user = userservice.findByUsername(username);
//       List<JournalEntity> collect = user.getJe().stream().filter(x->x.getID().equals(myID)).collect(Collectors.toList());
//        if(!collect.isEmpty()) {
//            Optional<JournalEntity> je = journalEntrySer.findById(myID);
//            if (je.isPresent()) {
//                return new ResponseEntity<>(je.get(), HttpStatus.OK);
//            }
       // }


        if (user == null || user.getJe() == null || user.getJe().isEmpty()) {
        return new ResponseEntity<>("No journals found", HttpStatus.NOT_FOUND);
    }

        List<JournalDTO> result = user.getJe().stream()
                .map(JournalDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
       // return new ResponseEntity<>( HttpStatus.NOT_FOUND);// FOR STATUS CODES,SUPPOSE CLIENT IS REQUESTING SOMETHING THAT DOES NOT EXISTS. THEN TO HANDLE THAT.
    }                                                                //SAME WAY RESPONSE ENTITY CAN BE CREATED FOR ALL REQUESTS.


    //WHEN i USED BELOW DELETE MAPPING FUNCTION THE JOURNAL ENTRY WAS DELETED FROM db BUT ITS
    //REFERENCE WAS STILL AVAILABLE IN USERS TABLE . SO TO ondelete cascade IN MONGODB, USE BELOW FUNCTION.

    @Operation(
            summary = "Delete a journal entry by ID for authenticated user",
            description = "Deletes the specified journal entry if it belongs to the currently authenticated user."
    )
    @DeleteMapping("ID/{myID}")
    public ResponseEntity<?> deleteMappingByID(@PathVariable ObjectId myID)
    {  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        boolean removed = journalEntrySer.deleteById(myID,username);
        if (removed)
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); //ENTRY SUCCESSFULLY DELETED
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //ENTRY NOT FOUND THUS COULD NOT BE DELETED
    }


//        @DeleteMapping("deleteUserByID/{username}/{myID}")
//    public ResponseEntity<?> deleteMappingByID(@PathVariable ObjectId myID,@PathVariable String username)
//    {
//        journalEntrySer.deleteById(myID,username);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }


//    @PutMapping ("id/{myID}")
//    public JournalEntity putMappingByID(@PathVariable ObjectId myID,@RequestBody JournalEntity newentry )
//    {   JournalEntity preventry = journalEntrySer.findById(myID).orElse(null);
//
//        if(preventry!= null){
//            preventry.setTitle(newentry.getTitle()!= null && !newentry.getTitle().equals("")? newentry.getTitle() : preventry.getTitle());
//            preventry.setContent(newentry.getContent()!= null && !newentry.getContent().equals("")? newentry.getContent() : preventry.getContent());
//        }
//        preventry.setDate(LocalDateTime.now());
//        journalEntrySer.saveEntry(preventry, user);
//        return preventry;
//    }

    @Operation(
            summary = "Update a journal entry by ID for authenticated user",
            description = "Updates the specified journal entry fields (title, content) if it belongs to the currently authenticated user."
    )
    @PutMapping ("ID/{myID}")
    public ResponseEntity<?> putMappingByID(@PathVariable ObjectId myID,@RequestBody JournalEntity newentry )
    {   Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserEntry user = userservice.findByUsername(username);
        List<JournalEntity> collect = user.getJe().stream().filter(x -> x.getID().equals(myID)).collect(Collectors.toList());
        if (!collect.isEmpty()) {

            Optional<JournalEntity> je = journalEntrySer.findById(myID);
            if (je.isPresent()) {
                JournalEntity preventry = journalEntrySer.findById(myID).orElse(null);
                preventry.setTitle(newentry.getTitle() != null && !newentry.getTitle().equals("") ? newentry.getTitle() : preventry.getTitle());
                preventry.setContent(newentry.getContent() != null && !newentry.getContent().equals("") ? newentry.getContent() : preventry.getContent());
                preventry.setDate(LocalDateTime.now());
                journalEntrySer.saveEntry(preventry);
                return new ResponseEntity<>(je.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


        }

    }




