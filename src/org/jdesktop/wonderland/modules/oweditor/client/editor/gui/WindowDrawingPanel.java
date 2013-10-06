package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A class which is used for drawing. It implements the main surface for the
 * 2D shape objects and calls the shapeManager on repaint.
 * 
 * @author Patrick
 *
 */
public class WindowDrawingPanel extends JPanel implements ChangeListener {  

    private static final long serialVersionUID = 1L;
    private RenderingHints hints;  
    
    private Dimension size;
    private double scale = 1.0;
    
    private ListenerPan pan = null;    
    private GUIController gc = null;
    
    /*
     * These two translation integers are used to 
     * center the image. For example, when negative 
     * coordinates are used, it will move the image to the 
     * center in order to see whats beyond the origin.
     */
    private int translationX = 0;
    private int translationY = 0;
    
   // public static final int CANVAS_WIDTH = 400;
   // public static final int CANVAS_HEIGHT = 140;
    //public static final Color CANVAS_BG_COLOR = Color.CYAN;
       
    /**
     * Creates a new instance of the drawingPanel
     * 
     * @param gc A GUIControler instance.
     */
    public WindowDrawingPanel(GUIController gc) {
        
        this.gc = gc;
        
        hints = new RenderingHints(null);  
        hints.put(RenderingHints.KEY_ANTIALIASING,  
                  RenderingHints.VALUE_ANTIALIAS_ON);  
        hints.put(RenderingHints.KEY_TEXT_ANTIALIASING,  
                  RenderingHints.VALUE_TEXT_ANTIALIAS_ON);  
        hints.put(RenderingHints.KEY_FRACTIONALMETRICS,  
                  RenderingHints.VALUE_FRACTIONALMETRICS_ON); 

        size = new Dimension(10,10);  
        setBackground(new Color(255,255,255)); 
        pan = new ListenerPan(this);
        this.addMouseListener(pan);
        this.addMouseMotionListener(pan);
        this. addMouseWheelListener( new MouseWheelListener( )
        {
        	/**
        	 * MouseListener for zooming.
        	 */
            @Override
            public void mouseWheelMoved( MouseWheelEvent e )
            {
            	double curScale = scale;
                changeScale(GUISettings.zoomSpeed * -(double)e.getWheelRotation());
                
                changeViewPort(curScale);
            }
        });
    } 
    
    /**
     * Changes the scale of the 2D graphics.
     * 
     * @param toAdd The amount which should be added/removed from
     * the scale value.
     */
    private void changeScale(double toAdd){
    	
    	Rectangle r = getVisibleRect();
    	if(size.width < r.width/(scale) &&
    			size.height < r.height/(scale) &&
    			toAdd < 0)
    		return;
    		

        scale += toAdd;
    	
        scale = Math.max(0.0001, scale);

        repaint();  
        revalidate(); 
    }
    
    /**
     * Changes the viewport, when zoomed with the mouse wheel.
     * 
     * @param curScale The old scale value, before the new one
     * was set.
     */
    private void changeViewPort(double curScale){
    	Rectangle r = getVisibleRect();
        
        double v_x = r.x/(curScale)*scale;
        double v_y = r.y/(curScale)*scale;
        
        double add_x = (r.width/curScale-r.width/scale)/2; 
        double add_y = (r.height/curScale-r.height/scale)/2; 
        

        int new_x = (int) Math.round(v_x+ add_x);
        int new_y = (int) Math.round(v_y+ add_y);

        Point p = new Point(new_x, new_y);
        
        gc.mainScrollPanel.getViewport().setView(gc.mainScrollPanel.getViewport().getView());
    	gc.mainScrollPanel.getViewport().setViewPosition(p);
    }
   
    public void stateChanged(ChangeEvent e) {   
        repaint();  
        revalidate();  
    }  
    
    @Override
    public void paint(Graphics g) {
      super.paint(g);
      paintComponent(g);
    }
   
    protected void paintComponent(Graphics g) {  
        super.paintComponent(g); 
        Graphics2D g2 = (Graphics2D)g;  
        g2.setRenderingHints(hints);  
        
        AffineTransform at = new AffineTransform();
        at.translate(translationX, translationY); 
        at.scale(scale, scale);  
        g2.setPaint(GUISettings.backgroundColor); 
        
        gc.sm.drawShapes(g2, at, scale);
        
    } 
    
    /**
     * Sets a new width for the drawing panel.
     * 
     * @param width the new width.
     */
    public void setNewWidth(int width){
    	size.width = (int)( (width + gc.frame.getWidth()/GUISettings.widthDivisor));
    	if(size.width < gc.frame.getWidth())
    		size.width = gc.frame.getWidth();
    	
        revalidate();  
    }
    
    /**
     * Sets a new height for the drawing panel.
     * 
     * @param height the new height.
     */
    public void setNewHeight(int height){
		size.height = (int)( (height + gc.frame.getHeight()/GUISettings.heightDivisor));
    	if(size.height < gc.frame.getHeight())
    		size.height = gc.frame.getHeight();
    	
        revalidate();  
    }
   
    /**
     * Returns the preferred size of the drawing panel.
     */
    public Dimension getPreferredSize() {  
        int w = (int)(scale*size.width);  
        int h = (int)(scale*size.height);  
        return new Dimension(w, h);  
    } 
    
    /**
     * Returns the current scale.
     * 
     * @return The scale.
     */
    public double getScale(){
        return scale;
    }
    
    /**
     * Sets a new translation value for x, which is
     * used for centering the objects.
     * 
     * @param x the new x value.
     */
    public void setNewMinX(int x){
    	translationX = (-x)+ gc.frame.getWidth()/(GUISettings.widthDivisor*2);
    }
    
    /**
     * Sets a new translation value for y, which is
     * used for centering the objects.
     * 
     * @param y the new y value.
     */
    public void setNewMinY(int y){
    	translationY = (-y)+ gc.frame.getHeight()/(GUISettings.heightDivisor*2);
    }


    
   
    
    
   
     
}  