package exo1;

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
    int sum = 0;
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

    public int number_of_lines_in_app(File folder){
        
        for (File file : folder.listFiles()) {
        	String[] s = file.toString().split("\\\\");
            
        	String class_name_java = s[s.length-1];
            if(file.isFile() && class_name_java.endsWith(".java")) {
            	sum+=countLine(file.toString());
            }
            else {
            	if(file.isDirectory())
            		number_of_lines_in_app(new File(folder+"\\"+class_name_java));
            }
        }
        num = sum;
        return sum ;
    }
    
}
