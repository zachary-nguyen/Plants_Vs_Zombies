package View;

import Model.Backyard;
import Model.Sprite;

import javax.swing.*;
import java.awt.*;
import java.util.PriorityQueue;

public class View extends JFrame {

    private JButton addSunflower, addPeashooter, addRepeater, addWallnut, save,  skip, shovel, exit,undo, redo,genWave, load;
    private Tile[][] buttonGrid;
    private JPanel backyardPanel, actionPanel, scorePanel;
    private JFrame frame;
    private JLabel wave, sun, score, zombieAlive, zombieLeft;

    public View() {
        //Initialize Frame
        this.frame = new JFrame("Plants Vs Zombies");
        frame.setLayout(new BorderLayout());

        //Create action Panel
        actionPanel = new JPanel(new GridLayout());
        //Create Action buttons
        addSunflower = new JButton("Sunflower (50)");
        addPeashooter = new JButton("Peashooter (100)");
        addRepeater = new JButton("Repeater (200)");
        addWallnut = new JButton("Walnut (50)");

        save = new JButton("Save");
        load = new JButton("Load");
        skip = new JButton("Skip");
        shovel = new JButton("Shovel");
        undo = new JButton("Undo");
        redo = new JButton("Redo");
        exit = new JButton("Exit");
        genWave = new JButton("GenWave");

        //Set action commands
        addSunflower.setActionCommand("sunflower");
        addPeashooter.setActionCommand("peashooter");
        addRepeater.setActionCommand("repeater");
        addWallnut.setActionCommand("wallnut");
        save.setActionCommand("save");
        load.setActionCommand("load");
        skip.setActionCommand("skip");
        shovel.setActionCommand("shovel");
        exit.setActionCommand("exit");
        undo.setActionCommand("undo");
        redo.setActionCommand("redo");
        genWave.setActionCommand("genWave");

        //Add buttons to action panel
        actionPanel.add(addSunflower);
        actionPanel.add(addPeashooter);
        actionPanel.add(addRepeater);
        actionPanel.add(addWallnut);
        actionPanel.add(save);
        actionPanel.add(load);
        actionPanel.add(skip);
        actionPanel.add(shovel);
        actionPanel.add(undo);
        actionPanel.add(redo);
        actionPanel.add(genWave);
        actionPanel.add(exit);

        //Create score panel
        scorePanel = new JPanel(new GridLayout());

        //Create new text fields
        wave = new JLabel("Wave ");
        score = new JLabel("Score: ");
        sun = new JLabel("Sun: ");
        zombieAlive = new JLabel("Zombies Alive: ");
        zombieLeft = new JLabel("Zombies Left: ");

        scorePanel.add(wave);
        scorePanel.add(score);
        scorePanel.add(sun);
        scorePanel.add(zombieAlive);
        scorePanel.add(zombieLeft);

        //initialize button grid
        backyardPanel = new JPanel(new GridLayout(Backyard.HEIGHT, Backyard.WIDTH));
        backyardPanel.setOpaque(false);
        buttonGrid = new Tile[Backyard.HEIGHT][Backyard.WIDTH];

        for (int row = 0; row < Backyard.HEIGHT; row++) {
            for (int col = 0; col < Backyard.WIDTH; col++) {
                Tile tile = new Tile(row, col);
                tile.setOpaque(true);
                tile.setContentAreaFilled(false);
                tile.setBorderPainted(true);
                tile.setFocusPainted(false);
                //alternate background colors
                if (row % 2 == 0) {
                    if (col % 2 == 0) {
                        tile.setBackground(new Color(65, 195, 32));
                    } else {
                        tile.setBackground(new Color(0, 153, 0));
                    }
                } else {
                    if (col % 2 == 0) {
                        tile.setBackground(new Color(102, 204, 0));
                    } else {
                        tile.setBackground(new Color(0, 204, 0));

                    }
                }
                buttonGrid[row][col] = tile;
                backyardPanel.add(buttonGrid[row][col]);
            }
        }
        frame.getContentPane().add(backyardPanel, BorderLayout.CENTER);
        frame.getContentPane().add(actionPanel, BorderLayout.PAGE_START);
        frame.getContentPane().add(scorePanel, BorderLayout.AFTER_LAST_LINE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 1000);
        frame.setVisible(true);
    }

