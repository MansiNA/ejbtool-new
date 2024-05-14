package ch.martinelli.demo.keycloak.data.service;

import ch.martinelli.demo.keycloak.data.entity.SqlDefinition;
import ch.martinelli.demo.keycloak.data.repository.SqlDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SqlDefinitionService {

    private final SqlDefinitionRepository sqlDefinitionRepository;
    private List<SqlDefinition> sqlDefinitionList;

    @Autowired
    public SqlDefinitionService(SqlDefinitionRepository sqlDefinitionRepository) {
        this.sqlDefinitionRepository = sqlDefinitionRepository;
        this.sqlDefinitionList = sqlDefinitionRepository.findAll();
    }

    public List<SqlDefinition> getAllSqlDefinitions() {
        sqlDefinitionList = sqlDefinitionRepository.findAll();
        return sqlDefinitionList;
    }

    public SqlDefinition getSqlDefinitionById(Long id) {
        return sqlDefinitionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SqlDefinition not found with id: " + id));
    }

    public SqlDefinition saveSqlDefinition(SqlDefinition sqlDefinition) {
        return sqlDefinitionRepository.save(sqlDefinition);
    }

    // Add more service methods as needed (e.g., saveSqlDefinition, deleteSqlDefinition, etc.)
    public List<SqlDefinition> getRootProjects() {
        List<SqlDefinition> rootProjects = sqlDefinitionList
                .stream()
                .filter(sqlDef -> sqlDef.getPid() == null)
                .collect(Collectors.toList());

        // Log the names of root projects
        rootProjects.forEach(project -> System.out.println("Root Project: " + project.getName()));

        return rootProjects;
    }

    public List<SqlDefinition> getChildProjects(SqlDefinition parent) {

        List<SqlDefinition> childProjects = sqlDefinitionList
                .stream()
                .filter(sqlDef -> Objects.equals(sqlDef.getPid(), parent.getId()))
                .collect(Collectors.toList());

        // Log the names of child projects
        childProjects.forEach(project -> System.out.println("Child Project of " + parent.getName() + ": " + project.getName()));

        return childProjects;
    }

}
