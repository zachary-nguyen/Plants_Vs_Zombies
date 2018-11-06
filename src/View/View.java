package View;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    private JButton addSunflower,addPeashooter,save,collect,skip,shovel,exit;

    private JFrame frame;
    public View(){
        //Initialize Frame
        this.frame = new JFrame("Plants Vs Zombies");
        frame.setLayout(new FlowLayout());

        //Create menu bar
        JMenuBar menuBar = new JMenuBar();

        //Create menu items
        JButton  addSunflower = new JButton("Sunflower");
        JButton addPeashooter = new JButton("Peashooter");
        JButton save = new JButton("Save");
        JButton collect = new JButton("Collect");
        JButton skip = new JButton("Skip");
        JButton  shovel = new JButton("Shovel");
        JButton exit = new JButton("Exit");


        menuBar.add(addSunflower);
        menuBar.add(addPeashooter);
        menuBar.add(save);
        menuBar.add(collect);
        menuBar.add(skip);
        menuBar.add(shovel);
        menuBar.add(exit);

        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280,1000);
        frame.setVisible(true);
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
}
