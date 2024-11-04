package org.myproject.utils;

import java.nio.file.Paths;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;



public class FileUtils{
    public static String ReadFileToString(String fileName) {
        Scanner scanner = null;
		try {
			scanner = new Scanner(Paths.get("src/main/resources/" + fileName), StandardCharsets.UTF_8.name());
			// we can use Delimiter regex as "\\A", "\\Z" or "\\z"
			String data = scanner.useDelimiter("\\A").next();
			return data;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (scanner != null)
				scanner.close();
		}
    }
    public static String Path(String filename) {
        return "src/main/resources/" + filename;
    }
}
