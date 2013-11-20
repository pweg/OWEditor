package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.AdapterCommunicationInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.FrameToShapeInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.InputToShapeInterface;

public class ShapeController {
    
    protected ShapeManager sm = null;
    protected CopyManager scm = null;
    protected RotationManager srm = null;
    protected SelectionManager ssm = null;
    protected TranslationManager stm = null;
    
    protected InternalShapeMediatorInterface smi = null;
    
    protected FrameToShapeInterface frame = null;
    protected InputToShapeInterface input = null;
    
    protected ExternalShapeToFrameInterface frameInterface = null;
    protected ExternalShapeToInputFacadeInterface inputInterface = null;
    private ExternalShapeInterface shapeFacadeInterface = null;
    
    public ShapeController(ExternalShape shapeFacade, AdapterCommunicationInterface adapter){
        this.shapeFacadeInterface = shapeFacade;

        smi = new InternalShapeMediator(adapter);
        
        sm = new ShapeManager(smi);
        stm = new TranslationManager(smi);
        ssm = new SelectionManager(smi);
        srm = new RotationManager(smi);
        scm = new CopyManager(smi);
        
        smi.registerSelectionManager(ssm);
        smi.registerShapeManager(sm);
        smi.registerTranslationManager(stm);
        
        frameInterface = new ExternalShapeToFrame(sm);
        inputInterface = new ExternalShapeToInputFacade(this, adapter);
    }

}
