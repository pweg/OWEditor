package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.ITransformedObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindowToGraphic;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.DraggingObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.ShapeObject;

/**
 * The graphic controller, which creates the graphics classes.
 * It also implements the window interface.
 * 
 * @author Patrick
 *
 */
public class GraphicController implements IGraphicToWindow{


    private IInternalMediator med = null;
    private ShapeManager sm = null;
    //protected TransformationManager strm = null;
    //protected TranslationManager stm = null;
    private SelectionManager ssm = null;
    private CopyManager scm = null;
    private ServerTransformationManager stm = null;
    
    private GraphicToInputFacade inputInterface = null;
    
    public GraphicController(){
        
        med = new InternalMediator();
        
        sm = new ShapeManager(med);
        stm = new ServerTransformationManager(med);
        ssm = new SelectionManager(med);
        scm = new CopyManager(med);
        
        //frameInterface = new ExternalShapeToFrame(sm);
        inputInterface = new GraphicToInputFacade();
        
        registerComponents();
    }
    
    private void registerComponents(){
        
        med.registerSelectionManager(ssm);
        med.registerShapeManager(sm);
        
        inputInterface.registerCopyManager(scm);
        inputInterface.registerSelectionManager(ssm);
        inputInterface.registerShapeManager(sm);;
        inputInterface.registerEditorTransformManager(
                new EditorTransformationManager(med));
    }


    @Override
    public void drawShapes(Graphics2D g2, AffineTransform at) {
        sm.drawShapes(g2, at);
    }


    @Override
    public void createShape(ITransformedObject dataObject) {
        if(sm.setDataUpdate(dataObject))
            stm.lateTransform(dataObject.getID());
    }

    @Override
    public void createDraggingRect(int width, int height, int x, int y,
            double rotation, double scale) {
        sm.createDraggingRect(width, height, x, y, rotation, scale);
    }

    @Override
    public void createDraggingCircle(int width, int height, int x, int y,
            double rotation, double scale) {
        sm.createDraggingEllipse(width, height, x, y, rotation, scale);
    }

    @Override
    public void removeShape(long id) {
        sm.removeShape(id);
    }

    @Override
    public void updateShapeCoordinates(long id, int x, int y, double z) {
        stm.translateShape(id, x, y, z);
    }

    @Override
    public void updateShapeName(long id, String name) {
        sm.renameShape(id,name);
    }


    @Override
    public void updateShapeRotation(long id, double rotation) {
        stm.setRotation(id, rotation);
        
    }

    @Override
    public void updateShapeScale(long id, double scale) {
        stm.setScale(id, scale);
    }


    @Override
    public void registerWindowInterface(IWindowToGraphic window) {
        med.registerWindowInterface(window);
        inputInterface.registerWindowInterface(window);
    }

    @Override
    public IGraphicToWindow getFrameInterface() {
        return this;
    }

    @Override
    public IGraphicToInput getInputInterface() {
        return inputInterface;
    }

    /*
     * This function can theoretically be used for 
     * all dragging shapes, when making an array of points,
     * but currently there is no need for it.
     */
    @Override
    public Point getDraggingCoords() {
        ArrayList<DraggingObject> shapes = sm.getDraggingShapes();
        int x = Integer.MAX_VALUE;
        int y = Integer.MAX_VALUE;
        
        for(DraggingObject shape : shapes){
            shape.setState(null);
            int sx = shape.getX();
            int sy = shape.getY();

            x = Math.min(sx, x);
            y = Math.min(sy, y);
        }
        sm.clearDraggingShapes();
        
        return new Point(x,y);
    }

    @Override
    public void updateShapeImage(long id, String imgName, String dir) {
        ShapeObject shape = sm.getShape(id);
        
        if(shape != null)
            shape.setImage(imgName, dir);
    }

    @Override
    public void selectAllShapes() {
        ArrayList<ShapeObject> shapes = sm.getShapes();
        for(ShapeObject shape : shapes)
            ssm.setSelected(shape, true);
    }

    @Override
    public void setBackground(boolean b) {
        ArrayList<ShapeObject> shapes = ssm.getSelection();
        
        for(ShapeObject shape : shapes)
            sm.setBackground(shape.getID(), b);

        ssm.clearCurSelection();
    }

    @Override
    public ArrayList<Long> getSelectedShapes() {
        ArrayList<Long> ids = new ArrayList<Long>();
        ArrayList<ShapeObject> shapes = ssm.getSelection();
        
        for(ShapeObject shape : shapes){
            ids.add(shape.getID());
        }
        return ids;
    }

    @Override
    public ArrayList<Long> getAllIDs() {
        ArrayList<Long> ids = new ArrayList<Long>();
        
        for(ShapeObject s : sm.getBGShapes()){
            ids.add(s.getID());
        }

        for(ShapeObject s : sm.getShapes()){
            ids.add(s.getID());
        }
        
        return ids;
    }

    @Override
    public String getShapeName(long id) {
        ShapeObject s = sm.getShape(id);
        
        if(s == null)
            return "";
        return s.getName();
    }

    @Override
    public void deleteCurrentSelection() {
        ssm.deleteCurrentSelection();
    }

}
