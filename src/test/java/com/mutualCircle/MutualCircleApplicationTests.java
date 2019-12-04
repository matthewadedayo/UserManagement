package com.mutualCircle;

import com.mutualCircle.exception.UserNotFoundException;
import com.mutualCircle.model.Users;
import com.mutualCircle.repository.UsersRepository;
import com.mutualCircle.service.UsersService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/*@RunWith(SpringRunner.class)
@SpringBootTest
public class MutualCircleApplicationTests {

    @Autowired
     UsersService userService;
            
    @MockBean
    private UsersRepository userRep;
    
     //testing Persist method @service level 
    @Test
    public void TestAddUser() throws Exception{
        Users user = new Users();
        user.setUsersId(1);
        user.setUsername("Adedayo");
        user.setEmailAddress("dayo@mail.com");
        user.setPassword("dayo@4anu");
        user.setFirstName("Sheyi");
        user.setLastName("Anifannu");
        user.setLocation("Location");
        user.setGender("male");
        user.setPhoneNumber("08030670577");
        user.setActive(true);
        user.setActivationCode("activationCode");
        user.setDateCreated();
        Mockito.when(userRep.save(user)).thenReturn(user);
        assertThat(userService.create(user)).isEqualTo(user);

    }
    //testing Returning Single Event method @service level
    @Test
    public void getUserById() throws Exception{
        User user = new User();
        user.setId(1);
        user.setUsername("Adedayo");
        user.setEmail("dayo@mail.com");
        user.setPassword("dayo@4anu");
        user.setFirstname("Sheyi");
        user.setLastname("Anifannu");
        user.setLocation("Location");
        user.setGender("male");
        user.setPhone("08030670577");
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        Mockito.when(userRep.findById(1)).thenReturn(Optional.of(user));
        assertThat(userService.getUser(1)).isEqualTo(user);
    }
    
    //testing Returning All Event method @service level
    @Test
    public void getAllUser() throws UserNotFoundException, InterruptedException{
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("Adedayo");
        user1.setEmail("dayo@mail.com");
        user1.setPassword("dayo@4anu");
        user1.setFirstname("Sheyi");
        user1.setLastname("Anifannu");
        user1.setLocation("Location");
        user1.setGender("male");
        user1.setPhone("08030670577");
        user1.setEnabled(true);
        user1.setAccountNonExpired(true);
        user1.setCredentialsNonExpired(true);
        user1.setAccountNonLocked(true);
        
        User user2 = new User();
        user2.setId(2);
        user2.setUsername("Adedayo");
        user2.setEmail("dayo@mail.com");
        user2.setPassword("dayo@4anu");
        user2.setFirstname("Sheyi");
        user2.setLastname("Anifannu");
        user2.setLocation("Location");
        user2.setGender("male");
        user2.setPhone("08030670577");
        user2.setEnabled(true);
        user2.setAccountNonExpired(true);
        user2.setCredentialsNonExpired(true);
        user2.setAccountNonLocked(true);
        
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        
        Mockito.when(userRep.findAll()).thenReturn(userList);
        assertThat(userService.getAllUser()).isEqualTo(userList);
    }
    
    
    @Test
    public void updateEvent() throws Exception{
        User user = new User();
        user.setId(1);
        user.setUsername("Adedayo");
        user.setEmail("dayo@mail.com");
        user.setPassword("dayo@4anu");
        user.setFirstname("Sheyi");
        user.setLastname("Anifannu");
        user.setLocation("Location");
        user.setGender("male");
        user.setPhone("08030670577");
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        Mockito.when(userRep.findById(1)).thenReturn(Optional.of(user));
        
        user.setFirstname("Adedayo Anifannu");
        Mockito.when(userRep.save(user)).thenReturn(user);
        
        assertThat(userService.updateUser(1, user)).isEqualTo(user);
    }
    //testing delete method @service level
     /*public void deleteEvent() {
        Event eve = new Event();
        eve.setEventId(1);
        eve.setEventName("Lakers");
        eve.setEventType("Java Jazz");
        eve.setEventTag("yes");
        eve.setVenue("ile wa");
        eve.setDressCode("light red");
        eve.setAgeLimit("40");
        
        Mockito.when(eventRep.findById(1)).thenReturn(Optional.of(eve));
        Mockito.when(eventRep.exists(eve.getEventId())).thenReturn(false);
        assertThat(eventRep.exists(eve.getEventId()));
}*/

	


