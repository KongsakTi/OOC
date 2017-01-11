package HomeWork1;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.DirectoryWalker;


public class Task01 {

  static String PATH = "/Users/Sam/Desktop/OOC/Homeworks/docs/";

  public static void main(String[] args) {


      File file = new File(PATH);
      FileCleaner fileCleaner = new FileCleaner();
      fileCleaner.walk(file);

  }

  public static class FileCleaner extends DirectoryWalker {
    HashMap<String, Integer> extensions = new HashMap<String, Integer>();

    int totalNumberOfFile = 0;
    int totalNumberOfDirectory = 0;

    public FileCleaner() {
      super();
    }

    public void walk(File startDirectory) {
      List collection = new ArrayList();
      try {
        walk(startDirectory, collection);
      } catch (java.io.IOException e) {
        System.out.println("Error");
      }
      System.out.printf("Total number of files: %d\n" +
                        "Total number of directory: %d\n" +
                        "Total number of unique file extensions: %d\n" +
                        "List all unique file extensions: %s\n" +
                        "Total number of files for each extension: %s\n",
                        totalNumberOfFile, totalNumberOfDirectory, extensions.size(),
                        extensions.keySet(), extensions.entrySet() );
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
}

