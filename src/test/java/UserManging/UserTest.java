package UserManging;

import com.example.JavaTaskProject.UserManging.User;
import com.example.JavaTaskProject.UserManging.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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