package model;

import functionalities.Vector3D;

public class Ball {
	 private Vector3D position;
	    private Vector3D previousPosition;
	    private Vector3D velocity;
	    private Vector3D acceleration;
	    private final double radius = 0.033; // Approximate radius of a tennis ball in meters
	    private final double mass = 0.057; // Approximate mass of a tennis ball in kilograms
	    private final double gravity = -9.81; // Gravity in m/s^2
	    private boolean hasBounced = false;

	    public Ball(Vector3D position, Vector3D velocity) {
	        this.position = position;
	        this.previousPosition = position;
	        this.velocity = velocity;
	        this.acceleration = new Vector3D(0, 0, gravity);
	    }

	    public void setPosition(Vector3D position) {
	        this.position = position;
	    }

	    public void setVelocity(Vector3D velocity) {
	        this.velocity = velocity;
	    }

	    public void applyForce(Vector3D force) {
	        Vector3D forceAcceleration = force.scale(1 / mass);
	        acceleration = acceleration.plus(forceAcceleration);
	    }

	    public void update(double deltaTime) {
	        // Update velocity
	        velocity = velocity.plus(acceleration.scale(deltaTime));
	        // Update position
	        previousPosition = position; // Store the previous position
	        position = position.plus(velocity.scale(deltaTime));
	        // Reset acceleration
	        acceleration = new Vector3D(0, 0, gravity);
	    }

	    public boolean checkBounce(double groundHeight) {
	        if ((previousPosition._z > groundHeight && position._z <= groundHeight) ||
	            (previousPosition._z < groundHeight && position._z >= groundHeight)) {
	            // Interpolate to find the exact point where z = groundHeight
	            double t = (groundHeight - previousPosition._z) / (position._z - previousPosition._z);
	            double xAtGround = previousPosition._x + t * (position._x - previousPosition._x);
	            double yAtGround = previousPosition._y + t * (position._y - previousPosition._y);
	            position = new Vector3D(xAtGround, yAtGround, groundHeight); // Update position to ground level

	            // Reverse vertical velocity to simulate bounce
	            velocity._z = -velocity._z;
	            hasBounced = true;
	            return true;
	        }
	        return false;
	    }

	    public boolean checkNetCollision(double netHeight) {
	        // Check if the ball crossed the net plane (x=0) between the last position and current position
	        if ((previousPosition._x < 0 && position._x >= 0) || (previousPosition._x > 0 && position._x <= 0)) {
	            // Check if the ball's z-coordinate is below the net height at the net plane
	            double zAtNet = previousPosition._z + (position._z - previousPosition._z) * (0 - previousPosition._x) / (position._x - previousPosition._x);
	            if (zAtNet <= netHeight) {
	                return true; // Ball hits the net
	            }
	        }
	        return false; // Ball does not hit the net
	    }

	    public boolean isInServiceBox(double xMin, double xMax, double yMin, double yMax) {
	        return position._x >= xMin && position._x <= xMax && position._y >= yMin && position._y <= yMax;
	    }

	    public boolean hasBounced() {
	        return hasBounced;
	    }

	    @Override
	    public String toString() {
	        return "Ball{" +
	                "position=" + position +
	                ", velocity=" + velocity +
	                ", acceleration=" + acceleration +
	                '}';
	    }
}
