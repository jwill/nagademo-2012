package com.html5gamebook.core;

import playn.core.Image;
import playn.core.Platform;
import playn.core.Graphics;
import playn.scene.ImageLayer;
import playn.scene.GroupLayer;

import playn.scene.SceneGame;
import pythagoras.f.Dimension;

public class Pong extends SceneGame {
  Ball ball;
  Paddle paddleOne, paddleTwo;

  public Pong (Platform plat) {
    super(plat, 33); // update our "simulation" 33ms (30 times per second)

    GroupLayer gl = new GroupLayer();
    Dimension g = (Dimension)plat.graphics().viewSize;

    ball = new Ball(plat, gl, g.width()/2, g.height()/2);
    paddleOne = new Paddle(plat, gl, g.width() - 80, g.height() / 2f);
    paddleTwo = new Paddle(plat, gl, 5, g.height() / 2f);

    // create and add background image layer
    Image bgImage = plat.assets().getImage("images/bg.png");
    ImageLayer bgLayer = new ImageLayer(bgImage);
    // scale the background to fill the screen
    bgLayer.setSize(plat.graphics().viewSize);
    rootLayer.add(bgLayer);
    rootLayer.add(gl);
  }
}
