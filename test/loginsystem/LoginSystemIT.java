package loginsystem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginSystemIT {

    @Test
    public void testTaskDescriptionLength() {
        String task1_description = "Create Login to authenticate users";
        String task2_description = "Create Add Task feature to add task users";

        assertTrue(task1_description.length() <= 50, "Task 1 description should be less than or equal to 50 characters");
        assertTrue(task2_description.length() <= 50, "Task 2 description should be less than or equal to 50 characters");
    }

    @Test
    public void testTotalHoursAccumulated() {
        int[] durations = {8, 10};
        int totalHours = 0;

        for (int duration : durations) {
            totalHours += duration;
        }

        assertEquals(18, totalHours, "Total hours accumulated should be 18 for the given durations");

        // Additional test data
        int[] additionalDurations = {10, 12, 55, 11, 1};
        int additionalTotalHours = 0;

        for (int duration : additionalDurations) {
            additionalTotalHours += duration;
        }

        assertEquals(89, additionalTotalHours, "Total hours accumulated should be 89 for the additional durations");
    }

    @Test
    public void testTaskCaptureSuccess() {
        String taskDescription = "Create Login to authenticate users";
        String developerDetails = "Robyn Harrison";
        String taskStatus = "To Do";

        String result = captureTask(taskDescription, developerDetails, taskStatus);
        assertEquals("Task successfully captured", result, "Task capture should be successful");
    }

    @Test
    public void testTaskCaptureFailure() {
        String taskDescription = "This is a very long task description that exceeds the character limit";
        String developerDetails = "Mike Smith";
        String taskStatus = "Doing";

        String result = captureTask(taskDescription, developerDetails, taskStatus);
        assertEquals("Please enter a task description of less than 50 characters", result, "Task capture should fail due to long task description");
    }

    public String captureTask(String taskDescription, String developerDetails, String taskStatus) {
        String taskId = generateTaskId();
        // Implement the logic to capture the task with the provided details
        // Return appropriate success or failure message
        if (taskDescription.length() <= 50) {
            // Task capture successful
            return "Task successfully captured";
        } else {
            // Task capture failed due to long task description
            return "Please enter a task description of less than 50 characters";
        }
    }

    public String generateTaskId() {
        // Implement the logic to generate a unique task ID
        // Return the generated task ID
        return "Auto generated task ID";
    }
}
