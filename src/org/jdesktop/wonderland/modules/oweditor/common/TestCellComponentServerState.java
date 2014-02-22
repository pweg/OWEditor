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

package org.jdesktop.wonderland.modules.oweditor.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.jdesktop.wonderland.common.cell.state.CellComponentServerState;
import org.jdesktop.wonderland.common.cell.state.annotation.ServerState;

/**
 * Server state for Test Cell Component.
 *
 * @author Jordan Slott <jslott@dev.java.net>
 */
@XmlRootElement(name="test-cell-component")
@ServerState
public class TestCellComponentServerState extends CellComponentServerState {

    // The image path
    @XmlElement(name = "image")
    private String image = null;

    /** Default constructor */
    public TestCellComponentServerState() {
    }

    @Override
    public String getServerComponentClassName() {
        return "org.jdesktop.wonderland.modules.oweditor.server" +
               ".TestCellComponentMO";
    }

    /**
     * Returns the image path.
     *
     * @return The image path
     */
    @XmlTransient
    public String getImage() {
        return image;
    }

    /**
     * Sets the image path.
     *
     * @param image The image path
     */
    public void setImage(String image) {
        this.image = image;
    }
}
