package controller.gameControllers;

import model.Card;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Does the actual calculation and decides what help
 * the noob player gets.
 *
 * @author Max Frennessen, Oscar Kareld
 * @version 1.6
 * 22/2-2021
 */
public class HandCalculation {
    private ArrayList<String> finalHihglight = new ArrayList<String>();
    private ArrayList<String> nbrForStraight = new ArrayList<String>();
    private ArrayList<String> nbrForStraight1 = new ArrayList<String>();
    private ArrayList<String> aiCards = new ArrayList<String>();

    private ArrayList<Integer> cardNbr = new ArrayList<Integer>();
    private ArrayList<String> cardClr = new ArrayList<String>();
    private boolean highCards = false;
    private boolean lowCards = false;
    private boolean rlyhighCards = false;
    private int colorChance;
    private int straightChance;
    private int pairsNmore;
    private String yourCard = "1,1";
    private String yourCard2 = "1,1";
    private String otherCard = "1,1";
    private String theColor;
    private ArrayList<String> toHighlight = new ArrayList<String>();
    private String advicee;
    private String whatStraight;
    private int handStrenght = 0;

    ArrayList<Integer> allKnownCards = new ArrayList<Integer>();
    ArrayList<Card> knownCards = new ArrayList<>();
    CorrectHandCalc correctHandCalc = new CorrectHandCalc(knownCards);

    /**
     * @param aiCards Current cards needed for evaluate.
     * @param cards
     */
    public HandCalculation(ArrayList<String> aiCards, ArrayList<Card> cards) {
        updateAllKnownCards(cards);
        this.aiCards = aiCards;
        getCardValues(aiCards);
        toHighlight.clear();
        highCards = checkHighCards();
        colorChance = checkSuit();
        pairsNmore = checkPairAndMore();
        straightChance = checkStraight();

        Help();
    }


    /**
     * converts the cards value into two different arraylists.
     * one for card number and one for card color.
     *
     * @param aiCards current card being used
     */
    public void getCardValues(ArrayList<String> aiCards) {

        for (int i = 0; i < aiCards.size(); i++) {            //CardNumber
            String temp = aiCards.get(i);
            String[] splitter = temp.split(",");
            int tempInt = Integer.parseInt(splitter[0]);
            cardNbr.add(tempInt);
        }


        for (int i = 0; i < aiCards.size(); i++) {            //CardColor
            String temp = aiCards.get(i);
            String[] splitter = temp.split(",");
            String tempString = splitter[1];
            cardClr.add(tempString);
        }
    }


    /**
     * @return returns how many pairs or more the player has.
     */
    public int checkPairAndMore() {
        int same = 1;
        int nbrOftemp = 0;
        int nbrOftemp1 = 0;
        int nbrOftemp2 = 0;
        int size = aiCards.size(); //Antal kort i spelarens hand + kort p?? bordet.
        int[] cards = new int[size + 1];

        // Oscars kod: den h??r biten ??r f??r att highlight:a ??ven par och dylikt som finns bland community-korten p??
        // bordet.
        boolean[] isPaired = new boolean[size + 1];

        for (int i = 0; i < size; i++) {
            cards[i] = cardNbr.get(i);
            allKnownCards.add(cards[i]);
        }

        System.out.println(allKnownCards.size());
        for (int i = 0; i < allKnownCards.size(); i++) {
            int temp = allKnownCards.get(i);
            for (int j = i+1; j < allKnownCards.size(); j++) {
                if (temp == allKnownCards.get(j)) {
                    isPaired[i] = true;
                    isPaired[j] = true;
                }
            }
        }
        toHighlight.clear();

        for (int i = 0; i < isPaired.length; i++) {
            if (isPaired[i]) {
                toHighlight.add(aiCards.get(i));
            }
        }
        //Slut p?? Oscars kod.

        if (cards[0] == cards[1]) { //Kollar om man har par p?? hand
            int temp = cards[0]; //temp = f??rsta kortet p?? hand
            nbrOftemp = 2;
            yourCard = aiCards.get(0);
            otherCard = aiCards.get(1);

            for (int i = 2; i < cards.length; i++) {
                if (cards[i] == temp) { //cards ??r en array av int som bara sparar kortens val??rer. cards[0] och cards[1] ??r spelarens h??lkort
                    nbrOftemp++;

                }
            }
        } else {
            int temp1 = cards[0];
            int temp2 = cards[1];


            nbrOftemp1 = 1;
            nbrOftemp2 = 1;


            for (int i = 2; i < cards.length; i++) {

                if (cards[i] == temp1) {
                    if (cards[i] + temp2 <= 10) {
                        lowCards = true;
                    }
                    if (cards[i] + temp2 > 17) {
                        highCards = true;
                    }

                    nbrOftemp1++;

                    yourCard = aiCards.get(0);
                    otherCard = aiCards.get(i);
                    yourCard2 = aiCards.get(0);
                }
                if (cards[i] == temp2) {

                    if (cards[i] + temp2 > 17) {
                        highCards = true;
                    }

                    if (cards[i] + temp2 <= 10) {
                        lowCards = true;
                    }
                    nbrOftemp2++;
                    yourCard = aiCards.get(1);
                    otherCard = aiCards.get(i);

                }
            }
        }

        if (nbrOftemp > 0) {
            same = nbrOftemp;
        }

        if (nbrOftemp1 > 1) {
            same = nbrOftemp1;
        }

        if (nbrOftemp2 > 1) {
            if (nbrOftemp1 > 1) {
                same = Integer.parseInt(nbrOftemp1 + "" + nbrOftemp2);
            } else
                same = nbrOftemp2;
        }

        if (same == 1)
            same = 0;
        return same;

    }

