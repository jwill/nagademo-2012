/*
 * Handles events for when the enemies are destroyed
 * Derived from code shared by Ray Cromwell on playn google group
 */ 
package com.html5gamebook.core;

import com.google.web.bindery.event.shared.Event;

public class EnemyKilledEvent extends Event<EnemyKilledHandler> {
  
  private EnemyShip enemyShip;
  public static Type<EnemyKilledHandler> TYPE = new Type<EnemyKilledHandler>();
  
  public EnemyKilledEvent(EnemyShip enemyShip) {
    this.enemyShip = enemyShip;
  }

  @Override
  public Type<EnemyKilledHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(EnemyKilledHandler handler) {
    handler.onEnemyKilled(this);
  }

  public EnemyShip getEnemyShip() {
    return enemyShip;
  }
}
