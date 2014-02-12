/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.common;

import java.awt.image.BufferedImage;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.jdesktop.wonderland.common.cell.state.CellComponentServerState;
import org.jdesktop.wonderland.common.cell.state.annotation.ServerState;

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
    
    @XmlElement(name = "img")
    private BufferedImage img = null;

    /** Default constructor is needed for JAXB */
    public ImageCellComponentServerState() {
    }

    @Override
    public String getServerComponentClassName() {
        return "org.jdesktop.wonderland.modules.tooltip.server.ImageCellComponentMO";
    }
    
    @XmlTransient
    public void setImage(BufferedImage img){
        this.img = img;
    }
    
    @XmlTransient
    public BufferedImage getImage(){
        return img;
    }

    /*@XmlTransient public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    @XmlTransient public int getTimeout() { return timeout; }
    public void setTimeout(int timeout) { this.timeout = timeout; }*/
}