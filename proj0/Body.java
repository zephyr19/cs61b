public class Body {
	public double xxPos; //Its current x position
	public double yyPos;
	public double xxVel; //Its current velocity in the x direction
	public double yyVel;
	public double mass; //Its mass
	public String imgFileName; /** The name of the file that corresponds
							to the image that depicts the body */
	
	public Body(double xP, double yP, double xV,
				double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Body(Body b) {
		xxPos = this.xxPos;
		yyPos = this.yyPos;
		xxVel = this.xxVel;
		yyVel = this.yyVel;
		mass = this.mass;
		imgFileName = this.imgFileName;	
	}

	/**A method returns the distance of two bodys */
	public double calcDistance(Body b) {
		return Math.sqrt(Math.pow(this.xxPos-b.xxPos, 2) + Math.pow(this.yyPos-b.yyPos, 2));
	}

	/**A method returns the force exerted by a given body */
	public double calcForceExertedBy(Body b) {
		final double G = 6.67e-11; //why static final doesn't work
		return G * this.mass * b.mass / Math.pow(this.calcDistance(b), 2);
	}

	/**The following two methods returns the x or y direction force */
	public double calcForceExertedByX(Body b) {
		return (b.xxPos - this.xxPos) * this.calcForceExertedBy(b) / this.calcDistance(b);
	}	

	public double calcForceExertedByY(Body b) {
		return (b.yyPos - this.yyPos) * this.calcForceExertedBy(b) / this.calcDistance(b);
	}

	/** THe following two methods each take in an array of Bodys
		and returns the x or y direction net force */
	public double calcNetForceExertedByX(Body[] bodys) {
		/** the convinient way to use the 'for' */
		double net_force_x = 0;
		for(Body b : bodys) {
			if(this == b) {
				continue;
			}
			else {
				net_force_x += this.calcForceExertedByX(b);
			}
		}
		return net_force_x;
	}

	public double calcNetForceExertedByY(Body[] bodys) {
		double net_force_y = 0;
		for(Body b : bodys) {
			if(this.equals(b)) {
				continue;
			}
			else {
				net_force_y += this.calcForceExertedByY(b);
			}
		}
		return net_force_y;
	}

	/** A method determines how much the forces exterd on the body
		will cause that body to accelerate, and changes the body's
		velocity and position. */
	public void update(double dt, double fX, double fY) {
		double aX = fX / this.mass;
		double aY = fY / this.mass;
		this.xxVel = this.xxVel + dt * aX;
		this.yyVel = this.yyVel + dt * aY;
		this.xxPos = this.xxPos + dt * this.xxVel;
		this.yyPos = this.yyPos + dt * this.yyVel;
	}

	/** The draw method uses the StdDraw API to draw the Body's
	 *	image at the Body's position. */
	public void draw() {
		String imgToDraw = "images/" + this.imgFileName;
		StdDraw.picture(this.xxPos, this.yyPos, imgToDraw);
	}
}