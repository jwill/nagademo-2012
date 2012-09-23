package com.html5gamebook.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import com.html5gamebook.core.Parallax;

public class ParallaxHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform platform = HtmlPlatform.register();
    platform.assets().setPathPrefix("Parallax/");
    PlayN.run(new Parallax());
  }
}
