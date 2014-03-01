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
@XmlRootElement(name="image-cell-component")
@ServerState
public class ImageCellComponentServerState extends CellComponentServerState {

    // The image path
    @XmlElement(name = "image")
    private String image = null;
    
    @XmlElement(name = "directory")
    private String dir = null;

    /** Default constructor */
    public ImageCellComponentServerState() {
    }

    @Override
    public String getServerComponentClassName() {
        return "org.jdesktop.wonderland.modules.oweditor.server" +
               ".ImageCellComponentMO";
    }

    /**
     * Returns the image name.
     *
     * @return The image name
     */
    @XmlTransient
    public String getImage() {
        return image;
    }
    
    /**
     * Returns the image directory.
     * 
     * @return The directory 
     */
    @XmlTransient
    public String getDir(){
        return dir;
    }

    /**
     * Sets the image name.
     *
     * @param image The image name
     */
    public void setImage(String image) {
        this.image = image;
    }
    
    /**
     * Sets the image directory.
     * 
     * @param dir The directory.
     */
    public void setDir(String dir){
        this.dir = dir;
    }
}
