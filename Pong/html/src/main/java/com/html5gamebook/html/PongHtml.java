package com.html5gamebook.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import com.html5gamebook.core.Pong;

public class PongHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform platform = HtmlPlatform.register();
    platform.assets().setPathPrefix("Pong/");
    PlayN.run(new Pong());
  }
}
