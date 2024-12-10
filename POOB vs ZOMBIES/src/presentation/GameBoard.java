package presentation;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JPanel {
    private static final int ROWS = 5;
    private static final int COLUMNS = 10;
    private static final int CELL_SIZE = 60; // Tamaño de cada celda

    private JLabel[][] cells;

    public GameBoard() {
        setLayout(new GridLayout(ROWS, COLUMNS));
        setPreferredSize(new Dimension(COLUMNS * CELL_SIZE, ROWS * CELL_SIZE));
        cells = new JLabel[ROWS][COLUMNS];

        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                cells[row][col] = new JLabel();
                cells[row][col].setOpaque(true);
                cells[row][col].setBackground(Color.GREEN);
                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                if (col == 0) {
                    // Primera columna para podadores
                    cells[row][col].setBackground(Color.GRAY);
                } else if (col == COLUMNS - 1) {
                    // Última columna para zombies
                    cells[row][col].setBackground(Color.RED);
                }

                add(cells[row][col]);
            }
        }
    }

    public boolean placePlant(int row, int col) {
        if (col >= 1 && col <= 8 && cells[row][col].getBackground() == Color.GREEN) {
            cells[row][col].setBackground(Color.BLUE);
            return true;
        }
        return false;
    }

    public boolean placeZombie(int row) {
        int col = COLUMNS - 1;
        if (cells[row][col].getBackground() == Color.RED) {
            cells[row][col].setBackground(Color.BLACK);
            return true;
        }
        return false;
    }
}
