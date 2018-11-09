package View;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

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

    void setImage(Icon imagePath) {
        this.setIcon(imagePath);
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
