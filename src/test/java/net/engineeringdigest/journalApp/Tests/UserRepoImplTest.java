package net.engineeringdigest.journalApp.Tests;

import com.mongodb.assertions.Assertions;
import com.mongodb.internal.VisibleForTesting;
import net.engineeringdigest.journalApp.Entity.UserEntry;
import net.engineeringdigest.journalApp.Repository.UserRepoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserRepoImplTest {

    @Autowired
    UserRepoImpl  userRepo1;
    @Test
    public void testSaveNewUser(){

        System.out.println(userRepo1.getUserForSA());
        Assertions.assertNotNull(userRepo1.getUserForSA());
    }

}

