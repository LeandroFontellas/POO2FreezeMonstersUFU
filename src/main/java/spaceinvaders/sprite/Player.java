package spaceinvaders.sprite;

import spaceinvaders.Commons;
import spriteframework.PlayerComms;
import spriteframework.listenersInterface.KeyPressedListenerInterface;
import spriteframework.listenersInterface.KeyReleasedListenerInterface;
import spriteframework.sprite.Position;
import java.awt.event.KeyEvent;

import static spaceinvaders.Commons.*;

public class Player extends PlayerComms {

    public Player() {
        super(PLAYER_IMAGE_PATH, new Position(INIT_PLAYER_X, INIT_PLAYER_Y));
        getImageDimensions();
        setKeyPressedListener(new KeyPressedListenerInterface() {
            @Override
            public void onKeyPressed(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();

                if (key == KeyEvent.VK_LEFT) {
                    moveHorizontalDisplacement(2, LEFT_DIRECTION);
                }
                else if (key == KeyEvent.VK_RIGHT) {
                    moveHorizontalDisplacement(2, RIGHT_DIRECTION);
                }
            }
        });

        setKeyReleasedListener(new KeyReleasedListenerInterface() {
            @Override
            public void onKeyReleased(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();

                if (key == KeyEvent.VK_LEFT) {
                    moveHorizontalDisplacement(0, LEFT_DIRECTION);
                } else if (key == KeyEvent.VK_RIGHT) {
                    moveHorizontalDisplacement(0, RIGHT_DIRECTION);
                }
            }
        });
    }

    public void update() {
        super.update();
        if (getX() <= 2) {
            setX(2);
        }
        if (getX() >= Commons.BOARD_WIDTH - 2 * this.getImageWidth()) {
            setX(Commons.BOARD_WIDTH - 2 * this.getImageWidth());
        }
    }
}
