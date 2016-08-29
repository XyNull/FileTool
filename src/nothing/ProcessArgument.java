package nothing;

import java.util.List;

public class ProcessArgument {
    public static void countLines(List<String> args){
    	//process arguments for CL
        for(String str : args){
        	if(str.startsWith("."))
        		View.getTargets().add(str);
        	if(str.equals("-V") || str.equals("-v"))
        		View.setVerbose(true);
        	if(str.equals("-I") || str.equals("-i"))
        		View.setIgnoreBlank(true);
        }
    }
    
    public static void countBytes(int arg){
    	View.setSort(arg);
    }
    
    public static void searchFile(List<String> args){
    	for(String str : args){
        	if(str.equals("-V") || str.equals("-v"))
        		View.setVerbose(true);
        	else View.getTargets().add(str);
    	}
    }
}
