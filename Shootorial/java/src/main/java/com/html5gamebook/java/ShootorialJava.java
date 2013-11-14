package com.html5gamebook.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import com.html5gamebook.core.Shootorial;

public class ShootorialJava extends Shootorial {
    ControllerHandler handler;
  public static void main(String[] args) {
    JavaPlatform.Config config = new JavaPlatform.Config();
    // use config to customize the Java platform, if needed
    JavaPlatform.register(config);
    PlayN.run(new ShootorialJava());
  }

    public ShootorialJava() {
	    super();
	    handler = new ControllerHandler();
	    handler.setListener(this);
    }

    @Override
    public void update(int delta) {
	    handler.processEvents();
	    super.update(delta);
    }
}
