package breakingBad;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Assets {
    //class that contains game resources

    //Images
    public static BufferedImage background;
    public static BufferedImage player;
    public static BufferedImage playerSecond;
    public static BufferedImage ball;
    public static BufferedImage livesText;
    public static BufferedImage gameOver;
    public static ArrayList<BufferedImage> bricks;

    //Sound clips
    public static SoundClip explosionSound;

    // init creates obejects so that they are available to our game
    public static void init() {
        //Initializae brick array list
        bricks = new ArrayList<BufferedImage>();
        
        //Images
        background = ImageLoader.loadImage("/Images/background.jpg");
        player = ImageLoader.loadImage("/Images/barra.JPG");
        playerSecond = ImageLoader.loadImage("/Images/explosion1.png");
        ball = ImageLoader.loadImage("/Images/ball.png");
        livesText = ImageLoader.loadImage("/Images/LivesLeft.png");
        gameOver = ImageLoader.loadImage("/Images/gameover.png");
        bricks.add(ImageLoader.loadImage("/Images/white-block.png"));
        bricks.add(ImageLoader.loadImage("/Images/blue-block.png"));
        bricks.add(ImageLoader.loadImage("/Images/red-block.png"));

        //Sounds
        explosionSound = new SoundClip("/Sounds/explosion2.wav");
        explosionSound.setLooping(false);
        explosionSound.prePlayLoad();
    }
}