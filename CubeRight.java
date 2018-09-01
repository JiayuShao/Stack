import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.*;
/**
Name: Ryan Shao
Date: June 10
Course: ICS 4U
Program: CubeRight class
*/

public class CubeRight extends CubeAbstract{
	
	public CubeRight() {
		//Set color
		sR= 150;
		sG= 182;
		sB= 87;
		//Set location
		aX = 410;
		bX = 560;
		cX = 410;
		dX = 260;
		eX = dX;
		fX = aX;
		gX = bX;
		aY = 162.5 - 20;
		bY = 267.5 - 20;
		cY = 372.5 - 20;
		dY = 267.5 - 20;
		eY = 267.5;
		fY = 372.5;
		gY = 267.5;
		speed=2.0;
	}
	//Construcotr
	public CubeRight(double aX1, double bX1, double cX1, double dX1, double eX1, double fX1, double gX1, double aY1,
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
	//Move method
	public void move() {
		if (aX > 410) {
			forward = true;
			backward = false;
		} else if (aX < 0) {
			backward = true;
			forward = false;
		}
		//Move backward
		if (backward == true) {
			aX=aX+(1*speed);
			bX=bX+(1*speed);
			cX=cX+(1*speed);
			dX=dX+(1*speed);
			eX=eX+(1*speed);
			fX=fX+(1*speed);
			gX=gX+(1*speed);
			aY = aY - (0.7*speed);
			bY = bY - (0.7*speed);
			cY = cY - (0.7*speed);
			dY = dY - (0.7*speed);
			eY = eY - (0.7*speed);
			fY = fY - (0.7*speed);
			gY = gY - (0.7*speed);
		}
		//Move upward
		else if (forward == true) {
			aX=aX-(1*speed);
			bX=bX-(1*speed);
			cX=cX-(1*speed);
			dX=dX-(1*speed);
			eX=eX-(1*speed);
			fX=fX-(1*speed);
			gX=gX-(1*speed);
			aY = aY + (0.7*speed);
			bY = bY + (0.7*speed);
			cY = cY + (0.7*speed);
			dY = dY + (0.7*speed);
			eY = eY + (0.7*speed);
			fY = fY + (0.7*speed);
			gY = gY + (0.7*speed);
		}
	}

	
}
