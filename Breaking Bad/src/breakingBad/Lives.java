package breakingBad;

import java.awt.*;

public class Lives extends Item{

    //stores the number of lifes
    private int lifeCount;

    public Lives(int lifeCount){
        super(0, 0);
        this.lifeCount = lifeCount;
    }

    public int getLifeCount(){
        return lifeCount;
    }

    //method to reduce lifes
    public void looseLife(){
        lifeCount--; 
    }
    //method to ask if lives are over
    public boolean livesOver(){
        return lifeCount == 0;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g){
        g.drawImage(Assets.livesText, 1, 8, 120, 40, null);
        //drawing lives
        for(int i = 0, currPlacement = 120; i < lifeCount; i++, currPlacement += 50){
            g.drawImage(Assets.player, currPlacement + 20, 8, 40, 40, null);
        }
    }
}
