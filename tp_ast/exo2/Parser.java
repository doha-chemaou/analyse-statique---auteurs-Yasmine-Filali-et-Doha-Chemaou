package exo2;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.SwappedDataInputStream;
import org.eclipse.core.internal.utils.FileUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
		List<String> a_enfant = new ArrayList<>();
		List<String> a_enfant_guillemets = new ArrayList<>();

		//			
		JSONObject jsonObject_main = new JSONObject();
		List<JSONObject> json_objs = new ArrayList<>();
		String s_json_objs = "";
		//File fileEntry ;
		for (File fileEntry : javaFiles) {
			s_json_objs = json_objs.toString();
			//  System.out.println("\n_________________________\nstring version od json objs " + s_json_objs+"\n_________________________\n");
			String[] s = fileEntry.toString().split("\\\\");
			String class_name_java = s[s.length-1];
			class_name = class_name_java.substring(0,class_name_java.length()-5);
			method_sorties = new Hashtable<>();
			method_entrees = new Hashtable<>();
			//a_enfant = new ArrayList<>();

			String content = FileUtils.readFileToString(fileEntry);

			CompilationUnit parse = parse(content.toCharArray());
			printMethodInvocationInfo(parse);

			//System.out.println("**************************");
			System.out.println("\t\tméthodes - sorties");
			System.out.println("\t\t------------------");
			String racine_json ="";
			for (Map.Entry<String, List<String>> e : method_sorties.entrySet()){
				if (racine_json.equals("")) racine_json = e.getKey();
				System.out.println("\t\t"+e.getKey() + " : "+ e.getValue());
				
			}
			// json file
			Set<String> keys = method_sorties.keySet();
			List<String> l_keys = new ArrayList<String>(keys);
			//l_keys = swaps_keys(l_keys);
			keys = new HashSet<>(l_keys);
			//iterate through with a for loop
			boolean first = true;
			JSONObject json_obj1 = new JSONObject(), json_obj2= new JSONObject() ;
			//String key ;
			boolean passage=false;
			for(String key : keys) {
				//if(first){
					// .......
				String method = key;
				json_obj2 = new JSONObject();
				//System.out.println("help solve prob "+method);
				//System.out.println("help solve prob "+method_sorties.get(key));

				List<String> sorties_meths = method_sorties.get(key);
				//System.out.println("sorties_meths:::::::::: "+ sorties_meths);
				if(sorties_meths.size()>1){
					// need to browse sorties_meths
					int i = 0 , size = sorties_meths.size();
					passage = false;
					while (i < size){
						passage = false;
						String sortie_meth = sorties_meths.get(i);
						//System.out.println("help solve prob " +sortie_meth);
						/*if (a_enfant_guillemets.contains(sortie_meth)){
							json_obj2.put(sortie_meth,"");//null or enfant
							a_enfant_guillemets.add(method);
						}*/
						if (a_enfant.contains(sortie_meth)){
							json_obj2.put(sortie_meth,"");//null or enfant
							a_enfant_guillemets.add(sortie_meth);
						}
						else {
							json_obj2.put(sortie_meth, null);//null or enfant
							passage = true;
						}
						
						i++;
					}
					JSONObject jj = new JSONObject();
					jj.put(method,json_obj2);
					String s_jj = jj.toString();
					//System.out.println("|||||||||||||||||||||| "+ s_jj);
					if(!s_json_objs.contains(s_jj))
						json_obj1.put(method,json_obj2);
				}
				else{
					JSONObject jj = new JSONObject();
					jj.put(method,sorties_meths.get(0));
					String s_jj = jj.toString();
					//System.out.println("|||||||||||||||||||||| "+ s_jj);
					if(!s_json_objs.contains(s_jj))
						json_obj1.put(method,sorties_meths.get(0));
				}
				if(!a_enfant.contains(method)){
					a_enfant.add(method);
					//first = false;
				}
				                                   
			}                                              
			//List<Object> new_json_objs = new ArrayList<>();
			String jj = json_obj1.toString();
			//System.out.println("||||||||||||||||||||||||||||||| "+ jj);
			if(!jj.equals("{}"))
				json_objs.add(json_obj1);
    
			          
			//System.out.println(json_objs);
			//json_objs = swaps_keys(json_objs);
			//System.out.println(json_objs);
			
			// System.out.println(a_enfant);
			// System.out.println(a_enfant_guillemets);
			int k = json_objs.get(0).size();
			// System.out.println(k);
			/*if (k>1){
				// right loop
				for(int i = 0 ; i < a_enfant.size();i++){
					String meth_source = a_enfant.get(i);
					Object sorties_of_meth_source = json_objs.get(0).get(meth_source);
					String s_sortiesOms = sorties_of_meth_source.toString();
					if(s_sortiesOms.contains(",")){
						int length = s_sortiesOms.length();
						s_sortiesOms = s_sortiesOms.substring(1,length-1);
					}
					else{
						JSONObject sorties = (JSONObject)json_objs.get(0).get(s_sortiesOms);
						json_objs.get(0).put(meth_source,sorties);
						JSONObject j = new JSONObject();
						JSONObject j1 = new JSONObject();
						j1.put(s_sortiesOms,sorties);
						j.put(meth_source,j1);
						new_json_objs.add(j);
						System.out.println("new new wwwwwwwwwwwwwww "+new_json_objs);
					}
				}
			}*/
			//System.out.println(json_objs.get(0).getKeys());
			//System.out.println(swaps_keys(asList(json_objs.get(0)))); 
			

			//System.out.println(method_sorties.get(key));

			//System.out.println(json_objs);
			/*if(json_objs.size()>1){

			}*/
			
			
			System.out.println("\n\t\tméthodes - entrées");
			System.out.println("\t\t------------------");
			for (Map.Entry<String, List<String>> e : method_entrees.entrySet()){
				System.out.println("\t\t"+e.getKey() + " : "+ e.getValue());
			}
			System.out.println("_____________________________________________"+class_name);

			//System.out.println("\n");
		}
		//for()
		//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		String s_filling = "";
		boolean string = true;
		JSONObject filling = new JSONObject();
			for(int i = 0 ; i < a_enfant_guillemets.size();i++){
				String meth = a_enfant_guillemets.get(i);
				for (int j = 0 ; j < json_objs.size();j++){
					if(json_objs.get(j).get(meth)!=null){
						//System.out.println(meth + " " +json_objs.get(j).get(meth));
						if(!json_objs.get(j).get(meth).toString().contains("{"))
							s_filling = json_objs.get(j).get(meth).toString();
						else{
							filling = (JSONObject)json_objs.get(j).get(meth);
							string = false;
						}
					}
				}
				for (int j = 0 ; j < json_objs.size();j++){
					// System.out.println("~~~~~~~~~~~~~~~~~~~é "+json_objs.get(j).toString().contains("\""+meth+"\":\"\""));//.contains(meth));
					//System.out.println(json_objs.get(j));
					// System.out.println(meth + " " +json_objs.get(j).get(meth) + " filling  "+s_filling);
					if(json_objs.get(j).toString().contains("\""+meth+"\":\"\"")){
						// System.out.println(json_objs.get(j));	
						//System.out.println(json_objs.get(j));
						//json_objs.get(j).toString().replaceAll("\"\"",json_objs.get(j).get(meth).toString());
						//System.out.println(json_objs.get(j).keySet());
						Set<String> keys = json_objs.get(j).keySet();
						json_objs.get(j);
						for (String value : keys){
							//System.out.println(("||||||||||||||||||||||||||||||||| ")+(JSONObject)(json_objs.get(j).get(value))+"\n");//.put(meth,filling);
							// (JSONObject)
							// System.out.println("==========================================="+(((JSONObject)(json_objs.get(j).get(value))).get(meth).toString()).equals(""));
							// System.out.println("**************************** "+meth+" "+((((JSONObject)(json_objs.get(j).get(value))))));
							// System.out.println("'''''''''''''''''''''''''''''''''''''''''''trying to figure out prob");
							// System.out.println(json_objs.get(j)+"\n");
							// System.out.println(json_objs.get(j).get(value)+"\n");
							// System.out.println(((JSONObject)json_objs.get(j).get(value)).get(meth)+"\n");
							if(((JSONObject)json_objs.get(j).get(value)).get(meth)!=null && (((JSONObject)(json_objs.get(j).get(value))).get(meth).toString()).equals(""))
								((JSONObject)(json_objs.get(j).get(value))).put(meth,(string?s_filling:filling));
							//(json_objs.get(j)).put(meth,filling);
							//String d = json_objs.get(j).toString().replaceAll("\"\"",filling);//json_objs.get(j).get(value).toString());
							// json_objs.get(j).toString().replaceAll("\"\"",filling);
							// System.out.println(">>>>>>>>>>>>>>>>>>>>> " +json_objs+"\n");

						}
					}
				}
			}
			System.out.println(">>>>>>>>>>>>>>>>>>>>>json_objs:::\n " +json_objs+"\n");

			//for (int i = 0 ; i < ; i++)
		try {
			//jsonObject_main.put("",new_json_objs);
			FileWriter file = new FileWriter("graphe_appel.json");
			file.write(jsonObject_main.toJSONString());
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\nJSON file created: "+jsonObject_main);
	}
	// to chnage 
	//public static <T> List<T> swaps_keys(List<T> keys){
	/*public static JSONObject swaps_keys(JSONObject keys){
		JSONObject swaped_keys = keys;
		//String keys_ = swaped_keys.keys();
		
		//JSONObject swaped_keys = keys.getJSONObject("");
		//JSONArray songsArray = swaped_keys.toJSONArray(swaped_keys.names());

		//ArrayList<JSONObject> objs = new ArrayList<>(swaped_keys);
		System.out.println("swaps keys "+swaped_keys);
		int i = 0;
		int j = keys.size()-1;
		while(i < j){
			JSONObject inter = (JSONObject)swaped_keys.get(i);
			System.out.println("swaps keys "+keys.get(j));
			System.out.println("swaps keys "+inter);			

			swaped_keys.set(i,(JSONObject)keys.get(j));
			swaped_keys.set(j,inter);
			i++; j--;
		}
		System.out.println("swaps keys "+swaped_keys);

		return swaped_keys;
	}*/

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
