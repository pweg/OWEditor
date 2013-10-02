package org.jdesktop.wonderland.modules.oweditor.client.adapter;

import com.jme.math.Vector3f;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.ClientContext;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellCache;
import org.jdesktop.wonderland.client.comms.WonderlandSession;
import org.jdesktop.wonderland.client.login.LoginManager;
import org.jdesktop.wonderland.common.cell.CellTransform;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.ShapeObjectRectangle;

public class AdapterWorldBuilder {
    
    private ServerUpdateAdapter sua = null;
    private ArrayList<Cell> cells = null;
    
    public AdapterWorldBuilder(ServerUpdateAdapter sua){
        this.sua = sua;
        cells = new ArrayList<Cell>();
    }
    
    public void build(){
        initShapes();
        
        WonderlandSession session = LoginManager.getPrimary().getPrimarySession();
        
        CellCache cache = ClientContext.getCellCache(session);
        if (cache == null) {
            //LOGGER.warning("Unable to find Cell cache for session " + session);
            return;
        }
        String s ="";
        Collection<Cell> rootCells = cache.getRootCells();
        for (Cell rootCell : rootCells) {
            //s=s+"\n"+ rootCell.getName();
            //cells.add(rootCell);
            s=iterateChilds(rootCell, s, false);
        }
        
        for(Cell cell : cells){
            createDataObject(cell);
            
        }
        
    }
    
    private String iterateChilds(Cell cell, String s, boolean b){
        
        Collection<Cell> childs = cell.getChildren();
        String name = "";
        if(b == true)
            name = "CHILD__";
        s= s+"\n "+name+cell.getName();
        cells.add(cell);
   
        for(Cell child : childs)
        {
            iterateChilds(child,s,true);
        }
        return s;
        
    }
    
    private void createDataObject(Cell cell){
        
        CellTransform transform = cell.getLocalTransform();
        Vector3f transl = transform.getTranslation(null);
        
        
        //cell.getWorldBounds().;
        
        int x = (int) Math.round(transl.x);
        int y = (int) Math.round(transl.y);
        int z = (int) Math.round(transl.z);
        long id = Long.valueOf(cell.getCellID().toString());
        String name = cell.getName();
        float rotation = 0;//transform.getRotation(null);
        float scale = transform.getScaling();
        
        
        
        
        sua.createObject(id, x, y, z, rotation, scale, 10, 10, name);
    }
    
    private void initShapes() {  

        //sua.createObject(0, 160, 160, 0, 0, 1, 70, 70, "Chair");
        //sua.createObject(2, 260, 260, 0, 0, 1, 80, 80, "ChairofTestinghugelength");
        //sua.createObject(1, 400, 400, 0, 0, 1, 200, 200, "Desk");
       
    }
    

}
