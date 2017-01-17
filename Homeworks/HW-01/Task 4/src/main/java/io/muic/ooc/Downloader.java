package io.muic.ooc;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by Sam on 1/12/17.
 */
public class Downloader {
  public void download(HttpClient client, String url, String name) {

    try {

      HttpGet request = new HttpGet(url);
      HttpResponse response = client.execute(request);
      HttpEntity entity = response.getEntity();

      InputStream inputStream = entity.getContent();
      write(inputStream, name);

    } catch (Exception e) {
      System.out.println("Error: " + e);
    }
  }

  public void write(InputStream inputStream, String name) {
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(name);
      ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
      fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
    } catch (Exception e) {
      System.out.println("Error: " + e);
    }
  }
}


// https://github.com/bobbylough/crawler/blob/master/src/main/java/com/bobbylough/crawler/support/CrawlHelper.java