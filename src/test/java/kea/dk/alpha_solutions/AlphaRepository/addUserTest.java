package kea.dk.alpha_solutions.AlphaRepository;

import kea.dk.alpha_solutions.alphaRepository.AlphaRepositoryUser;
import kea.dk.alpha_solutions.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class addUserTest
{
    @Autowired
    private AlphaRepositoryUser alphaRepositoryUser;

    @BeforeAll
    void setUp()
    {
        AlphaRepositoryUser alphaRepositoryUser1= new AlphaRepositoryUser();
        System.out.println("Before all");
    }
    @BeforeEach
    void setUpEach()
    {
        //Arrange
        User user = new User(1007, "test7@test.com", "test", 100, "testName", false);
        //act
        alphaRepositoryUser.addUser(user);
        System.out.println("BeforeEach runs before each test is done");
    }
    @Test
    void addUser()
    {
        //Arrange
        System.out.println("Test runs");
        //Act
        User user = alphaRepositoryUser.getUserByEmail("test7@test.com");
        //Assert
        assertEquals(1007,user.getUserId());
    }
}
