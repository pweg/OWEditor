package org.jdesktop.wonderland.modules.oweditor.client.adapter;
import java.awt.*;  
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.font.*;  
import java.awt.geom.*;  
import javax.swing.*;  
import javax.swing.event.*;  
   
public class zoom2 extends JPanel implements ChangeListener {  
    RenderingHints hints;  
    Shape[] shapes;  
    Dimension size;  
    double scale = 1.0; 
    
    PanListener pan = null;
   
    public zoom2() {  
        hints = new RenderingHints(null);  
        hints.put(RenderingHints.KEY_ANTIALIASING,  
                  RenderingHints.VALUE_ANTIALIAS_ON);  
        hints.put(RenderingHints.KEY_TEXT_ANTIALIASING,  
                  RenderingHints.VALUE_TEXT_ANTIALIAS_ON);  
        hints.put(RenderingHints.KEY_FRACTIONALMETRICS,  
                  RenderingHints.VALUE_FRACTIONALMETRICS_ON);  
        size = new Dimension(10,10);  
        setBackground(new Color(240,200,200)); 
        pan = new PanListener(this);
        this.addMouseListener(pan);
        this.addMouseMotionListener(pan);
        this. addMouseWheelListener( new MouseWheelListener( )
        {
            @Override
            public void mouseWheelMoved( MouseWheelEvent e )
            {
                
                int notches = e.getWheelRotation();
                if (notches < 0)
                    scale += 0.1;
                else{
                    if(scale > 0)
                        scale -=0.1;
                };
                repaint();  
                revalidate(); 
            }
        });
    }  
   
    public void stateChanged(ChangeEvent e) {  
        int value = ((JSlider)e.getSource()).getValue();  
        scale = value/100.0;  
        repaint();  
        revalidate();  
    }  
   
    protected void paintComponent(Graphics g) {  
        super.paintComponent(g);  
        Graphics2D g2 = (Graphics2D)g;  
        g2.setRenderingHints(hints);  
        if(shapes == null) initShapes();  
        // Keep shapes centered on panel.  
        double x = (getWidth() - scale*size.width)/2;  
        double y = (getHeight() - scale*size.height)/2;  
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);  
        at.scale(scale, scale);  
        g2.setPaint(Color.blue);  
        g2.draw(at.createTransformedShape(shapes[0]));  
        g2.setPaint(Color.green.darker());  
        g2.draw(at.createTransformedShape(shapes[1]));  
        g2.setPaint(new Color(240,240,200));  
        g2.fill(at.createTransformedShape(shapes[2]));  
        g2.setPaint(Color.red);  
        g2.draw(at.createTransformedShape(shapes[2]));  
        // Draw some text.  
        String s = "Hello World";  
        Font font = g2.getFont().deriveFont(18f);  
        g2.setFont(font);  
        FontRenderContext frc = g2.getFontRenderContext();  
        float width = (float)font.getStringBounds(s, frc).getWidth();  
        LineMetrics lm = font.getLineMetrics(s, frc);  
        float height = lm.getHeight();  
        float descent = lm.getDescent();  
        // Translate to origin of centered, scaled text.  
        int w = getWidth();  
        int h = getHeight();  
        x = (w - scale*width)/2;  
        y = (h + scale*height)/2 - scale*descent;  
        // We have finished using at above for the spatial transform  
        // work so we can refit/resue it for the text rendering.  
        at.setToTranslation(x, y);  
        // Scale text in place (we have already adjusted the origin  
        // for the scaling portion).  
        at.scale(scale, scale);  
        // Rotate text to align with sw - ne diagonal.  
        // Determine the angle of rotation.  
        Rectangle2D bounds = shapes[0].getBounds2D();  
        double dy = -bounds.getHeight();  // looking up (-) toward this origin  
        double dx = bounds.getWidth();  
        double theta = Math.atan2(dy, dx);  
        // In java, positive angles are measured clockwise from 3 o'clock  
        // except in arc measure which is reversed.  
        //System.out.printf("theta = %.1f%n", Math.toDegrees(theta));  
        // Make up another transform that rotates theta radians about  
        // the center of the text bounding rectangle. Multiply this  
        // new transform by the translation/scaling transform.  
        // Since the text is centered on this component we can use  
        // its center coordinates for the rotation origin.  
        // Otherwise, we would locate the center of rotation as  
        x += scale*width/2;  
        y = y + scale*descent - scale*height/2;  
        System.out.printf("x = %.1f\ty = %.1f\tw/2 = %d\th/2 = %d%n", x, y, w/2, h/2);  
        AffineTransform xfRotate = AffineTransform.getRotateInstance(theta, w/2, h/2);  
        xfRotate.concatenate(at);    // The order is important (matrix math).  
        g2.setFont(g2.getFont().deriveFont(xfRotate));  
        // Since we did all the work in the transform, draw text at (0,0)  
        g2.drawString(s, 0, 0);  
   }  
   
    public Dimension getPreferredSize() {  
        int w = (int)(scale*size.width);  
        int h = (int)(scale*size.height);  
        return new Dimension(w, h);  
    }  
   
    private void initShapes() {  
        shapes = new Shape[3];  
        int w = getWidth();  
        int h = getHeight();  
        shapes[0] = new Rectangle2D.Double(w/16, h/16, w*7/8, h*7/8);  
        shapes[1] = new Line2D.Double(w/16, h*15/16, w*15/16, h/16);  
        shapes[2] = new Ellipse2D.Double(w/4, h/4, w/2, h/2);  
        size.width = w;  
        size.height = h;  
    }  
   
    private JSlider getControl() {  
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 50, 200, 100);  
        slider.setMajorTickSpacing(50);  
        slider.setMinorTickSpacing(10);  
        slider.setPaintTicks(true);  
        slider.setPaintLabels(true);  
        slider.addChangeListener(this);  
       
        return slider;   
    }  
   
    public static void main(String[] args) {  
        zoom2 app = new zoom2();  
        JFrame f = new JFrame();  
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        f.getContentPane().add(new JScrollPane(app));  
        //f.getContentPane().add(app.getControl(), "Last");  
        f.setSize(400, 400);  
        f.setLocation(200,200);  
        f.setVisible(true);  
    }  
}  