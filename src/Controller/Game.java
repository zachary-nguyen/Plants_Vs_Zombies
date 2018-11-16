package Controller;

import Model.AbstractPlant;
import Model.Backyard;
import Model.Peashooter;
import Model.Sunflower;
import View.Tile;
import View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Game class is a controller class in charge of treating user input and the flow of the game.
 *
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Mathew Smith
 */
public class Game implements ActionListener {

    private Backyard backyard;
    private View view;

    public static boolean gameOver = false;
    private static final int MAX_NUMBER_OF_WAVES = 3;
    private static int currentWaveNumber;

    private AbstractPlant plantToAdd = null;
    private boolean removePlant = false;

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
        this.view = new View();
        this.backyard.setCurrentWave(5);
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

        for (int i = 0; i < Backyard.HEIGHT; i++) {
            for (int j = 0; j < Backyard.WIDTH; j++) {
                view.getButtonGrid()[i][j].addActionListener(this);
            }
        }
    }

    /**
     * Treat ActionEvents
     *
     * @param e Event being treated.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (plantToAdd != null && e.getSource() instanceof Tile) {
            Tile tile = (Tile) e.getSource();
            if(backyard.addSprite(tile.getCol(), tile.getRow(), plantToAdd)) { //make sure we're not trying to add on an existing sprite
                plantToAdd = null;
                view.enableCommandBtns();
                backyard.updateBackyard();
                nextTurn();
            }
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
     * Disables buttons for adding plants that player cannot afford
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

    private void nextTurn() {
        if(gameOver){
            JOptionPane.showMessageDialog(this.view.getFrame(),"Your backyard has been overrun!");
            System.exit(0);
        }
        view.displayBackyard(backyard.getMap());
        view.updateScorePanel(currentWaveNumber, backyard.getScore(), backyard.getMoney(),
                backyard.getCurrentWave().getNumZombieAlive(), backyard.getCurrentWave().getNumZombiesSpawn());

        disableUnaffordablePlants();
        if (backyard.getCurrentWave() != null && backyard.getCurrentWave().isComplete()) {
            currentWaveNumber++;
            //Check if the level is completed
            if (currentWaveNumber == MAX_NUMBER_OF_WAVES) {
                JOptionPane.showMessageDialog(this.view.getFrame(),"YOU HAVE WON THE GAME!!!");
                System.exit(0);
            }
            JOptionPane.showMessageDialog(this.view.getFrame(),"WAVE COMPLETE!!!");
            backyard.setCurrentWave(5 * currentWaveNumber);//creates a new wave for backyard
        }

        view.displayBackyard(backyard.getMap());
        view.updateScorePanel(currentWaveNumber, backyard.getScore(), backyard.getMoney(),
                backyard.getCurrentWave().getNumZombieAlive(), backyard.getCurrentWave().getNumZombiesSpawn());

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