/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.editor.controllerinterfaces;


/**
 * This is only used for the EditorClientPlugin, which is in the client
 * package. This interface allows the client plugin to control the
 * visibility of the gui component.
 *
 * @author Patrick
 */
public interface MainControllerPluginInterface {
    
    public static final byte DUMMYADAPTER = 0;
    public static final byte WONDERLAND = 1;
    
    public void initialize(byte adaptertype);

    
    /**
     * Sets the visibility of the gui component.
     * 
     * @param visibility true, if the gui should be seen,
     *         false otherwise.
     */    
    public void setVisible(boolean visibility);
    
}
