package spriteframework;

import spriteframework.listenersInterface.KeyPressedListenerInterface;
import spriteframework.listenersInterface.KeyReleasedListenerInterface;
import spriteframework.sprite.Position;
import spriteframework.sprite.Sprite;

import java.awt.event.KeyEvent;

public abstract class PlayerComms extends Sprite {

    private KeyPressedListenerInterface keyPressedListener;
    private KeyReleasedListenerInterface keyReleasedListener;
    private int horizontalDisplacement = 0;
    private int verticalDisplacement = 0;

    public PlayerComms(String playerImagePath, Position playerInitialPosition) {
        setImageFromResource(playerImagePath);
        setPosition(playerInitialPosition);
    }

    public PlayerComms(String playerImagePath, int width, int height, Position playerInitialPosition) {
        setImageFromResource(playerImagePath, width, height);
        setPosition(playerInitialPosition);
    }

    public void setKeyPressedListener(KeyPressedListenerInterface keyPressedListener) {
        this.keyPressedListener = keyPressedListener;
    }

    public void setKeyReleasedListener(KeyReleasedListenerInterface keyReleasedListener) {
        this.keyReleasedListener = keyReleasedListener;
    }

    public void keyPressed(KeyEvent eventKey) {
        if (keyPressedListener != null)
            keyPressedListener.onKeyPressed(eventKey);
    }

    public void keyReleased(KeyEvent eventKey) {
        if (keyReleasedListener != null)
            keyReleasedListener.onKeyReleased(eventKey);
    }

    public void update(){
        moveX(horizontalDisplacement);
        moveY(verticalDisplacement);
    }

    public int moveHorizontalDisplacement(int quantityToMove, int direction ){
        return horizontalDisplacement = quantityToMove*direction;
    }

    public int moveVerticalDisplacement(int quantityToMove, int direction){
        return verticalDisplacement = quantityToMove*direction;
    }
}
