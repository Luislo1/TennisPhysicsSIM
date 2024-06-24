package model;

import functionalities.Vector3D;

public class Player {
	 private Vector3D position;

	    public Player(Vector3D position) {
	        this.position = position;
	    }

	    public void strikeBall(Ball ball, Vector3D force) {
	        ball.applyForce(force);
	    }
	    
	    public void serve(Ball ball, Vector3D initialPosition, Vector3D initialVelocity) {
	        ball.setPosition(initialPosition);
	        ball.setVelocity(initialVelocity);
	    }
	}