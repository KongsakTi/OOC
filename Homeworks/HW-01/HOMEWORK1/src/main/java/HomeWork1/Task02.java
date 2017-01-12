package HomeWork1;

import java.io.File;
import org.apache.commons.cli.*;


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

      String ext = "";
      if (cmdLine.hasOption("num-ext")) {
        ext = ((String)cmdLine.getParsedOptionValue("num-ext"));
      }

      File file = new File(PATH);
      DirectoryUtil fileCleaner = new DirectoryUtil();
      fileCleaner.walk(file, cmdLine.hasOption("a"),
              cmdLine.hasOption("b"),
              cmdLine.hasOption("c"),
              cmdLine.hasOption("d"), false, ext);
    } catch (org.apache.commons.cli.ParseException e) {
      System.out.println("Error:" + e);}
  }
}

