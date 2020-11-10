
import java.util.*;

import jdk.jfr.StackTrace;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.*;
import java.io.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.io.File;
import java.io.FileInputStream;


/**
 * The Class TopBloc which includes most of the code for this project.
 */
public class TopBloc {

    /**
     * Instance Variables:
     * Students is an array list containing each student as a Student object
     * scores is a Map from IDs to scores
     */
    public static ArrayList<Student> students = new ArrayList<Student>();
    private static HashMap scores = new HashMap<Float, Float>();


    /**
     * This method is a helper function to read an xlsx file and returns an iterator to run through it
     */
    public Iterator<Row> setUpIterator(String path) {
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


    /**
     * This method puts all students from the "Student Info" file into the students variable
     */
    public void ProcessStudentData() {
        Iterator<Row> itr = setUpIterator("/Users/Alessandro/Desktop/Turtle-TopBloc/Student Info.xlsx");
        itr.next();
        while (itr.hasNext()) {
            Row row = itr.next();
            Iterator<Cell> cell = row.cellIterator();
            Student s = new Student(cell.next().toString(), cell.next().toString(), cell.next().toString());
            this.students.add(s);
        }
    }


    /**
     * This method assigns each id with a score in the scores HashMap
     */
    public void processTests() {
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


    /**
     * This method compares the retakes from the original scores and replaces them as needed
     */
    public void processRetakes() {
        Iterator<Row> itr = setUpIterator("/Users/Alessandro/Desktop/Turtle-TopBloc/Test Retake Scores.xlsx");
        itr.next();
        while (itr.hasNext()) {
            Row row = itr.next();
            Iterator<Cell> cell = row.cellIterator();
            Float id = Float.valueOf(cell.next().toString());
            Float score = Float.valueOf(cell.next().toString());
            if (Float.compare(score, (Float) scores.get(id)) == 1) {
                scores.replace(id, score);
            }

        }
    }

    /**
     * This method calculates the average scores after taking retakes into consideration
     * HashMap h is a parameter, intended to be the scores HashMap
     * Returns a float representing the average test score
     */
    public float calculateAverage(HashMap h) {
        float total = 0;
        for (Object value : h.values()) {
            total+= (Float) value;
        }
        return total/(h.values().size());
    }

    /**
     * This function returns a list of IDs of students who are female computer scientist majors.
     * @param students: a list of Student objects containing ID, major, and sex.
     * @return An array of Strings representing the IDs of these students.
     */
    public ArrayList<String> getFCSMajors(ArrayList<Student> students) {
        ArrayList<String> answer = new ArrayList<String>();
        for (Student s : students) {
            if (s.getMajor().equals("computer science") && s.getSex().equals("F")) {
                answer.add(String.valueOf(s.getId()));
            }
        }
        return answer;
    }


    //Run the main method to execute the code
    public static void main(String[] args) {

        // Instantiate a TopBloc Object
        TopBloc a = new TopBloc();
        //Process the Student Data, Tests, and Retakes
        a.ProcessStudentData();
        a.processTests();
        a.processRetakes();

        // Calculating class average
        System.out.println("The class average, after retakes is: ");
        System.out.print(a.calculateAverage(scores));
        System.out.println();

        // Fining female computer science students
        System.out.println("The IDs of the students that are female computer science majors are: ");
        System.out.println(a.getFCSMajors(students));
        ArrayList<String> fcs = a.getFCSMajors(students);
        //Sorting the IDs of the females for JSON purposes
        Collections.sort(fcs);

        //Creating new JSON Object and printing it
        JSONObject json = new JSONObject();
        json.put("id", "alessandrobuy@gmail.com");
        json.put("name", "Alessandro Buy");
        json.put("average", Math.round(a.calculateAverage(scores)));
        json.put("studentIDs", fcs.toString());
        StringWriter output = new StringWriter();
        json.write(output);

        String jsonText = output.toString();
        System.out.println(jsonText);
        System.out.println();

        // Writing a JSON request at the given port
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://54.90.99.192:5000/challenge"))
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(jsonText))
                    .build();

            HttpResponse<Void> response = client.send(request,
                    HttpResponse.BodyHandlers.discarding());

            System.out.println(response.statusCode());
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

    }
}