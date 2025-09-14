package net.engineeringdigest.journalApp.Repository;

import net.engineeringdigest.journalApp.Entity.UserEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<UserEntry,ObjectId> {


    UserEntry findByUsername(String username);

    void deleteByUsername(String username);
}
