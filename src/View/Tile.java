package View;

import javax.swing.*;

public class Tile extends JButton {

    private int row,col;
    private boolean isEmpty;

    public Tile(int row ,int col){
        this.row = row;
        this.col = col;
        this.isEmpty = true;

        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
    }

    public void setImage(String imagePath){
        this.setIcon(new ImageIcon(this.getClass().getResource(imagePath)));
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}
