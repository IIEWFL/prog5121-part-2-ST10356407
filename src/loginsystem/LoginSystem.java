package loginsystem;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class LoginSystem {
    private static List<User> users = new ArrayList<>();
    private static List<Task> tasks = new ArrayList<>();
    private static double totalDuration = 0;

    public static void main(String[] args) {

        displayMenu();
    }
//registration for user
    public static void registerUser() {
        String firstName = inputDialog("Please enter first name:");
        String lastName = inputDialog("Please enter last name:");
        String username = inputDialog("Please enter a username:");
//shall user not meet username restrictions 
        while (!isValidUsername(username)) {
            showMessageDialog("Username is not correctly formatted. Please ensure that your username contains an underscore and is no more than 5 characters in length.");
            username = inputDialog("re-enter username:");
        }

        String password = inputDialog("Please enter a password:");
//shall user not meet password restrictions
        while (!isValidPassword(password)) {
            showMessageDialog("Password is not correctly formatted. Please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.");
            password = inputDialog("re-enter password:");
        }

        users.add(new User(firstName, lastName, username, password));

        showMessageDialog("User registered successfully!");
    }
    //https://stackoverflow.com/questions/53185191/creating-a-sign-up-and-log-in-page-using-java
//promt user to use registration details while logging in
    public static void loginUser() {
        String enteredUsername = inputDialog("Please enter username:");
        String enteredPassword = inputDialog("Please enter password:");

        for (User user : users) {
            if (user.getUsername().equals(enteredUsername) && user.getPassword().equals(enteredPassword)) {
                showMessageDialog("Welcome to EasyKanBan, " + user.getFullName() + "! You have successfully logged in!.");
                return;
            }
        }
//shall user fill out incorrect details 
        showMessageDialog("Incorrect username or password. Please try again with your correct details.");
        loginUser();
    }
    //https://stackoverflow.com/questions/53185191/creating-a-sign-up-and-log-in-page-using-java
//once user enters EasyKanBan the main menu appears with three options
    public static void displayMenu() {
        String[] options = { "Add Tasks", "Show Report", "Quit" };// option 1, option 2 and option3

        while (true) {
            int choice = showOptionDialog("|MAIN MENU|", "Menu", options);

            switch (choice) {
                case 0:
                    addTasks();
                    break;
                case 1:
                    showMessageDialog("Coming Soon!");//displays when user clicks option 2
                    break;
                case 2:
                    showMessageDialog("Closing the application...");//displays when user clicks option 3 
                    return;
                default:
                    showMessageDialog("Invalid choice. Please try again and enter a valid option.");//shall user choose an invalid option
                    //https://stackoverflow.com/questions/66600627/create-a-simple-menu-with-java
            }
        }
    }
//details about tasks
    public static void addTasks() {
        int numTasks = parseInt(inputDialog("How many tasks would you like to add?"));

        for (int i = 0; i < numTasks; i++) {
            String taskName = inputDialog("Please enter task name:");
            String taskDescription = inputDialog("Please enter task description (up to 50 characters):");

            if (taskDescription.length() > 50) {
                showMessageDialog("Please ensure the task description is less than 50 characters.");
                i--;
                continue;
            }

            String developerDetails = inputDialog("Please enter developer details (Name and Surname):");
            double taskDuration = parseDouble(inputDialog("Enter task duration (In hours):"));
            String taskID = generateTaskID(taskName, i + 1, developerDetails);
//Code for users Task ID
            showMessageDialog("Task successfully captured!\n\nTask ID: " + taskID);

            Task newTask = new Task(taskName, taskDescription, developerDetails, taskDuration, taskID);
            tasks.add(newTask);

            totalDuration += taskDuration;

            // Edit task status
            String[] statusOptions = { "To Do", "Doing", "Done" };
            int choice = showOptionDialog("Change Task Status", "Task Status", statusOptions);

            if (choice >= 0 && choice < statusOptions.length) {
                newTask.setStatus(statusOptions[choice]);
                showMessageDialog("Task status changed successfully!");
            } else {
                showMessageDialog("Invalid choice. Task status not changed.");
            }
        }
        //https://stackoverflow.com/questions/69904005/how-can-i-add-a-button-to-a-todo-list-to-mark-tasks-as-done-javascript

        showReport();
    }
//Task report 
    public static void showReport() {
        StringBuilder report = new StringBuilder("|TASK REPORT|\n");

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            report.append("\nTask Status: ").append(task.getStatus());
            report.append("\nDeveloper Details: ").append(task.getDeveloperDetails());
            report.append("\nTask Number: ").append(i);
            report.append("\nTask Name: ").append(task.getTaskName());
            report.append("\nTask Description: ").append(task.getTaskDescription());
            report.append("\nTask ID: ").append(task.getTaskID());
            report.append("\nTask Duration: ").append(task.getTaskDuration()).append(" hours\n");
        }

        report.append("\nTotal Duration: ").append(totalDuration).append(" hours");

        showMessageDialog(report.toString());
        //https://stackoverflow.com/questions/22792139/how-do-i-generate-reports-in-java
    }
//
    private static boolean isValidUsername(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    private static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$");
        //https://stackoverflow.com/questions/33512547/generating-random-password-with-lot-of-restriction-in-java
    }

    private static String inputDialog(String message) {
        return JOptionPane.showInputDialog(null, message);
    }

    private static void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private static int showOptionDialog(String message, String title, String[] options) {
        return JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }

    private static int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return -1.0;
        }
    }

    static class User {
        private String firstName;
        private String lastName;
        private String username;
        private String password;

        public User(String firstName, String lastName, String username, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.username = username;
            this.password = password;
        }

        public String getFullName() {
            return firstName + " " + lastName;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    static class Task {
        private String taskName;
        private String taskDescription;
        private String developerDetails;
        private double taskDuration;
        private String taskID;
        private String status;

        public Task(String taskName, String taskDescription, String developerDetails, double taskDuration, String taskID) {
            this.taskName = taskName;
            this.taskDescription = taskDescription;
            this.developerDetails = developerDetails;
            this.taskDuration = taskDuration;
            this.taskID = taskID;
            this.status = "To Do";
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTaskName() {
            return taskName;
        }

        public String getTaskDescription() {
            return taskDescription;
        }

        public String getDeveloperDetails() {
            return developerDetails;
        }

        public double getTaskDuration() {
            return taskDuration;
        }

        public String getTaskID() {
            return taskID;
        }
    }

    private static String generateTaskID(String taskName, int taskNumber, String developerDetails) {
        String taskID = "";

        if (taskName.length() >= 2) {
            taskID += taskName.substring(0, 2);
        } else {
            taskID += taskName;
        }

        taskID += ":" + taskNumber + ":";

        if (developerDetails.length() >= 3) {
            taskID += developerDetails.substring(developerDetails.length() - 3);
        } else {
            taskID += developerDetails;
        }

        return taskID;
        //https://stackoverflow.com/questions/15866028/auto-generate-id
    }
}
//Farrel, J. 20170101, Programming Logic and Design, Comprehensive, 9th Edition, Cengage Learning. Available from: vbk://9781337517041
//Farrel, J. Java Programming, Comprehensive, 9th Edition Cengage Learning//9781337397070