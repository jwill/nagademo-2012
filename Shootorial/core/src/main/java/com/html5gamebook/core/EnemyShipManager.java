package com.html5gamebook.core;

import java.util.Iterator;
import static playn.core.PlayN.*;

import playn.core.Layer;
import pythagoras.f.Point;
import pythagoras.f.Rectangle;

import java.util.concurrent.CopyOnWriteArrayList;

public class EnemyShipManager {
  private int spawnTime, currentTime;
  private boolean spawning;
  private Ship heroShip;
  private Explosion explosion;
  private CopyOnWriteArrayList<EnemyShip> enemies;

  public EnemyShipManager() {
    enemies = new CopyOnWriteArrayList<EnemyShip>();
    spawnTime = 1500;
    currentTime = 0;
    spawning = true;
    explosion = new Explosion();
    spawn();
  }

  public void setSpawnTime(int time) {
    this.spawnTime = time;
  }

  public void spawn() {
    enemies.add(new EnemyShip());
    currentTime = 0;
  }

  // whether to spawn enemy ships or not
  public void setSpawn(boolean state) {
    this.spawning = state;
  }

  public void killShip(EnemyShip ship) {
    enemies.remove(ship);
    // TODO Animation
  }

  public void setHeroShip(Ship ship) {
    this.heroShip = ship;
  }

  public void update(float delta) {
    currentTime += delta;
    if (currentTime >= spawnTime) {
      //Spawn new enemy
      spawn();
    }

    CopyOnWriteArrayList bullets = heroShip.getBullets();    
    Iterator<EnemyShip> iter = enemies.iterator();
    Iterator<Bullet> bulletIter = bullets.iterator();    
    while(iter.hasNext()) {
      EnemyShip enemy = (EnemyShip)iter.next();
      if (heroShip.checkCollision(enemy)) {
        enemy.isMoving(false);
        enemy.getLayer().setVisible(false);
        enemies.remove(enemy);
        Point p = enemy.getPosition();
        explosion.spawnExplosion(p.x(),p.y());
        
      }
      while(bulletIter.hasNext()) {
        Bullet bullet = (Bullet)bulletIter.next();
        if (heroShip.checkBulletCollision(bullet, enemy)) {
          enemy.isMoving(false);
          enemy.getLayer().setVisible(false);
          enemies.remove(enemy);
          Point p = enemy.getPosition();
          explosion.spawnExplosion(p.x(),p.y());
        }
      }
      // Enemy's bullets
      CopyOnWriteArrayList enemyBullets = enemy.getBullets();
      Iterator<Bullet> enemyBulletIter = enemyBullets.iterator();
      while(enemyBulletIter.hasNext()) {
        Bullet bullet = (Bullet)enemyBulletIter.next();
        if (enemy.checkBulletCollision(bullet, heroShip)) {
          bullet.destroy();
          enemyBullets.remove(bullet);
        }
      }

        
      

      if (enemy.destroyed()) {
        enemies.remove(enemy);
      }
      enemy.update(delta);

    }

    
  }


}
