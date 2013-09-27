package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.util.ArrayList;

import javax.swing.JScrollPane;

import org.jdesktop.wonderland.modules.oweditor.client.data.DataManagerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.controler.MainControlerGUIInterface;

public class GUIControler implements GUIControlerInterface{

    private WindowFrame frame = null;
    private WindowDrawingPanel drawingPan = null;
    private JScrollPane mainScrollPanel = null;
    
    
    private ShapeManager sm = null;
    private GUISelectAndMoveManager samm = null;
    private MainControlerGUIInterface mc = null;
    
    public GUIControler(MainControlerGUIInterface mc){
        this.mc = mc;
    }
    
    
    @Override
    public void createFrame() {
        if(frame == null){
            frame = new WindowFrame();
            initiallize();
        }
        
        
    }
    
    private void initiallize(){

        sm = new ShapeManager();
        drawingPan = new WindowDrawingPanel(sm);
        samm = new GUISelectAndMoveManager(this);
       
        mainScrollPanel = new JScrollPane(drawingPan);
        frame.getContentPane().add(mainScrollPanel);
        
    }

    @Override
    public void setVisibility(boolean visibility) {
        frame.setVisible(visibility);
        
        
    }
    
    
    public WindowFrame getFrame(){
        return frame;
    }
    
    public ShapeManager getShapeManager(){
        return sm;
    }
    
    public WindowDrawingPanel getDrawingPanel(){
        return drawingPan;
    }
    
    public GUISelectAndMoveManager getSelectManager(){
        return samm;
    }

    public void setAdapterUpdate() {
       ArrayList<ShapeObject> list = sm.getUpdateShapes();
       if(list.isEmpty())
           return;
       
       for(ShapeObject shape : list){
           
           mc.setAdapterTranslationUpdate(shape.getID(), shape.getX(), shape.getY());
       }
        
    }

    @Override
    public void getAdapterUpdate(int id) {
        sm.getAdapterUpdate(id);
        drawingPan.repaint();
        
    }

    @Override
    public void setDataManager(DataManagerInterface dm) {
        sm.setDataManager(dm);
        
    }

    

}
