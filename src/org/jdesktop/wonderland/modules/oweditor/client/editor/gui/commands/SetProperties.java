package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;

public class SetProperties implements Command{
    
    private ArrayList<Command> commands = null;
    
    /**
     * Creates a new properties changed command.
     * 
     * @param translation All translation commands.
     * @param rotation All rotation commands.
     * @param scale All scale commands.
     * @param setImage All set image commands.
     * @param furtherCommands All further commands.
     */
    public SetProperties(Command translation, Command rotation,
            Command scale, Command setImage, ArrayList<Command> furtherCommands){
        
        commands = new ArrayList<Command>();
        
        //rotation needs to be done first, then translation and then scaling!
        if(rotation != null)
            commands.add(rotation);
        if(translation != null)
            commands.add(translation);
        if(scale != null)
            commands.add(scale);
        if(setImage != null)
            commands.add(setImage);
        if(furtherCommands != null)
            commands.addAll(furtherCommands);
    }

    @Override
    public void execute(GUIObserverInterface goi) throws Exception {
        for(Command command : commands)
            command.execute(goi);
    }

    @Override
    public void undo(GUIObserverInterface goi) throws Exception {
        for(Command command : commands)
            command.undo(goi);
    }

    @Override
    public void redo(GUIObserverInterface goi) throws Exception {
        for(Command command : commands)
            command.redo(goi);
    }
    
    
}
