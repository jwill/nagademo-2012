package com.html5gamebook.core;

import static playn.core.PlayN.*;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.GroupLayer;
import playn.core.ResourceCallback;

import pythagoras.f.Transform;

public class EnemyShip {
  float velocity;
  public static String IMAGE = "images/enemyShip.png";  
  private ImageLayer layer = null;

  public EnemyShip() {
     final float x = 700;
     final float y = (float)Math.random() * 300;
     velocity = (float)Math.random() * 5 + 5;
     Image image = assets().getImage(IMAGE);
     layer = graphics().createImageLayer(image);

     // Callback for image load
     image.addCallback(new ResourceCallback<Image>() {
         @Override
         public void done(Image image) {
            // layer.setOrigin(0, image.height() / 2f);
             layer.setTranslation(x,y);
             graphics().rootLayer().add(layer);
         }

         @Override
         public void error(Throwable throwable) {
             log().error("Error loading image!", throwable);
         }
     });

  }

  public Transform getTransform() {
    return layer.transform();
  }

  public boolean destroyed() {
    return layer.destroyed();
  }

  public void update(float delta) {
    getTransform().translateX(-velocity);
    if (-50 > getTransform().tx()) {
      layer.destroy();
    }
  }
}

