package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.AdapterCommunicationInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame.FrameToGraphicInterface;

public class ShapeController {
    
    protected ShapeManager sm = null;
    protected CopyManager scm = null;
    protected TransformationManager srm = null;
    protected SelectionManager ssm = null;
    protected TranslationManager stm = null;
    
    protected InternalMediatorInterface smi = null;
    
    protected FrameToGraphicInterface frame = null;
    
    //protected ExternalShapeToFrameInterface frameInterface = null;
    protected GraphicToInputInterface inputInterface = null;
    protected GraphicToFrameInterface shapeFacadeInterface = null;
    
    public ShapeController(GraphicToFrame shapeFacade, AdapterCommunicationInterface adapter){
        this.shapeFacadeInterface = shapeFacade;

        smi = new InternaMediator(adapter);
        
        sm = new ShapeManager(smi);
        stm = new TranslationManager(smi);
        ssm = new SelectionManager(smi);
        srm = new TransformationManager(smi);
        scm = new CopyManager(smi);
        
        smi.registerSelectionManager(ssm);
        smi.registerShapeManager(sm);
        smi.registerTranslationManager(stm);
        
        //frameInterface = new ExternalShapeToFrame(sm);
        inputInterface = new GraphicToInputFacade(this, adapter);
    }

}
