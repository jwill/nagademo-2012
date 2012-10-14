package com.html5gamebook.core;

import static playn.core.PlayN.*;
import playn.core.Platform;
import playn.core.Touch;
import playn.core.Image;
import playn.core.Layer;
import playn.core.ImageLayer;
import playn.core.GroupLayer;
import playn.core.AssetWatcher;

public class Controls extends Touch.Adapter implements AssetWatcher.Listener {
  private String INNER_CONTROL = "images/inner-control.png";
  private String OUTER_CONTROL = "images/outer-control.png";
  private String TRIGGER_CONTROL = "images/trigger.png";
  private Image innerControl, outerControl, triggerControl;
  private GroupLayer directionControlLayer;
  private ImageLayer triggerControlLayer, innerControlLayer, outerControlLayer;

  AssetWatcher watcher = new AssetWatcher(this);

  public Controls() {
    Platform.Type platformType = platformType();
    if (platformType.equals(Platform.Type.ANDROID) || platformType.equals(Platform.Type.IOS)) {
      log().debug("Start watcher if on a touch enabled OS.");
      innerControl = assets().getImage(INNER_CONTROL);
      outerControl = assets().getImage(OUTER_CONTROL);
      triggerControl = assets().getImage(TRIGGER_CONTROL);


      watcher.start();
    } else {
      log().info("Not implemented on this platform.");
    }
  }

  public Layer getDirectionControls() {
    return directionControlLayer;
  }

  public Layer getTriggerControl() {
    return triggerControlLayer;
  }
  
  @Override
  public void onTouchStart(Touch.Event[] touches) {

  }
  
  @Override
  public void onTouchEnd(Touch.Event[] touches) {

  }

  @Override
  public void onTouchMove(Touch.Event[] touches) {

  }

  @Override
  public void done() {
    log().debug("Finished loading.");
    
    innerControlLayer = graphics().createImageLayer(innerControl);
    outerControlLayer = graphics().createImageLayer(outerControl);

    // TODO: Determine position of controls

    // Inner layer will display at location of interaction
    // Outer layer will actually react to touches
    outerControlLayer.setInteractive(true);
    triggerControlLayer = graphics().createImageLayer(triggerControl);

    directionControlLayer = graphics().createGroupLayer();
    directionControlLayer.add(outerControlLayer);
    directionControlLayer.add(innerControlLayer);

    touch().setListener(this);
    graphics().rootLayer().add(getDirectionControls());
  }

  @Override
  public void error(Throwable e) {

  }
}
