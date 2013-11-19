package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

public class InputToShape implements InputToShapeInterface{

    private InputController ic = null;
    
    public InputToShape(InputController ic) {
        this.ic = ic;
    }

    @Override
    public void setRotationStrategy() {
        ic.setRotationStrategy();        
    }

    @Override
    public void setRotationCenterStrategy() {
        ic.setRotationCenterStrategy();
    }

}
