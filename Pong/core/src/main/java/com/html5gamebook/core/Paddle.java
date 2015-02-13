package com.html5gamebook.core;

import playn.core.*;
import playn.scene.ImageLayer;
import playn.scene.GroupLayer;

import pythagoras.f.Transform;


public class Paddle {
    public static String IMAGE = "images/paddle.png";
    private ImageLayer layer;
    private float height, width;
    private boolean isPlayerOne = true;

    public Paddle(final Platform plat, final GroupLayer parentLayer, final float x, final float y) {
        Image image = plat.assets().getImage(IMAGE);
        layer = new ImageLayer(image);
        final Paddle p = this;
        // Callback for image load
        layer.setOrigin(image.width() / 2f, image.height() / 2f);
        layer.setTranslation(x, y);
        p.height = image.height();
        p.width = image.width();
        parentLayer.add(layer);
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

    }
}
