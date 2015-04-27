package Main;

import javafx.stage.Screen;

import javax.swing.*;
import java.awt.*;

/**
 * Created by pedroreissilva on 27/04/15.
 */
public class Game extends Canvas implements Runnable{

    private boolean isRunning;
    private Screen screen;

    public Game(){

    }

    public void run(){
        try{
            this.gameLoop();
        }catch(){

        }
    }


    public synchronized void start(){
        isRunning = true;
        this.start();

    }

    public synchronized void stop(){
        isRunning = false;
    }


    public void gameLoop(){

    }

    public void render(){

    }

    public void update(){

    }


}
