package Controller;

import Model.Peashooter;
import View.Backyard;

import java.util.Scanner;

public class Game{

    private Backyard backyard;
    /**
     * Constructor for game.
     */
    public Game(){
        this.backyard = new Backyard();
    }

    /**
     * Helper method for main method to add sprite.
     */
    private void addSprite(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select coordinates in format 'X Y' ");

        String response = scanner.nextLine();

        while(true) {

            String[] coordinate = response.trim().split(" ");

            if (coordinate.length == 2) {
                int xValueToAdd = Integer.valueOf(coordinate[0]);
                int yValueToAdd = Integer.valueOf(coordinate[1]);

                //Make sure coordinates are in bound
                if (xValueToAdd >= 0 && xValueToAdd < 19 && yValueToAdd >= 0 && yValueToAdd < 5) {
                    backyard.addSprite(xValueToAdd, yValueToAdd, new Peashooter());
                    break;
                } else {
                    System.out.println("Coordinates out of bonds!");
                }
            } else {
                System.out.println("Invalid coordinates!");
            }
            System.out.println("Select coordinates in format 'X Y' ");

        }
    }

    public static void main(String[] args){

            //Set up the game
            final Game game = new Game();

            //user input
            Scanner scanner = new Scanner(System.in);

            System.out.println("Type anything to start game :");
            String response = scanner.next();

            while (!response.equals("exit")) {
                //Print the backyard
                game.getBackyard().print();

                System.out.println("What is your move? 'Add' 'Remove' 'Skip' 'Exit'");
                response = scanner.next();
                response = response.trim().toLowerCase();
                scanner.nextLine();

                //Treat User response
                if (response.equals("add")) {
                    game.addSprite();
                }
            }
    }

    public Backyard getBackyard() {
        return backyard;
    }
}