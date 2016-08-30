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
    
    public static int countBytes(List<String> args){
    	View.setSort(Integer.parseInt(String.valueOf(args.get(0).charAt(0))));
    	
    	String limit = args.get(1);
    	if(limit.equals("")) return Integer.MIN_VALUE;
    	
    	int size = limit.length(), multiple = 1, res = 0, h = size-2;
    	for(int i = 0; i < size; i++){
    		if((limit.charAt(i) > 47 || limit.charAt(i) < 58) && h > 0)
    			res += Integer.parseInt(String.valueOf(limit.charAt(i))) * Math.pow(10, --h);
    		
    		switch(limit.charAt(i)){
    		case 75: multiple = 1024; break;
    		case 77: multiple = 1024 * 1024; break;
    		case 71: multiple = 1024 * 1024 * 1024; break;
    		}
    	}
    	return res * multiple;
    }
    
    public static void searchFile(List<String> args){
    	for(String str : args){
        	if(str.equals("-V") || str.equals("-v"))
        		View.setVerbose(true);
        	else View.getTargets().add(str);
    	}
    }
}
