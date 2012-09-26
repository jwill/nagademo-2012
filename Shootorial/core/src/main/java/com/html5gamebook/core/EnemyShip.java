package com.html5gamebook.core;

import static playn.core.PlayN.*;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.GroupLayer;
import playn.core.ResourceCallback;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;


import pythagoras.f.Transform;


public class EnemyShip {
  float velocity;
  private boolean isMoving = true;
  public static String IMAGE = "images/enemyShip.png";  
  private ImageLayer layer = null;
  int shootInterval;
  int currentTime = 0;
  CopyOnWriteArrayList<Bullet> bullets = new CopyOnWriteArrayList<Bullet>();
  


  public EnemyShip() {
     final float x = 700;
     final float y = (float)Math.random() * 300;
     shootInterval = (int)Math.random()*500 + 1000;
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

  public pythagoras.f.Point getPosition() {
    Transform t = getTransform();
    return new pythagoras.f.Point(t.tx(),t.ty());
  }

  public boolean destroyed() {
    return layer.destroyed();
  }

  public boolean isMoving() { return isMoving;}
  public void isMoving(boolean state) { isMoving = state; }

  public void shoot() {
        Bullet b = new Bullet(getTransform().tx() - 45, getTransform().ty() + 3, false);
        bullets.add(b);
  }


  public void update(float delta) {
    if (isMoving) {
      getTransform().translateX(-velocity);
      if (-50 > getTransform().tx()) {
        layer.destroy();
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


    currentTime += delta;
    if (currentTime >= shootInterval) {
      //Spawn new enemy
      shoot();
      currentTime = 0;
    }

  }

   public CopyOnWriteArrayList<Bullet> getBullets() {
    return bullets;
  }

  public boolean checkBulletCollision(Bullet bullet, Ship enemy) {
    Transform t = enemy.getTransform();
    Transform b = bullet.getTransform();
    // tweak to catch edge case
    if ((t.ty()-10 <= b.ty()) && ((t.ty() + enemy.getLayer().height()) >= b.ty())) {
      if ( (t.tx() <= b.tx()) && ((t.tx()+enemy.getLayer().width() ) >= b.tx()) ) {
        log().debug("hit player");
        bullet.destroy();
        bullets.remove(bullet);
        return true;
      }
    }
    return false;
  }



  public ImageLayer getLayer() {
     return layer;
  }

}

