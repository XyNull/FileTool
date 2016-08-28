package nothing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.lang.System;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {        
	private static String Usage =
			"This is a multifunction file manager tool.\n" +
			"you can \n" +
			"1.Counting your code lines.\n" + 
			"2.Find something you may not need to save your hard disk space.\n"+
			"3.Search a file by name/part of name/suffix in a rootFolder.\n\n"+
			"RootFolder: Folder you want to check.\n" + 
			"ex. D:\\LeetCode\n" +
			"please input rootFolder:\n";
	
	private static String functionUsageEn =
			"\nplease choose one funtion\nInput " +
			"CountLines or cl  to counting code line.\n" +
			"      CountBytes or cb  to counting files' size by paths.\n"+
			"      SearchFile or sf  to searching specific file.\n\n";
	
	private static String addonsUsageEn = 
			"Addons:\n"+
			"Target: The name with or without suffix or suffix of code file.\nStart with '.' for suffix.\n" +
			"ex. \".java\" \".cpp\" \"action-movie.avi\" \n\n" +
			"\"-v\"    Show process log.\n" +
			"\"-i\"    Ignore blank files & path in log.\n" +
			"PS.In counting lines ignoring blank lines is default.\n\n" +
			"please input the addons (separated by enter & ignore the order)\n" +
			"input \"over\" to end:";
	
    private static String rootFolder;

    private static List<String> targets = new ArrayList<String>();

    private static boolean ignoreBlank = false;

    private static boolean verbose = false;
    
    private static int chooseFunction = 0;
    
    public static void processArgsForCL(List<String> args){
    	//process arguments for CL
        for(String str : args){
        	if(str.startsWith("."))
        		targets.add(str);
        	if(str.equals("-V") || str.equals("-v"))
        		verbose = true;
        	if(str.equals("-I") || str.equals("-i"))
        		ignoreBlank = true;
        }
    }
    
    public static void processArgsForSF(List<String> args){
    	for(String str : args){
        	if(str.equals("-V") || str.equals("-v"))
        		verbose = true;
        	else targets.add(str);
    	}
    }
    
    public static void main(String[] args){
    	System.out.println(Usage);
    	
        Scanner in = new Scanner(System.in);
        rootFolder = in.nextLine().trim();

        long time = -(process(in) - new Date().getTime());
        System.out.println("Time: " + time + "ms.");
        
        System.out.println("input 0 to quit or 1 to continue.\n");
        if(in.nextLine().equals("1")) main(new String[1]);
        in.close();
    }
    
    public static long process(Scanner in){
    	long start = 0;
    	//choose function
        System.out.println(functionUsageEn);
    	String function = in.nextLine();
    	
    	if(function.equals("cb") || function.equals("CountBytes")){
        	chooseFunction = 1;
        	System.out.println("Total:" + VisitDirectory(rootFolder) + " bytes(s).\n");
            start = new Date().getTime();
            return start;
        }
    	
    	//input add-ons
    	System.out.println(addonsUsageEn);
    	List<String> addons = new ArrayList<String>();
    	while(true){
    		String a = in.nextLine();
    		if(a.equals("over")) break;
    		addons.add(a);
    	}
    	
        //process add-ons by each function
        if(function.equals("cl") || function.equals("CountLines")){
        	System.out.println("please wait....\n");
        	processArgsForCL(addons);
            start = new Date().getTime();
            System.out.println("Total:" + VisitDirectory(rootFolder) + " line(s).\n");
            return start;
        }
        
        else if(function.equals("sf") || function.equals("SearchFile")){
        	processArgsForSF(addons);
        	start = new Date().getTime();
        	int i = VisitDirectory(rootFolder);
        	if(i >= 1)
        		System.out.println("there are " + i + " file(s) matched in " + rootFolder);
        	else System.out.println("can't find any file");
        	return start;
        }
        else {
        	System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nAttetion:input error,please input again.\n");
    		main(new String[1]);
    		return 0;
        }
    }
    
    public static int countLines(String path){
		try{
			Path p = Paths.get(path);
	    	Charset cs = Charset.defaultCharset();
	    	
			List<String> lines = Files.readAllLines(p,cs);
			while(lines.remove("")); //if not ignore blank lines ?
			int count = lines.size();

			if (verbose){
        		if((ignoreBlank && count != 0) || !ignoreBlank)
        			System.out.println(path.toString() + " -- " + count + " line(s).");
			}
			return count;
		}
		
		catch (Exception e){
			if (verbose)
				System.out.println("Can't read " + path + " for "+ e);
        return 0;
		}
	}
    
    public static long countBytes(String path){
		try {
			Path p = Paths.get(path);
			long bytes = Files.size(p);
			if (verbose){
				if(bytes >= 1024){
					if(bytes >= 1048576) System.out.println(path.toString() + " -- " + bytes/1048576 + "MB.");
					else System.out.println(path.toString() + " -- " + bytes/1024 + "MB.");
				}
				else System.out.println(path.toString() + " -- " + bytes + "B.");
			}
			return bytes;
		} 
		catch (IOException e) {
			if (verbose)
				System.out.println("Can't read " + path + " for "+ e);
			return 0;
		}
	}
    
    public static int isTarget(String path){
		File f = new File(path);
		String name = f.getName();
		for(String str:targets){
			if(name.contains(str))
				return 1;
		}
		return 0;
	}
    
	public static long VisitFile(String path){
    	switch(chooseFunction){
    		case 0: return countLines(path);
    		case 1: return countBytes(path);
    		case 2: return isTarget(path);
    	}
		return 0;
    }
	
	public static int VisitDirectory(String path){
        try{
            String[] dirs = FileHelper.getDirectories(path);
            int count=0; 
            for(int i = 0; i < dirs.length; i++){
            	count += VisitDirectory(dirs[i]);
            }

            File[] files = FileHelper.getAllFiles(path);
            
            for(int i = 0; i < files.length; i++){
            	if(filePicker(files[i].getName()))
            		count += VisitFile(files[i].toString());
            }
            String end = null;
            switch(chooseFunction){
            	case 0: end = " line(s).";
            	case 1: end = " byte(s).";
            	case 2: {
            		if(count >= 1)
            			System.out.println("there are " + count + " file(s) matched in " + path.toString());
            		return count;
            	}
            }
            if (verbose){
            	if((ignoreBlank && count != 0) || !ignoreBlank)
            		System.out.println(path.toString() + " -- " + count + end);
            }
            return count;
        }
        
        catch (Exception e){
            if (verbose)
            	System.out.println("Can't read " + path);
            return 0;
        }
	}
	
	public static boolean filePicker(String name) {
		for(String suf : targets){
			if (name.toLowerCase().endsWith(suf))
				return true;
		}
		return false;
	}
}
