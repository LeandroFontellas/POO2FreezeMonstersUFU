package spaceinvaders.sprite;

import spaceinvaders.Commons;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.BadnessBoxSprite;

import java.util.LinkedList;

public class BomberSprite extends BadnessBoxSprite {

    private Bomb bomb;

    public BomberSprite(int x, int y) {
        initBomber(x, y);
    }

    private void initBomber(int x, int y) {
        this.setX(x);
        this.setY(y);
        bomb = new Bomb(x, y);
        setImageFromPath(Commons.ALIEN_IMAGE_PATH);
    }

    public Bomb getBomb() {
        return bomb;
    }

	@Override
	public LinkedList<BadSprite> getBadnesses() {
		LinkedList<BadSprite> aBomb = new LinkedList<BadSprite>();
		aBomb.add(bomb);
		return aBomb;
	}
}
