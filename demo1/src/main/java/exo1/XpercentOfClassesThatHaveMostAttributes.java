package exo1;

/* Auteurs : 
	Yasmine FILALI 
	DohA CHEMAOU
*/
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class XpercentOfClassesThatHaveMostAttributes{
    File folder ; 
    public LinkedHashMap<String,List<String>> sorted_attributes_by_class;
    List<String> classes = new ArrayList<>();

    
    public XpercentOfClassesThatHaveMostAttributes(File folder){
        this.folder = folder;
    }

    public List<String> xpercent(AvgNumberOfAttributes a,Scanner sc,int num) throws IOException{
        sorted_attributes_by_class = IntermediateFunctions.sort_classes_based_on_number_of_methods_or_attributes(a.classe_attributes);
        System.out.print("entrez un pourcentage pour savoir quelles sont les des classes qui ont le plus grand nombre d'attributs \n");

        //Hashtable<String,List<String>> classes = a.classe_attributes;
        int percent = IntermediateFunctions.percent(num,sc);

        //System.out.println(percent);
        int index = 0;
        for (Map.Entry<String, List<String>> element : sorted_attributes_by_class.entrySet()){
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