package ch.martinelli.demo.keycloak.data.repository;

import ch.martinelli.demo.keycloak.data.entity.SqlDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SqlDefinitionRepository extends JpaRepository<SqlDefinition, Long> {
}
