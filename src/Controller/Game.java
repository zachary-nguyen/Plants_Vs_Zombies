package Controller;

import Model.Peashooter;
import Model.Zombie;
import View.Backyard;

import java.util.Scanner;

public class Game {

    private Backyard backyard;

    private int score;

    private int money;

    /**
     * Constructor for game.
     */
    public Game() {
        this.backyard = new Backyard();
        this.score = 0;
        this.money = 300;
    }

    /**
     * Helper method for main method to get coordinates to add/remove sprite.
     */
    private String[] inputCoordinates() {
        Scanner scanner = new Scanner(System.in);
        int xValueToAdd;
        int yValueToAdd;
        String[] coordinate;

        while (true) {
            System.out.println("Select coordinates in format 'X Y' ");
            String response = scanner.nextLine();
            coordinate = response.trim().split(" ");

            if (coordinate.length == 2) {
                xValueToAdd = Integer.valueOf(coordinate[0]);
                yValueToAdd = Integer.valueOf(coordinate[1]);

                //Make sure coordinates are in bound
                if (xValueToAdd < 0 || xValueToAdd > 19 || yValueToAdd < 0 || yValueToAdd > 5) {
                    System.out.println("Coordinates out of bounds");
                } else {
                    //Coordinates are valid break the loop
                    break;
                }
            } else {
                System.out.println("Invalid Coordinates");
            }
        }
        return coordinate;
    }

    public void parse(String command) {
        switch (command) {
            case "add":
                String[] addCoordinate = inputCoordinates();
                backyard.addSprite(Integer.valueOf(addCoordinate[0]), Integer.valueOf(addCoordinate[1]), new Peashooter());
                break;
            case "shovel":
                String[] removeCoordinate = inputCoordinates();
                backyard.removeSprite(Integer.valueOf(removeCoordinate[0]), Integer.valueOf(removeCoordinate[1]));
                break;
            case "skip":
                backyard.updateBackyard();
                break;
            case "exit":
                break;
            default:
                System.out.println("Invalid command!");
                break;

        }
    }

    public static void main(String[] args) {

        //Set up the game
        final Game game = new Game();

        //user input
        Scanner scanner = new Scanner(System.in);

        System.out.println("Type anything to start game :");
        String response = scanner.next();

        while (!response.equals("exit")) {

            System.out.println("----------WAVE 1----------");
            System.out.println("Score: " + game.score + " Money : " + game.money);
            game.getBackyard().print(); //print backyard
            System.out.println("What is your move? 'Add' 'Shovel' 'Skip' 'Exit'");
            response = scanner.next();
            response = response.trim().toLowerCase();
            scanner.nextLine();

            //Treat User response

            game.parse(response);
        }
    }

    public Backyard getBackyard() {
        return backyard;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}