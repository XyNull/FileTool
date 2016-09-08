package nothing;

import java.util.*;
import java.io.File;
import java.lang.System;

public class View {
    private static String rootFolder;
    
    private static List<String> targets = new ArrayList<String>();
    public static List<String> getTargets() {
		return targets;
	}
	public static void setTargets(List<String> targets) {
		View.targets = targets;
	}

	private static boolean ignoreBlank = false;
    private static boolean verbose = false;
    
    public static boolean isIgnoreBlank() {
		return ignoreBlank;
	}
	public static boolean isVerbose() {
		return verbose;
	}
	public static void setIgnoreBlank(boolean ignoreBlank) {
		View.ignoreBlank = ignoreBlank;
	}
	public static void setVerbose(boolean verbose) {
		View.verbose = verbose;
	}
	
	private static int chooseFunction = 0;
    public static int getChooseFunction() {
		return chooseFunction;
	}
	public static void setChooseFunction(int chooseFunction) {
		View.chooseFunction = chooseFunction;
	}
	
	private static int sortNum = 0;
    public static int getSort() {
		return sortNum;
	}
	public static void setSort(int sort) {
		View.sortNum = sort;
	}
	
	private static int byteLimit = 0;
	public static int getByteLimit() {
		return byteLimit;
	}
	public static void setByteLimit(int byteLimit) {
		View.byteLimit = byteLimit;
	}

	private static long start = 0;
	
	public static void main(String[] args){
    	System.out.println(Values.Usage);
    	
        Scanner in = new Scanner(System.in);
        rootFolder = in.nextLine().trim();
        
    	while(!new File(rootFolder).isDirectory()){
        	System.err.println("\n\n\n\nAttetion:input error,please input again.\n");
        	rootFolder = in.nextLine().trim();
    	}

		Process.preProcess(rootFolder);

        long time = -(controller(in) - new Date().getTime());
        System.out.println("Time: " + time + "ms.");
        
        System.out.println("input 0 to quit or 1 to continue.\n");
        if(in.nextLine().equals("1")) main(new String[1]);
        in.close();
    }
    
    public static long controller(Scanner in){
    	//choose function
        System.out.println(Values.functionUsageEn);
    	String function = in.nextLine();
    	
        //process add-ons by each function
        if(function.equals("cl") || function.equals("CountLines")){
        	System.out.println(Values.addonsUsageCL);
        	ProcessArgument.countLines(getAddons(in));
        	System.out.println("please wait....\n");
            System.out.println("Total:" + Process.VisitDirectory(rootFolder) + " line(s).\n");
        }
        
        //Count Bytes
        else if(function.equals("cb") || function.equals("CountBytes")){
            chooseFunction = 1;
            verbose = true;
        	System.out.println(Values.addonsUsageCB);
        	byteLimit = ProcessArgument.countBytes(getAddons(in));
        	Process.VisitDirectory(rootFolder);
        	
            HashMap<Integer,String> folderList = Process.getFolderList();
            List<Integer> Bytes = Process.getBytes();
			Collections.sort(Bytes);
			Collections.reverse(Bytes);

            if(sortNum != 0){
            	System.out.println("\n\nThe " + sortNum + "the biggest folder:\n");
				int i = 0;
				for (int btyes :
						Bytes) {
					if(i < sortNum){
						if(folderList.get(btyes) != null){
							Process.conversion(btyes, folderList.get(btyes));
							i++;
						}
					}
				}
            }
        }
        
        //search files
        else if(function.equals("sf") || function.equals("SearchFile")){
        	chooseFunction = 2;
        	System.out.println(Values.addonsUsageSF);
        	ProcessArgument.searchFile(getAddons(in));
        	int i = (int) Process.VisitDirectory(rootFolder);
        	if(i > 0)
        		System.out.println("there are " + i + " file(s) matched in " + rootFolder);
        	else System.out.println("can't find any file");
         }
        else {
        	System.err.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nAttetion:input error,please input again.\n");
    		main(new String[1]);
    		return 0;
        }
        return start;
    }
    
    public static List<String> getAddons(Scanner in){
    	//input add-ons
    	List<String> addons = new ArrayList<String>();
    	if(chooseFunction == 1){
    		addons.add(in.nextLine());
    		addons.add(in.nextLine());
    		System.out.println("please wait....\n");
    	}
    	else{
    		while(true){
    		String a = in.nextLine();
    		if(a.equals("over")) break;
    		addons.add(a);
    		}
    	}
    	start = new Date().getTime();
    	return addons;
    }
}
