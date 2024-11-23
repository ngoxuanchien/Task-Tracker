package nxc.hcmus;

public interface TaskService {

    Task getById(Long id);
    Task createTask(String description);
}
