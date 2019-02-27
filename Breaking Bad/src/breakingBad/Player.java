package breakingBad;

import java.awt.*;

public class Player extends Item{

    //Game
    private Game game;

    //Dimenstions
    private int width;
    private int height;
    Rectangle area;

    //velocity and dynamics
    private int xVelocity;
    private int yVelocity;
    private Timer changeImageTimer;
    private boolean changeImageInCollition;
    private boolean canDrag;

    public Player(int x, int y, int direction, int width, int height, Game game) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;
        this.xVelocity = 0;
        this.yVelocity = 0;
        this.changeImageTimer = new Timer(game.getFps());
        this.changeImageInCollition = false;
        this.area = new Rectangle(x, y, width, height);
        this.canDrag = false;
    }

    @Override
    public void tick() {
        //adding the current velocity on both axis
        setY(getY() + yVelocity);
        setX(getX() + xVelocity);
        area.setLocation(x, y);

        //check if we need to change the image back
        if (changeImageTimer != null && changeImageTimer.isActive() && changeImageTimer.doneWaiting()) {
            //change image back
            changeImageInCollition = false;
        }

        if(!canDrag && game.getMouseManager().isIzquierdo() && area.contains(game.getMouseManager().getX(), game.getMouseManager().getY())) canDrag = true;
        else if(!game.getMouseManager().isIzquierdo()) canDrag = false;

        //checking for presses in mouse
        if (canDrag) {
            setX(game.getMouseManager().getX() - width / 2);
            setY(game.getMouseManager().getY() - height / 2);
        }

        //Managing screen collisions
        //right border collision
        if (getX() + getWidth() >= game.getWidth()) {
            setX(game.getWidth() - getWidth());
            changeImageTimer.setUp(.5);
            //change picture
            changeImageInCollition = true;

        }
        //left border collision
        if (getX() <= 0) {
            setX(0);
            changeImageTimer.setUp(.5);
            //change picture
            changeImageInCollition = true;
        }
        //up border collision
        if (getY() <= 0) {
            setY(0);
            changeImageTimer.setUp(.5);
            //change picture
            changeImageInCollition = true;
        }
        //down border collission
        if (getY() + getHeight() >= game.getHeight()) {
            setY(game.getHeight() - getHeight());
            changeImageTimer.setUp(.5);
            //change picture
            changeImageInCollition = true;
        }
    }

    @Override
    public void render(Graphics g) {
        //check if the flag is active to change the image in a collition
        if(changeImageInCollition)
            g.drawImage(Assets.playerSecond, getX(), getY(), getWidth(), getHeight(), null);
        else
            g.drawImage(Assets.player, getX(), getY(), getWidth(), getHeight(), null);
    }


    public void resetPosition() {
        //generating a new random position
        setX(RandomGenerator.generate(1, game.getWidth() / 2 - 100));
        setY(RandomGenerator.generate(1, game.getHeight() - getHeight()));
        canDrag = false;
    }

    public Rectangle getArea(){
        return area;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}