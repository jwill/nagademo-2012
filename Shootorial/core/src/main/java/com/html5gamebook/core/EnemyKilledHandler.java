/*
 * Interface for handling events for when the enemies are destroyed
 * Derived from code shared by Ray Cromwell on playn google group
 */ 
package com.html5gamebook.core;

public interface EnemyKilledHandler {
  void onEnemyKilled(EnemyKilledEvent event);
}
