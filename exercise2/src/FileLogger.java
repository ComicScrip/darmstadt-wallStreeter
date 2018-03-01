import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileLogger {
    private String pathToLogFile;
    private File textFile;
    private FileWriter fileWriter;

    public FileLogger(String pathToLogFile){
        this.pathToLogFile = pathToLogFile;
        textFile = new File(pathToLogFile);
        try {
            textFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*textFile.getParentFile().mkdirs();
        textFile.createNewFile();*/
    }

    public String getPathToLogFile() {
        return pathToLogFile;
    }

    public void setPathToLogFile(String pathToLogFile) {
        this.pathToLogFile = pathToLogFile;
    }

    //Writes the stockname and the stockprice to the logFile
    public void writeToFile(String stockName, String stockPrice){
        try{
            fileWriter = new FileWriter(textFile, true);
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date dateobj = new Date();
            String currentDate = df.format(dateobj).toString();
            String logToAdd = currentDate+";"+stockName+";"+stockPrice+"\n";
            // Write the logs to the logFile
            fileWriter.write(logToAdd);
            fileWriter.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Search the last-writed specified stock transaction
    public String searchLogFile(String actionName){
        //Input file which needs to be parsed
        String fileToParse = pathToLogFile;
        BufferedReader fileReader = null;


        //Delimiter used in CSV file
        final String DELIMITER = ";";
        try
        {
            String lineMatched ="";
            String line;
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(fileToParse));

            //Read the file line by line
            while ((line = fileReader.readLine()) != null)
            {
                //Get all tokens available in line
                String[] tokens = line.split(DELIMITER);

                //print the token if the transaction date is between the min and max date
                if((actionName.equals(tokens[1]))){
                    lineMatched = ("Date : " + tokens[0] + " Nom de l'action : " + tokens[1] + " Prix : " + tokens[2]);
                }
            }
            System.out.println(lineMatched);
            return lineMatched;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }



    //Search the specified stock's data between dateMin and dateMax
    public String searchLogFile( String dMin, String dMax, String actionName, boolean minimize){
        //Input file which needs to be parsed
        String fileToParse = pathToLogFile;
        BufferedReader fileReader = null;


        //Delimiter used in CSV file
        final String DELIMITER = ";";
        try
        {
            String linesMatched = "";
            String line = "";
            int minCounter = 0;  // Counter which will be incremented for each conforms lines

            //Create the file reader
            fileReader = new BufferedReader(new FileReader(fileToParse));

            //Read the file line by line
            while ((line = fileReader.readLine()) != null)
            {
                //Get all tokens available in line
                String[] tokens = line.split(DELIMITER);
                String date = (tokens[0].split(" "))[0];  // get date without hour


                DateFormat df = new SimpleDateFormat("dd/MM/yy");
                Date transactionDate = df.parse(date);
                Date dateMin = df.parse(dMin);
                Date dateMax = df.parse(dMax);

                //Affect the token if the transaction date is between the min and max date
                if((transactionDate.after(dateMin))&&(transactionDate.before(dateMax)) && (actionName.equals(tokens[1]))){
                    linesMatched += ("Date : " + tokens[0] + " Nom de l'action : " + tokens[1] + " Prix : " + tokens[2] + "\n");
                    //If the boolean minimize is set to true, the client only wants to read 10 entry
                    if(minimize) {
                        minCounter++;
                        if (minCounter == 10) break;
                    }
                }
            }
            return linesMatched;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
