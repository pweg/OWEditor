/**
 * Project Wonderland
 *
 * Copyright (c) 2004-2010, Sun Microsystems, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * Sun designates this particular file as subject to the "Classpath" 
 * exception as provided by Sun in the License file that accompanied 
 * this code.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.bounding.BoundingVolume;
import com.jme.scene.Node;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.jdesktop.mtgame.Entity;
import org.jdesktop.mtgame.RenderComponent;
import org.jdesktop.mtgame.RenderManager;
import org.jdesktop.mtgame.WorldManager;
import org.jdesktop.wonderland.client.cell.utils.CellCreationException;
import org.jdesktop.wonderland.client.cell.utils.CellUtils;
import org.jdesktop.wonderland.client.jme.ClientContextJME;
import org.jdesktop.wonderland.client.jme.artimport.DeployedModel;
import org.jdesktop.wonderland.client.jme.artimport.ImportSettings;
import org.jdesktop.wonderland.client.jme.artimport.ImportedModel;
import org.jdesktop.wonderland.client.jme.artimport.LoaderManager;
import org.jdesktop.wonderland.client.jme.artimport.ModelLoader;
import org.jdesktop.wonderland.client.login.LoginManager;
import org.jdesktop.wonderland.client.login.ServerSessionManager;
import org.jdesktop.wonderland.client.modules.ModuleUtils;
import org.jdesktop.wonderland.common.FileUtils;
import org.jdesktop.wonderland.common.cell.CellID;
import org.jdesktop.wonderland.common.login.AuthenticationInfo;
import org.jdesktop.wonderland.common.modules.ModuleInfo;
import org.jdesktop.wonderland.common.modules.ModuleList;
import org.jdesktop.wonderland.common.modules.ModuleUploader;
import org.jdesktop.wonderland.common.modules.utils.ModuleJarWriter;

/**
 * This class is fairly similar to the art importer, but is a little bit 
 * modified. The main purpose is to import a kmz file and build a 
 * module out of it.
 */
public class KMZImporter {
    
    private static final Logger LOGGER =
            Logger.getLogger(KMZImporter.class.getName());
    
    private ImportedModel importedModel = null;
    private Node rootBG;
    private KMZTransformProcessorComponent transformProcessor = null;
    
    private String moduleName = "";
    private ServerSessionManager targetServer = null;
    private CellID lastCellID = null;
    
    /**
     * Imports a kmz model. The model is stored here for later usage.
     * @param url The url of the model.
     */
    public void importKMZ(String url){
        
        try{
        File file = new File(url);
        
        ImportSettings importSettings = 
                new ImportSettings(file.toURI().toURL());
        importedModel = loadModel(importSettings);
        
        }catch(IOException e){
            LOGGER.log(Level.SEVERE, "Could not import KMZ file!", e);
        }
    }
    
    /**
     * Loads the model from a file
     * 
     * @param settings The import settings for the model.
     */
     
    private ImportedModel loadModel(ImportSettings settings) throws IOException {
        rootBG = new Node();

        URL url = settings.getModelURL();

        ModelLoader modelLoader =
                LoaderManager.getLoaderManager().getLoader(url);


        if (modelLoader == null) {
            //String urlString = url.toExternalForm();
            //String fileExtension = FileUtils.getFileExtension(urlString);
            return null;
        }

        ImportedModel loadedModel = modelLoader.importModel(settings);
        Node modelBG = loadedModel.getModelBG();

        rootBG.attachChild(modelBG);

        WorldManager wm = ClientContextJME.getWorldManager();

        RenderManager renderManager = wm.getRenderManager();
        //ZBufferState buf = (ZBufferState) renderManager.createRendererState(
        //        RenderState.RS_ZBUFFER);
        //buf.setEnabled(true);
        //buf.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);

        //MaterialState matState =
        //        (MaterialState) renderManager.createRendererState(
        //       RenderState.RS_MATERIAL);
        //matState.setDiffuse(color);
        //rootBG.setRenderState(matState);
        //rootBG.setRenderState(buf);

        Entity entity = new Entity(loadedModel.getWonderlandName());
        RenderComponent scene = renderManager.createRenderComponent(rootBG);
        entity.addComponent(RenderComponent.class, scene);

        scene.setLightingEnabled(loadedModel.getImportSettings().isLightingEnabled());
        transformProcessor = new KMZTransformProcessorComponent(wm, modelBG, rootBG);
        entity.addComponent(KMZTransformProcessorComponent.class,
                transformProcessor);

        wm.addEntity(entity);
        loadedModel.setEntity(entity);

//      findTextures(modelBG);

        return loadedModel;
    }
    
