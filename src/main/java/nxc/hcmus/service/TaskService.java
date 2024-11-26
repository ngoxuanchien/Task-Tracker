package nxc.hcmus.service;

import nxc.hcmus.model.entity.Task;
import nxc.hcmus.model.enums.TaskStatus;

import java.util.List;

public interface TaskService {

    Task getById(Long id);
    Task createTask(String description);

    void updateTask(Long taskId, String description);

    void deleteTask(Long taskId);

    List<Task> getAllTasks();

    List<Task> getAllTask(TaskStatus status);

    void mark(Long taskId, TaskStatus status);
}
