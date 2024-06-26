package model;

import functionalities.Vector3D;

public class Player {
	private Vector3D position;
    private Vector3D previousPosition;
    private Vector3D velocity;
    private Vector3D acceleration;

    public Player(Vector3D position) {
        this.position = position;
        this.velocity=new Vector3D(0,0,0,true);
    }
    
    public Vector3D getPosition() {
		return position;
	}

    public void strikeBall(Ball ball, Vector3D force) {
        ball.applyForce(force);
    }
    
    public void serve(Ball ball, Vector3D initialPosition, Vector3D initialVelocity) {
        ball.setPosition(initialPosition);
        ball.setVelocity(initialVelocity);
    }
    
    public void update(double deltaTime) {
        // Update velocity
        velocity = velocity.plus(acceleration.scale(deltaTime));
        // Update position
        previousPosition = position; // Store the previous position
        position = position.plus(velocity.scale(deltaTime));
        // Reset acceleration
        //acceleration = new Vector3D(0, 0, 5);
    }
    
    public void angleReaction(double phi, Vector3D pos) {
    	if(pos.get_y()>this.position.get_y()) { //Yo estoy a la derecha de donde me están tirando la bola
    		if(phi<0 && -6<phi) {
    			this.acceleration=new Vector3D(0,5,0,true);
    		}
    		else if(phi<=-6 && -10<phi) {
    			this.acceleration=new Vector3D(0,0,0,true);
    		}
    		else if(phi<=-10) {
    			this.acceleration=new Vector3D(0,-5,0,true);
    		}
    	}
    	//else if() TODO other angleReactions
    }
    
    public String toString() {
        return "Player{" +
                "position=" + position +
                ", velocity=" + velocity +
                ", acceleration=" + acceleration +
                '}';
    }
    
	}