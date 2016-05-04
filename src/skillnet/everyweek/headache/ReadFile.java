package skillnet.everyweek.headache;

import com.opencsv.CSVReader;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by Yaseen on 5/3/2016.
 */
public class ReadFile {
    private List<String[]> csvEnteries;
    List<TimeEntryDate> timeEntryDates;
    List<ProjectName> projectNames;
    List<TaskName> taskNames;
    List<Description> descriptions;
    List<Farzi> farzis;
    List<TotalTime> totalTimes;
    Map<String, Object> data;
    String numberOfProject[];
    public void readFile(List workbookFiles){
        String file = (String)workbookFiles.get(0);
        try {
            FileReader fileReader = new FileReader(new File(GlobalResources.directoryStructure + file));
            CSVReader csvReader = new CSVReader(fileReader);
            csvEnteries = csvReader.readAll();
            fillMyData();
            fillMyMap();
            sumUp();

            HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
//            hssfWorkbook.setSheetName(1,"Yaseen");
            HSSFSheet sheet = hssfWorkbook.createSheet("YAseen");

            for(String[] rows : csvEnteries){
                for(int j=0; j<csvEnteries.size(); j++){
                    Row row = sheet.createRow(j);
                    for(int i =0; i<csvEnteries.size(); i++){
                        Cell cell = row.createCell(i);
                        cell.setCellValue(csvEnteries.get(j)[i]);
                    }
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(new File(GlobalResources.directoryStructure + "temp.xls"));
            hssfWorkbook.write(fileOutputStream);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public float feedMeAndSaveYours(String time){
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


    public void fillMyData(){
        TimeEntryDate timeEntryDate;
        ProjectName projectName;
        TaskName taskName;
        Description description;
        Farzi farzi;
        Farzi farzi2;
        TotalTime totalTime;

        timeEntryDates = new ArrayList<>();
        projectNames = new ArrayList<>();
        taskNames = new ArrayList<>();
        descriptions = new ArrayList<>();
        farzis = new ArrayList<>();
        totalTimes = new ArrayList<>();
        
        for(int i=0; i<csvEnteries.size(); i++){
            timeEntryDate = new TimeEntryDate();
            projectName = new ProjectName();
            taskName = new TaskName();
            description = new Description();
            farzi = new Farzi();
            farzi2 = new Farzi();
            totalTime = new TotalTime();

            if(i==0)continue;
            for(int j=0,k=0; j<csvEnteries.size(); j++){
                switch (j){
                    case 0:

                        timeEntryDate.setDate(csvEnteries.get(i)[k].toString());
                        k++;
                        break;
                    case 1:
                        projectName.setProjectName(csvEnteries.get(i)[k].toString());
                        k++;
                        break;
                    case 2:
                        taskName.setTaskName(csvEnteries.get(i)[k].toString());
                        k++;
                        break;
                    case 3:
                        description.setDescription(csvEnteries.get(i)[k].toString());
                        k++;
                        break;
                    case 4:
                        farzi.setFarziEmptyVariable("");
                        k++;
                        break;
                    case 5:
                        farzi2.setFarziEmptyVariable("");
                        k++;
                        break;
                    case 6:
                        totalTime.setTotalTime(csvEnteries.get(i)[k].toString());
                        k=0;
                        break;
                }
            }
            timeEntryDates.add(timeEntryDate);
            projectNames.add(projectName);
            taskNames.add(taskName);
            descriptions.add(description);
            farzis.add(farzi);
            totalTimes.add(totalTime);
        }
    }

    public void fillMyMap(){
        data = new HashMap<>();
        for(int i=0; i<csvEnteries.size(); i++){
            if(i!=0)break;
            for(int j=0,k=0; j<csvEnteries.size(); j++){
                switch (j){
                    case 0:
                        data.put(csvEnteries.get(i)[k],timeEntryDates);
                        k++;
                        break;
                    case 1:
                        data.put(csvEnteries.get(i)[k], projectNames);
                        k++;
                        break;
                    case 2:
                        data.put(csvEnteries.get(i)[k], taskNames);
                        k++;
                        break;
                    case 3:
                        data.put(csvEnteries.get(i)[k], descriptions);
                        k++;
                        break;
                    case 4:
                        data.put(csvEnteries.get(i)[k], farzis);
                        k++;
                        break;
                    case 5:
                        data.put(csvEnteries.get(i)[k], farzis);
                        k++;
                        break;
                    case 6:
                        data.put(csvEnteries.get(i)[k],totalTimes);
                        k=0;
                        break;
                }
            }
        }
    }

    public void sumUp(){
        int numberOfProjects=0;
        Iterator iterator = data.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry keyValue = (Map.Entry)iterator.next();
            if(keyValue.getKey().equals(GlobalResources.PROJECT_NAME)){
                List<ProjectName> tempList = (ArrayList)keyValue.getValue();
                for(int i=0; i<tempList.size(); i++){
                    if(i != 0)break;
                    for(int j=0; j<tempList.size(); j++){
                        if(!tempList.get(i).getProjectName().equals(tempList.get(j).getProjectName()))numberOfProjects++;
                    }
                }
            }
        }
    }
}
