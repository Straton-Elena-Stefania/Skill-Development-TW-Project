package com.cooking.controllers.commands;

public abstract class Command {
    protected String commandName;

    public Command(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public boolean matches(String commandName) {
        if (commandName.matches(this.commandName)) {
            return true;
        }
        return false;
    }

    public abstract Object run(String commandName);
}
