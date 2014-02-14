/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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
    
     private static final Logger LOGGER =
            Logger.getLogger(ImageCellComponentServerState.class.getName());
    

    @XmlElement(name = "rep_image")
    private String img;

    /** Default constructor is needed for JAXB */
    public ImageCellComponentServerState() {
        img = "";
    }

    @Override
    public String getServerComponentClassName() {
        return "org.jdesktop.wonderland.modules.oweditor.server.ImageCellComponentMO";
    }
    
    public void setImage(String img){
        LOGGER.warning("SERVERSTATE set image IN STATE");
        this.img = img;
        if(img != null)
            LOGGER.warning("SERVERVSTATE set image not null");
    }
    
    @XmlTransient
    public String getImage(){
        LOGGER.warning("SERVERSTATE GET IMAGE");
        return img;
    }
    
    /*public static class RepImage implements Serializable {

        /* The x dimension or radius of the bounds *
        public BufferedImage img = null;

        /** Default constructor *
        public RepImage() {
        }

        public RepImage(BufferedImage img) {
            this.img = img;
        }
        
        public void writeObject(ObjectOutputStream out) throws IOException {
            out.defaultWriteObject();
            if(img != null)
                ImageIO.write(img, "png", out); // png is lossless
        }

        public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();
            img = ImageIO.read(in);
        }
    }*/

    /*@XmlTransient public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    @XmlTransient public int getTimeout() { return timeout; }
    public void setTimeout(int timeout) { this.timeout = timeout; }*/
}