package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.menu;

import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JMenuItem;

/**
 * Manages the menu entries, which means it sets 
 * menus active and deactive.
 * 
 * @author Patrick
 *
 */
public class JItemManager {

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");

    private static final boolean COPYSELECTED = true;
    private static final boolean CUTSELECTED = true;
    private static final boolean ROTATESELECTED = true;
    private static final boolean SCALESELECTED = true;
    private static final boolean PROPERTIESSELECTED = true; 
    private static final boolean TOBACKSELECTED = true; 
    
    private static final boolean PASTECOPYEXIST = true;

    private static String COPY = BUNDLE.getString("MenuCopy");
    private static String CUT = BUNDLE.getString("MenuCut");
    private static String PASTE = BUNDLE.getString("MenuPaste");
    private static String ROTATE = BUNDLE.getString("MenuRotate");
    private static String SCALE = BUNDLE.getString("MenuScale");
    private static String PROPERTIES = BUNDLE.getString("MenuProperties");
    private static String TOBACK = BUNDLE.getString("MenuToBackground");
    private static String TOFORE = BUNDLE.getString("MenuToForeground");
    
    private HashMap<String, JMenuItem> topItems;
    private HashMap<String, JMenuItem> popupItems;
    
    public JItemManager(){
        
    }
    
    public void setTopItems(HashMap<String, JMenuItem> items){
        topItems = items;
    }
    
    public void setPopupItems(HashMap<String, JMenuItem> items){
        popupItems = items;
    }

    public void setItemsEnabledSelection(boolean selected) {
        if(topItems != null){
            topItems.get(COPY).setEnabled(selected && COPYSELECTED);
            topItems.get(CUT).setEnabled(selected && CUTSELECTED);
            topItems.get(ROTATE).setEnabled(selected && ROTATESELECTED);
            topItems.get(SCALE).setEnabled(selected && SCALESELECTED);
            //topItems.get(PROPERTIES).setEnabled(selected && PROPERTIESSELECTED);
        }

        if(popupItems != null){
            popupItems.get(COPY).setEnabled(selected && COPYSELECTED);
            popupItems.get(CUT).setEnabled(selected && CUTSELECTED);
            popupItems.get(ROTATE).setEnabled(selected && ROTATESELECTED);
            popupItems.get(SCALE).setEnabled(selected && SCALESELECTED);
            popupItems.get(PROPERTIES).setEnabled(selected && PROPERTIESSELECTED);
            popupItems.get(TOBACK).setEnabled(selected && TOBACKSELECTED);
        }
    }
    
    public void setItemsEnabledCopy(boolean copyShapesExist){
        if(topItems != null){
            
            topItems.get(PASTE).setEnabled(copyShapesExist && PASTECOPYEXIST);
        }

        if(popupItems != null){            
            popupItems.get(PASTE).setEnabled(copyShapesExist && PASTECOPYEXIST);
        }
    }
    
    public void setToBackgroundVisible(boolean b){
        if(popupItems != null){
            popupItems.get(TOBACK).setVisible(b);
            popupItems.get(TOFORE).setVisible(!b);
        }
    }

}
