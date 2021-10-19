package exo1;

/* Auteurs : 
	Yasmine FILALI 
	DohA CHEMAOU
*/
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class XpercentOfClassesThatHaveMostMethods{
    File folder ; 
    public LinkedHashMap<String,List<String>> sorted_methods_by_class;
    List<String> classes = new ArrayList<>();

    
    public XpercentOfClassesThatHaveMostMethods(File folder){
        this.folder = folder;
    }

    public List<String> xpercent(TotalNumberOfMethods t,Scanner sc,Number_of_classes num) throws IOException{
        sorted_methods_by_class = IntermediateFunctions.sort_classes_based_on_number_of_methods_or_attributes(t.classe_methods);
        System.out.print("entrez un pourcentage pour savoir quelles sont les classes qui ont le plus grand nombre de méthodes \n");

        //Hashtable<String,List<String>> classes = a.classe_attributes;
        int percent = IntermediateFunctions.percent(num,sc);
        //System.out.println(percent);
        int index = 0;
        for (Map.Entry<String, List<String>> element : sorted_methods_by_class.entrySet()){
            if (index < percent){
                //System.out.print(element.getKey()+ (index+1<percent? " , " : "\n\n"));
                classes.add(element.getKey());
            }
            if (index >= percent)
                break; 
            index++;
        }
        return classes ;
    }
}