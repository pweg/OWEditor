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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Patrick
 */
public class ImageTranslator {
    
    public static final String ImageToString(BufferedImage img){
        if(img != null){
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(img, "png", baos);
                baos.flush();
                byte[] bimg = baos.toByteArray();
                baos.close();
                return DatatypeConverter.printBase64Binary(bimg);
             } catch (IOException ex) {
                 Logger.getLogger(ImageCellComponentServerState.class.getName()).log(Level.SEVERE, null, ex);
             } catch (Exception e){
                 Logger.getLogger(ImageCellComponentServerState.class.getName()).log(Level.SEVERE, "Something went wrong",e);
             }
        }
        
        return "";
    }
    
    public static final BufferedImage StringToImage(String string){
        
        if(string == null || string.equals("")){
            return null;
        }
        
        byte[] bimg = DatatypeConverter.parseBase64Binary(string);
        
        if(bimg != null){
            try {
               String b = "";
               InputStream in = new ByteArrayInputStream(bimg);
               BufferedImage img = ImageIO.read(in);
               in.close();
               return img;
            } catch (IOException ex) {
                Logger.getLogger(ImageCellComponentServerState.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
}
