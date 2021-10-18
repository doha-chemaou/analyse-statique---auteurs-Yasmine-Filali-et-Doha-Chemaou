/* Auteurs : 
	Yasmine FILALI 
	DohA CHEMAOU
*/
import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect.Type;
import java.lang.instrument.ClassDefinition;
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

import javax.management.openmbean.SimpleType;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.eclipse.jdt.internal.core.search.matching.MatchLocatorParser.ClassAndMethodDeclarationVisitor;
import org.eclipse.jdt.internal.core.search.matching.MatchLocatorParser.ClassButNoMethodDeclarationVisitor;

import jdk.nashorn.api.tree.ClassDeclarationTree;

public class Number_of_classes {
    File folder;
    static int number = 0;
    List<String> classes = new ArrayList<>();
    public Number_of_classes(File folder){
        this.folder = folder;
    }
    

    public int total_number_of_classes_prime(){
        int total = 0;
        for (File file : folder.listFiles()) {
            String[] s = file.toString().split("\\\\");
            String class_name_java = s[s.length-1];
            String class_name = class_name_java.substring(0,class_name_java.length()-5);
            total++;
            classes.add(class_name);
        }
        return total;
    }
    //ASTNode
    public int total_number_of_classes() throws IOException{
        int[] total={0};
        for (File file : folder.listFiles()) {
            //methods = new ArrayList<>();
            //attributes = new ArrayList<>();
			//System.out.println(file.toString().contains("\\\\"));
            //String s1 = file.toString().replaceAll("\\\\","!");
            //System.out.println(s1);
            String[] s = file.toString().split("\\\\");
            //String[] s2 = s1.split(Pattern.quote("\\\\"));
            
            String class_name_java = s[s.length-1];
            String class_name = class_name_java.substring(0,class_name_java.length()-5);
            //System.out.println(class_name);
    
			Path path = Paths.get(file.toString());
			String string_file = Files.readString(path, StandardCharsets.US_ASCII);
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setSource(string_file.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);	
			final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
	
			cu.accept(new ASTVisitor() {
				Set names = new HashSet();
				public boolean visit(TypeDeclaration node) {
                    //System.out.println("here "+node);
					SimpleName name = node.getName();
                    //String name = node.getName();
					//this.names.add(name.getIdentifier());
                    total[0]++;
                    //methods.add(name.toString());
					//System.out.println("Declaration of '"+name+"' at line"+cu.getLineNumber(name.getStartPosition()));
					return false; // do not continue to avoid usage info
				}
			});
            classes.add(class_name);
		}
        number=total[0];
        return total[0];
    }

}
