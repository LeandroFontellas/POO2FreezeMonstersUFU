package freezemonster.sprite;

import freezemonster.Commons;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.Position;

import static freezemonster.Commons.getRandomNumberInRage;

public class MonsterShot extends BadSprite {

    private final int LEFT = 1;
    private final int UP = 3;
    private final int RIGHT = 2;
    private final int movementSpeed = 3;
    private final int DOWN = 4;
    private boolean destroyed = false;
    private int direction = getRandomNumberInRage(4,1);

    public MonsterShot(int x, int y) {
        initShot(x, y);
    }

    private void initShot(int x, int y) {
        setImageFromResource(Commons.MONSTER_SHOT_IMAGE_PATH, Commons.SHOT_SIZE, Commons.SHOT_SIZE);

        int H_SPACE = 6;
        setX(x + H_SPACE);

        int V_SPACE = 1;
        setY(y - V_SPACE);
    }

    public boolean shotWasHit(Position position) {
        boolean hit = isHit(position);
        if (!isDestroyed() && hit) {
            setDestroyed(true);
        } else {
            hit = false;
        }
        return hit;
    }

    private boolean isHit(Position position) {
        return position.getxPosition() >= (getX())
                && position.getxPosition() <= (getX() + getImageWidth())
                && position.getyPosition() >= (getY())
                && position.getyPosition() <= (getY() + getImageHeight());
    }

    public void shotMovement() {
        int x;
        int y;
        if (direction == LEFT) {
            x = getX() - movementSpeed;
            if (x > 2) {
                setX(x);
            } else {
                setX(2);
                setDestroyed(true);
                this.direction = findNewDirection(direction);
            }

        } else if (direction == RIGHT) {
            x = getX() + movementSpeed;
            if (x <= Commons.BOARD_WIDTH - Commons.SHOT_SIZE) {
                setX(x);
            } else {
                setX(Commons.BOARD_WIDTH - Commons.SHOT_SIZE);
                setDestroyed(true);
                this.direction = findNewDirection(direction);
            }

        } else if (direction == UP) {
            y = getY() - movementSpeed;
            if (y > 2) {
                setY(y);
            } else {
                setY(2);
                setDestroyed(true);
                this.direction = findNewDirection(direction);
            }

        } else if (direction == DOWN) {
            y = getY() + movementSpeed;
            if (y <= Commons.BOARD_HEIGHT - Commons.SHOT_SIZE) {
                setY(y);
            } else {
                setY(Commons.BOARD_HEIGHT - Commons.SHOT_SIZE);
                setDestroyed(true);
                this.direction = findNewDirection(direction);
            }
        }
    }

    private int findNewDirection(int direction){
        int newDirection = getRandomNumberInRage(4, 1);

        while(newDirection == direction){
            newDirection = getRandomNumberInRage(4, 1);
        }
        return newDirection;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }


}
