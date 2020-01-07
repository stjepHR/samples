import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Stjepan
 */
public class FotoliaCodes {
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        
        Path current = getCurrentDirectory();
        File[] allFiles = listFiles(current.toString());
        List<String> codesList = new ArrayList<String>();
        BufferedWriter out = new BufferedWriter(new FileWriter(current + "/Fotolia kodovi.txt"));
        String[] output = createList(allFiles, codesList).toString().split(", ");
        for (int i = 0; i < output.length; i++) {
            if (output[i].contains("fotolia")) out.write(output[i] + "\r\n");
        }
        
        out.close();
        
    }
    
    private static List createList(File[] allFiles, List codesList) {
        for (int i = 0; i < allFiles.length; i++) {
            if (allFiles[i].getName().contains("fotolia")&&!codesList.contains(allFiles[i].getName())) {
                codesList.add(allFiles[i].getName());
                codesList.add("\n");
            }
            if (allFiles[i].isDirectory()) {
                //Rekurzija
                Path folder = Paths.get(allFiles[i].getPath());
                File[] folderFiles = listFiles(folder.toString());
                createList (folderFiles, codesList);
                }
            }
        return codesList;
    }
    
    /*
    Vraca popis datoteka i foldera u zadanom pathu.
    */
    private static File[] listFiles(String current) {
        File folder = new File(current);
        File[] listOfFiles = folder.listFiles();
        return listOfFiles;
    }
    
    /*
    Vraca trenutni path.
    */
    private static Path getCurrentDirectory() throws IOException {
            String current = new java.io.File( "." ).getCanonicalPath();
            Path currentPath  = Paths.get(current);
            //System.out.println(currentPath);
            return currentPath;
    }
    
    
}