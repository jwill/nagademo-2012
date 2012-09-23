package com.html5gamebook.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import com.html5gamebook.core.Parallax;

public class ParallaxActivity extends GameActivity {

  @Override
  public void main(){
    platform().assets().setPathPrefix("com/html5gamebook/resources");
    PlayN.run(new Parallax());
  }
}
