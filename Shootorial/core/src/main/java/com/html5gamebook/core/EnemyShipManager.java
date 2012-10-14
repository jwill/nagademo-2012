package com.html5gamebook.core;

import java.util.Iterator;
import static playn.core.PlayN.*;

import playn.core.Layer;
import pythagoras.f.Point;
import pythagoras.f.Rectangle;
import playn.core.AssetWatcher;

import java.util.ArrayList;
import com.google.gwt.event.shared.SimpleEventBus;

public class EnemyShipManager {
  private int spawnTime, currentTime;
  private boolean spawning;
  private Ship heroShip;
  private static Explosion explosion;
  private ArrayList<EnemyShip> enemies;
  private AssetWatcher assetWatcher;
  private static SimpleEventBus eventBus;

  public EnemyShipManager() {
    enemies = new ArrayList<EnemyShip>();
    spawnTime = 1000;
    currentTime = 0;
    spawning = false;
    explosion = new Explosion();
    assetWatcher = new AssetWatcher(new AssetWatcher.Listener(){
      @Override
      public void done() {
        log().debug("spawning.");
        setSpawn(true);
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
    assetWatcher.add(assets().getImage("images/bullet.png"));
    assetWatcher.start();
  }

  public void setSpawnTime(int time) {
    this.spawnTime = time;
  }

  public static void spawnExplosion(EnemyShip enemy, float x, float y) {
    explosion.spawnExplosion(x,y);
    eventBus.fireEvent(new EnemyKilledEvent(enemy));
  }

  public void spawn() {
    enemies.add(new EnemyShip(heroShip));
    currentTime = 0;
  }

  // whether to spawn enemy ships or not
  public void setSpawn(boolean state) {
    this.spawning = state;
    this.currentTime = 0;
  }

  public void killShip(EnemyShip ship) {
    enemies.remove(ship);
  }

  public void setHeroShip(Ship ship) {
    this.heroShip = ship;
  }

  public void setEventBus(SimpleEventBus eventBus) {
    this.eventBus = eventBus;
  }

  public void update(float delta) {
    currentTime += delta;
    if (currentTime >= spawnTime && spawning) {
      //Spawn new enemy
      spawn();
    }

    ArrayList bullets = heroShip.getBullets();    
    for (Object obj : enemies.toArray()) {
    //while(iter.hasNext()) {
      EnemyShip enemy = (EnemyShip)obj;
      for (Object obj2 : bullets.toArray()) {
        Bullet bullet = (Bullet)obj2;
        if (heroShip.checkBulletCollision(bullet, enemy)) {
          enemy.isMoving(false);
          enemy.getLayer().setVisible(false);
          enemy.getLayer().destroy();
          Point p = enemy.getPosition();
          explosion.spawnExplosion(p.x(),p.y());
        }
      }
    

        
      

      if (enemy.destroyed() && enemy.getBullets().size() == 0 ) {
        enemies.remove(enemy);
      }
      enemy.update(delta);

    }

    
  }

  }
