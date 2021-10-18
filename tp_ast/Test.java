/* Auteurs : 
	Yasmine FILALI 
	DohA CHEMAOU
*/
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

 
public class Test {
	public static void main(String args[]) throws IOException{
		File folder = new File("..\\visitorDesignPattern\\src");
		Scanner sc = new Scanner(System.in);
		//------------------------------------------------------ total number of classes
		System.out.println("_________________________________________________________________________Q1 : nombre de classes ");
		Number_of_classes num_of_classes = new Number_of_classes(folder);
		int total = num_of_classes.total_number_of_classes();
		System.out.println("le nombre total des classes : "+total+"\n");
		//System.out.print("voici les noms des classes : ");
		List<String> classes = num_of_classes.classes;
		System.out.print("Voici les classes présentes dans notre application : ");
		for (int i = 0; i < classes.size();i++){
			System.out.print(classes.get(i)+(i+1==classes.size()?"\n":" , "));
		}

		// -----------------------------------------------------------------------------total number of packages
		System.out.println("_________________________________________________________________________Q4 : nombre des packages");
		NumberOfPackages num_packages = new NumberOfPackages(folder);
		int num = num_packages.total_number_of_packages();
		List<String> packages = num_packages.packages;
		System.out.println("Le nombre de packages sur l'ensemble des classes : "+num);
		System.out.print("\nvoici les packages présents sur l'ensemble de l'application : ");
		int size = packages.size();
		for (int i = 0 ; i < size ; i++)
			System.out.print(packages.get(i)+((i+1==size)?"\n":" , "));

		// ------------------------------------------------------ avg number of attributes
		System.out.println("_________________________________________________________________________Q7 : nombre moyen d'attributs");
		AvgNumberOfAttributes avg_num_of_att = new AvgNumberOfAttributes(folder);
		total = avg_num_of_att.total_number_of_attributes_in_all_classes();
		System.out.println("le nombre total d'attributs dans toutes les classes : "+ total+"\n");
		//avg_num_of_att.total_number_of_attributes_in_all_classes();
		//System.out.println(total+"\n");
		Hashtable<String,List<String>> classe_attributes = avg_num_of_att.classe_attributes;
		/*List<String> attr;
        for (Map.Entry<String, List<String>> classe_att : classe_attributes.entrySet()){
			attr = classe_att.getValue();
			int size = attr.size();
			System.out.print("La classe "+classe_att.getKey()+" a : ");
			System.out.print(size==0?"0 attributs \n":"");
			for (int i = 0 ; i < attr.size();i++){
				System.out.print(attr.get(i)+((i+1==size)?"\n":" , "));
			}

		}*/
		System.out.println("Le nombre moyen d'attributs par classe : "+avg_num_of_att.avg(num_of_classes));
	
		
		/*for (Map.Entry<String, List<String>> element : xpercent.sorted_attributes_by_class.entrySet()){
            System.out.println("**************\n");
            System.out.print(element.getKey()+ " : ");
            classes = element.getValue();
            if(classes.size()==0) System.out.println("\n");
            for (int i = 0; i < classes.size(); i++){
                System.out.print(classes.get(i));
                if (i+1 == classes.size()){
                    System.out.print("\n\n");
                }
                else{
                    System.out.print(" , ");
                }
            }
        }*/
		

		//------------------------------------------------------------------------------ total number of methods
		System.out.println("_________________________________________________________________________Q3 : nombre total des méthodes");
		TotalNumberOfMethods total_of_methods = new TotalNumberOfMethods(folder);
		num = total_of_methods.total_number_of_methods_in_all_classes();
		int num______ = num;
		System.out.println("Le nombre total de méthodes sur tout l'ensemble des classes : "+ num);

		//------------------------------------------------------------------------------ nombre moyen de méthodes dans une classe
		System.out.println("_________________________________________________________________________Q5 : nombre moyen des méthodes dans une classe");
		AvgNumberOfMethods avg_num_of_meth = new AvgNumberOfMethods(folder);
		System.out.println("Le nombre moyen de méthodes par classe : "+avg_num_of_meth.avg(num_of_classes,num______));

		

		
		//------------------------------------------------------------------------------ 10% des classes qui ont le plus de méthodes :
		System.out.println("_________________________________________________________________________Q8 : x% des classes qui ont le plus de méthodes");
		XpercentOfClassesThatHaveMostMethods xpercent_m = new XpercentOfClassesThatHaveMostMethods(folder);
		List<String>classes_methods = xpercent_m.xpercent(total_of_methods ,sc,num_of_classes);
		System.out.print("Les "+IntermediateFunctions.pourcentage +"% des classes qui ont le plus de méthodes : ");
		//System.out.println(classes_attributes);
		size = classes_methods.size();
		for (int i = 0 ; i < classes_methods.size() ; i++)
			System.out.print(classes_methods.get(i)+((i+1==size)?"\n":" , "));
		
		//-------------------------------------------------------------- 10% des classes qui possèdent le plus d'attributs 
		System.out.println("_________________________________________________________________________Q9 : x% des classes qui ont le plus d'attributs");
		XpercentOfClassesThatHaveMostAttributes xpercent = new XpercentOfClassesThatHaveMostAttributes(folder);
		List<String>classes_attributes = xpercent.xpercent(avg_num_of_att ,sc,num_of_classes);
		System.out.print("Les "+IntermediateFunctions.pourcentage +"% des classes qui ont le plus d'attrbiuts : ");
		//System.out.println(classes_attributes);
		size = classes_attributes.size();
		for (int i = 0 ; i < classes_attributes.size() ; i++)
			System.out.print(classes_attributes.get(i)+((i+1==size)?"\n":" , "));
		
		
		// les classes qui font partie en meme temps aux deux catégories précédentes

		System.out.println("_________________________________________________________________________Q10 : les classes qui font partie en meme temps aux deux catégories précédentes");
		System.out.print("les classes qui font partie des 2 catégories précédentes : ");
		ClassesThatAreInBoth classesTaib = new ClassesThatAreInBoth();
		classes = classesTaib.classes(classes_attributes,classes_methods);
		for (int i = 0 ; i < classes.size();i++){
			System.out.print(classes.get(i)+(i+1==classes.size()?"\n":" , "));
		}

		
		// les classes qui ont plus que x méthodes 
		System.out.println("_________________________________________________________________________Q11 : les classes qui ont plus que x méthdodes");
		System.out.print("Entrez un nombre , les classes ayant au moins ce nombre de méthodes seront affichées :");
		Classes_having_more_than_X_meths c = new Classes_having_more_than_X_meths();
		List<String> classes_ = c.classes(sc,xpercent_m);
		System.out.print("les classes qui ont au moins "+ c.number +" méthodes : ");
		if (classes_.size()==0) System.out.println(" ...\nIl n'y a pas de classes dont le nombre de méthodes dépasse le nombre voulu\n");
		else{
			for (int i = 0; i < classes_.size();i++){
				System.out.print(classes_.get(i)+(i+1==classes_.size()?"\n":" , "));
			}
		}
		
		// nombre de lignes de l'app 
		System.out.println("_________________________________________________________________________Q1 : nombre de lignes de l'application");
		NumberOfLines num_lines = new NumberOfLines(folder);
		int num_ = num_lines.number_of_lines_in_app();
		System.out.println("Le nombre de lignes dans toute l'application : "+num_);
		// nombre moyen de lignes par méthode
		System.out.println("_________________________________________________________________________Q6 : nombre moyen de ligne par méthode");
		AvgNumberOfLinesByMethod n = new AvgNumberOfLinesByMethod(folder);
		int total_number_of_lines_for_meth = n.number_of_lines_in_all_methods(num_of_classes);
		int avg = n.avg_linese_meth(num______);
		System.out.println("le nombre total des lignes consacrées aux méthodes sur toute l'application : "+ total_number_of_lines_for_meth);
		System.out.println("le nombre moyen de lignes dédiées aux méthodes dans chaque classe : "+ avg);
		// x% des méthodes ayant le plus de lignes 
		System.out.println("_________________________________________________________________________Q12 : x% des méthodes ayant le plus de lignes");
		XpercentOfMethThatHaveMostLines xpercent__m = new XpercentOfMethThatHaveMostLines();
		List<String> meth_lines = xpercent__m.xpercent(n,sc,total_of_methods);
        System.out.println("Les "+IntermediateFunctions.pourcentage +"% des methodes qui ont le plus de lignes :");
		  
		size = meth_lines.size();
		//System.out.println("size __ Test : " + size);
		for (int i = 0 ; i < size ; i++)
			System.out.println(meth_lines.get(i));//.get(i)+((i+1==size)?"\n":" , "));
		/*for (int i = 0 ; i < classes.size();i++){
			System.out.print(classes.get(i)+(i+1==classes.size()?"\n":" , "));
		}*/
		//IntermediateFunctions.prints_methods(xpercent__m.sorted_meth_lines);
		
		// nombre max des paramètres 
		System.out.println("_________________________________________________________________________Qu13 : le nombre max des paramètres");
		System.out.println("la méthode ayant le nombre max de paramètres est " + n.meth_with_max_params +"\nElle appartient à la classe "+n.calss_with_meth_with_max_params+"\nLe nombre max de paramètres étant :"+n.maxParams);

		System.out.println("_________________________________________________________________________AFFICHAGE");
		System.out.println("\nvoici un affichage de l'ensemble des méthodes et du nombre de lignes qu'elles occupent :");
		IntermediateFunctions.prints_methods(xpercent__m.sorted_meth_lines);
		System.out.println("_________________________________________________________________________AFFICHAGE");
		System.out.println("\nvoici un affichage de l'ensemble des classes et de leurs méthodes :");
		IntermediateFunctions.prints_classes(xpercent_m.sorted_methods_by_class, "méthodes");
		System.out.println("_________________________________________________________________________AFFICHAGE");
		System.out.println("\nvoici un affichage de l'ensemble des classes et de leurs attributs (0 attributs quand la classe n'a aucun attribut) :");
		IntermediateFunctions.prints_classes(xpercent.sorted_attributes_by_class, "attributs");
		}
}