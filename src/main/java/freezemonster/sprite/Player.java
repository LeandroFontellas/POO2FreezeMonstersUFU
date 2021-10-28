package freezemonster.sprite;

import freezemonster.Commons;
import spriteframework.PlayerComms;
import spriteframework.listenersInterface.KeyPressedListenerInterface;
import spriteframework.listenersInterface.KeyReleasedListenerInterface;
import spriteframework.sprite.Position;

import java.awt.event.KeyEvent;

import static freezemonster.Commons.LEFT;

public class Player extends PlayerComms {

    private String playerLastDirection = Commons.UP;

    public Player() {
        super(Commons.PLAYER_IMAGE_PATH, Commons.PLAYER_WIDTH, Commons.PLAYER_HEIGHT,
                new Position(Commons.INIT_PLAYER_X, Commons.INIT_PLAYER_Y));
        getImageDimensions();
        setKeyPressedListener(new KeyPressedListenerInterface() {
            @Override
            public void onKeyPressed(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                if (key == KeyEvent.VK_LEFT) {
                    moveHorizontalDisplacement(2, LEFT_DIRECTION);
                        playerLastDirection = LEFT;
                } else if (key == KeyEvent.VK_RIGHT) {
                    moveHorizontalDisplacement(2, RIGHT_DIRECTION);
                        playerLastDirection = Commons.RIGHT;
                } else if (key == KeyEvent.VK_UP) {
                    moveVerticalDisplacement(2, UP_DIRECTION);
                        playerLastDirection = Commons.UP;
                } else if (key == KeyEvent.VK_DOWN) {
                    moveVerticalDisplacement(2, DOWN_DIRECTION);
                        playerLastDirection = Commons.DOWN;
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
                } else if (key == KeyEvent.VK_UP) {
                    moveVerticalDisplacement(0, UP_DIRECTION);
                } else if (key == KeyEvent.VK_DOWN) {
                    moveVerticalDisplacement(0, DOWN_DIRECTION);
                }
            }
        });
    }

    public void update() {
        super.update();
        if (getX() <= 2) {
            setX(2);
        }

        if(getY() <= 2){
            setY(2);
        }

        int rightLimit = 45;
        int bottomLimit = 90;
        if (getX() >= (Commons.BOARD_WIDTH - rightLimit)) {
            setX(Commons.BOARD_WIDTH - rightLimit);
        }
        if(getY() >= (Commons.BOARD_HEIGHT - bottomLimit)){
            setY(Commons.BOARD_HEIGHT - bottomLimit);
        }
    }

    public String getPlayerLastDirection() {
        return playerLastDirection;
    }
}
