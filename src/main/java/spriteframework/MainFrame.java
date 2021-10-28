package spriteframework;

import javax.swing.*;


public abstract class MainFrame extends JFrame {

    protected abstract AbstractBoard createBoard();

    public MainFrame(String title, int BOARD_WIDTH, int BOARD_HEIGHT) {
        add(createBoard());
        setTitle(title);
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
