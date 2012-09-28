package com.html5gamebook.core;

import static playn.core.PlayN.*;

import playn.core.Key;
import static playn.core.Key.*;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.ResourceCallback;
import pythagoras.f.Transform;
import pythagoras.f.Point;
import java.util.Iterator;
import java.util.ArrayList;



public class Ship {
   public static String IMAGE = "images/ship.png";
   int shotMax = 8;   
   ArrayList<Bullet> bullets = new ArrayList<Bullet>();
   private ImageLayer layer;

   private int velocity = 10;   
   private boolean isMoving = false;
   private Key direction = null;

   public Ship() {
     Image image = assets().getImage(IMAGE);
     layer = graphics().createImageLayer(image);

     // Callback for image load
     image.addCallback(new ResourceCallback<Image>() {
         @Override
         public void done(Image image) {
            // layer.setOrigin(0, image.height() / 2f);
             layer.setTranslation(0,0);
             graphics().rootLayer().add(layer);
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

   public ImageLayer getLayer() {
     return layer;
   }

    public void shoot() {
      if(bullets.size() < shotMax) {
        Bullet b = new Bullet(getTransform().tx() + 75, getTransform().ty() + 3, true);
        bullets.add(b);

      }
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

      Iterator<Bullet> iter = bullets.iterator();
      while(iter.hasNext()) {
        Bullet bullet = (Bullet)iter.next();
        if (bullet.destroyed()) {
          bullets.remove(bullet);
        }
        bullet.update(delta);

      }

  }

  public Transform getTransform() {
    return layer.transform();
  }

  public void moveX(int x) {
    float nextX = this.layer.transform().tx() + x;
    if ((-10 < nextX) && (nextX < graphics().width() - 10)) {
      this.layer.transform().translateX(x);
    }
  }

  public void moveY(int y) {
    float nextY = this.layer.transform().ty() + y;
    if ((-10 < nextY) && (nextY < graphics().height() - 10)) {
      this.layer.transform().translateY(y);   
    }
  }

  public boolean checkCollision(EnemyShip enemy) {
    Transform t = getTransform();
    Point point = enemy.getPosition();
    if (point == null) return false;
    if (enemy.getLayer().visible() == false) return false;
    // tweak to catch edge case
    if ((t.ty()-20 <= point.y()) && ((t.ty() + layer.height()) >= point.y())) {
      if ( (t.tx() <= point.x()) && ((t.tx()+layer.width() ) >= point.x()) ) {
        log().debug("collided "+point);
        return true;
      }
    }
    return false;
  }

  public ArrayList<Bullet> getBullets() {
    return bullets;
  }

  public boolean checkBulletCollision(Bullet bullet, EnemyShip enemy) {
    Transform t = enemy.getTransform();
    Transform b = bullet.getTransform();
    Point point = enemy.getPosition();
    // tweak to catch edge case
    if ((t.ty()-10 <= b.ty()) && ((t.ty() + enemy.getLayer().height()) >= b.ty())) {
      if ( (t.tx() <= b.tx()) && ((t.tx()+enemy.getLayer().width() ) >= b.tx()) ) {
        bullet.destroy();
        bullets.remove(bullet);
        return true;
      }
    }
    return false;
  }


}
