package freezemonster.sprite;

import freezemonster.Commons;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.Position;

import static freezemonster.Commons.*;


public class PlayerShot extends BadSprite {

    public PlayerShot() {
    }

    public PlayerShot(Position position) {
        initShot(position.getxPosition(), position.getyPosition());
    }

    private void initShot(int x, int y) {

        setImageFromResource(RAY_IMAGE_PATH, SHOT_SIZE, SHOT_SIZE);

         int H_SPACE = 6;
        setX(x + H_SPACE);

        int V_SPACE = 1;
        setY(y - V_SPACE);
    }

    public void actShot(String playerLastDirection) {
        if(isVisible()) {
            if (playerLastDirection.equals(LEFT)) {
                this.moveShotToLeftDirection();
            } else if (playerLastDirection.equals(RIGHT)) {
                this.moveShotToRightDirection();
            } else if (playerLastDirection.equals(DOWN)) {
                this.moveShotToDownDirection();
            } else if (playerLastDirection.equals(UP)) {
                this.moveShotToUpDirection();
            }
        }
    }

    public void moveShotToRightDirection(){
        int x = getX() + 4;
        if(x > Commons.BOARD_WIDTH){
            die();
        }else{
            setX(x);
        }
    }

    public void moveShotToLeftDirection(){
        int x = getX() - 4;
        if(x < 0){
            die();
        }else{
            setX(x);
        }
    }

    public void moveShotToDownDirection(){
        int y = getY() + 4;
        if(y > Commons.BOARD_HEIGHT){
            die();
        }else{
            setY(y);
        }
    }

    public void moveShotToUpDirection(){
        int y = getY() - 4;
        if(y < 0){
            die();
        }else{
            setY(y);
        }
    }
}
