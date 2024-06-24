package model;
//import org.json.JSONObject;

import functionalities.Vector3D;

public abstract class Component {
	protected String id;
	protected Vector3D v;
	protected Vector3D f;
	protected Vector3D p;
	protected double m;
	
	Component(String id, Vector3D v, Vector3D p, double m)  {
		if (id == null || v == null || p == null) {
			throw new IllegalArgumentException("One of the parameters of your component is invalid");
		}
		
		if (id.trim().length()<=0) {
			throw new IllegalArgumentException("The ID's must not contain spaces");
		}
		if (m <= 0) {
			throw new IllegalArgumentException("The mass of your component can't be a negative value");
		}
		this.id = id;
		this.v = v;
		this.p = p;
		this.m = m;
		f = new Vector3D();
	}
	public String getId() {
		return id;
	}
	public Vector3D getVelocity() {
		return v;
	}
	public Vector3D getPosition() {
		return p;
	}
	public Vector3D getForce() {
		return f;
	}
	public double getMass() {
		return m;
	}
	void addForce(Vector3D fNew) { //package protected
		f = f.plus(fNew);
	}
	void resetForce() {
		f = new Vector3D();
	}
	abstract void advance(double dt);
	/*
	public JSONObject getState() {
		JSONObject jo = new JSONObject();
		jo.put("id", id);
		jo.put("m", m);
		jo.put("p", p.asJSONArray());
		jo.put("v", v.asJSONArray());
		jo.put("f", f.asJSONArray());
		return jo;

	}*/
	/*
	public String toString() {
		return getState().toString();
	}*/
	@Override
	public boolean equals(Object o) {
		Component c = (Component) o;
		return this.id.equals(c.id);
	}
}
