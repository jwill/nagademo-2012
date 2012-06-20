package com.html5gamebook.core;

import playn.core.*;
import static playn.core.PlayN.*;

public class Card {
  static Image masterCardBack;
  String suit, ordinal;
  final Image cardFront, cardBack;
  int value; 

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
}
