package com.html5gamebook.core;
import java.util.ArrayList;

public class Deck {
  private ArrayList cards;
  private int numDecks;
  private int [] vals;
  static CardSpriteSheet sheet;

  public Deck(int numDecks) {
    cards = new ArrayList<Card>();
    this.numDecks = numDecks;
    sheet = new CardSpriteSheet("images/sheet.png");
    initCards(null);
  }

  private void initCards(int[] cardValues) {
    String[] ordinals = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
    String[] suits = {"club", "spade", "heart", "diamond"};
    if (cardValues == null) {
      vals = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13};
    } else vals = cardValues;
    for (int k = 0; k<numDecks; k++) {
      for (int j = 0; j < suits.length; j++) {
          for (int i = 0; i < ordinals.length; i++) {
                cards.add( new Card(ordinals[i],vals[i], suits[j])); 
  
          }
      }
    }
    shuffleDecks();
  }

  public Card dealCard() {
    if (cards.size() > 0) {
      return (Card)cards.remove(cards.size()-1);
    } else {
      initCards(vals);
      return (Card)cards.remove(cards.size()-1);
    }
  }

  public ArrayList dealCards(int num) {
    ArrayList cards = new ArrayList<Card>();
    for(int i=0; i<num; i++) {
      cards.add(dealCard());
    }
    return cards;
  }

  private int rand(int max) {
    return (int)Math.floor(Math.random()*max);
  }

  private void swap(int i, int j) {
    Card temp = (Card)this.cards.get(j);
    cards.set(j, cards.get(i));
    cards.set(i, temp);
  }

  private void shuffleDecks() {
    for (int j=0; j<numDecks; j++) {
      for (int i=(numDecks*51); i>=0; i--) {
        int r = rand(i);
        swap(i,r);
      }
    }
  }

}
