package io.muic.ooc;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App 
{

  static int MAX_DEPTH = 1;

  public static void main( String[] args ) throws Exception
  {

    String url = "https://cs.muic.mahidol.ac.th/courses/ooc/docs";
    Set<String> visited = new HashSet<String>();

    craw(url, "./", visited, 0);
  }

  static void craw(String url, String dir, Set<String> visited, int depth) throws Exception {

    if (depth >= MAX_DEPTH) {
      return;
    }

    if (!visited.contains(url)) {
      Document doc = Jsoup.connect(url).get();
      visited.add(url);

      dir += url.substring(url.indexOf("docs"));
      System.out.println(dir + "/technotes/css");
      File file = new File(dir + "/technotes/css");
      file.mkdirs();

      List<String> links = getLinks(doc);
      List<String> srcs = getSrcs(doc);

      System.out.println(depth + " " + url);
      System.out.println(links);
      System.out.println(srcs);
      System.out.println("=================================================");

      if (url.endsWith(".html")) {
        url = url.substring(0, url.lastIndexOf("/"));
      }

      for (String link: links) {
        if (!link.contains(".css")) {
          if (link.startsWith("../")) {
            craw(removeDotDot(url, link), dir, visited, depth + 1);
          } else {
            craw(url + "/" + link, dir, visited, depth + 1);
          }
        } else {
          // Download
        }
      }
    }
  }

  static String removeDotDot(String url, String link) {
    if (url.endsWith("/")) {
      url = url.substring(0, url.lastIndexOf("/"));
    }

    String newUrl = url.substring(0, url.lastIndexOf("/"));
    String newLink = link.substring(3);
    if (newLink.startsWith("../")) {
      return removeDotDot(newUrl, newLink);
    }
    return newUrl + "/" + newLink;
  }


  static List<String > getSrcs(Document doc) {
    Elements raw_srcs = doc.select("[src]");
    List<String> srcs = new ArrayList<String>();
    for (Element raw_src: raw_srcs) {
      srcs.add(raw_src.attr("src"));
    }
    return srcs;
  }

  static List<String> getLinks(Document doc) {
    Elements raw_links = doc.select("[href]");
    List<String> links = new ArrayList<String>();
    for (Element raw_link: raw_links) {
      String link = raw_link.attr("href");
      if (!link.contains("http")) {
        if (link.contains("#")) {
          link = link.split("#")[0];
          if (link.equals(" ")) {
            links.add(link);
          }
        } else {
          if (!link.equals("index.html")) {
            links.add(link);
          }
        }
      }
    }
    return links;
  }
}
