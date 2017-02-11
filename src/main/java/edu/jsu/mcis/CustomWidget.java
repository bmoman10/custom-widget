package edu.jsu.mcis;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class CustomWidget extends JPanel implements MouseListener {
    private java.util.List<ShapeObserver> observers;
    
    
    private final Color HEXAGON_SELECTED_COLOR = Color.green;
    private final Color DEFAULT_COLOR = Color.white;
    private boolean selected;
    private Point[] vertex;
	private Hexagon hexagon;
	private Octagon octagon;

    
    public CustomWidget() {
        observers = new ArrayList<>();
        
		
        selected = false;
        vertex = new Point[4];
        for(int i = 0; i < vertex.length; i++) { vertex[i] = new Point(); }
        Dimension dim = getPreferredSize();
		hexagon = new Hexagon(new Point(125, 175), 75);
		octagon = new Octagon(new Point(325, 175), 75);
        //calculateVertices(dim.width, dim.height);
        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener(this);
    }
	
	public Hexagon getHexagon() {
		return this.hexagon;
	}

    
    public void addShapeObserver(ShapeObserver observer) {
        if(!observers.contains(observer)) observers.add(observer);
    }
    public void removeShapeObserver(ShapeObserver observer) {
        observers.remove(observer);
    }
    private void notifyObservers() {
        ShapeEvent event = new ShapeEvent(selected);
        for(ShapeObserver obs : observers) {
            obs.shapeChanged(event);
        }
    }
    
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    private void calculateVertices(int width, int height) {
        // Square size should be half of the smallest dimension (width or height).
        int side = Math.min(width, height) / 2;
        Point[] sign = {new Point(-1, -1), new Point(1, -1), new Point(1, 1), new Point(-1, 1)};
        for(int i = 0; i < vertex.length; i++) {
            vertex[i].setLocation(width/2 + sign[i].x * side/2, 
                                  height/2 + sign[i].y * side/2);
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        //calculateVertices(getWidth(), getHeight());
        //Shape shape = getShape();
        g2d.setColor(Color.black);
        g2d.drawPolygon(hexagon.getHexagon());
		g2d.drawPolygon(octagon.getOctagon());
        if(selected) {
            g2d.setColor(HEXAGON_SELECTED_COLOR);
            g2d.fill(hexagon.getHexagon());
        }
        else {
            g2d.setColor(DEFAULT_COLOR);
            g2d.fill(hexagon.getHexagon());
			g2d.fill(octagon.getOctagon());
        }
    }

    public void mouseClicked(MouseEvent event) {
        //Shape shape = getShape();
        if(hexagon.getHexagon().contains(event.getX(), event.getY())) {
            selected = true;
            notifyObservers();
			repaint(hexagon.getHexagon().getBounds());
        }
		else {
			selected = false;
			notifyObservers();
			repaint(octagon.getOctagon().getBounds());
		}
        
    }
    public void mousePressed(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}
    
    public Shape getShape() {
        int[] x = new int[vertex.length];
        int[] y = new int[vertex.length];
        for(int i = 0; i < vertex.length; i++) {
            x[i] = vertex[i].x;
            y[i] = vertex[i].y;
        }
        Shape shape = new Polygon(x, y, vertex.length);
        return shape;
    }
    public boolean isSelected() { return selected; }



	public static void main(String[] args) {
		JFrame window = new JFrame("Custom Widget");
        window.add(new CustomWidget());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(300, 300);
        window.setVisible(true);
	}
}
