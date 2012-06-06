package com.html5gamebook.core;

import playn.core.*;
import pythagoras.f.Transform;

import static playn.core.PlayN.*;

public class Paddle {
    public static String IMAGE = "images/paddle.png";
    private ImageLayer layer;
    private float height, width;
    private boolean isPlayerOne = true;

    public Paddle(final GroupLayer parentLayer, final float x, final float y) {
        Image image = assets().getImage(IMAGE);
        layer = graphics().createImageLayer(image);
        final Paddle p = this;
        // Callback for image load
        image.addCallback(new ResourceCallback<Image>() {
            @Override
            public void done(Image image) {
                layer.setOrigin(image.width() / 2f, image.height() / 2f);
                layer.setTranslation(x, y);
                p.height = image.height();
                p.width = image.width();
                parentLayer.add(layer);
            }

            @Override
            public void error(Throwable throwable) {
                log().error("Error loading image!", throwable);
            }
        });


    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public Transform getTransform() {
        return layer.transform();
    }

    public void translateY(float y) {
        this.layer.transform().translateY(y);
    }

    public float getY() { return this.layer.transform().ty(); };

    public void update(float delta) {
        // Check collisions and update accordingly


    }


    public boolean isPlayerOne() {
        return isPlayerOne;
    }

    public void setPlayerOne(boolean playerOne) {
        isPlayerOne = playerOne;
    }
}
