package com.html5gamebook.core;

import java.util.Iterator;
import static playn.core.PlayN.*;

import playn.core.Layer;
import pythagoras.f.Point;
import pythagoras.f.Rectangle;
import playn.core.AssetWatcher;

import java.util.ArrayList;

public class EnemyShipManager {
  private int spawnTime, currentTime;
  private boolean spawning;
  private Ship heroShip;
  private Explosion explosion;
  private ArrayList<EnemyShip> enemies;
  private AssetWatcher assetWatcher;

  public EnemyShipManager() {
    enemies = new ArrayList<EnemyShip>();
    spawnTime = 1500;
    currentTime = 0;
    spawning = false;
    explosion = new Explosion();
    assetWatcher = new AssetWatcher(new AssetWatcher.Listener(){
      @Override
      public void done() {
        log().debug("spawning.");
        spawning = true;
        spawn();
      }
      @Override
      public void error(Throwable e) {
        log().debug(e.toString());
      }
    });
    
    assetWatcher.add(assets().getImage("images/enemyShip.png"));
    assetWatcher.add(assets().getImage("images/explosion_2.png"));
    assetWatcher.add(assets().getImage("images/enemy-bullet.png"));
    assetWatcher.start();
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
    if (currentTime >= spawnTime && spawning) {
      //Spawn new enemy
      spawn();
    }

    ArrayList bullets = heroShip.getBullets();    
    Iterator<EnemyShip> iter = enemies.iterator();
    Iterator<Bullet> bulletIter = bullets.iterator();    
    for (Object obj : enemies.toArray()) {
    //while(iter.hasNext()) {
      EnemyShip enemy = (EnemyShip)obj;
      if (heroShip.checkCollision(enemy)) {
        enemy.isMoving(false);
        enemy.getLayer().setVisible(false);
        if (enemy.getBullets().size() == 0)
          enemies.remove(enemy);
        Point p = enemy.getPosition();
        explosion.spawnExplosion(p.x(),p.y());
      }
      for (Object obj2 : bullets.toArray()) {
      //while(bulletIter.hasNext()) {
        Bullet bullet = (Bullet)obj2;
        if (heroShip.checkBulletCollision(bullet, enemy)) {
          enemy.isMoving(false);
          enemy.getLayer().setVisible(false);
          if (enemy.getBullets().size() == 0)
            enemies.remove(enemy);
          Point p = enemy.getPosition();
          explosion.spawnExplosion(p.x(),p.y());
        }
      }
      // Enemy's bullets
      ArrayList enemyBullets = enemy.getBullets();
      Iterator<Bullet> enemyBulletIter = enemyBullets.iterator();
      for (Object obj3 : enemyBullets.toArray()) {
      //while(enemyBulletIter.hasNext()) {
        Bullet bullet = (Bullet)obj3;
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
