package nothing;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Process {
	private static boolean verbose = View.isVerbose();
	
	private static HashMap<Integer,String> folderList = new HashMap<>();
	public static HashMap<Integer,String> getFolderList() {
		return folderList;
	}

	private static List<Integer> bytes = new ArrayList<>();
	public static List<Integer> getBytes() {
		return bytes;
	}

	private static List<String> levelOnePath = new ArrayList<>();

	public static float VisitFile(String path){
		int function = View.getChooseFunction();
    	switch(function){
    		case 0: return countLines(path);
    		case 1: return countBytes(path);
    		case 2: return isTarget(path);
    	}
		return 0;
    }

    public static void preProcess(String path){
		String[] dirs = getDirectories(path);
		for (String dir :
				dirs) {
			levelOnePath.add(dir);
		}
	}

	public static float VisitDirectory(String path){
		int function = View.getChooseFunction();
        try{
        	//get all directories
            String[] dirs = getDirectories(path);
			float count=0;
			if(dirs.length != 0){
				for (String dir:
						dirs) {
					count += VisitDirectory(dir);
				}
			}

            //get all files
            File[] files = getAllFiles(path);
			for (File file:
				 	files) {
				if( (function == 0 && filePicker(file.getName())) || function != 0 )
					count += VisitFile(file.toString());
			}
            
            switch(function){
            	case 0: {
                    if (verbose){
                    	if(count != 0 || !View.isIgnoreBlank())
                    		System.out.println(path + " -- " + count + " line(s).");
                    }
            		break;
            	}
            	case 1: {
            		if(levelOnePath.contains(path) && count >= View.getByteLimit()){
            			folderList.put((int)count,path);
						conversion(count,path);
					}
        			bytes.add((int)count);

            		break;
            	}
            	case 2: {
            		if(count >= 1)
            			System.out.println("there are " + count + " file(s) matched in " + path);
            		break;
            	}
            }
            return count;
        }
        
        catch (Exception e){
            if (verbose)
            	System.out.println("Can't read " + path);
            return 0;
        }
	}

    public static float countLines(String path){
		try{
			Path p = Paths.get(path);
	    	Charset cs = Charset.defaultCharset();
	    	
			List<String> lines = Files.readAllLines(p,cs);
			while(lines.remove("")); //if not ignore blank lines ?
			float count = lines.size();

			if (verbose){
        		if((count != 0) || !View.isIgnoreBlank())
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
    
    public static float countBytes(String path){
		try {
			Path p = Paths.get(path);
			float bytes = Files.size(p);
			return bytes;
		}
		catch (IOException e) {
			if (verbose)
				System.err.println("Can't read " + path + " for "+ e);
			return 0;
		}
	}

	public static boolean filePicker(String name) {
		for(String suf : View.getTargets()){
			if (name.toLowerCase().endsWith(suf))
				return true;
		}
		return false;
	}
	
	public static float isTarget(String path){
		File f = new File(path);
		String name = f.getName();
		for(String str:View.getTargets()){
			if(name.contains(str))
				return 1;
		}
		return 0;
	}

	public static String[] getDirectories(String path) {
		if (!pathIsEmpty(path)) {
			return new String[0];
		}

		File[] files = new File(path).listFiles();
		List<String> temp = new ArrayList<>();
		
		for (File file : files) {
			if (file.exists() && file.isDirectory()) {
				temp.add(file.toString());
			}
		}
		
		String[] dirs = new String[temp.size()];
		int i = 0;
		for(String str:temp)
			dirs[i++] = str;
		return dirs;
	}

	public static boolean pathIsEmpty(String path) {
		if (path == null || path.length() < 1) {
			System.err.println("Please input something");
			return false;
		} 
		
		else if (!new File(path).exists()) {
			System.err.println(path +"not found");
			return false;
			
		} else return true;
	}
	
	public static File[] getAllFiles(String path) {
		List<File> temp = new ArrayList<File>();
		File rootPath = new File(path);
		File[] items = rootPath.listFiles();

		for (File item :
				items) {
			if (item.exists()) {
				if (item.isFile())
					temp.add(item);
				else if (item.isDirectory())
					getAllFiles(item.getPath());
			}
		}
		
		File[] files = new File[temp.size()];
		int i = 0;
		for(File f:temp)
			files[i++] = f;
		
		return files;
	}
	
	public static void conversion(float count,String path){
		int temp = 0;
		String[] digit = {"","K","M","G","T"};
		while(true){
			if(count < 1024){
				System.out.println(path + "--" + count + digit[temp] + "B.");
				break;
			}
			else {
				temp++;
				count /= 1024;
			}

		}
	}

}
