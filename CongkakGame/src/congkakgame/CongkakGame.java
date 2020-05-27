package congkakgame;

import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Forge-15 1650
 */
public class CongkakGame {

    static int[] congkak = new int[16];
    static String[] c = new String[16];
    static int turn = 1;
//Player2 = congkak[0]
//Player1 = congkak[8]

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < congkak.length; i++) {
            congkak[i] = 7;
        }
        congkak[0] = 0;
        congkak[8] = 0;

        while (!endGame()) {
            display();
            if (turn == 1) {
                System.out.println("Player 1 turn's");
                System.out.print("Choose a house (1-7)(left-right): ");
            } else {
                System.out.println("Player 2 turn's");
                System.out.print("Choose a house (9-15)(right-left): ");
            }
            int n = sc.nextInt();
            if ((n != 0 && n < 8 && turn == 1) || (n > 8 && turn == 2)) {
                start(n);
            } else {
                System.err.println("Error input!!!");
            }
        System.out.println("");
        }
        display();
        if (congkak[0] < congkak[8]) {
            System.out.println("Player 1 wins");
        } else if (congkak[0] > congkak[8]) {
            System.out.println("Player 2 wins");
        } else {
            System.out.println("Draw");
        }
    }

    public static void display() {
        String standSpace = "";
        System.out.print("       ");
        for (int i = 1; i < 8; i++) {
            System.out.print(String.format("%2d", congkak[i]) + "(" + String.format("%2d", i) + ")" + "  ");
            standSpace += String.format("%2s", "") + "  ";
        }
        System.out.println("\n" + "(P2)" + String.format("%2d", congkak[0]) + standSpace + standSpace + "(P1)" + String.format("%2d", congkak[8]));
        System.out.print("       ");
        for (int j = 15; j > 8; j--) {
            System.out.print(String.format("%2d", congkak[j]) + "(" + String.format("%2d", j) + ")" + "  ");
        }
        System.out.println("\nPlayer 1: " + congkak[8]);
        System.out.println("Player 2: " + congkak[0]);
    }

    public static boolean endGame() {
        for (int i = 0; i < congkak.length; i++) {
            if (congkak[i] != 0 && i != 0 && i != 8) {
                return false;
            }
        }
        return true;
    }

    public static void start(int n) {
        if (congkak[n] == 0) {
            System.out.println("Choose again!!!");
            return;
        }
        display();
        int temp = congkak[n];
        congkak[n] = 0;
        if (turn == 1) { //Player 1 turn
            while (temp != 0) {
                n = (n + 1) % congkak.length;
                if (n == 0) { //skip the P2 storehouse
                    n++;
                }
                congkak[n]++;
                temp--;
                //last marble in hand
                if (temp == 1) {
                    //at our own side
                    n = (n + 1) % congkak.length;
                    if (n == 0) { //skip the opponent storehouse
                        n++;
                    } else if (n == 8) { //last marble insert into our storehouse and change to P2 turn
                        congkak[n]++;
                        turn = 2;
                        return;
                    } //stop at own storehouse
                    if (n < 8) {
                        if (congkak[n] == 0) { //if empty(need to stop)
                            congkak[8] += 1 + congkak[16 - n];
                            congkak[16 - n] = 0;
                            turn = 2;
                            return;
                        } else { //take and continue
                            congkak[n]++;
                            start(n);
                        }
                    } else {
                        //at opponent side
                        if (congkak[n] == 0) { //congkak stop at opponent side 
                            congkak[n]++;
                            turn = 2;
                            return;
                        } else { //take and continue
                            congkak[n]++;
                            start(n);
                        }
                    }
                }
            }
        } else { //Player 2 turn
            while (temp != 0) {
                n = (n + 1) % congkak.length;
                if (n == 8) { //skip the P1 storehouse
                    n++;
                }
                congkak[n]++;
                temp--;
                //last marble in hand
                if (temp == 1) {
                    //at our own side
                    n = (n + 1) % congkak.length; //point to the house that will receive last marble
                    if (n == 8) { //skip P1 storehouse
                        n++;
                    } else if (n == 0) { //last marble insert into our storehouse and change to P1 turn
                        congkak[n]++;
                        turn = 1;
                        return;
                    }
                    if (n > 8) { //at our side (9-15)
                        if (congkak[n] == 0) { //if empty(need to stop)
                            congkak[0] += 1 + congkak[16 - n];
                            congkak[16 - n] = 0;
                            turn = 1;
                            return;
                        } else { //take and continue
                            congkak[n]++;
                            start(n);
                        }
                    } else { //at opponent side                     
                        if (congkak[n] == 0) { //congkak stop at opponent side 
                            congkak[n]++;
                            turn = 2;
                            return;
                        } else { //take and continue
                            congkak[n]++;
                            start(n);
                        }
                    }
                }
            }
        }
    }
}
