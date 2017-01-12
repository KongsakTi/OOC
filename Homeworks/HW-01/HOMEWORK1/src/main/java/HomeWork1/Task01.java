package HomeWork1;

import java.io.File;

public class Task01 {

  static String PATH = "/Users/Sam/Desktop/OOC/Homeworks/docs/";

  public static void main(String[] args) {


    File file = new File(PATH);
    DirectoryUtil fileCleaner = new DirectoryUtil();
    fileCleaner.walk(file);

  }
}

