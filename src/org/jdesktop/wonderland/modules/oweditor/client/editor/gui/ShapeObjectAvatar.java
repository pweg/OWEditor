package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class ShapeObjectAvatar extends SimpleShapeObject{
	
	
	private Ellipse2D originalShape = null;
    private Shape transformedShape = null;
    private Paint color = GUISettings.avatarColor;
    
    ShapeObjectAvatar(int x, int y, int radius){
    	originalShape = new Ellipse2D.Double(x, y, radius, radius);
    }

	@Override
	public Shape getTransformedShape() {
		return transformedShape;
	}

	@Override
	public Shape getShape() {
		return originalShape;
	}

	@Override
	public void paintOriginal(Graphics2D g, AffineTransform at, double scale) {
		g.setPaint(color);  
        
        transformedShape = at.createTransformedShape(originalShape);
        
        g.fill(at.createTransformedShape(originalShape));
	}

	@Override
	public void setLocation(int x, int y) {
		originalShape.setFrame(x, y, originalShape.getWidth(), originalShape.getHeight());
	}

	@Override
	public void setTranslation(double distance_x, double distance_y) {
		
	}

	@Override
	public int getX() {
		return (int)originalShape.getX();
	}

	@Override
	public int getY() {
		return (int)originalShape.getY();
	}

	@Override
	public int getWidth() {
		return (int)originalShape.getWidth();
	}

	@Override
	public int getHeight() {
		return (int)originalShape.getHeight();
	}

}
