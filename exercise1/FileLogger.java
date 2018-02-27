import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class FileLogger {
    private String pathToLogFile;
    private File textFile;
    private FileWriter fileWriter;

    public FileLogger(String pathToLogFile) {
        this.pathToLogFile = pathToLogFile;
    }

    public String getPathToLogFile() {
        return pathToLogFile;
    }

    public void setPathToLogFile(String pathToLogFile) {
        this.pathToLogFile = pathToLogFile;
    }

    public void writeToFile(String stockName, String stockPrice){
        try{

            textFile = new File(pathToLogFile);
            fileWriter = new FileWriter(textFile, true);
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date dateobj = new Date();
            String currentDate = df.format(dateobj).toString();
            String logToAdd = currentDate+";"+stockName+";"+stockPrice+"\n";
            // Write
            fileWriter.write(logToAdd);
            // "Fermeture" du FileWriter
            fileWriter.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FileLogger fileLogger = new FileLogger("text.csv");
        fileLogger.writeToFile("apple","100");
        fileLogger.writeToFile("apple","200");
        fileLogger.writeToFile("apple","300");
        fileLogger.writeToFile("apple","400");
        fileLogger.writeToFile("apple","500");
    }

}
