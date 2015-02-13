package com.html5gamebook.java;

import playn.java.JavaPlatform;

import com.html5gamebook.core.Pong;

public class PongJava {

  public static void main (String[] args) {
    JavaPlatform.Config config = new JavaPlatform.Config();
    // use config to customize the Java platform, if needed
    JavaPlatform plat = new JavaPlatform(config);
    new Pong(plat);
    plat.start();
  }
}
