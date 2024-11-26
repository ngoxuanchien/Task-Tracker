package nxc.hcmus.service;

import nxc.hcmus.config.ApplicationConfig;
import nxc.hcmus.exception.TaskNotFoundException;
import nxc.hcmus.model.entity.Task;
import nxc.hcmus.model.enums.TaskStatus;
import nxc.hcmus.repository.TaskRepository;
import nxc.hcmus.repository.TaskRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository = ApplicationConfig.getTaskRepository();

    private static class SingletonHelper {
        private static final TaskServiceImpl INSTANCE = new TaskServiceImpl();
    }

    public static TaskServiceImpl getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @Override
    public Task getById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }

    @Override
    public Task createTask(String description) {
        return Optional.ofNullable(description)
                .map(Task::new)
                .map(taskRepository::save)
                .orElse(null);
    }

    @Override
    public void updateTask(Long taskId, String description) {
        var task = getById(taskId);
        task.setDescription(description);
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.findById(taskId).ifPresent(taskRepository::delete);

    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getAllTask(TaskStatus status) {
        return taskRepository.findAllByStatus(status);
    }

    @Override
    public void mark(Long taskId, TaskStatus status) {
        var task = getById(taskId);
        task.setStatus(status);
        taskRepository.save(task);
    }
}
