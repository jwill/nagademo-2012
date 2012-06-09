package com.html5gamebook.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import com.html5gamebook.core.Sprites;

public class SpritesHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform platform = HtmlPlatform.register();
    platform.assets().setPathPrefix("Sprites/");
    PlayN.run(new Sprites());
  }
}
