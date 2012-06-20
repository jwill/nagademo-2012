package com.html5gamebook.core;

import java.util.*;

public class Hand {
  private ArrayList<Card> cards;
  private int pos;

  public Hand() {
    cards = new ArrayList<Card>();
    pos = 0;
  }

  public ArrayList<Card> getCards() {
    return cards;
  }

  public void addToHand(Card card) {
    if (cards.size() < 5) {
      card.data.put("positionInHand", pos++);
      card.data.put("state", false);
      cards.add(card);
    } else {
      Integer num = findCardToReplace();
      if (num != null) {
        card.data.put("state", true);
        card.data.put("positionInHand", num);
        cards.set(num, card);
      }
    }
  }

  Integer findCardToReplace() {
    int i = 0;
    for(Card card : cards) {
      Boolean state = (Boolean)card.data.get("state");
      if (state == false)
        return i;
      i++;
    }
    return null;
  }

  public void flipCards() {
    for (Card card : cards) {
      Boolean frontShown = (Boolean)card.data.get("frontShown");
      if (frontShown != true) 
        card.flipCard();
    }
  }

  public Integer cardsNeeded() {
    if (cards.size() < 5)
      return 5;
    else {
      int numCards = 0;
      for (int i=0; i<5; i++) {
        Card card = (Card)cards.get(i);
        Boolean state = (Boolean)card.data.get("state");
        if (state == false) {
          numCards++;
        }
      }
      return numCards;
    }
  }

  public void clearCards() {
    for(Card card : cards) {
      card.trashCard();
    }
    cards.clear();
    pos = 0;
  }

}
