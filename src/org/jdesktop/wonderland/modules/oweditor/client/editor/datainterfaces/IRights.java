package org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces;

public interface IRights {
    
    /**
     * Returns the type of rights owner, aka 
     * user, usergroup, or everybody.
     * 
     * @return A string containing the type
     */
    public String getType();
    
    /**
     * Returns the name of the user/usergroup.
     * 
     * @return A string with the name.
     */
    public String getName();
    
    /**
     * Returns whether the user/usergroup is
     * the owner of the object.
     * 
     * @return True, if the user is the owner, false
     * otherwise.
     */
    public boolean getOwner();
    
    /**
     * Returns whether the user/usergroup is permitted
     * to change object abilities.
     * 
     * @return True if changing abilities is allowed,
     * false otherwise.
     */
    public boolean getPermChangeAbilities();
    
    /**
     * Returns whether the user/usergroup is permitted
     * to add sub objects to the object.
     * 
     * @return True if adding/changing sub object is
     * allowed, false otherwise.
     */
    public boolean getPermAddSubObjects();
    
    /**
     * Returns whether the user/usergroup is permitted
     * to move the object.
     * 
     * @return True, if the user is allowed to move the 
     * object, false otherwise.
     */
    public boolean getPermMove();
    
    /**
     * Returns whether the user/usergroup is permitted
     * to view the object.
     * 
     * @return True, if the user is allowed to view the object,
     * false otherwise.
     */
    public boolean getPermView();

}
