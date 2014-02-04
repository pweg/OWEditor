/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.bounding.BoundingVolume;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.RenderState;
import com.jme.scene.state.ZBufferState;
import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.xml.bind.JAXBException;
import org.jdesktop.mtgame.Entity;
import org.jdesktop.mtgame.RenderComponent;
import org.jdesktop.mtgame.RenderManager;
import org.jdesktop.mtgame.WorldManager;
import org.jdesktop.wonderland.client.cell.CellEditChannelConnection;
import org.jdesktop.wonderland.client.comms.WonderlandSession;
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
import org.jdesktop.wonderland.common.cell.CellEditConnectionType;
import org.jdesktop.wonderland.common.cell.CellID;
import org.jdesktop.wonderland.common.cell.messages.CellCreateMessage;
import org.jdesktop.wonderland.common.login.AuthenticationInfo;
import org.jdesktop.wonderland.common.modules.ModuleInfo;
import org.jdesktop.wonderland.common.modules.ModuleList;
import org.jdesktop.wonderland.common.modules.ModuleUploader;
import org.jdesktop.wonderland.common.modules.utils.ModuleJarWriter;

/**
 *
 * @author Patrick
 */
public class KMZImporter {
    
    private static final Logger LOGGER =
            Logger.getLogger(KMZImporter.class.getName());
    
    private ImportedModel importedModel = null;
    private Node rootBG;
    private TransformProcessorComponent transformProcessor = null;
    
    private String moduleName = "";
    private ServerSessionManager targetServer = null;
    
    public void importKMZ(String url){
        
        try{
        File file = new File(url);
        
        ImportSettings importSettings = 
                new ImportSettings(file.toURI().toURL());
        importedModel = loadModel(importSettings);
        
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "Could not import KMZ file!", e);
        }
    }
    
        /**
     * Load model from file
     * 
     * @param origFile
     */
     
    ImportedModel loadModel(ImportSettings settings) throws IOException {
        rootBG = new Node();

        URL url = settings.getModelURL();

        Node modelBG = null;

        ModelLoader modelLoader =
                LoaderManager.getLoaderManager().getLoader(url);


        if (modelLoader == null) {
            String urlString = url.toExternalForm();
            //String fileExtension = FileUtils.getFileExtension(urlString);
            return null;
        }

        ImportedModel loadedModel = modelLoader.importModel(settings);
        modelBG = loadedModel.getModelBG();

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
//        matState.setDiffuse(color);
        //rootBG.setRenderState(matState);
        //rootBG.setRenderState(buf);

        Entity entity = new Entity(loadedModel.getWonderlandName());
        RenderComponent scene = renderManager.createRenderComponent(rootBG);
        entity.addComponent(RenderComponent.class, scene);

        scene.setLightingEnabled(loadedModel.getImportSettings().isLightingEnabled());
        transformProcessor = new TransformProcessorComponent(wm, modelBG, rootBG);
        entity.addComponent(TransformProcessorComponent.class,
                transformProcessor);

        wm.addEntity(entity);
        loadedModel.setEntity(entity);

//        findTextures(modelBG);

        return loadedModel;
    }
    
    public int[] getModelSize(CoordinateTranslator translator){
        
        if(importedModel == null){
            LOGGER.log(Level.SEVERE, "No model was previously imported!");
            return null;
        }
        
        BoundingVolume bounds = importedModel.getModelBG().getWorldBound();
        return translator.transformSize(bounds);
    }

    public boolean setProperties(String name, String image_url, 
            double x, double y, double z, 
            double rotationX, double rotationY, double rotationZ, 
            double scale) {
        
        Collection<ServerSessionManager> servers = LoginManager.getAll();
        targetServer = null;
        
        //@Todo: Make a selection option in the gui!
        for (ServerSessionManager server : servers) {
            targetServer = server;
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
            boolean conflict = false;
            for (int i = 0; i < modules.length && !conflict; i++) {
                if (name.equals(modules[i].getName())) {
                    return false;
                }
            }
        }
        
        moduleName = name;
        importedModel.setWonderlandName(name);
        //Remember the changed y and z values.
        importedModel.setTranslation(new Vector3f(
                (float)x,(float)z,(float)y));
        importedModel.setScale(new Vector3f(
                (float)scale,(float)scale,(float)scale));
        importedModel.setOrientation(new Vector3f(
                (float)rotationX,(float)rotationY,
                (float)rotationZ));
        deployToServer();
        return true;
    }
    
    public void deployToServer(){
        
        
        final ArrayList<DeployedModel> deploymentInfo = new ArrayList();
        WorldManager wm = ClientContextJME.getWorldManager();
        final File moduleJar = createModuleJar(deploymentInfo, null);

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
                
            return;
        } catch (IOException e) {
                    
            return;
        } catch (Throwable t) {
                    
           return;
        }

        // Now create the cells for the new content
        WonderlandSession session =
                LoginManager.getPrimary().getPrimarySession();
        CellEditChannelConnection connection =
               (CellEditChannelConnection) session.getConnection(
               CellEditConnectionType.CLIENT_TYPE);
        for (DeployedModel info : deploymentInfo) {
            CellID parentCellID = null;
            CellCreateMessage msg = new CellCreateMessage(
                parentCellID, info.getCellServerState());
            connection.send(msg);
        }

        // Remove entities, once we create the cells on the server we
        // will be sent the client cells
        wm.removeEntity(importedModel.getEntity());
        

        importedModel = null;
    }
    
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
            } catch (JAXBException ex) {
            }

            if (moduleJar == null) {
                LOGGER.log(Level.SEVERE, "Could not create a new Module Jar!");
                return null;
            }
        } catch (IOException ex) {
            return null;
        }

        return moduleJar;

}  

    
}
