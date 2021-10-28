package spaceinvaders.sprite;

import spriteframework.sprite.BadSprite;

public class Bomb extends BadSprite {

    private boolean destroyed;

    public Bomb(int x, int y) {

        initBomb(x, y);
    }

    private void initBomb(int x, int y) {

        setDestroyed(true);
        setX(x);
        setY(y);
        String bombImg = "images/bomb.png";
        setImageFromPath(bombImg);
    }

    public void setDestroyed(boolean destroyed) {

        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
    
    
}
