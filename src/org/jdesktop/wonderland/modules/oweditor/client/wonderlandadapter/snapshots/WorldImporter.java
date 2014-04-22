/**
 * Open Wonderland
 *
 * Copyright (c) 2011 - 2012, Open Wonderland Foundation, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * The Open Wonderland Foundation designates this particular file as
 * subject to the "Classpath" exception as provided by the Open Wonderland
 * Foundation in the License file that accompanied this code.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.snapshots;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.CellEditChannelConnection;
import org.jdesktop.wonderland.client.comms.WonderlandSession;
import org.jdesktop.wonderland.client.jme.ClientContextJME;
import org.jdesktop.wonderland.client.jme.ViewManager;
import org.jdesktop.wonderland.client.login.LoginManager;
import org.jdesktop.wonderland.client.login.ServerSessionManager;
import org.jdesktop.wonderland.common.cell.CellEditConnectionType;
import org.jdesktop.wonderland.common.cell.CellID;
import org.jdesktop.wonderland.common.cell.CellTransform;
import org.jdesktop.wonderland.common.cell.messages.CellCreateMessage;
import org.jdesktop.wonderland.common.cell.messages.CellCreatedMessage;
import org.jdesktop.wonderland.common.cell.state.CellServerState;
import org.jdesktop.wonderland.common.cell.state.CellServerStateFactory;
import org.jdesktop.wonderland.common.cell.state.PositionComponentServerState;
import org.jdesktop.wonderland.common.messages.ErrorMessage;
import org.jdesktop.wonderland.common.messages.ResponseMessage;
import org.jdesktop.wonderland.common.utils.ScannedClassLoader;
import org.jdesktop.wonderland.modules.contentrepo.client.ContentRepository;
import org.jdesktop.wonderland.modules.contentrepo.client.ContentRepositoryRegistry;
import org.jdesktop.wonderland.modules.contentrepo.common.ContentCollection;
import org.jdesktop.wonderland.modules.contentrepo.common.ContentNode;
import org.jdesktop.wonderland.modules.contentrepo.common.ContentRepositoryException;
import org.jdesktop.wonderland.modules.contentrepo.common.ContentResource;
import org.jdesktop.wonderland.modules.contentrepo.common.ContentNode.Type;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.snapshots.SnapshotArchive.ServerStateHolder;

/**
 *
 * @author spcworld
 * 
 * Changed a bit, to suit the needs.
 * 
 * @author Patrick
 */
public class WorldImporter  {
    private static final Logger LOGGER =
            Logger.getLogger(WorldImporter.class.getName());
    
    public static WorldImporter getInstance() {
        return new WorldImporter();
    }
    
    /**
     * Imports a file.
     * 
     * @param file The file to import.
     */
    public void importFile(File file) 
    {
        //1) Unpackage the .wlexport archive
        //Unpack into temporary directory
        //upload resources to server

        try {
            LOGGER.warning("Bla");
            SnapshotArchive archive = new SnapshotArchive();
            LOGGER.warning("Bla2");
            archive.unpackArchive(file);
            
            LOGGER.warning("Bla3");
            uploadContent(archive);
                
            //2) Recreate server state from xml
            //3) Create cells from server states
            createCells(archive.getServerStates(), null);
            
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error processing archive " + file,e);
        } catch (RuntimeException ex) {
        }
    }

