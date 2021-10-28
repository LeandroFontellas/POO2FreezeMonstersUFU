package freezemonster.sprite;

import freezemonster.Commons;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.BadnessBoxSprite;
import spriteframework.sprite.Position;

import java.util.LinkedList;

import static freezemonster.Commons.getRandomNumberInRage;

public class MonsterSprite extends BadnessBoxSprite {
    private final int UP = 3;
    private final int DOWN = 4;
    private final int LEFT = 1;
    private final int RIGHT = 2;
    private int direction = LEFT ;
    private int numberOfSteps = 0;
    private final int LIMIT_MOVEMENT = 80;
    private String imagePathDead;
    private MonsterShot shot;

    public MonsterSprite(Position position, String imagePath, String imagePathDead) {
        initMonster(position.getxPosition(), position.getxPosition());
        setImageFromResource(imagePath, 40,40);
        this.imagePathDead = imagePathDead;
    }

    private void initMonster(int x, int y) {
        this.setX(x);
        this.setY(y);
        shot = new MonsterShot(x,y);
    }

    @Override
    public LinkedList<BadSprite> getBadnesses() {
        LinkedList<BadSprite> aMonsterShot = new LinkedList<BadSprite>();
        aMonsterShot.add(shot);
        return aMonsterShot;
    }

    public void createShot(){
        if (shotCanBeCreated()) {
            shot.setDestroyed(false);
            shot.setX(getX());
            shot.setY(getY());
        }
    }

    private boolean shotCanBeCreated(){
        return !isDying() && shot.isDestroyed();
    }

    public boolean monsterHit(Position position){
        boolean hit = isHit(position);
        if(!isDying() && hit){
            monsterFrozen();
        }else{
            hit = false;
        }
        return hit;
    }

    private boolean isHit(Position position){
        return position.getxPosition() >= (getX())
                && position.getxPosition() <= (getX() + getImageWidth())
                && position.getyPosition() >= (getY())
                && position.getyPosition() <= (getY() + getImageHeight());
    }

    private void monsterFrozen(){
        setImageFromResource(imagePathDead,40,40);
        if(shot.isDestroyed()){
            shot.die();
        }
        setDying(true);
    }

    public void moveMonster(){
        if(!isDying()){
            numberOfSteps++;
            if(numberOfSteps == 20 ){
                numberOfSteps = 0;
            }
            if(numberOfSteps == 0){
                direction = getRandomNumberInRage(4,1);
            }
            boolean monsterMoved = false;
            while(true){
                if(direction == LEFT){
                    monsterMoved = moveMonsterToLeftDirection();
                }else if(direction == RIGHT){
                    monsterMoved = moveMonsterToRightDirection();
                }
                else if(direction == UP){
                    monsterMoved = moveMonsterToUpDirection();
                }
                else if(direction == DOWN){
                    monsterMoved = moveMonsterToDownDirection();
                }

                if(monsterNeedMove(monsterMoved)){
                    direction = getRandomNumberInRage(4,1);
                }else{
                    break;
                }
            }
        }
    }

    private boolean monsterNeedMove(boolean moved){
        return !moved;
    }

    private boolean moveMonsterToUpDirection(){
        int y = getY() - 2;
        if(y < 0){
            return false;
        }
        setY(y);
        return true;
    }

    private boolean moveMonsterToDownDirection(){
        int y = getY() + 2;
        if(y > Commons.BOARD_HEIGHT - LIMIT_MOVEMENT){
            return false;
        }
        setY(y);
        return true;
    }

    private boolean moveMonsterToLeftDirection(){
        int x = getX() - 2;
        if(x < 0){
            return false;
        }
        setX(x);
        return true;
    }

    private boolean moveMonsterToRightDirection(){
        int x = getX() + 2;
        if(x > Commons.BOARD_WIDTH - LIMIT_MOVEMENT){
            return false;
        }
        setX(x);
        return true;
    }

    public MonsterShot getShot() {
        return shot;
    }

    public boolean shotWasHit(Position position){
        return shot.shotWasHit(position);
    }
}
