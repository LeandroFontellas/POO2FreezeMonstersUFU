package freezemonster;

import java.util.Random;

public interface Commons {

    String PLAYER_IMAGE_PATH = "/freezemonsterimages/woody.png";
    String RAY_IMAGE_PATH = "/freezemonsterimages/ray.png";
    String MONSTER_SHOT_IMAGE_PATH = "/freezemonsterimages/gosma.png";
    String[] MONSTERS_PATH_IMAGES = {
            "/freezemonsterimages/monster1.png", "/freezemonsterimages/monster2.png",
            "/freezemonsterimages/monster3.png", "/freezemonsterimages/monster4.png",
            "/freezemonsterimages/monster5.png", "/freezemonsterimages/monster6.png",
            "/freezemonsterimages/monster7.png", "/freezemonsterimages/monster8.png",
            "/freezemonsterimages/monster9.png"};
    String[] DEAD_MONSTERS_PATH_IMAGES = {
            "/freezemonsterimages/monster1bg.png", "/freezemonsterimages/monster2bg.png",
            "/freezemonsterimages/monster3bg.png", "/freezemonsterimages/monster4bg.png",
            "/freezemonsterimages/monster5bg.png", "/freezemonsterimages/monster6bg.png",
            "/freezemonsterimages/monster7bg.png", "/freezemonsterimages/monster8bg.png",
            "/freezemonsterimages/monster9bg.png"};


    String LEFT = "left";
    String RIGHT = "right";
    String UP = "up";
    String DOWN = "down";

    int SHOT_SIZE = 25;
    int PLAYER_WIDTH = 30;
    int PLAYER_HEIGHT = 50;
    int INIT_PLAYER_X = 500;
    int INIT_PLAYER_Y = 500;
    int NUMBER_OF_MONSTERS_TO_DESTROY = MONSTERS_PATH_IMAGES.length;
    int BORDER_RIGHT = 30;
    int GROUND = 290;
    int BOARD_WIDTH = 600;
    int BOARD_HEIGHT = 600;
    int DELAY = 17;

    static int getRandomNumberInRage(int max, int min){
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

}
