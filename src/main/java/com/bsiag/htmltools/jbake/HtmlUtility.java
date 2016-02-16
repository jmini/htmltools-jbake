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

    String relPrefixPath = computeRelPath(outputFile, contentFile);

    if (!relPrefixPath.isEmpty()) {
      Elements imgElements = doc.getElementsByTag("img");
      for (Element e : imgElements) {
        String src = e.attr("src");
        if (src != null) {
          String newSrc = relPrefixPath + src;
          e.attr("src", newSrc);
        }
      }

      Elements aElements = doc.getElementsByTag("a");
      for (Element e : aElements) {
        String href = e.attr("href");
        if (shouldFixLink(href)) {
          String newSrc;
          if (href.startsWith("#")) {
            String fileName = fileName(contentFile);
            newSrc = relPrefixPath + fileName + href;
          }
          else {
            newSrc = relPrefixPath + href;
          }
          e.attr("href", newSrc);
        }
      }
    }

    return doc.body().html();
  }

  static boolean shouldFixLink(String href) {
    return href != null && !href.isEmpty() && !href.matches("(mailto\\:|[a-zA-Z]+\\://|//).+");
  }

  static String computeRelPath(String outputFile, String contentFile) {
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

  static String fileName(String contentFile) {
    if (contentFile == null || contentFile.isEmpty()) {
      return "";
    }
    int i1 = contentFile.lastIndexOf("/");
    int i2 = contentFile.lastIndexOf("\\");
    if (i1 > 0 || i2 > 0) {
      return contentFile.substring(Math.max(i1, i2) + 1);
    }
    return contentFile;
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
