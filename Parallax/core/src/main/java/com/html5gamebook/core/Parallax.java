package com.html5gamebook.core;

import static playn.core.PlayN.*;

import playn.core.Game;
import playn.core.Image;
import playn.core.GroupLayer;
import playn.core.ImageLayer;
import playn.core.Surface;
import playn.core.ImmediateLayer;

public class Parallax implements Game {
  GroupLayer rootLayer = graphics().rootLayer();
  private float position = 0, duration = 2500, percentDone = 0;

  @Override
  public void init() {

    graphics().setSize(320,200);
    // create and add background image layer
    final Image layer0Image = assets().getImage("images/Parallax-scroll-example-layer-0.gif");
    final Image layer1Image = assets().getImage("images/Parallax-scroll-example-layer-1.gif");
    final Image layer2Image = assets().getImage("images/Parallax-scroll-example-layer-2.gif");
    final Image layer3Image = assets().getImage("images/Parallax-scroll-example-layer-3.gif");
    
    rootLayer.add(graphics().createImmediateLayer(new ImmediateLayer.Renderer(){
      public void render(Surface surf) {
        surf.drawImage(layer0Image, 0, 0);
        drawLayer(surf,layer1Image);
        drawLayer(surf,layer2Image);
        drawLayer(surf,layer3Image);
        
      }
      public void drawLayer(Surface surf, Image image) {
        float startX = percentDone * image.width();
        float pixelsLeft = image.width() - startX;

        // Signature is flipped from Canvas
        surf.drawImage(image, 0, 0, pixelsLeft, image.height(), startX, 0, pixelsLeft, image.height());
        if (pixelsLeft < graphics().width()) {
          float pixelsToDraw = image.width() - pixelsLeft;
          surf.drawImage(image, pixelsLeft-1, 0, pixelsToDraw, image.height(), 0, 0, pixelsToDraw, image.height());
        }
      }
    }));
  }

  @Override
  public void paint(float alpha) {
    // the background automatically paints itself, so no need to do anything here!
  }

  @Override
  public void update(float delta) {
    position += delta;
    percentDone = (position) / duration;
    if (percentDone > 1) { 
      percentDone = 0;
      position = 0;
    }
  }

  @Override
  public int updateRate() {
    return 25;
  }
}
