package com.html5gamebook.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import com.html5gamebook.core.IntegratedView;

public class IntegratedViewJava {

  public static void main(String[] args) {
    JavaPlatform platform = JavaPlatform.register();
    platform.assets().setPathPrefix("com/html5gamebook/resources");
    PlayN.run(new IntegratedView());
  }
}
