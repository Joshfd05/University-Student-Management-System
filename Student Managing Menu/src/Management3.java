import java.io.File;    // Create file object
import java.io.FileNotFoundException;   // Exception thrown when the file doesn't exist
import java.io.FileWriter;  // Write to file
import java.io.IOException; // Superclass to handle errors
import java.util.Random;    // Generating random numbers. Used to create random ID
import java.util.Scanner;   // Read user input


public class Management3 {
    private static String[][] studentsArray = new String[100][5]; // 2D array for tasks 1-7
    private static Student[] students = new Student[100]; // Array of Student objects for task 8

    public static void main(String[] args) {
        initialize(studentsArray);

        Scanner sc = new Scanner(System.in);

            while (true) {          // Loop will continue and keep display the menu until the user exit
                try{
                // Displaying 8 options to user as menu
                System.out.println("1. Check Available Seats");
                System.out.println("2. Register Student (With ID)");
                System.out.println("3. Delete Student");
                System.out.println("4. Find student (with student ID)");
                System.out.println("5. Store Student Details");
                System.out.println("6. Load student details from the file to the system");
                System.out.println("7. View the list of students based on their names");
                System.out.println("8. Manage students Results");
                System.out.println("9. Exit");

                System.out.print("Enter the option you want: ");
                int choice = sc.nextInt();
                sc.nextLine();  // Get a new line

                switch (choice) {
                    case 1:
                        checkAvailableSeats();
                        break;
                    case 2:
                        registerStudent(sc);
                        break;
                    case 3:
                        deleteStudent(sc);
                        break;
                    case 4:
                        findStudent(sc);
                        break;
                    case 5:
                        storeDetails("StudentRecordsTask3.txt");
                        break;
                    case 6:
                        loadDetails();
                        break;
                    case 7:
                        viewDetails();
                        break;
                    case 8:
                        manageResults(sc);
                        break;
                    case 9:
                        System.out.println("Thank you for using Student Management System...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please Try Again");
                }
            }
                catch (Exception e) {
                    System.out.println("Input miss match..");
                    sc.next();
                }
        }

    }

