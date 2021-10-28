package spaceinvaders.sprite;

import spriteframework.sprite.BadSprite;


public class Shot extends BadSprite {

    public Shot() {
    }

    public Shot(int x, int y) {
        initShot(x, y);
    }

    private void initShot(int x, int y) {

        String shotImg = "images/shot.png";
        setImageFromPath(shotImg);

        int H_SPACE = 6;
        setX(x + H_SPACE);

        int V_SPACE = 1;
        setY(y - V_SPACE);
    }
}
