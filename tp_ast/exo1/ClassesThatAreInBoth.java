package exo1;

/* Auteurs : 
	Yasmine FILALI 
	DohA CHEMAOU
*/
import java.util.ArrayList;
import java.util.List;

public class ClassesThatAreInBoth {
    
    List<String> classes(List<String> a, List<String> m){
        List<String> classes_ = new ArrayList<>();
        int size_a = a.size();
        int size_m = m.size();
        int size_ = Math.min(size_a,size_m);
        //System.out.print(size_ + " ");
        for (int i = 0 ; i < size_ ; i++){
            if(size_a == size_){
                String element = a.get(i);
                if(!classes_.contains(element) && m.contains(element)){
                    classes_.add(element);
                }
            }
            else {
                String element = m.get(i);
                if(!classes_.contains(element) && a.contains(element)){
                    classes_.add(element);
                }
            }

            }
        return classes_;
    }
}
