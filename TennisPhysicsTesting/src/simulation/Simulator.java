package simulation;

import functionalities.Vector3D;
import model.Ball;
import model.Player;

public class Simulator {
	public static void main(String[] args) {
        // Initial setup
        Ball ball = new Ball(new Vector3D(0, 0, 1), new Vector3D(0, 0, 0)); // Ball's initial position and velocity will be set by the serve
        Player player1 = new Player(new Vector3D(-11.885, 2.0575, 0)); // Server's position on the deuce side

        double netHeight = 0.914; // Net height in meters
        double groundHeight = 0; // Height of the ground
        double deltaTime = 0.01; // Time step for the simulation

        // Define the service box boundaries for the opponent's right service box
        double serviceBoxXMin = 0.0; // Minimum x-coordinate of the service box
        double serviceBoxXMax = 6.4; // Maximum x-coordinate of the service box
        double serviceBoxYMin = -4.115; // Minimum y-coordinate of the service box
        double serviceBoxYMax = 0.0; // Maximum y-coordinate of the service box

        // Player 1 serves the ball
        Vector3D servePosition = new Vector3D(-11.5, 0.5, 3); // Serve position (slightly above ground for the serve)
        Vector3D serveVelocity = new Vector3D(36, -6, -4); // Serve velocity (forward, slightly towards the left service box, and upwards)
        
        player1.serve(ball, servePosition, serveVelocity);

        boolean serveIn = false;

        // Simulation loop
        for (int i = 0; i < 1000; i++) { // Run for 1000 iterations as an example
            ball.update(deltaTime);
            if (ball.checkNetCollision(netHeight)) {
                System.out.println("Ball hit the net. Point is over.");
                break;
            }
            if (ball.checkBounce(groundHeight) && !serveIn) {
                // Check if the ball lands in the service box after the first bounce
                serveIn = ball.isInServiceBox(serviceBoxXMin, serviceBoxXMax, serviceBoxYMin, serviceBoxYMax);
                if (serveIn) {
                    System.out.println("Step : " + i + " - " + ball);
                    System.out.println("Serve is in!");
                } else {
                    System.out.println("Step : " + i + " - " + ball);
                    System.out.println("Serve is out!");
                }
                break;
            }

            // Print ball status
            System.out.println("Step : " + i + " - " + ball);
        }

        if (!serveIn && !ball.checkNetCollision(netHeight)) {
            System.out.println("Serve did not land in the service box.");
        }
    }
}
