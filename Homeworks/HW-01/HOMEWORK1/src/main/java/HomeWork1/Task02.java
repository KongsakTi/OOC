package HomeWork1;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.cli.*;
import org.apache.commons.io.DirectoryWalker;


public class Task02 {

  static String PATH = "/Users/Sam/Desktop/OOC/Homeworks/docs/";

  public static void main(String[] args) {
    CommandLineParser parser = new DefaultParser();

    Options options = new Options();
    options.addOption("a", "total-num-files", false, "Total number of files");
    options.addOption("b", "total-num-dirs", false, "Total number of directory");
    options.addOption("c", "total-unique-exts", false, "Total number of unique file extensions");
    options.addOption("d", "list-exts", false, "List all unique file extensions. Do not list duplicates");
    options.addOption(OptionBuilder.withLongOpt("num-ext")
            .withDescription("List total number of file for specified extension EXT")
            .withType(String.class)
            .hasArg()
            .create());


    try {
      CommandLine cmdLine = parser.parse(options, args);

      String ext = ""; // initialize to some meaningful default value
      if (cmdLine.hasOption("num-ext")) {
        ext = ((String)cmdLine.getParsedOptionValue("num-ext"));
      }


      File file = new File(PATH);
      FileCleaner fileCleaner = new FileCleaner();
      fileCleaner.walk(file, cmdLine.hasOption("a"),
              cmdLine.hasOption("b"),
              cmdLine.hasOption("c"),
              cmdLine.hasOption("d"), ext);
    } catch (org.apache.commons.cli.ParseException e) {
      System.out.println("Error:" + e);}


  }

  public static class FileCleaner extends DirectoryWalker {
    HashMap<String, Integer> extensions = new HashMap<String, Integer>();

    int totalNumberOfFile = 0;
    int totalNumberOfDirectory = 0;

    public FileCleaner() {
      super();
    }

    public void walk(File startDirectory, boolean optionA, boolean optionB,
                     boolean optionC, boolean optionD,
                     String ext) {
      List collection = new ArrayList();
      try {
        walk(startDirectory, collection);
      } catch (java.io.IOException e) {
        System.out.println("Error");
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

        // ================================================
        // >>>>>>>Just to turn off duplicate warning<<<<<<<
        // ================================================
        String a = "";
        // ================================================

        if (extensions.containsKey(ext)) {
          extensions.put(ext, extensions.get(ext) + 1);
        } else {
          extensions.put(ext, 1);
        }
      }
    }


  }
}

