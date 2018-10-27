package Controller;

import Model.Peashooter;
import Model.Sunflower;
import View.Backyard;

import java.util.Scanner;

public class Game {


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
            return name + "("+cost+")";
        }

        Plants(String name, int cost) {
            this.name = name;
            this.cost = cost;
        }
    }

    private Backyard backyard;

    public static int wave;
    private int round;

    public static boolean gameOver = false;
    public static boolean endRound = false;

    /**
     * Constructor for game.
     */
    public Game() {
        this.backyard = new Backyard();
        this.wave = 1;
        this.round = 1;
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
                backyard.removeSprite(Integer.valueOf(removeCoordinate[0]), Integer.valueOf(removeCoordinate[1]));
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
        Return a list of plants that the player can afford
     */
    private String getAffordablePlants() {
        String list = "";
        Plants[] plants = Plants.values();
        for (Plants plant : plants) {
            if (backyard.getMoney() >= plant.getCost()) {
                list += plant.toString() +" ";
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
        wave = 1;

        //user input
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Plants Vs Zombies!\nType anything to start game :");
        String response = scanner.next();

        while (!response.equals("exit") && !(gameOver)) {

            // set zombies for next wave
            Backyard.setNumZombiesSpawn(5);
            Backyard.setSpawnCounter(2);
            while (!response.equals("exit") && !(endRound) && !gameOver) {
                System.out.println("----------WAVE " + getWave() + ", ROUND " + getRound() + "----------");
                //round++;
                System.out.println("Score: " + backyard.getScore() + " Money : " + backyard.getMoney());
                getBackyard().print(); //print backyard
                System.out.println("What is your move? 'Add' 'Shovel' 'Skip' 'Collect' 'Exit'");
                response = scanner.next();
                response = response.trim().toLowerCase();
                scanner.nextLine();

                //Treat User response
                parse(response);
            }

            System.out.println("---------------------Wave Complete!---------------------");
            endRound = false;

            if (getWave() == 2) { // 2 waves per round
                // reset wave for next round
                // increment wave
                System.out.println("-------------------Level Complete------------------");
                gameOver = true;


            }
            setWave(getWave() + 1);
        }

        if (gameOver) {
            //End game message
            System.out.println("---------------------Game Over!---------------------");
            //System.out.println("Your garden has been overrun! Better luck next time!");
        }
    }

    public static void main(String[] args) {
        //Set up the game
        final Game game = new Game();
    }


    public Backyard getBackyard() {
        return backyard;
    }

    public int getWave() {
        return wave;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int level) {
        this.round = level;
    }
}