package net.androidbootcamp.chatterbox.inviteGen;

import java.util.Random;

public class InviteGenerator {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static char randChar1;
    private static char randChar2;

    public static String Generate(){

        int i = 5;
        String inviteReturn = "";

        randChar1 = genRandomChar();
        randChar2 = genRandomChar();

        inviteReturn = String.format("%c%c", randChar1, randChar2);

        while(i > 0) {

            inviteReturn += genRandomNum(0, 9);
            i--;

        }

        return inviteReturn;

    }

    private static char genRandomChar(){

        char rand;
        int min = 0;
        int max = 51;
        int randomPos = genRandomNum(min, max);

        rand = ALPHABET.charAt(randomPos);

        return rand;

    }

    private static int genRandomNum(int min, int max){

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random rand = new Random();

        return rand.nextInt((max - min) + 1) + min;

    }

}
