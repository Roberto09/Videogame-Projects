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
    //Player
    Player player;
    
    //Dimenstions
    private int width;
    private int height;
    Rectangle area;

    //velocity and dynamics
    private int xVelocity;
    private int yVelocity;

    public Ball(int x, int y, int direction, int width, int height, Game game) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;
        this.xVelocity = 0;
        //inicialmente esta cayendo
        this.yVelocity = -2;
        this.area = new Rectangle(x, y, width, height);
        player = game.getPlayer();
    }
    
    @Override
    public void tick() {
        area.setLocation(x, y);
        setX(getX() + xVelocity);
        setY(getY() + yVelocity);
        
        //Managing screen collisions
        //right border collision
        if(getX() + getWidth() >= game.getWidth()){
            setX(game.getWidth() - getWidth());
            xVelocity *= -1;
        }
        //left border collision
        if(getX() <= 0) {
            setX(0);
            xVelocity *= -1;
        }
        //up border collision
        if(getY() <= 0){
            setY(0);
            yVelocity *= -1;
        }
        //down border collission
        if(getY() + getHeight() >= game.getHeight()){
            setY(game.getHeight() - getHeight());
            yVelocity *= -1;
        }
        
        //check if collision was made
        if(player.getArea().contains(getX() + getWidth()/2, getY() + getHeight())){
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