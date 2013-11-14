package com.html5gamebook.java;

import playn.core.Key;
import playn.core.Keyboard;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

public class ControllerHandler {
  Keyboard.Listener listener;

  public ControllerHandler() {
    try {
      Controllers.create();
      System.out.println(Controllers.getControllerCount());
    } catch (LWJGLException ex) {
    
    }
  }

  public void setListener(Keyboard.Listener listener) {
    this.listener = listener;
  }

  public void processEvents() {
    while(Controllers.next()) {
      Controller c = Controllers.getEventSource();
      if (Controllers.isEventButton()) {
        if (c.isButtonPressed(0)) {
            listener.onKeyDown(new Keyboard.Event.Impl(null, Controllers.getEventNanoseconds(), Key.SPACE));
        }
      }
      if (Controllers.isEventAxis()) {
        // Process D-pad
        float xAxis = c.getXAxisValue();
        float yAxis = c.getYAxisValue();
        if (Controllers.isEventYAxis()) {
        if (yAxis > 0) {
            listener.onKeyDown(new Keyboard.Event.Impl(null, Controllers.getEventNanoseconds(), Key.DOWN));
        } else if (yAxis < 0) {
            listener.onKeyDown(new Keyboard.Event.Impl(null, Controllers.getEventNanoseconds(), Key.UP));  
        }
        }
      
        if (Controllers.isEventXAxis()){
        if (xAxis > 0) {
            listener.onKeyDown(new Keyboard.Event.Impl(null, Controllers.getEventNanoseconds(), Key.RIGHT));
        } else if (xAxis < 0) {
            listener.onKeyDown(new Keyboard.Event.Impl(null, Controllers.getEventNanoseconds(), Key.LEFT));  
        }
        }
      }
    }
    
  }
}
