public class Student {

    private int id;
    private String major;
    private String sex;
    private int testScore1;
    private int testScore2;

    public void Student(int i, String m, String s) {
        this.id = i;
        this.major = m;
        this.sex = s;
    }

    public void setTestScore1(int i) {
        this.testScore1 = i;
    }

    public void setTestScore2(int i) {
        this.testScore2 = i;
    }

    public int getTestScore1() { return testScore1; }
    public int getTestScore2() { return testScore2; }
    public int getId() { return id;}
    public String getMajor() {return major;}
    public String getSex() {return sex;}




}
