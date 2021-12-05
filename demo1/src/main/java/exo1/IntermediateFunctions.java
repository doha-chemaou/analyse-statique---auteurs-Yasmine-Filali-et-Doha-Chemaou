package exo1;

/* Auteurs : 
	Yasmine FILALI 
	DohA CHEMAOU
*/
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class IntermediateFunctions {
    static int pourcentage;
    // works also for attributes 
    public static LinkedHashMap<String,List<String>> sort_classes_based_on_number_of_methods_or_attributes(Hashtable<String,List<String>> methods_of_classes){
        // not sorted yet
        Hashtable<String,List<String>> actual_hashtable = methods_of_classes;
        Collection<String> classes = actual_hashtable.keySet();
        List<String> l_classes = new ArrayList<String>(classes);

        Collection<List<String>> methods_by_class = actual_hashtable.values();
        List<List<String>> l_methods_by_class = new ArrayList<List<String>>(methods_by_class);

        List<Integer> number_of_methods_by_class = new ArrayList<>();
        int size;
        for (int i = 0; i < l_methods_by_class.size() ; i++){
            size = l_methods_by_class.get(i).size();
            number_of_methods_by_class.add(size);
        }
        List<Integer>sorted_number_of_methods_by_class=number_of_methods_by_class.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        
        int replacement = sorted_number_of_methods_by_class.get(0)+1;
        int number_of_meth = 0;
        int index = 0;
        String actual_class;
        List<String> actual_methods;
        Hashtable<Integer, Hashtable<String,List<String>>> sorted_hashtable = new Hashtable<Integer,Hashtable<String,List<String>>>();
        Hashtable<String, List<String>> element_to_put_in_sorted_hashtable = new Hashtable<String,List<String>>();
        while(!l_methods_by_class.isEmpty() && !l_classes.isEmpty()){
            number_of_meth = l_methods_by_class.get(0).size();
            index = sorted_number_of_methods_by_class.indexOf(number_of_meth);
            actual_methods = l_methods_by_class.get(0);
            actual_class = l_classes.get(0);
            element_to_put_in_sorted_hashtable.put(actual_class,actual_methods);
            sorted_hashtable.put(index,element_to_put_in_sorted_hashtable);

            element_to_put_in_sorted_hashtable = new Hashtable<>();
            sorted_number_of_methods_by_class.set(index,replacement);
            l_methods_by_class.remove(0);
            l_classes.remove(0);
 
        }
        LinkedHashMap<String , List<String>> res_sorted_hashtable = new LinkedHashMap<String,List<String>>();
        String key;
        List<String> element;
        int i = 0;
        for(Map.Entry entry: sorted_hashtable.entrySet()){
            for(Map.Entry entry1: sorted_hashtable.get(i).entrySet()){
                key = entry1.getKey().toString(); 
                element = (List) (entry1.getValue());
                res_sorted_hashtable.put(key,element);
            }
            i++;
        }
        return res_sorted_hashtable;
    }
    static int percent(int num, Scanner sc) throws IOException{
        pourcentage = sc.nextInt();
        double d_pourcentage = 0.0;
        if (pourcentage>1) d_pourcentage = (double)pourcentage/(double)100;
        int number_of_classes = num;
        double d_classes_to_display = d_pourcentage*number_of_classes;

        double d1,d2;
        d1 = d_classes_to_display - (double)((int)d_classes_to_display);
        d2 = d_classes_to_display - (double)((int)d_classes_to_display+1);
        int classes_to_display;
        if (d1 > d2){
            classes_to_display = (int)d_classes_to_display+1;
        }
        else{
            classes_to_display = (int)d_classes_to_display;
        }
        if (classes_to_display<1) classes_to_display = 1;
        if (classes_to_display>number_of_classes) classes_to_display = number_of_classes;
        return classes_to_display;
    }
    static int percent_(TotalNumberOfMethods t, Scanner sc) throws IOException{
        pourcentage = sc.nextInt();
        double d_pourcentage = 0.0;
        if (pourcentage>1) d_pourcentage = (double)pourcentage/(double)100;
        int number_of_classes = t.total_number_of_methods_in_all_classes(Main.folder);
        double d_classes_to_display = d_pourcentage*number_of_classes;

        double d1,d2;
        d1 = d_classes_to_display - (double)((int)d_classes_to_display);
        d2 = d_classes_to_display - (double)((int)d_classes_to_display+1);
        int classes_to_display;
        if (d1 > d2){
            classes_to_display = (int)d_classes_to_display+1;
        }
        else{
            classes_to_display = (int)d_classes_to_display;
        }
        if (classes_to_display<1) classes_to_display = 1;
        if (classes_to_display>number_of_classes) classes_to_display = number_of_classes;
        return classes_to_display;
    }

    public static void prints_classes(LinkedHashMap<String,List<String>> linkedhash, String string){
        List<String> classes;
        for (Map.Entry<String, List<String>> element : linkedhash.entrySet()){
            System.out.println("**********************************************");
            System.out.print("La classe " + element.getKey()+ " a : ");
            classes = element.getValue();
            if(classes.size()==0) System.out.print("0 "+ string + "\n");
            for (int i = 0; i < classes.size(); i++){
                System.out.print(classes.get(i));
                if (i+1 == classes.size()){
                    System.out.print("\n");
                }
                else{
                    System.out.print(" , ");
                }
            }
        }
    }

    public static LinkedHashMap<String,Integer> sort_methods_based_on_number_of_lines(AvgNumberOfLinesByMethod a){
        Hashtable<String,Integer> lines_of_methods = a.method_lines;
        LinkedHashMap<String,Integer> sorted_lines_of_methods = new LinkedHashMap<>();
        ArrayList<Map.Entry<String, Integer>> a_lines_of_methods = new ArrayList(lines_of_methods.entrySet());
        Collections.sort(a_lines_of_methods, new Comparator<Map.Entry<?, Integer>>(){

         public int compare(Map.Entry<?, Integer> o1, Map.Entry<?, Integer> o2) {
            return o2.getValue().compareTo(o1.getValue());
        }});
        for (int i = 0; i < a_lines_of_methods.size();i++){
            int num = a_lines_of_methods.get(i).getValue();
            String meth = a_lines_of_methods.get(i).getKey(); 
            sorted_lines_of_methods.put(meth,num);
        }
        return sorted_lines_of_methods;
    }
    public static void prints_methods(LinkedHashMap<String,Integer> linkedhash){
        for (Map.Entry<String, Integer> element : linkedhash.entrySet()){
            System.out.println("**********************************************");
            System.out.println("La methode \n" + element.getKey()+ "\ninclut : " + element.getValue() + " lignes\n");
        }
    }
    
}
