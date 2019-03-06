/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breakingBad;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Ulises
 */
public class Brick extends Item {

    //Game
    private Game game;

    //Ball ball
    private Ball ball;
    
    // Diemensions
    private int width;
    private int height;
    Rectangle left,right,up,down;

    // Dynamics
    private int potencia;
    
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Rectangle getLeft() {
        return left;
    }

    public Rectangle getRight() {
        return right;
    }

    public Rectangle getUp() {
        return up;
    }

    public Rectangle getDown() {
        return down;
    }
    
    public int getPotencia(){
        return potencia;
    }

    
    public Brick(int x, int y, int width, int height, int potencia,Game game) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.potencia = potencia;
        this.game = game;
        this.ball = game.getBall();
        this.left = new Rectangle(x, y, 10, height);
        this.right = new Rectangle(x+width-10, y, 10, height);
        this.up = new Rectangle(x+10, y, width-10, 10);
        this.down = new Rectangle(x+10, y+height-10, width-10, 10);
    }

    
    public void tick() {
        if(getLeft().intersects(ball.getArea()) || getRight().intersects(ball.getArea())){
            //Check if the collision is in the left by comparing x and y axis
            // Collision left or right
            
            //flip xVelocity sign of the ball
            double xVelocity = -1 * ball.getxVelocity();
            double xDisplacement = -1 * ball.getxDisplacement();
            ball.setxDisplacement(xDisplacement);
            ball.changeVelocity(xVelocity, ball.getyVelocity());
            if(getRight().intersects(ball.getArea()))
                ball.setX(ball.getX()+20);
            else
                ball.setX(ball.getX()-20);
            //System.out.println(ball.getY());
            //game.setRunning(false);
            
            
            potencia -= 1;
        }
        else if(getDown().intersects(ball.getArea()) || getUp().intersects(ball.getArea())){
            // Collision up or down
            //flip yVelocity sign of the ball
            double yVelocity = -1 * ball.getyVelocity();
            double yDisplacement = -1 * ball.getyDisplacement();
            ball.changeVelocity(ball.getxVelocity(),yVelocity);
            ball.setyDisplacement(yDisplacement);
            if(getDown().intersects(ball.getArea()))
                ball.setY(ball.getY()+20);
            else
                ball.setY(ball.getY()-20);
            //game.setRunning(false);
            potencia -= 1;
        }
        
        if(potencia <0)
            game.eraseBrick(this);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.bricks.get(potencia), getX(), getY(), getWidth(), getHeight(), null);
    }

}
