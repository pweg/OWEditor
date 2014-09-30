package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUISettings;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.IInternalMediator;

/**
 * A class for standard rectangles. The rectangles are used as a 
 * representation of non-round objects.
 * 
 * @author Patrick
 */
public class ShapeRectangle extends ShapeObject{
    
    private Rectangle originalShape = null;
    private Shape scaledShape = null;
    private Shape transformedShape = null;
    private Shape printShape = null;
    
    private long id = -1;
    private boolean isSelected = false;
    
    private Paint color = GUISettings.OBJECTCOLOR;
    private final Paint nameColor = GUISettings.OBJECTNAMECOLOR;
    
    private String name = "";
    private String printName = "";
    private boolean nameWrapp = false;
    
    private double rotation = 0;
    private double scale = 0;
    
    public double z = 0;
    //private BufferedImage img = null;
    private String imgName = "";
    private String imgDir = "";
    
    private IInternalMediator smi = null;
    
    
    //These variables are used to determine, where the name of the object should be.
    private int nameBoundsX = GUISettings.NAMEPOSITIONINX;
    private int nameBoundsAbove = GUISettings.NAMEPOSITIONINY;
    private final int imageMargin = GUISettings.IMGMARGIN;
        
    /**
     * Creates a new ObjectRectangle shape instance.
     * 
     * @param x the x coordinate of the shape.
     * @param y the y coordinate of the shape.
     * @param z the z coordinate
     * @param width the width of the shape.
     * @param height the height of the shape.
     * @param id the shape id.
     * @param name the name of the shape.
     * @param rotation the rotation
     * @param scale the scale
     * @param imgName the representatinal image name
     * @param imgDir the representational image directory
     * @param smi the graphic mediator (used for retrieving 
     * the representational image).
     */
    public ShapeRectangle(int x, int y, double z,
            int width, int height, long id, String name,
            double rotation, double scale, String imgName, 
            String imgDir, IInternalMediator smi){
        
        if(scale == 0)
            scale = 1;
        originalShape = new Rectangle (x, y, width, height);
        this.name = name;
        this.id = id;
        this.rotation = rotation;
        this.scale = scale;
        this.imgDir = imgDir;
        this.imgName = imgName;
        this.z = z;
        this.smi = smi;
        
        scaledShape = ShapeUtilities.scaleShape(originalShape, scale, 0,0);
        transformedShape = ShapeUtilities.rotateShape(scaledShape, rotation);
    }
    
    @Override
    public Shape getTransformedShape() {
        return transformedShape;
    }

    @Override
    public Rectangle getShape() {
        return originalShape;
    }

    @Override
    public long getID() {
        return id;
    }

    @Override
    public void paintOriginal(Graphics2D g, AffineTransform at) {
        g.setPaint(color); 
        scaledShape = ShapeUtilities.scaleShape(originalShape, scale, 0,0);
        transformedShape = ShapeUtilities.rotateShape(scaledShape, rotation);
        
        //image transformation
        printShape =  at.createTransformedShape(transformedShape);
        
        g.fill(printShape);
        
        //changes color when selected.
        if(isSelected){
            g.setPaint(GUISettings.SELECTIONCOLOR);
        }else if(color.equals(GUISettings.BGOBJECTCOLOR)){
            g.setPaint(GUISettings.BGBORDERCOLOR);
        }else
        {
            g.setPaint(GUISettings.OBJECTNAMECOLOR);
        }
        g.draw(printShape); 
        
        if(!color.equals(GUISettings.BGOBJECTCOLOR)){
            paintImage(g, at);
            paintName(g,at);
        }
    }
    
    private void paintImage(Graphics2D g, AffineTransform at){
        BufferedImage img = smi.getImage(imgName, imgDir);
        
        if(img == null)
            return;
        
        //Create an unrotated transformed shape from the scaled one
        //Interestingly using just the affine transform on the graphics2d
        //does not seem to work every time. Zooming and right clicking
        //moves the shape when affine transform is used on g.
        Shape transformed =  at.createTransformedShape(scaledShape);
        Rectangle r = transformed.getBounds();
        int imageMarginLocal = (int) Math.round(this.imageMargin*at.getScaleX());

        //Make the image size proportional to the shape size
        int width = r.width-2*imageMarginLocal;
        int height = r.height-2*imageMarginLocal;
        int iwidth = img.getWidth();
        int iheight= img.getHeight();
        double scale_w = (double)width/iwidth;
        double scale_h = (double)height/iheight;

        int iheight_s = (int) Math.round(iheight * scale_w);
        int iwidth_s = (int) Math.round(iwidth * scale_h);
        
        if(iheight_s <= height){
            iwidth=width;
            iheight=iheight_s;
        }else if(iwidth_s <= width){
            iwidth=iwidth_s;
            iheight=height;
        }else{
            iwidth= width;
            iheight=height;
        }
        
        int x = r.x+(int) Math.round((width-iwidth)/2);
        int y = r.y+(int) Math.round((height-iheight)/2);
        
        x=Math.max(x+imageMarginLocal, r.x);
        y=Math.max(y+imageMarginLocal, r.y);
        
        //Watermark image
        AlphaComposite alpha = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, GUISettings.ALPHA);
        Composite cur_comp = g.getComposite();
        g.setColor(Color.white);

