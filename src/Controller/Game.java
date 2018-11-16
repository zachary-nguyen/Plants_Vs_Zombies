package Controller;

import Model.Backyard;
import Model.Peashooter;
import Model.Sunflower;
import View.View;
import Model.*;
import View.*;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
        nextTurn();
    }

    private void addActionListeners() {
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
    private boolean removePlant = false;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (plantToAdd != null && e.getSource() instanceof Tile) {
            Tile tile = (Tile) e.getSource();
            backyard.addSprite(tile.getCol(), tile.getRow(), plantToAdd);
            plantToAdd = null;
            view.enableCommandBtns();
            backyard.updateBackyard();
            nextTurn();
        } else if (removePlant && e.getSource() instanceof Tile) {
            Tile tile = (Tile) e.getSource();
            if (backyard.removePlant(tile.getCol(), tile.getRow())) {
                removePlant = false;
                view.enableCommandBtns();
                backyard.updateBackyard();
                nextTurn();
            }
        } else if (e.getSource() instanceof JButton) {
            JButton btn = (JButton) e.getSource();

            switch (btn.getActionCommand()) {
                case "peashooter":
                    plantToAdd = new Peashooter();
                    view.disableCommandBtns();
                    backyard.setMoney(backyard.getMoney() - Plants.PEASHOOTER.getCost());
                    break;
                case "sunflower":
                    plantToAdd = new Sunflower();
                    view.disableCommandBtns();
                    backyard.setMoney(backyard.getMoney() - Plants.SUNFLOWER.getCost());
                    break;
                case "collect":
                    backyard.collectSun();
                    backyard.updateBackyard();
                    nextTurn();
                    break;
                case "skip":
                    backyard.updateBackyard();
                    nextTurn();
                    break;
                case "save":
                    //TODO: implement save (milestone 3)
                case "shovel":
                    removePlant = true;
                    view.disableCommandBtns();
                    break;
                case "exit":
                    System.exit(0);
                    break;
            }
        }
    }

    /**
     * Disbales buttons for adding plants that player cannot afford
     */
    private void disableUnaffordablePlants() {
        if (backyard.getMoney() < 50) {
            view.getAddSunflower().setEnabled(false);
        } else {
            view.getAddSunflower().setEnabled(true);
        }

        if (backyard.getMoney() < 100) {
            view.getAddPeashooter().setEnabled(false);
        } else {
            view.getAddPeashooter().setEnabled(true);
        }
    }


    /**
     * This is the main loop for the game. Treats user inputs and determines when game is done.
     */
    private void startGame() throws IOException {
        view.displayBackyard(backyard.getMap());
        nextTurn();
    }

    public void nextTurn() {
        //Prepares new wave
        backyard.setCurrentWave(5);//initialize the first wave
        view.displayBackyard(backyard.getMap());
        view.updateScorePanel(currentWaveNumber, backyard.getScore(), backyard.getMoney(),
                backyard.getCurrentWave().getNumZombieAlive(), backyard.getCurrentWave().getNumZombiesSpawn());

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
        view.updateScorePanel(currentWaveNumber, backyard.getScore(), backyard.getMoney(),
                backyard.getCurrentWave().getNumZombieAlive(), backyard.getCurrentWave().getNumZombiesSpawn());

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