/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.oweditor.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import org.jdesktop.wonderland.client.BaseClientPlugin;
import org.jdesktop.wonderland.client.jme.JmeClientMain;
import org.jdesktop.wonderland.client.login.ServerSessionManager;
import org.jdesktop.wonderland.common.annotation.Plugin;
import org.jdesktop.wonderland.modules.oweditor.client.editor.controler.MainControler;
import org.jdesktop.wonderland.modules.oweditor.client.editor.controlerinterfaces.MainControlerAdapterInterface;



@Plugin
public class OWEditorClientPlugin extends BaseClientPlugin {
    
    private JMenuItem myMenuItem = null;
    private MainControlerAdapterInterface main = null;
    
    @Override
    public void initialize(ServerSessionManager loginInfo) {
        
        
        myMenuItem = new JMenuItem("OW-Editor");
        myMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(main == null)
                    main = new MainControler();
                main.setVisible(true);
               
            }
        });
        super.initialize(loginInfo);
    }
    

    @Override
    protected void activate() {
        JmeClientMain.getFrame().addToToolsMenu(myMenuItem);
    }

    @Override
    protected void deactivate() {
        JmeClientMain.getFrame().removeFromToolsMenu(myMenuItem);
    }

    @Override
    public void cleanup() {
        myMenuItem = null;
        main = null;
        super.cleanup();
    }

}
