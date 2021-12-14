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
import java.util.Arrays;
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
public class AvgNumberOfLinesByMethod { 
    File folder ;
    int maxParams = 0;
    String calss_with_meth_with_max_params = "";
    String meth_with_max_params = "";
    Hashtable<String,Integer> method_lines = new Hashtable<String,Integer>();
    int number_of_lines_for_meth = 0;
    public AvgNumberOfLinesByMethod(File folder){
        this.folder = folder;
    }
    
 
    public int number_of_lines_in_all_methods(Number_of_classes n,File folder) throws IOException {
        int j = 0;
        for (File file : folder.listFiles()) {
        	String[] s = file.toString().split("\\\\");
            
        	String class_name_java = s[s.length-1];
            if(file.isFile() && class_name_java.endsWith(".java") && n.classes.size()>0 && j < n.classes.size()) {
            final String class_name = n.classes.get(j);
            Path path = Paths.get(file.toString());
			String string_file = Files.readString(path, StandardCharsets.US_ASCII);
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setSource(string_file.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);	
			final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

			cu.accept(new ASTVisitor() {
				Set names = new HashSet();
				public boolean visit(MethodDeclaration node) {
                    String methode = node.toString();
                    SimpleName name = node.getName();

                    String[] lines = methode.split("\n");
                    List<String> m = Arrays.asList(lines);
                    int number = m.size();
                    String meth_name = "";// to change
                    int i = 0;
                    while(i < number){
                        if(m.get(i).contains(name.toString()) && m.get(i).contains("(")){
                            meth_name = m.get(i);
                            if(m.get(i).contains("@Override")){
                                String[] parts = meth_name.split(" ");
                                String[] meth = Arrays.copyOfRange(parts, 1, parts.length);
                                meth_name = String.join(" ", meth);
                            } 
                            

                            method_lines.put(class_name+" : "+meth_name,number);
                            int i_ = meth_name.indexOf("("),j_ = meth_name.indexOf(")");
                            String s_params = meth_name.substring(i_+1,j_);
                            String[] params = s_params.split(",");
                            int len = params.length;
                            if(maxParams < len){
                                maxParams = len; 
                                calss_with_meth_with_max_params = class_name; 
                                meth_with_max_params = meth_name;
                            }
                            break;
                        }
                        i++;
                    }
                    
                    number_of_lines_for_meth+=m.size();                    
					return false; // do not continue to avoid usage info
				}
			});
            j++;
            }
            else {
            	if(file.isDirectory())
            		number_of_lines_in_all_methods(n,new File(folder+"\\"+class_name_java));
            }
        }

        return number_of_lines_for_meth;
	}

    public int avg_linese_meth(int n) {
    	int returned_value = 0;
        int nombre_de_methodes = n;
        if (nombre_de_methodes!=0)
        	returned_value = number_of_lines_for_meth/ nombre_de_methodes; 
        return returned_value;
    }

    
}
