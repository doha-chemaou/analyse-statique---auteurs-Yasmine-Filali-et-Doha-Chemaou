/* Auteurs : 
	Yasmine FILALI 
	DohA CHEMAOU
*/
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NumberOfLines {
    File folder ; 
    static int num = 0;
    public NumberOfLines(File folder){
        this.folder = folder;
    }
    
    public long countLine(String fileName) {
        Path path = Paths.get(fileName);
        long lines = 0;
        try {
            lines = Files.lines(path).count();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public int number_of_lines_in_app(){
        int sum = 0;
        for (File file : folder.listFiles()) {
            sum+=countLine(file.toString());
        }
        num = sum;
        return sum ;
    }
    
}
