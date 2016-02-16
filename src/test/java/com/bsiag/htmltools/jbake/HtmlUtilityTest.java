package com.bsiag.htmltools.jbake;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HtmlUtilityTest {

  @Test
  public void testFixHtmlImgSrc() {
    String html = "<p><span class=\"image\"><img src=\"jbake-logo.png\" alt=\"jbake-logo\"></span></p>";
    String output = HtmlUtility.fixHtml(html, "index.html", "blog/2013/third-post.html");
    String expected = "<p><span class=\"image\"><img src=\"blog/2013/jbake-logo.png\" alt=\"jbake-logo\"></span></p>";
    assertEquals(expected, output);
  }

  @Test
  public void testFixHtmlAHref() {
    String html = "<p>See <a href=\"third-post.adoc\">this post</a></p>";
    String output = HtmlUtility.fixHtml(html, "index.html", "blog/2013/third-post.html");
    String expected = "<p>See <a href=\"blog/2013/third-post.adoc\">this post</a></p>";
    assertEquals(expected, output);
  }

  @Test
  public void testFixHtmlAHrefAnchor() {
    String html = "<p>Link to the <a href=\"#nam\">text section</a>.</p>";
    String output = HtmlUtility.fixHtml(html, "index.html", "blog/2013/third-post.html");
    String expected = "<p>Link to the <a href=\"blog/2013/third-post.html#nam\">text section</a>.</p>";
    assertEquals(expected, output);
  }

  @Test
  public void testShouldFixLink() throws Exception {
    assertEquals(false, HtmlUtility.shouldFixLink(null));
    assertEquals(false, HtmlUtility.shouldFixLink(""));
    assertEquals(false, HtmlUtility.shouldFixLink("mailto:me@email.com"));
    assertEquals(false, HtmlUtility.shouldFixLink("http://somesite.com"));
    assertEquals(false, HtmlUtility.shouldFixLink("https://www.this.fr"));
    assertEquals(false, HtmlUtility.shouldFixLink("ftp://xx@me.com:10/"));
    assertEquals(false, HtmlUtility.shouldFixLink("ssh://me@it.org/top"));

    assertEquals(true, HtmlUtility.shouldFixLink("some-post.html"));
    assertEquals(true, HtmlUtility.shouldFixLink("index.html#top"));
    assertEquals(true, HtmlUtility.shouldFixLink("#top"));

  }

  @Test
  public void testComputeRelPath() {
    assertEquals("", HtmlUtility.computeRelPath("blog/2013/third-post.html", "blog/2013/third-post.html"));
    assertEquals("", HtmlUtility.computeRelPath("", ""));

    assertEquals("", HtmlUtility.computeRelPath("index.html", "some-post.html"));
    assertEquals("", HtmlUtility.computeRelPath("index.html", "/some-post.html"));
    assertEquals("", HtmlUtility.computeRelPath("/index.html", "some-post.html"));
    assertEquals("", HtmlUtility.computeRelPath("/index.html", "/some-post.html"));

    assertEquals("", HtmlUtility.computeRelPath("blog/index.html", "blog/some-post.html"));
    assertEquals("", HtmlUtility.computeRelPath("blog/index.html", "/blog/some-post.html"));
    assertEquals("", HtmlUtility.computeRelPath("/blog/index.html", "blog/some-post.html"));
    assertEquals("", HtmlUtility.computeRelPath("/blog/index.html", "/blog/some-post.html"));

    assertEquals("blog/2013/", HtmlUtility.computeRelPath("index.html", "blog/2013/third-post.html"));
    assertEquals("blog/2013/", HtmlUtility.computeRelPath("index.html", "/blog/2013/third-post.html"));
    assertEquals("blog/2013/", HtmlUtility.computeRelPath("/index.html", "blog/2013/third-post.html"));
    assertEquals("blog/2013/", HtmlUtility.computeRelPath("/index.html", "/blog/2013/third-post.html"));

    assertEquals("blog/2014/", HtmlUtility.computeRelPath("index.html", "blog/2014/"));
    assertEquals("blog/2014/", HtmlUtility.computeRelPath("index.html", "/blog/2014/"));
    assertEquals("blog/2014/", HtmlUtility.computeRelPath("/index.html", "blog/2014/"));
    assertEquals("blog/2014/", HtmlUtility.computeRelPath("/index.html", "/blog/2014/"));

    assertEquals("2015/", HtmlUtility.computeRelPath("blog/page.html", "blog/2015/third-post.html"));
    assertEquals("2015/", HtmlUtility.computeRelPath("blog/page.html", "/blog/2015/third-post.html"));
    assertEquals("2015/", HtmlUtility.computeRelPath("/blog/page.html", "blog/2015/third-post.html"));
    assertEquals("2015/", HtmlUtility.computeRelPath("/blog/page.html", "/blog/2015/third-post.html"));

    assertEquals("2012/", HtmlUtility.computeRelPath("blog/page.html", "blog/2012/"));
    assertEquals("2012/", HtmlUtility.computeRelPath("blog/page.html", "/blog/2012/"));
    assertEquals("2012/", HtmlUtility.computeRelPath("/blog/page.html", "blog/2012/"));
    assertEquals("2012/", HtmlUtility.computeRelPath("/blog/page.html", "/blog/2012/"));

    assertEquals("../", HtmlUtility.computeRelPath("blog/page.html", "third-post.html"));
    assertEquals("../", HtmlUtility.computeRelPath("blog/page.html", "/third-post.html"));
    assertEquals("../", HtmlUtility.computeRelPath("/blog/page.html", "third-post.html"));
    assertEquals("../", HtmlUtility.computeRelPath("/blog/page.html", "/third-post.html"));

    assertEquals("../blog/2013/", HtmlUtility.computeRelPath("archive/page.html", "blog/2013/third-post.html"));
    assertEquals("../blog/2013/", HtmlUtility.computeRelPath("archive/page.html", "/blog/2013/third-post.html"));
    assertEquals("../blog/2013/", HtmlUtility.computeRelPath("/archive/page.html", "blog/2013/third-post.html"));
    assertEquals("../blog/2013/", HtmlUtility.computeRelPath("/archive/page.html", "/blog/2013/third-post.html"));
  }

  @Test
  public void testFileName() throws Exception {
    assertEquals("page.html", HtmlUtility.fileName("page.html"));
    assertEquals("page.html", HtmlUtility.fileName("some/page.html"));
    assertEquals("page.html", HtmlUtility.fileName("some\\page.html"));
    assertEquals("page.html", HtmlUtility.fileName("/some\\page.html"));

    assertEquals("", HtmlUtility.fileName(null));
    assertEquals("", HtmlUtility.fileName(""));
  }

  @Test
  public void testComputeGitViewerUrl() {
    assertEquals("https://github.com/jmini/jbake-sample/blob/master/src/main/jbake/content/blog/2013/fourth-post.adoc", HtmlUtility.computeGitViewerUrl("C:\\****\\git\\jbake-sample_repo\\src\\main\\jbake\\content\\blog\\2013\\fourth-post.adoc", "jbake-sample_repo", "https://github.com/jmini/jbake-sample/blob/master/"));
    assertEquals("https://github.com/jmini/jbake-sample/blob/master/src/main/jbake/content/blog/2013/fourth-post.adoc", HtmlUtility.computeGitViewerUrl("C:\\****\\git\\jbake-sample_repo\\src\\main\\jbake\\content\\blog\\2013\\fourth-post.adoc", "jbake-sample_repo", "https://github.com/jmini/jbake-sample/blob/master"));
    assertEquals("https://github.com/jmini/jmini.github.io/blob/develop/src/main/jbake/content/blog/2016/2016-01-03-desktop-wallpaper-eclipse.adoc", HtmlUtility.computeGitViewerUrl("C:\\xxxxxx\\git\\jmini.github.io\\src\\main\\jbake\\content\\blog\\2016\\2016-01-03-desktop-wallpaper-eclipse.adoc", "jmini.github.io", "https://github.com/jmini/jmini.github.io/blob/develop"));
  }

}
