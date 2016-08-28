package nothing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {
	
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
	
	public static boolean pathIsEmpty(String path) {
		if (path == null || path.length() < 1) {
			System.err.println("ERROR:给定路径 "+ path +" 不能为空。");
			return false;
		} else if (!new File(path).exists()) {
			System.err.println("ERROR:给定路径 "+ path +" 不存在。");
			return false;
		} else {
			return true;
		}
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
