package com.html5gamebook.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import com.html5gamebook.core.MirahExample;

public class MirahExampleActivity extends GameActivity {

  @Override
  public void main(){
    platform().assets().setPathPrefix("com/html5gamebook/resources");
    PlayN.run(new MirahExample());
  }
}
