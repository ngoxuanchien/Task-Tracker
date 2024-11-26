package nxc.hcmus.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nxc.hcmus.config.ObjectMapperConfig;
import nxc.hcmus.model.entity.Task;
import nxc.hcmus.model.enums.TaskStatus;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class TaskRepositoryImpl implements TaskRepository {

    private static class SingletonHelper {
        private static final TaskRepository INSTANCE = new TaskRepositoryImpl();
    }

    public static TaskRepository getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private static final List<Task> taskList = new ArrayList<>();

    private final ObjectMapper objectMapper = ObjectMapperConfig.getObjectMapper();

    private final String fileName = "./data/data.json";

    Logger log = Logger.getLogger(TaskRepositoryImpl.class.getName());

    public TaskRepositoryImpl() {
        loadData();

    }

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
            log.info("Data saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Task save(Task task) {
        try {
            var temp = taskList.stream()
                    .filter(t -> t.getId().equals(task.getId()))
                    .findFirst();
            if (temp.isPresent()) {
                temp.get().setDescription(task.getDescription());
                temp.get().setStatus(task.getStatus());
                temp.get().setUpdatedAt(LocalDateTime.now());
            } else {
                task.setCreatedAt(LocalDateTime.now());
                taskList.add(task);
            }
            return task;
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

    @Override
    public void delete(Task task) {
        try {
            taskList.remove(task);
        } finally {
            saveData();
        }
    }

    @Override
    public List<Task> findAll() {
        return taskList;
    }

    @Override
    public List<Task> findAllByStatus(TaskStatus status) {
        return taskList.stream()
                .filter(task -> task.getStatus().equals(status))
                .toList();
    }
}
