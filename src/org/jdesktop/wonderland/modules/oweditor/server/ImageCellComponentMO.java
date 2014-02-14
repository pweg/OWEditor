/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.server;

import org.jdesktop.wonderland.modules.oweditor.common.ImageCellComponentServerState;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import org.jdesktop.wonderland.common.cell.ClientCapabilities;
import org.jdesktop.wonderland.common.cell.state.CellComponentClientState;
import org.jdesktop.wonderland.common.cell.state.CellComponentServerState;
import org.jdesktop.wonderland.modules.oweditor.common.ImageCellComponentClientState;
import org.jdesktop.wonderland.server.cell.CellComponentMO;
import org.jdesktop.wonderland.server.cell.CellMO;
import org.jdesktop.wonderland.server.comms.WonderlandClientID;

/**
 *
 * @author Patrick
 */
public class ImageCellComponentMO extends CellComponentMO {

    private BufferedImage img = null;
    
     private static final Logger LOGGER =
            Logger.getLogger(ImageCellComponentServerState.class.getName());
    
    public ImageCellComponentMO(CellMO cell) {
        super(cell);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getClientClass() {
        return "org.jdesktop.wonderland.modules.oweditor.client" +
               ".wonderlandadapter.imagecomponent.ImageCellComponent";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CellComponentClientState getClientState(
            CellComponentClientState state, WonderlandClientID clientID,
            ClientCapabilities capabilities) {

            LOGGER.warning("MO GETCLIENTSTATE");
        if (state == null) {
            state = new ImageCellComponentClientState();
            LOGGER.warning("client state created");
        }
        ((ImageCellComponentClientState) state).setImage(img);
        return super.getClientState(state, clientID, capabilities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CellComponentServerState getServerState(
            CellComponentServerState state) {
        
            LOGGER.warning("MO GETSERVERSTATE");
        if (state == null) {
            LOGGER.warning("MO No serverstate1");
            state = new ImageCellComponentServerState();
            LOGGER.warning("MO No serverstate2");
        }
        if(img != null){
            try {
                LOGGER.warning("MO transform1");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                LOGGER.warning("MO transform2");
                ImageIO.write(img, "jpg", baos);
                baos.flush();
                LOGGER.warning("MO transform3");
                byte[] bimg = baos.toByteArray();
                
                LOGGER.warning("MO transform4");
                baos.close();
                ((ImageCellComponentServerState) state).setImage(
                        DatatypeConverter.printBase64Binary(bimg));
             } catch (IOException ex) {
                 Logger.getLogger(ImageCellComponentServerState.class.getName()).log(Level.SEVERE, null, ex);
             } catch (Exception e){
                 Logger.getLogger(ImageCellComponentServerState.class.getName()).log(Level.SEVERE, "Something went wrong",e);
             }
        }
        LOGGER.warning("MO GETSERVERSTATE 2");
        return super.getServerState(state);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setServerState(CellComponentServerState state) {
        LOGGER.warning("MO set serverstate");
        super.setServerState(state);
        
        String imgStr = ((ImageCellComponentServerState) state).getImage();
        
        if(imgStr == null || imgStr.equals("")){
            img = null;
            return;
        }
        
        byte[] bimg = DatatypeConverter.parseBase64Binary(imgStr);
        if(bimg != null){
            try {
               String b = "";
               InputStream in = new ByteArrayInputStream(bimg);
               img = ImageIO.read(in);
               in.close();
            } catch (IOException ex) {
                Logger.getLogger(ImageCellComponentServerState.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
