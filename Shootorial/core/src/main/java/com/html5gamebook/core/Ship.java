package com.html5gamebook.core;

import static playn.core.PlayN.*;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.ResourceCallback;
import pythagoras.f.Transform;

public class Ship {
   public static String IMAGE = "images/ship.png";
   private ImageLayer layer;

   private float x, y;

   public Ship(final GroupLayer parentLayer, final float x, final float y) {
     Image image = assets().getImage(IMAGE);
     layer = graphics().createImageLayer(image);

     // Callback for image load
     image.addCallback(new ResourceCallback<Image>() {
         @Override
         public void done(Image image) {
            // layer.setOrigin(0, image.height() / 2f);
             layer.setTranslation(x,y);
             parentLayer.add(layer);
         }

         @Override
         public void error(Throwable throwable) {
             log().error("Error loading image!", throwable);
         }
     });
   }

  public void update(float delta) {
      
  }

  public Transform getTransform() {
    return layer.transform();
  }

  public void moveX(int x) {
    log().debug("Move "+x);
    this.layer.transform().translateX(x);
  }

  public void moveY(int y) {
    this.layer.transform().translateY(y);   
  }

}