    /**
     * @return returns true if cards value >= 17.
     * 'rlyHigh not yet implemented.
     */
    public boolean checkHighCards() {
        boolean high = false;

        int card1 = cardNbr.get(0);
        int card2 = cardNbr.get(1);

        int total = (card1 + card2);

        if (total >= 17) {
            high = true;
        }
        if (card1 >= 10 && card2 >= 10) {
            rlyhighCards = true;
        }

        return high;
    }

    /**
     * @return returns if the player has a suit or even has a chance for one.
     */
    public int checkSuit() {
        int C = 0;
        int S = 0;
        int H = 0;
        int D = 0;
        int color = 0;

        for (String x : cardClr) {
            if (x.equals("S")) {
                S++;
            }
            if (x.equals("C")) {
                C++;
            }
            if (x.equals("D")) {
                D++;
            }
            if (x.equals("H")) {
                H++;
            }
        }

        if (S > color) {
            toHighlight.clear();
            color = S;
            theColor = "spader";
            for (int i = 0; i < cardClr.size(); i++) {
                String temp = cardClr.get(i);
                if (S == 5)
                    if (temp.equals("S")) {
                        toHighlight.add(aiCards.get(i));
                    }
            }
        }

        if (H > color) {
            toHighlight.clear();
            color = H;
            theColor = "hj??rter";
            for (int i = 0; i < cardClr.size(); i++) {
                String temp = cardClr.get(i);
                if (H == 5)
                    if (temp.equals("H")) {
                        toHighlight.add(aiCards.get(i));
                    }
            }
        }
        if (D > color) {
            toHighlight.clear();
            color = D;
            theColor = "ruter";
            for (int i = 0; i < cardClr.size(); i++) {
                String temp = cardClr.get(i);
                if (D == 5)
                    if (temp.equals("D")) {
                        toHighlight.add(aiCards.get(i));
                    }
            }
        }
        if (C > color) {
            toHighlight.clear();
            color = C;
            theColor = "kl??ver";
            for (int i = 0; i < cardClr.size(); i++) {
                String temp = cardClr.get(i);
                if (C == 5)
                    if (temp.equals("C")) {
                        toHighlight.add(aiCards.get(i));
                    }
            }
        }

        return color;
    }

