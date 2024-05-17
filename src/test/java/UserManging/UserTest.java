package UserManging;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void saving_user(){
        User user = User.builder()
                .firstName("ehsan")
                .lastName("sdr")
                .email("sehsan251@gmail.com")
                .password("123456789")
                .build();

        userRepository.save(user);
    }
}