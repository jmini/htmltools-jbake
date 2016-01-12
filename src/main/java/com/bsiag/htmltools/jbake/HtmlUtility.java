package com.bsiag.htmltools.jbake;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public final class HtmlUtility {

  /**
   * @param htmlContent
   *          content as HTML
   * @param outputFile
   *          the file where the content will be written
   * @param contentFile
   *          the file corresponding to the post or to the page
   * @return
   */
  public static String fixHtml(String htmlContent, String outputFile, String contentFile) {
    Document doc = Jsoup.parseBodyFragment(htmlContent);
    doc.outputSettings().charset("ASCII");

    String imgPrefixPath = computeImgPath(outputFile, contentFile);

    if (!imgPrefixPath.isEmpty()) {
      Elements elements = doc.getElementsByTag("img");
      for (Element e : elements) {
        String src = e.attr("src");
        if (src != null) {
          String newSrc = imgPrefixPath + src;
          e.attr("src", newSrc);
        }
      }
    }

    return doc.body().html();
  }

  static String computeImgPath(String outputFile, String contentFile) {
    if (outputFile == null || contentFile == null || outputFile.equals(contentFile)) {
      return "";
    }
    Path pathAbsolute = folderPath(contentFile);
    Path pathBase = folderPath(outputFile);
    Path pathRelative = pathBase.relativize(pathAbsolute);
    String result = pathRelative.toString();
    if (result.isEmpty()) {
      return "";
    }
    return result.replaceAll("\\\\", "/") + "/";

  }

  private static Path folderPath(String filePath) {
    int index = filePath.lastIndexOf("/");
    if (index > 0) {
      String folder = filePath.substring(0, index);
      if (folder.startsWith("/")) {
        return Paths.get(folder);
      }
      else {
        return Paths.get("/" + folder);
      }
    }
    else {
      return Paths.get("/");
    }
  }

  /**
   * @param srcFile
   *          absolute path to the file on the local file system
   * @param repoName
   *          name of the Git Repository as checked out on the local file system. (srcFile contains this string)
   * @param gitViewerPrefix
   *          prefix of the URL for the online Git viewer
   * @return
   */
  public static String computeGitViewerUrl(String srcFile, String repoName, String gitViewerPrefix) {
    int i = srcFile.indexOf(repoName);
    int delta;
    if (gitViewerPrefix.endsWith("/")) {
      delta = 1;
    }
    else {
      delta = 0;
    }
    return gitViewerPrefix + srcFile.substring(i + repoName.length() + delta).replaceAll("\\\\", "/");
  }

  private HtmlUtility() {
  }
}
