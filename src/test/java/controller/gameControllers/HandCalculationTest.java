package controller.gameControllers;

import model.Card;
import model.Deck;
import model.Suit;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HandCalculationTest {


     @Test
     void calcPwrBarLvl() {
          Deck deck = new Deck();
          deck.shuffle();

          ArrayList<Card> cards = new ArrayList<Card>();
          for(int i = 0; i < 7; i++) {
               cards.add(deck.getCard());
          }
          ArrayList<String> aiCards = new ArrayList<String>();
          for(Card card : cards) {
               char c = card.getCardSuit().charAt(0);
               String s = card.getCardValue() + "," + String.valueOf(c);
               aiCards.add(s);
          }

          HandCalculation handCalc = new HandCalculation(aiCards, cards);


          assertTrue(handCalc.calcPwrBarLvl() >= 1 && handCalc.calcPwrBarLvl() <= 4);
     }

     @Test
     void pwrBarLvlOnTurnOne() {
          Deck deck = new Deck();
          deck.shuffle();

          ArrayList<Card> cards = new ArrayList<Card>();
          for(int i = 0; i < 7; i++) {
               cards.add(deck.getCard());
          }
          ArrayList<String> aiCards = new ArrayList<String>();
          for(Card card : cards) {
               char c = card.getCardSuit().charAt(0);
               String s = card.getCardValue() + "," + String.valueOf(c);
               aiCards.add(s);
          }

          HandCalculation handCalc = new HandCalculation(aiCards, cards);

          assertTrue(handCalc.pwrBarLvlOnTurnOne() >= 1 && handCalc.pwrBarLvlOnTurnOne()<= 4);
     }

     @Test
     void pwrBarLvlOnTurnTwo() {
          Deck deck = new Deck();
          deck.shuffle();

          ArrayList<Card> cards = new ArrayList<Card>();
          for(int i = 0; i < 7; i++) {
               cards.add(deck.getCard());
          }
          ArrayList<String> aiCards = new ArrayList<String>();
          for(Card card : cards) {
               char c = card.getCardSuit().charAt(0);
               String s = card.getCardValue() + "," + String.valueOf(c);
               aiCards.add(s);
          }

          HandCalculation handCalc = new HandCalculation(aiCards, cards);

          assertTrue(handCalc.pwrBarLvlOnTurnTwo() >= 1 && handCalc.pwrBarLvlOnTurnTwo()<= 4);
     }

     @Test
     void pwrBarLvlOnTurnThree() {
          Deck deck = new Deck();
          deck.shuffle();

          ArrayList<Card> cards = new ArrayList<Card>();
          for(int i = 0; i < 7; i++) {
               cards.add(deck.getCard());
          }
          ArrayList<String> aiCards = new ArrayList<String>();
          for(Card card : cards) {
               char c = card.getCardSuit().charAt(0);
               String s = card.getCardValue() + "," + String.valueOf(c);
               aiCards.add(s);
          }

          HandCalculation handCalc = new HandCalculation(aiCards, cards);

          assertTrue(handCalc.pwrBarLvlOnTurnThree() >= 1 && handCalc.pwrBarLvlOnTurnThree()<= 4);
     }

     @Test
     void pwrBarLvlOnTurnFour() {
          Deck deck = new Deck();
          deck.shuffle();

          ArrayList<Card> cards = new ArrayList<Card>();
          for(int i = 0; i < 7; i++) {
               cards.add(deck.getCard());
          }
          ArrayList<String> aiCards = new ArrayList<String>();
          for(Card card : cards) {
               char c = card.getCardSuit().charAt(0);
               String s = card.getCardValue() + "," + String.valueOf(c);
               aiCards.add(s);
          }

          HandCalculation handCalc = new HandCalculation(aiCards, cards);

          assertTrue(handCalc.pwrBarLvlOnTurnFour() >= 1 && handCalc.pwrBarLvlOnTurnFour()<= 4);
     }

     @Test
     void newHelp() { //7 cards
          Deck deck = new Deck();
          deck.shuffle();

          ArrayList<Card> cards = new ArrayList<Card>();
          for(int i = 0; i < 7; i++) {
               cards.add(deck.getCard());
          }
          ArrayList<String> aiCards = new ArrayList<String>();
          for(Card card : cards) {
               char c = card.getCardSuit().charAt(0);
               String s = card.getCardValue() + "," + String.valueOf(c);
               aiCards.add(s);
          }

          String[] help = {"HIGH CARD", "ONE PAIR", "TWO PAIR", "THREE OF A KIND", "FOUR OF A KIND",
                    "FLUSH", "STRAIGHT", "FULL HOUSE"};

          ArrayList<String> bestHands = new ArrayList<String>();
          for(int i = 0; i < 8; i++) {
               bestHands.add(help[i]);
          }

          HandCalculation handCalc = new HandCalculation(aiCards, cards);

          assertTrue(bestHands.contains(handCalc.newHelp()));
     }

     @Test
     void handCalculation_WithNullAsInput() { //null
          Throwable exception = assertThrows(Exception.class, () -> new HandCalculation(null, null));
          assertEquals("Cannot invoke \"java.util.ArrayList.size()\" because \"aiCards\" is null", exception.getMessage());
     }

     @Test
     void handCalculation_WithInvalidAmountOfCards() { //Less than 2 cards
          Deck deck = new Deck();
          deck.shuffle();

          ArrayList<Card> cards = new ArrayList<Card>();
          for(int i = 0; i < 1; i++) {
               cards.add(deck.getCard());
          }
          ArrayList<String> aiCards = new ArrayList<String>();
          for(Card card : cards) {
               char c = card.getCardSuit().charAt(0);
               String s = card.getCardValue() + "," + String.valueOf(c);
               aiCards.add(s);
          }

          Throwable exception = assertThrows(Exception.class, () -> new HandCalculation(new ArrayList<String>(), new ArrayList<Card>()));
          assertEquals("Index 0 out of bounds for length 0", exception.getMessage());
     }

     @Test
     void help() { //7 cards
          Deck deck = new Deck();
          deck.shuffle();

          ArrayList<Card> cards = new ArrayList<Card>();
          for(int i = 0; i < 7; i++) {
               cards.add(deck.getCard());
          }
          ArrayList<String> aiCards = new ArrayList<String>();
          for(Card card : cards) {
               char c = card.getCardSuit().charAt(0);
               String s = card.getCardValue() + "," + String.valueOf(c);
               aiCards.add(s);
          }

          String[] helper = {
                    "Ingenting, tyv??rr...",
                    "'ONE-PAIR' i ",
                    "'TWO PAIRS'  i ",
                    "'THREE OF A KIND' i ",
                    "En 'STRAIGHT'!! Du har 5/5.\n",
                    "En 'FLUSH' i ",
                    "'FULL HOUSE' med ",
                    "'FOUR OF A KIND' i ",
                    "'STRAIGHT FLUSH' i f??rgen "
          };


          HandCalculation handCalc = new HandCalculation(aiCards, cards);

          assertTrue(handCalc.Help().contains(helper[0]) ||
                    handCalc.Help().contains(helper[1]) ||
                    handCalc.Help().contains(helper[2]) ||
                    handCalc.Help().contains(helper[3]) ||
                    handCalc.Help().contains(helper[4]) ||
                    handCalc.Help().contains(helper[5]) ||
                    handCalc.Help().contains(helper[6]) ||
                    handCalc.Help().contains(helper[7]) ||
                    handCalc.Help().contains(helper[8]));
     }

     @Test
     void advice() {
          Deck deck = new Deck();
          deck.shuffle();

          ArrayList<Card> cards = new ArrayList<Card>();
          for(int i = 0; i < 7; i++) {
               cards.add(deck.getCard());
          }
          ArrayList<String> aiCards = new ArrayList<String>();
          for(Card card : cards) {
               char c = card.getCardSuit().charAt(0);
               String s = card.getCardValue() + "," + String.valueOf(c);
               aiCards.add(s);
          }

          HandCalculation handCalc = new HandCalculation(aiCards, cards);

          String[] advices = {"Du har bara 'HIGH CARD'. \nOm det ??r billigt s?? kan du prova och se.\n",
                    "Du har ett h??gt 'HIGH CARD'. \nOm det ??r billigt s?? kan du prova och se.\n",
                    "'ONE-PAIR' p?? f??rsta-handen ??r en stark hand!\nS?? k??r p??!\n",
                    "'ONE-PAIR' p?? f??rsta-handen ??r en stark hand!\nOch d?? detta ??r ??ven ??r ett h??gt par, S?? k??r verkligen!!\n",
                    "'ONE-PAIR' ??r en ok hand. Om det inte kostar f??r mycket. S?? k??r p??!\n",
                    "'ONE-PAIR' ??r en ok hand, ??ven d?? detta ??r ett l??gt par.\nOm det inte kostar f??r mycket. S?? k??r p??!\n",
                    "'ONE-PAIR' ??r en ok hand. Och detta ??r ??ven ett h??gt par vilket ??r ??nnu b??ttre.\nOm det inte kostar f??r mycket. K??r p??!\n",
                    "'ONE-PAIR' ??r en hyfsat ok hand. Om det inte kostar f??r mycket. S?? k??r p??!\n",
                    "'ONE-PAIR' ??r en hyfsat ok hand, ??ven d?? detta ??r ett l??gt par.\nOm det inte kostar f??r mycket. S?? k??r p??!\n",
                    "'ONE-PAIR'  ??r en hyfsat ok hand. Och detta ??r ??ven ett h??gt par vilket ??r ??nnu b??ttre.\nOm det inte kostar f??r mycket. K??r p??!\n",
                    "'TWO PAIRS' ??r en bra hand, k??r p??.\n",
                    "'THREE OF A KIND' ??r en v??ldigt stark hand. K??r p??! Fundera ??ven p?? att h??ja!\n",
                    "En 'STRAIGHT' ??r en riktigt bra hand. K??r p??! \nFundera ??ven p?? att h??ja!\n",
                    "Du har en 'FLUSH'! K??r p??, din hand ??r sv??r att sl??!\n",
                    "Det ??r inte mycket som sl??r denna hand! H??ja ??r rekomenderat!\n",
                    "'FOUR OF A KIND' ??r en av de b??sta h??nderna. K??r p??! Fundera ??ven p?? att h??ja!\n",
                    "'STRAIGHT FLUSH' ??r b??sta handen i spelet. K??r p?? och h??j!\n",
          };

          String[] advicesAddon = {
                    "Du har en chans p?? en 'STRAIGHT'",
                    "Du har en chans f??r en 'FLUSH'",
          };


          ArrayList<String> adviceList = new ArrayList<String>();
          for(int i = 0; i < advices.length; i++) {
               adviceList.add(advices[i]);
          }

          System.out.println("ADVICE " + handCalc.advice());

          assertTrue(adviceList.contains(handCalc.advice()) || handCalc.advice().contains(advicesAddon[0]) ||
                    handCalc.advice().contains(advicesAddon[1]));
     }

     @Test
     void toHighlight() {
          Deck deck = new Deck();
          deck.shuffle();

          ArrayList<Card> cards = new ArrayList<Card>();
          for(int i = 0; i < 7; i++) {
               cards.add(deck.getCard());
          }
          ArrayList<String> aiCards = new ArrayList<String>();
          for(Card card : cards) {
               char c = card.getCardSuit().charAt(0);
               String s = card.getCardValue() + "," + String.valueOf(c);
               aiCards.add(s);
          }

          HandCalculation handCalc = new HandCalculation(aiCards, cards);

          assertTrue(handCalc.toHighlight() instanceof ArrayList); //Lite tamt men alternativet ??r att kolla om array size 0 till m??ngden kort som hand skapades med
     }

     @Test
     void calcHandStrength() {
          Deck deck = new Deck();
          deck.shuffle();

          ArrayList<Card> cards = new ArrayList<Card>();
          for(int i = 0; i < 7; i++) {
               cards.add(deck.getCard());
          }
          ArrayList<String> aiCards = new ArrayList<String>();
          for(Card card : cards) {
               char c = card.getCardSuit().charAt(0);
               String s = card.getCardValue() + "," + String.valueOf(c);
               aiCards.add(s);
          }

          HandCalculation handCalc = new HandCalculation(aiCards, cards);
          assertTrue(handCalc.calcHandstrenght() >= 0 && handCalc.calcHandstrenght() <= 8);
     }
}