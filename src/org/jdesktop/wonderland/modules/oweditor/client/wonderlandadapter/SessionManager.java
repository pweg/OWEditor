/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import org.jdesktop.wonderland.client.ClientContext;
import org.jdesktop.wonderland.client.cell.CellCache;
import org.jdesktop.wonderland.client.comms.WonderlandSession;
import org.jdesktop.wonderland.client.login.LoginManager;

/**
 * This class stores the current session and can be used to get
 * instances linked to the session, like cellCache.
 * 
 * @author Patrick
 */
public class SessionManager {
    
    private WonderlandSession session = null;
    
    public SessionManager(){
        session = LoginManager.getPrimary().getPrimarySession();
    }
    
    /**
     * Returns the current wonderland session.
     * 
     * @return The wonderland session.
     */
    public WonderlandSession getSession(){
        return session;
    }
    
    /**
     * Returns the cell cache of the current session.
     * 
     * @return The cellCache instance.
     */
    public CellCache getCellCache(){
        return ClientContext.getCellCache(session);
    }
    
}
