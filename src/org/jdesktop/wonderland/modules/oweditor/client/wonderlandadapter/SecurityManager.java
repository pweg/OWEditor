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
import org.jdesktop.wonderland.common.security.Action;
import org.jdesktop.wonderland.modules.security.client.SecurityQueryComponent;
import org.jdesktop.wonderland.modules.security.common.ActionDTO;
import org.jdesktop.wonderland.modules.security.common.CellPermissions;
import org.jdesktop.wonderland.modules.security.common.Permission;
import org.jdesktop.wonderland.modules.security.common.Permission.Access;
import org.jdesktop.wonderland.modules.security.common.Principal;
import org.jdesktop.wonderland.modules.security.common.Principal.Type;
import org.jdesktop.wonderland.modules.security.common.SecurityComponentServerState;

/**
 *
 * @author Patrick
 */
public class SecurityManager {
    
    private static final Logger LOGGER =
            Logger.getLogger(SecurityManager.class.getName());
    
    private WonderlandAdapterController ac = null;
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    
    //stores the actions. not very elaborate, but there is no
    //obvious way to create a new action.
    private ActionDTO moveAction = null;
    private ActionDTO viewAction = null;
    private ActionDTO changeCompAction = null;
    private ActionDTO changeSubAction = null;
    
    public SecurityManager(WonderlandAdapterController ac){
        this.ac = ac;
    }
    
    
    public LinkedHashMap<String, Right> getSecurity(Cell cell){
        
        SecurityQueryComponent component =
                cell.getComponent(SecurityQueryComponent.class);
        
        LinkedHashMap<String, Right> map = 
                new LinkedHashMap<String, Right>();
        
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
        
        for (Principal p : perms.getOwners()) {
             
            Right right = new Right();
            right.name = p.getId();
             
            if(p.getType() == Principal.Type.EVERYBODY){
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
        
        for(Permission p : perms.getPermissions()) {
            String name = p.getPrincipal().getId();
             
            Right right = map.get(name);
             
            if(right == null){
                right = new Right();
                right.name = name;
                
                if(p.getPrincipal().getType() == Principal.Type.EVERYBODY){
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
                moveAction = p.getAction();
                right.permitMove = access;
            }else if(action.equals("View")){
                viewAction = p.getAction();
                right.permitView = access;
            }else if(action.equals("ChangeCellChildren")){
                changeSubAction = p.getAction();
                right.permitSubObjects = access;
            }else if(action.equals("ChangeCellComponent")){
                changeCompAction = p.getAction();
                right.permitAbilityChange = access;
            }
         }
        
        return map;
    }

    public CellPermissions changeRight(Cell cell, String oldType, String oldName, 
            String type, String name, boolean owner, boolean addSubObjects, 
            boolean changeAbilities, boolean move, boolean view) {
        
        LOGGER.warning("CHange right" + oldName);
        
        SecurityQueryComponent component =
                cell.getComponent(SecurityQueryComponent.class);
        
        LinkedHashMap<String, Right> map = 
                new LinkedHashMap<String, Right>();
        
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
        
        //search in owners
        for (Principal p : perms.getOwners()) {
             
            if(p.getId().equals(oldName)){
                LOGGER.warning("found owner");
                if(!owner){
                LOGGER.warning("not  owner anymore");
                    perms.getOwners().remove(p);
                    addPerm(type, name, owner, addSubObjects, changeAbilities,
                            move, view, perms);
                    return perms;
                }else{
                    
                    LOGGER.warning("still owner");
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
                
                LOGGER.warning("found perms");
                found = true;
                
                if(owner){
                    
                    LOGGER.warning("is owner now");
                    perms.getPermissions().remove(p);
                    if(!perms.getOwners().contains(princ)){
                        perms.getOwners().add(princ);
                    }
                }else{
                    
                    LOGGER.warning("normal");
                    String action = p.getAction().getAction().getName();
                    
                    Access access = Permission.Access.DENY;
                    if(action.equals("Move")){
                        if(moveAction == null)
                            moveAction = p.getAction();
                        if(move)
                            access = Permission.Access.GRANT;
                        
                        p.setAccess(access);
                    }else if(action.equals("View")){
                        if(viewAction == null)
                            viewAction = p.getAction();
                        if(view)
                            access = Permission.Access.GRANT;
                        
                        p.setAccess(access);
                    }else if(action.equals("ChangeCellChildren")){
                        if(changeSubAction == null)
                            changeSubAction = p.getAction();
                        if(addSubObjects)
                            access = Permission.Access.GRANT;
                        
                        p.setAccess(access);
                    }else if(action.equals("ChangeCellComponent")){
                        if(changeCompAction == null)
                            changeCompAction = p.getAction();
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
