public class Student {

    private int id;
    private String major;
    private String sex;


    public Student(String i, String m, String s) {
        this.id = Integer.parseInt(i.substring(0, i.length() - 2));
        this.major = m;
        this.sex = s;
    }


    public int getId() { return id;}
    public String getMajor() {return major;}
    public String getSex() {return sex;}


    public String toString() {
        return (String.valueOf(this.id));
    }



}
