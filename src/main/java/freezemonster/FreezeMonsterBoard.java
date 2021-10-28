package freezemonster;

import freezemonster.sprite.MonsterShot;
import freezemonster.sprite.MonsterSprite;
import freezemonster.sprite.Player;
import freezemonster.sprite.PlayerShot;
import spriteframework.AbstractBoard;
import spriteframework.PlayerComms;
import spriteframework.listenersInterface.*;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.Position;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import static freezemonster.Commons.NUMBER_OF_MONSTERS_TO_DESTROY;
import static freezemonster.Commons.getRandomNumberInRage;

public class FreezeMonsterBoard extends AbstractBoard {
    private LinkedList<BadSprite> monsters;
    private PlayerShot shot;
    
    private int boardWidth = Commons.BOARD_WIDTH;
    private int boardHeight = Commons.BOARD_HEIGHT;
    private Color color = new Color(3, 190, 115);

    private int deaths = 0;
    private String playerLastDirection = Commons.UP;
    private Player player;
    protected String message = "Game Over";


    public FreezeMonsterBoard() {
        super(Commons.BOARD_WIDTH,Commons.BOARD_HEIGHT,new Color(3, 190, 115),Commons.DELAY,true);
        configListeners();
    }

    private void configListeners() {
        configKeyPressedListener();
        configKeyReleasedListener();
        configOtherSpritesListener();
    }

    /**Implementação do keyPressedListner desse jogo */
    private void configKeyPressedListener() {
        setKeyPressedListener(new KeyPressedListenerInterface() {
            @Override
            public void onKeyPressed(KeyEvent keyEvent) {
                player.keyPressed(keyEvent);
                processOtherSprites(player, keyEvent);
            }
        });
    }

    private void configKeyReleasedListener() {
        setKeyReleasedListener(new KeyReleasedListenerInterface() {
            @Override
            public void onKeyReleased(KeyEvent keyEvent) {
                player.keyReleased(keyEvent);
            }
        });
    }

    private void configOtherSpritesListener() {
        setOtherSpriteListener(new OtherSpriteListenerInterface() {
            @Override
            public void createOtherSprites() {
                shot = new PlayerShot();
            }

            @Override
            public void drawOtherSprites() {
                if (shot.isVisible()) {
                    drawSprite(shot);
                }
            }
        });
    }

    private void processOtherSprites(PlayerComms player, KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            if (inGame) {
                if (!shot.isVisible()) {
                    Position playerPosition = player.getPosition();
                    shot = new PlayerShot(playerPosition);
                }
            }
        }
    }

    @Override
    protected LinkedList<BadSprite> createBadSprites() {
        monsters = new LinkedList<>();
        for (int i = 0; i < Commons.MONSTERS_PATH_IMAGES.length; i++) {
            monsters.add(createMonster(i));
        }
        return monsters;
    }

    private BadSprite createMonster(int position) {
        int x = getRandomNumberInRage(Commons.BOARD_WIDTH - 200, 0);
        int y = getRandomNumberInRage(Commons.BOARD_HEIGHT - 200, 0);
        MonsterSprite monster = new MonsterSprite(
                new Position(x,y),
                Commons.MONSTERS_PATH_IMAGES[position],
                Commons.DEAD_MONSTERS_PATH_IMAGES[position]
        );
        return monster;
    }


    @Override
    protected void createPlayers() {
        player = new Player();
        players.add(player);
    }

    @Override
    protected void drawBoard(Graphics graphics) {
        graphics.setColor(color);
        graphics.drawRect(
                0,
                0,
                dimension.width,
                dimension.height
        );
    }

    @Override
    protected void gameFinished(Graphics graphics) {
        graphics.setColor(new Color(0, 0,0));
        graphics.fillRect(
                0,
                0,
                boardWidth,
                boardHeight
        );

        graphics.setColor(new Color(0, 32, 48));
        graphics.drawRect(
            50,
            boardWidth / 2 - 30,
            boardWidth - 100,
            50
        );

        graphics.setColor(new Color(255,255,255));
        graphics.drawRect(
            50,
            boardWidth / 2 - 30,
            boardWidth - 100,
            50
        );

        Font small = new Font("Roboto", Font.BOLD, 14);
        FontMetrics fontMetrics = this.getFontMetrics(small);
        Position position = new Position(
                (boardWidth - fontMetrics.stringWidth(message)) / 2,
                boardWidth / 2
        );
        graphics.setColor(Color.white);
        graphics.setFont(small);
        graphics.drawString(
                message,
                position.getxPosition(),
                position.getyPosition()
        );
    }

    /**Implementação concreta do observer feita nesse jogo que chama uma
     * cadeia de atualizações na tela.
     */
    @Override
    protected void update() {
        if (deaths == NUMBER_OF_MONSTERS_TO_DESTROY) {
            inGame = false;
            timer.stop();
            message = "Game won!";
        }

        player.update();
        updateShot();
        updateMonsters();
        updateOtherSprites();
    }

    private void updateShot() {
        if (shot.isVisible()) {
            for (BadSprite monster : badSprites) {
                MonsterSprite monsterSprite = (MonsterSprite) monster;
                boolean monsterHit = monsterSprite.monsterHit(shot.getPosition());
    
                if (monsterHit) {
                    deaths++;
                    shot.die();
                }
            }
            shot.actShot(playerLastDirection);
        } else {
            playerLastDirection = player.getPlayerLastDirection();
        }
    }

    private void updateMonsters() {
        for (BadSprite monster : badSprites) {
            MonsterSprite monsterSprite = (MonsterSprite) monster;
            monsterSprite.moveMonster();
        }
    }

    protected void updateOtherSprites() {
        for (BadSprite monster : badSprites) {
            MonsterSprite monsterSprite = (MonsterSprite) monster;
            MonsterShot monsterShot = monsterSprite.getShot();
            if (monsterSprite.shotWasHit(shot.getPosition())) {
                shot.die();
            }
            monsterSprite.createShot();
            if (player.isVisible()) {
                if (isPositionInsidePlayerPosition(monsterSprite.getPosition()) && !monsterSprite.isDying()) {
                    player.setDying(true);
                }
                else if(isPositionInsidePlayerPosition(monsterShot.getPosition()) && !monsterShot.isDestroyed()){
                    player.setDying(true);
                    monsterShot.setDestroyed(true);
                }
            }
            if (!monsterShot.isDestroyed()) {
                monsterShot.shotMovement();
            }
        }
    }

    private boolean isPositionInsidePlayerPosition(Position position) {
        int x = position.getxPosition();
        int y = position.getyPosition();
        int playerX = player.getX();
        int playerY = player.getY();
        return x >= (playerX) && x <= (playerX + Commons.PLAYER_WIDTH)
                && y >= (playerY)
                && y <= (playerY + Commons.PLAYER_HEIGHT);
    }

}
