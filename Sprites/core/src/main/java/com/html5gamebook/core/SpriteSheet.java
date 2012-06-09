package com.html5gamebook.core;

import static playn.core.PlayN.*;

import playn.core.Image;
import playn.core.GroupLayer;
import playn.core.ResourceCallback;
import playn.core.SurfaceLayer;

import java.util.ArrayList;

public class SpriteSheet {
    public static String IMAGE = "images/zombie_0.png";
    private Image image;
    private SurfaceLayer layer;
    private ArrayList<Image.Region> regions;
    private int currentFrame = 0;

    public SpriteSheet(final GroupLayer parent, final float x, final float y) {
        image = assets().getImage(IMAGE);
        layer = graphics().createSurfaceLayer(128,128);

        // Callback for image load
        image.addCallback(new ResourceCallback<Image>() {
            @Override
            public void done(Image image) {
                makeSubImages(image);
                log().debug("done loading");
                parent.add(layer);
            }

            @Override
            public void error(Throwable throwable) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    void makeSubImages(Image image) {
        regions = new ArrayList<Image.Region>();
        int row = 0;                            // only using top row of sprite sheet
        for(int i = 0; i<36; i++) {
            Image.Region region = image.subImage(i*128, row*128, 128, 128);
            regions.add(region);
        }
    }

    public void setCurrentFrame(int currentFrame) {
        //log().debug(""+currentFrame);
        this.currentFrame = currentFrame;
    }

    void paint(float alpha) {
        layer.surface().clear();
        layer.surface().drawImage(regions.get(currentFrame), 0, 0);
    }

    public void update(float delta) {


    }

}
