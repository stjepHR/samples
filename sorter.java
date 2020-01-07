package generalisorter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Stjepan
 */
public class GeneraliSorter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //define a file to be sorted
        File whichFile = new File("map-mock.txt");
        //prepare a map to store Language objects read from the file
        List<Language> map = getDataFromFile(whichFile, args);

        //sort&print the map
        Collections.sort(map);
        printList(map);
    }
    
    /*
    This method prints out Language objects stored in the map.
    */
    public static void printList(List<Language> map) {
        for (int i = 0; i < map.size(); i++) {
            System.out.println(map.get(i).toString());
        }
    }
    
    /*
    This method parses data stored in an external .txt file and creates a Language objects' map.
    */
    public static List<Language> getDataFromFile(File fileName, String[] args) {
        
        //prepare low/high limit variables if user passed those as arguments
        int lowLimit = 0;
        int highLimit = 0;
        //prepare a flag variable to determine if the output map should be limited to entered values
        boolean valuesLimited = false;
        try {
            //check which of the first two values is smaller
            if (Integer.parseInt(args[0]) < Integer.parseInt(args[1])) {
                //update limit variables
                lowLimit = Integer.parseInt(args[0]);
                highLimit = Integer.parseInt(args[1]);
            } else {
                lowLimit = Integer.parseInt(args[1]);
                highLimit = Integer.parseInt(args[0]);
            }
            //update flag
            valuesLimited = true;
        } catch (Exception exc) {}

        //create a Language object and a map (as an ArrayList) to store it into
        Language lang;
        List<Language> map = new ArrayList<>();
        
        try  {
            //this characted divides title and count in the external .txt file
            char delimiter = ';';
            //prepare a flag variable for parsing
            boolean delimiterPassed = false;
            char currChar;
            String languageTitle = "";
            String languageCount = "";
            int languageCountInt;
            //read .txt file line by line
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                 //System.out.println(line);
                for (int i = 0; i < line.length(); i++) {
                    currChar = line.charAt(i);
                    if (currChar == delimiter) {
                        delimiterPassed = true;
                    }
                    //each line is written in title-delimiter-count order
                    if (delimiterPassed) {
                        languageCount += currChar;
                    } else {
                        languageTitle += currChar;
                    }
                }

                //exclude empty characters from both strings
                languageTitle = languageTitle.trim();
                //also exclude the delimiter for languageCount
                languageCount = languageCount.substring(1).trim();

                //create a new Language object and store it in the map
                lang = new Language();
                lang.setTitle(languageTitle);
                languageCountInt = Integer.parseInt(languageCount);
                lang.setCount(languageCountInt);
                //if limiting values are passed as arguments, check whether should Language object be added to the map
                if (!valuesLimited ||
                        (valuesLimited && languageCountInt >= lowLimit && languageCountInt <= highLimit)) {
                    map.add(lang);
                }

                //reset for next iteration
                languageTitle = "";
                languageCount = "";
                delimiterPassed = false;
           }

    } catch (IOException exc) {
        System.out.println(exc.getMessage());
    }
        
    return map;
    
    }
    
}

class Language implements Comparable<Language> {
    private String title;
    private int count;

    //getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /*
    For the purposes of sorting Language objects, this method will define
    the compare value (count).
    */
    @Override
    public int compareTo(Language t) {
        return Integer.compare(this.count, t.count);
    }

    /*
    This method will define how will the Language object be printed out
    in console.
    */
    @Override
    public String toString() {
        return (this.title + "; " + this.count);
    }
    
}