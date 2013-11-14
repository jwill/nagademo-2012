package com.html5gamebook.core;

import static playn.core.PlayN.*;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.GroupLayer;
import playn.core.util.Callback;

import pythagoras.f.Transform;

public class Bullet {
  float velocity = 20;
  public static String IMAGE = "images/bullet.png";
  public static String ALTERNATE_IMAGE = "images/enemy-bullet.png";  
  private ImageLayer layer = null;
  boolean isHero = false;

  public Bullet(final float x, final float y, boolean isHero) {
    Image image;
    this.isHero = isHero;
    if (isHero)
      image = assets().getImage(IMAGE);
    else image = assets().getImage(ALTERNATE_IMAGE);
    layer = graphics().createImageLayer(image);

    // Callback for image load
    image.addCallback(new Callback<Image>() {
      @Override
      public void onSuccess(Image image) {
        // layer.setOrigin(0, image.height() / 2f);
        layer.setTranslation(x,y);
        layer.setScale(0.5f);
        graphics().rootLayer().add(layer);
      }

    @Override
    public void onFailure(Throwable throwable) {
      log().error("Error loading image!", throwable);
    }
    });

  }

  public Transform getTransform() {
    return layer.transform();
  }

  public void destroy() {
    layer.destroy();
  }

  public boolean destroyed() {
    return layer.destroyed();
  }

  public void update(int delta) {
    if (isHero)
      getTransform().translateX(velocity);
    else getTransform().translateX(-velocity);
    if ((graphics().width() < getTransform().tx()) || (0 > getTransform().tx())) {
      layer.destroy();
    }
  }
}
