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
                case "shovel":

                    break;
                case "exit":
                    System.exit(0);
                    break;
            }
        }
    }

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

    public static void main(String[] args) {
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