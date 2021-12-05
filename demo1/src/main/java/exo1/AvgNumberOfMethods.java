package exo1;

/* Auteurs : 
	Yasmine FILALI 
	DohA CHEMAOU
*/
import java.io.File;
import java.io.IOException;


public class AvgNumberOfMethods {
    File folder;
    int avg = 0;
    public AvgNumberOfMethods(File folder){
        this.folder = folder;
    }
    public int avg(int n,int t) throws IOException{
        int avg = 0;
        int sum = t;
        int number_of_classes = n;
        if(number_of_classes!=0)
            avg = sum/number_of_classes;
        return avg;
    }
}