    public void uploadContent(SnapshotArchive archive) throws RuntimeException{
        Cell cell = ClientContextJME.getViewManager().getPrimaryViewCell();
        WonderlandSession session = cell.getCellCache().getSession();
        ContentRepositoryRegistry registry = ContentRepositoryRegistry.getInstance();
        ContentRepository repo = registry.getRepository(session.getSessionManager());

        ContentCollection userRoot;

        // First try to find the resource, if it exists, then simply upload the
        // new bits. Otherwise create the resource and upload the new bits

        //@TODO something should be done about file.getName() to make sure our
        //archive's resources get uploaded correctly even if they might have
        // duplicate names.

        try {
            //get content/user directory from .wonderland-server
            userRoot = repo.getUserRoot();

            //for each resource file in the archive...
            for(File file : archive.getContent()) {
                                
                //grab directory pointer if available
                File fileRoot = new File(archive.getArchiveRoot(),"content");
                ContentCollection cDir = populateDirectories(userRoot,
                                                            fileRoot,
                                                            file);
                ContentNode node = (ContentNode)cDir.getChild(file.getName());
                if (node == null) {
                    //if not avaible, create it.

                    node = (ContentNode)cDir.createChild(file.getName(), Type.RESOURCE);
                }
                //do the heavy lifting.
                
                InputStream upload = processFile(file, LoginManager.getPrimary().getUsername());
                         
                ((ContentResource)node).put(upload);
            }

        } catch (ContentRepositoryException excp) {
            LOGGER.log(Level.WARNING, "Error uploading file in uploadResources()", excp);
            throw new RuntimeException();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error uploading file in uploadResources()", e);
            throw new RuntimeException();
        }
    }

    protected InputStream processFile(File file, String username) throws IOException {
        
        //handle ModelCoponent case
        if(file.getName().toLowerCase().endsWith(".dep")) {
            return processDEPFile(file, username);
        }

        return new FileInputStream(file);
    }

    protected InputStream processDEPFile(File file, String username) throws IOException {
        String fileContents = updateContentURIs(file, username);
        return new ByteArrayInputStream(fileContents.getBytes());
    }