        //Draw image
        AffineTransform original =  g.getTransform();
        g.rotate(Math.toRadians(rotation), 
                r.getCenterX(), 
                r.getCenterY());
        g.fillRect(x, y, iwidth, iheight);
        g.setComposite(alpha);
        g.drawImage(img,x,y,iwidth, iheight, null);
        
        //Reset graphics
        g.setTransform(original);
        g.setComposite(cur_comp);

    }

    /**
     * Paints the name of the object. This is not implemented in the
     * normal paint function, because the names have to be painted
     * later, in order to see them, if another object is blocking the view.
     * 
     * @param g the graphics2d instance.
     * @param at the affine transformation.
     * @param scale
     */
    private void paintName(Graphics2D g, AffineTransform at) {
        g.setPaint(nameColor); 
        
        double scaleFactor = at.getScaleX();
        
        //changes color when selected.
        if(isSelected)
            g.setPaint(GUISettings.SELECTIONCOLOR);

        int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
        int font_size = (int)Math.round(GUISettings.OBJECTNAMESIZE*scaleFactor * screenRes / 72.0);

        Font font = new Font(GUISettings.OBJECTNAMEFONTTYPE, Font.PLAIN, font_size);        
        g.setFont(font);

        //You have to create a transformed shape without rotation, or
        //the text will be somewhere else.
        
        Shape transformed =  at.createTransformedShape(scaledShape);
        Rectangle r = transformed.getBounds();
        int x = (int) (r.getX() + Math.round(nameBoundsX*scaleFactor));
        int y = (int) (r.getY() + Math.round(nameBoundsAbove*scaleFactor));
        
        if(!nameWrapp)
            nameWrapp(g,scaleFactor, font, r);

        AffineTransform original =  g.getTransform();
        g.rotate(Math.toRadians(rotation), 
                r.getCenterX(), 
                r.getCenterY());
        g.drawString(printName, x, y);  
        g.setTransform(original);
    }
    
    /**
     * Wrappes the name, when it is too long to fit into the shape and
     * also moves it outside, when the shapes height is too small.
     * 
     * @param g Graphics2D
     * @param scale The scale of the whole 2d graphic.
     * @param font the font of the text.
     * @param r the bounds of the transformed shape.
     */
    private void nameWrapp(Graphics2D g, double scale, Font font, Rectangle r){
        
        FontRenderContext context = g.getFontRenderContext();
        
        Rectangle2D bounds_r = font.getStringBounds(name, context);
        
        double width = bounds_r.getWidth();
        double height = bounds_r.getHeight();
        double xBounds = nameBoundsX*scale;
        
        if(height+(GUISettings.NAMEPOSITIONINYADD*scale) > r.height){
            nameBoundsX = GUISettings.NAMEPOSITIONOUTX;
            nameBoundsAbove = GUISettings.NAMEPOSITIONOUTY;
        }
        
        double max_width = r.width-2*xBounds;
                
        if(width > max_width){
            double percent = max_width/width*100;
            int cut = (int) Math.round(name.length()*percent/100)-3;
            if(cut <= 1){
                cut = 3;
                nameBoundsX = GUISettings.NAMEPOSITIONOUTX;
                nameBoundsAbove = GUISettings.NAMEPOSITIONOUTY;
            }
            
            if(cut < name.length())
                printName = name.substring(0,cut)+"...";
            else
                printName = name;
            nameWrapp = true;
        }else{
            printName = name;
        }
    }

    @Override
    public void setSelected(boolean select) {
        isSelected = select;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void switchSelection() {
        isSelected = !isSelected;
    }

    @Override
    public void setLocation(int x, int y, double z) {
        originalShape.setLocation(x, y);
        this.z = z;
    }

    @Override
    public void setName(String name) {
        nameWrapp = false;
        this.name = name;
    }

    @Override
    public void setTranslation(double distance_x, double distance_y) {
        int new_x = (int) Math.round(originalShape.x-distance_x);
        int new_y = (int) Math.round(originalShape.y-distance_y);
        originalShape.setLocation(new_x, new_y);
    }

    @Override
    public int getX() {
        return originalShape.x;
    }

    @Override
    public int getY() {
        return originalShape.y;
    }
    
    @Override
    public double getZ(){
        return z;
    }
    
    @Override
    public int getWidth() {
        return originalShape.width;
    }

    @Override
    public int getHeight() {
        return originalShape.height;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isNameAbbreviated() {
        return nameWrapp;
    }

    @Override
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    @Override
    public double getRotation() {
        return rotation;
    }

    @Override
    public void set(int x, int y, int width, int height) {
        originalShape.setLocation(x, y);
        originalShape.setSize(width, height);
        
    }

    @Override
    public ShapeObject clone() {
        ShapeObject shape = new ShapeRectangle(originalShape.x, originalShape.y, z,
                originalShape.width, originalShape.height, id, name, rotation, scale, imgName,
                imgDir, smi);
        return shape;
    }

    @Override
    public double getScale() {
        return scale;
    }

    @Override
    public void setScale(double scale) {
        this.scale = scale;
        nameWrapp = false;
    }
    
    @Override
    public void setImage(String imgName, String dir){
        this.imgName = imgName;
        this.imgDir = dir;
    }

    @Override
    public void setColor(Paint color) {
        this.color = color;
    }


}
