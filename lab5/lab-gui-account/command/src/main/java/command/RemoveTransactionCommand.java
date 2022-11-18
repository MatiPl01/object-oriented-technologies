package command;

import model.Account;
import model.Transaction;

public class RemoveTransactionCommand implements Command {
    private final Transaction transactionToRemove;
    private final Account account;

    public RemoveTransactionCommand(Transaction transactionToRemove, Account account) {
        this.transactionToRemove = transactionToRemove;
        this.account = account;
    }

    @Override
    public void execute() {
        account.removeTransaction(transactionToRemove);
    }

    @Override
    public String getName() {
        return "New transaction: " + transactionToRemove.toString();
    }

    @Override
    public void undo() {
        account.removeTransaction(transactionToRemove);
    }

    @Override
    public void redo() {
        account.addTransaction(transactionToRemove);
    }
}
