package org.goober.server.commands;

public interface Command {

    /**
     * Executes the command with the given arguments.
     *
     * @param args the arguments passed to the command
     * @return the result or message after executing the command
     */
    String execute(String[] args);

    /**
     * Provides a short description of the command.
     *
     * @return a brief description of the command
     */
    String getDescription();

    /**
     * Provides the usage information for the command.
     *
     * @return a string describing how to use the command
     */
    String getUsage();
}


