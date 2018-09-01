import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

/**
Name: Ryan Shao
Date: June 10
Course: ICS 4U
Program: CubeAbstract abstract class
*/

public abstract class CubeAbstract {
	//Declare and initialize globle virables
	public double aX, aY, bX, bY, cX, cY, dX, dY, eX, eY, fX, fY, gX, gY,speed;
	public boolean forward = true, backward = false;
	public int sR, sG, sB;
	//Draw the cube
	public void draw(Graphics2D g2) {
		//Set the 3D color
		Color surface = new Color(sR, sG, sB);
		Color dark = new Color(sR - 71, sG - 80, sB - 28);
		Color light = new Color(sR - 56, sG - 55, sB - 18);
		//Draw the polygon
		g2.setColor(surface);
		Polygon surface1 = new Polygon();
		surface1.addPoint((int) aX, (int) aY);
		surface1.addPoint((int) bX, (int) bY);
		surface1.addPoint((int) cX, (int) cY);
		surface1.addPoint((int) dX, (int) dY);
		g2.fillPolygon(surface1);

		g2.setColor(dark);
		Polygon surface2 = new Polygon();
		surface2.addPoint((int) cX, (int) cY);
		surface2.addPoint((int) dX, (int) dY);
		surface2.addPoint((int) eX, (int) eY);
		surface2.addPoint((int) fX, (int) fY);
		g2.fillPolygon(surface2);

		g2.setColor(light);
		Polygon surface3 = new Polygon();
		surface3.addPoint((int) bX, (int) bY);
		surface3.addPoint((int) cX, (int) cY);
		surface3.addPoint((int) fX, (int) fY);
		surface3.addPoint((int) gX, (int) gY);
		g2.fillPolygon(surface3);
	}
	
	public abstract void move();
	//Get methods
	public double getaX() {
		return aX;
	}

	public double getaY() {
		return aY;
	}

	public double getbX() {
		return bX;
	}

	public double getbY() {
		return bY;
	}

	public double getcX() {
		return cX;
	}

	public double getcY() {
		return cY;
	}

	public double getdX() {
		return dX;
	}

	public double getdY() {
		return dY;
	}

	public double geteX() {
		return eX;
	}

	public double geteY() {
		return eY;
	}

	public double getfX() {
		return fX;
	}

	public double getfY() {
		return fY;
	}

	public double getgX() {
		return gX;
	}

	public double getgY() {
		return gY;
	}
	public double getSpeed() {
		return speed;
	}
	public int getR() {
		return sR;
	}
	
	public int getG() {
		return sG;
	}
	
	public int getB() {
		return sB;
	} 
	//Set methods
	public void setSpeed(double s){
		speed=s;
	}
	public void setCube(double aX1, double bX1, double cX1, double dX1, double eX1, double fX1, double gX1, double aY1,
			double bY1, double cY1, double dY1, double eY1, double fY1, double gY1) {
		aX=aX1;
		bX=bX1;
		cX=cX1;
		dX=dX1;
		eX=eX1;
		fX=fX1;
		gX=gX1;
		aY=aY1;
		bY=bY1;
		cY=cY1;
		dY=dY1;
		eY=eY1;
		fY=fY1;
		gY=gY1;
	}
	
	public void decelerate(){
		aY=aY+19;
		bY=bY+19;
		cY=cY+19;
		dY=dY+19;
		eY=eY+19;
		fY=fY+19;
		gY=gY+19;
	}
	public void setaX(double aX1) {
		aX=aX1;
	}
	public void setbX(double bX1) {
		bX=bX1;
	}
	public void setcX(double cX1) {
		cX=cX1;
	}
	public void setdX(double dX1) {
		dX=dX1;
	}
	public void seteX(double eX1) {
		eX=eX1;
	}
	public void setfX(double fX1) {
		fX=fX1;
	}
	public void setgX(double gX1) {
		gX=gX1;
	}
	public void setaY(double aY1) {
		aY=aY1;
	}
	public void setbY(double bY1) {
		bY=bY1;
	}
	public void setcY(double cY1) {
		cY=cY1;
	}
	public void setdY(double dY1) {
		dY=dY1;
	}
	public void seteY(double eY1) {
		eY=eY1;
	}
	public void setfY(double fY1) {
		fY=fY1;
	}
	public void setgY(double gY1) {
		gY=gY1;
	}
	public void setRGB(int r1,int g1, int b1) {
		sR=r1;
		sG=g1;
		sB=b1;
	}
	
}