    /**
     * @return returns if the player has a straight or even has a chance for one.
     */
    public int checkStraight() {

        int threshold = 0;

        for (int i = 0; i < cardNbr.size(); i++) {
            if (cardNbr.get(i) == 14) {
                cardNbr.add(1);
            }
        }

        int[] CurrentCardsArray = new int[cardNbr.size()];

        for (int i = 0; i < cardNbr.size(); i++) {
            CurrentCardsArray[i] = cardNbr.get(i);
        }

        Arrays.sort(CurrentCardsArray);
        int inStraight = 0;
        int check = 4;

        for (int x = 0; x < CurrentCardsArray.length; x++) {
            int CurrentHighestInStraight = CurrentCardsArray[x] + check;
            int CurrentLowestInStraight = CurrentCardsArray[x];
            String tempStraight = CurrentLowestInStraight + "-" + CurrentHighestInStraight;

            inStraight = 0;


            for (int i = 0; i < CurrentCardsArray.length; i++) {

                if (CurrentCardsArray[i] <= CurrentHighestInStraight && !(CurrentCardsArray[i] < CurrentLowestInStraight)) {

                    if (i == 0) {                            //kollar om 0 ??r samma som 1.

                        inStraight++;
                        if (CurrentCardsArray[i] == 1) {
                            nbrForStraight.add(String.valueOf(CurrentCardsArray[CurrentCardsArray.length - 1]));
                        } else {
                            nbrForStraight.add(String.valueOf(CurrentCardsArray[i]));
                        }

                    }

                    if (i >= 1) {
                        if (!(CurrentCardsArray[i] == CurrentCardsArray[i - 1])) {        //kollar om 1-4 ??r samma som n??n annan.
                            inStraight++;
                            nbrForStraight.add(String.valueOf(CurrentCardsArray[i]));
                        }
                    }

                }
            }

            if (inStraight >= threshold) {  // >= s?? om man f??r 5 igen men med h??gre tal s?? blir det den som visas.

                threshold = inStraight;
                whatStraight = tempStraight;
                nbrForStraight1.clear();
                for (String a : nbrForStraight) {
                    nbrForStraight1.add(a);
                }
            }
            nbrForStraight.clear();
        }

        return threshold;
    }

    public ArrayList<String> getToHighlight() {
        for (int y = 0; y < nbrForStraight1.size(); y++) {
            int same = 1;
            for (int i = 0; i < aiCards.size(); i++) {

                String temp = aiCards.get(i);
                String[] tempSplit = temp.split(",");
                if (nbrForStraight1.get(y).equals(tempSplit[0])) {
                    if (same == 1) {
                        finalHihglight.add(aiCards.get(i));
                        same++;
                    }
                }
            }
        }
        return finalHihglight;
    }


    public int calcPwrBarLvl() {
        int pwrBar = 1;
        //TURNONE PWRLEVEL
        if (aiCards.size() == 2) {
            pwrBar = pwrBarLvlOnTurnOne();
        }
        //TURNTWO PWRLEVEL
        if (aiCards.size() == 5) {
            pwrBar = pwrBarLvlOnTurnTwo();
        }
        //TURNTHREE PWRLEVEL
        if (aiCards.size() == 6) {
            pwrBar = pwrBarLvlOnTurnThree();
        }
        //TURNFOUR PWRLEVEL
        if (aiCards.size() == 7) {
            pwrBar = pwrBarLvlOnTurnFour();
        }
        return pwrBar;
    }

    public int pwrBarLvlOnTurnOne() {
        int pwrBar = 1;
        if (colorChance == 2) {
            pwrBar = 2;
        }
        if (highCards) {
            pwrBar = 2;
            if (rlyhighCards) {
                pwrBar = 3;
            }
        }
        if (straightChance == 2) {
            pwrBar = 2;
            if (rlyhighCards) {
                pwrBar = 3;
            }
            if (colorChance == 2) {
                pwrBar = 3;
            }
        }
        if (pairsNmore > 0) {
            pwrBar = 4;
        }
        return pwrBar;
    }

    public int pwrBarLvlOnTurnTwo() {
        int pwrBar = 1;

        if (highCards) {
            pwrBar = 1;
            if (rlyhighCards) {
                pwrBar = 2;
            }
        }
        if (colorChance == 3) {
            pwrBar = 2;
        }
        if (straightChance >= 2) {
            pwrBar = 2;
            if (straightChance >= 4) {
                pwrBar = 3;
            }
            if (colorChance == 3) {
                pwrBar = 3;
            }
        }
        if (pairsNmore == 2) {
            pwrBar = 3;
        }
        if (pairsNmore == 22) {
            pwrBar = 4;
        }
        if (pairsNmore == 3) {
            pwrBar = 4;
        }
        if (pairsNmore == 4 || pairsNmore == 24) {
            pwrBar = 4;
        }
        if (pairsNmore == 23 || pairsNmore == 32) {
            pwrBar = 4;
        }
        if (straightChance == 5 || colorChance == 5) {
            pwrBar = 4;
        }


        return pwrBar;
    }

