package ch.martinelli.demo.keycloak.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@Entity
@Table(name = "Configuration")
public class Configuration extends AbstractEntity{

    @NotEmpty
    @Column(name = "NAME")
    private String name="";
    @NotEmpty
    @Column(name = "USER_NAME")
    private String userName="";
    @NotEmpty
    private String password="";
    @NotEmpty
    private String db_Url="";
    @PrePersist
    @PreUpdate
    private void encryptPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, this.password)) {
            this.password = encoder.encode(this.password);
        }
    }
}
