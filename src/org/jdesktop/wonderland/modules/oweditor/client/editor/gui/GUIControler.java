package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import javax.swing.JScrollPane;

public class GUIControler implements GUIInterface{

    private WindowFrame frame = null;
    private WindowDrawingPanel drawingPan = null;
    private JScrollPane mainScrollPanel = null;
    
    
    private ShapeManager shapeManager = null;
    private GUISelectAndMoveManager selectManager = null;
    
    
    @Override
    public void createFrame() {
        if(frame == null){
            frame = new WindowFrame();
            initiallize();
        }
        
        
    }
    
    private void initiallize(){

        shapeManager = new ShapeManager();
        drawingPan = new WindowDrawingPanel(shapeManager);
        selectManager = new GUISelectAndMoveManager(this);
        
        
        
        
       
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
        return shapeManager;
    }
    
    public WindowDrawingPanel getDrawingPanel(){
        return drawingPan;
    }
    
    public GUISelectAndMoveManager getSelectManager(){
        return selectManager;
    }
    

}
