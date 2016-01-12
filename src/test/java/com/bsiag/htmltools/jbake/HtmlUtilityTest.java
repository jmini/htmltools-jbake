package com.bsiag.htmltools.jbake;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HtmlUtilityTest {

  @Test
  public void testFixHtml() {
    String html = "<p><span class=\"image\"><img src=\"jbake-logo.png\" alt=\"jbake-logo\"></span></p>";
    String output = HtmlUtility.fixHtml(html, "index.html", "blog/2013/third-post.html");
    String expected = "<p><span class=\"image\"><img src=\"blog/2013/jbake-logo.png\" alt=\"jbake-logo\"></span></p>";
    assertEquals(expected, output);
  }

  @Test
  public void testComputeImgPath() {
    assertEquals("", HtmlUtility.computeImgPath("blog/2013/third-post.html", "blog/2013/third-post.html"));
    assertEquals("", HtmlUtility.computeImgPath("", ""));

    assertEquals("", HtmlUtility.computeImgPath("index.html", "some-post.html"));
    assertEquals("", HtmlUtility.computeImgPath("index.html", "/some-post.html"));
    assertEquals("", HtmlUtility.computeImgPath("/index.html", "some-post.html"));
    assertEquals("", HtmlUtility.computeImgPath("/index.html", "/some-post.html"));

    assertEquals("", HtmlUtility.computeImgPath("blog/index.html", "blog/some-post.html"));
    assertEquals("", HtmlUtility.computeImgPath("blog/index.html", "/blog/some-post.html"));
    assertEquals("", HtmlUtility.computeImgPath("/blog/index.html", "blog/some-post.html"));
    assertEquals("", HtmlUtility.computeImgPath("/blog/index.html", "/blog/some-post.html"));

    assertEquals("blog/2013/", HtmlUtility.computeImgPath("index.html", "blog/2013/third-post.html"));
    assertEquals("blog/2013/", HtmlUtility.computeImgPath("index.html", "/blog/2013/third-post.html"));
    assertEquals("blog/2013/", HtmlUtility.computeImgPath("/index.html", "blog/2013/third-post.html"));
    assertEquals("blog/2013/", HtmlUtility.computeImgPath("/index.html", "/blog/2013/third-post.html"));

    assertEquals("blog/2014/", HtmlUtility.computeImgPath("index.html", "blog/2014/"));
    assertEquals("blog/2014/", HtmlUtility.computeImgPath("index.html", "/blog/2014/"));
    assertEquals("blog/2014/", HtmlUtility.computeImgPath("/index.html", "blog/2014/"));
    assertEquals("blog/2014/", HtmlUtility.computeImgPath("/index.html", "/blog/2014/"));

    assertEquals("2015/", HtmlUtility.computeImgPath("blog/page.html", "blog/2015/third-post.html"));
    assertEquals("2015/", HtmlUtility.computeImgPath("blog/page.html", "/blog/2015/third-post.html"));
    assertEquals("2015/", HtmlUtility.computeImgPath("/blog/page.html", "blog/2015/third-post.html"));
    assertEquals("2015/", HtmlUtility.computeImgPath("/blog/page.html", "/blog/2015/third-post.html"));

    assertEquals("2012/", HtmlUtility.computeImgPath("blog/page.html", "blog/2012/"));
    assertEquals("2012/", HtmlUtility.computeImgPath("blog/page.html", "/blog/2012/"));
    assertEquals("2012/", HtmlUtility.computeImgPath("/blog/page.html", "blog/2012/"));
    assertEquals("2012/", HtmlUtility.computeImgPath("/blog/page.html", "/blog/2012/"));

    assertEquals("../", HtmlUtility.computeImgPath("blog/page.html", "third-post.html"));
    assertEquals("../", HtmlUtility.computeImgPath("blog/page.html", "/third-post.html"));
    assertEquals("../", HtmlUtility.computeImgPath("/blog/page.html", "third-post.html"));
    assertEquals("../", HtmlUtility.computeImgPath("/blog/page.html", "/third-post.html"));

    assertEquals("../blog/2013/", HtmlUtility.computeImgPath("archive/page.html", "blog/2013/third-post.html"));
    assertEquals("../blog/2013/", HtmlUtility.computeImgPath("archive/page.html", "/blog/2013/third-post.html"));
    assertEquals("../blog/2013/", HtmlUtility.computeImgPath("/archive/page.html", "blog/2013/third-post.html"));
    assertEquals("../blog/2013/", HtmlUtility.computeImgPath("/archive/page.html", "/blog/2013/third-post.html"));
  }

  @Test
  public void testComputeGitViewerUrl() {
    assertEquals("https://github.com/jmini/jbake-sample/blob/master/src/main/jbake/content/blog/2013/fourth-post.adoc", HtmlUtility.computeGitViewerUrl("C:\\****\\git\\jbake-sample_repo\\src\\main\\jbake\\content\\blog\\2013\\fourth-post.adoc", "jbake-sample_repo", "https://github.com/jmini/jbake-sample/blob/master/"));
    assertEquals("https://github.com/jmini/jbake-sample/blob/master/src/main/jbake/content/blog/2013/fourth-post.adoc", HtmlUtility.computeGitViewerUrl("C:\\****\\git\\jbake-sample_repo\\src\\main\\jbake\\content\\blog\\2013\\fourth-post.adoc", "jbake-sample_repo", "https://github.com/jmini/jbake-sample/blob/master"));
    assertEquals("https://github.com/jmini/jmini.github.io/blob/develop/src/main/jbake/content/blog/2016/2016-01-03-desktop-wallpaper-eclipse.adoc", HtmlUtility.computeGitViewerUrl("C:\\xxxxxx\\git\\jmini.github.io\\src\\main\\jbake\\content\\blog\\2016\\2016-01-03-desktop-wallpaper-eclipse.adoc", "jmini.github.io", "https://github.com/jmini/jmini.github.io/blob/develop"));
  }

}
