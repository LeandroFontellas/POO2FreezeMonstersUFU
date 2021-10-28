package spriteframework.sprite;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Sprite {
    protected int LEFT_DIRECTION = -1;
    protected int RIGHT_DIRECTION = 1;
    protected int UP_DIRECTION = -1;
    protected int DOWN_DIRECTION = 1;

    private boolean visible;
    private Image image;
    private boolean dying;

    private int imageWidth = 0;
    private int imageHeight = 0;
    private Position position = new Position();

    public Sprite() {
        visible = true;
    }

    public void die() {
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    protected void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setImageFromPath(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        this.image = imageIcon.getImage();
        setImageDimensions(image);
    }

    public void setImageFromPath(String imagePath, int width, int height){
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image scaledImage = getScaledImage(imageIcon, width,height);
        this.image = scaledImage;
        setImageDimensions(width, height);
    }

    public void setImageFromResource(String imageResource){
        URL imageUrl = this.getClass().getResource(imageResource);
        ImageIcon imageIcon = new ImageIcon(imageUrl);
        this.image = imageIcon.getImage();
        setImageDimensions(image);
    }

    public void setImageFromResource(String imageResource, int width, int height){
        URL imageUrl = this.getClass().getResource(imageResource);
        ImageIcon imageIcon = new ImageIcon(imageUrl);
        Image scaledImage = getScaledImage(imageIcon, width,height);
        this.image = scaledImage;
        setImageDimensions(width, height);
    }

    public Image getScaledImage(ImageIcon imageIcon, int width, int height){
        return imageIcon.getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH);
    }

    public void setImage(Image image){
        this.image = image;
        setImageDimensions(image);
    }

    private void setImageDimensions(Image image){
        this.imageWidth = image.getWidth(null);
        this.imageHeight = image.getHeight(null);
    }

    private void setImageDimensions(int imageWidth, int imageHeight){
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    public Image getImage() {
        return image;
    }

    public void setX(int x) {
        position.setxPosition(x);
    }

    public void setY(int y) {
        position.setyPosition(y);
    }

    public int getY() {
        return position.getyPosition();
    }

    public int getX() {
        return position.getxPosition();
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public Position getPosition(){
        return this.position;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public Rectangle getRect() {
        return new Rectangle(getX(), getY(),
                image.getWidth(null), image.getHeight(null));
    }

    public void getImageDimensions() {
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    public boolean isDying() {
        return this.dying;
    }

    public void moveX(int direction) {
        int newPosition = getX()+direction;
        setX(newPosition);
    }

    public void moveY(int direction) {
        int newPosition = getY()+direction;
        setY(newPosition);
    }
}
