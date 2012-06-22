package com.html5gamebook.core;

import playn.core.*;
import static playn.core.PlayN.*;

public class VideoPoker implements Game, Keyboard.Listener {
  Hand hand;
  Deck deck;
  Evaluator evaluator;
  int roundState = 0;
  // Might retrieve from web service in the future
  int tokens = 500, maxBet = 5, currentBet = 1;
  @Override
  public void init() {
    // create and add background image layer
    Image bgImage = assets().getImage("images/bg.png");
    ImageLayer bgLayer = graphics().createImageLayer(bgImage);
    graphics().rootLayer().add(bgLayer);
    deck = new Deck(1);
    hand = new Hand();
    evaluator = new Evaluator();
    for (int i=0; i<5; i++) {
      Card c = deck.dealCard();
      log().debug(c.toString());
      hand.addToHand(c);
    }
    evaluator.evaluate(hand);
    //ImageLayer layer = c.getBackLayer();
    //graphics().rootLayer().add(layer);
    //
    keyboard().setListener(this);
  }
  
  @Override
  public void onKeyDown(Keyboard.Event event) {
    Key key = event.key();
    switch (key) {
      case D:
        deal();
        break;
      case P:
        hand.printHand();
      break;
      // Toggle held cards
      case K1:
        hand.getCards().get(0).toggleState();
        log().debug(key.toString());
        break;
      case K2:
        hand.getCards().get(1).toggleState();
        log().debug(key.toString());
        break;
      case K3:
        hand.getCards().get(2).toggleState();
        log().debug(key.toString());
        break;
      case K4:
        hand.getCards().get(3).toggleState();
        log().debug(key.toString());
        break;
      case K5:
        hand.getCards().get(4).toggleState();
        log().debug(key.toString());
        break;

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


  @Override
  public void paint(float alpha) {
    // the background automatically paints itself, so no need to do anything here!
  }

  @Override
  public void update(float delta) {
  }

  @Override
  public int updateRate() {
    return 25;
  }

  void deal() {
    // Round is over
    if (roundState == 2) {
      hand.clearCards();
      roundState = 0;
    }
    if (roundState == 0) {
      tokens -= currentBet;
      // Update label
    }
    dealHand();
    // TODO Update shown cards
    hand.printHand();
    Object[] winnings = evaluator.evaluate(hand);
    if (roundState == 1) {
      // Award winning hand
      if (winnings != null) {
        tokens += (((Integer)winnings[1]) * currentBet);
        // TODO Update label
        // Play sound
        log().debug("Won.");
        log().debug(tokens +" tokens.");
      }
    }
    roundState++;
  }

  void dealHand() {
    int numCards = hand.cardsNeeded();
    for (int i = 0; i<numCards; i++) {
      hand.addToHand(deck.dealCard());
    }
  }
}
