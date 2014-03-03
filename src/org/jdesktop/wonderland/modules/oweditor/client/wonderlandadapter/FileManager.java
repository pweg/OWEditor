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
import java.util.logging.Level;
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
     
     /**
      * Loads an image file to the users server directory.
      * 
      * @param file The image file to upload.
      * @param info The file info, wherre the directory and img name will be
      *         stored for later use.
      * @throws Exception 
      */
     public void uploadImage(File file, FileInfo info) 
             throws Exception{
         uploadFile(file,info, imgRootName);
     }
     
     /**
      * Downloads an image file to the client. It will overwrite an image,
      * if it already exists.
      * 
      * @param fileName The name of the image file.
      * @param fileDir The user directory, where the file is stored.
      * @return A buffered image, if success, null otherwise.
      * @throws Exception 
      */
     public BufferedImage downloadImage(String fileName, String fileDir) 
             throws Exception{
         
         InputStream is = downloadFile(fileName, fileDir, imgRootName);
         BufferedImage image = ImageIO.read(is);
         is.close();         
         return image;
     }
    
     /**
      * Loads a file to the users server directory. It will overwrite the file
      * if it exists.
      * 
      * @param file The file to be uploaded to the server.
      * @param info The file info, which is filled by this method and contains
      *         information of the files directory and its name.
      * @param dir The directory, where the file should be stored.
      * @throws Exception 
      */
    public void uploadFile(File file, FileInfo info, 
            String dir)throws Exception{
        String fileName = file.getName();
        info.fileName = fileName;
        
        ContentResource fileNode = getOwnFile(fileName, dir);
            
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
    
    /**
     * Downloads a file from the server.
     * 
     * @param fileName The name of the file.
     * @param dir The directory, where the file is found.
     * @param userDir The user directory of the file.
     * @return An input stream of the file.
     * @throws Exception 
     */
    public InputStream downloadFile(String fileName, String dir, 
            String userDir) throws Exception{
        
          ContentNode fileNode = getForeignFile(fileName, dir,
                        userDir);
          if(fileNode == null){
              LOGGER.warning("DOWNLOAD CONTENT: "
                      + "Could not get the necessary node for the file "+
                      fileName);
              throw new IOException();
          }
          
          return ((ContentResource) fileNode).getInputStream();
    }
    
    /**
     * Looks whether or not an image file exists.
     * 
     * @param fileName The name of the image file.
     * @return True if it exists, false otherwise.
     */
    public boolean imageFileExists(String fileName){
        return fileExists(fileName, imgRootName);
    }
    
    /**
     * Looks wheter or not a file exists.
     * 
     * @param fileName The file's name.
     * @param dirName The directory the file is in.
     * @return True if it exists, false otherwise.
     */
    public boolean fileExists(String fileName, String dirName){
        ContentRepositoryRegistry r = ContentRepositoryRegistry.getInstance();
        ServerSessionManager session = LoginManager.getPrimary();
        
        ContentCollection root;
         try {
            root = r.getRepository(session).getUserRoot();
            ContentNode directory = root.getChild(dirName);
            
            if(directory == null)
                return false;
            else if(!(directory instanceof ContentCollection))
                return false;
            
            ContentNode file = ((ContentCollection) directory).getChild(fileName);
            
            if(file == null)
                return false;
            
            return true;
            
         } catch (ContentRepositoryException ex) {
             Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
             return false;
         }
    }
    
    /**
     * Searches for a file in the current users directory and returns the 
     * content resource to this file. It will create a new file, if it does
     * not exist and overwrite existing files, if their instance is wrong.
     * 
     * @param filename The file's name.
     * @param dirName The directory
     * @return A content resource if the creation was successfull, null otherwise.
     * @throws ContentRepositoryException 
     */
    private ContentResource getOwnFile(String filename, String dirName) throws ContentRepositoryException {

        // Fetch the user's root using the current primary server. It should
        // be ok to use the primary server at this point
        ContentRepositoryRegistry r = ContentRepositoryRegistry.getInstance();
        ServerSessionManager session = LoginManager.getPrimary();

        // Try to find the pdf/ directory if it exists, otherwise, create it
        ContentCollection root = r.getRepository(session).getUserRoot();
        
        ContentNode directory = root.getChild(dirName);
        if (directory == null) {
            LOGGER.warning("IMG root is null");
                directory = root.createChild(dirName, ContentNode.Type.COLLECTION);
        }else if(!(directory instanceof ContentCollection)){
            directory.getParent().removeChild(dirName);
            directory = root.createChild(dirName, ContentNode.Type.COLLECTION);
        }
        if(directory == null){
            LOGGER.warning("Could not create img folder");
            throw new ContentRepositoryException();
        }
        
        ContentNode node = ((ContentCollection) directory).getChild(filename);
        if (node == null) {
            node = (ContentNode) ((ContentCollection)directory).createChild(
                    filename, ContentNode.Type.RESOURCE);
        } else if (!(node instanceof ContentResource)) {
            node.getParent().removeChild(filename);
            node = (ContentNode) ((ContentCollection)directory).createChild(
                    filename, ContentNode.Type.RESOURCE);
        }
        
        if(!(node instanceof ContentResource)){
            LOGGER.warning("The created node is not the instance of content resource");
            throw new ContentRepositoryException();
        }
        
        return (ContentResource) node;
    }
    
    /**
     * Searches for a file in another user's directory. 
     * 
     * @param filename The file name.
     * @param dirName The directories name.
     * @param userDir The name of the user's directory.
     * @return A content resource when successfull, null otherwise.
     * @throws ContentRepositoryException 
     */
    private ContentResource getForeignFile(String filename, String dirName, 
            String userDir)throws ContentRepositoryException{
         // Fetch the user's root using the current primary server. It should
        // be ok to use the primary server at this point
        ContentRepositoryRegistry r = ContentRepositoryRegistry.getInstance();
        ServerSessionManager session = LoginManager.getPrimary();

        // Try to find the pdf/ directory if it exists, otherwise, create it
        ContentCollection root = r.getRepository(session).getUserRoot().getParent();
        
        ContentCollection directory = (ContentCollection)(
                (ContentCollection)root.getChild(dirName)).getChild(userDir);
        
        ContentNode node = directory.getChild(filename);
        
        if(!(node instanceof ContentResource)){
            LOGGER.warning("Downloading File: File is not a Content Resource");
            return null;
        }
        return (ContentResource) node;
    }
    
}