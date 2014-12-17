/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.components;

import java.util.ResourceBundle;
import org.jdesktop.wonderland.client.cell.registry.annotation.CellComponentFactory;
import org.jdesktop.wonderland.client.cell.registry.spi.CellComponentFactorySPI;
import org.jdesktop.wonderland.common.cell.state.CellComponentServerState;
import org.jdesktop.wonderland.modules.oweditor.common.IDCellComponentServerState;

/**
 *The id cell component factory.
 * 
 * @author Patrick
 */
@CellComponentFactory
public class IDCellComponentFactory implements CellComponentFactorySPI {

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");

    /**
     * {@inheritDoc}
     */
    public String getDisplayName() {
        return BUNDLE.getString("ID_Cell_Component");
    }

    /**
     * {@inheritDoc}
     * @param <T>
     */
    public <T extends CellComponentServerState> T getDefaultCellComponentServerState() {
        IDCellComponentServerState state = new IDCellComponentServerState();
        return (T) state;
    }

    /**
     * {@inheritDoc}
     */
    public String getDescription() {
        return BUNDLE.getString("ID_Cell_Component_Description");
    }
}

