public class Student {
    private String name;
    private String stID;
    private Module[] modules;

    public Student(String name, String stID) {
        this.name = name;
        this.stID = stID;
        this.modules = new Module[3]; // Assuming each student has 3 modules by default
    }

    public String getName() {
        return name;
    }

    public String getStID() {
        return stID;
    }

    public Module[] getModules() {
        return modules;
    }

    public void setModules(Module[] modules) {
        this.modules = modules;
    }

    public double calculateTotalMarks() {
        double total = 0;
        for (Module module : modules) {
            total += module.getMarks();
        }
        return total;
    }

    public double calculateAverageMarks() {
        double total = calculateTotalMarks();
        double average = total / modules.length;
        return average;
    }

    public String calculateGrade() {
        double average = calculateAverageMarks();

        if (average >= 80) {
            return "Distinction";
        } else if (average >= 70) {
            return "Merit";
        } else if (average >= 40) {
            return "Pass";
        } else {
            return "Fail";
        }
    }
}
