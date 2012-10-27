package com.html5gamebook.core;

import static playn.core.PlayN.*;

import playn.core.Image;
import playn.core.GroupLayer;
import playn.core.ResourceCallback;
import playn.core.Surface;
import playn.core.ImmediateLayer;

import java.util.ArrayList;

public class Explosion {
  public static String IMAGE = "images/explosion_2.png";
  private static Image image;
  private static ArrayList<Image.Region> regions;
  private int currentFrame = 0;
  float position = 0, percentDone = 0, duration = 1500;


  public Explosion() {
    if (Explosion.image == null) {
      Explosion.image = assets().getImage(IMAGE);

      // Callback for image load
      Explosion.image.addCallback(new ResourceCallback<Image>() {
        @Override
        public void done(Image image) {
          makeSubImages(image);
          log().debug("done loading");
        }

      @Override
      public void error(Throwable throwable) {
        //To change body of implemented methods use File | Settings | File Templates.
      }
      });
    }
  }

  void makeSubImages(Image image) {
    regions = new ArrayList<Image.Region>();
    int row = 0;                            // only using top row of sprite sheet
    for (int j = 0; j<3; j++) {
      for(int i = 0; i<24; i++) {
        Image.Region region = image.subImage(i*64, j*64, 64, 64);
        regions.add(region);
      }
    }
  }

  public void update(float delta) {
  }

  public void spawnExplosion(final float x, final float y) {
    final ImmediateLayer layer = graphics().createImmediateLayer(new ImmediateLayer.Renderer(){
      float position = 0, percentDone = 0, duration = 2000;

      public void render(Surface surf) {
        drawLayer(surf);
        if (percentDone == 1) {
          return;
        }

      }
      public void drawLayer(Surface surf) {
        int frame = (int)Math.floor(percentDone * regions.size());
        //log().debug(""+frame);
        if (percentDone == 1) {
          //remove surface
          return;
        }

        Image image = regions.get(frame);
        // Signature is flipped from Canvas
        surf.drawImage(image, x, y, image.width(), image.height(), 0, 0, image.width(), image.height());
        position += 25; // default updateRate
        percentDone = (position) / duration;
      }
    });
    //layer.setDepth(1000);
    graphics().rootLayer().add(layer);
  }


}

