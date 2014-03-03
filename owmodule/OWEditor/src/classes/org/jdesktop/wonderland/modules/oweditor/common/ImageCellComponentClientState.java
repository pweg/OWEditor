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
 * Client state for Image Cell Component.
 */
public class ImageCellComponentClientState extends CellComponentClientState {

    // The image path
    private String image;
    private String dir;

    /** Default constructor */
    public ImageCellComponentClientState() {
    }

    /**
     * Returns the image name.
     *
     * @return The image name.
     */
    public String getImage() {
        return image;
    }
    
    /**
     * Returns the image directory.
     * 
     * @return The directory.
     */
    public String getDir(){
        return dir;
    }

    /**
     * Sets the image name.
     *
     * @param image The image name.
     */
    public void setImage(String image) {
        this.image = image;
    }
    
    /**
     * Sets the image directory.
     * 
     * @param dir The directory
     */
    public void setDir(String dir){
        this.dir = dir;
    }
}

