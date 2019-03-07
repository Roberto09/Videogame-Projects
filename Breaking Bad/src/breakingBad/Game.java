package breakingBad;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

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
    
    //bricks
    private ArrayList<Brick> bricks; 
    
    //Life system
    private Lives lives;

    //key and mouse manager
    KeyManager keyManager;
    MouseManager mouseManager;
    
    //pause mechanics
    private boolean isPaused = false;
    private boolean isOnSaveScreen = false;
    private boolean isOnLoadScreen = false;
    private boolean gameIsOver = false;
    
    //game sesison
    GameSession gs;

    //method that cotains cycle that executes the instructions to run our game
    @Override
    public void run() {
        init();
        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timeTick;
            lastTime = now;
            if (delta >= 1) {
                //key manager updates
                keyManager.tick();
                //check for pauses press
                if(keyManager.pause && !keyManager.pausePrev){
                    isPaused = !isPaused;
                }
                //check for save press
                if(keyManager.save && !keyManager.savePrev){
                    if(isOnSaveScreen) gs.save();
                    isOnSaveScreen = !isOnSaveScreen;
                    isPaused = !isPaused;
                }
                    
                //check for recover press
                if(keyManager.load && !keyManager.loadPrev){
                    if(isOnLoadScreen) gs.resumePastSave();
                    isOnLoadScreen = !isOnLoadScreen;
                    isPaused = !isPaused;
                }
                
                //check for game over
                if(gameIsOver || bricks.isEmpty()){
                    if(keyManager.enter && !keyManager.enterPrev){
                        isPaused = false;
                        //reseteamos la ball
                        ball.reset();
                        //reseteamos el player
                        player.reset(getWidth()/2-70, getHeight() - 50);
                        //reseteamos los bricks
                        bricks = new ArrayList();
                        for(int i=50; i<750; i+=110){
                            for(int j=50; j<300; j+=70){
                                bricks.add(new Brick(i,j,80, 50, 2, this));
                            }
                        }
                        gameIsOver = false;
                    }
                    else isPaused = true;
                }

                if(keyManager.cancel && !keyManager.cancelPrev){
                    if(isOnSaveScreen){
                        isPaused = !isPaused;
                        isOnSaveScreen = !isOnSaveScreen;
                    }
                    else if(isOnLoadScreen){
                        isPaused = !isPaused;
                        isOnLoadScreen = !isOnLoadScreen;
                    }
                }
                //if it's not paused execute the tick
                if(!isPaused)
                    tick();
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
            //renderizamos los ladrillos
            for(int i=0; i<bricks.size(); i++)
                bricks.get(i).render(g);
            //renderizamos las lives
            lives.render(g);
            //renderizamos el game over cuando las vidas se acaban
            if(lives.livesOver())
                g.drawImage(Assets.gameOver, 310, 200, getWidth()-620, getHeight()-400, null);
            //renderizamos la image del pause
            if(isPaused && !isOnSaveScreen && !isOnLoadScreen){
                g.drawImage(Assets.pauseImage, 150, 100, getWidth()-300, getHeight()-200, null);
            }
            if(isOnSaveScreen){
                g.drawImage(Assets.saveImage, 150, 100, getWidth()-300, getHeight()-200, null);
            }
            if(isOnLoadScreen){
                g.drawImage(Assets.loadImage, 150, 100, getWidth()-300, getHeight()-200, null);
            }
            if(gameIsOver){
                g.drawImage(Assets.gameOver, 150, 100, getWidth()-300, getHeight()-200, null);
            }
            bs.show();
            g.dispose();
        }
    }

    private void tick(){
        //actualizamos el tick del player
        player.tick();
        //actualizamos el tick de la ball
        ball.tick();
        //actualizamos el tick de los jugadores
        for(int i=0; i<bricks.size(); i++)
            bricks.get(i).tick();
    }

    //initializes out game display
    public void init() {
        display = new Display(title, width, height);
        //inicializamos assets del juego
        Assets.init();
        //inizializamos la ball
        ball = new Ball(getWidth()-70, getHeight() , 1, 15, 15, this);
        //inicializamos el player
        player = new Player(getWidth()/2-70, getHeight() - 50, 1, 140, 28, this);
        //game session
        gs = new GameSession(this);
        //inizializamos los ladrillos
        for(int i=50; i<750; i+=110){
            for(int j=50; j<300; j+=70){
                bricks.add(new Brick(i,j,80, 50, 2, this));
            }
        }
        
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
        bricks = new ArrayList<>();
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
    
    //mehtod to get ball
    public Ball getBall(){
        return ball;
    }
    
    public void eraseBrick(Brick delete){
        bricks.remove(delete);
    }
    
    public ArrayList<Brick> getBricks(){
        return bricks;
    }
    
    public void setBricks(ArrayList<Brick> bricks){
        this.bricks = bricks;
    }
    
    void setGameIsOver(boolean gameIsOver){
        this.gameIsOver = gameIsOver;
    }
    
    boolean getGameIsOver(){
        return gameIsOver;
    }
}