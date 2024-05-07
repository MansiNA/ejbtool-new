package ch.martinelli.demo.keycloak.data.service;

import ch.martinelli.demo.keycloak.data.entity.QSql;
import ch.martinelli.demo.keycloak.data.entity.SqlDefinition;
import ch.martinelli.demo.keycloak.data.repository.SqlDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return sqlDefinitionRepository.findAll();
    }

    public SqlDefinition getSqlDefinitionById(Long id) {
        return sqlDefinitionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SqlDefinition not found with id: " + id));
    }

    // Add more service methods as needed (e.g., saveSqlDefinition, deleteSqlDefinition, etc.)
    public List<SqlDefinition> getRootProjects() {
        return sqlDefinitionList
                .stream()
                .filter(sqlDef -> sqlDef.getPid() == null)
              //  .filter(projects -> hasAccess(user.getRoles(), projects.getRole_access()))
                .collect(Collectors.toList());
    }

    public List<SqlDefinition> getChildProjects(SqlDefinition parent) {
        System.out.println(parent.getName()+"--------------------------yesssssssssss");
        return sqlDefinitionList
                .stream()
                .filter(sqlDef -> Objects.equals(sqlDef.getPid(), parent.getId()))
               // .filter(projects -> hasAccess(user.getRoles(), projects.getRole_access()))
                .collect(Collectors.toList());
    }
}
