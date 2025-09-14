package net.engineeringdigest.journalApp.Service;

import net.engineeringdigest.journalApp.Entity.JournalEntity;
import net.engineeringdigest.journalApp.Entity.UserEntry;
import net.engineeringdigest.journalApp.Repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo jeRepo;

    @Autowired
    private UserService userservice;


 //@Transactional
    public void saveEntry (JournalEntity journalentity, String username){
      try {
          UserEntry user = userservice.findByUsername(String.valueOf(username));
          journalentity.setDate(LocalDateTime.now());// Journal entry is saved.
          JournalEntity saved = jeRepo.save(journalentity); //The saved entry is taken in local variable saved.
          user.getJe().add(saved); // the saved entry is added in journal entries list in user.
          userservice.saveEntry(user);//saved in user as 'add' operation is in-memory not persistent.
      }
      catch (Exception e)
      {
          throw new RuntimeException("An error occurred while saving the entry.", e);
      }
    }

    public void saveEntry (JournalEntity journalentity) {

        jeRepo.save(journalentity);
    }


    public List<JournalEntity> getAll(){
       return jeRepo.findAll();
    }

    public Optional<JournalEntity> findById(ObjectId id){
        return jeRepo.findById(id);
    }


    public boolean deleteById(ObjectId id,String username) {
     boolean removed = false;
     try{
     UserEntry user = userservice.findByUsername(String.valueOf(username));
        removed = user.getJe().removeIf(x -> x.getID().equals(id));//deleted from userRepo
        if (removed) {
            userservice.saveEntry(user); // saved changes in user service to maintain data consistency and sync.
            jeRepo.deleteById(id);  //deleted from journal repo
        } }
     catch(Exception e){
         System.out.println(e);
         throw new RuntimeException("error occured while deleting the entry.");
     }
    return removed;}

//    public List <JournalEntity> findByUsername(String userName)
//    {
//
//    }
}
