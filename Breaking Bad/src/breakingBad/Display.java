package breakingBad;

import javax.swing.*;
import java.awt.*;

public class Display {
    private JFrame jFrame; //app class
    private Canvas canvas; //to display images
    private String title;
    private int width;
    private int height;


    /* create the app, the canvas and add the canvas to the window app*/
    public void createDisplay() {
        jFrame = new JFrame(title);

        jFrame.setSize(width, height);

        //set not rezisable, visible and possible to close
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);

        //create the canvas to paint and setting size
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setFocusable(false);

        // adding the canvas to the app window and packing to the right dimensions
        jFrame.add(canvas);
        jFrame.pack();
    }

    public Display (String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        createDisplay(); // initialize display
    }

    //metodo para poder manipular canvas
    public Canvas getCanvas() {
        return canvas;
    }

    //metodo para poder manipular jFrame
    public JFrame getjFrame() {
        return jFrame;
    }
}