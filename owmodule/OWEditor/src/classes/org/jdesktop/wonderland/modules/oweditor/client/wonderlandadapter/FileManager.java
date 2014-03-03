/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.jdesktop.wonderland.client.login.LoginManager;
import org.jdesktop.wonderland.client.login.ServerSessionManager;
import org.jdesktop.wonderland.modules.contentrepo.client.ContentRepositoryRegistry;
import org.jdesktop.wonderland.modules.contentrepo.common.ContentCollection;
import org.jdesktop.wonderland.modules.contentrepo.common.ContentNode;
import org.jdesktop.wonderland.modules.contentrepo.common.ContentRepositoryException;
import org.jdesktop.wonderland.modules.contentrepo.common.ContentResource;
import org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.components.ImageCellComponentProperties;

/**
 *
 * @author Patrick
 */

public class FileManager {
    
    
    
     private static final Logger LOGGER =
            Logger.getLogger(ImageCellComponentProperties.class.getName());
     
     private final String imgRootName = "img";
     
     public void uploadImage(File file, FileInfo info) 
             throws Exception{
         uploadFile(file,info, imgRootName);
     }
     
     public BufferedImage downloadImage(String fileName, String fileDir) 
             throws Exception{
         
         InputStream is = downloadFile(fileName, fileDir, imgRootName);
         BufferedImage image = ImageIO.read(is);
         is.close();         
         return image;
     }
    
    public void uploadFile(File file, FileInfo info, 
            String root)throws Exception{
        String fileName = file.getName();
        info.fileName = fileName;
        LOGGER.warning("uploading file");
        
        ContentResource fileNode = getOwnFile(fileName, root);
            
        if(fileNode == null){
            LOGGER.warning("UPLOAD CONTENT: "
                    + "Could not create the necessary node for the file "+
                    fileName);
            throw new IOException();
        }
            
        InputStream is = new FileInputStream(file);

        fileNode.put(is);
        is.close();
            
        info.fileDir = fileNode.getParent().getParent().getName();
    }
    
    public InputStream downloadFile(String fileName, String dir, 
            String rootName) throws Exception{
        
          ContentNode fileNode = getForeignFile(fileName, dir,
                        rootName);
          if(fileNode == null){
              LOGGER.warning("DOWNLOAD CONTENT: "
                      + "Could not get the necessary node for the file "+
                      fileName);
              throw new IOException();
          }
          
          return ((ContentResource) fileNode).getInputStream();
    }
    
    public ContentResource getOwnFile(String filename, String rootName) throws ContentRepositoryException {

        // Fetch the user's root using the current primary server. It should
        // be ok to use the primary server at this point
        ContentRepositoryRegistry r = ContentRepositoryRegistry.getInstance();
        ServerSessionManager session = LoginManager.getPrimary();

        // Try to find the pdf/ directory if it exists, otherwise, create it
        ContentCollection root = r.getRepository(session).getUserRoot();
        
        ContentNode imgRoot = root.getChild(rootName);
        if (imgRoot == null) {
            LOGGER.warning("IMG root is null");
                imgRoot = root.createChild(rootName, ContentNode.Type.COLLECTION);
        }else if(!(imgRoot instanceof ContentCollection)){
            imgRoot.getParent().removeChild(rootName);
            imgRoot = root.createChild(rootName, ContentNode.Type.COLLECTION);
        }
        if(imgRoot == null){
            LOGGER.warning("Could not create img folder");
            throw new ContentRepositoryException();
        }
        
        ContentNode node = root.getChild(filename);
        if (node == null) {
            node = (ContentNode) ((ContentCollection)imgRoot).createChild(
                    filename, ContentNode.Type.RESOURCE);
        } else if (!(node instanceof ContentResource)) {
            node.getParent().removeChild(filename);
            node = (ContentNode) ((ContentCollection)imgRoot).createChild(
                    filename, ContentNode.Type.RESOURCE);
        }
        
        if(!(node instanceof ContentResource)){
            LOGGER.warning("The created node is not the instance of content resource");
            throw new ContentRepositoryException();
        }
        
        return (ContentResource) node;
    }
    
    public ContentResource getForeignFile(String filename, String dir, 
            String rootName)throws ContentRepositoryException{
         // Fetch the user's root using the current primary server. It should
        // be ok to use the primary server at this point
        ContentRepositoryRegistry r = ContentRepositoryRegistry.getInstance();
        ServerSessionManager session = LoginManager.getPrimary();

        // Try to find the pdf/ directory if it exists, otherwise, create it
        ContentCollection root = r.getRepository(session).getUserRoot().getParent();
        
        ContentCollection imgRoot = (ContentCollection)(
                (ContentCollection)root.getChild(dir)).getChild(rootName);
        
        ContentNode node = imgRoot.getChild(filename);
        
        if(!(node instanceof ContentResource)){
            LOGGER.warning("Downloading File: File is not a Content Resource");
            return null;
        }
        return (ContentResource) node;
    }
    
}