    public int pwrBarLvlOnTurnThree() {
        int pwrBar = 1;

        if (highCards) {
            pwrBar = 1;
            if (rlyhighCards) {
                pwrBar = 2;
            }
        }
        if (colorChance == 4) {
            pwrBar = 2;
        }
        if (straightChance == 4) {
            pwrBar = 2;

            if (colorChance == 4) {
                pwrBar = 3;
            }
        }
        if (pairsNmore == 2) {
            pwrBar = 2;
        }
        if (pairsNmore == 22) {
            pwrBar = 3;
        }
        if (pairsNmore == 3) {
            pwrBar = 4;
        }
        if (pairsNmore == 4 || pairsNmore == 24) {
            pwrBar = 4;
        }
        if (pairsNmore == 23 || pairsNmore == 32) {
            pwrBar = 4;
        }
        if (straightChance == 5 || colorChance == 5) {
            pwrBar = 4;
        }


        return pwrBar;
    }

    public int pwrBarLvlOnTurnFour() {
        int pwrBar = 1;

        if (highCards) {
            pwrBar = 1;
            if (rlyhighCards) {
                pwrBar = 1;
            }
        }
        if (pairsNmore == 2) {
            pwrBar = 2;
        }
        if (pairsNmore == 22) {
            pwrBar = 3;
        }
        if (pairsNmore == 3) {
            pwrBar = 4;
        }
        if (pairsNmore == 4 || pairsNmore == 24) {
            pwrBar = 4;
        }
        if (pairsNmore == 23 || pairsNmore == 32) {
            pwrBar = 4;
        }
        if (straightChance == 5 || colorChance == 5) {
            pwrBar = 4;
        }


        return pwrBar;
    }

    /**
     * Updates all known cards in object and in the correctHandCalc object
     * @param allKnownCards
     */
    public void updateAllKnownCards(ArrayList<Card> allKnownCards) { //1
        correctHandCalc.updateAllKnownCards(allKnownCards);
        knownCards = allKnownCards;
    }

    /**
     * Creates a now CorrectHandCalc object
     */
    public void createNewCorrectHandCalc() { //2
        correctHandCalc = new CorrectHandCalc(knownCards);
//        System.out.println("NUVARANDE HAND: " + correctHandCalc.calculateHand());

    }

    //TODO: Som det ser ut nu st??mmer inte "DU HAR"-info ??verens med "R??D"-infon, d?? "DU HAR" har sin utr??kning i CorrectHandCalc,
    //TODO medan "R??D"-infon kommer fr??n Help()-metoden h??r nedanf??r. /Oscar

    /**
     * New help-method
     * @return String with the player's best hand
     */
    public String newHelp() {
        return correctHandCalc.calculateHand();
    }

