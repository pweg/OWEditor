package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

public class BackupObject {
    
    private double rotationX = 0;
    private double rotationY = 0;
    private double rotationZ = 0;
    
    private float translationX = 0;
    private float translationY = 0;
    private float translationZ = 0;
    
    private double scaleX = 0;
    private double scaleY = 0;
    private double scaleZ = 0;
    
    private boolean toDelete = false;
    
    private ServerObject object = null;
    
    public BackupObject(ServerObject object, double rotationX, double rotationY,
            double rotationZ, double scaleX, double scaleY, double scaleZ){
        this.object = object;
        this.rotationX = rotationX;
        this.rotationY = rotationY;
        this.rotationZ = rotationZ;
        
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
    }
    
    public void setTranslation(float x, float y, float z){
        translationX = x;
        translationY = y;
        translationZ = z;
    }
    
    public ServerObject getObject(){
        return object;
    }
    
    public float getTranslationX(){
        return translationX;
    }
    
    public float getTranslationY(){
        return translationY;
    }
    
    public float getTranslationZ(){
        return translationZ;
    }
    
    public double getRotationX(){
        return rotationX;
    }
    
    public double getRotationY(){
        return rotationY;
    }
    
    public double getRotationZ(){
        return rotationZ;
    }
    
    public double getScaleX(){
        return scaleX;
    }

    
    public double getScaleY(){
        return scaleY;
    }

    
    public double getScaleZ(){
        return scaleZ;
    }
    
    public void setDeletion(boolean delete){
        toDelete = delete;
    }
    
    public boolean isForDeletion(){
        return toDelete;
    }

}
