package View;

import Model.Backyard;
import Model.Sprite;

import javax.swing.*;
import java.awt.*;
import java.util.PriorityQueue;

public class View extends JFrame {

    private JButton addSunflower, addPeashooter, save, collect, skip, shovel, exit;
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
        addSunflower = new JButton("Sunflower");
        addPeashooter = new JButton("Peashooter");
        save = new JButton("Save");
        collect = new JButton("Collect");
        skip = new JButton("Skip");
        shovel = new JButton("Shovel");
        exit = new JButton("Exit");

        //Set action commands
        addSunflower.setActionCommand("sunflower");
        addPeashooter.setActionCommand("peashooter");
        save.setActionCommand("save");
        collect.setActionCommand("collect");
        skip.setActionCommand("skip");
        shovel.setActionCommand("shovel");
        exit.setActionCommand("exit");

        //Add buttons to action panel
        actionPanel.add(addSunflower);
        actionPanel.add(addPeashooter);
        actionPanel.add(save);
        actionPanel.add(collect);
        actionPanel.add(skip);
        actionPanel.add(shovel);
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
        frame.setSize(1280, 1000);
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
        save.setEnabled(false);
        collect.setEnabled(false);
        skip.setEnabled(false);
        shovel.setEnabled(false);
        exit.setEnabled(false);
    }

    public void enableCommandBtns() {
        addSunflower.setEnabled(true);
        addPeashooter.setEnabled(true);
        save.setEnabled(true);
        collect.setEnabled(true);
        skip.setEnabled(true);
        shovel.setEnabled(true);
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

    public JButton getSave() {
        return save;
    }

    public void setSave(JButton save) {
        this.save = save;
    }

    public JButton getCollect() {
        return collect;
    }

    public void setCollect(JButton collect) {
        this.collect = collect;
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
}