    /**
     * @return returns a advice for the player that is current for his or her hand.
     */
    public String Help() {

        String helper = "Ingenting, tyv??rr...";

        String[] splitter = yourCard.split(",");
        int intCardNbr = Integer.parseInt(splitter[0]);
        String yourCardInt = "";
        yourCardInt = String.valueOf(intCardNbr);
        String cardOne = cardNbr.get(0) + ":or";
        String cardTwo = cardNbr.get(1) + ":or";

        if (cardNbr.get(0) > 10) {
            if (cardNbr.get(0) == 11) {
                cardOne = "Knektar";
            }
            if (cardNbr.get(0) == 12) {
                cardOne = "Damer";
            }

            if (cardNbr.get(0) == 13) {
                cardOne = "Kungar";
            }
            if (cardNbr.get(0) == 14) {
                cardOne = "Ess";
            }
        }

        if (cardNbr.get(1) > 10) {
            if (cardNbr.get(1) == 11) {
                cardTwo = "Knektar";
            }
            if (cardNbr.get(1) == 12) {
                cardTwo = "Damer";
            }

            if (cardNbr.get(1) == 13) {
                cardTwo = "Kungar";
            }
            if (cardNbr.get(1) == 14) {
                cardTwo = "Ess";
            }
        }
        if (intCardNbr < 11) {
            yourCardInt += ":or";
        }
        if (intCardNbr > 10) {
            if (intCardNbr == 11) {
                yourCardInt = "Knektar";
            }
            if (intCardNbr == 12) {
                yourCardInt = "Damer";
            }

            if (intCardNbr == 13) {
                yourCardInt = "Kungar";
            }
            if (intCardNbr == 14) {
                yourCardInt = "Ess";
            }

        }
        //Writing out what advice to give and help for player, starting to check the lowest possible and if the player has better than it,
        //im overwriting it with a better card. starting from high card only and ending on straight flush.

        //HIGH CARD
        String advice = "Du har bara 'H??GT KORT'. \nOm det ??r billigt s?? kan du prova och se.\n";

        if (highCards) {
            advice = "Du har ett h??gt 'H??GT KORT'. \nOm det ??r billigt s?? kan du prova och se.\n";
        }


        // ONE PAIR
        if (pairsNmore == 2) {
            helper = "'ETT PAR' i " + yourCardInt + "\n";
            if (aiCards.size() == 2) {
                advice = "'ETT PAR' p?? f??rsta-handen ??r en stark hand!\nS?? k??r p??!\n";
                if (highCards) {
                    advice = "'ETT PAR' p?? f??rsta-handen ??r en stark hand!\nOch d?? detta ??r ??ven ??r ett h??gt par, S?? k??r verkligen!!\n";
                }
            }
            if (aiCards.size() == 5) {
                advice = "'ETT PAR' ??r en ok hand. Om det inte kostar f??r mycket. S?? k??r p??!\n";
                if (lowCards) {
                    advice = "'ETT PAR' ??r en ok hand, ??ven d?? detta ??r ett l??gt par.\nOm det inte kostar f??r mycket. S?? k??r p??!\n";
                }
                if (highCards) {
                    advice = "'ETT PAR' ??r en ok hand. Och detta ??r ??ven ett h??gt par vilket ??r ??nnu b??ttre.\nOm det inte kostar f??r mycket. K??r p??!\n";
                }
            }

            if (aiCards.size() > 5) {
                advice = "'ETT PAR' ??r en hyfsat ok hand. Om det inte kostar f??r mycket. S?? k??r p??!\n";
                if (lowCards) {
                    advice = "'ETT PAR' ??r en hyfsat ok hand, ??ven d?? detta ??r ett l??gt par.\nOm det inte kostar f??r mycket. S?? k??r p??!\n";
                }
                if (highCards) {
                    advice = "'ETT PAR'  ??r en hyfsat ok hand. Och detta ??r ??ven ett h??gt par vilket ??r ??nnu b??ttre.\nOm det inte kostar f??r mycket."
                            + " K??r p??!\n";
                }
            }
            // writes the active cards to highlight
            if (straightChance < 5 && colorChance < 5) {
                toHighlight.clear();
                for (int i = 0; i < aiCards.size(); i++) {
                    String[] seeIfSame = aiCards.get(i).split(",");
                    int temp = Integer.parseInt(seeIfSame[0]);
                    if (intCardNbr == temp) {
                        toHighlight.add(aiCards.get(i));
                    }
                }
            }
        }


        //TWO PAIRS
        if (pairsNmore == 22) {
            helper = "'TV?? PAR'  i " + cardOne + " och " + cardTwo;
            advice = "'TV?? PAR' ??r en bra hand, k??r p??.\n";
            // writes the active cards to highlight
            if (straightChance < 5 && colorChance < 5) {
                toHighlight.clear();
                for (int i = 0; i < aiCards.size(); i++) {
                    int cardIntOne = cardNbr.get(0);
                    int cardIntTwo = cardNbr.get(1);

                    if (cardIntOne == cardNbr.get(i)) {
                        toHighlight.add(aiCards.get(i));
                    }
                    if (cardIntTwo == cardNbr.get(i)) {
                        toHighlight.add(aiCards.get(i));
                    }
                }
            }
        }

        //THREE OF A KIND
        if (pairsNmore == 3) {
            helper = "'TRETAL' i " + yourCardInt;
            advice = "'TRETAL' ??r en v??ldigt stark hand. K??r p??! Fundera ??ven p?? att h??ja!\n";
            // writes the active cards to highlight
            if (straightChance < 5 && colorChance < 5) {
                toHighlight.clear();
                for (int i = 0; i < aiCards.size(); i++) {
                    String[] seeIfSame = aiCards.get(i).split(",");
                    int temp = Integer.parseInt(seeIfSame[0]);
                    if (intCardNbr == temp) {
                        toHighlight.add(aiCards.get(i));
                    }
                }
            }
        }


        //STRAIGHT
        if (straightChance == 5) {
            helper = "En 'STEGE'!! Du har 5/5.\n";
            advice = "En 'STEGE' ??r en riktigt bra hand. K??r p??! \nFundera ??ven p?? att h??ja!\n";
            toHighlight.clear();
            toHighlight = getToHighlight();


        }

        //FLUSH
        if (colorChance == 5) {
            helper = "En 'F??RG' i " + theColor + "!! Du har 5/5!!\n";
            advice = "Du har en 'F??RG'! K??r p??, din hand ??r sv??r att sl??!\n";
            //To HIHGLIGHT IS IN checkSuit Method.
            toHighlight.clear();
            checkSuit();
        }


        //FULL HOUSE
        if (pairsNmore == 23 || pairsNmore == 32) {
            helper = "'K??K' med " + cardOne + " och " + cardTwo + "!!";
            advice = "Det ??r inte mycket som sl??r denna hand! H??ja ??r rekomenderat!\n";
            // writes the active cards to hihglight
            toHighlight.clear();
            for (int i = 0; i < aiCards.size(); i++) {
                int cardIntOne = cardNbr.get(0);
                int cardIntTwo = cardNbr.get(1);

                if (cardIntOne == cardNbr.get(i)) {
                    toHighlight.add(aiCards.get(i));
                }
                if (cardIntTwo == cardNbr.get(i)) {
                    toHighlight.add(aiCards.get(i));
                }
            }

        }

        //FOUR OF A KIND
        if (pairsNmore == 4 || pairsNmore == 42 || pairsNmore == 24) {
            helper = "'FYRTAL' i " + yourCardInt;
            advice = "'FYRTAL' ??r en av de b??sta h??nderna. K??r p??! Fundera ??ven p?? att h??ja!\n";
            // writes the active cards to hihglight
            if (straightChance < 5 && colorChance < 5) {
                toHighlight.clear();
                for (int i = 0; i < aiCards.size(); i++) {
                    String[] seeIfSame = aiCards.get(i).split(",");
                    int temp = Integer.parseInt(seeIfSame[0]);
                    if (intCardNbr == temp) {
                        toHighlight.add(aiCards.get(i));
                    }
                }
            }
        }

        //STRAIGHT FLUSH
        if (straightChance == 5 && colorChance == 5) {                            //"i stegen  " + whatStraight;
            helper = "'F??RGSTEGE' i f??rgen " + theColor + "! ";   //ev add what straight it is ex 2-6.
            advice = "'F??RGSTEGE' ??r b??sta handen i spelet. K??r p?? och h??j!\n";
            // Highlightning happens in checkStraight and checkSuit.
        }

        //STRAIGHTCHANCE TEXT AND COLORCHANCE TEXT
        if (aiCards.size() < 3) {
            if (straightChance == 2) {
                advice += "Du har en chans p?? en 'STEGE', du har 2/5. \n";
            }
            if (colorChance == 2) {
                advice += "Du har en chans f??r en 'F??RG' i " + theColor + ", du har 2/5.\n";
            }
        }
        if (aiCards.size() < 6) {
            if (straightChance == 3) {
                advice += "Du har en chans p?? en 'STEGE', du har 3/5.\n";
            }
            if (colorChance == 3) {
                advice += "Du har en chans f??r en 'F??RG' i " + theColor + ", du har 3/5.\n";
            }
        }

        if (aiCards.size() < 7) {
            if (straightChance == 4) {
                advice += "Du har en chans p?? en 'STEGE', du har 4/5.\n";
            }
            if (colorChance == 4) {
                advice += "Du har en chans f??r en 'F??RG' i " + theColor + ", du har 4/5.\n";
            }
        }

        advicee = advice;
        return helper;

    }

