package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUISettings;

/**
 * A class which is used for drawing. It implements the main surface for the
 * 2D shape objects and calls the shapeManager on repaint.
 * 
 * @author Patrick
 *
 */
public class DrawingPanel extends JPanel implements ChangeListener, IDrawingPanel {  

    private static final long serialVersionUID = 2;
    private final RenderingHints hints;  
    
    private Dimension size;
    private double scale = 1.0;
      
    private FrameController fc = null;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();
    
    private double zoomSpeed = 1;
    
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
     * @param fc A frame controller instance.
     */
    public DrawingPanel(FrameController fc) {
        
        this.fc = fc;
        
        hints = new RenderingHints(null);  
        hints.put(RenderingHints.KEY_ANTIALIASING,  
                  RenderingHints.VALUE_ANTIALIAS_ON);  
        hints.put(RenderingHints.KEY_TEXT_ANTIALIASING,  
                  RenderingHints.VALUE_TEXT_ANTIALIAS_ON);  
        hints.put(RenderingHints.KEY_FRACTIONALMETRICS,  
                  RenderingHints.VALUE_FRACTIONALMETRICS_ON); 

        size = new Dimension(10,10);  
        setBackground(new Color(255,255,255)); 
    } 
    
    /**
     * Changes the scale of the 2D graphics.
     * 
     * @param toAdd The amount which should be added/removed from
     * the scale value.
     */
    public void changeScale(double toAdd){

        double curScale = getScale();
        
        writeLock.lock();
        
        Rectangle r = getVisibleRect();
        
        if(size.width < r.width/(scale) &&
                size.height < r.height/(scale) &&
                toAdd < 0){

            writeLock.unlock();
            return;
        }
    
        double newScale = scale + (toAdd*zoomSpeed);
        
        //adjust the zoom speed 
        if(newScale <= (zoomSpeed/10)){
            zoomSpeed = zoomSpeed/10;
        }else if(newScale> zoomSpeed && newScale>scale && zoomSpeed < 1){
            scale += (toAdd*zoomSpeed);
            zoomSpeed = zoomSpeed*10;
            scale -= (toAdd*zoomSpeed);
        }

        scale = scale + (toAdd*zoomSpeed);
        
        writeLock.unlock();

        repaint();  
        revalidate(); 

        changeViewPort(curScale);
    }
    
    /**
     * Changes the viewport, when zoomed with the mouse wheel.
     * 
     * @param curScale The old scale value, before the new one
     * was set.
     * @NonNull String scrollPanel;
     */
    public void changeViewPort(double curScale){
        Rectangle r = getVisibleRect();
        
        readLock.lock();
        double v_x = r.x/curScale*scale;
        double v_y = r.y/curScale*scale;

        Point mouse = this.getMousePosition();   
        
        /*
         * Calculates the distance the mouse has to the 
         * coordinates of the viewport
         */
        double mouse_x = mouse.x-r.x;
        double mouse_y = mouse.y-r.y;
        
        /*
         * Calculates the mouse position differences to the viewport.
         * This is done to make the viewport zoom in to the mouse position.
         */
        double add_x = mouse_x - (mouse_x/curScale*scale);
        double add_y = mouse_y - (mouse_y/curScale*scale);
        readLock.unlock();
        
        //adds/subtract the viewport difference from the current viewport.
        int new_x = (int) Math.max(0,Math.round(v_x - add_x));
        int new_y = (int) Math.max(0,Math.round(v_y - add_y));
        
        //sets the new view.
        Point p = new Point(new_x, new_y);        
        fc.mainScrollPanel.getViewport().setView(fc.mainScrollPanel.getViewport().getView());
        fc.mainScrollPanel.getViewport().setViewPosition(p);
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
   
    @Override
    protected void paintComponent(Graphics g) {  
        super.paintComponent(g); 
        Graphics2D g2 = (Graphics2D)g;  
        g2.setRenderingHints(hints);  

        AffineTransform at = new AffineTransform();
        
        readLock.lock();
        at.translate(translationX*scale, translationY*scale); 
        at.scale(scale, scale);  
        readLock.unlock();
        
        g2.setPaint(GUISettings.BGCOLOR); 
        
        fc.window.drawShapes(g2, at);
    } 
    
    /**
     * Sets a new width for the drawing panel.
     * 
     * @param width the new width.
     */
    public void setNewWidth(int width){
        size.width = (int)( (width + fc.mainframe.getWidth()/GUISettings.WIDTHDIVISOR));
        if(size.width < fc.mainframe.getWidth())
            size.width = fc.mainframe.getWidth();
        revalidate();  
    }
    
    /**
     * Sets a new height for the drawing panel.
     * 
     * @param height the new height.
     */
    public void setNewHeight(int height){
        size.height = (int)( (height + fc.mainframe.getHeight()/GUISettings.HEIGHTDIVISOR));
        if(size.height < fc.mainframe.getHeight())
            size.height = fc.mainframe.getHeight();
        
        revalidate();  
    }
   
    /**
     * Returns the preferred size of the drawing panel.
     * 
     * @return The size of the drawing pan.
     */
    @Override
    public Dimension getPreferredSize() {  
        readLock.lock();
        int w = (int)(scale*size.width);  
        int h = (int)(scale*size.height);  
        readLock.unlock();
        
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
        writeLock.lock();
        int curTrans = translationX;
        translationX = (-x)+ fc.mainframe.getWidth()/(GUISettings.WIDTHDIVISOR*2);
        writeLock.unlock();
        
        setViewportSizeChange(translationX-curTrans, 0);
    }
    
    /**
     * Sets a new translation value for y, which is
     * used for centering the objects.
     * 
     * @param y the new y value.
     */
    public void setNewMinY(int y){
        writeLock.lock();
        int curTrans = translationY;
        translationY = (-y)+ fc.mainframe.getHeight()/(GUISettings.HEIGHTDIVISOR*2);
        writeLock.unlock();
        
        setViewportSizeChange(0, translationY-curTrans);
    }
    
    /**
     * @NonNull String scrollPanel;
     */
    private void setViewportSizeChange(int x, int y){
        readLock.lock();
        Rectangle r = getVisibleRect();
        
        if(y == 0){
            Point p = new Point((int)Math.round(r.x+x*scale), r.y);
            
            fc.mainScrollPanel.getViewport().setView(fc.mainScrollPanel.getViewport().getView());
            fc.mainScrollPanel.getViewport().setViewPosition(p);
            
        }else if(x == 0){
            Point p = new Point(r.x, (int)Math.round(r.y+y*scale));
            
            fc.mainScrollPanel.getViewport().setView(fc.mainScrollPanel.getViewport().getView());
            fc.mainScrollPanel.getViewport().setViewPosition(p);
            
        }
        readLock.unlock();
    }

    private AffineTransform getTransformation() {
        readLock.lock();
        
        AffineTransform at = new AffineTransform();
        at.translate(translationX*scale, translationY*scale); 
        at.scale(scale, scale);
        
        readLock.unlock();
        
        return at;
    }

    @Override
    public Point transformBack(Point p) {

        try {
            Point revert = new Point(0,0);
            AffineTransform at = getTransformation();
            at.inverseTransform(p, revert);
            return revert;
        } catch (NoninvertibleTransformException e) {
        }
        return null;
    }
     
}  