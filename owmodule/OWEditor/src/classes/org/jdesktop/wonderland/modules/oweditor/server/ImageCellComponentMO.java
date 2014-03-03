/**
 * Project Wonderland
 *
 * Copyright (c) 2004-2010, Sun Microsystems, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * Sun designates this particular file as subject to the "Classpath"
 * exception as provided by Sun in the License file that accompanied
 * this code.
 */
package org.jdesktop.wonderland.modules.oweditor.server;

import org.jdesktop.wonderland.common.cell.ClientCapabilities;
import org.jdesktop.wonderland.common.cell.state.CellComponentClientState;
import org.jdesktop.wonderland.common.cell.state.CellComponentServerState;
import org.jdesktop.wonderland.modules.oweditor.common.ImageCellComponentClientState;
import org.jdesktop.wonderland.modules.oweditor.common.ImageCellComponentServerState;
import org.jdesktop.wonderland.server.cell.CellComponentMO;
import org.jdesktop.wonderland.server.cell.CellMO;
import org.jdesktop.wonderland.server.comms.WonderlandClientID;

/**
 * The server-side Image Cell Component.
 *
 */
public class ImageCellComponentMO extends CellComponentMO {

    // The image
    private String image = null;
    private String dir = null;

    
    /**
     * Constructor, takes the CellMO associated with the Cell Component.
     *
     * @param cell The CellMO associated with this component
     */
    public ImageCellComponentMO(CellMO cell) {
        super(cell);
    }

    /**
     * {@inheritDoc}
     * @return The client class
     */
    @Override
    protected String getClientClass() {
        return "org.jdesktop.wonderland.modules.oweditor.client" +
               ".wonderlandadapter.components.ImageCellComponent";
    }

    /**
     * {@inheritDoc}
     * @return ClientState
     */
    @Override
    public CellComponentClientState getClientState(
            CellComponentClientState state, WonderlandClientID clientID,
            ClientCapabilities capabilities) {

        if (state == null) {
            state = new ImageCellComponentClientState();
        }
        ((ImageCellComponentClientState) state).setImage(image);
        ((ImageCellComponentClientState) state).setDir(dir);
        return super.getClientState(state, clientID, capabilities);
    }

    /**
     * {@inheritDoc}
     * @return ServerState
     */
    @Override
    public CellComponentServerState getServerState(
            CellComponentServerState state) {
        
        if (state == null) {
            state = new ImageCellComponentServerState();
        }
        ((ImageCellComponentServerState) state).setImage(image);
        ((ImageCellComponentServerState) state).setDir(dir);
        return super.getServerState(state);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setServerState(CellComponentServerState state) {
        super.setServerState(state);
        image = ((ImageCellComponentServerState) state).getImage();
        dir = ((ImageCellComponentServerState) state).getDir();
    }
}
