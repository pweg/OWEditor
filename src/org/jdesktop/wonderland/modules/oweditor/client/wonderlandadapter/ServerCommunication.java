/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import java.io.File;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellCache;
import org.jdesktop.wonderland.client.cell.CellEditChannelConnection;
import org.jdesktop.wonderland.client.cell.MovableComponent;
import org.jdesktop.wonderland.client.cell.registry.CellComponentRegistry;
import org.jdesktop.wonderland.client.cell.registry.spi.CellComponentFactorySPI;
import org.jdesktop.wonderland.client.cell.utils.CellUtils;
import org.jdesktop.wonderland.client.comms.WonderlandSession;
import org.jdesktop.wonderland.common.cell.CellEditConnectionType;
import org.jdesktop.wonderland.common.cell.CellID;
import org.jdesktop.wonderland.common.cell.CellTransform;
import org.jdesktop.wonderland.common.cell.messages.CellDuplicateMessage;
import org.jdesktop.wonderland.common.cell.messages.CellServerComponentMessage;
import org.jdesktop.wonderland.common.cell.messages.CellServerComponentResponseMessage;
import org.jdesktop.wonderland.common.cell.messages.CellServerStateRequestMessage;
import org.jdesktop.wonderland.common.cell.messages.CellServerStateResponseMessage;
import org.jdesktop.wonderland.common.cell.messages.CellServerStateUpdateMessage;
import org.jdesktop.wonderland.common.cell.state.CellComponentServerState;
import org.jdesktop.wonderland.common.cell.state.CellServerState;
import org.jdesktop.wonderland.common.cell.state.PositionComponentServerState;
import org.jdesktop.wonderland.common.messages.ErrorMessage;
import org.jdesktop.wonderland.common.messages.OKMessage;
import org.jdesktop.wonderland.common.messages.ResponseMessage;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.components.IDCellComponent;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.components.IDCellComponentFactory;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.components.ImageCellComponent;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.components.ImageCellComponentFactory;
import org.jdesktop.wonderland.modules.oweditor.common.IDCellComponentServerState;
import org.jdesktop.wonderland.modules.oweditor.common.ImageCellComponentServerState;

/**
 * This class is used for outgoing communication with the server.
 * 
 * @author Patrick
 */
public class ServerCommunication {
    
    private static final Logger LOGGER =
            Logger.getLogger(GUIEventManager.class.getName());
    
    private WonderlandAdapterController ac = null;
    private CellComponentFactorySPI imagespi = null;
    private CellComponentFactorySPI idspi = null;
    
    
    public ServerCommunication(WonderlandAdapterController ac){
        this.ac = ac;
        imagespi = new ImageCellComponentFactory();
        idspi = new IDCellComponentFactory();
    }
    
    /**
     * Translates a cell to the given position.
     * 
     * @param id The id of the cell.
     * @param translation The new wonderland coordinates. 
     * @throws org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.ServerCommException 
     */
    public void translate(long id, Vector3f translation) throws ServerCommException{
        CellCache cache = ac.sm.getCellCache();
        
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            throw new ServerCommException();
        }
        
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        
        //Do not remove this, because this can shake up the 
        //gui event manager, when creating a cell, because
        //it will throw an exception, which will reach the gui,
        //which is not desireable.
        if(cell == null){
            LOGGER.warning("Translation: CELL == NULL");
            throw new ServerCommException();
        }
        
        MovableComponent movableComponent = cell.getComponent(MovableComponent.class);

