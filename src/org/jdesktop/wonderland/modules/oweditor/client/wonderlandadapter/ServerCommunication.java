/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellCache;
import org.jdesktop.wonderland.client.cell.CellEditChannelConnection;
import org.jdesktop.wonderland.client.cell.MovableComponent;
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
import org.jdesktop.wonderland.common.messages.ErrorMessage;
import org.jdesktop.wonderland.common.messages.ResponseMessage;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.imagecomponent.ImageCellComponent;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.imagecomponent.ImageCellComponentFactory;
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
    
    
    public ServerCommunication(WonderlandAdapterController ac){
        this.ac = ac;
        imagespi = new ImageCellComponentFactory();
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
        if(cell == null)
            throw new ServerCommException();
        
        MovableComponent movableComponent = cell.getComponent(MovableComponent.class);

        if (movableComponent != null) {
            CellTransform cellTransform = cell.getLocalTransform();
            cellTransform.setTranslation(translation);
            movableComponent.localMoveRequest(cellTransform);
        }else{
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
    
    /*private void addImageComponent(Cell cell) throws ServerCommException{        
        if(cell == null)
            throw new ServerCommException();
        
        CellComponent component = new ImageCellComponent(cell);
        cell.addComponent(component);
    }*/

    public boolean addImage(long id, BufferedImage img) throws ServerCommException{
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
            LOGGER.warning("COMPONENT == NULL");
            
            CellComponentServerState state = imagespi.getDefaultCellComponentServerState();
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
                LOGGER.warning("some response " +response.toString());
            }
            else if (response instanceof ErrorMessage) {
                // Log an error. Eventually we should display a dialog
                LOGGER.log(Level.WARNING, "Unable to add component to the server: " +
                        ((ErrorMessage) response).getErrorMessage(),
                        ((ErrorMessage) response).getErrorCause());
            }
            return false;
            //addImageComponent(cell);
        }else{
            LOGGER.warning("set image1");
            CellServerState state = fetchCellServerState(cell);
            
            if(state == null){
                LOGGER.warning("state null");
                throw new ServerCommException();
            }
            
                LOGGER.warning("fetching statre");
            CellComponentServerState compState = state.getComponentServerState(
                ImageCellComponentServerState.class);
                LOGGER.warning("state fetched");
            
            if(compState != null){
                LOGGER.warning("state not null");
                
                if(img != null){
                    try {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(img, "jpg", baos);
                        baos.flush();
                        byte[] bimg = baos.toByteArray();
                        baos.close();
                        ((ImageCellComponentServerState) compState).setImage(DatatypeConverter.printBase64Binary(bimg));
                     } catch (IOException ex) {
                         Logger.getLogger(ImageCellComponentServerState.class.getName()).log(Level.SEVERE, null, ex);
                     } catch (Exception e){
                         Logger.getLogger(ImageCellComponentServerState.class.getName()).log(Level.SEVERE, "Something went wrong",e);
                     }
                }else
                    ((ImageCellComponentServerState) compState).setImage(null);
                
                Set<CellComponentServerState> compStateSet = new HashSet();
                compStateSet.add(compState);
                        
                
                CellServerStateUpdateMessage msg = new CellServerStateUpdateMessage(
                    cell.getCellID(), state, compStateSet);
                
                ResponseMessage response = cell.sendCellMessageAndWait(msg);
                
                if (response instanceof ErrorMessage) {
            
                    ErrorMessage em = (ErrorMessage) response;
                    LOGGER.log(Level.WARNING, "Error updating cell: " +
                            em.getErrorMessage(), em.getErrorCause());
                }else
                    LOGGER.warning("IMAGE SERVER SUCCESS");
            }
            return true;
            
            
            //CellServerState state = fetchCellServerState(cell);
            
            /*if(state == null){
                LOGGER.warning("state == null");
                throw new ServerCommException();
            }*/
            
            /*ImageCellComponentServerState image = (ImageCellComponentServerState)
                    state.getComponentServerState(ImageCellComponentServerState.class);*/
            /*ImageCellComponentClientState state = new ImageCellComponentClientState();
            state.setImage(img);
            imageComponent.setClientState(state);
            LOGGER.warning("set image2");*/
            
            //image.setImage(img);
            
            //imageComponent.setImage(img);
        }
    }
    
        /**
     * Asks the server for the server state of the cell; returns null upon
     * error
     */
    private CellServerState fetchCellServerState(Cell cell) {
        // Fetch the setup object from the Cell object. We send a message on
        // the cell channel, so we must fetch that first.
        LOGGER.warning("1");
        ResponseMessage response = cell.sendCellMessageAndWait(
                new CellServerStateRequestMessage(cell.getCellID()));
        
        if (response == null) {
            LOGGER.warning("null");
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
        LOGGER.warning("1 2");
        CellServerStateResponseMessage cssrm = (CellServerStateResponseMessage) response;
        LOGGER.warning("2");
        CellServerState state = cssrm.getCellServerState();
        
        LOGGER.warning("3");
        if (state != null) {
            LOGGER.warning("if");
            //state.removeComponentServerState(PositionComponentServerState.class);
        }
        LOGGER.warning("4");
        return state;
    }
    
}
