package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class ImagePanel extends JPanel{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private BufferedImage img = null;
    
    public ImagePanel(){
        super();
    }
    
    public void setImage(BufferedImage img){
        this.img = img;
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        if(img != null){
            
            int iwidth = img.getWidth();
            int iheight= img.getHeight();
            
            int width = this.getWidth()-4;
            int height = this.getHeight()-4;
            
            double scale_w = (double)width/iwidth;
            double scale_h = (double)height/iheight;

            int iheight_s = (int) Math.round(iheight * scale_w);
            int iwidth_s = (int) Math.round(iwidth * scale_h);
            
            
            if(iheight_s <= height){
                iwidth=width;
                iheight=iheight_s;
                System.out.println("width");
            }else if(iwidth_s <= width){
                iwidth=iwidth_s;
                iheight=height;
                System.out.println("height");
            }else{
                iwidth= width;
                iheight=height;
                System.out.println("else");
            }
            
            int x = (int) Math.round((width-iwidth)/2);
            int y = (int) Math.round((height-iheight)/2);
            
            x=Math.max(x+2, 0);
            y=Math.max(y+2, 0);
            
            g.drawImage(img,x,y,iwidth, iheight, null);
        }
    }
    
}
