package nothing;

public class Values {
	public static String Usage =
			"This is a multifunction file manager tool.\n" +
			"you can \n" +
			"1.Counting your code lines.\n" + 
			"2.Find something you may not need to save your hard disk space.\n"+
			"3.Search a file by name/part of name/suffix in a rootFolder.\n\n"+
			"RootFolder: Folder you want to check.\n" + 
			"ex. D:\\LeetCode\n" +
			"please input rootFolder:\n";
	
	public static String functionUsageEn =
			"\nplease choose one funtion\nInput " +
			"CountLines or cl  to counting code line.\n" +
			"      CountBytes or cb  to counting files' size by paths.\n"+
			"      SearchFile or sf  to searching specific file.\n\n";
	
	public static String addonsUsageCL = 
			"Addons:\n"+
			"Target: The suffix of code file.\nStart with '.' for suffix.\n" +
			"ex. \".java\" \".cpp\" \n\n" +
			"\"-v\"    Show process log.\n" +
			"\"-i\"    Ignore blank files & path in log.\n" +
			"PS.Ignoring blank lines is default.\n\n" +
			"please input the addons (separated by enter & ignore the order)\n" +
			"input \"over\" to end:";
	
	public static String addonsUsageSF =
			"Addons:\n"+
			"Target: Any part of name of file you want to search.\n" +
			"ex. \"action-movie.avi\" \"avi\" \"action-movie\" \"action\" \n\n" +
			"\"-v\"    Show process log.\n" +
			"\"-i\"    Ignore blank files & path in log.\n" +
			"please input the addons (separated by enter & ignore the order)\n" +
			"input \"over\" to end:";
	
	public static String addonsUsageCB = 
			"Addons:\n"+
			"Sort number: List x biggst folder in root.\n" +
			"Ignore bytes: Ignore smaller than x bytes' file&folder in log. No input, no limits.\n" +
			"please input the addons\n" +
			"(ONE number & Enter & x KB/MB/GB)\n";
}
