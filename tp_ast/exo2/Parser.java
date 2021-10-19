package exo2;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.internal.utils.FileUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class Parser {
	
	//public static final String projectPath = "C:\\mes_dossiers\\S3\\913 - evolution et restructuration des modeles\\TP2\\partie 2\\visitorDesignPattern";
	public static final String projectSourcePath = "..\\visitorDesignPattern\\src";
	public static final String jrePath = "C:\\Program Files\\Java\\jre1.8.0_51\\lib\\rt.jar";
	static Hashtable<String,List<String>> method_entrees = new Hashtable<>();
	static Hashtable<String,List<String>> method_sorties = new Hashtable<>();

	public static void main(String[] args) throws IOException {
		String class_name = "";
		//HashMap<String,List<List<String>>> methods_entrees_sorties = new HashMap<>();

		// read java files
		final File folder = new File(projectSourcePath);
		ArrayList<File> javaFiles = listJavaFilesForFolder(folder);

		//
		for (File fileEntry : javaFiles) {
			String[] s = fileEntry.toString().split("\\\\");
			String class_name_java = s[s.length-1];
			class_name = class_name_java.substring(0,class_name_java.length()-5);
			method_sorties = new Hashtable<>();
			method_entrees = new Hashtable<>();


			String content = FileUtils.readFileToString(fileEntry);
			// System.out.println(content);

			CompilationUnit parse = parse(content.toCharArray());

			// print methods info
			//printMethodInfo(parse);

			// print variables info
			//printVariableInfo(parse);
			
			//print method invocations
			printMethodInvocationInfo(parse);
			//System.out.println("**************************");
			System.out.println("\t\tméthodes - sorties");
			System.out.println("\t\t------------------");
			for (Map.Entry<String, List<String>> e : method_sorties.entrySet()){
				System.out.println("\t\t"+e.getKey() + " : "+ e.getValue());
			}
			System.out.println("\n\t\tméthodes - entrées");
			System.out.println("\t\t------------------");
			for (Map.Entry<String, List<String>> e : method_entrees.entrySet()){
				System.out.println("\t\t"+e.getKey() + " : "+ e.getValue());
			}
			System.out.println("_____________________________________________"+class_name);

		}
	}

	// read all java files from specific folder
	public static ArrayList<File> listJavaFilesForFolder(final File folder) {
		ArrayList<File> javaFiles = new ArrayList<File>();
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				javaFiles.addAll(listJavaFilesForFolder(fileEntry));
			} else if (fileEntry.getName().contains(".java")) {
				javaFiles.add(fileEntry);
			}
		}

		return javaFiles;
	}

	// create AST
	private static CompilationUnit parse(char[] classSource) {
		ASTParser parser = ASTParser.newParser(AST.JLS4); // java +1.6
		parser.setResolveBindings(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
 
		parser.setBindingsRecovery(true);
 
		Map options = JavaCore.getOptions();
		parser.setCompilerOptions(options);
 
		parser.setUnitName("");
 
		String[] sources = { projectSourcePath }; 
		String[] classpath = {jrePath};
 
		parser.setEnvironment(classpath, sources, new String[] { "UTF-8"}, true);
		parser.setSource(classSource);
		
		return (CompilationUnit) parser.createAST(null); // create and parse
	}
	
	// navigate method invocations inside method
		public static void printMethodInvocationInfo(CompilationUnit parse) {

			MethodDeclarationVisitor visitor1 = new MethodDeclarationVisitor();
			parse.accept(visitor1);
			for (MethodDeclaration method : visitor1.getMethods()) {

				MethodInvocationVisitor visitor2 = new MethodInvocationVisitor();
				method.accept(visitor2);

				for (MethodInvocation methodInvocation : visitor2.getMethods()) {
					List<String> invocations_sorties = new ArrayList<>();
					List<String> invocations_entrees = new ArrayList<>();

					//method_sorties
					if(method_sorties.get(method.getName().toString()) == null){
						invocations_sorties.add(methodInvocation.getName().toString());
						method_sorties.put(method.getName().toString(), invocations_sorties);
					}
					else{
						invocations_sorties = method_sorties.get(method.getName().toString());
						if(!invocations_sorties.contains(methodInvocation.getName().toString())){
							invocations_sorties.add(methodInvocation.getName().toString());
							method_sorties.put(method.getName().toString(), invocations_sorties);
						}
					}
					//method_entrees
					if(method_entrees.get(methodInvocation.getName().toString()) == null){
						invocations_entrees.add(method.getName().toString());
						method_entrees.put(methodInvocation.getName().toString(), invocations_entrees);
					}
					else{
						invocations_entrees = method_entrees.get(methodInvocation.getName().toString());
						if(!invocations_entrees.contains(method.getName().toString())){
							invocations_entrees.add(method.getName().toString());
							method_entrees.put(methodInvocation.getName().toString(), invocations_entrees);
						}
					}
					//System.out.println("method " + method.getName() + " invoc method "
					//		+ methodInvocation.getName());
				}

			}
		}

}
