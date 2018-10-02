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

    public void responseTreatment(String response){

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

            System.out.println("What is your move? 'Add' 'Remove'");
            response = scanner.next();
            response = response.trim();
            response = response.toLowerCase();

            if(response.equals("add")){
                scanner.nextLine();
                System.out.println("Select coordinates in format 'X Y' ");
                response = scanner.nextLine();
                response = response.trim();


            }
        }


    }
}