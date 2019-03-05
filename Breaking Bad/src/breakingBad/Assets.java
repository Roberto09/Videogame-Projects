package breakingBad;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Assets {
    //class that contains game resources

    //Images
    public static BufferedImage background;
    public static BufferedImage player;
    public static BufferedImage playerSecond;
    public static BufferedImage ball;
    public static BufferedImage livesText;
    public static BufferedImage gameOver;

    //Sound clips
    public static SoundClip explosionSound;

    // init creates obejects so that they are available to our game
    public static void init() {
        //Images
        background = ImageLoader.loadImage("/Images/background.jpg");
        player = ImageLoader.loadImage("/Images/barra.JPG");
        playerSecond = ImageLoader.loadImage("/Images/explosion1.png");
        ball = ImageLoader.loadImage("/Images/ball.png");
        livesText = ImageLoader.loadImage("/Images/LivesLeft.png");
        gameOver = ImageLoader.loadImage("/Images/gameover.png");

        //Sounds
        explosionSound = new SoundClip("/Sounds/explosion2.wav");
        explosionSound.setLooping(false);
        explosionSound.prePlayLoad();
    }
}