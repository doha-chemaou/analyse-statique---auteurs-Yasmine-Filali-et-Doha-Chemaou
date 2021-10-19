package exo1;

/* Auteurs : 
	Yasmine FILALI 
	DohA CHEMAOU
*/
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class AvgNumberOfMethods {
    File folder;
    int avg = 0;
    public AvgNumberOfMethods(File folder){
        this.folder = folder;
    }
    public int avg(Number_of_classes n,int t) throws IOException{
        int avg = 0;
        int sum = t;
        //System.out.println(sum);
        int number_of_classes = n.total_number_of_classes();
        if(number_of_classes!=0)
            avg = sum/number_of_classes;
        //this.avg = avg;
        return avg;
    }
}