    // Initialize the 2D array to "empty"
    private static void initialize(String[][] studentsArray) {
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 2; y++) {
                studentsArray[x][y] = "empty";
            }
        }
    }

    // Option 1: Check and display available seats
    private static void checkAvailableSeats() {
        int availableSeats = 0;
        for (int x = 0; x < studentsArray.length; x++) {
            if (studentsArray[x][0].equals("empty")) {
                System.out.println("Seat " + (x + 1) + " is empty");
                availableSeats++;
            }
        }
        System.out.println("Number of Available Seats: " + availableSeats);
        System.out.println("==============================================");
    }

    // Generate a random ID for student
    private static String generateRandomID() {
        Random ranID = new Random();
        String id = "w";    // write "w" character in front of each ID
        // Loop till the ID get 7 digits
        for (int i = 0; i < 7; i++) {
            id += ranID.nextInt(10);
        }
        return id;  // Return ID for access in other methods
    }

    // Option 2: Registering new student
    private static void registerStudent(Scanner sc) {
        String name;    // Variable for name

        // Validate the name to contain only characters
        while (true) {
            System.out.print("Enter Student Name: ");
            name = sc.nextLine().toLowerCase();

            if (name.matches("[a-zA-Z ]+")) {
                break;
            } else {
                System.out.println("Invalid name. Please enter a name containing only letters.");
            }
        }

        String id;
        boolean registered = false;

        while (!registered) {
            id = generateRandomID();
            boolean idExist = false;

            for (String[] student : studentsArray) {
                if (student[1].equals(id)) {
                    idExist = true;
                    break;
                }
            }

            if (!idExist) {
                // Find the first empty slot and register the student
                for (int x = 0; x < studentsArray.length; x++) {
                    if (studentsArray[x][0].equals("empty")) {
                        studentsArray[x][0] = name;
                        studentsArray[x][1] = id;
                        studentsArray[x][2] = null;
                        studentsArray[x][3] = null;
                        studentsArray[x][4] = null;
                        System.out.println("Registered " + name + " with ID " + id + " in seat number " + (x + 1));
                        registered = true;
                        break;
                    }
                }
            }
        }
    }

    // Option 3: Delete student by their ID
    private static void deleteStudent(Scanner sc) {
        System.out.print("Enter ID of the student you want to delete: ");
        String id = sc.nextLine().toLowerCase();

        boolean found = false;

        // Find the student based on the entered ID and delete their record
        for (int x = 0; x < studentsArray.length; x++) {
            if (studentsArray[x][1].equals(id)) {
                studentsArray[x][0] = "empty";
                studentsArray[x][1] = "empty";
                studentsArray[x][2] = "empty";
                studentsArray[x][3] = "empty";
                studentsArray[x][4] = "empty";
                found = true;
                break;
            }
        }
        if (found) {
            System.out.println("Student " + id + " has been deleted successfully");
            updateFile("StudentRecordsTask3.txt");
        }
        else {
            System.out.println("Student " + id + " not found");
        }
    }

    // Helper method to update text file after deleting method
    private static void updateFile(String fileName) {

        // Opening Filewriter wihin the try block ensures it closes properly
        try (FileWriter writer = new FileWriter(fileName)) {

            // Iterate through each element in studentsArray
            for (String[] student : studentsArray) {
                if (!student[0].equals("empty")) {
                    // Write data to text file(name, ID, mark1, mark2, mark3)
                    writer.write(student[0] + "," + student[1] + "," + student[2] + "," + student[3] + "," + student[4] + "\n");
                }
            }
            System.out.println("Data has been updated in " + fileName);
        }
        catch (IOException e) {
            System.out.println("An error occurred while updating data in file: " + e.getMessage());
        }
    }

    // Option 4: Find a student by ID
    private static void findStudent(Scanner sc) {
        System.out.print("Enter ID of the student you want to find: ");
        String id = sc.nextLine().toLowerCase();

        boolean found = false;
        for (int x = 0; x < studentsArray.length; x++) {
            if (studentsArray[x][1].equals(id)) {
                System.out.println("Student " + id + " has been found");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Student " + id + " not found");
        }
    }

    // Option 5: Storing details to text file(StudentRecords.txt)
    private static void storeDetails(String filename) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            for (String[] student : studentsArray) {
                if (!student[0].equals("empty") && !idStored(filename, student[1])) {
                    writer.write(student[0] + "," + student[1] + "," + student[2] + "," + student[3] + "," + student[4] + "\n");
                }
            }
            System.out.println("Data has been saved to " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred while saving data to file: " + e.getMessage());
        }
    }

    // Helper method to check if ID is already stored
    private static boolean idStored(String filename, String id) {
        try {
            Scanner scan = new Scanner(new File(filename));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] details = line.split(",");
                if (details.length >= 2 && details[1].equals(id)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found" + e.getMessage());
        }
        return false;
    }

    // Option 6: Load student details from the file to the system
    private static void loadDetails() {
        try {
            File file = new File("StudentRecordsTask3.txt");
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] details = line.split(",");

                if (details.length >= 5) {
                    boolean idExist = false;
                    for (int x = 0; x < studentsArray.length; x++) {
                        if (studentsArray[x][1] != null && studentsArray[x][1].equals(details[1])) {
                            idExist = true;
                            break;
                        }
                    }
                    if (!idExist) {
                        for (int x = 0; x < studentsArray.length; x++) {
                            if (studentsArray[x][0].equals("empty")) {
                                studentsArray[x][0] = details[0];
                                studentsArray[x][1] = details[1];
                                studentsArray[x][2] = details[2];
                                studentsArray[x][3] = details[3];
                                studentsArray[x][4] = details[4];
                                break;
                            }
                        }
                    } else {
                        System.out.println("Already loaded. Try again after registering a new student.");
                    }
                } else {
                    System.out.println("Invalid data format: " + line);
                }
            }
            scan.close();
            System.out.println("Data has been loaded to the System successfully");
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while loading data from the file: " + e.getMessage());
        }
    }

    // Option 7: View the lit of students based on their name(Sorted)
    private static void viewDetails() {
        for (int i = 0; i < studentsArray.length; i++) {
            for (int j = i + 1; j < studentsArray.length; j++) {
                if (studentsArray[i][0].compareTo(studentsArray[j][0]) > 0) {
                    String[] temp = studentsArray[i];
                    studentsArray[i] = studentsArray[j];
                    studentsArray[j] = temp;
                }
            }
        }

        System.out.println("Sorted list of students based on their names:");
        System.out.println("======================================================");
        for (int x = 0; x < studentsArray.length; x++) {
            if (!studentsArray[x][0].equals("empty")) {
                System.out.println("Name: " + studentsArray[x][0] + ", ID: " + studentsArray[x][1]);
            }
        }
    }

    // Option 8: Manage student results
    private static void manageResults(Scanner sc) {
        loadStudentsFromFile("StudentRecordsTask3.txt");    // Call the load helper method to load data from text file

        while(true){
            try {
                // Display menu option for sub options in option 8
                System.out.println("1. Add student name");
                System.out.println("2. Module marks 1, 2 and 3");
                System.out.println("3. Generate a summary of the system");
                System.out.println("4. Generate complete report");
                System.out.println("5. Back to main menu");

                System.out.print("Enter your choice: ");
                int option = sc.nextInt();
                sc.nextLine();  // Get a new line

                // Handle each method through menu
                switch (option) {
                    case 1:
                        addStudentName(sc);
                        break;
                    case 2:
                        addModuleMarks(sc);
                        break;
                    case 3:
                        generateSummary();
                        break;
                    case 4:
                        generateCompleteReport(sc);
                        break;
                    case 5:
                        return;     // Return from the option 8 and going back to main menu
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
            catch (Exception e) {
                System.out.println("Input miss match..");
                sc.next();
            }
        }
    }

    // Sub Option 1: Check if the entered name is available
    private static void addStudentName(Scanner sc) {
        System.out.print("Enter student name: ");
        String addName = sc.nextLine();

        boolean nameExists = false;

        for(int x = 0; x < students.length; x++) {
            if(students[x] != null && addName.equalsIgnoreCase(students[x].getName())){
                System.out.println("Student " + addName + " already exists with ID: " + students[x].getStID());
                nameExists = true;
                break;
            }
        }
        // Print error message when the entered student doesn't exist
        if (!nameExists) {
            System.out.println("Student " + addName + " doesn't exist..");
        }
    }

    // Sub Option 2: Add marks for each module based on entered student ID
    private static void addModuleMarks(Scanner sc) {
        System.out.print("Enter Student ID: ");
        String id = sc.nextLine().toLowerCase();

        Student student = null;
        for (Student s : students) {
            if (s != null && s.getStID().equals(id)) {
                student = s;
                break;
            }
        }

        if (student == null) {
            System.out.println("Student not found. Please register the student first.");
            return;
        }

        Module[] modules = student.getModules();
        for (int i = 0; i < 3; i++) {
            System.out.print("Enter marks for Module " + (i + 1) + ": ");
            double marks = sc.nextDouble();
            modules[i].setMarks(marks);
        }

        // Update the file with the new data
        updateStudentFile("StudentRecordsTask3.txt");
        System.out.println("Marks for student " + student.getName() + " have been updated.");
    }

    // Helper method to update the text file when the
    private static void updateStudentFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Student student : students) {
                if (student != null) {
                    writer.write(student.getName() + "," + student.getStID());

                    Module[] modules = student.getModules();
                    if (modules != null) {
                        for (Module module : modules) {
                            writer.write("," + module.getMarks());
                        }
                    }

                    writer.write("\n");
                }
            }
            System.out.println("Data has been updated in " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred while updating data in file: " + e.getMessage());
        }
    }


    // Sub Option 3: Generate a summary about each module
    private static void generateSummary() {
        int totalRegistrations = 0;
        int[] passedModuleCount = new int[3];

        for (Student student : students) {
            if (student != null) {
                totalRegistrations++;
                Module[] modules = student.getModules();
                if (modules != null) {
                    for (int i = 0; i < 3; i++) {
                        if (modules[i] != null && modules[i].getMarks() >= 40) {
                            passedModuleCount[i]++;
                        }
                    }
                }
            }
        }

        System.out.println("Total student registrations: " + totalRegistrations);
        for (int i = 0; i < 3; i++) {
            System.out.println("Total no of students who scored more than 40 marks in Module " + (i + 1) + ": " + passedModuleCount[i]);
        }
    }



    // Helper method to sort. Sort by average highest to lowest
    private static void sortByAverage() {
        // Bubble sort based on average marks in descending order
        for (int i = 0; i < students.length - 1; i++) {
            for (int j = 0; j < students.length - i - 1; j++) {
                if (students[j] == null || students[j + 1] == null) continue;
                if (students[j].calculateAverageMarks() < students[j + 1].calculateAverageMarks()) {
                    // Swap students[j] and students[j + 1]
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
    }

    // Sub Option 4:  Generate complete report with list of students includes
    private static void generateCompleteReport(Scanner sc) {
        sortByAverage();        // Sort all the students based on average in the start

        System.out.println("Complete Report");
        System.out.println("====================================================");

        for(Student student : students) {
            if (student == null) continue;

            // Assign calculated total, average and Grade
            double total = student.calculateTotalMarks();
            double average = student.calculateAverageMarks();
            String grade = student.calculateGrade();


            // Display total and average
            System.out.println("Student ID: " + student.getStID());
            System.out.println("Student Name: " + student.getName());
            // Get each module's marks
            Module[] modules = student.getModules();
            for (int i = 0; i < modules.length; i++) {
                System.out.println("Module " + (i + 1) + " mark: " + modules[i].getMarks());
            }
            System.out.println("Total: " + total);
            System.out.println("Average: " + average);
            System.out.println("Grade: " + grade);
            System.out.println(".........................................................................");
        }

        // Update the file with the new data
        updateStudentFile("StudentRecordsTask3.txt");
        System.out.println("Marks for all students have been updated...");
    }


    // Helper method to load data from text file into objects
    private static void loadStudentsFromFile(String filename) {
        try {
            File file = new File(filename);
            Scanner scan = new Scanner(file);

            int index = 0;
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] details = line.split(",");

                if (details.length >= 5) {
                    String name = details[0];
                    String id = details[1];

                    // Initialize marks array with default values (0.0)
                    double[] marks = new double[3];
                    for (int i = 0; i < 3; i++) {
                        marks[i] = details[i + 2].equals("null") ? 0.0 : Double.parseDouble(details[i + 2]);
                    }

                    Module[] modules = new Module[3];
                    for (int i = 0; i < 3; i++){
                        modules[i] = new Module();
                        modules[i].setMarks(marks[i]);
                    }

                    students[index] = new Student(name, id);
                    students[index].setModules(modules);
                    index++;
                }
                else {
                    System.out.println("Invalid data format: " + line);
                }
            }
            System.out.println("Data loaded from " + filename + " to Student objects successfully.");
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found: " + e.getMessage());
        }
        catch (NumberFormatException e) {
            System.out.println("Error parsing module marks: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }
}