package Controller;

import Model.Peashooter;
import View.Backyard;

import java.util.Scanner;

public class Game{

    private int score;

    private int money;

    /**
     * Constructor for game.
     */
    public Game(){
        this.score = 0;
        this.money = 250;
    }

    public static void main(String[] args){

        //Set up the game
        final Game game = new Game();
        final Backyard backyard = new Backyard();

        //user input
        Scanner scanner = new Scanner(System.in);

        System.out.println("Type anything to start game :");
        String response = scanner.next();

        while(!response.equals("exit")){
            backyard.print();

            System.out.println("What is your move? 'Add' 'Remove' 'Skip' ");
            response = scanner.next();
            response = response.trim().toLowerCase();
            scanner.nextLine();

            if(response.equals("add")){
                boolean addFlag = false;
                while(true) {

                    System.out.println("Select coordinates in format 'X Y' ");
                    response = scanner.nextLine();

                    String[] coordinate = response.trim().split(" ");
                    int xValueToAdd = Integer.valueOf(coordinate[0]);
                    int yValueToAdd = Integer.valueOf(coordinate[1]);

                    //Make sure coordinates are in bound
                    if (xValueToAdd >= 0 && xValueToAdd < 19 && yValueToAdd >= 0 && yValueToAdd < 6) {
                        backyard.addSprite(xValueToAdd, yValueToAdd, new Peashooter());
                        break;
                    } else {
                        System.out.println("Wrong coordinates!");
                    }
                }
            }
        }
    }
}