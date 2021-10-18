/* Auteurs : 
	Yasmine FILALI 
	DohA CHEMAOU
*/
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Classes_having_more_than_X_meths {
    public int number = 0;

    public List<String> classes(Scanner sc,XpercentOfClassesThatHaveMostMethods x){
        List<String> classes = new ArrayList<String>();
        LinkedHashMap<String,List<String>> sorted_methods_by_class = x.sorted_methods_by_class;

            number = sc.nextInt();
            for (Map.Entry<String, List<String>> element : sorted_methods_by_class.entrySet()){
                if (element.getValue().size() >= number){
                    classes.add(element.getKey());
                }   
                else {
                    break;
                }
            }
        return classes;
    }
}
