package org.jdesktop.wonderland.modules.oweditor.client.editor.data;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IImage;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IRights;

/**
 * This class houses all necessary data of an virtual world item and 
 * also contains the original coordinates.
 * 
 * @author Patrick
 *
 */
public class DataObject implements IDataObject{
    
    private Vector3D coords = null;
    private float width = 0;
    private float height = 0;
    
    private double rotationX = 0;
    private double rotationY = 0;
    private double rotationZ = 0;
    private double scale = 1;
    
    private long id = 0;
    private String name = "";
    private byte type = IDataObject.RECTANGLE;
    
    private IImage img = null;
    private ArrayList<IRights> rights = null;
    
    /**
     * Creates an empty object instance.
     */
    public DataObject(){
        coords = new Vector3D(0,0,0);
    }
    
    /**
     * Creates an object instance with the data given in the 
     * parameters.
     * 
     * @param id the object id.
     * @param x the object x coordinate.
     * @param y the object y coordinate.
     * @param z the object z coordinate.
     * @param rotationX the object roation in x axis.
     * @param rotationY the object rotation in y axis.
     * @param rotationZ the object rotation in z axis.
     * @param scale the objects scale.
     * @param width the objects width.
     * @param height the objects height.
     * @param name the objects name.
     */
    public DataObject(long id, float x, float y, float z, double rotationX, 
            double rotationY, double rotationZ, double scale, int width, 
            int height, String name){
        this.id = id;
        coords = new Vector3D(x,y,z);
        this.rotationX = rotationX;
        this.rotationY = rotationY;
        this.rotationZ = rotationZ;
        this.scale = scale;
        
        this.width = width;
        this.height = height;
        this.name = name;
    }
    
    @Override
    public void setCoordinates(float x, float y, float z){
        coords.x = x;
        coords.y = y;
        coords.z = z;
    }
    
    /**
     * Returns the coordinates of the object.
     * 
     * @return a 3D vector with the coordinates.
     */
    public Vector3D getCoordinates(){
        return coords;
    }
    
    @Override
    public double getScale(){
        return scale;
    }
    
    @Override
    public long getID(){
        return id;
    }
    
    @Override
    public float getWidthf(){
        return width;
    }
    
    @Override
    public float getHeightf(){
        return height;
    }

    @Override
    public float getXf() {
        return coords.x;
    }

    @Override
    public float getYf() {
        return coords.y;
    }

    @Override
    public float getZf() {
        return coords.z;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setX(float x) {
        coords.x = x;
        
    }

    @Override
    public void setY(float y) {
        coords.y = y;
        
    }

    @Override
    public void setZ(float z) {
        coords.z = z;
        
    }

    @Override
    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public void setID(long id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setScale(double scale) {
        this.scale = scale;
    }

    @Override
    public void setType(byte type) {
        this.type = type;
    }

    @Override
    public byte getType() {
        return type;
    }

    @Override
    public int getX() {
        return (int) Math.round(coords.x);
    }

    @Override
    public int getY() {
        return (int) Math.round(coords.y);
    }

    @Override
    public int getWidth() {
        return (int) Math.round(width);
    }

    @Override
    public int getHeight() {
        return (int) Math.round(height);
    }
        
    @Override
    public double getRotationX() {
        return rotationX;
    }
        
    @Override
    public double getRotationY() {
        return rotationY;
    }
        
    @Override
    public double getRotationZ() {
        return rotationZ;
    }
    
    @Override
    public void setRotationX(double rotation){
        rotationX = rotation;
    }
    
    @Override
    public void setRotationY(double rotation){
        rotationY = rotation;
    }
    
    @Override
    public void setRotationZ(double rotation){
        rotationZ = rotation;
    }

    @Override
    public double getRotation() {
        return rotationX;
    }
    
    @Override
    public void setImage(BufferedImage img, String name, String path){
        if(name == null)
            name = "";
        if(path == null)
            path = "";
        
        this.img = new Image(name, img, path);
    }
    
    public IImage getImgClass(){
        return img;
    }

    @Override
    public void setRight(String type, String name, boolean isOwner,
            boolean permitSubObjects, boolean permitAbilityChange,
            boolean permitMove, boolean permitView, boolean isEditable,
            boolean isEverybody) {
        if(rights == null)
             rights = new ArrayList<IRights>();
        
        IRights right = getRight(name, type);
        
        if(right != null){
            right.set(type, name, isOwner, permitSubObjects, 
                    permitAbilityChange, permitMove, 
                    permitView, isEditable, isEverybody);
        }else{
            right = new Rights(type, name, isOwner, 
                permitSubObjects, permitAbilityChange,
                permitMove, permitView, isEditable,
                isEverybody);
            
            rights.add(right);
        }     
    }
    
    public void rightComponentCreated(){
        if(rights == null)
            rights = new ArrayList<IRights>();
    }
    
    public void rightComponentRemoved(){
        rights = null;
    }

    @Override
    public ArrayList<IRights> getRights() {
        return rights;
    }

    @Override
    public IRights getRight(String name, String type) {
        
        for(IRights right : rights){
            if(right.getName().equals(name) &&
                    right.getType().equals(type)){
                return right;
            }
        }
        return null;
    }
    
    public void clearRights(){
        if(rights != null)
            rights.clear();
    }

    public void removeRight(String type, String name){
        
        for(IRights right : rights){
            if(right.getName().equals(name) &&
                    right.getType().equals(type)){
                rights.remove(right);
                return;
            }
        }
    }

}
