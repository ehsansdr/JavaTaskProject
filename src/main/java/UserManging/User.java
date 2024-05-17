package UserManging;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Users")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User implements UserDetails {  // each time you want to work with spring security you need to
                                            // ensure UserDetails interface in order to make security easy

    @Id
    @GeneratedValue
    private Integer id;

    private String firstName;
    private String lastName;

    /* because of using UserDetails interface, this is essential to name the field in right format
       because UserDetails can find the email and password variable na dwe do not need to override the
       spatial method, UserDetails will find the email and password related field and no need to have extra code
    */
    @UniqueElements(message = "Email must be unique")
    @Email
    private String email;

     /**
      *  if you change the name format you will get an error and need to declare the
      *  getPassword() manually */
    private String password;

    // because it is enum we need this
    @Enumerated(EnumType.STRING)
    private Role role;      //because of Authority, i need to get user and admin role to know how limit they should have

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;  // default : return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // default is false we change it to true
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // default is false we change it to true
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // default is false we change it to true
    }

    @Override
    public boolean isEnabled() {
        return true;   // default is false we change it to true
    }
}
