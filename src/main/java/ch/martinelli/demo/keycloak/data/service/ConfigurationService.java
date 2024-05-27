package ch.martinelli.demo.keycloak.data.service;

import ch.martinelli.demo.keycloak.data.repository.ConfigurationRepository;
import ch.martinelli.demo.keycloak.data.entity.Configuration;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConfigurationService {

    private ConfigurationRepository configurationRepository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public ConfigurationService(ConfigurationRepository configurationRepository) {

        this.configurationRepository = configurationRepository;
    };

   public List<Configuration> findAllConfigurations(){
            return configurationRepository.findAll();
        };

 //   public List<Configuration> findMessageConfigurations(){
 //       return configurationRepository.findByName();
 //   };

   public List<Configuration> findMessageConfigurations(){
        return configurationRepository.findAll();
    };


    public void saveConfigurationOld(Configuration config) {

        if (config == null) {
            System.err.println("Configuration is null!");
            return;
        }
        configurationRepository.save(config);
    }

    @Transactional
    public void saveConfiguration(Configuration config) {
        if (config == null) {
            System.err.println("Configuration is null!");
            return;
        }
      //  String plainTextPassword = config.getPassword();

        Optional<Configuration> existingConfigOptional = configurationRepository.findById(config.getId());
        if (existingConfigOptional.isPresent()) {
            Configuration existingConfig = existingConfigOptional.get();
            System.out.println("password existing = "+existingConfig.getPassword());
            boolean passwordChanged = !config.getPassword().equals(existingConfig.getPassword());
            System.out.println("password new updated = "+config.getPassword());
            System.out.println("password changed = "+passwordChanged);

            if (passwordChanged) {
                System.out.println("password changed!!!!");
                String encodedPassword = encoder.encode(config.getPassword());
                configurationRepository.updateWithPassword(
                        config.getId(),
                        config.getName(),
                        config.getUserName(),
                        encodedPassword,
                        config.getDb_Url()
                );
            } else {
                System.out.println("password not changed!!!!");
                configurationRepository.updateWithoutPassword(
                        config.getId(),
                        config.getName(),
                        config.getUserName(),
                        config.getDb_Url()
                );
            }
        } else {
            config.setPassword(encoder.encode(config.getPassword()));
            configurationRepository.save(config);
        }
    }
}
