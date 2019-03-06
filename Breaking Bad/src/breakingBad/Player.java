package breakingBad;

import java.awt.*;

public class Player extends Item{

    //Game
    private Game game;
    
   //Ball ball
    private Ball ball;

    //Dimensions
    private int width;
    private int height;
    Rectangle area;

    //velocity and dynamics
    private int xVelocity;
    private int yVelocity;
    private Timer changeImageTimer;
    private boolean changeImageInCollition;

    public Player(int x, int y, int direction, int width, int height, Game game) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;
        this.xVelocity = 3;
        this.yVelocity = 3;
        this.changeImageTimer = new Timer(game.getFps());
        this.changeImageInCollition = false;
        this.area = new Rectangle(x, y, width, height);
        this.ball = game.getBall();
    }

    @Override
    public void tick() {
        area.setLocation(x, y);
        //check if we need to change the image back
        if (changeImageTimer != null && changeImageTimer.isActive() && changeImageTimer.doneWaiting()) {
            //change image back
            changeImageInCollition = false;
        }

       //checking for presses in keys
        //left press
        if(game.getKeyManager().left) {
            //substract in x axis
            setX(getX() - xVelocity);
        }
        //right press
        if(game.getKeyManager().right) {
            //add in x axis
            setX(getX() + xVelocity);
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
        
        
        //checking collision with ball
        //check if collision was made
        if(this.getArea().contains(ball.getX() + ball.getWidth()/2, ball.getY() + ball.getHeight())){
            int position = 140 - (ball.getX()+ball.getWidth()/2 - getX());
            double xDirection = 8 * Math.cos(Math.toRadians((double) position * (180.0/140.0)));
            double yDirection = -8 * Math.sin(Math.toRadians((double) position * (180.0/140.0)));
            ball.changeVelocity(xDirection, yDirection);
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