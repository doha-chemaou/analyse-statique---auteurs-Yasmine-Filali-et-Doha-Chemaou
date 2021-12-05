package exo1;

import java.io.BufferedReader;
/* Auteurs : 
	Yasmine FILALI 
	DohA CHEMAOU
*/
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

 
public class Main {
	public static String projectSourcePath;
	static File folder;

	public static void main(String args[]) throws IOException{
		
		System.out.println("please enter the path to the java app you want to analyze (make sure it resembles either this : directory1/src/ or this directory\src\\ ");		BufferedReader inputReader;
		inputReader = new BufferedReader(new InputStreamReader(System.in));
		projectSourcePath = inputReader.readLine();
		folder = new File(projectSourcePath);
		Scanner sc = new Scanner(System.in);
		//------------------------------------------------------ total number of classes
		System.out.println("\n_________________________________________________________________________Q1 : nombre de classes \n");
		Number_of_classes num_of_classes = new Number_of_classes(folder);
		
		int total = num_of_classes.total_number_of_classes(folder);
	      
		System.out.println("le nombre total des classes : "+total+"\n");
		List<String> classes = num_of_classes.classes;
		System.out.print("Voici les classes présentes dans notre application : ");
		for (int i = 0; i < classes.size();i++){
			System.out.print(classes.get(i)+(i+1==classes.size()?"\n":" , "));
		}

		// -----------------------------------------------------------------------------total number of packages
		System.out.println("\n_________________________________________________________________________Q4 : nombre des packages\n");
		NumberOfPackages num_packages = new NumberOfPackages(folder);
		int num = num_packages.total_number_of_packages(folder);
		List<String> packages = num_packages.packages;
		System.out.println("Le nombre de packages sur l'ensemble des classes : "+num);
		System.out.print("\nvoici les packages présents sur l'ensemble de l'application : ");
		int size = packages.size();
		for (int i = 0 ; i < size ; i++)
			System.out.print(packages.get(i)+((i+1==size)?"\n":" , "));

		// ------------------------------------------------------ avg number of attributes
		System.out.println("\n_________________________________________________________________________Q7 : nombre moyen d'attributs\n");
		AvgNumberOfAttributes avg_num_of_att = new AvgNumberOfAttributes(folder);
		int total_ = 0;
		total_ = avg_num_of_att.total_number_of_attributes_in_all_classes(folder);
		System.out.println("le nombre total d'attributs dans toutes les classes : "+ total_+"\n");
		
		System.out.println("Le nombre moyen d'attributs par classe : "+avg_num_of_att.avg(num_of_classes));
		

		//------------------------------------------------------------------------------ total number of methods
		System.out.println("\n_________________________________________________________________________Q3 : nombre total des méthodes\n");
		TotalNumberOfMethods total_of_methods = new TotalNumberOfMethods(folder);
		num = total_of_methods.total_number_of_methods_in_all_classes(folder);
		int num______ = num;
		System.out.println("Le nombre total de méthodes sur tout l'ensemble des classes : "+ num);

		//------------------------------------------------------------------------------ nombre moyen de méthodes dans une classe
		System.out.println("\n_________________________________________________________________________Q5 : nombre moyen des méthodes dans une classe\n");
		AvgNumberOfMethods avg_num_of_meth = new AvgNumberOfMethods(folder);
		System.out.println("Le nombre moyen de méthodes par classe : "+avg_num_of_meth.avg(total,num______));

		

		
		//------------------------------------------------------------------------------ 10% des classes qui ont le plus de méthodes :
		System.out.println("\n_________________________________________________________________________Q8 : x% des classes qui ont le plus de méthodes\n");
		XpercentOfClassesThatHaveMostMethods xpercent_m = new XpercentOfClassesThatHaveMostMethods(folder);
		List<String>classes_methods = xpercent_m.xpercent(total_of_methods ,sc,total);
		System.out.print("Les "+IntermediateFunctions.pourcentage +"% des classes qui ont le plus de méthodes : ");
		size = classes_methods.size();
		for (int i = 0 ; i < classes_methods.size() ; i++)
			System.out.print(classes_methods.get(i)+((i+1==size)?"\n":" , "));
		
		//-------------------------------------------------------------- 10% des classes qui possèdent le plus d'attributs 
		System.out.println("\n_________________________________________________________________________Q9 : x% des classes qui ont le plus d'attributs\n");
		XpercentOfClassesThatHaveMostAttributes xpercent = new XpercentOfClassesThatHaveMostAttributes(folder);
		List<String>classes_attributes = xpercent.xpercent(avg_num_of_att ,sc,total);
		System.out.print("Les "+IntermediateFunctions.pourcentage +"% des classes qui ont le plus d'attrbiuts : ");
		size = classes_attributes.size();
		for (int i = 0 ; i < classes_attributes.size() ; i++)
			System.out.print(classes_attributes.get(i)+((i+1==size)?"\n":" , "));
		
		
		// les classes qui font partie en meme temps aux deux catégories précédentes

		System.out.println("\n_________________________________________________________________________Q10 : les classes qui font partie en meme temps aux deux catégories précédentes\n");
		System.out.print("les classes qui font partie des 2 catégories précédentes : ");
		ClassesThatAreInBoth classesTaib = new ClassesThatAreInBoth();
		classes = classesTaib.classes(classes_attributes,classes_methods);
		for (int i = 0 ; i < classes.size();i++){
			System.out.print(classes.get(i)+(i+1==classes.size()?"\n":" , "));
		}

		
		// les classes qui ont plus que x méthodes 
		System.out.println("\n_________________________________________________________________________Q11 : les classes qui ont plus que x méthdodes\n");
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
		System.out.println("\n_________________________________________________________________________Q1 : nombre de lignes de l'application\n");
		NumberOfLines num_lines = new NumberOfLines(folder);
		int num_ = num_lines.number_of_lines_in_app(folder);
		System.out.println("Le nombre de lignes dans toute l'application : "+num_);
		// nombre moyen de lignes par méthode
		System.out.println("\n_________________________________________________________________________Q6 : nombre moyen de ligne par méthode\n");
		AvgNumberOfLinesByMethod n = new AvgNumberOfLinesByMethod(folder);
		int total_number_of_lines_for_meth = n.number_of_lines_in_all_methods(num_of_classes,folder);
		int avg = n.avg_linese_meth(num______);
		System.out.println("le nombre total des lignes consacrées aux méthodes sur toute l'application : "+ total_number_of_lines_for_meth);
		System.out.println("le nombre moyen de lignes dédiées aux méthodes dans chaque classe : "+ avg);
		// x% des méthodes ayant le plus de lignes 
		System.out.println("\n_________________________________________________________________________Q12 : x% des méthodes ayant le plus de lignes\n");
		XpercentOfMethThatHaveMostLines xpercent__m = new XpercentOfMethThatHaveMostLines();
		List<String> meth_lines = xpercent__m.xpercent(n,sc,total_of_methods);
        System.out.println("Les "+IntermediateFunctions.pourcentage +"% des methodes qui ont le plus de lignes :");
		  
		size = meth_lines.size();
		for (int i = 0 ; i < size ; i++)
			System.out.println(meth_lines.get(i));

		
		// nombre max des paramètres 
		System.out.println("\n_________________________________________________________________________Q13 : le nombre max des paramètres\n");
		System.out.println("la méthode ayant le nombre max de paramètres est " + n.meth_with_max_params +"\nElle appartient à la classe "+n.calss_with_meth_with_max_params+"\nLe nombre max de paramètres étant :"+n.maxParams);

		System.out.println("\n_________________________________________________________________________AFFICHAGE : méthodes - nombre de lignes\n");
		System.out.println("\nvoici un affichage de l'ensemble des méthodes et du nombre de lignes qu'elles occupent :");
		IntermediateFunctions.prints_methods(xpercent__m.sorted_meth_lines);
		System.out.println("\n_________________________________________________________________________AFFICHAGE : classe : méthodes\n");
		System.out.println("\nvoici un affichage de l'ensemble des classes et de leurs méthodes :");
		IntermediateFunctions.prints_classes(xpercent_m.sorted_methods_by_class, "méthodes");
		System.out.println("\n_________________________________________________________________________AFFICHAGE : classe - attributs\n");
		System.out.println("\nvoici un affichage de l'ensemble des classes et de leurs attributs (0 attributs quand la classe n'a aucun attribut) :");
		IntermediateFunctions.prints_classes(xpercent.sorted_attributes_by_class, "attributs");
		}
}