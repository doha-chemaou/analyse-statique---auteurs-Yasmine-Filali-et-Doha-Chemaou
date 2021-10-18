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
import java.util.List;
import java.util.Set;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PackageDeclaration;


public class NumberOfPackages {
    File folder;
    List<String> packages = new ArrayList<>();
    public NumberOfPackages(File folder){
        this.folder = folder;
    }

    //ASTNode
    public int total_number_of_packages() throws IOException{
        int[] total={0};
        //List<String> packages=new ArrayList<>();
        for (File file : folder.listFiles()) {
            String[] s = file.toString().split("\\\\");
            
            String class_name_java = s[s.length-1];
            String class_name = class_name_java.substring(0,class_name_java.length()-5);
    
			Path path = Paths.get(file.toString());
			String string_file = Files.readString(path, StandardCharsets.US_ASCII);
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setSource(string_file.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);	
			final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

			cu.accept(new ASTVisitor() {
				Set names = new HashSet();
				public boolean visit(PackageDeclaration node) {
					Name name = node.getName();
                    if (!packages.contains(name.toString())){
                        total[0]++;
                        packages.add(name.toString());
                    }
					return false; // do not continue to avoid usage info
				}
			});
		}
        return total[0];
    }
}
