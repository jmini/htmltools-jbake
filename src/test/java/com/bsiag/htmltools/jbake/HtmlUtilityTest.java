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
    assertEquals("blog/2013/", HtmlUtility.computeImgPath("index.html", "blog/2013/third-post.html"));
    assertEquals("", HtmlUtility.computeImgPath("index.html", "some-post.html"));
    assertEquals("blog/2013/", HtmlUtility.computeImgPath("index.html", "blog/2013/"));
  }

}
