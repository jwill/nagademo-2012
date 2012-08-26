package com.html5gamebook.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import com.html5gamebook.core.IntegratedView;

public class IntegratedViewHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform platform = HtmlPlatform.register();
    platform.assets().setPathPrefix("IntegratedView/");
    PlayN.run(new IntegratedView());
  }
}
