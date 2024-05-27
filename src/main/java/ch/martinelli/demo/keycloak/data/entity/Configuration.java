package ch.martinelli.demo.keycloak.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

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

}
