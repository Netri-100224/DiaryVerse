package net.engineeringdigest.journalApp.Repository;

import net.engineeringdigest.journalApp.Entity.UserEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepoImpl {  //TO FIND ALL USERS WHO HAVE ENABLED MOTIVATIONAL QUOTES OPTION AND WHOSE EMAILS ARE VALID

@Autowired
   private MongoTemplate mongoTemplate;
public List<UserEntry> getUserForSA(){

    Query query = new Query();
    //query.addCriteria(Criteria.where("email").exists(true));
   // query.addCriteria(Criteria.where("sentimentAnalysis").exists(true));
    //Criteria criteria = new Criteria();

    query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$"));
    query.addCriteria(Criteria.where("motivationalQuotes").is(true));
    List<UserEntry> users = mongoTemplate.find(query, UserEntry.class);

    return users;
    }
}
