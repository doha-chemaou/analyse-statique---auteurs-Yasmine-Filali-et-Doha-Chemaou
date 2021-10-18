/* Auteurs : 
	Yasmine FILALI 
	DohA CHEMAOU
*/
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class XpercentOfMethThatHaveMostLines {// AvgNumberOfLinesByMethod method_lines
    LinkedHashMap<String,Integer> sorted_meth_lines;

    public List<String> xpercent(AvgNumberOfLinesByMethod a,Scanner sc,TotalNumberOfMethods num) throws IOException{
        sorted_meth_lines = IntermediateFunctions.sort_methods_based_on_number_of_lines(a);
        List<String> methods = new ArrayList<>();
        System.out.print("entrez un pourcentage pour savoir quelles sont les des méthodes qui ont le plus grand nombre d'attributs \n");
        int percent = IntermediateFunctions.percent_(num,sc);
        //System.out.println("XpercentOfMethThatHaveMostLines " + percent);
        int index = 0;
        for (Entry<String, Integer> element : sorted_meth_lines.entrySet()){
            if (index < percent){
                //System.out.print(element.getKey()+ (index+1<percent? " \n " : "\n\n"));
                methods.add(element.getKey());
            }
            if (index >= percent)
                break; 
            index++;
        }
        return methods;
    }
}
