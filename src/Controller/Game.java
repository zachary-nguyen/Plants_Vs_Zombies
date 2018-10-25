package Controller;

import Model.Peashooter;
import Model.Sunflower;
import View.Backyard;

import java.util.Scanner;

public class Game {

    private Backyard backyard;

    private int score;
    private int money;

    private int wave;
    private int round;

    public static boolean endFlag = false;
    public static boolean endRound = false;

    /**
     * Constructor for game.
     */
    public Game() {
        this.backyard = new Backyard();
        this.score = 0;
        this.money = 300;
        this.wave = 1;
        this.round = 1;
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
                if (xValueToAdd < 0 || xValueToAdd > Backyard.WIDTH - 1  || yValueToAdd < 0 || yValueToAdd > Backyard.HEIGHT - 1) {
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

    public void parse(String command, Game game) {

        switch (command) {
            case "add":
                if(this.addPlant(game)){
                    backyard.updateBackyard(); //only update the backyard if a plant was successfully added
                }
                break;
            case "shovel":
                String[] removeCoordinate = inputCoordinates();
                backyard.removeSprite(Integer.valueOf(removeCoordinate[0]), Integer.valueOf(removeCoordinate[1]));
                backyard.updateBackyard();
                break;
            case "skip":
                backyard.updateBackyard();
                break;
            case "collect":
                money += backyard.collectSun();
                backyard.updateBackyard();
                break;
            case "exit":
                break;
            default:
                System.out.println("Invalid command!\n");
                break;
        }
    }

    /**
     * Helper method for the parse for user to add a new plant
     * @param game The game being played
     * @return Return true if a plant was successfully added or else return false
     */
    public boolean addPlant(Game game){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("What kind of plant do you want to add?");
            System.out.println("(S)unflower(50), (P)eashooter (100)");
            String response = scanner.nextLine(); //Get user answer
            response = response.trim().toLowerCase();

            if (response.equals("sunflower") || response.equals("s")) {
                //Verify the user has enough money to buy
                if(game.getMoney() >= 50){
                    String[] addCoordinate = inputCoordinates();
                    backyard.addSprite(Integer.valueOf(addCoordinate[0]), Integer.valueOf(addCoordinate[1]), new Sunflower());
                    game.setMoney(game.getMoney() - 50);
                    return true;
                }else{
                    System.out.println("Insufficient funds!\n");
                    break;
                }
            } else if (response.equals("peashooter") || response.equals("p")) {
                if(game.getMoney() >= 100) {
                    String[] addCoordinate = inputCoordinates();
                    backyard.addSprite(Integer.valueOf(addCoordinate[0]), Integer.valueOf(addCoordinate[1]), new Peashooter());
                    game.setMoney(game.getMoney() - 100); //Hardcoded values for now
                    return true;
                }else{
                    System.out.println("Insufficient funds!\n");
                    break;
                }
            } else {
                System.out.println("Please select a plant!\n");
            }
        }
        return false;
    }

    public static void main(String[] args) {

        //Set up the game
        final Game game = new Game();

        //user input
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Plants Vs Zombies!\nType anything to start game :");
        String response = scanner.next();

        while (!response.equals("exit") && !(endFlag)) {

            // set zombies for next wave
            Backyard.setNumZombiesSpawn(5);
            Backyard.setSpawnCounter(2);
            while (!response.equals("exit") && !(endRound)) {
                System.out.println("----------WAVE " + game.getWave() + ", ROUND " + game.getRound() + "----------");
                //round++;
                System.out.println("Score: " + game.getScore() + " Money : " + game.getMoney());
                game.getBackyard().print(); //print backyard
                System.out.println("What is your move? 'Add' 'Shovel' 'Skip' 'Collect' 'Exit'");
                response = scanner.next();
                response = response.trim().toLowerCase();
                scanner.nextLine();

                //Treat User response
                game.parse(response, game);
            }

            System.out.println("---------------------Wave Complete!---------------------");
            endRound = false;

            if (game.getWave() == 2) { // 2 waves per round
                // reset wave for next round
                // increment wave
                System.out.println("-------------------Level Complete------------------");
                endFlag = true;

            }
            game.setWave(game.getWave()+1);
        }

        if (endFlag == true) {
            //End game message
            System.out.println("---------------------Game Over!---------------------");
            System.out.println("Your garden has been overrun! Better luck next time!");
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

    public int getWave() { return wave;  }

    public void setWave(int wave) { this.wave = wave; }

    public int getRound() { return round; }

    public void setRound(int level) {   this.round = level;  }
}