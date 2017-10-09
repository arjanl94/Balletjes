/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package movingballsfx;

import javafx.scene.paint.Color;

/**
 *
 * @author Peter Boots
 */
public class BallRunnable implements Runnable {

    private Ball ball;
    private Monitor monitor;

    public BallRunnable(Ball ball, Monitor monitor) {
        this.ball = ball;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (ball.getColor() == Color.BLUE && ball.isEnteringCs()) {
                    monitor.enterWriter();
                } else if (ball.getColor() == Color.RED && ball.isEnteringCs()) {
                    monitor.enterReader();
                } else if (ball.getColor() == Color.BLUE && ball.isLeavingCs()) {
                    monitor.exitWriter();
                } else if (ball.getColor() == Color.RED && ball.isLeavingCs()) {
                    monitor.exitReader();
                }

                ball.move();
                   
                Thread.sleep(ball.getSpeed());
                
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