        if (movableComponent != null) {
            CellTransform cellTransform = cell.getLocalTransform();
            cellTransform.setTranslation(translation);
            movableComponent.localMoveRequest(cellTransform);
        }else{
            LOGGER.warning("Movable component of cell " + id + "is null");
            throw new ServerCommException();
        }
    }
    
    /**
     * Rotates a cell.
     * 
     * @param id The id of the cell
     * @param rotation The rotation as vector. Remember, wonderland uses
     * the y coordinates for rotation around the editors xy axis. 
     * @throws org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.ServerCommException 
     */
    public void rotate(long id, Vector3f rotation) throws ServerCommException{
        
        CellCache cache = ac.sm.getCellCache();

        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}"
                    , ac.sm.getSession());
            throw new ServerCommException();
        }
        
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
                
        //Do not remove this, because this can shake up the 
        //gui event manager, when creating a cell, because
        //it will throw an exception, which will reach the gui,
        //which is not desireable.
        if(cell == null)
            throw new ServerCommException();
        
        MovableComponent movableComponent = cell.getComponent(MovableComponent.class);
        
        Quaternion newRotation = new Quaternion(new float[] 
                { rotation.x, rotation.y, rotation.z });
        
        if (movableComponent != null) {
            CellTransform cellTransform = cell.getLocalTransform();
            cellTransform.setRotation(newRotation);
            movableComponent.localMoveRequest(cellTransform);
        }else{
            throw new ServerCommException();
        }
    }
    
    /**
     * Scales a cell.
     * 
     * @param id The id of the cell.
     * @param scale The scale value.
     * @throws org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.ServerCommException 
     */
    public void scale (long id, float scale) throws ServerCommException {
        
        CellCache cache = ac.sm.getCellCache();
        
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            throw new ServerCommException();
        }
        
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        
        //Do not remove this, because this can shake up the 
        //gui event manager, when creating a cell, because
        //it will throw an exception, which will reach the gui,
        //which is not desireable.
        if(cell == null)
            throw new ServerCommException();
        
        MovableComponent movableComponent = cell.getComponent(MovableComponent.class);
        
        if (movableComponent != null) {
            CellTransform cellTransform = cell.getLocalTransform();
            cellTransform.setScaling((float) scale);
            movableComponent.localMoveRequest(cellTransform);
        }else{
            throw new ServerCommException();
        }
    }
    
    public void changeName(long id, String name) throws ServerCommException{
        CellCache cache = ac.sm.getCellCache();
        
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            throw new ServerCommException();
        }
        
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        
        CellServerState state = fetchCellServerState(cell);
        ((CellServerState) state).setName(name);
        sendServerUpdateState(cell, state, new HashSet());
    }
    
    /**
     * Removes a given cell.
     * 
     * @param id The id of the cell to remove.
     * @throws org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.ServerCommException
     */
    public void remove(long id) throws ServerCommException{
        
        CellCache cache = ac.sm.getCellCache();
        
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            throw new ServerCommException();
        }
        
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        
        if(cell == null)
            throw new ServerCommException();
        
        CellUtils.deleteCell(cell);
    }
    
    /**
     * Copies a cell.
     * 
     * @param cell The cell to copy.
     * @param name The name of the new cell.
     * @throws org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.ServerCommException
     */
    public void paste(Cell cell, String name) throws ServerCommException{
        WonderlandSession session = ac.sm.getSession();
        
        String message = MessageFormat.format(name, cell.getName());
           
        CellEditChannelConnection connection = 
                (CellEditChannelConnection) session.getConnection(
                CellEditConnectionType.CLIENT_TYPE);
        CellDuplicateMessage msg =
                new CellDuplicateMessage(cell.getCellID(), message);
        connection.send(msg);
    }
    
    /**
     * Uploads an image.
     * 
     * @param img The image file.
     * @throws ServerCommException 
     */
    public void uploadImage(File img) throws ServerCommException{
        try {
            ac.fm.uploadImage(img, null);
        } catch (Exception ex) {
            Logger.getLogger(ServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerCommException();
        }
    }
    
    /**
     * Changes the image of a cell
     * @param id
     * @param imgName
     * @param dir
     * @return
     * @throws ServerCommException 
     */
    public boolean changeImage(long id, String imgName, String dir) throws ServerCommException{
        CellCache cache = ac.sm.getCellCache();
        
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            throw new ServerCommException();
        }
        
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        
        //Do not remove this, because this can shake up the 
        //gui event manager, when creating a cell, because
        //it will throw an exception, which will reach the gui,
        //which is not desireable.
        if(cell == null)
            throw new ServerCommException();
        
        ImageCellComponent imageComponent = cell.getComponent(ImageCellComponent.class);
        
        if(imageComponent == null){
            addComponent(cell, ImageCellComponent.class, imagespi);
        }
        
        CellServerState state = fetchCellServerState(cell);
            
        if(state == null){
            LOGGER.warning("Cell Server State is null");
            throw new ServerCommException();
        }
        
        CellComponentServerState imgState = state.getComponentServerState(
            ImageCellComponentServerState.class);
            
        if(imgState != null){
                try {
                        
                    ((ImageCellComponentServerState) imgState).setImage(
                        imgName);
                    ((ImageCellComponentServerState) imgState).setDir(
                        dir);
                    
                    HashSet<CellComponentServerState> compStateSet = new HashSet();
                    compStateSet.add(imgState);
                    sendServerUpdateState(cell, state, compStateSet);
                 } catch (ServerCommException e) {
                     LOGGER.log(Level.SEVERE, null, e);
                     throw new ServerCommException();
            }
            
        }else{
            return false;
        }
        return true;
    }
    
    /**
     * Adds an ID component to a cell. This is used for undo/redo.
     * 
     * @param cellID The current id of the cell, which should get 
     * the component.
     * @param origID The original id, the cell had before its first deletion.
     * @throws ServerCommException 
     */
    public void addIDComponent(long cellID, long origID) throws ServerCommException{
        CellCache cache = ac.sm.getCellCache();
        
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            throw new ServerCommException();
        }
        
        CellID cellid = new CellID(cellID);
        Cell cell = cache.getCell(cellid);
        
        //Do not remove this, because this can shake up the 
        //gui event manager, when creating a cell, because
        //it will throw an exception, which will reach the gui,
        //which is not desireable.
        if(cell == null){
            LOGGER.warning("No cell found for given id " +cellid);
            throw new ServerCommException();
        }
        
        IDCellComponent idComponent = cell.getComponent(IDCellComponent.class);
        if(idComponent == null){
            addComponent(cell, IDCellComponent.class, idspi);
        }
        CellServerState state = fetchCellServerState(cell);
            
        if(state == null){
            LOGGER.warning("Could not find cell server state of cell "+cellID);
            throw new ServerCommException();
        }
        CellComponentServerState compState = state.getComponentServerState(
            IDCellComponentServerState.class);
           
        if(compState != null){
                
            ((IDCellComponentServerState) compState).setID(origID);
            HashSet<CellComponentServerState> compStateSet = new HashSet();
            compStateSet.add(compState);
            sendServerUpdateState(cell, state, compStateSet);
        }
    }
    
 
    /*private void updateState(Cell cell, CellServerState state, 
            CellComponentServerState compState) throws ServerCommException{
        
        Set<CellComponentServerState> compStateSet = new HashSet();
            compStateSet.add(compState);
            CellServerStateUpdateMessage msg = new CellServerStateUpdateMessage(
                cell.getCellID(), state, compStateSet);
             
            ResponseMessage response = cell.sendCellMessageAndWait(msg);
            
            if(response == null){
                LOGGER.warning("Received a null reply from cell with id " +
                        cell.getCellID() + " with name " +
                        cell.getName() + " setting component state.");
                throw new ServerCommException();
            }
               
            if (response instanceof ErrorMessage) {
            
                ErrorMessage em = (ErrorMessage) response;
                LOGGER.log(Level.WARNING, "Error updating cell: " +
                        em.getErrorMessage(), em.getErrorCause());
                throw new ServerCommException();
            }
    }*/
    
     /**
     * Updates the cell compononts for a given cell
     * 
     * @param cell The cell to update.
     * @param state The cell server state.
     * @param compStateSet The component states, which should be updated.
     * @throws ServerCommException 
     */
    private void sendServerUpdateState(Cell cell, CellServerState state, 
            HashSet<CellComponentServerState> compStateSet) throws ServerCommException{
        CellServerStateUpdateMessage msg = new CellServerStateUpdateMessage(
            cell.getCellID(), state, compStateSet);
             
        ResponseMessage response = cell.sendCellMessageAndWait(msg);
            
        if(response == null){
            LOGGER.warning("Received a null reply from cell with id " +
                    cell.getCellID() + " with name " +
                    cell.getName() + " setting component state.");
           throw new ServerCommException();
        }
               
        if (response instanceof ErrorMessage) {
            
            ErrorMessage em = (ErrorMessage) response;
            LOGGER.log(Level.WARNING, "Error updating cell: " +
                    em.getErrorMessage(), em.getErrorCause());
            throw new ServerCommException();
        }
    }
    
    /**
     * Adds a component to a given cell.
     * 
     * @param cell The cell which should get the component.
     * @param componentClass The component class.
     * @param factory The component factory.
     * @throws ServerCommException 
     */
    private void addComponent(Cell cell, Class componentClass, 
            CellComponentFactorySPI factory) throws ServerCommException{
        
        if(cell.getComponent(componentClass) != null)
            return;
        
        CellComponentServerState state = factory.getDefaultCellComponentServerState();
        CellServerComponentMessage message =
            CellServerComponentMessage.newAddMessage(cell.getCellID(), state);
            
        ResponseMessage response = cell.sendCellMessageAndWait(message);
            
        if (response == null) {
            // log and error and post a dialog box
            LOGGER.warning("Received a null reply from cell with id " +
                    cell.getCellID() + " with name " +
                    cell.getName() + " adding component.");
            throw new ServerCommException();
        }

        if (response instanceof CellServerComponentResponseMessage) {
            // If successful, add the component to the GUI by refreshing the
            // Cell that is selected.
            //LOGGER.warning("Component successfully created");
        }
        else if (response instanceof ErrorMessage) {
            // Log an error. Eventually we should display a dialog
            LOGGER.log(Level.WARNING, "Unable to add component to the server: " +
                    ((ErrorMessage) response).getErrorMessage(),
                    ((ErrorMessage) response).getErrorCause());
            throw new ServerCommException();
        }        
    }
    
    /**
     * Deletes a component of a certein type from the cell.
     * 
     * @param id The id of the cell.
     * @param componentClass The components class which should be deleted. 
     * @throws org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.ServerCommException 
     */
    public void deleteComponent(long id, Class componentClass) throws ServerCommException{
        CellCache cache = ac.sm.getCellCache();
        
        if (cache == null) {
            LOGGER.log(Level.WARNING, "Unable to find Cell cache for session {0}", ac.sm.getSession());
            throw new ServerCommException();
        }
        
        CellID cellid = new CellID(id);
        Cell cell = cache.getCell(cellid);
        
        CellComponentRegistry r = CellComponentRegistry.getCellComponentRegistry();
        CellComponentFactorySPI spi = r.getCellFactoryByStateClass(componentClass);
        if (spi == null) {
            LOGGER.warning("Could not find cell component factory for " +
                    componentClass.getName());
            throw new ServerCommException();
        }
        
        CellComponentServerState s = spi.getDefaultCellComponentServerState();
        String className = s.getServerComponentClassName();
        
        CellServerComponentMessage cscm =
                CellServerComponentMessage.newRemoveMessage(cellid, className);
        ResponseMessage response = cell.sendCellMessageAndWait(cscm);
        if (response == null) {
            LOGGER.warning("Received a null reply from cell with id " +
                    cellid + " with name " + cell.getName() +
                    " removing component.");
            throw new ServerCommException();
        }
        if (response instanceof ErrorMessage) {
            // Log an error. Eventually we should display a dialog
            LOGGER.log(Level.WARNING, "Unable to add component to the server: " +
                    ((ErrorMessage) response).getErrorMessage(),
                    ((ErrorMessage) response).getErrorCause());
            throw new ServerCommException();
        }if (response instanceof OKMessage) {
            LOGGER.warning("Component DELETED");
        }
    }
    
     /**
     * Asks the server for the server state of the cell; returns null upon
     * error.
     * @param cell The cell for which the server state should be fetched.  
     */
    private CellServerState fetchCellServerState(Cell cell) {
        // Fetch the setup object from the Cell object. We send a message on
        // the cell channel, so we must fetch that first.
        ResponseMessage response = cell.sendCellMessageAndWait(
                new CellServerStateRequestMessage(cell.getCellID()));
        
        if (response == null) {
            return null;
        }else if (response instanceof ErrorMessage) {
            
            ErrorMessage em = (ErrorMessage) response;
            LOGGER.log(Level.WARNING, "Error fetching server state: " +
                    em.getErrorMessage(), em.getErrorCause());
            return null;
        }else if (!(response instanceof CellServerStateResponseMessage)){
            LOGGER.log(Level.SEVERE, "Error fetching server state: "
                    + "Wrong response"+
                    response.getClass().getName());
            return null;
         }

        // We need to remove the position component first as a special case
        // since we do not want to update it after the cell is created.
        CellServerStateResponseMessage cssrm = (CellServerStateResponseMessage) response;
        CellServerState state = cssrm.getCellServerState();
        
        if (state != null) {
            //needs to be here, otherwise communication would not work.
            state.removeComponentServerState(PositionComponentServerState.class);
        }
        return state;
    }
    
    
}
