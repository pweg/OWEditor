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

import org.jdesktop.wonderland.common.cell.state.CellComponentClientState;

/**
 * Client state for Test Cell Component.
 *
 * @author Jordan Slott <jslott@dev.java.net>
 */
public class TestCellComponentClientState extends CellComponentClientState {

    // The image path
    private String image;

    /** Default constructor */
    public TestCellComponentClientState() {
    }

    /**
     * Returns the image path.
     *
     * @return The image path
     */
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

