package exo1;

/* Auteurs : 
	Yasmine FILALI 
	DohA CHEMAOU
*/
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class Number_of_classes {
	static public  List<FileFilter> textFilefilter = new ArrayList<>();;
	static int total=0;
    String class_name;
	String string_file;

    File folder;
    List<String> classes = new ArrayList<>();
    public Number_of_classes(File folder){
        this.folder = folder;
    }
    
    //ASTNode
    public int total_number_of_classes(File folder) throws IOException{
        classes = new ArrayList<>();
        for (File file : folder.listFiles()) {
        	String[] s = file.toString().split("\\\\");

        	String class_name_java = s[s.length-1];
            if(file.isFile() && class_name_java.endsWith(".java")) {
            	class_name = class_name_java.substring(0,class_name_java.length()-5);
    			Path path = Paths.get(file.toString());
    			String string_file = Files.readString(path, StandardCharsets.US_ASCII);
    			ASTParser parser = ASTParser.newParser(AST.JLS3);
    			parser.setSource(string_file.toCharArray());
    			parser.setKind(ASTParser.K_COMPILATION_UNIT);	
    			final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
    			cu.accept(new ASTVisitor() {
    				Set names = new HashSet();
    				public boolean visit(TypeDeclaration node) {
    					SimpleName name = node.getName();
                        total++;
                        classes.add(class_name);

    					return false; // do not continue to avoid usage info
    				}
    			});
            }
            else {
            	if(file.isDirectory())
            		total_number_of_classes(new File(folder+"\\"+class_name_java));
            }
		}
        return total;
    }
    
}
