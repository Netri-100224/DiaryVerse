package net.engineeringdigest.journalApp.Tests;

import net.engineeringdigest.journalApp.Entity.UserEntry;
import net.engineeringdigest.journalApp.Repository.UserRepo;
import net.engineeringdigest.journalApp.Service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class userservice_testing {
    @InjectMocks
    private UserDetailsServiceImpl udsi;

    @Mock
    private UserRepo userepo;

    @Test
    public void testFindByUserName(){
        when(userepo.findByUsername(ArgumentMatchers.anyString())).thenReturn(UserEntry.builder()
                .username("{Username}")
                .passwd("{password}")
                .roles(new ArrayList<>())
                .build());
        UserDetails user = udsi.loadUserByUsername("{username}");
        Assertions.assertNotNull(user);
        assertNotNull(userepo.findByUsername("{username}"));


    }
}
