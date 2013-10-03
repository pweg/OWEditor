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


public class WindowDrawingPanel extends JPanel implements ChangeListener {  
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private RenderingHints hints;  
    //private Shape[] shapes;  
    private Dimension size;
    //private ArrayList<ShapeObject> shapes;
    private double scale = 1.0; 
    private double oldScale = 1.0;
    private ListenerPan pan = null;
    
    private ShapeManager shapeManager = null;
    private boolean firstDraw = true;
    private Point centerPoint = new Point();
    private Point mousePoint = new Point();
    
   // public static final int CANVAS_WIDTH = 400;
   // public static final int CANVAS_HEIGHT = 140;
    //public static final Color CANVAS_BG_COLOR = Color.CYAN;
       
    public WindowDrawingPanel(ShapeManager shapeMan) {
        
        shapeManager = shapeMan;
        
        
        hints = new RenderingHints(null);  
        hints.put(RenderingHints.KEY_ANTIALIASING,  
                  RenderingHints.VALUE_ANTIALIAS_ON);  
        hints.put(RenderingHints.KEY_TEXT_ANTIALIASING,  
                  RenderingHints.VALUE_TEXT_ANTIALIAS_ON);  
        hints.put(RenderingHints.KEY_FRACTIONALMETRICS,  
                  RenderingHints.VALUE_FRACTIONALMETRICS_ON); 
        mousePoint = centerPoint;

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
            	mousePoint = e.getPoint();
                changeScale(0.1 * -(double)e.getWheelRotation());
            }
        });
        
    } 
    
    synchronized private void changeScale(double toAdd){
        
    	oldScale= scale;
    	scale += toAdd;
        scale = Math.max(0.0001, scale);

        repaint();  
        revalidate(); 
    }
   
    public void stateChanged(ChangeEvent e) {   
        //scale = value/100.0;  
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

        
        if(firstDraw){
            size.width = getWidth();    
            size.height = getHeight();
            firstDraw = false;
        }

        Rectangle r = getVisibleRect();
       
        double v_w = r.width/scale/2;
        double v_h = r.height/scale/2;
        //if(shapes.size() == 0) initShapes(); 
        
        // Keep shapes centered on panel.  
        double x = getWidth() / 2; 
        double y = getHeight() / 2; 
        System.out.println(v_w);
        
        
        AffineTransform at = AffineTransform.getTranslateInstance(v_w, v_h);  
        //AffineTransform at = new AffineTransform();
        at.scale(scale, scale);  
        g2.setPaint(GUISettings.backgroundColor); 
        
        shapeManager.drawShapes(g2, at, scale);
        
   } 
   
    public Dimension getPreferredSize() {  
        int w = (int)(scale*size.width);  
        int h = (int)(scale*size.height);  
        return new Dimension(w, h);  
    } 
    
    public double getScale(){
        return scale;
    }
    
    public double getTranslationX(){
        return (getWidth() - scale*size.width)/2;  
    }
    
    public double getTranslationY(){
        return (getHeight() - scale*size.height)/2;  
    }


    
   
    
    
   
     
}  