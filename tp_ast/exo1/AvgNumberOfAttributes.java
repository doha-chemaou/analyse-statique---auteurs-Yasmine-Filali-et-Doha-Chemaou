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
//import java.util.regex.Pattern;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class AvgNumberOfAttributes {
    File folder;
    //List<String> classes = new Number_of_classes(folder).classes;
    Hashtable<String,List<String>> classe_attributes = new Hashtable<>();
    List<String> attributes = new ArrayList<>();
    public AvgNumberOfAttributes(File folder){
        this.folder = folder;
    }

    public int total_number_of_attributes_in_all_classes(List classes) throws IOException{
        int[] total = {0};
        String class_name = "";
        
        for (File file : folder.listFiles()) {
            attributes = new ArrayList<>();
            String[] s = file.toString().split("\\\\");
            
            String class_name_java = s[s.length-1];
            class_name = class_name_java.substring(0,class_name_java.length()-5);
    
			Path path = Paths.get(file.toString());
			String string_file = Files.readString(path, StandardCharsets.US_ASCII);
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setSource(string_file.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);	
			final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

			cu.accept(new ASTVisitor() {
				Set names = new HashSet();
				public boolean visit(FieldDeclaration node) {//VariableDeclarationFragment
                    VariableDeclarationFragment variable=(VariableDeclarationFragment)node.fragments().get(0);
                    SimpleName name = variable.getName();//.getIdentifier();//getFullyQualifiedName()
					//SimpleName name = node;
					this.names.add(name.getIdentifier());
                    total[0]++;
                    System.out.println("WAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                    System.out.println(node.getType().toString());
                    if(classes.contains(node.getType().toString()))
                        IntermediateFunctions.type_attribute_or_parameter.add(name.toString());
                    attributes.add(name.getIdentifier());//node.toString());
					return false; // do not continue to avoid usage info
				}
			});
            classe_attributes.put(class_name,attributes);

		}
        
        return total[0];
    }

    public int avg(Number_of_classes n) throws IOException{
        int avg = 0;    
        int sum = total_number_of_attributes_in_all_classes(n.classes);
        int number_of_classes = n.total_number_of_classes();
        if(number_of_classes!=0)
            avg = sum/number_of_classes;
        return avg;
    }
}

