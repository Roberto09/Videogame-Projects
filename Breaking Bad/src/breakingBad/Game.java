package breakingBad;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable{
    private BufferStrategy bs; //to have several buffers when displaying
    private Graphics g; // to paint objects
    private Display display; //display of the game
    String title; //title of window
    private int width; //widht
    private int height; // height
    private Thread thread; //thread to create the game
    private boolean running; // to set the game

    //general movement dynamics (fps)
    private int fps = 50; // frames per second
    private double timeTick = 1000000000 / fps; //time for each tick in nano seconds
    private double delta = 0; // initializing delta
    private long  now; //define now to use inside loop
    private long lastTime = System.nanoTime(); // initializing last time to the computer time in nanosecs

    //player
    private Player player;
    
    //ball
    private Ball ball;

    //Life system
    private Lives lives;

    //key and mouse manager
    KeyManager keyManager;
    MouseManager mouseManager;

    //method that cotains cycle that executes the instructions to run our game
    @Override
    public void run() {
        init();
        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timeTick;
            lastTime = now;
            if (delta >= 1) {
                if(!lives.livesOver()){
                    tick();
                    render();
                }
                else
                    render();
                delta--;
            }
        }
        stop();
    }

    private void render() {
        //get buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();
        //if it is null we define one with 3 buffers to display images of the game,
        //otherwise we we display every image of the game after clearing the rectangle
        if(bs == null)
            display.getCanvas().createBufferStrategy(3);
        else{
            g = bs.getDrawGraphics();
            g.drawImage(Assets.background, 0, 0, width, height, null);
            //renderizamos el player
            player.render(g);
            ball.render(g);
            //renderizamos las lives
            lives.render(g);
            //renderizamos el game over cuando las vidas se acaban
            if(lives.livesOver())
                g.drawImage(Assets.gameOver, 310, 200, getWidth()-620, getHeight()-400, null);
            bs.show();
            g.dispose();
        }
    }

    private void tick(){
        //key manager updates
        keyManager.tick();
        //actualizamos el tick del player
        player.tick();
        //actualizamos el tick de la ball
        ball.tick();
    }

    //initializes out game display
    public void init() {
        display = new Display(title, width, height);
        //inicializamos assets del juego
        Assets.init();
        //inicializamos el player
        player = new Player(getWidth()/2-70, getHeight() - 100, 1, 140, 28, this);
        //inizializamos la ball
        ball = new Ball(getWidth()/2-17, getHeight() - 300, 1, 35, 35, this);
        
        display.getjFrame().addKeyListener(keyManager);
        display.getjFrame().addMouseListener(mouseManager);
        display.getjFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
    }

    //setting the thread for the game
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
        //if thread hasnt been created we just create it as in above
    }

    //stopping the thread for the game
    public synchronized void stop(){
        if(running){
            running = false;
            try{
                thread.join();
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }
    }

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        lives = new Lives(3);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    //metodo para que los items puedan accesar al key manager
    public KeyManager getKeyManager() {
        return keyManager;
    }
    public MouseManager getMouseManager(){
        return mouseManager;
    }

    //method to get fps
    public int getFps(){
        return fps;
    }

    //method to get lives
    public Lives getLives(){
        return lives;
    }
    //methodd to get Player
    public Player getPlayer(){
        return player;
    }

}