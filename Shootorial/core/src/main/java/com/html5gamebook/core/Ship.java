package com.html5gamebook.core;

import static playn.core.PlayN.*;

import playn.core.Key;
import static playn.core.Key.*;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.ResourceCallback;
import pythagoras.f.Transform;

public class Ship {
   public static String IMAGE = "images/ship.png";
   private ImageLayer layer;

   private float x, y;
   private int velocity = 10;   
   private boolean isMoving = false;
   private Key direction = null;

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

   public boolean isMoving() { return isMoving;}
   public void isMoving(boolean state) { isMoving = state; }
   public Key getDirection() { return direction; }
   public void setDirection(Key direction) { 
     if (direction != null) 
       isMoving(true);
     else isMoving(false);
     this.direction = direction; 
   
   }


  public void update(float delta) {
      if (isMoving) {
        switch(direction) {
          case UP:
            moveY(-velocity);
            break;
          case DOWN:
            moveY(velocity);
            break;
          case RIGHT:
            moveX(velocity);
            break;
          case LEFT:
            moveX(-velocity);
            break;
        }
      }
  }

  public Transform getTransform() {
    return layer.transform();
  }

  public void moveX(int x) {
    this.layer.transform().translateX(x);
  }

  public void moveY(int y) {
    this.layer.transform().translateY(y);   
  }

}
