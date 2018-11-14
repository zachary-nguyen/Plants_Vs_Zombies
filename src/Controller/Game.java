package Controller;

import Model.*;
import View.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Game class is a controller class in charge of treating user input and the flow of the game.
 *
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Mathew Smith
 */
public class Game implements ActionListener {

    private Backyard backyard;
    private View view;
    public static boolean gameOver = false;
    private static final int MAX_NUMBER_OF_WAVES = 2;
    private static int currentWaveNumber;

    //keeps track of plants to avoid hardcoded values and make it easier to add new plants
    enum Plants {
        SUNFLOWER("sunflower", 50), PEASHOOTER("peashooter", 100);
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
        currentWaveNumber = 1;

        view = new View();
        addActionListeners();
        startGame();

    }

    /**
     * Initialiaze Action Listener
     */
    public void initGame() {

    }

    public void addActionListeners() {
        view.getAddSunflower().addActionListener(this);
        view.getAddPeashooter().addActionListener(this);
        view.getSave().addActionListener(this);
        view.getCollect().addActionListener(this);
        view.getSkip().addActionListener(this);
        view.getShovel().addActionListener(this);
        view.getExit().addActionListener(this);

        for (int i = 0; i < backyard.HEIGHT; i++) {
            for (int j = 0; j < backyard.WIDTH; j++) {
                view.getButtonGrid()[i][j].addActionListener(this);
            }
        }
    }

    /**
     * Treat ActionEvents
     *
     * @param e Event being treated.
     */
    private AbstractPlant plantToAdd = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (plantToAdd != null && e.getSource() instanceof Tile) {
            Tile tile = (Tile) e.getSource();
            backyard.addSprite(tile.getCol(), tile.getRow(), plantToAdd);
            plantToAdd = null;
            view.enableCommandBtns();
            try {
                backyard.updateBackyard();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            nextTurn();
        } else if (e.getSource() instanceof JButton) {
            JButton btn = (JButton) e.getSource();

            switch (btn.getActionCommand()) {
                case "peashooter":
                    try {
                        plantToAdd = new Peashooter();
                        view.disableCommandBtns();
                        backyard.setMoney(backyard.getMoney()-Plants.PEASHOOTER.getCost());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "sunflower":
                    try {
                        plantToAdd = new Sunflower();
                        view.disableCommandBtns();
                        backyard.setMoney(backyard.getMoney()-Plants.SUNFLOWER.getCost());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "collect":
                    try {
                        backyard.collectSun();
                        backyard.updateBackyard();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    nextTurn();
                    break;
                case "skip":
                    try {
                        backyard.updateBackyard();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    nextTurn();
                    break;
                case "exit":
                    System.exit(0);
                    break;
            }
        }
    }


    /**
     * Helper method for main method to get coordinates to add/remove sprite.
     *
     * @return Return the coordinates in an array.
     */
/*
    private String[] inputCoordinates() {
        Scanner scanner = new Scanner(System.in);
        int xValueToAdd;
        int yValueToAdd;
        String[] coordinate;

        while (true) {
            System.out.println("Select coordinates in format 'X Y' ");
            String response = scanner.nextLine();
            coordinate = response.trim().split(" ");

            if (coordinate.length == 2 && isInteger(coordinate[0]) && isInteger(coordinate[1])) {
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
*/
    /**
     * Parse the user input and determine what action to take.
     *
     * @param command The command being parsed.
     */
/*
    private void parse(String command) throws IOException {

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

*/
    /**
     * Disbales buttons for adding plants that player cannot afford
     *
     */
    private void disableUnaffordablePlants() {
        if(backyard.getMoney()<50){
            view.getAddSunflower().setEnabled(false);
        }else{
            view.getAddSunflower().setEnabled(true);
        }

        if(backyard.getMoney()<100){
            view.getAddPeashooter().setEnabled(false);
        }else{
            view.getAddPeashooter().setEnabled(true);
        }
    }

    /**
     * Helper method for the parse for user to add a new plant
     *
     * @return Return true if a plant was successfully added or else return false
     */
/*
    private boolean addPlant() throws IOException {
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
*/

    /**
     * This is the main loop for the game. Treats user inputs and determines when game is done.
     */
    private void startGame()  {
        //user input
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Plants Vs Zombies!\nType anything to start game :");
        scanner.next();
        backyard.setCurrentWave(5);//initialize the first wave
        view.displayBackyard(backyard.getMap());
        nextTurn();
/*
        while (!response.equals("exit") && !(gameOver)) {

            //Prepares new wave
            if (backyard.getCurrentWave() != null && backyard.getCurrentWave().isComplete()) {
                currentWaveNumber++;
                //Check if the level is completed
                if(currentWaveNumber == MAX_NUMBER_OF_WAVES){
                    System.out.println("---------------------Level Completed!---------------------");
                    break;
                }
                System.out.println("---------------------Wave Complete!---------------------");
                System.out.println("Type anything to start the next wave:");
                Scanner scan = new Scanner(System.in);
                scan.next();
                backyard.setCurrentWave(5*currentWaveNumber);//creates a new wave for backyard
            }



        System.out.println(backyard.getCurrentWave());
        System.out.println("Score: " + backyard.getScore() + " Money : " + backyard.getMoney());
        //getBackyard().print(); //print backyard
        System.out.println("What is your move? 'Add' 'Shovel' 'Skip' 'Collect' 'Exit'");
        response = scanner.next();
        response = response.trim().toLowerCase();
        scanner.nextLine();

        //Treat User response
        parse(response);

        view.displayBackyard(backyard.getMap());

        }


        if (gameOver) {
            //End game message
            System.out.println("---------------------Game Over!---------------------");
            //System.out.println("Your garden has been overrun! Better luck next time!");
        }
        */

    }

    public void nextTurn() {
        //while (!response.equals("exit") && !(gameOver)) {

        //Prepares new wave
        disableUnaffordablePlants();
        if (backyard.getCurrentWave() != null && backyard.getCurrentWave().isComplete()) {
            currentWaveNumber++;
            //Check if the level is completed
            if (currentWaveNumber == MAX_NUMBER_OF_WAVES) {
                System.out.println("---------------------Level Completed!---------------------");

            }
            System.out.println("---------------------Wave Complete!---------------------");
            System.out.println("Type anything to start the next wave:");
            Scanner scan = new Scanner(System.in);
            scan.next();
            backyard.setCurrentWave(5 * currentWaveNumber);//creates a new wave for backyard
        }

        System.out.println(backyard.getCurrentWave());
        System.out.println("Score: " + backyard.getScore() + " Money : " + backyard.getMoney());

        view.displayBackyard(backyard.getMap());

        if (gameOver) {
            //End game message
            System.out.println("---------------------Game Over!---------------------");
            //System.out.println("Your garden has been overrun! Better luck next time!");
        }
    }

    /**
     * Determine is a string is Integer
     *
     * @param str String to check
     * @return Return true if String is integer else return false
     */
    private static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        //Set up the game
        final Game game = new Game();
    }

    /***********************
     * GETTERS and SETTERS
     ***********************/

    public static int getCurrentWaveNumber() {
        return currentWaveNumber;
    }

    public static void setCurrentWaveNumber(int currentWaveNumber) {
        Game.currentWaveNumber = currentWaveNumber;
    }

    private Backyard getBackyard() {
        return backyard;
    }
}