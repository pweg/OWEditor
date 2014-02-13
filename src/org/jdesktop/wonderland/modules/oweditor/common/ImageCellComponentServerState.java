/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.common;

import java.awt.image.BufferedImage;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.jdesktop.wonderland.common.cell.state.CellComponentServerState;
import org.jdesktop.wonderland.common.cell.state.annotation.ServerState;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.GUIEventManager;

/**
 *
 * @author Patrick
 */
@XmlRootElement(name="image-cell-component")
@ServerState
public class ImageCellComponentServerState extends CellComponentServerState {

    /*@XmlElement(name = "text")
    private String text = null;

    @XmlElement(name = "timeout")
    private int timeout = -1;*/
    
     private static final Logger LOGGER =
            Logger.getLogger(ImageCellComponentServerState.class.getName());
    

    //@XmlElement(name = "img")
    private BufferedImage img = null;

    /** Default constructor is needed for JAXB */
    public ImageCellComponentServerState() {
    }

    @Override
    public String getServerComponentClassName() {
        return "org.jdesktop.wonderland.modules.oweditor.server.ImageCellComponentMO";
    }
    
    public void setImage(BufferedImage img){
        LOGGER.warning("SERVERSTATE set image IN STATE");
        this.img = img;
    }
    
    //@XmlTransient
    public BufferedImage getImage(){
        LOGGER.warning("SERVERSTATE GET IMAGE");
        return img;
    }

    /*@XmlTransient public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    @XmlTransient public int getTimeout() { return timeout; }
    public void setTimeout(int timeout) { this.timeout = timeout; }*/
}