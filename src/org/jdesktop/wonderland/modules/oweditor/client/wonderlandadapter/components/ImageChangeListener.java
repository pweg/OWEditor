/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.components;

import java.awt.image.BufferedImage;
import org.jdesktop.wonderland.client.cell.Cell;

/**
 *
 * @author Patrick
 */
public interface ImageChangeListener{

    public void imageChanged(BufferedImage img, Cell cell);
    
}
