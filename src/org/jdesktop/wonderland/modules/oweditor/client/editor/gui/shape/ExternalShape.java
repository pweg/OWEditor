package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.TranslatedObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.AdapterCommunicationInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.FrameToShapeInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.InputToShapeInterface;

public class ExternalShape implements ExternalShapeInterface{
    
    private InternalShapeMediatorInterface smi = null;
    private ShapeManager sm = null;
    private TransformationManager srm = null;
    private TranslationManager stm = null;
    
    private ShapeController sc = null;

    public ExternalShape(AdapterCommunicationInterface adapter){
        this.sc = new ShapeController(this, adapter);
        registerCompontens();
    }
    
    private void registerCompontens(){
        smi = sc.smi;
        sm = sc.sm;
        srm = sc.srm;
        stm = sc.stm;
    }

    @Override
    public void createShape(TranslatedObjectInterface dataObject) {
        sm.setDataUpdate(dataObject);
    }

    @Override
    public void removeShape(long id) {
        sm.removeShape(id);
    }

    @Override
    public void updateShapeCoordinates(long id, int x, int y) {
        stm.translateShape(id, x, y);
    }

    @Override
    public void updateShape(long id, int x, int y, String name) {
        sm.changeShape(id, x, y, name);
    }


    @Override
    public void updateShapeRotation(long id, double rotation) {
        srm.setRotation(id, rotation);
        
    }

    @Override
    public void updateShapeScale(long id, double scale) {
        srm.setScale(id, scale);
    }


    @Override
    public void registerFrameInterface(FrameToShapeInterface frameInterface) {
        sc.frame = frameInterface;
        smi.registerFrameInterface(frameInterface);
    }

    @Override
    public void registerInputInterface(InputToShapeInterface input) {
        sc.input = input;
    }

    @Override
    public ExternalShapeToFrameInterface getFrameInterface() {
        return sc.frameInterface;
    }

    @Override
    public ExternalShapeToInputFacadeInterface getInputInterface() {
        return sc.inputInterface;
    }
}
