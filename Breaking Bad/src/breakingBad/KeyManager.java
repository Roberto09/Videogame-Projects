package breakingBad;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
    public boolean up, upPrev;
    public boolean down, downPrev;
    public boolean left,leftPrev;
    public boolean right, rightPrev;
    public boolean pause, pausePrev;
    public boolean save, savePrev;
    public boolean load, loadPrev;
    public boolean cancel, cancelPrev;
    public boolean space, spacePrev;
    public boolean enter, enterPrev;

    private boolean keys[];

    public KeyManager(){
        keys = new boolean [256];
    }

    //manage states of arrows on every click
    public void tick() {
        upPrev = up;
        up = keys[KeyEvent.VK_UP];

        downPrev = down;
        down = keys[KeyEvent.VK_DOWN];

        leftPrev = left;
        left = keys[KeyEvent.VK_LEFT];

        rightPrev = right;
        right = keys[KeyEvent.VK_RIGHT];
        
        pausePrev = pause;
        pause = keys[KeyEvent.VK_P];
        
        savePrev = save;
        save = keys[KeyEvent.VK_S];
        
        loadPrev = load;
        load = keys[KeyEvent.VK_C];
        
        cancelPrev = cancel;
        cancel = keys[KeyEvent.VK_X];
        
        spacePrev = space;
        space = keys[KeyEvent.VK_SPACE];
        
        enterPrev = enter;
        enter = keys[KeyEvent.VK_ENTER];
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
}