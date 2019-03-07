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
    public static BufferedImage sprites;
    public static BufferedImage ball[];
    public static BufferedImage livesText;
    public static BufferedImage gameOver;
    public static BufferedImage pauseImage;
    public static BufferedImage saveImage;
    public static BufferedImage loadImage;
    public static ArrayList<BufferedImage> bricks;

    //Sound clips
    public static SoundClip explosionSound;

    // init creates obejects so that they are available to our game
    public static void init() {
        //Initialize brick array list
        bricks = new ArrayList<BufferedImage>();
        //Initialize ball array
        ball = new BufferedImage[8];
        
        //Images
        background = ImageLoader.loadImage("/Images/background.jpg");
        player = ImageLoader.loadImage("/Images/barra.JPG");
        playerSecond = ImageLoader.loadImage("/Images/explosion1.png");
        livesText = ImageLoader.loadImage("/Images/LivesLeft.png");
        gameOver = ImageLoader.loadImage("/Images/gameover.JPG");
        bricks.add(ImageLoader.loadImage("/Images/white-block.png"));
        bricks.add(ImageLoader.loadImage("/Images/blue-block.png"));
        bricks.add(ImageLoader.loadImage("/Images/red-block.png"));
        
        sprites = ImageLoader.loadImage("/Images/ball.png");
        // creating array of images before animations
        SpriteSheet spritesheet = new SpriteSheet(sprites);
        //Ball positions
        for(int i=0; i<8; i++){
            ball[i] = spritesheet.crop(i*300, 0, 300, 300);
        }
        pauseImage = ImageLoader.loadImage("/Images/Pausa.JPG");
        saveImage = ImageLoader.loadImage("/Images/Guardar.JPG");
        loadImage = ImageLoader.loadImage("/Images/Cargar.JPG");

        //Sounds
        explosionSound = new SoundClip("/Sounds/explosion2.wav");
        explosionSound.setLooping(false);
        explosionSound.prePlayLoad();
    }
}