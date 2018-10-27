package Controller;

import Model.Backyard;
import Model.Peashooter;
import Model.Sunflower;
import Model.Wave;

import java.util.Scanner;

public class Game {

    private Backyard backyard;
    public static boolean gameOver = false;

    //keeps track of plants to avoid hardcoded values and make it easier to add new plants
    enum Plants {
        SUNFLOWER("(S)unflower", 50), PEASHOOTER("(P)eashooter", 100);
        private int cost;
        private String name;

        public int getCost() {
            return cost;
        }

        public String getName() {
            return name;
        }

        public String toString() {
            return name + "(" + cost + ")";
        }

        Plants(String name, int cost) {
            this.name = name;
            this.cost = cost;
        }
    }

    /**
     * Constructor for game.
     */
    public Game() {
        this.backyard = new Backyard();
        startGame();
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
                if (xValueToAdd < 0 || xValueToAdd > Backyard.WIDTH - 1 || yValueToAdd < 0 || yValueToAdd > Backyard.HEIGHT - 1) {
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

    private void parse(String command) {

        switch (command) {
            case "add":
                if (this.addPlant()) {
                    backyard.updateBackyard(); //only update the backyard if a plant was successfully added
                }
                break;
            case "shovel":
                String[] removeCoordinate = inputCoordinates();
                backyard.removePlant(Integer.valueOf(removeCoordinate[0]), Integer.valueOf(removeCoordinate[1]));
                backyard.updateBackyard();
                break;
            case "skip":
                backyard.updateBackyard();
                break;
            case "collect":
                backyard.collectSun();
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
     * Return a list of plants that the player can afford
     */
    private String getAffordablePlants() {
        String list = "";
        Plants[] plants = Plants.values();
        for (Plants plant : plants) {
            if (backyard.getMoney() >= plant.getCost()) {
                list += plant.toString() + " ";
            }
        }
        return list;
    }

    /**
     * Helper method for the parse for user to add a new plant
     *
     * @return Return true if a plant was successfully added or else return false
     */
    private boolean addPlant() {
        Scanner scanner = new Scanner(System.in);
        String[] addCoordinate;

        //if they cant buy the cheapest plant return false right away
        if (backyard.getMoney() < 50) {
            System.out.println("You need at least 50 sun to buy a plant!!\n");
            return false;
        }

        while (true) {
            System.out.println("What kind of plant do you want to add?");
            System.out.println(getAffordablePlants());
            String response = scanner.nextLine(); //Get user answer
            response = response.trim().toLowerCase();

            switch (response) {
                case "sunflower":
                case "s":
                    //Verify the user has enough money to buy
                    if (backyard.getMoney() < Plants.SUNFLOWER.getCost()) {
                        System.out.println("Insufficient funds!\n");
                        break;
                    }
                    addCoordinate = inputCoordinates();
                    if (backyard.addSprite(Integer.valueOf(addCoordinate[0]), Integer.valueOf(addCoordinate[1]), new Sunflower())) {
                        backyard.setMoney(backyard.getMoney() - Plants.SUNFLOWER.getCost());
                        return true;
                    }
                    break;
                case "peashooter":
                case "p":
                    if (backyard.getMoney() < Plants.PEASHOOTER.getCost()) {
                        System.out.println("Insufficient funds!\n");
                        break;
                    }
                    addCoordinate = inputCoordinates();
                    if (backyard.addSprite(Integer.valueOf(addCoordinate[0]), Integer.valueOf(addCoordinate[1]), new Peashooter())) {
                        backyard.setMoney(backyard.getMoney() - Plants.PEASHOOTER.getCost()); //Hardcoded values for now
                        return true;
                    }
                    break;
                default:
                    System.out.println("Please select a plant!\n");
                    break;
            }
        }
    }

    private void startGame() {
        //user input
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Plants Vs Zombies!\nType anything to start game :");
        String response = scanner.next();

        while (!response.equals("exit") && !(gameOver)) {

            // set zombies for next wave
            if (backyard.getCurrentWave() == null || backyard.getCurrentWave().isComplete()) {
                backyard.setCurrentWave(5);//TODO: Make number of zombies increase as levels go on
            }

            System.out.println(backyard.getCurrentWave());
            System.out.println("Score: " + backyard.getScore() + " Money : " + backyard.getMoney());
            getBackyard().print(); //print backyard
            System.out.println("What is your move? 'Add' 'Shovel' 'Skip' 'Collect' 'Exit'");
            response = scanner.next();
            response = response.trim().toLowerCase();
            scanner.nextLine();

            //Treat User response
            parse(response);
            // }
        }

        if (gameOver) {
            //End game message
            System.out.println("---------------------Game Over!---------------------");
            //System.out.println("Your garden has been overrun! Better luck next time!");
        }
        System.out.println("Thanks for playing!");
    }

    public static void main(String[] args) {
        //Set up the game
        final Game game = new Game();
    }


    public Backyard getBackyard() {
        return backyard;
    }
}