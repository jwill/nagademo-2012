package com.html5gamebook.core;

import java.util.*;
import com.google.common.base.Function;
import com.google.common.collect.Multimap;

import static playn.core.PlayN.*;

public class Evaluator {
  HashMap <String,Integer> basePayouts;
  HashMap <String,String> labels;
  Function<Card,Integer> ordinalHandler;
  Function<Card,String> suitHandler;
  public Evaluator() {
    initMaps();
  }

  public HashMap ordinalHandler(ArrayList<Card> cards) {
    HashMap map = new HashMap<String, Integer>();
    for (Card card : cards) {
      String ord = card.getOrdinal();
      if (map.get(ord) == null) {
        map.put(ord, 1);
      } else {
        Integer val = (Integer)map.get(ord);
        map.put(ord, ++val);
      }
    }
    return map;
  }


  public HashMap suitHandler(ArrayList<Card> cards) {
    HashMap map = new HashMap<String, Integer>();
    for (Card card : cards) {
      String suit = card.getSuit();
      if (map.get(suit) == null) {
        map.put(suit, 1);
      } else {
        Integer val = (Integer)map.get(suit);
        map.put(suit, ++val);
      }
    }
    return map;
  }

  public void initMaps() {
    basePayouts = new HashMap<String,Integer>();
    basePayouts.put("JacksOrBetter", 1);
    basePayouts.put("TwoPair", 2);
    basePayouts.put("ThreeKind", 3);
    basePayouts.put("Straight", 4);
    basePayouts.put("Flush", 6);
    basePayouts.put("FullHouse", 9);
    basePayouts.put("FourKind", 25);
    basePayouts.put("StraightFlush", 50);
    basePayouts.put("RoyalFlush", 250);


    labels = new HashMap<String,String>();
    labels.put("JacksOrBetter", "Jacks or Better");
    labels.put("TwoPair", "Two Pair");
    labels.put("ThreeKind", "Three of a Kind");
    labels.put("Straight", "Straight");
    labels.put("Flush", "Flush");
    labels.put("FullHouse", "Full House");
    labels.put("FourKind", "Four of a Kind");
    labels.put("StraightFlush", "Straight Flush");
    labels.put("RoyalFlush", "Royal Flush");
  }

  public Object[] evaluate(Hand hand) {
    int currentValue = 0;
    ArrayList<String> labels = new ArrayList<String>();
    ArrayList<Integer> values = new ArrayList();
    labels.add(checkFlush(hand));
    labels.add(checkFourKind(hand));
    labels.add(checkFullHouse(hand));
    labels.add(checkThreeKind(hand));
    labels.add(checkStraight(hand));
    labels.add(checkTwoPair(hand)); 
    labels.add(checkJacksOrBetter(hand));
    // Remove nulls
    labels.removeAll(Collections.singleton(null));
    // Get payouts
    for (String label:labels) {
      values.add(basePayouts.get(label));
    }
     log().debug(labels.toString());
    log().debug(values.toString());
    if (labels.size() > 0) {
      return new Object[] {labels.get(0), values.get(0)};
    }
    return null;
  }

  ArrayList checkSize(HashMap map, Integer i) {
    ArrayList list = new ArrayList();
    Iterator iter = map.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry)iter.next();
      if (entry.getValue() == i) {
        list.add(entry.getKey());
      }
    }
    return list;
  }

  ArrayList checkPair(Hand hand) {
    HashMap map = ordinalHandler(hand.getCards());
    ArrayList pairs = checkSize(map,2);

    if (pairs.size() == 0)
      return null;
    else return pairs;
  }

  String checkThreeKind(Hand hand) {
    HashMap map = ordinalHandler(hand.getCards());
    ArrayList threekind = checkSize(map,3);

    if (threekind.size() == 0)
      return null;
    else return "ThreeKind";
  }

  String checkFourKind(Hand hand) {
    HashMap map = ordinalHandler(hand.getCards());
    ArrayList fourkind = checkSize(map,4);

    if (fourkind.size() == 0)
      return null;
    else return "FourKind";
  }

  String checkTwoPair(Hand hand) {
    ArrayList twopair = checkPair(hand);
    if (twopair != null && twopair.size() == 2)
      return "TwoPair";
    return null;
  }
  String checkFullHouse(Hand hand) {
    String triple = checkThreeKind(hand);
    ArrayList pair = checkPair(hand);
    if (pair != null && triple != null )
      return "FullHouse";
    return null;
  }

  String checkJacksOrBetter(Hand hand) {
    ArrayList pairs = checkPair(hand);
    if (pairs != null) {
      for (Object pp : pairs) {
        String p = (String)pp;
        if (p.equals("1") || p.equals("jack") || p.equals("queen") || p.equals("king") )
          return "JacksOrBetter";
      }
    }
    return null;
  }

  String checkStraight(Hand hand) {
    ArrayList values = new ArrayList<Integer>();
    for (Card card : hand.getCards()) {
      Integer val = card.getValue();
      values.add(val);
    }
    Collections.sort(values);
    if (values.toArray() == new Object[]{1, 10, 11, 12, 13})
      return "Straight";

    Integer startValue = (Integer)values.get(0);
    for (int i = 0; i<5 ; i++) {
      Integer currentValue = (Integer)values.get(i);
      if ((startValue + i) != currentValue)
        return null;
    }
    return "Straight";
  }
  
  String checkStraightFlush(Hand hand) {
    String straight = checkStraight(hand);
    if (!straight.equals(null)) {
      HashMap suits = suitHandler(hand.getCards());
      if (suits.size() == 0)
        return "StraightFlush";
      else return "Straight";
    }
    return null;
  }

  String checkRoyalFlush(Hand hand) {
    ArrayList values = new ArrayList<Integer>();
    for (Card card : hand.getCards()) {
      Integer val = card.getValue();
      values.add(val);
    }
    Collections.sort(values);
    if (values.toArray() == new Object[]{1, 10, 11, 12, 13})
      return "RoyalFlush";
    else return null;

  }

  String checkFlush(Hand hand) {
    HashMap suits = suitHandler(hand.getCards());
    if (suits.size() == 0) {
      String royal = checkRoyalFlush(hand);
      String straightFlush = checkStraightFlush(hand);
      if (!royal.equals(null)) return royal;
      if (!straightFlush.equals(null)) return straightFlush;
      return "Flush";
    }
    return null;
  }
}
