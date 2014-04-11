/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.common.cell.state.CellServerState;
import org.jdesktop.wonderland.modules.security.client.SecurityQueryComponent;
import org.jdesktop.wonderland.modules.security.common.ActionDTO;
import org.jdesktop.wonderland.modules.security.common.CellPermissions;
import org.jdesktop.wonderland.modules.security.common.Permission;
import org.jdesktop.wonderland.modules.security.common.Permission.Access;
import org.jdesktop.wonderland.modules.security.common.Principal;
import org.jdesktop.wonderland.modules.security.common.Principal.Type;
import org.jdesktop.wonderland.modules.security.common.SecurityComponentServerState;

/**
 * This class deals with the security module in setting and getting
 * the security parameters.
 * 
 * @author Patrick
 */
public class SecurityManager {
    
    private WonderlandAdapterController ac = null;
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    
    private String users = "users";
    
    //stores the actions. not very elaborate, but there is no
    //obvious way to create a new action.
    
    public SecurityManager(WonderlandAdapterController ac){
        this.ac = ac;
    }
    
    /**
     * Gets the security parameters of a cell.
     * 
     * @param cell The cell.
     * @return The rights settings in a linked hash map.
     */
    public LinkedHashMap<String, Right> getSecurity(Cell cell){
        
        LinkedHashMap<String, Right> map = 
                new LinkedHashMap<String, Right>();
        
        if(cell == null)
            return map;
        
        SecurityQueryComponent component =
                cell.getComponent(SecurityQueryComponent.class);
        
        if(component == null)
            return map;
       
        CellServerState cellServerState = ac.sc.fetchCellServerState(cell);
        
        if(cellServerState == null)
            return map ;
        
        SecurityComponentServerState state =
                (SecurityComponentServerState) cellServerState.getComponentServerState(
                SecurityComponentServerState.class);
        
        if(state == null)
            return map;
        CellPermissions perms = state.getPermissions();
        
        //get owners. 
        for (Principal p : perms.getOwners()) {
             
            Right right = new Right();
            right.name = p.getId();
             
            if(p.getType() == Principal.Type.EVERYBODY){
                users = p.getId();
                right.name = BUNDLE.getString("Everybody");
                right.type = BUNDLE.getString("Everybody");
                right.isEverybody = true;
            }else if(p.getType() == Principal.Type.GROUP){
                right.type = BUNDLE.getString("Group");
            }else if(p.getType() == Principal.Type.USER){
                right.type = BUNDLE.getString("User");
            }
             
            right.owner = true;
            right.permitAbilityChange = true;
            right.permitMove = true;
            right.permitSubObjects = true;
            right.permitView = true;
             
            map.put(p.getId(), right);
        }
        
        //get permissions other than owners
        for(Permission p : perms.getPermissions()) {
            String name = p.getPrincipal().getId();
             
            Right right = map.get(name);
             
            if(right == null){
                right = new Right();
                right.name = name;
                
                if(p.getPrincipal().getType() == Principal.Type.EVERYBODY){
                    users = p.getPrincipal().getId();
                    right.name = BUNDLE.getString("Everybody");
                    right.type = BUNDLE.getString("Everybody");
                    right.isEverybody = true;
                }else if(p.getPrincipal().getType() == Principal.Type.GROUP){
                    right.type = BUNDLE.getString("Group");
                }else if(p.getPrincipal().getType() == Principal.Type.USER){
                    right.type = BUNDLE.getString("User");
                }
                map.put(name, right);  
            }
            
            String action = p.getAction().getAction().getName();
            
            boolean access = true;
            if(p.getAccess() == Permission.Access.DENY )
                access = false;
            
            if(action.equals("Move")){
                right.permitMove = access;
            }else if(action.equals("View")){
                right.permitView = access;
            }else if(action.equals("ChangeCellChildren")){
                right.permitSubObjects = access;
            }else if(action.equals("ChangeCellComponent")){
                right.permitAbilityChange = access;
            }
         }
        
        return map;
    }

