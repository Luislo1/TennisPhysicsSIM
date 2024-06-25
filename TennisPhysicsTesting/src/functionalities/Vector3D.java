package functionalities;

//import org.json.JSONArray;

public class Vector3D {

	private double _x;
	private double _y;
	private double _z;

	private float _magnitude;

	private double _theta;
	private double _phi;

	// create the zero vector
	public Vector3D() {
		_x = _y = _z = 0.0;
	}

	// copy constructor
	public Vector3D(Vector3D v) {
		_x = v._x;
		_y = v._y;
		_z = v._z;
	}

	// create a vector from an array
	public Vector3D(double x, double y, double z) {
		_x = x;
		_y = y;
		_z = z;
		this._magnitude = (float) Math.sqrt(_x * _x + _y * _y + _z * _z);
		this._phi = angleHorizontal();
		this._theta = angleTheta();
		//this._theta = Math.toDegrees(Math.atan2(_z, Math.sqrt(_x * _x + _y * _y)));
		//this._phi = Math.toDegrees(Math.atan2(_y, _x));

	}

	public Vector3D(float magnitude, double theta, double phi) {
		// Convert angles to radians
		//double thetaRad = Math.toRadians(theta);
		//double phiRad = Math.toRadians(phi);
		double thetaRad = theta * Math.PI / 180.0;
		double phiRad = phi  * Math.PI / 180.0;
		
		// Calculate the components
		this._x = magnitude * Math.sin(thetaRad) * Math.cos(phiRad);
		this._y = magnitude * Math.sin(thetaRad) * Math.sin(phiRad);
		this._z = magnitude * Math.cos(thetaRad);

		this._magnitude = magnitude;

		this._theta = theta;
		this._phi = phi;
	}

	// return the inner product of this Vector a and b
	public double dot(Vector3D that) {
		return _x * that._x + _y * that._y + _z * that._z;
	}

	// return the length of the vector
	public double module() {
		return Math.sqrt(dot(this));
	}

	// return the distance between this and that
	public double distanceTo(Vector3D that) {
		return minus(that).module();
	}

	// create and return a new object whose value is (this + that)
	public Vector3D plus(Vector3D that) {
		return new Vector3D(_x + that._x, _y + that._y, _z + that._z);
	}

	// create and return a new object whose value is (this - that)
	public Vector3D minus(Vector3D that) {
		return new Vector3D(_x - that._x, _y - that._y, _z - that._z);
	}

	// return the corresponding coordinate
	public double getX() {
		return _x;
	}

	public double getY() {
		return _y;
	}

	public double getZ() {
		return _z;
	}

	// create and return a new object whose value is (this * factor)
	public Vector3D scale(double factor) {
		return new Vector3D(_x * factor, _y * factor, _z * factor);
	}

	// return the corresponding unit vector
	public Vector3D direction() {
		if (module() > 0.0)
			return scale(1.0 / module());
		else
			return new Vector3D(this);
	}

	// rotate a vector by 'alpha' degrees
	public Vector3D rotate(double alpha, char axis) {

		assert (alpha >= -180.0 && alpha <= 180.0);
		assert (axis == 'x' || axis == 'y' || axis == 'z');

		double angle = alpha * Math.PI / 180.0;
		double sine = Math.sin(angle);
		double cosine = Math.cos(angle);

		double x = _x;
		double y = _y;
		double z = _z;
		Vector3D r = new Vector3D(0, 0, 0);

		switch (axis) {
		case 'x':
			// Rotation around the x-axis
			r._x = x;
			r._y = cosine * y + (-sine) * z;
			r._z = sine * y + cosine * z;
			break;
		case 'y':
			// Rotation around the y-axis
			r._x = cosine * x + sine * z;
			r._y = y;
			r._z = -sine * x + cosine * z;
			break;
		case 'z':
			// Rotation around the z-axis (similar to your 2D rotation)
			r._x = cosine * x + (-sine) * y;
			r._y = sine * x + cosine * y;
			r._z = z;
			break;
		}

		return r;
	}

	// compute the angle 'alpha' between 'this' and 'v',
	// it's such that this.rotate(alpha) equals 'v'
	/*
	 * public double angle(Vector3D v) { double a2 = Math.atan2(v.getX(), v.getY());
	 * double a1 = Math.atan2(_x, _y); double angle = a1 - a2; double K = a1 > a2 ?
	 * -2.0 * Math.PI : 2.0 * Math.PI; angle = (Math.abs(K + angle) <
	 * Math.abs(angle)) ? K + angle : angle; return angle * 180.0 / Math.PI; }
	 */

	public double angle(Vector3D v) {
		double dot = this.dot(v);
		double magnitude1 = this.module();
		double magnitude2 = v.module();
		double cosine = dot / (magnitude1 * magnitude2);

		// Clamp cosine to the range [-1, 1] to avoid numerical issues
		if (cosine < -1.0) {
			cosine = -1.0;
		} else if (cosine > 1.0) {
			cosine = 1.0;
		}

		double angle = Math.acos(cosine);
		return (angle * 180.0 / Math.PI);
	}

	public double angleHorizontal() {
		double cosine = (this._x * _x) / (Math.abs(this._x) * Math.sqrt(_x * _x + _y * _y));

		// Clamp cosine to the range [-1, 1] to avoid numerical issues
		if (cosine < -1.0) {
			cosine = -1.0;
		} else if (cosine > 1.0) {
			cosine = 1.0;
		}
		double angle = Math.acos(cosine);
		if (_y < 0)
			angle = -angle;
		return (angle * 180.0 / Math.PI);
	}
	
	public double angleTheta() {
		double cosine = (this._z) / module();
		
		// Clamp cosine to the range [-1, 1] to avoid numerical issues
		if (cosine < -1.0) {
			cosine = -1.0;
		} else if (cosine > 1.0) {
			cosine = 1.0;
		}

		double angle = Math.acos(cosine);
		return (angle * 180.0 / Math.PI);
	}

	/*
	 * public JSONArray asJSONArray() { JSONArray a = new JSONArray(); a.put(_x);
	 * a.put(_y); return a; }
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(_x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(_y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector3D other = (Vector3D) obj;
		if (Double.doubleToLongBits(_x) != Double.doubleToLongBits(other._x))
			return false;
		if (Double.doubleToLongBits(_y) != Double.doubleToLongBits(other._y))
			return false;
		if (Double.doubleToLongBits(_z) != Double.doubleToLongBits(other._z))
			return false;
		return true;
	}

	// return a string representation of the vector
	public String toString() {
		return "[" + _x + "," + _y + "," + _z + "]";
	}

	public double get_x() {
		return _x;
	}

	public double get_y() {
		return _y;
	}

	public double get_z() {
		return _z;
	}

	public float get_magnitude() {
		return _magnitude;
	}

	public double get_theta() {
		return _theta;
	}

	public double get_phi() {
		return _phi;
	}

	public void set_x(double _x) {
		this._x = _x;
	}

	public void set_y(double _y) {
		this._y = _y;
	}

	public void set_z(double _z) {
		this._z = _z;
	}

	public void set_magnitude(float _magnitude) {
		this._magnitude = _magnitude;
	}

	public void set_theta(double _theta) {
		this._theta = _theta;
	}

	public void set_phi(double _phi) {
		this._phi = _phi;
	}

}
