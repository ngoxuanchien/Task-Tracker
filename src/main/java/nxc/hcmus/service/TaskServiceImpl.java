package nxc.hcmus;

import java.util.Optional;

public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository = new TaskRepositoryImpl();

    @Override
    public Task getById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public Task createTask(String description) {
        return Optional.ofNullable(description)
                .map(Task::new)
                .map(taskRepository::save)
                .orElse(null);
    }
}
