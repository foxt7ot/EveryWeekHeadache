package skillnet.everyweek.headache;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        /*if(args.length > 1 || args.length <= 0) {
            System.out.println("GTFO");
            return;
        }
        String directoryPath = args[0];*/
        String directoryPath = "C:/Users/Yaseen/Documents/idk/";
        GlobalResources.directoryStructure = directoryPath;
        File directory = new File(directoryPath);
        if(!directory.isDirectory()) {
            System.out.println("GTFO");
            return;
        }
        String files[] = directory.list();
        if(files == null || files.length<=0){
            System.out.println("Provided directory doesn't contains files");
            return;
        }
        List<String> workbookFiles = new ArrayList<>();
        for(String file:files){
            if(FilenameUtils.isExtension(file,"csv")){
                workbookFiles.add(file);
            }
        }
        ReadFile.readFile(workbookFiles);

    }
}
