package ch.martinelli.demo.keycloak.data.repository;

import ch.martinelli.demo.keycloak.data.entity.Configuration;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ConfigurationRepository extends JpaRepository<Configuration, UUID> {

    @Query("Select c from Configuration c where c.userName='Michi'")
    List<Configuration> findByName();

    @Modifying
    @Transactional
    @Query("UPDATE Configuration c SET c.name = :name, c.userName = :userName, c.db_Url = :dbUrl WHERE c.id = :id")
    void updateWithoutPassword(UUID id, String name, String userName, String dbUrl);

    @Modifying
    @Transactional
    @Query("UPDATE Configuration c SET c.name = :name, c.userName = :userName, c.password = :password, c.db_Url = :dbUrl WHERE c.id = :id")
    void updateWithPassword(UUID id, String name, String userName, String password, String dbUrl);
}
