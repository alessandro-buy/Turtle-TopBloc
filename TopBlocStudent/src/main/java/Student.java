/**
 * Class Student represents Students from the student info file.
 * Contains information such as ID, major, and sex
 */
public class Student {

    /*
    Class variables, ID is an int, major and sex are Strings
     */
    private int id;
    private String major;
    private String sex;

    /**
     * Constructor saves variables appropriately
     * @param i
     * @param m
     * @param s
     */
    public Student(String i, String m, String s) {
        this.id = Integer.parseInt(i.substring(0, i.length() - 2));
        this.major = m;
        this.sex = s;
    }

    /**
     * Getters for the attributes
     */
    public int getId() { return id;}
    public String getMajor() {return major;}
    public String getSex() {return sex;}

    /**
     * ToString returns the class as a String representation including just the IDs to represent each student
     * @return String
     */
    public String toString() {
        return (String.valueOf(this.id));
    }
}