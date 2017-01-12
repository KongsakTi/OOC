package HomeWork1;

import org.apache.commons.io.DirectoryWalker;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class DirectoryUtil extends DirectoryWalker {
  HashMap<String, Integer> extensions = new HashMap<String, Integer>();

  int totalNumberOfFile = 0;
  int totalNumberOfDirectory = 0;

  public DirectoryUtil() {
    super();
  }

  public void walk(File startDirectory) {
    walk(startDirectory, true, true, true, true, true, "");
  }

  public void walk(File startDirectory, boolean optionA, boolean optionB,
                   boolean optionC, boolean optionD, boolean optionE,
                   String ext) {
    List collection = new ArrayList();
    try {
      walk(startDirectory, collection);
    } catch (java.io.IOException e) {
      System.out.println("Error: " + e);
    }

    String output = "";

    if (optionA) {
      output += "Total number of files: " + totalNumberOfFile + "\n";
    }
    if (optionB) {
      output += "Total number of directory: " + totalNumberOfDirectory + "\n";
    }
    if (optionC) {
      output += "Total number of unique file extensions: " + extensions.size() + "\n";
    }
    if (optionD) {
      output += "List all unique file extensions: " + extensions.keySet() + "\n";
    }
    if (optionE) {
      output += "Total number of files for each extension: " + extensions.entrySet() + "\n";
    }

    if (ext.length() > 0) {
      output += "Total number of files with " + ext + " as extension: " + extensions.get(ext);
    }

    System.out.println(output);

  }

  protected boolean handleDirectory(File directory, int depth, Collection collection) {
    totalNumberOfDirectory += 1;
    return true;
  }

  protected void handleFile(File file, int depth, Collection collection) {
    totalNumberOfFile += 1;
    String fileName = file.getName();
    if (fileName.contains(".")) {

      // fileName.lastIndexOf("." + 1) to exclude "."
      String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
      if (extensions.containsKey(ext)) {
        extensions.put(ext, extensions.get(ext) + 1);
      } else {
        extensions.put(ext, 1);
      }
    }
  }
}
