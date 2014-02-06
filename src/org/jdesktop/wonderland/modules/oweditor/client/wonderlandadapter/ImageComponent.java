/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import java.awt.image.BufferedImage;
//import org.jdesktop.wonderland.common.cell.ClientCapabilities;
//import org.jdesktop.wonderland.server.comms.WonderlandClientID;

/**
 *
 * @author Patrick
 */

//@DependsOnCellComponentMO(TooltipCellComponentMO.class)
public class ImageComponent //extends CellMO
{
    
    private BufferedImage image;
    
    public ImageComponent(){
       
    }
    
    public void setImage(BufferedImage image){
         this.image = image;
    }
    
    public BufferedImage getImage(){
        return image;
    }

    //@Override
    /*protected String getClientCellClassName(WonderlandClientID clientID, ClientCapabilities capabilities) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    
}
