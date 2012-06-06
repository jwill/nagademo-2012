package com.html5gamebook.core;

import static playn.core.PlayN.*;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.ResourceCallback;
import pythagoras.f.Transform;

public class Ball {
   public static String IMAGE = "images/ball.png";
   private ImageLayer layer;
   private Direction direction;
   private float x, y;

   public Ball(final GroupLayer parentLayer, final float x, final float y) {
       Image image = assets().getImage(IMAGE);
       layer = graphics().createImageLayer(image);
       pickDirection();

       // Callback for image load
       image.addCallback(new ResourceCallback<Image>() {
           @Override
           public void done(Image image) {
               layer.setOrigin(image.width() / 2f, image.height() / 2f);
               layer.setTranslation(x,y);
               parentLayer.add(layer);
           }

           @Override
           public void error(Throwable throwable) {
               log().error("Error loading image!", throwable);
           }
       });
   }

   public void update(float delta) {
       Transform t = layer.transform();
       // Check collisions and update accordingly
       if (t.ty() <= 0 + layer.height()/2 || t.ty() >= graphics().height() - layer.height()/2)
           direction = Direction.flipNorthSouth(direction);
      // if (t.tx() <=0 || t.tx() >= graphics().width())
      //     direction = Direction.flipEastWest(direction);
       t.translateX( direction.x()*1 );
       t.translateY(direction.y()*1);

   }

   public Transform getTransform() {
       return layer.transform();
   }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    // Ported from JS, excuse the sloppiness
    public void flipNorthSouth () {
        char c = direction.toString().charAt(0);
        if (c == 'N') {
            direction = Direction.valueOf("S"+direction.toString().charAt(1));
        } else {
            direction = Direction.valueOf("N"+direction.toString().charAt(1));
        }
    }
    public void  flipEastWest () {
        char c = direction.toString().charAt(1);
        if (c == 'E') {
            direction = Direction.valueOf(direction.toString().charAt(0)+"W");
        } else {
            direction = Direction.valueOf(direction.toString().charAt(0)+"E");
        }
    }

    public void pickDirection() {
        int num = (int)Math.floor(Math.random() * 4);
        direction = Direction.values()[num];
    }

    public enum Direction {
        SE(-1, -1),
        SW(1,-1),
        NE(-1,1),
        NW(1,1);

        private final int deltaX;
        private final int deltaY;

        Direction(int x, int y) {
            this.deltaX = x;
            this.deltaY = y;
        }

        public int x() { return deltaX; }
        public int y() { return deltaY; }

        // Ported from JS, excuse the sloppiness
        public static Direction flipNorthSouth (Direction d) {
            char c = d.toString().charAt(0);
            if (c == 'N') {
                return Direction.valueOf("S"+d.toString().charAt(1));
            } else {
                return Direction.valueOf("N"+d.toString().charAt(1));
            }
        }
        public static Direction flipEastWest (Direction d) {
            char c = d.toString().charAt(1);
            if (c == 'E') {
                return Direction.valueOf(d.toString().charAt(0)+"W");
            } else {
                return Direction.valueOf(d.toString().charAt(0)+"E");
            }
        }


    };
}
