package com.html5gamebook.android;

import playn.android.GameActivity;

import com.html5gamebook.core.Pong;

public class PongJavaActivity extends GameActivity {

  @Override public void main () {
    new Pong(platform());
  }
}
