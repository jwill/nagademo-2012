package com.html5gamebook.core;

import playn.core.*;
import java.util.*;
import static playn.core.PlayN.*;
import static playn.core.Platform.Type.*;




public class VideoPoker implements Game, Keyboard.Listener {
  Hand hand;
  Deck deck;
  Evaluator evaluator;
  int roundState = 2;
  GroupLayer layer;
  Sound winningSound;
  Layer messageLayer;

  // Might retrieve from web service in the future
  int tokens = 500, maxBet = 5, currentBet = 1;
  @Override
  public void init() {
    // create and add background image layer
    graphics().setSize(1024,768);
    Image bgImage = assets().getImage("images/background.png");
    ImageLayer bgLayer = graphics().createImageLayer(bgImage);
    graphics().rootLayer().add(bgLayer);

    deck = new Deck(1);
    hand = new Hand();
    evaluator = new Evaluator();
    
     // For text
    layer = graphics().createGroupLayer();
    graphics().rootLayer().add(layer);


    // Deal throwaway hand 
    // in order to show card back images
    ArrayList<Card> cards = deck.dealCards(5);
    for(Card c : cards) {
      hand.addToHand(c);
    }
    hand.drawCards();

       
    doLayout();
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
        hand.drawCards();
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
      // Increment and decrement bet
      case EQUALS:
        incrementBet();
        break;
      case W:
        winningSound.play();
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
      if (messageLayer != null)
        messageLayer.setAlpha(0.0f);
      roundState = 0;
    }
    if (roundState == 0) {
      tokens -= currentBet;
      // Update label
    }
    dealHand();
    hand.drawCards();

    // TODO Update shown cards
    hand.printHand();
    hand.flipCards();
    Object[] winnings = evaluator.evaluate(hand);
    if (roundState == 1) {
      // Award winning hand
      if (winnings != null) {
        int tokensWon = (((Integer)winnings[1]) * currentBet);
        tokens += tokensWon;
        // TODO Update label
        // Play sound
        log().debug("Won "+ tokensWon);
        log().debug(tokens +" tokens.");
        messageLayer = createMessageText("Won "+tokensWon, 36, 0x0000FF00);
        messageLayer.setTranslation(graphics().width()/2, graphics().height()/2-100);
        graphics().rootLayer().add(messageLayer);

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

  void incrementBet() {
    if (roundState != 1) {
      currentBet++;
      if (currentBet > maxBet) currentBet = 1;
      // TODO Set bet label
      // TODO Update payout pane
      log().debug("Current Bet: "+currentBet);
    }
  }

  void doLayout () {
    // Possibly move this to a service class later
    switch (platformType()) {
      case JAVA:case HTML:
        drawDesktopInterface();
        break;
      case ANDROID:
        drawAndroidInterface();
        break;
    }
  }

  void drawDesktopInterface() {
    log().debug(platformType().toString());

    Layer textLayer = createMessageText("blah blah blah", 16, null);

    textLayer.setTranslation(100,100);
    graphics().rootLayer().add(textLayer);

  //  Root root = iface.createRoot(AxisLayout.vertical().gap(15), SimpleStyles.newSheet());
  //  root.setSize(graphics().width(), graphics().height());
  //  root.addStyles(Style.BACKGROUND.is(Background.solid(0x009900).inset(5)));
  //  layer.add(root.layer);
    
  }

  void drawAndroidInterface() {
    log().debug(platformType().toString());

  }

  Layer createMessageText(String text, int fontSize, Integer fontColor) {
    Font font = graphics().createFont("Sans serif", Font.Style.PLAIN, fontSize);
    if (fontColor == null)
      fontColor = 0xFF660000;
    TextLayout layout = graphics().layoutText(
      text, new TextFormat().withFont(font).withWrapWidth(200).withTextColor(fontColor));
    Layer textLayer = createTextLayer(layout);
    return textLayer;
  }

  protected Layer createTextLayer(TextLayout layout) {
    CanvasImage image = graphics().createImage((int)Math.ceil(layout.width()),
                                               (int)Math.ceil(layout.height()));
    image.canvas().fillText(layout, 0, 0);
    return graphics().createImageLayer(image);
  }

}
