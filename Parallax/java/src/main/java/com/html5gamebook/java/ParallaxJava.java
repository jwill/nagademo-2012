package com.html5gamebook.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import com.html5gamebook.core.Parallax;

public class ParallaxJava {

  public static void main(String[] args) {
    JavaPlatform.Config config = new JavaPlatform.Config();
    config.width = 300;
    config.height = 200;
    // use config to customize the Java platform, if needed
    JavaPlatform.register(config);
    PlayN.run(new Parallax());
  }
}
