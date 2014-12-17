package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ResourceBundle;

import javax.swing.JButton;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUISettings;

/**
 * This class implements a button, which shows an image that could be set.
 * 
 * @author Patrick
 *
 */
public class ImageButton extends JButton{
    
    private static final long serialVersionUID = 1001;
    private static final int MARGIN = GUISettings.IMGMARGINPANEL;
    
    private BufferedImage img = null;

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    
    public ImageButton(){
        super(BUNDLE.getString("NoImage"));
        setContentAreaFilled(false);
    }
    
    /**
     * Sets the image of the button.
     * 
     * @param img The image.
     */
    public void setImage(BufferedImage img){
        this.img = img;
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        if(img != null){
            
            //Calculate the right width proportional to the panel
            int iwidth = img.getWidth();
            int iheight= img.getHeight();
            
            int width = this.getWidth()-MARGIN*2;
            int height = this.getHeight()-MARGIN*2;
            
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
            
            int x = (int) Math.round((width-iwidth)/2);
            int y = (int) Math.round((height-iheight)/2);
            
            x=Math.max(x+MARGIN, 0);
            y=Math.max(y+MARGIN, 0);
            
            g.drawImage(img,x,y,iwidth, iheight, null);
        }
        if(this.model.isPressed()){
            g.setColor(GUISettings.IMAGESELECT);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
    
}
