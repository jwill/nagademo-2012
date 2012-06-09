package com.html5gamebook.core;

import static playn.core.PlayN.*;

import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;

import org.pushingpixels.trident.Timeline;

public class Sprites implements Game {
  SpriteSheet sheet;
  @Override
  public void init() {
    // create and add background image layer
    Image bgImage = assets().getImage("images/bg.png");
    ImageLayer bgLayer = graphics().createImageLayer(bgImage);
    GroupLayer g = graphics().createGroupLayer();
    sheet = new SpriteSheet(g,100,100);
    graphics().rootLayer().add(bgLayer);
    graphics().rootLayer().add(g);

    Timeline timeline = new Timeline(sheet);
    timeline.addPropertyToInterpolate("currentFrame", 0, 36);
    timeline.setDuration(5000);
    timeline.playLoop(Timeline.RepeatBehavior.LOOP);
  }

  @Override
  public void paint(float alpha) {
    // the background automatically paints itself, so no need to do anything here!
    sheet.paint(alpha);
  }

  @Override
  public void update(float delta) {
      sheet.update(delta);
  }

  @Override
  public int updateRate() {
    return 25;
  }
}
