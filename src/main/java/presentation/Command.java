/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import exception.CommandException;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
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