    /**
     * Changes one security entry.
     * 
     * @param cell The cell.
     * @param oldType The old user type.
     * @param oldName The old user/usergroup name.
     * @param type The new user type.
     * @param name The new user name.
     * @param owner Is owner.
     * @param addSubObjects Permission
     * @param changeAbilities Permission
     * @param move Permission
     * @param view Permission
     * @return The permission set.
     */
    public CellPermissions changeRight(Cell cell, String oldType, String oldName, 
            String type, String name, boolean owner, boolean addSubObjects, 
            boolean changeAbilities, boolean move, boolean view) {
        if(cell == null)
            return null;
        
        SecurityQueryComponent component =
                cell.getComponent(SecurityQueryComponent.class);
                
        if(component == null)
            return null;
       
        CellServerState cellServerState = ac.sc.fetchCellServerState(cell);
        
        if(cellServerState == null)
            return null;
        
        SecurityComponentServerState state =
                (SecurityComponentServerState) cellServerState.getComponentServerState(
                SecurityComponentServerState.class);
        
        if(state == null)
            return null;
        
        CellPermissions perms = state.getPermissions();
        
        if(oldName == null){
            addPerm(type, name, owner, addSubObjects, changeAbilities,
                            move, view, perms);
            return perms;
        }
        
        if(oldName.equals(BUNDLE.getString("Everybody"))){
            oldName = users;
        }
        
        //search in owners
        for (Principal p : perms.getOwners()) {
             
            if(p.getId().equals(oldName)){
                
                Type t = null;

                if(type.equals(BUNDLE.getString("User"))){
                    t = Type.USER;
                }else if(type.equals(BUNDLE.getString("Group"))){
                    t = Type.GROUP;
                }
                p.setId(name);

                if(t == null)
                    return perms;
                p.setType(t);
                
                if(!owner){
                    perms.getOwners().remove(p);
                    addPerm(type, name, owner, addSubObjects, changeAbilities,
                            move, view, perms);
                    return perms;
                }else{
                    return perms;
                }
            }
        }
        
        //search in permissions.
        Set<Permission> permission = new LinkedHashSet<Permission>();
        permission.addAll(perms.getPermissions());
        
        boolean found = false;
        
        for(Permission p : permission) {
            
            Principal princ = p.getPrincipal();
            
            if(princ.getId().equals(oldName)){
                
                if(found == false){
                    Type t = null;

                    if(type.equals(BUNDLE.getString("User"))){
                        t = Type.USER;
                    }else if(type.equals(BUNDLE.getString("Group"))){
                        t = Type.GROUP;
                    }
                    p.getPrincipal().setId(name);
                    oldName = name;

                    if(t == null)
                        return perms;
                    p.getPrincipal().setType(t);
                    found = true;
                }
                
                if(owner){
                    perms.getPermissions().remove(p);
                    if(!perms.getOwners().contains(princ)){
                        perms.getOwners().add(princ);
                    }
                }else{
                    String action = p.getAction().getAction().getName();
                    
                    Access access = Permission.Access.DENY;
                    if(action.equals("Move")){
                        if(move)
                            access = Permission.Access.GRANT;
                        
                        p.setAccess(access);
                    }else if(action.equals("View")){
                        if(view)
                            access = Permission.Access.GRANT;
                        
                        p.setAccess(access);
                    }else if(action.equals("ChangeCellChildren")){
                        if(addSubObjects)
                            access = Permission.Access.GRANT;
                        
                        p.setAccess(access);
                    }else if(action.equals("ChangeCellComponent")){
                        if(changeAbilities)
                            access = Permission.Access.GRANT;
                        
                        p.setAccess(access);
                    }
                }
            }
        }
        
        if(!found)
            addPerm(type, name, owner, addSubObjects, changeAbilities,
                            move, view, perms);
        return perms;
        
    }
    
