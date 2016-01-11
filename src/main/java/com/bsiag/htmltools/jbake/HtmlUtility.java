package com.bsiag.htmltools.jbake;

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
    int index = contentFile.lastIndexOf("/");
    if (index > 0) {
      return contentFile.substring(0, index + 1);
    }
    return "";
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
