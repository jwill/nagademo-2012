package com.html5gamebook.core;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class EnemyShipManager {
  private int spawnTime, currentTime;
  private boolean spawning;
  private CopyOnWriteArrayList<EnemyShip> enemies;

  public EnemyShipManager() {
    enemies = new CopyOnWriteArrayList<EnemyShip>();
    spawnTime = 1500;
    currentTime = 0;
    spawning = true;
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

  public void update(float delta) {
    currentTime += delta;
    if (currentTime >= spawnTime) {
      //Spawn new enemy
      spawn();
    }

    Iterator<EnemyShip> iter = enemies.iterator();
    while(iter.hasNext()) {
      EnemyShip enemy = (EnemyShip)iter.next();
      if (enemy.destroyed()) {
        enemies.remove(enemy);
      }
      enemy.update(delta);

    }

  }


}
