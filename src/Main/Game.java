package Main;

import Graphics.Screen;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;

/**
 * Created by pedroreissilva on 27/04/15.
 */
public class Game extends Canvas implements Runnable{

    private boolean isRunning;
    private Screen screen;
    private JFrame gameWindow;
    private static int WIDTH = 800;
    private static int HEIGHT = 600;
    private int xOffcet = 0;
    private int yOffcet = 0;

    /* We will create a image buffer to load the tiles, this buffer is the size of screen and the window,
    the final result will be an array with colors, or tiles;
    */
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    public Game(){
        //instantiate the window frame and the screen
        gameWindow = new JFrame();
        screen = new Screen(WIDTH, HEIGHT);

        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setSize(new Dimension(WIDTH, HEIGHT));
        gameWindow.setVisible(true);

    }

    public void run(){
        this.requestFocus();
        /*
        We will save de last time because when we start the method run, he takes a few nanoseconds to enter in while cicle,
        when java enter in cicle we will try to know the diference between that times

        Dont forget:

        1 sec = 1000 milliseconds;
        1000 milliseconds  = 1000000000 nanoseconds;

        1000 milliseconds / 60 (fps) = 16.666666667 milliseconds;

        16.666666667 milliseconds = 16666666.667 nanoseconds;

        1000000000.0 / 60.0 = 16666666.667 nanoseconds;

         */
        long lastNanoTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double nanoSeconds = 1000000000.0 / 60.0;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        while(isRunning){
            long nowNanoTime = System.nanoTime();
            delta += (nowNanoTime - lastNanoTime) / nanoSeconds;
            lastNanoTime = nowNanoTime;

            /*
             example of delta 0.27979285999997355, when delta pass or equal 1 that means is time to update,
             the game have to update 60 times per second FPS
             */
            while (delta >= 1){
                this.update();
                updates++;
                delta--;
            }

            this.render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){ //passed one second
                //System.out.println(System.currentTimeMillis());
                timer += 1000;
                gameWindow.setTitle("Updates: " + updates + " --- Frames: " + frames);
                updates = 0;
                frames = 0;
            }
        }

    }


    public synchronized void start(){
        isRunning = true;
        Thread thread = new Thread(this);
        thread.start();

    }

    public synchronized void stop(){
        isRunning = false;
    }

    /**
     * The method render will render the screen tiles and present them to the gamer
     */
    public void render(){

        /*Triple buffering:

        [To          ***********         ***********        ***********
            display] *         * --------+---------+------> *         *
               <---- * Front B *   Show  * Mid. B. *        * Back B. * <---- Rendering
                     *         * <------ *         * <----- *         *
                     ***********         ***********        ***********

        */
        BufferStrategy bufferStrategy = gameWindow.getBufferStrategy();
        if(bufferStrategy == null){
            gameWindow.createBufferStrategy(3);
            return;
        }

        screen.clear();

        screen.render(xOffcet, yOffcet);

        for(int i = 0; i < (getWidth() * getHeight()); i++){
            this.pixels[i] = screen.getPixels()[i];
        }


        Graphics graphics = bufferStrategy.getDrawGraphics(); // connect the graphics to the buffer created by me
        graphics.drawImage(image, 0, 0, null);

        /*
        Free resources allocated by Graphics, normally the Garbage collector clean
        this memory automatically, but for better performance we will free manually
        */
        graphics.dispose();
        bufferStrategy.show();
    }

    public void update(){
    }

    public int getHeight(){
        return this.HEIGHT;
    }

    public int getWidth(){
        return this.WIDTH;
    }


}
