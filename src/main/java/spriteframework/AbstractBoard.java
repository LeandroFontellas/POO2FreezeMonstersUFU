package spriteframework;


import spriteframework.listenersInterface.KeyPressedListenerInterface;
import spriteframework.listenersInterface.KeyReleasedListenerInterface;
import spriteframework.listenersInterface.OtherSpriteListenerInterface;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.BadnessBoxSprite;
import spriteframework.sprite.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public abstract class AbstractBoard extends JPanel {


    protected Dimension dimension;
    protected LinkedList<PlayerComms> players = new LinkedList<>();
    protected LinkedList<BadSprite> badSprites;
    protected boolean inGame = true;
    protected Timer timer;

    private Graphics2D graphics2D;

    private OtherSpriteListenerInterface otherSpriteListener;
    private TAdapter tAdapter = new TAdapter();
    private boolean showDeadSprite = false;

    public AbstractBoard(int width, int height, Color color, int delay) {
        initBoard(width, height, color, delay);
    }

    public AbstractBoard(int width, int height, Color color, int delay,boolean showDeadSprite) {
        this.showDeadSprite = showDeadSprite;
        initBoard(width, height, color, delay);
    }

    protected void setKeyPressedListener(KeyPressedListenerInterface keyPressedListener) {
        tAdapter.keyPressedListener = keyPressedListener;
    }

    protected void setKeyReleasedListener(KeyReleasedListenerInterface keyReleasedListener) {
        tAdapter.keyReleasedListener = keyReleasedListener;
    }

    public void setOtherSpriteListener(OtherSpriteListenerInterface otherSpriteListener) {
        this.otherSpriteListener = otherSpriteListener;
        this.otherSpriteListener.createOtherSprites();
    }

    private void initBoard(int width, int height, Color color, int delay) {
        configBoard(width, height, color, delay);
        createSprites();
    }
    /** Sujeito adiciona o observer nele.*/
    private void configBoard(int width, int height, Color color, int delay) {
        addKeyListener(tAdapter);
        setFocusable(true);
        dimension = new Dimension(
            width,
            height
        );
        setBackground(color);
        timer = new Timer(delay, new GameCycle());
        timer.start();
    }

    private void createSprites() {
        createPlayers();
        badSprites = createBadSprites();
    }

    protected abstract LinkedList<BadSprite> createBadSprites();

    protected abstract void createPlayers();

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        doDrawing(graphics);
    }

    private void doDrawing(Graphics graphics) {
        configGraphics(graphics);
        drawBoard(graphics);
        drawGame(graphics);
        Toolkit.getDefaultToolkit().sync();
    }

    private void configGraphics(Graphics graphics) {
        this.graphics2D = (Graphics2D) graphics;
    }

    protected abstract void drawBoard(Graphics graphics);

    private void drawGame(Graphics graphics) {
        if (inGame) {
            drawGameIsRunning();
        } else {
            drawGameIsOver(graphics);
        }
    }

    private void drawGameIsRunning() {
        drawBadSprites();
        drawPlayers();
        if (otherSpriteListener != null) {
            otherSpriteListener.drawOtherSprites();
        }
    }

    private void drawBadSprites() {
        for (BadSprite bad : badSprites) {
            drawBadSprite(bad);
            drawBadnessFromBadnessBoxSprite(bad);
        }
    }

    private void drawBadSprite(BadSprite bad) {
        drawSpriteIfIsVisible(bad);
        if (!showDeadSprite) {
            setBadSpriteDeadIfIsDying(bad);
        }
    }

    private void setBadSpriteDeadIfIsDying(BadSprite bad) {
        if (bad.isDying()) {
            bad.die();
        }
    }

    private void drawBadnessFromBadnessBoxSprite(BadSprite bad) {
        if (isBadnessBoxSprite(bad)) {
            badnessBoxSpriteTreatment((BadnessBoxSprite) bad);
        }
    }

    private boolean isBadnessBoxSprite(BadSprite bad) {
        return bad instanceof BadnessBoxSprite;
    }

    private void badnessBoxSpriteTreatment(BadnessBoxSprite badnessBoxSprite) {
        if (badnessBoxSpriteBadnessIsNotNull(badnessBoxSprite)) {
            for (BadSprite badness : badnessBoxSprite.getBadnesses()) {
                drawBadnessIfNotDestroyed(badness);
            }
        }
    }

    private boolean badnessBoxSpriteBadnessIsNotNull(BadnessBoxSprite badnessBoxSprite) {
        return badnessBoxSprite.getBadnesses() != null;
    }

    private void drawBadnessIfNotDestroyed(BadSprite badness) {
        if (badnessIsNotDestroyed(badness)) {
            drawSprite(badness);
        }
    }

    private boolean badnessIsNotDestroyed(BadSprite badness) {
        return !badness.isDestroyed();
    }

    private void drawPlayers() {
        for (PlayerComms player : players) {
            drawSpriteIfIsVisible(player);
            playerIsDyingTreatment(player);
        }
    }

    private void drawSpriteIfIsVisible(Sprite sprite) {
        if (sprite.isVisible()) {
            drawSprite(sprite);
        }
    }

    private void playerIsDyingTreatment(PlayerComms player) {
        if (player.isDying()) {
            player.die();
            inGame = false;
        }
    }

    protected void drawSprite(Sprite sprite) {
        graphics2D.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), this);
    }

    private void drawGameIsOver(Graphics graphics) {
        if (timer.isRunning()) {
            timer.stop();
        }
        gameFinished(graphics);
    }

    protected abstract void gameFinished(Graphics graphics);

    /** Implementação do observer, toda vez que houver uma ação ele refresh o jogo */
    private class GameCycle implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            refresh();
        }
    }

    /** Método concreto que da update no tabuleiro */
    private void refresh() {
        update();
        repaint();
    }

    /** Método update tem que ser implementada de acordo com cada tabuleiro de jogo, mas
     * é ela que vai ser disparada quando tiver uma ação
     */
    protected abstract void update();

    /*Define um tipo de observer composto por dois tipos de eventos a ser escutado*/
    private class TAdapter extends KeyAdapter {

        protected KeyPressedListenerInterface keyPressedListener;
        protected KeyReleasedListenerInterface keyReleasedListener;
        
        /** Sobrescreve a funcao keyReleased, que chama o método implementado onKeyReleased
         * de cada tipo de jogo
         */
        @Override
        public void keyReleased(KeyEvent e) {
            if (keyReleasedListener != null) {
                keyReleasedListener.onKeyReleased(e);
            }
        }

        /** Sobrescreve a funcao keyPressed, que chama o método implementado onKeyPressed
         * de cada tipo de jogo
         */
        @Override
        public void keyPressed(KeyEvent e) {
            if (keyPressedListener != null) {
                keyPressedListener.onKeyPressed(e);
            }
        }
    }

}

