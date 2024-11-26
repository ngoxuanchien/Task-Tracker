package nxc.hcmus.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import nxc.hcmus.model.entity.Config;
import nxc.hcmus.model.entity.Task;
import nxc.hcmus.repository.TaskRepository;
import nxc.hcmus.repository.TaskRepositoryImpl;
import nxc.hcmus.service.TaskService;
import nxc.hcmus.service.TaskServiceImpl;

import java.io.File;

public class ApplicationConfig {

    private static class SingletonHelper {
        private static final ApplicationConfig INSTANCE = new ApplicationConfig();
    }

    public static ApplicationConfig getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private ApplicationConfig() {

    }

    private String fileName = "./data/config.json";
    private final ObjectMapper objectMapper = ObjectMapperConfig.getObjectMapper();

    public void loadConfig() {
        // Load configuration from file
        File configFile = new File(fileName);
        createParentDirectory(configFile);
        Config config;
        try {
            if (configFile.createNewFile()) {
                config = defaultConfig();
            } else {
                config = objectMapper.readValue(configFile, Config.class);
            }

            // Set configuration
            Task.setAvailableId(config.getAvailableId());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Config defaultConfig() {
        return new Config(1L);
    }

    private void createParentDirectory(File file) {
        File parent = file.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }
    }

    public void saveConfig() {
        // Save configuration to file
        File configFile = new File(fileName);
        createParentDirectory(configFile);
        Config config = new Config(Task.getAvailableId());
        try {
            objectMapper.writeValue(configFile, config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TaskService getTaskService() {
        return TaskServiceImpl.getInstance();
    }

    public static TaskRepository getTaskRepository() {
        return TaskRepositoryImpl.getInstance();
    }

}
