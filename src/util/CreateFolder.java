package util;

import java.io.File;

public class CreateFolder {
    public static String saveDirectory = "C:\\Users\\" + System.getProperty("user.name") + "\\AttachFile Mail";

	public static void createFolderIfNotExists(String folderPath) {
        File directory = new File(folderPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
