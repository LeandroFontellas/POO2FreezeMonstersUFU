package freezemonster;

import spriteframework.AbstractBoard;
import spriteframework.MainFrame;

import java.awt.*;

public class FreezeMonsterGame extends MainFrame {
    public FreezeMonsterGame () {
        super("Freeze Monster", Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
    }

    protected  AbstractBoard createBoard() {
        return new FreezeMonsterBoard();
    }


    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            new FreezeMonsterGame();
        });
    }
}
