package com.html5gamebook.core;

import static playn.core.PlayN.*;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.GroupLayer;
import playn.core.ResourceCallback;

import pythagoras.f.Transform;

public class Bullet {
  float velocity = 20;
  public static String IMAGE = "images/bullet.png";  
  private ImageLayer layer = null;

  public Bullet(final float x, final float y) {
     Image image = assets().getImage(IMAGE);
     layer = graphics().createImageLayer(image);

     // Callback for image load
     image.addCallback(new ResourceCallback<Image>() {
         @Override
         public void done(Image image) {
            // layer.setOrigin(0, image.height() / 2f);
             layer.setTranslation(x,y);
             layer.setScale(0.5f);
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
    getTransform().translateX(velocity);
    if (graphics().width() < getTransform().tx()) {
      layer.destroy();
    }
  }
}
