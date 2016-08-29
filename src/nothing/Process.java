package nothing;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Process {
	public static long VisitFile(String path){
    	switch(View.getChooseFunction()){
    		case 0: return countLines(path);
    		case 1: return countBytes(path);
    		case 2: return isTarget(path);
    	}
		return 0;
    }
	
	public static int VisitDirectory(String path){
        try{
        	//get all directories
            String[] dirs = getDirectories(path);
            int count=0; 
            for(int i = 0; i < dirs.length; i++){
            	count += VisitDirectory(dirs[i]);
            }

            //get all files
            File[] files = getAllFiles(path);
            for(int i = 0; i < files.length; i++){
            	if( (View.getChooseFunction() == 0 && filePicker(files[i].getName())) || View.getChooseFunction() != 0 )
            			count += VisitFile(files[i].toString());
            }
            
            String end = null;
            switch(View.getChooseFunction()){
            	case 0: {
            		end = " line(s).";
            		break;
            	}
            	case 1: {
            		end = " byte(s).";
            		break;
            	}
            	case 2: {
            		if(count >= 1)
            			System.out.println("there are " + count + " file(s) matched in " + path.toString());
            		break;
            	}
            }
            if (View.isVerbose()){
            	if((View.isIgnoreBlank() && count != 0) || !View.isIgnoreBlank())
            		System.out.println(path.toString() + " -- " + count + end);
            }
            return count;
        }
        
        catch (Exception e){
            if (View.isVerbose())
            	System.out.println("Can't read " + path);
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

			if (View.isVerbose()){
        		if((View.isIgnoreBlank() && count != 0) || !View.isIgnoreBlank())
        			System.out.println(path.toString() + " -- " + count + " line(s).");
			}
			return count;
		}
		
		catch (Exception e){
			if (View.isVerbose())
				System.out.println("Can't read " + path + " for "+ e);
        return 0;
		}
	}
    
    public static long countBytes(String path){
		try {
			Path p = Paths.get(path);
			long bytes = Files.size(p);
			if (View.isVerbose()){
				if(bytes >= 1024){
					if(bytes >= 1048576) System.out.println(path.toString() + " -- " + bytes/1048576 + "MB.");
					else System.out.println(path.toString() + " -- " + bytes/1024 + "MB.");
				}
				else System.out.println(path.toString() + " -- " + bytes + "B.");
			}
			return bytes;
		} 
		catch (IOException e) {
			if (View.isVerbose())
				System.out.println("Can't read " + path + " for "+ e);
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
	
	public static int isTarget(String path){
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
			return null;
		}

		File[] files = new File(path).listFiles();
		List<String> temp = new ArrayList<String>();
		
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
	
	//not use
	public static boolean pathIsEmpty(String path) {
		if (path == null || path.length() < 1) {
			System.err.println("ERROR:给定路径 "+ path +" 不能为空。");
			return false;
		} 
		
		else if (!new File(path).exists()) {
			System.err.println("ERROR:给定路径 "+ path +" 不存在。");
			return false;
			
		} else return true;
	}
	
	public static File[] getAllFiles(String path) {
		List<File> temp = new ArrayList<File>();
		File rootPath = new File(path);
		File[] items = rootPath.listFiles();
		
		for (int i = 0; i < items.length; i++) {
			if (items[i].exists()) {
				if (items[i].isFile()) 
					temp.add(items[i]);
				else if (items[i].isDirectory()) 
					getAllFiles(items[i].getPath());
				
			}
		}
		
		File[] files = new File[temp.size()];
		int i = 0;
		for(File f:temp)
			files[i++] = f;
		
		return files;
	}

}
