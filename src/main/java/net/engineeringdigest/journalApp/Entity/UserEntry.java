package net.engineeringdigest.journalApp.Entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document (collection = "users")
@Data
@AllArgsConstructor
@Builder
public class UserEntry {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String username;
    private String email;
    private boolean motivationalQuotes;
    @NonNull
    private String passwd;
 @DBRef  // we are referencing journal entries from users because these are 2 different collections of database
 // so we can get object id of journal entries. This is working as Foreign key.
    private List<JournalEntity> je =new ArrayList<>();
 private List<String> roles;



}
