package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.AdapterMainInterface;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IAdapterObserver;

/**
 * The adapter controller class, which initiallizes the adapter.
 * @author Patrick
 *
 */
public class DummyAdapterController implements AdapterMainInterface{
    
    
    protected GUIEventManager cua = null;
    protected ServerEventManager sem = null;
    protected ServerSimulator server = null;
    protected CoordinateTranslator ct = null;
    protected BackupManager bom = null;
    protected ImageManager im = null;
    
    public DummyAdapterController(){
        
    }

    @Override
    public void initialize() {
        cua = new GUIEventManager(this);
        sem = new ServerEventManager(this);
        server = new ServerSimulator();
        ct = new CoordinateTranslator();
        bom = new BackupManager();
        im = new ImageManager();
        
        registerComponents();
    }
    
    private void registerComponents(){
        server.registerServerUpdate(sem);
    }

    @Override
    public void registerDataUpdateInterface(IAdapterObserver i) {
       sem.setDataUpdateInterface(i);
        
    }

    @Override
    public GUIObserverInterface getClientUpdateInterface() {
        return cua;
    }

    @Override
    public void getCurrentWorld() {
        
        sem.setUserDir();

        for(ImageClass img : getImgLib(new File(AdapterSettings.IMAGEDIR))){
            im.addImage(AdapterSettings.IMAGEDIR, img.name, img.img);
            sem.updateImgLib(img.img, img.name);
        }
        
        WorldBuilder builder = new WorldBuilder(this, sem);
        builder.build();
    }
    
    private ArrayList<ImageClass> getImgLib(final File folder){
        ArrayList<ImageClass> imgs = new ArrayList<ImageClass>();
        
        if(folder == null || !folder.exists())
            return imgs;
        
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                imgs.addAll(getImgLib(fileEntry));
            } else {
                try {
                    BufferedImage img = ImageIO.read(fileEntry);
                    ImageClass i = new ImageClass();
                    i.img = img;
                    i.name = fileEntry.getName();
                    imgs.add(i);
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        }
        return imgs;
    }

    @Override
    public CoordinateTranslatorInterface getCoordinateTranslator() {
        return ct;
    }
    
    class ImageClass{
        protected BufferedImage img = null;
        protected String  name = null;
    }

}
