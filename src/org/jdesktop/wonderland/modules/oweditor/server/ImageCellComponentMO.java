/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.server;

import org.jdesktop.wonderland.modules.oweditor.common.ImageCellComponentServerState;
import java.awt.image.BufferedImage;
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
    
    public ImageCellComponentMO(CellMO cell) {
        super(cell);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getClientClass() {
        return "org.jdesktop.wonderland.modules.oweditor.client" +
               ".ImageCellComponent";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CellComponentClientState getClientState(
            CellComponentClientState state, WonderlandClientID clientID,
            ClientCapabilities capabilities) {

        if (state == null) {
            state = new ImageCellComponentClientState();
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
        
        if (state == null) {
            state = new ImageCellComponentServerState();
        }
        ((ImageCellComponentServerState) state).setImage(img);
        return super.getServerState(state);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setServerState(CellComponentServerState state) {
        super.setServerState(state);
        img = ((ImageCellComponentServerState) state).getImage();
    }
}