    /**
     * returns what advice to give the user
     *
     * @return what advice to give the user
     */
    public String advice() {
        return advicee;
    }

    /**
     * @return what to be highlighed.
     */
    public ArrayList<String> toHighlight() {
        return toHighlight;

    }

    /**
     * sets and returns the current handStrength of the users cards.
     *
     * @return sets and returns the current handStrength of the users cards.
     */
    public int calcHandstrenght() {

        if (pairsNmore == 2) {     //Pair
            handStrenght = 1;
        }
        if (pairsNmore == 22) {     //Two pair
            handStrenght = 2;
        }
        if (pairsNmore == 3) {     //Three of a kind
            handStrenght = 3;
        }
        if (straightChance == 5) {    //Straight
            handStrenght = 4;
        }
        if (colorChance == 5) {        //Flush
            handStrenght = 5;
        }
        if (pairsNmore == 23 || pairsNmore == 32) {    //Full house
            handStrenght = 6;
        }
        if (pairsNmore == 4 || pairsNmore == 42 || pairsNmore == 24) {    //Four of a kind
            handStrenght = 7;
        }
        if (colorChance == 5 && straightChance == 5) {        //Straight flush
            handStrenght = 8;
        }

        return handStrenght;
    }

    public void setCardNbr(ArrayList<Integer> cardNbr) {
        this.cardNbr = cardNbr;
    }
}
