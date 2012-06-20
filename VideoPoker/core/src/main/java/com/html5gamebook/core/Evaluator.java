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

  public void evaluate(Hand hand) {
    int currentValue = 0;
    String b = checkFourKind(hand);
    String c = checkFullHouse(hand);
    String f = checkTwoPair(hand); 
    String g = checkJacksOrBetter(hand);
    log().debug(b);
    log().debug(c);
    log().debug(f);
    log().debug(g);
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
        if (p.equals("1") || p.compareTo("10") == 1 )
          return "JacksOrBetter";
      }
    }
    return null;
  }

}
