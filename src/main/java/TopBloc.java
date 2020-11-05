import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;


public class TopBloc {





    public void ProcessData() {

    }



    public static void main(String[] args)
    {
        try
        {
            File file = new File("/Users/Alessandro/Desktop/Turtle-TopBloc/Student Info.xlsx");
            FileInputStream fis = new FileInputStream(file);



            //creating Workbook instance that refers to .xlsx file
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object

            Iterator<Row> itr = sheet.iterator();    //iterating over excel file
            itr.next();

            while (itr.hasNext())
            {
                Row row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column
                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();

                    if(cell.getCellTypeEnum() == CellType.STRING) {
                        System.out.print(cell.getStringCellValue() + "\t\t\t");
                        //break;
                    } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                        System.out.print(cell.getNumericCellValue() + "\t\t\t");
                        //break;
                    }

                }
                System.out.println("");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
