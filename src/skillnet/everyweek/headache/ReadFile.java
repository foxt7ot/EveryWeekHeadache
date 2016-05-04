package skillnet.everyweek.headache;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Yaseen on 5/3/2016.
 */
public class ReadFile {
    public static void readFile(List workbookFiles){
        String file = (String)workbookFiles.get(0);
        try {
            FileReader fileReader = new FileReader(new File(GlobalResources.directoryStructure + file));
            CSVReader csvReader = new CSVReader(fileReader);
            List<String[]> myEnteries = csvReader.readAll();
            for(String string[]: myEnteries){
                System.out.println(string);
            }
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
//            hssfWorkbook.setSheetName(1,"Yaseen");
            HSSFSheet sheet = hssfWorkbook.createSheet("YAseen");

            for(String[] rows : myEnteries){
                for(int j=0; j<myEnteries.size(); j++){
                    Row row = sheet.createRow(j);
                    for(int i =0; i<myEnteries.size(); i++){
                        Cell cell = row.createCell(i);
                        cell.setCellValue(myEnteries.get(j)[i]);
                    }
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(new File(GlobalResources.directoryStructure + "temp.xls"));
            hssfWorkbook.write(fileOutputStream);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static float feedMeAndSaveYours(String time){
        String bifurcateTime[] = time.split(":");
        float hour, minutes, minutesInHour, totalTime;
        hour = Float.valueOf(bifurcateTime[0]);
        minutes = Float.valueOf(bifurcateTime[1]);
        minutesInHour = minutes/60;
        totalTime = hour+minutesInHour;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        totalTime = Float.valueOf(decimalFormat.format(totalTime));
        return totalTime;
    }
}
