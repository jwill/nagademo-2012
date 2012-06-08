package com.html5gamebook.core;

import static playn.core.PlayN.*;

import playn.core.*;
import pythagoras.f.Transform;

public class Pong implements Game, Keyboard.Listener {
    Ball ball;
    Paddle paddleOne, paddleTwo;

    @Override
    public void init() {
        GroupLayer g = graphics().createGroupLayer();
        ball = new Ball(g, graphics().width()/2, graphics().height()/2);
        paddleOne = new Paddle(g, graphics().width() - 25, graphics().height() / 2f);
        paddleTwo = new Paddle(g, 25, graphics().height() / 2f);
        
        // create and add background image layer
        Image bgImage = assets().getImage("images/bg.png");
        ImageLayer bgLayer = graphics().createImageLayer(bgImage);
        graphics().rootLayer().add(bgLayer);
        graphics().rootLayer().add(g);

        keyboard().setListener(this);
    }

    @Override
    public void paint(float alpha) {
        // the background automatically paints itself, so no need to do anything here!
    }

    @Override
    public void update(float delta) {
        //
        Transform b = ball.getTransform(), p1 = paddleOne.getTransform(), p2 = paddleTwo.getTransform();
        float height = paddleOne.getHeight() / 2;
        if (b.ty() >= p1.ty() - height && b.ty() <= p1.ty() + height)
            if (b.tx() == p1.tx() - paddleOne.getWidth())
                ball.flipEastWest();
        if (b.ty() >= p2.ty() - height && b.ty() <= p2.ty() + height)
            if (b.tx() == p2.tx() + paddleTwo.getWidth())
                ball.flipEastWest();

        ball.update(delta);
        paddleOne.update(delta);
        paddleTwo.update(delta);
    }

    @Override
    public int updateRate() {
        return 25;
    }

    @Override
    public void onKeyDown(Keyboard.Event event) {
        if (event.key().equals(Key.UP)) {
            if (paddleOne.getY() > 0)
                paddleOne.translateY(-10);
        } else if (event.key().equals(Key.DOWN)) {
            if (paddleOne.getY() < graphics().height())
                paddleOne.translateY(10);
        }
        if (event.key().equals(Key.A)) {
            if (paddleTwo.getY() > 0)
                paddleTwo.translateY(-10);
        } else if (event.key().equals(Key.Z)) {
            if (paddleTwo.getY() < graphics().height())
                paddleTwo.translateY(10);
        }
        if (event.key().equals(Key.R)) {
            // reset ball
            ball.getTransform().setTranslation(graphics().width()/2, graphics().height()/2);
            ball.pickDirection();
        }
        if (event.key().equals(Key.ESCAPE)) {
            //System.exit(0);
        }
    }

    @Override
    public void onKeyTyped(Keyboard.TypedEvent event) {
        // Does nothing.
    }

    @Override
    public void onKeyUp(Keyboard.Event event) {
        // Does nothing.
    }


}
