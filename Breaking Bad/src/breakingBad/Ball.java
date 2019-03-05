/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breakingBad;

import java.awt.Graphics;
import java.awt.Rectangle;


public class Ball extends Item{

    //Game
    private Game game;
    
    //Dimenstions
    private int width;
    private int height;
    Rectangle area;

    //velocity and dynamics
    private double xVelocity;
    private double yVelocity;
    private double xDisplacement;
    private double yDisplacement;

    public Ball(int x, int y, int direction, int width, int height, Game game) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;
        this.xVelocity = 0;
        this.yVelocity = -8;
        this.xDisplacement = 0;
        this.yDisplacement = 0;
        this.area = new Rectangle(x, y, width, height);
    }
    
    @Override
    public void tick() {
        area.setLocation(x, y);
        //setting xvelocity
        xDisplacement += xVelocity;
        setX((int) (getX() + Math.round(xDisplacement)));
        xDisplacement %= .5;
        
        //setting yvelocity
        yDisplacement += yVelocity;
        setY((int) (getY() + Math.round(yDisplacement)));
        yDisplacement %= .5;
        
        //Managing screen collisions
        //right border collision
        if(getX() + getWidth() >= game.getWidth()){
            setX(game.getWidth() - getWidth());
            xDisplacement *= -1;
            xVelocity *= -1;
        }
        //left border collision
        if(getX() <= 0) {
            setX(0);
            xDisplacement *= -1;
            xVelocity *= -1;
        }
        //up border collision
        if(getY() <= 0){
            setY(0);
            yDisplacement *= -1;
            yVelocity *= -1;
        }
        //down border collission
        if(getY() + getHeight() >= game.getHeight()){
            setY(game.getHeight() - getHeight());
            yDisplacement *= -1;
            yVelocity *= -1;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ball, getX(), getY(), getWidth(), getHeight(), null);
    }

    public void resetPosition() {
        //generating a new random position
        setX(RandomGenerator.generate(1, game.getWidth() / 2 - 100));
        setY(RandomGenerator.generate(1, game.getHeight() - getHeight()));
    }
    
    public void changeVelocity(double newX, double newY){
        xVelocity = newX;
        yVelocity = newY;
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