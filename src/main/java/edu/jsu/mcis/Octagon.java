package edu.jsu.mcis;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Octagon {
	private final int radius;

	private final Point center;

	private final Polygon octagon;

	public Octagon(Point center, int radius) {
		this.center = center;
		this.radius = radius;
		this.octagon = createOctagon();
	}

	private Polygon createOctagon() {
		Polygon polygon = new Polygon();

		for (int i = 0; i < 8; i++) {
			int xval = (int) (center.x + radius
					* Math.cos(i * 2 * Math.PI / 8D));
			int yval = (int) (center.y + radius
					* Math.sin(i * 2 * Math.PI / 8D));
			polygon.addPoint(xval, yval);
		}

		return polygon;
	}

	public int getRadius() {
		return radius;
	}

	public Point getCenter() {
		return center;
	}

	public Polygon getOctagon() {
		return octagon;
	}
}