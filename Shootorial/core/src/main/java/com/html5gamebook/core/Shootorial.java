package com.html5gamebook.core;

import static playn.core.PlayN.*;

import playn.core.Key;
import playn.core.Game;
import playn.core.Image;
import playn.core.Surface;
import playn.core.SurfaceLayer;
import java.util.Iterator;
import playn.core.Keyboard;
import playn.core.ImageLayer;
import playn.core.ImmediateLayer;


public class Shootorial implements Game, Keyboard.Listener {
  Ship ship;
  float position = 0, percentDone =0, duration = 15000;
  EnemyShipManager enemyManager = new EnemyShipManager();
 // Image bgImage;

  @Override
  public void init() {
    final Image bgImage = assets().getImage("images/scrollingBackground.jpeg");
    
    graphics().setSize(640,300);
    // create and add background image layer
       
    keyboard().setListener(this);

  //  layer = graphics().createSurfaceLayer(graphics().width(), graphics().height());
  //  graphics().rootLayer().add(layer);

    ImmediateLayer layer = graphics().createImmediateLayer(new ImmediateLayer.Renderer(){
      public void render(Surface surf) {
        drawLayer(surf, bgImage);
      }
      public void drawLayer(Surface surf, Image image) {
        float startX = percentDone * image.width();
        float pixelsLeft = image.width() - startX;

        // Signature is flipped from Canvas
        surf.drawImage(image, 0, 0, pixelsLeft, graphics().height(), startX, 0, pixelsLeft, image.height());
        if (pixelsLeft < graphics().width()) {
          float pixelsToDraw = image.width() - pixelsLeft;
          surf.drawImage(image, pixelsLeft-1, 0, pixelsToDraw, graphics().height(), 0, 0, pixelsToDraw, image.height());
        }
      }
    });

    layer.setDepth(-5);
    graphics().rootLayer().add(layer);

   // graphics().rootLayer().add(layer);
    ship = new Ship();
   
    enemyManager.setHeroShip(ship);
  }

   public void drawLayer(Surface surf, Image image) {
        float startX = percentDone * image.width();
        float pixelsLeft = image.width() - startX;

        // Signature is flipped from Canvas
        surf.drawImage(image, 0, 0, pixelsLeft, graphics().height(), startX, 0, pixelsLeft, image.height());
        if (pixelsLeft < graphics().width()) {
          float pixelsToDraw = image.width() - pixelsLeft;
          surf.drawImage(image, pixelsLeft-1, 0, pixelsToDraw, graphics().height(), 0, 0, pixelsToDraw, image.height());
        }
      }


  @Override
  public void paint(float alpha) {
    // the background automatically paints itself, so no need to do anything here!
    //drawLayer(layer.surface(),bgImage);
  }

  @Override
  public void update(float delta) {
    // Parallax background
    position += delta;
    percentDone = (position) / duration;
    if (percentDone > 1) { 
      percentDone = 0;
      position = 0;
    }

    ship.update(delta);
    enemyManager.update(delta);
  }

  @Override
  public int updateRate() {
    return 30;
  }

  @Override
  public void onKeyDown(Keyboard.Event event) {
    switch(event.key()) {
      case UP: case DOWN: case LEFT: case RIGHT:
        ship.setDirection(event.key());
        break;
      case SPACE:
        ship.shoot();
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
      switch(event.key()) {
        case UP:case DOWN: case RIGHT: case LEFT:
        ship.setDirection(null);
        break;
      }

  }

}