    /**
     * Adds a permission to a set.
     * 
     * @param type The permission type.
     * @param name The name of the user/usergroup
     * @param owner Is owner.
     * @param addSubObjects Permission
     * @param changeAbilities Permission
     * @param move Permission
     * @param view Permission
     * @param perms The permission set to add the entry.
     */
    private void addPerm(String type, String name, boolean owner,
            boolean addSubObjects, 
            boolean changeAbilities, boolean move, boolean view,
            CellPermissions perms){
        
        Type t = null;

        if(type.equals(BUNDLE.getString("User"))){
            t = Type.USER;
        }else if(type.equals(BUNDLE.getString("Group"))){
            t = Type.GROUP;
        }
        
        ActionDTO moveAction = null;
        ActionDTO viewAction = null;
        ActionDTO changeCompAction = null;
        ActionDTO changeSubAction = null;
        
        for(ActionDTO a : perms.getAllActions()){
            if(a.getAction().getName().equals("Move"))
                moveAction = a;
            else if(a.getAction().getName().equals("View"))
                viewAction = a;
            else if(a.getAction().getName().equals("ChangeCellChildren"))
                changeSubAction = a;
            else if(a.getAction().getName().equals("ChangeCellComponent"))
                changeCompAction = a;  
        }

        if(t == null)
            return;
        Principal princ = new Principal(name, t);

        if(!owner){
            Access access = Permission.Access.DENY;
            if(move)
                access = Permission.Access.GRANT;
            Permission perm = new Permission(princ, moveAction, access);
            perms.getPermissions().add(perm);

            access = Permission.Access.DENY;
            if(view)
                access = Permission.Access.GRANT;
            perm = new Permission(princ, viewAction, access);
            perms.getPermissions().add(perm);

            access = Permission.Access.DENY;
            if(addSubObjects)
                access = Permission.Access.GRANT;
            perm = new Permission(princ, changeSubAction, access);
            perms.getPermissions().add(perm);

            access = Permission.Access.DENY;
            if(changeAbilities)
                access = Permission.Access.GRANT;
            perm = new Permission(princ, changeCompAction, access);
            perms.getPermissions().add(perm);
        }else{
            perms.getOwners().add(princ);
        }
    }

    /**
     * Removes one security entry.
     * 
     * @param cell The cell..
     * @param name The name of the security entry to remove.
     * @return The permissions.
     */
    public CellPermissions removeSecurity(Cell cell, String name) {
        if(cell == null)
            return null;
        
        SecurityQueryComponent component =
                cell.getComponent(SecurityQueryComponent.class);
                
        if(component == null)
            return null;
       
        CellServerState cellServerState = ac.sc.fetchCellServerState(cell);
        
        if(cellServerState == null)
            return null;
        
        SecurityComponentServerState state =
                (SecurityComponentServerState) cellServerState.getComponentServerState(
                SecurityComponentServerState.class);
        
        if(state == null)
            return null;
        
        CellPermissions perms = state.getPermissions();
        
        if(name.equals(BUNDLE.getString("Everybody"))){
            return null;
        }
        
        //search in owners
        for (Principal p : perms.getOwners()) {
            if(p.getId().equals(name)){
                perms.getOwners().remove(p);
                return perms;
            }
        }
        
        Set<Permission> permission = new LinkedHashSet<Permission>();
        permission.addAll(perms.getPermissions());
        
        for(Permission p : permission) {
            
            Principal princ = p.getPrincipal();
            
            if(princ.getId().equals(name)){
                perms.getPermissions().remove(p);
            }
        }
        
        return perms;
    }
    
    
    public class Right{
        protected String name = null;
        protected String type = null;
        protected boolean owner = false;
        protected boolean permitSubObjects = true;
        protected boolean permitAbilityChange = true;
        protected boolean permitMove = true;
        protected boolean permitView = true;
        protected boolean isEditable = true;
        protected boolean isEverybody = false;
    }
    
}
