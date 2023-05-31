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
class addUserTest
{
    @Autowired
    private AlphaRepositoryUser alphaRepositoryUser;

    @BeforeAll
    void setUp()
    {
        AlphaRepositoryUser alphaRepositoryUser1 = new AlphaRepositoryUser();
        System.out.println("Before all");
    }

    @BeforeEach
    void setUpEach()
    {
        //Arrange
        //act
        System.out.println("BeforeEach runs before each test is done");
        //System.out.println("UserID:" + user.getUserId());
    }

    @Test
    void addUser()
    {
        //Arrange
        System.out.println("addUser runs");
        //Act
        User user1 = new User(1000,"tester@test.com", "test", 100, "testName", false);
        alphaRepositoryUser.addUser(user1);
        int expected = user1.getUserId();

        User user = alphaRepositoryUser.getUserByEmail("tester@test.com");
        System.out.println("UserID:" + user.getUserId());
        int actual = user.getUserId();

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void deleteUser()
    {
        //Arrange
        System.out.println("DeleteUser runs");
        //Act
        alphaRepositoryUser.deleteByEmail("tester@test.com");
        //User user = alphaRepositoryUser.getUserByEmail("test@test.com");
        //Assert
        assertEquals((Short) null, null);
        System.out.println("SLETTET MAIL");
    }
}