    public void displayBackyard(PriorityQueue[][] map) {

        for (int row = 0; row < Backyard.HEIGHT; row++) {
            for (int col = 0; col < Backyard.WIDTH; col++) {
                try {
                    if (map[row][col].peek() != null) { //null pointer safeguard
                        Sprite sprite = (Sprite) map[row][col].peek();
                        buttonGrid[row][col].setImage(sprite.getImage());
                    } else {
                        buttonGrid[row][col].setImage(null); // remove image
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    public void disableCommandBtns() {
        addSunflower.setEnabled(false);
        addPeashooter.setEnabled(false);
        addRepeater.setEnabled(false);
        addWallnut.setEnabled(false);
        save.setEnabled(false);
        load.setEnabled(false);
        skip.setEnabled(false);
        shovel.setEnabled(false);
        undo.setEnabled(false);
        redo.setEnabled(false);
        exit.setEnabled(false);
    }

    public void enableCommandBtns() {
        addSunflower.setEnabled(true);
        addPeashooter.setEnabled(true);
        addRepeater.setEnabled(true);
        addWallnut.setEnabled(true);
        save.setEnabled(true);
        load.setEnabled(true);
        skip.setEnabled(true);
        shovel.setEnabled(true);
        undo.setEnabled(true);
        redo.setEnabled(true);
        exit.setEnabled(true);
    }

    public void updateScorePanel(int wave, int score, int sun, int zombieAlive, int zombieLeft) {
        this.wave.setText("Wave: " + wave);
        this.score.setText("Score: " + score);
        this.sun.setText("Sun: " + sun);
        this.zombieAlive.setText("Zombies Alive: " + zombieAlive);
        this.zombieLeft.setText("Zombies Remaining: " +zombieLeft);
    }

    public JButton getAddSunflower() {
        return addSunflower;
    }

    public void setAddSunflower(JButton addSunflower) {
        this.addSunflower = addSunflower;
    }

    public JButton getAddPeashooter() {
        return addPeashooter;
    }

    public void setAddPeashooter(JButton addPeashooter) {
        this.addPeashooter = addPeashooter;
    }

    public JButton getAddRepeater() {
        return addRepeater;
    }

    public void setAddRepeater(JButton addRepeater) {
        this.addRepeater = addRepeater;
    }

    public JButton getAddWallnut() {
        return addWallnut;
    }

    public void setAddWallnut(JButton addWallnut) {
        this.addWallnut = addWallnut;
    }

    public JButton getSave() {
        return save;
    }

    public void setSave(JButton save) {
        this.save = save;
    }

    public JButton getSkip() {
        return skip;
    }

    public void setSkip(JButton skip) {
        this.skip = skip;
    }

    public JButton getShovel() {
        return shovel;
    }

    public void setShovel(JButton shovel) {
        this.shovel = shovel;
    }

    public JButton getExit() {
        return exit;
    }

    public void setExit(JButton exit) {
        this.exit = exit;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public Tile[][] getButtonGrid() {
        return buttonGrid;
    }

    public JPanel getBackyardPanel() {
        return backyardPanel;
    }

    public void setBackyardPanel(JPanel backyardPanel) {
        this.backyardPanel = backyardPanel;
    }

    public JPanel getActionPanel() {
        return actionPanel;
    }

    public void setActionPanel(JPanel actionPanel) {
        this.actionPanel = actionPanel;
    }

    public JPanel getScorePanel() {
        return scorePanel;
    }

    public void setScorePanel(JPanel scorePanel) {
        this.scorePanel = scorePanel;
    }

    public JLabel getWave() {
        return wave;
    }

    public void setWave(JLabel wave) {
        this.wave = wave;
    }

    public JLabel getSun() {
        return sun;
    }

    public void setSun(JLabel sun) {
        this.sun = sun;
    }

    public JLabel getScore() {
        return score;
    }

    public void setScore(JLabel score) {
        this.score = score;
    }

    public JLabel getZombieAlive() {
        return zombieAlive;
    }

    public void setZombieAlive(JLabel zombieAlive) {
        this.zombieAlive = zombieAlive;
    }

    public JLabel getZombieLeft() {
        return zombieLeft;
    }

    public void setZombieLeft(JLabel zombieLeft) {
        this.zombieLeft = zombieLeft;
    }

    public JButton getUndo() {
        return undo;
    }

    public void setUndo(JButton undo) {
        this.undo = undo;
    }

    public JButton getRedo() {return redo;}

    public void setRedo(JButton redo) {this.redo = redo;}

    public JButton getGenWave() {return genWave;}

    public void setGenWave(JButton genWave) {this.genWave = genWave;}

    public JButton getLoad() {
        return load;
    }

    public void setLoad(JButton load) {
        this.load = load;
    }
}
