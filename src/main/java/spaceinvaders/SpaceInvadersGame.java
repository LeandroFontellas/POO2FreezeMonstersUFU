package spaceinvaders;

import java.awt.*;

import spriteframework.AbstractBoard;
import spriteframework.MainFrame;

public class SpaceInvadersGame extends MainFrame {


	public SpaceInvadersGame () {
		super("Space Invaders", Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
	}
	
	protected  AbstractBoard createBoard() {
		return new SpaceInvadersBoard();
	}


	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {

			new SpaceInvadersGame();
		});
	}

}
