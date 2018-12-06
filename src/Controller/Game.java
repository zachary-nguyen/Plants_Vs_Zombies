package Controller;

import Model.*;
import View.Tile;
import View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Stack;

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

    private Stack<Backyard> undo;
    private Stack<Backyard> redo;

    //keeps track of plants to avoid hardcoded values and make it easier to add new plants
    enum Plants {
        SUNFLOWER("sunflower", 50), PEASHOOTER("peashooter", 100), REPEATER("repeater", 200), WALNUT("walnut", 50);
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
        this.backyard.setCurrentWaveAmountOfZombies(5);

        currentWaveNumber = 1;
        this.view = new View();
        this.undo = new Stack<>();
        this.redo = new Stack<>();

        addActionListeners();
        backyard.getCurrentWave().generateZombies();
        nextTurn();
    }

    private void addActionListeners() {
        view.getAddSunflower().addActionListener(this);
        view.getAddPeashooter().addActionListener(this);
        view.getAddRepeater().addActionListener(this);
        view.getAddWallnut().addActionListener(this);
        view.getSave().addActionListener(this);
        view.getSkip().addActionListener(this);
        view.getShovel().addActionListener(this);
        view.getExit().addActionListener(this);
        view.getUndo().addActionListener(this);
        view.getRedo().addActionListener(this);
        view.getGenWave().addActionListener(this);
        view.getLoad().addActionListener(this);
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
        if (e.getSource() instanceof Tile) {
            Tile tile = (Tile) e.getSource();

            if (plantToAdd != null && backyard.addSprite(tile.getCol(), tile.getRow(), plantToAdd)) { //make sure we're not trying to add on an existing sprite
                plantToAdd = null;
                view.enableCommandBtns();
                backyard.updateBackyard();
                nextTurn();

            } else if (removePlant && backyard.removePlant(tile.getCol(), tile.getRow())) {
                removePlant = false;
                view.enableCommandBtns();
                backyard.updateBackyard();
                nextTurn();

            }else{
                this.undo.push(this.backyard.cloneBackyard());
                backyard.collectSun(tile.getRow(),tile.getCol());
                view.displayBackyard(backyard.getMap());
                view.updateScorePanel(currentWaveNumber, backyard.getScore(), backyard.getMoney(),
                        backyard.getCurrentWave().getNumZombieAlive(), backyard.getCurrentWave().getNumZombiesSpawn());
                disableUnaffordablePlants();
            }
        } else if (e.getSource() instanceof JButton) {
            JButton btn = (JButton) e.getSource();

            switch (btn.getActionCommand()) {
                case "peashooter":
                    //Before an action is performed add it to the undo stack
                    this.undo.push(this.backyard.cloneBackyard());
                    plantToAdd = new Peashooter();
                    view.disableCommandBtns();
                    backyard.setMoney(backyard.getMoney() - Plants.PEASHOOTER.getCost());
                    break;
                case "sunflower":
                    //Before an action is performed add it to the undo stack
                    this.undo.push(this.backyard.cloneBackyard());
                    plantToAdd = new Sunflower();
                    view.disableCommandBtns();
                    backyard.setMoney(backyard.getMoney() - Plants.SUNFLOWER.getCost());
                    break;
                case "repeater":
                    //Before an action is performed add it to the undo stack
                    this.undo.push(this.backyard.cloneBackyard());
                    plantToAdd = new Repeater();
                    view.disableCommandBtns();
                    backyard.setMoney(backyard.getMoney() - Plants.REPEATER.getCost());
                    break;
                case "wallnut":
                    //Before an action is performed add it to the undo stack
                    this.undo.push(this.backyard.cloneBackyard());
                    plantToAdd = new Walnut();
                    view.disableCommandBtns();
                    backyard.setMoney(backyard.getMoney() - Plants.WALNUT.getCost());
                    break;
                case "skip":
                    //Before an action is performed add it to the undo stack
                    this.undo.push(this.backyard.cloneBackyard());
                    backyard.updateBackyard();
                    nextTurn();
                    break;
                case "save":
                    JFileChooser chooser = new JFileChooser();
                    int result = chooser.showSaveDialog(this.view.getFrame());
                    if(result == JFileChooser.APPROVE_OPTION){
                        String filename = chooser.getSelectedFile().getName();
                        String dir = chooser.getCurrentDirectory().toString();
                        if(filename != null && dir != null) {
                            this.saveGame(filename, dir);
                        }
                    }
                    if(result == JFileChooser.CANCEL_OPTION){
                        System.out.println("Save cancelled");
                    }
                    break;
                case "load":
                    JFileChooser chooseLoad = new JFileChooser();
                    int resultLoad = chooseLoad.showOpenDialog(this.view.getFrame());
                    if(resultLoad == JFileChooser.APPROVE_OPTION){
                        String filename = chooseLoad.getSelectedFile().getName();
                        String dir = chooseLoad.getCurrentDirectory().toString();
                        if(filename != null && dir != null) {
                            this.loadGame(filename, dir);
                        }
                    }

                    if(resultLoad == JFileChooser.CANCEL_OPTION){
                        System.out.println("Cancelled loading");
                    }
                    nextTurn();
                    break;
                case "shovel":
                    //Before an action is performed add it to the undo stack
                    removePlant = true;
                    view.disableCommandBtns();
                    break;
                case "undo":
                    if (this.undo.size() > 0) {
                        this.redo.push(this.backyard.cloneBackyard()); //add turn to redo stack
                        this.backyard = this.undo.pop(); //set current model to top of undo stack
                        nextTurn();
                    }
                    break;
                case "redo":
                    if (this.redo.size() > 0) {
                        this.undo.push(this.backyard.cloneBackyard()); //add the turn to the undo stack
                        this.backyard = this.redo.pop(); //set current model to top of redo stack
                        nextTurn();
                    }
                    break;
                case "exit":
                    System.exit(0);
                    break;
                case "genWave":
                    try {
                        int zombies = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of regular zombies to spawn"));
                        int flagzombies = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of flag zombies to spawn"));
                        int conezombies = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of Conehead zombies to spawn"));
                        int saveOption = JOptionPane.showConfirmDialog(null, "Do you want to save the\nlevel to XML?");
                        if (saveOption == JOptionPane.YES_OPTION){
                            System.out.println("Saving to XML");
                            // add logic here
                        }
                        this.backyard.setCurrentWave(new Wave(zombies, flagzombies, conezombies));
                    }catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "Invalid number, please try again");
                    }
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
            view.getAddWallnut().setEnabled(false);
        } else {
            view.getAddSunflower().setEnabled(true);
            view.getAddWallnut().setEnabled(true);
        }

        if (backyard.getMoney() < 100) {
            view.getAddPeashooter().setEnabled(false);
            view.getAddRepeater().setEnabled(false);
        } else {
            view.getAddPeashooter().setEnabled(true);
            view.getAddRepeater().setEnabled(true);

        }

        if(backyard.getMoney() < 200){
            view.getAddRepeater().setEnabled(false);
        } else {
            view.getAddRepeater().setEnabled(true);
        }
    }

    private void nextTurn() {
        if (gameOver) {
            JOptionPane.showMessageDialog(this.view.getFrame(), "Your backyard has been overrun!");
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
                JOptionPane.showMessageDialog(this.view.getFrame(), "YOU HAVE WON THE GAME!!!");
                System.exit(0);
            }
            JOptionPane.showMessageDialog(this.view.getFrame(), "WAVE COMPLETE!!!");
            backyard.setCurrentWaveAmountOfZombies(5 * currentWaveNumber);//creates a new wave for backyard
            backyard.getCurrentWave().generateZombies();
        }
    }

    /**
     * Save the current backyard state to a file to be able to load it later on.
     */
    private void saveGame(String filename, String dir){
        try{
            FileOutputStream file = new FileOutputStream(dir + "\\" +  filename + ".ser"); //create a new save file
            ObjectOutputStream out = new ObjectOutputStream(file);

            //Serialize backyard object
            out.writeObject(this.backyard);
            out.close();
            file.close();
        }catch(IOException ex){
            System.out.println("Error saving!");
        }
    }

    /**
     * Load a game file to resume the game from a different state.
     */
    private void loadGame(String filename, String dir){
        try{
            FileInputStream file = new FileInputStream(dir + "\\"  + filename);
            ObjectInputStream in = new ObjectInputStream(file);

            //Deserialize backyard object
            this.backyard = (Backyard)in.readObject();
            in.close();
            file.close();
        }catch(IOException ex){
            System.out.println("Error loading!");
        } catch(ClassNotFoundException ex){
            System.out.println("Class not found exception caught!");
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

    public AbstractPlant getPlantToAdd() {
        return plantToAdd;
    }

    public void setPlantToAdd(AbstractPlant plantToAdd) {
        this.plantToAdd = plantToAdd;
    }

    public boolean isRemovePlant() {
        return removePlant;
    }

    public void setRemovePlant(boolean removePlant) {
        this.removePlant = removePlant;
    }

    public Stack<Backyard> getUndo() {
        return undo;
    }

    public void setUndo(Stack<Backyard> undo) {
        this.undo = undo;
    }

    public void setBackyard(Backyard backyard) {
        this.backyard = backyard;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    private Backyard getBackyard() {
        return backyard;
    }
}