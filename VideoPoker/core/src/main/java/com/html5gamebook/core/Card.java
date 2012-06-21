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

  @Override
  public String toString() {
    return "Card:"+this.ordinal+"-"+this.suit;
  }

  ImageLayer getFrontLayer() {
    if (frontLayer == null)
      frontLayer = graphics().createImageLayer(cardFront);
    return frontLayer;
  }

  ImageLayer getBackLayer() {
    if (backLayer == null)
      backLayer = graphics().createImageLayer(cardBack);
    return backLayer;
  }

  void trashCard() {
    graphics().rootLayer().remove(frontLayer);
    graphics().rootLayer().remove(backLayer);
  }

  void flipCard() {

  }
}
