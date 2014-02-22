/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.server;

import org.jdesktop.wonderland.modules.oweditor.common.ImageCellComponentServerState;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;
import org.jdesktop.wonderland.common.cell.ClientCapabilities;
import org.jdesktop.wonderland.common.cell.state.CellComponentClientState;
import org.jdesktop.wonderland.common.cell.state.CellComponentServerState;
import org.jdesktop.wonderland.modules.oweditor.common.ImageCellComponentClientState;
import org.jdesktop.wonderland.modules.oweditor.common.ImageTranslator;
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
               ".wonderlandadapter.components.ImageCellComponent";
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
        if(img != null && state != null){
           String imgStr = ImageTranslator.ImageToString(img);
           ((ImageCellComponentServerState) state).setImage(imgStr);
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
        img = ImageTranslator.StringToImage(imgStr);
    }
    
}
