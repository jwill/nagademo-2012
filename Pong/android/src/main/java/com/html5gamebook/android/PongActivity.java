package com.html5gamebook.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import com.html5gamebook.core.Pong;

public class PongActivity extends GameActivity {

  @Override
  public void main(){
    PlayN.run(new Pong());
  }
}
