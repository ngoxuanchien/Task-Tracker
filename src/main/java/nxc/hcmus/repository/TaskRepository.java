package nxc.hcmus.repository;

import nxc.hcmus.model.entity.Task;
import nxc.hcmus.model.enums.TaskStatus;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task save(Task task);
    Optional<Task> findById(Long id);

    void delete(Task task);

    List<Task> findAll();

    List<Task> findAllByStatus(TaskStatus status);
}
