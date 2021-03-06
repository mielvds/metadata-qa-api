package de.gwdg.metadataqa.api.util;

import com.jayway.jsonpath.Configuration;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public final class FileUtils {

  private static ClassLoader classLoader = FileUtils.class.getClassLoader();

  private FileUtils() {
  }

  public static List<String> readLines(String fileName)
      throws URISyntaxException, IOException {
    return Files.readAllLines(getPath(fileName), Charset.defaultCharset());
  }

  public static Path getPath(String fileName) throws IOException, URISyntaxException {
    URL url = classLoader.getResource(fileName);
    if (url == null) {
      throw new IOException(String.format("File %s in not available", fileName));
    }
    return Paths.get(url.toURI());
  }

  public static String readFirstLine(String fileName)
      throws URISyntaxException, IOException {
    List<String> lines = readLines(fileName);
    return lines.get(0);
  }

  public static String readContent(String fileName)
      throws URISyntaxException, IOException {
    return StringUtils.join(readLines(fileName), "");
  }

  public static Object buildDoc(String fileName) throws URISyntaxException, IOException {
    String jsonString = readFirstLine(fileName);
    Object jsonDoc = Configuration.defaultConfiguration().jsonProvider().parse(jsonString);
    return jsonDoc;
  }

  public static String readFromUrl(String url) throws IOException {
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      return readAll(rd);
    } finally {
      is.close();
    }
  }

  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }
}
