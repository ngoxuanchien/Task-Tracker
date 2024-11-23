package nxc.hcmus;

import java.time.LocalDateTime;

public class Task {

    private static Long availableId = 0L;

    public static Long setAvailableId(Long availableId) {
        Task.availableId = availableId;
        return availableId;
    }

    private Long id;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Task(String description) {
        this.id = availableId++;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.status = TaskStatus.TODO;
    }

    public Task() {

    }

}
