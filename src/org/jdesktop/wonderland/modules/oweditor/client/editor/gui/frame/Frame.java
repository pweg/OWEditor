package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.TranslatedObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.AdapterCommunicationInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicToFrame;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicToFrameInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.GraphicToInputFacadeInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.InputToFrameInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input.InputToShapeInterface;

public class Frame implements FrameInterface{
    
    private WindowDrawingPanel drawingPan = null;
    private WindowPopupMenu popupMenu = null;
    private MainFrame frame = null;
    private FrameController fc = null;
    private GraphicToFrameInterface graphic = null;
    
    public Frame(AdapterCommunicationInterface adapter){
        this.fc = new FrameController();
        registerComponents();
        graphic = new GraphicToFrame(adapter);
        fc.graphic = graphic;
    }
    
    private void registerComponents(){
        drawingPan = fc.drawingPan;
        popupMenu = fc.popupMenu;
        frame = fc.frame;
    }
    
    @Override
    public void registerInputInterface(InputToFrameInterface input){
        popupMenu.registerInputInterface(input);
    }

    @Override
    public void addMouseListener(MouseInputAdapter mkListener){
        drawingPan.addMouseListener(mkListener);
        drawingPan.addMouseMotionListener(mkListener);
    }
    
    @Override
    public void addKeyListener(KeyListener mkListener){
        frame.addKeyListener(mkListener);
    }
    
    @Override
    public void addMouseWheelListener(MouseWheelListener mouseWheelListener){
        drawingPan.addMouseWheelListener(mouseWheelListener);
    }

    @Override
    public void repaint() {
        fc.repaint();
    }

    @Override
    public void setVisible(boolean visibility) {
       frame.setVisible(visibility);
    }

    @Override
    public int getTranslationX() {
        return fc.getTranslationX();
    }

    @Override
    public int getTranslationY() {
        return fc.getTranslationY();
    }

    @Override
    public void setNewWidth(int width) {
        drawingPan.setNewWidth(width);
        
    }

    @Override
    public void setNewHeight(int height) {
        drawingPan.setNewHeight(height);
    }

    @Override
    public void setNewMinX(int x) {
        drawingPan.setNewMinX(x);
    }

    @Override
    public void setNewMinY(int y) {
        drawingPan.setNewMinY(y);
    }

    @Override
    public FrameToShapeInterface getShapeInterface() {
        return fc.shapeInterface;
    }

    @Override
    public FrameToInputInterface getInputInterface() {
        return fc.inputInterface;
    }

    public void setCoordinateTranslator(CoordinateTranslatorInterface coordinateTranslator) {
        fc.setCoordinateTranslator(coordinateTranslator);
    }

    @Override
    public void registerFrameInterface(FrameToShapeInterface frameInterface) {
        graphic.registerFrameInterface(frameInterface);
    }

    @Override
    public void registerInputInterface(InputToShapeInterface input) {
        graphic.registerInputInterface(input);
    }

    @Override
    public GraphicToFrameInterface getFrameInterface() {
        return graphic.getFrameInterface();
    }

    @Override
    public GraphicToInputFacadeInterface getGraphicInputInterface() {
        return graphic.getInputInterface();
    }

    @Override
    public void createShape(TranslatedObjectInterface dataObject) {
        graphic.createShape(dataObject);
    }

    @Override
    public void updateShape(long id, int x, int y, String name) {
        graphic.updateShape(id, x, y, name);
    }

    @Override
    public void removeShape(long id) {
        graphic.removeShape(id);
    }

    @Override
    public void updateShapeCoordinates(long id, int x, int y) {
        graphic.updateShapeCoordinates(id, x, y);
    }

    @Override
    public void updateShapeRotation(long id, double rotation) {
        graphic.updateShapeRotation(id, rotation);
    }

    @Override
    public void updateShapeScale(long id, double scale) {
        graphic.updateShapeScale(id, scale);
    }

}
