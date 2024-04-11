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
    	return "src/application/";
    }

    public static String getFile (String userID, String fileToLoad)
    {
    	return "src/application/" + userID + "/" + fileToLoad;
    }
}