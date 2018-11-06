package View;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    private JMenuItem addSunflower,addPeashooter,save,collect,skip,shovel,exit;

    private JFrame frame;
    public View(){
        //Initialize Frame
        this.frame = new JFrame("Plants Vs Zombies");
        frame.setLayout(new BorderLayout());

        //Create menu bar
        JMenuBar menuBar = new JMenuBar();

        //Create menu
        JMenu menu  = new JMenu("Actions");

        //Create menu items
        JMenuItem  addSunflower = new JMenuItem("Sunflower");
        JMenuItem  addPeashooter = new JMenuItem("Peashooter");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem collect = new JMenuItem("Collect");
        JMenuItem skip = new JMenuItem("Skip");
        JMenuItem  shovel = new JMenuItem("Shovel");
        JMenuItem exit = new JMenuItem("Exit");


        menu.add(addSunflower);
        menu.add(addPeashooter);
        menu.add(save);
        menu.add(collect);
        menu.add(skip);
        menu.add(shovel);
        menu.add(exit);


        menuBar.add(menu);

        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,800);
        frame.setVisible(true);
    }

    public JMenuItem getAddSunflower() {
        return addSunflower;
    }

    public void setAddSunflower(JMenuItem addSunflower) {
        this.addSunflower = addSunflower;
    }

    public JMenuItem getAddPeashooter() {
        return addPeashooter;
    }

    public void setAddPeashooter(JMenuItem addPeashooter) {
        this.addPeashooter = addPeashooter;
    }

    public JMenuItem getSave() {
        return save;
    }

    public void setSave(JMenuItem save) {
        this.save = save;
    }

    public JMenuItem getCollect() {
        return collect;
    }

    public void setCollect(JMenuItem collect) {
        this.collect = collect;
    }

    public JMenuItem getSkip() {
        return skip;
    }

    public void setSkip(JMenuItem skip) {
        this.skip = skip;
    }

    public JMenuItem getShovel() {
        return shovel;
    }

    public void setShovel(JMenuItem shovel) {
        this.shovel = shovel;
    }

    public JMenuItem getExit() {
        return exit;
    }

    public void setExit(JMenuItem exit) {
        this.exit = exit;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
