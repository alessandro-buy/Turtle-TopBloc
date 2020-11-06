import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.http.client.*;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.*;
import org.apache.http.HttpResponse;



public class TopBloc {

    private static ArrayList<Student> students = new ArrayList<Student>();
    //private static HashMap scores = new HashMap<Student, Float>();
    private static HashMap scores = new HashMap<Float, Float>();

    private Iterator<Row> setUpIterator(String path) {
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);
            return sheet.iterator();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private void ProcessStudentData() {
        Iterator<Row> itr = setUpIterator("/Users/Alessandro/Desktop/Turtle-TopBloc/Student Info.xlsx");
        itr.next();
        while (itr.hasNext()) {
            Row row = itr.next();
            Iterator<Cell> cell = row.cellIterator();
            Student s = new Student(cell.next().toString(), cell.next().toString(), cell.next().toString());
            this.students.add(s);
        }
    }

    private void processTests() {
        Iterator<Row> itr = setUpIterator("/Users/Alessandro/Desktop/Turtle-TopBloc/Test Scores.xlsx");
        itr.next();
        while (itr.hasNext()) {
            Row row = itr.next();
            Iterator<Cell> cell = row.cellIterator();
            Float id = Float.valueOf(cell.next().toString());
            Float v = Float.valueOf(cell.next().toString());
            scores.put(id, v);
        }
    }

    private void processRetakes() {
        Iterator<Row> itr = setUpIterator("/Users/Alessandro/Desktop/Turtle-TopBloc/Test Retake Scores.xlsx");
        itr.next();
        while (itr.hasNext()) {
            Row row = itr.next();
            Iterator<Cell> cell = row.cellIterator();
            Float id = Float.valueOf(cell.next().toString());
            Float score = Float.valueOf(cell.next().toString());
            if (Float.compare(score, (Float) scores.get(id)) == 1) {
                this.scores.replace(id, score);
            }

        }
    }

    private float calculateAverage(HashMap h) {
        float total = 0;
        for (Object value : h.values()) {
            total+= (float) value;
        }
        return total/(h.values().size());
    }

    public ArrayList<Integer> getFCSMajors(ArrayList<Student> students) {
        ArrayList<Integer> answer = new ArrayList<>();
        for (Student s : students) {
            if (s.getMajor().equals("computer science") && s.getSex().equals("F")) {
                answer.add(s.getId());
            }
        }
        return answer;
    }




    public static void main(String[] args)
    {
        TopBloc a = new TopBloc();
        a.ProcessStudentData();
        System.out.println(students);
        a.processTests();
        System.out.println(scores);
        a.processRetakes();
        System.out.println(scores);
        System.out.println();


        System.out.println("The class average, after retakes is: ");
        System.out.print(a.calculateAverage(scores));
        System.out.println();
        System.out.println("The IDs of the students that are female computer science majors are: ");
        System.out.println(a.getFCSMajors(students));

//
//        HttpClient httpClient = HttpClientBuilder.create().build();
//        try {
//            HttpPost request = new HttpPost("http://54.90.99.192:5000/challenge");
//            StringEntity params = new StringEntity("details={\"name\":\"xyz\",\"age\":\"20\"} ");
//            request.addHeader("content-type", "application/x-www-form-urlencoded");
//            request.setEntity(params);
//            HttpResponse response = httpClient.execute(request);
//        } catch (Exception ex) {
//        }






    }

}
