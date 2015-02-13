package com.html5gamebook.html;

import com.google.gwt.core.client.EntryPoint;
import playn.html.HtmlPlatform;
import com.html5gamebook.core.Pong;

public class PongJavaHtml implements EntryPoint {

  @Override public void onModuleLoad () {
    HtmlPlatform.Config config = new HtmlPlatform.Config();
    // use config to customize the HTML platform, if needed
    HtmlPlatform plat = new HtmlPlatform(config);
    plat.assets().setPathPrefix("Pong/");
    new Pong(plat);
    plat.start();
  }
}
