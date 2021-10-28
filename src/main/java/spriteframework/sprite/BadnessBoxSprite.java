package spriteframework.sprite;


import java.util.LinkedList;

public class BadnessBoxSprite extends BadSprite {
    LinkedList<BadSprite> badnesses = new LinkedList<>();

    public LinkedList<BadSprite> getBadnesses() {
        return badnesses;
    }

}
