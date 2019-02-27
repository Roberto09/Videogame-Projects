package breakingBad;

import java.awt.*;

public class ComputerPlayer extends Item {

    private int width;
    private int height;
    private Game game;
    private Player player;

    //velocity and dynamics
    private double xVelocity;
    private double yVelocity;
    private double xUnitary;
    private double yUnitary;
    private int xDisplacement;
    private int yDisplacement;

    public ComputerPlayer(int x, int y, int direction, int width, int height, Game game) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;
        xVelocity = 0;
        yVelocity = 0;
        player = game.getPlayer();
    }

    @Override
    public void tick() {
        //calculating distances to object
        xDisplacement = player.getX() - x;
        yDisplacement = player.getY() -  y;

        //calculating position using unitary vector
        if(xDisplacement != 0 || yDisplacement != 0){
            xUnitary = xDisplacement / Math.sqrt(xDisplacement * xDisplacement + yDisplacement * yDisplacement);
            yUnitary = yDisplacement / Math.sqrt(xDisplacement * xDisplacement + yDisplacement * yDisplacement);
            //adding the unitary vector to our velocities
            xVelocity += xUnitary;
            yVelocity += yUnitary;
        }
        else {
            xVelocity = 0;
            yVelocity = 0;
        }

        //adding the current velocity on both axis
        setX(getX() +(int) Math.round(xVelocity) * (3 - game.getLives().getLifeCount() + 1));
        setY(getY() + (int) Math.round(yVelocity) * (3 - game.getLives().getLifeCount() + 1));

        //regulating velocity
        xVelocity %= .5;
        yVelocity %= .5;

        //check if collision was made
        if(player.getArea().contains(x + width / 2, y + height / 2)){
            Assets.explosionSound.play();
            //Assets.explosionSound.stop();
            game.getLives().looseLife();
            resetPosition();
            player.resetPosition();
        }
    }

    @Override
    public void render(Graphics g) {
        // drawing computer player on screen
        g.drawImage(Assets.computerPlayer, getX(), getY(), getWidth(), getHeight(), null);
    }

    public void resetPosition(){
        setX(RandomGenerator.generate(game.getWidth()/2 + 100, game.getWidth() - getWidth()));
        setY(RandomGenerator.generate(1, game.getHeight() - getHeight()));
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