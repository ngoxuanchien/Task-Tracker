package nxc.hcmus;

import nxc.hcmus.config.ApplicationConfig;
import nxc.hcmus.model.entity.Task;
import nxc.hcmus.model.enums.TaskStatus;
import nxc.hcmus.service.TaskService;
import nxc.hcmus.service.TaskServiceImpl;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        var config = ApplicationConfig.getInstance();
        config.loadConfig();
        var taskService = ApplicationConfig.getTaskService();
        switch (args[0]) {
            case "add" -> {
                System.out.println("Adding task: " + args[1]);
                taskService.createTask(args[1]);
            }
            case "update" -> {
                System.out.println("Updating task: " + args[1]);
                taskService.updateTask(Long.parseLong(args[1]), args[2]);
            }
            case "delete" -> {
                System.out.println("Deleting task: " + args[1]);
                taskService.deleteTask(Long.parseLong(args[1]));
            }
            case "mark-in-progress" -> {
                System.out.println("Marking task in progress: " + args[1]);
                taskService.mark(Long.parseLong(args[1]), TaskStatus.IN_PROGRESS);
            }
            case "mark-done" -> {
                System.out.println("Marking task done: " + args[1]);
                taskService.mark(Long.parseLong(args[1]), TaskStatus.DONE);
            }
            case "mark-todo" -> {
                System.out.println("Marking task todo: " + args[1]);
                taskService.mark(Long.parseLong(args[1]), TaskStatus.TODO);
            }
            case "list" -> {
                System.out.println("Listing tasks");
                if (args.length == 1) {
                    taskService.getAllTasks()
                            .forEach(Task::printTask);
                } else {
                    switch (args[1]) {
                        case "todo" -> taskService.getAllTask(TaskStatus.TODO)
                                .forEach(Task::printTask);
                        case "done" -> taskService.getAllTask(TaskStatus.DONE)
                                .forEach(Task::printTask);
                        case "in-progress" -> taskService.getAllTask(TaskStatus.IN_PROGRESS)
                                .forEach(Task::printTask);
                        default -> taskService.getAllTasks()
                                .forEach(Task::printTask);
                    }
                }
            }
            default -> System.out.println("Invalid command");
        }
        config.saveConfig();
    }
}