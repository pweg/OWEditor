package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.util.ArrayList;

import javax.swing.JScrollPane;

import org.jdesktop.wonderland.modules.oweditor.client.adapter.ClientUpdateInterface;
import org.jdesktop.wonderland.modules.oweditor.client.data.DataObjectManagerInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.controler.MainControlerGUIInterface;

public class GUIControler implements GUIControlerInterface{

    private WindowFrame frame = null;
    private WindowDrawingPanel drawingPan = null;
    private JScrollPane mainScrollPanel = null;
    
    
    private ShapeManager sm = null;
    private GUISelectAndMoveManager samm = null;
    private MainControlerGUIInterface mc = null;
    public ClientUpdateInterface cui = null;
    public DataObjectManagerInterface dmi = null;
    
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
        //drawingPan.setOpaque(true);
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
           
           int id = shape.getID();
           cui.updateTranslation(id, shape.getX(), shape.getY(), dmi.getZ(id));
       }
        
    }

    @Override
    public void getDataUpdate(int id) {
        sm.getDataUpdate(id);
        drawingPan.repaint();
        
    }

    @Override
    public void setDataManager(DataObjectManagerInterface dm) {
        dmi = dm;
        sm.setDataManager(dm);
        
    }


    @Override
    public void setClientUpdateAdapter(ClientUpdateInterface cui) {
        this.cui = cui;
        
    }

    

}
