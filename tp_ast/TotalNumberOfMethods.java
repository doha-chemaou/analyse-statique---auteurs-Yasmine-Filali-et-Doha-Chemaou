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
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
//import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class TotalNumberOfMethods {
    File folder;
    private int[] total = {0};
    Hashtable<String,List<String>> classe_methods = new Hashtable<>();
    List<String> methods = new ArrayList<>();
    public TotalNumberOfMethods(File folder){
        this.folder = folder;
    }

    public int total_number_of_methods_in_all_classes() throws IOException{
        total[0]=0;
        String class_name = "";
        
        for (File file : folder.listFiles()) {
            methods = new ArrayList<>();
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
				public boolean visit(MethodDeclaration node) {
					SimpleName name = node.getName();
					this.names.add(name.getIdentifier());
                    total[0]++;
                    methods.add(name.toString());
					return false; // do not continue to avoid usage info
				}
			});
            classe_methods.put(class_name,methods);
		}
        return total[0];
    }
}