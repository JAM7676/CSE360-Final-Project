package application;

public class IOHandeler {
	public static boolean isWindows ()
	{
		if (System.getProperty("os.name").contains("Windows"))
			return true;
		else
			return false;
	}
	
    public static String getDirectory ()
    {
        // I CHANGED THE IF ELSE STATEMENT BY ADDING A '!', BECAUSE IT SEEMS FLIPPED AROUND
        if (!IOHandeler.isWindows() || System.getProperty("os.name").equals("Windows 11"))
            return System.getProperty("user.dir") + "\\CSE360ClassProject\\src\\application\\";
        else
            return "src/application/";
            

    }

    public static String getFile (String userID, String fileToLoad) {
        // SAME THING HERE
        if (!IOHandeler.isWindows() || System.getProperty("os.name").equals("Windows 11"))
            return System.getProperty("user.dir") + "\\CSE360ClassProject\\src\\application\\" + userID + "\\" + fileToLoad;
        else
            return "src/application/" + userID + "/" + fileToLoad;

    }
}