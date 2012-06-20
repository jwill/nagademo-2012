package com.html5gamebook.core;

import static playn.core.PlayN.*;

import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;

public class VideoPoker implements Game {
  @Override
  public void init() {
    // create and add background image layer
    Image bgImage = assets().getImage("images/bg.png");
    ImageLayer bgLayer = graphics().createImageLayer(bgImage);
    graphics().rootLayer().add(bgLayer);
    Deck d = new Deck(1);
    Card c = d.dealCard();
    log().debug(c.toString());
    ImageLayer layer = c.getBackLayer();
    graphics().rootLayer().add(layer);
  }

  @Override
  public void paint(float alpha) {
    // the background automatically paints itself, so no need to do anything here!
  }

  @Override
  public void update(float delta) {
  }

  @Override
  public int updateRate() {
    return 25;
  }
}
