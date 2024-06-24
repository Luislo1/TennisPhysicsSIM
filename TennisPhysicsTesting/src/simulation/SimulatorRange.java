package simulation;

import functionalities.Vector3D;
import model.Ball;
import model.Player;

public class SimulatorRange {
	public static void main(String[] args) {
		// Initial setup
		Ball ball = new Ball(new Vector3D(0, 0, 1), new Vector3D(0, 0, 0)); // Ball's initial position and velocity will
																			// be set by the serve
		Player player1 = new Player(new Vector3D(-11.885, 2.0575, 0)); // Server's position on the deuce side

		double netHeight = 0.914; // Net height in meters
		double groundHeight = 0; // Height of the ground
		double deltaTime = 0.01; // Time step for the simulation
		double vx = 62, vy = 0, vz = 0;
		// Define the service box boundaries for the opponent's right service box
		double serviceBoxXMin = 0.0; // Minimum x-coordinate of the service box
		double serviceBoxXMax = 6.4; // Maximum x-coordinate of the service box
		double serviceBoxYMin = -4.115; // Minimum y-coordinate of the service box
		double serviceBoxYMax = 0.0; // Maximum y-coordinate of the service box

		// Player 1 serves the ball
		Vector3D servePosition = new Vector3D(-11.5, 0.5, 3); // Serve position (slightly above ground for the serve)
		Vector3D serveVelocity = new Vector3D(62, vy, vz); // Serve velocity (forward, slightly towards the left service
															// box, and upwards)

		//player1.serve(ball, servePosition, serveVelocity);

		boolean serveIn = false;
		boolean bounce = false;
		int k = 0;
		vx = 66;
		System.out.println("Combinations for Vx : " + vx);
		double vyMin = 0, vyMax = 0;
		for (int i = 0; i < 30; i++) {
			vy = (double)-i;
			for (int j = 0; j < 20; j++) {
				vz = (double)-j;
				serveVelocity = new Vector3D(vx, vy, vz);
				k = 0;
				bounce = false;
				player1.serve(ball, servePosition, serveVelocity);
				while (k < 1000 && !bounce) {
					ball.update(deltaTime);
					//System.out.println("Step : " + k + " - " + ball);
					if (ball.checkNetCollision(netHeight)) {
						//System.out.println("Ball hit the net. Point is over.");
						break;
					}
					if (ball.checkBounce(groundHeight) && !serveIn) {
						// Check if the ball lands in the service box after the first bounce
						serveIn = ball.isInServiceBox(serviceBoxXMin, serviceBoxXMax, serviceBoxYMin, serviceBoxYMax);
						if (serveIn) {
							System.out.println("Vy: " + vy + " Vz: " + vz);
							//System.out.println("Vz: " + vz);
							//System.out.println("Step : " + k + " - " + ball);
							//System.out.println("Serve is in!");
							serveIn = false;
						} 
						bounce = true;
					}
					k++;
				}
				servePosition = new Vector3D(-11.5, 0.5, 3);
			}
		}
	}
}
