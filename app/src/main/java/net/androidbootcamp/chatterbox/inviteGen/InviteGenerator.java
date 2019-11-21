package net.androidbootcamp.chatterbox.inviteGen;

public class InviteGenerator {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static char randChar1;
    private static char randChar2;

    public static String Generate(){

        int i = 5;
        String inviteReturn = "";

        randChar1 = GenRandomChar();
        randChar2 = GenRandomChar();

        inviteReturn += randChar1 + randChar2 + "-";

        while(i > 0) {

            inviteReturn += GenRandomNum();
            i--;

        }

        return inviteReturn;

    }

    private static char GenRandomChar(){

        char rand;
        int min = 0;
        int max = ALPHABET.length()-1;

        rand = ALPHABET.charAt((int)(Math.random()*((max-min)+1))+min);

        return rand;

    }

    private static int GenRandomNum(){

        int rand;
        int min = 0;
        int max = 9;

        rand = (int)(Math.random()*((max-min)+1))+min;

        return rand;

    }

}
