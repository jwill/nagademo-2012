package com.html5gamebook.core;

import static playn.core.PlayN.*;

import playn.core.Key;
import playn.core.Game;
import playn.core.Image;
import playn.core.Keyboard;
import playn.core.ImageLayer;

public class Shootorial implements Game, Keyboard.Listener {
  Ship ship;
  int velocity = 10;

  @Override
  public void init() {
    // create and add background image layer
    Image bgImage = assets().getImage("images/bg.png");
    ImageLayer bgLayer = graphics().createImageLayer(bgImage);
    graphics().rootLayer().add(bgLayer);
    
    ship = new Ship(graphics().rootLayer(), 0,0);
    keyboard().setListener(this);
    
  }

  @Override
  public void paint(float alpha) {
    // the background automatically paints itself, so no need to do anything here!
  }

  @Override
  public void update(float delta) {
    ship.update(delta);
  }

  @Override
  public int updateRate() {
    return 25;
  }

  @Override
  public void onKeyDown(Keyboard.Event event) {
    switch(event.key()) {
      case UP:
        ship.moveY(-velocity);
        break;
      case DOWN:
        ship.moveY(velocity);
        break;
      case RIGHT:
        ship.moveX(velocity);
        break;
      case LEFT:
        ship.moveX(-velocity);
        break;
    }
  }

  @Override
  public void onKeyTyped(Keyboard.TypedEvent event) {
      // Does nothing.
  }

  @Override
  public void onKeyUp(Keyboard.Event event) {
      // Does nothing.
  }

}
