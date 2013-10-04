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
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 */
public class WindowDrawingPanel extends JPanel implements ChangeListener {  

    private static final long serialVersionUID = 1L;
    private RenderingHints hints;  
    private Dimension size;
    private double scale = 1.0; 
    private ListenerPan pan = null;
    
    private ShapeManager shapeManager = null;
    private GUIControler gc = null;
    
    private Point centerPoint = new Point();
    
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
       
    public WindowDrawingPanel(ShapeManager shapeMan, GUIControler gc) {
        
        shapeManager = shapeMan;
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
            @Override
            public void mouseWheelMoved( MouseWheelEvent e )
            {
            	double curScale = scale;
                changeScale(0.1 * -(double)e.getWheelRotation());
                
                changeViewPort(curScale);
               
            }
        });
        
    } 
    
    synchronized private void changeScale(double toAdd){
    	scale += toAdd;
        scale = Math.max(0.0001, scale);

        repaint();  
        revalidate(); 
    }
    
    private void changeViewPort(double curScale){
    	Rectangle r = getVisibleRect();
        
        double v_x = r.x/(curScale)*scale;
        double v_y = r.y/(curScale)*scale;
        
        double add_x = (r.width/curScale-r.width/scale)/2; 
        double add_y = (r.height/curScale-r.height/scale)/2; 
        
        System.out.println("scale  " +scale + " prev "+ curScale);
        System.out.println("curr_x  " +r.x + " " + r.y);
        System.out.println("xy " +v_x + " " + v_y);
        System.out.println("toadd " +add_x + " " + add_y);
        System.out.println("widthheight " +r.width + " " + r.height);
        System.out.println("center " +r.getCenterX() + " " + r.getCenterY());

        int new_x = (int) Math.round(v_x+ add_x);
        int new_y = (int) Math.round(v_y+ add_y);

        Point p = new Point(new_x, new_y);
        System.out.println("new_x " +new_x + " " + new_y);

        System.out.println("----------");
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
        at.translate(translationX, translationY); //.getTranslateInstance(v_w, v_h);  
        //AffineTransform at = new AffineTransform();
        at.scale(scale, scale);  
        g2.setPaint(GUISettings.backgroundColor); 
        
        shapeManager.drawShapes(g2, at, scale);
        
   } 
    
    public void setNewWidth(int width){
    	size.width = (int)( (width + gc.getFrameWidth()/GUISettings.widthDivisor));
        revalidate();  
    }
    
    public void setNewHeight(int height){
    	size.height = (int)( (height + gc.getFrameHeight()/GUISettings.heightDivisor));
        revalidate();  
    }
   
    public Dimension getPreferredSize() {  
        int w = (int)(scale*size.width);  
        int h = (int)(scale*size.height);  
        return new Dimension(w, h);  
    } 
    
    public double getScale(){
        return scale;
    }
    

    public void setNewMinX(int x){
    	translationX = (-x)+ gc.getFrameWidth()/(GUISettings.widthDivisor*2);
    }
    
    public void setNewMinY(int y){
    	translationY = (-y)+ gc.getFrameHeight()/(GUISettings.heightDivisor*2);
    }


    
   
    
    
   
     
}  