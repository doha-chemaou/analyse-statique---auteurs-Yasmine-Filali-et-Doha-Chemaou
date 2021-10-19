package exo2;
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
//import org.graalvm.compiler.nodes.java.NewArrayNode;

public class GrapheAppel {
    File folder ; 
	List<String> methodsNames = new ArrayList<>();

    public GrapheAppel(File folder){
        this.folder = folder;
    }

    public void graphe_appel_par_classe() throws IOException{
		// class name , method's names , entrees, sorties(liste des méthodes appelées par cette methode : nom methode , type statique de l'objet ) , 
			String class_name = "";
        for (File file : folder.listFiles()) {
            methodsNames = new ArrayList<>();
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
                    methodsNames.add(name.toString());
					
					return false; // do not continue to avoid usage info
				}
                /*public boolean visit(Block node) {
					SimpleName name = node.getName();
					this.names.add(name.getIdentifier());
					return false; // do not continue to avoid usage info
				}*/
			});
		}
    }
}
