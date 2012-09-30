package com.html5gamebook.core;

import static playn.core.PlayN.*;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.GroupLayer;
import playn.core.ResourceCallback;
import java.util.Iterator;
import java.util.ArrayList;


import pythagoras.f.Transform;
import pythagoras.f.Point;



public class EnemyShip {
  float velocity;
  private boolean isMoving = true;
  public static String IMAGE = "images/enemyShip.png";  
  private ImageLayer layer = null;
  int shootInterval;
  int currentTime = 0;
  ArrayList<Bullet> bullets = new ArrayList<Bullet>();
  Ship heroShip;
  Explosion explosion = new Explosion();


  public EnemyShip(Ship heroShip) {
     final float x = graphics().width()+200;
     final float y = (float)Math.random() * 300;
     shootInterval = (int)Math.random()*500 + 1500;
     velocity = (float)Math.random() * 5 + 5;
     Image image = assets().getImage(IMAGE);
     layer = graphics().createImageLayer(image);
     this.heroShip = heroShip;

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

    for (Object b : bullets.toArray()) {
        Bullet bullet = (Bullet)b;
        if (checkBulletCollision(bullet, heroShip)) {
          bullet.destroy();
          bullets.remove(bullet);
        }
        bullet.update(delta);
      }

     if (heroShip != null && heroShip.checkCollision(this)) {
        isMoving(false);
        getLayer().setVisible(false);
        getLayer().destroy();
        Point p = getPosition();
        explosion.spawnExplosion(p.x(),p.y());
      }

    


    currentTime += delta;
    if (currentTime >= shootInterval && getLayer().visible()) {
      //Shoot
      shoot();
      currentTime = 0;
    }

  }

   public ArrayList<Bullet> getBullets() {
    return bullets;
  }

  public boolean checkBulletCollision(Bullet bullet, Ship enemy) {
    if (enemy == null || bullet == null) return false;
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

