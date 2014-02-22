/**
 * Project Wonderland
 *
 * Copyright (c) 2004-2009, Sun Microsystems, Inc., All Rights Reserved
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
package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.components;

import java.util.ResourceBundle;
import org.jdesktop.wonderland.client.cell.registry.annotation.CellComponentFactory;
import org.jdesktop.wonderland.client.cell.registry.spi.CellComponentFactorySPI;
import org.jdesktop.wonderland.common.cell.state.CellComponentServerState;
import org.jdesktop.wonderland.modules.oweditor.common.ImageCellComponentServerState;

/**
 * The Cell Component Factory for the Image Cell Component.
 * 
 * @author Jordan Slott <jslott@dev.java.net>
 */
@CellComponentFactory
public class ImageCellComponentFactory implements CellComponentFactorySPI {

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");

    /**
     * {@inheritDoc}
     */
    public String getDisplayName() {
        return BUNDLE.getString("Image_Cell_Component");
    }

    /**
     * {@inheritDoc}
     */
    public String getDescription() {
        return BUNDLE.getString("Image_Cell_Component_Description");
    }

    /**
     * {@inheritDoc}
     */
    public <T extends CellComponentServerState> T getDefaultCellComponentServerState() {
        ImageCellComponentServerState state = new ImageCellComponentServerState();
        return (T) state;
    }
}
