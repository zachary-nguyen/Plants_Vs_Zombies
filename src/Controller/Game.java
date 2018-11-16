package Controller;

import Model.Backyard;
import Model.Peashooter;
import Model.Sunflower;
import View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Game class is a controller class in charge of treating user input and the flow of the game.
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Mathew Smith
 */
public class Game implements ActionListener {

    private Backyard backyard;
    private View view;
    public static boolean gameOver = false;
    private static final int MAX_NUMBER_OF_WAVES = 2;
    private static int currentWaveNumber;
    private String currentPlant = "";
    private int count = 0;

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
    public Game() throws IOException {
        this.backyard = new Backyard();
        currentWaveNumber = 1;

        view = new View();
        addActionListeners();
        startGame();

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

    public void actionPerformed(ActionEvent e){
        int row = 0;
        int col = 0;
        JButton pressedButton = (JButton)e.getSource();
        if (pressedButton.getActionCommand().equals("peashooter")) {

        } else if ((e.getSource()) == view.getAddSunflower()) {
            currentPlant = "Sunflower";
        } else if ((e.getSource()) == view.getSave()) {
            currentPlant = "Save";
        } else if (pressedButton.getActionCommand().equals("collect")) {
            try{
                this.backyard.collectSun();
            }catch(IOException e2) {
            }
        } else if ((e.getSource()) == view.getSkip()) {
            currentPlant = "Skip";
        } else if ((e.getSource()) == view.getShovel()) {
            currentPlant = "Shovel";
        } else if (pressedButton.getActionCommand().equals("exit")) {
            System.exit(0);
        }


        if (count > 0) {
            if (currentPlant.equals("Peashooter")) {
                try {
                    backyard.addSprite(row, col, new Peashooter());
                    return;
                } catch (IOException e2) {
                }
            } else if (currentPlant.equals("Sunflower")) {
                try {
                    backyard.addSprite(row, col, new Sunflower());
                    return;
                } catch (IOException e3) {
                }
            } else if (currentPlant.equals("Skip")) {
                try {
                    backyard.updateBackyard();
                    return;
                } catch (IOException e1) {
                }
            } else if (currentPlant.equals("Exit")) {
                System.exit(0);
            } else if (currentPlant.equals("Shovel")) {
                backyard.removePlant(row, col);
                return;
            } else if (currentPlant.equals("Collect")) {
                try {
                    backyard.collectSun();
                    return;
                }
                catch (IOException e4) {}
            }
        }
        count++;
    }

     /**
     * This is the main loop for the game. Treats user inputs and determines when game is done.
     */
    private void startGame() throws IOException {
        view.displayBackyard(backyard.getMap());
    }


    /**
     * Determine is a string is Integer
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