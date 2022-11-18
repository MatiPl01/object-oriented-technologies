package command;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommandRegistry {
    private final ObservableList<Command> commandStack = FXCollections
            .observableArrayList();
    private final ObservableList<Command> undoneCommandStack = FXCollections
            .observableArrayList();

    public void executeCommand(Command command) {
        command.execute();
        commandStack.add(command);
        undoneCommandStack.clear();
    }

    public void redo() {
        if (undoneCommandStack.size() == 0) return;
        int lastIndex = undoneCommandStack.size() - 1;
        Command command = undoneCommandStack.get(lastIndex);
        undoneCommandStack.remove(lastIndex);
        commandStack.add(command);
        command.redo();
    }

    public void undo() {
        if (commandStack.size() == 0) return;
        int lastIndex = commandStack.size() - 1;
        Command command = commandStack.get(lastIndex);
        commandStack.remove(lastIndex);
        undoneCommandStack.add(command);
        command.undo();
    }

    public ObservableList<Command> getCommandStack() {
        return commandStack;
    }
}