    protected String updateContentURIs(File file, String username) throws IOException {
        try {
            StringBuilder fileString = new StringBuilder();
            //StringBuffer fileString = new StringBuffer();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                //line = line.replace("wlcontent://users", "wlcontent://users/" + LoginManager.getPrimary().getUsername());

                fileString.append(updateURI(line, username)).append("\n");
            }
            
            reader.close();
            return fileString.toString();
        }catch(IOException e){
            return "";
        }
    }

    protected ContentCollection populateDirectories(ContentCollection node, File root, File file)
    throws ContentRepositoryException {
       Deque<File> directories = new LinkedList();

       //get the initial directory
       file = file.getParentFile();
       //while file is not the root directory
       while(!file.equals(root)) {

          //add the current directory to the deque
          directories.push(file);

          file = file.getParentFile();
       }

       ContentCollection cc = node;
       while(!directories.isEmpty()) {

           String name = directories.pop().getName();
           ContentNode cn = cc.getChild(name);
           if(cn == null) {
                cc = (ContentCollection)cc.createChild(name, Type.COLLECTION);
           }
           else if(cn instanceof ContentCollection) {
               cc = (ContentCollection)cn;
           }
           else  {
               throw new ContentRepositoryException(name + " already exists");
           }
       }

       return cc;
    }

    public CellServerState restoreServerState(File serverState) throws IOException {
        // get unmarshaller
        ScannedClassLoader loader =
                LoginManager.getPrimary().getClassloader();
        Unmarshaller unmarshaller = CellServerStateFactory.getUnmarshaller(loader);


        // decode each file into a sever state
        try {
            String serverStateString = updateContentURIs(serverState, LoginManager.getPrimary().getUsername());

            // ByteArrayInputStream stream = new ByteArrayInputStream(serverStateString.toString().getBytes());
            // CellServerState state = (CellServerState) unmarshaller.unmarshal(stream);
            CellServerState state = CellServerState.decode(
                    new StringReader(serverStateString),
                    unmarshaller);
            return state;
            //serverStates.add(state);


        } catch (JAXBException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            throw new IOException(ex);
        }
    }
    protected String updateURI(String text, String username) {
        int startIndex = text.indexOf("wlcontent://");
        if (startIndex == -1) {
            return text;
        }

        //get
        //wlcontent://users@AA.BB.CC.DD/Nicole/art/TeamRoomFloor2.kmz.dep

        //put
        //wlcontent://users/Ryan/Nicole/art/TeamRoomFloor2.kmz.dep
        int i1 = text.indexOf("/", startIndex + "wlcontent://".length() + 1);
        //i1 = text.indexOf("/", i1 + 1);

        return text.substring(0, startIndex) + "wlcontent://users/"+ username + text.substring(i1);

    }
    public void createCells(List <ServerStateHolder> serverStates, CellID parentID) {
        // recursively create cells based on tree of ServerStateHolders
        // decode on the fly using restoreServerState()
        
            LOGGER.warning("Bla2");
        for (ServerStateHolder stateHolder : serverStates) {
            LOGGER.warning("Creating from state: "+stateHolder.getState().getName());
            if(parentID == null) {
                LOGGER.warning("ParentID is null, creating root cell.");
            } else {
                LOGGER.warning("ParentID is "+ parentID.toString()+ ", creating child cell.");
            }
            try {
                
                CellServerState state = restoreServerState(stateHolder.getState());

                if (parentID == null) {
                    try {
                        CellTransform avatar = ViewManager.getViewManager().getPrimaryViewCell().getWorldTransform();
                        //CellServerState state = restoreServerState(stateHolder.getState());
                        //CellUtils.createCell(state);
                        // normalize the location
                        //position should never be null.
                        PositionComponentServerState position = (PositionComponentServerState) state.getComponentServerState(PositionComponentServerState.class);
                        if (position == null) {
                            position = new PositionComponentServerState();
                        }

                        CellTransform object = new CellTransform(position.getRotation(), position.getTranslation(), position.getScaling().x);
                        CellTransform applied = applyRelativeTransform(avatar, object);

                        // set the position to the new position
                        position.setTranslation(applied.getTranslation(null));
                        position.setRotation(applied.getRotation(null));
                        state.addComponentServerState(position);

                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }

                CellID cellID = createCell(state, parentID);
                if (cellID != null) {
                    createCells(stateHolder.getHolders(), cellID);
                }
            } catch (IOException e) {
                LOGGER.warning("Could not restore state, continuing...");
                continue;
            }
        }
    }

    private CellID createCell(CellServerState state, CellID parentID) {
                
        ServerSessionManager manager = LoginManager.getPrimary();
        WonderlandSession session = manager.getPrimarySession();
        CellEditChannelConnection connection = (CellEditChannelConnection) session.getConnection(CellEditConnectionType.CLIENT_TYPE);
        CellCreateMessage msg = new CellCreateMessage(parentID, state);
        try {
            ResponseMessage message = connection.sendAndWait(msg);
            LOGGER.warning("Got response message: "+message);
            if(message instanceof CellCreatedMessage) {
                //yay
                CellCreatedMessage cellCreatedMessage = (CellCreatedMessage)message;
                LOGGER.warning("CellID: "+cellCreatedMessage.getCellID());
                return cellCreatedMessage.getCellID();
            } else if (message instanceof ErrorMessage) {
                    LOGGER.log(Level.WARNING, ((ErrorMessage) message).getErrorMessage(),
                                              ((ErrorMessage) message).getErrorCause());
            }

        } catch (InterruptedException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

        return null;
    }

    protected CellTransform applyRelativeTransform(CellTransform avatar,
                                                 CellTransform object)
    {
        Vector3f objectTranslation = object.getTranslation(null);

        Quaternion avatarRotation = avatar.getRotation(null);
        Vector3f rotatedTranslation = avatarRotation.mult(objectTranslation);
        rotatedTranslation.addLocal(avatar.getTranslation(null));
        //rotatedTranslation = translation we want to apply
        //rotatedRotation is the correct rotation we want to apply to object
        // in relation to the avatar
        Quaternion rotatedRotation = avatarRotation.mult(object.getRotation(null));

        return new CellTransform(rotatedRotation, rotatedTranslation);
    }

 
}