    /**
     * Returns the size of the latet model imported.
     * 
     * @return The models size as bounding volume.
     */
    public BoundingVolume getModelSize(){
        
        if(importedModel == null){
            LOGGER.log(Level.SEVERE, "No model was previously imported!");
            return null;
        }
        
        return importedModel.getModelBG().getWorldBound();
    }

    /**
     * Checks the given module name for a conflict.
     * 
     * @param moduleName The name of the module.
     * @param serverName The server, where the module will be saved.
     * @return True, if the name already exsists, false otherwise.
     */
    public boolean checkName(String moduleName, String serverName) {
        Collection<ServerSessionManager> servers = LoginManager.getAll();
        targetServer = null;
        
        for (ServerSessionManager server : servers) {
            if(server.getServerNameAndPort().equals(serverName)){
                targetServer = server;
                break;
            }
        }
        if(targetServer == null){
            LOGGER.log(Level.SEVERE, "Could not find a server to connect!");
            return true;
        }

        // Check we are not about to overwrite an existing module
        String url = targetServer.getServerURL();
        ModuleList moduleList = ModuleUtils.fetchModuleList(url);
        ModuleInfo[] modules = moduleList.getModuleInfos();
        
        if (modules != null) {
            for (ModuleInfo module : modules) {
                if (moduleName.equals(module.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Builds a new module, uploads it to the server and builds a cell
     * out of the module.
     * 
     * @param module_name The name of the new module.
     * @param name The name of the object.
     * @return 
     */
    public boolean importToServer(String module_name, String name) {
        
        if(targetServer == null){
            LOGGER.log(Level.SEVERE, "No target server was selected!");
            return false;
        }
        
        if(importedModel == null || module_name == null || module_name.equals("")){
            LOGGER.log(Level.SEVERE, "Imported Model == null or module "
                    + "name wrong{0}", module_name);
            return false;
        }
        moduleName = module_name;
        importedModel.setWonderlandName(name);
        
        /*
         * This is just not working correctly. Also it can be a little
         * buggy, so better invoke transformations later.
         */
        /*importedModel.setTranslation(new Vector3f(
                (float)x,(float)z,(float)y));
        importedModel.setScale(new Vector3f(
                (float)scale,(float)scale,(float)scale));
        importedModel.setOrientation(new Vector3f(
                (float)rotationX,(float)rotationZ,
                (float)rotationY));*/
        return deployToServer();
         
    }
    
    /**
     * Creates a module and uploads it to the server.
     * 
     * @return 
     */
    private boolean deployToServer(){
        
        final ArrayList<DeployedModel> deploymentInfo = new ArrayList();
        WorldManager wm = ClientContextJME.getWorldManager();
        final File moduleJar = createModuleJar(deploymentInfo, null);
        
        if(moduleJar == null){
            LOGGER.log(Level.SEVERE, "Deploy to Server: Could not create"
                    + " a module ");
            return false;
        }

        // Now deploy to server
        try {

            ModuleUploader uploader = new ModuleUploader(new URL(targetServer.getServerURL()));

            // if the authentication type is NONE, don't authenticate,
            // since the upload will only accept modules from an
            // administrator.
            // XXX TODO: we should fix this so that upload writes to
            // the content repository, so that non-admin users can
            // upload art when authenication is turned on XXX
            if (targetServer.getDetails().getAuthInfo().getType() !=
                   AuthenticationInfo.Type.NONE)
            {
               uploader.setAuthURL(targetServer.getCredentialManager().getAuthenticationURL());
            }

            uploader.upload(moduleJar);
        } catch (MalformedURLException ex) {
            LOGGER.log(Level.SEVERE, "Deploy to Server: malformed url{0}", ex);
            return false;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Deploy to Server: IOException{0}", e);
            return false;
        } catch (Throwable t) {
            LOGGER.log(Level.SEVERE, "Deploy to Server: Error{0}", t);
           return false;
        }

        // Now create the cells for the new content
        /*WonderlandSession session =
                LoginManager.getPrimary().getPrimarySession();
        CellEditChannelConnection connection =
               (CellEditChannelConnection) session.getConnection(
               CellEditConnectionType.CLIENT_TYPE);*/
        for (DeployedModel info : deploymentInfo) {
            try {
                lastCellID = CellUtils.createCell(info.getCellServerState());
            } catch (CellCreationException ex) {
                Logger.getLogger(KMZImporter.class.getName()).log(Level.SEVERE, null, ex);
            }
            /*CellCreateMessage msg = new CellCreateMessage(
                parentCellID, info.getCellServerState());
            connection.send(msg);*/
        }

        // Remove entities, once we create the cells on the server we
        // will be sent the client cells
        wm.removeEntity(importedModel.getEntity());
        
        importedModel = null;
        return true;
    }
    
    /**
     * Creates a module out of the last stored model.
     * 
     * @param deploymentInfo A list were the deployment info will be written.
     * @param targetDir The modelfile.
     * @return  The created module.
     */
    private File createModuleJar(
            ArrayList<DeployedModel> deploymentInfo, File targetDir) {

        File moduleJar = null;

        try {
            File tmpDir = File.createTempFile("wlart", null);
            if (tmpDir.isDirectory()) {
                FileUtils.deleteDirContents(tmpDir);
            } else {
                tmpDir.delete();
            }
            tmpDir.mkdir();
            tmpDir = new File(tmpDir, moduleName);

           
            try {
                importedModel.setDeploymentBaseURL("wla://"+moduleName+"/");
                deploymentInfo.add(importedModel.getModelLoader().deployToModule(
                        tmpDir, importedModel));
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Building module {0}", ex);
            }

            ModuleJarWriter mjw = new ModuleJarWriter();
            File[] dirs = tmpDir.listFiles();
            if (dirs != null) {
                for (File f : dirs) {
                    if (f.isDirectory()) {
                        mjw.addDirectory(f);
                    }
                }
            }
            ModuleInfo mi =
                    new ModuleInfo(moduleName, 1, 0, 0, "This is description");
            mjw.setModuleInfo(mi);
            try {
                if (targetDir == null) {
                    targetDir = tmpDir.getParentFile();
                }
                moduleJar = new File(targetDir, moduleName + ".jar");
                mjw.writeToJar(moduleJar);
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Building module {0}", ex);
            } catch (JAXBException ex) {
                LOGGER.log(Level.SEVERE, "Building module {0}", ex);
            }

            if (moduleJar == null) {
                LOGGER.log(Level.SEVERE, "Could not create a new Module Jar!");
                return null;
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Building module {0}", ex);
            return null;
        }

        return moduleJar;
    }  

    /**
     * Returns the id, which was given to the last created cell.
     * 
     * @return The id as long.
     */
    public long getLastID() {
        if(lastCellID == null)
            return -1;
        return Long.valueOf(lastCellID.toString());
    }

    /**
     * Removes the model and deletes the client side visualization of it.
     */
    public void clearModel() {
        try{
            WorldManager wm = ClientContextJME.getWorldManager();
            wm.removeEntity(importedModel.getEntity());
            importedModel = null;
        }catch(Exception e){
            LOGGER.log(Level.INFO, "Clear model exception", e);
        }
    }

    
}
