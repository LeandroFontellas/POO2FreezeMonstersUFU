package spaceinvaders;


import spaceinvaders.sprite.Bomb;
import spaceinvaders.sprite.BomberSprite;
import spaceinvaders.sprite.Player;
import spaceinvaders.sprite.Shot;
import spriteframework.AbstractBoard;
import spriteframework.PlayerComms;
import spriteframework.listenersInterface.KeyPressedListenerInterface;
import spriteframework.listenersInterface.KeyReleasedListenerInterface;
import spriteframework.listenersInterface.OtherSpriteListenerInterface;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.Position;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class SpaceInvadersBoard extends AbstractBoard {
    private LinkedList<BadSprite> aliens;
    private Shot shot;
    private int direction = -1;
    private int deaths = 0;
    protected String message = "Game Over";

    public SpaceInvadersBoard() {
        super(Commons.BOARD_WIDTH,Commons.BOARD_HEIGHT,new Color(3, 190, 115),Commons.DELAY,true);
        setKeyPressedListener(new KeyPressedListenerInterface() {
            @Override
            public void onKeyPressed(KeyEvent keyEvent) {
                players.get(0).keyPressed(keyEvent);
                processOtherSprites(players.get(0), keyEvent);
            }
        });

        setKeyReleasedListener(new KeyReleasedListenerInterface() {
            @Override
            public void onKeyReleased(KeyEvent keyEvent) {
                players.get(0).keyReleased(keyEvent);
            }
        });

        setOtherSpriteListener(new OtherSpriteListenerInterface() {
            @Override
            public void createOtherSprites() {
                shot = new Shot();
            }

            @Override
            public void drawOtherSprites() {
                drawShot();
            }
        });
    }

    @Override
    protected LinkedList<BadSprite> createBadSprites() {
        aliens = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                BomberSprite alien = new BomberSprite(Commons.ALIEN_INIT_X + 18 * j,
                        Commons.ALIEN_INIT_Y + 18 * i);
                aliens.add(alien);
            }
        }
        return aliens;
    }


    private void drawShot() {
        if (shot.isVisible()) {
            drawSprite(shot);
        }
    }

    protected void processOtherSprites(PlayerComms player, KeyEvent e) {
        int x = player.getX();
        int y = player.getY();
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            if (inGame) {
                if (!shot.isVisible()) {
                    shot = new Shot(x, y);
                }
            }
        }
    }

    @Override
    protected void createPlayers() {
        players = new LinkedList<>();
        players.add(createPlayer());
    }

    @Override
    protected void drawBoard(Graphics graphics) {
        graphics.setColor(Color.black);
        graphics.drawRect(
                0,
                0,
                dimension.width,
                dimension.height
        );
        if (inGame) {
            Position initialLinePosition = new Position(
                    0,
                    Commons.GROUND
            );

            Position finalLinePosition = new Position(
                    Commons.BOARD_WIDTH,
                    Commons.GROUND
            );
            graphics.setColor(Color.green);
            graphics.drawLine(
                    initialLinePosition.getxPosition(),
                    initialLinePosition.getyPosition(),
                    finalLinePosition.getxPosition(),
                    finalLinePosition.getyPosition()
            );
        }
    }

    protected PlayerComms createPlayer() {
        return new Player();
    }

    @Override
    protected void gameFinished(Graphics graphics) {
        graphics.setColor(Color.black);
        graphics.fillRect(
                        0,
                        0,
                        Commons.BOARD_WIDTH,
                        Commons.BOARD_HEIGHT
        );

        graphics.setColor(new Color(0, 32, 48));
        graphics.fillRect(
                50,
                Commons.BOARD_WIDTH / 2 - 30,
                Commons.BOARD_WIDTH - 100,
                50
        );

        graphics.setColor(Color.white);
        graphics.drawRect(
            50,
            Commons.BOARD_WIDTH / 2 - 30,
            Commons.BOARD_WIDTH - 100,
            50
        );
    

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = this.getFontMetrics(small);
        Position position = new Position(
                (Commons.BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
                Commons.BOARD_WIDTH / 2
        );
        graphics.setColor(Color.white);
        graphics.setFont(small);
        graphics.drawString(
                message,
                position.getxPosition(),
                position.getyPosition()
        );
    }

    @Override
    protected void update() {
        if (deaths == Commons.NUMBER_OF_ALIENS_TO_DESTROY) {
            inGame = false;
            timer.stop();
            message = "Game won!";
        }

        for (PlayerComms player : players) {
            player.update();
        }

        if (shot.isVisible()) {

            int shotX = shot.getX();
            int shotY = shot.getY();

            for (BadSprite alien : badSprites) {

                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible() && shot.isVisible()) {
                    if (shotX >= (alienX)
                            && shotX <= (alienX + Commons.ALIEN_WIDTH)
                            && shotY >= (alienY)
                            && shotY <= (alienY + Commons.ALIEN_HEIGHT)) {

                        alien.setImageFromPath(Commons.EXPLOSION_IMAGE_PATH);
                        alien.setDying(true);
                        deaths++;
                        shot.die();
                    }
                }
            }

            int y = shot.getY();
            y -= 4;

            if (y < 0) {
                shot.die();
            } else {
                shot.setY(y);
            }
        }

        for (BadSprite alien : badSprites) {

            int x = alien.getX();

            if (x >= Commons.BOARD_WIDTH - Commons.BORDER_RIGHT && direction != -1) {

                direction = -1;

                Iterator<BadSprite> i1 = badSprites.iterator();

                while (i1.hasNext()) {
                    BadSprite a2 = i1.next();
                    a2.setY(a2.getY() + Commons.GO_DOWN);
                }
            }

            if (x <= Commons.BORDER_LEFT && direction != 1) {

                direction = 1;

                Iterator<BadSprite> i2 = badSprites.iterator();

                while (i2.hasNext()) {

                    BadSprite a = i2.next();
                    a.setY(a.getY() + Commons.GO_DOWN);
                }
            }
        }

        Iterator<BadSprite> it = badSprites.iterator();

        while (it.hasNext()) {

            BadSprite alien = it.next();

            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > Commons.GROUND - Commons.ALIEN_HEIGHT) {
                    inGame = false;
                    message = "Invasion!";
                }

                alien.moveX(direction);
            }
        }
        updateOtherSprites();
    }

    protected void updateOtherSprites() {
        Random generator = new Random();

        for (BadSprite alien : badSprites) {

            int shot = generator.nextInt(15);
            Bomb bomb = ((BomberSprite) alien).getBomb();

            if (shot == Commons.CHANCE && alien.isVisible() && bomb.isDestroyed()) {

                bomb.setDestroyed(false);
                bomb.setX(alien.getX());
                bomb.setY(alien.getY());
            }

            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int playerX = players.get(0).getX();
            int playerY = players.get(0).getY();

            if (players.get(0).isVisible() && !bomb.isDestroyed()) {

                if (bombX >= (playerX)
                        && bombX <= (playerX + Commons.PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + Commons.PLAYER_HEIGHT)) {


                    players.get(0).setImageFromPath(Commons.EXPLOSION_IMAGE_PATH);
                    players.get(0).setDying(true);
                    bomb.setDestroyed(true);
                }
            }

            if (!bomb.isDestroyed()) {

                bomb.setY(bomb.getY() + 1);

                if (bomb.getY() >= Commons.GROUND - Commons.BOMB_HEIGHT) {

                    bomb.setDestroyed(true);
                }
            }
        }
    }
}

