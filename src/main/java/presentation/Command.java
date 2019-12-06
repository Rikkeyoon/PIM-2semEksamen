package presentation;

import exception.CommandException;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The purpose of Command is to carry out an action It keeps a list of html
 * actions mapped to related commands (Polymorfi)
 *
 * Pattern: Command
 *
 * @author carol
 */
public abstract class Command {

    private static HashMap<String, Command> commands;

    /**
     * A private method to initiate the commands Map
     */
    private static void initCommands() {
        commands = new HashMap<>();
        commands.put("get_view", new ViewPageCommand());
        commands.put("create_product", new CreateProductCommand());
        commands.put("update_product", new UpdateProductCommand());
        commands.put("delete_product", new DeleteProductCommand());
        commands.put("search_product", new SearchProductCommand());
        commands.put("create_category", new CreateCategoryCommand());
        commands.put("edit_category", new EditCategoryCommand());
        commands.put("download_catalog", new DownloadCatalogCommand());
        commands.put("download_categories", new DownloadCategoriesCommand());
        commands.put("download_product", new DownloadProductCommand());
        commands.put("upload_json", new UploadJSONCommand());
        commands.put("bulkedit", new BulkEditCommand());
    }

    /**
     * A method to get the specific Command associated with the request or a
     * default UnknownCommand
     *
     * @param request servlet request
     * @return a Command, which relate to the request param, or a default
     * UnknownCommand
     */
    static Command from(HttpServletRequest request) {
        String cmd = request.getParameter("cmd");
        if (commands == null) {
            initCommands();
        }
        return commands.getOrDefault(cmd, new UnknownCommand());
    }

    /**
     * A method to execute the Command's task
     *
     * @param request servlet request
     * @param response servlet response
     * @throws CommandException if an error occurs
     */
    abstract String execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException;
}
