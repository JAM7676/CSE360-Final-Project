package application;

public class IOHandeler {
	public static boolean isWindows () {
		if (System.getProperty("os.name").contains("Windows")) {
			return true;
		}
		return false;
	}
	
	public static String getDirectory () {
		if (IOHandeler.isWindows()) {
			return System.getProperty("user.dir") + "\\CSE360ClassProject\\src\\application\\";
		} else {
			return "src/application/";
		}
	}
	
	public static String getFile (String userID, String fileToLoad) {
		if (IOHandeler.isWindows()) {
			return System.getProperty("user.dir") + "\\CSE360ClassProject\\src\\application\\" + userID + "\\" + fileToLoad;
		} else {
			return "src/application/" + userID + "/" + fileToLoad;
		}
	}
}