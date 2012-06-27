package com.html5gamebook.core;

import java.util.*;
import playn.core.*;
import static playn.core.PlayN.*;

public class Card {
  static Image masterCardBack;
  String suit, ordinal;
  final Image cardFront, cardBack;
  ImageLayer frontLayer, backLayer;
  int value;
  boolean frontShown = false;
  Layer heldLayer;
  float xPos = 0f, yPos = 0.0f;
  HashMap data = new HashMap();

  public Card(String ordinal, int value, String suit) {
    this.ordinal = ordinal;
    this.value = value;
    this.suit = suit;

    // load card back if not loaded
    if (Card.masterCardBack == null) {
      Card.masterCardBack = assets().getImage("images/back.png");
      Card.masterCardBack.addCallback(new ResourceCallback<Image>() {
        @Override
        public void done(Image image) {
          log().debug("loaded");
          
        }

        @Override
        public void error(Throwable throwable) {
        }
      
      });
    }
    
    this.cardBack = Card.masterCardBack;
    

    // load front of card
    String filename = "images/"+this.ordinal+"_"+this.suit+".png";
    cardFront = assets().getImage(filename);
  }

  String getOrdinal() {
    return ordinal;
  }

  int getValue() {
    return value;
  }

  String getSuit() {
    return suit;
  }

  void toggleState() {
    boolean state = (Boolean)data.get("state");
    data.put("state", !state);
    log().debug(this.toString() + " " + !state);
    if (!state == true){
      graphics().rootLayer().add(getHeldLayer());
    }
    else {
      try{graphics().rootLayer().remove(heldLayer);}catch(Exception ex){}
    }
  }

  int positionInHand() {
    int pos = (Integer)data.get("positionInHand");
    return pos;
  }

  @Override
  public String toString() {
    return "Card:"+this.ordinal+"-"+this.suit+"-"+data.get("state");
  }

  ImageLayer getFrontLayer() {
    if (frontLayer == null) {
      frontLayer = graphics().createImageLayer(cardFront);
      frontLayer.setInteractive(true);
      frontLayer.addListener(new Pointer.Adapter() {
        @Override
        public void onPointerStart(Pointer.Event evt) {
          log().debug("Mouse clicked");
          toggleState();
        }
      });
      
    }
    return frontLayer;
  }

  ImageLayer getBackLayer() {
    if (backLayer == null)
      backLayer = graphics().createImageLayer(cardBack);
    return backLayer;
  }

  Layer getHeldLayer() {
    if (heldLayer == null) {
    Font font = graphics().createFont("Sans serif", Font.Style.PLAIN, 16);
      
      TextLayout layout = graphics().layoutText(
      "HELD", new TextFormat().withFont(font).withWrapWidth(200).withTextColor(0xFF660000));
      heldLayer = createTextLayer(layout);
     // heldLayer.setAlpha(1.0f);
    }
    return heldLayer;
  }

  void drawCard() {
    graphics().rootLayer().add(getFrontLayer());
    graphics().rootLayer().add(getBackLayer());
    //graphics().rootLayer().add(getHeldLayer());

    
    log().debug("x: "+findXPos());
    
    getHeldLayer().setTranslation(findXPos()+50, 360);
    getFrontLayer().setTranslation(findXPos(), 400);
    getBackLayer().setTranslation(findXPos(), 400);

  }

  int findXPos() {
    return 10 + (positionInHand() * 189);
  }

  void trashCard() {
    try {
    graphics().rootLayer().remove(heldLayer);
    graphics().rootLayer().remove(frontLayer);
    graphics().rootLayer().remove(backLayer);
    } catch(Exception e) {}
  }

  void flipCard() {
  //  if (((Boolean)data.get("hidden")) == true)
  //    return;

    frontShown = !frontShown;
    if (frontShown) {
      getFrontLayer().setAlpha(1.0f);
      getBackLayer().setAlpha(0.0f);
    } else {
      getFrontLayer().setAlpha(0.0f);
      getBackLayer().setAlpha(1.0f);
    }
  }



  
  protected Layer createTextLayer(TextLayout layout) {
    CanvasImage image = graphics().createImage((int)Math.ceil(layout.width()),
                                               (int)Math.ceil(layout.height()));
    image.canvas().fillText(layout, 0, 0);
    return graphics().createImageLayer(image);
  }

}
