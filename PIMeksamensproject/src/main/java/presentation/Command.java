package presentation;

import exception.CommandException;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The purpose of Command is to carry out an action 
 * It keeps a list of html actions mapped to related commands (Polymorfi)
 *
 * Pattern: Command
 *
 * @author carol
 */
public abstract class Command {
    private static HashMap<String, Command> commands;
    
    private static void initCommands() {
        commands = new HashMap<>();
    }
    
    /**
     *
     * @param request servlet request
     * @return a Command, which relate to the request param, or a default UnknownCommand
     */
    static Command from(HttpServletRequest request) {
        String cmd = request.getParameter("cmd");
        if (commands == null) {
            initCommands();
        }
        return commands.getOrDefault(cmd, new UnknownCommand());
    }
    
    /**
     *
     * @param request servlet request
     * @param response servlet response
     * @throws CommandException if an error occurs
     */
    abstract String execute(HttpServletRequest request, HttpServletResponse response)
           throws CommandException;
}
