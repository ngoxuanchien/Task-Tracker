package nxc.hcmus;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class TaskRepositoryImpl implements TaskRepository {

    private static final List<Task> taskList = new ArrayList<>();

    private final ObjectMapper objectMapper = ObjectMapperConfig.objectMapper();

    private final String fileName = "./data/data.json";

    Logger log = Logger.getLogger(TaskRepositoryImpl.class.getName());

    private void loadData() {
        TypeReference<List<Task>> typeReference = new TypeReference<>() {};
        File dataFile = new File(fileName);
        createParentDirectory(dataFile);
        try {
            if (dataFile.length() == 0) {
                log.info("Data file is empty");
            } else if (dataFile.createNewFile()) {
                log.info("Data file not found, create new file");
            } else {
                taskList.addAll(objectMapper.readValue(dataFile, typeReference));
            }
            log.info("Data loaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createParentDirectory(File file) {
        File parent = file.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }
    }

    private void saveData() {
        File dataFile = new File(fileName);
        try {
            objectMapper.writeValue(dataFile, taskList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Task save(Task task) {
        try {
            loadData();
            return Optional.ofNullable(task)
                    .map(t -> {
                        taskList.add(t);
                        return t;
                    })
                    .orElse(null);
        } finally {
            saveData();
        }
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskList.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }
}
