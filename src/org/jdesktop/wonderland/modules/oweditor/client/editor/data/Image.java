package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import java.awt.image.BufferedImage;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IImage;

public  class Image implements IImage{
    
    private BufferedImage img = null;
    private String name = null;
    private String path = null;
    
    public Image(String name, BufferedImage img, String path){
        this.name = name;
        this.img = img;
        this.path = path;
    }

    @Override
    public BufferedImage getImage() {
        return img;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPath() {
        return path;
    }
    
}
