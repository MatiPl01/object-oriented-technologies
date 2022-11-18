package controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import command.AddTransactionCommand;
import command.RemoveTransactionCommand;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import command.Command;
import command.CommandRegistry;
import model.Account;
import model.Category;
import model.Transaction;

public class AccountOverviewController {

    private Account data;

    private AccountAppController appController;

    private CommandRegistry commandRegistry;

    @FXML
    private TableView<Transaction> transactionsTable;

    @FXML
    private TableColumn<Transaction, LocalDate> dateColumn;

    @FXML
    private TableColumn<Transaction, String> payeeColumn;

    @FXML
    private TableColumn<Transaction, Category> categoryColumn;

    @FXML
    private TableColumn<Transaction, String> memoColumn;

    @FXML
    private TableColumn<Transaction, BigDecimal> outflowColumn;

    @FXML
    private TableColumn<Transaction, BigDecimal> inflowColumn;

    @FXML
    private TableColumn<Transaction, BigDecimal> balanceColumn;

    @FXML
    private ListView<Command> commandLogView;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button addButton;

    @FXML
    private Button undoButton;

    @FXML
    private Button redoButton;

    @FXML
    private void initialize() {
        transactionsTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE);

        dateColumn.setCellValueFactory(dataValue -> dataValue.getValue()
                                                             .getDateProperty());
        payeeColumn.setCellValueFactory(dataValue -> dataValue.getValue()
                                                              .getPayeeProperty());
        categoryColumn.setCellValueFactory(dataValue -> dataValue.getValue()
                                                                 .getCategoryProperty());
        inflowColumn.setCellValueFactory(dataValue -> dataValue.getValue()
                                                               .getInflowProperty());
        deleteButton.disableProperty().bind(
                Bindings.isEmpty(transactionsTable.getSelectionModel()
                                                  .getSelectedItems()));

        editButton.disableProperty().bind(
                Bindings.size(
                        transactionsTable.getSelectionModel()
                                         .getSelectedItems()).isNotEqualTo(1));
    }

    @FXML
    private void handleDeleteAction() {
        for (Transaction transaction : List.copyOf(transactionsTable.getSelectionModel()
                                                               .getSelectedItems())) {
            RemoveTransactionCommand removeTransactionCommand =
                    new RemoveTransactionCommand(transaction, data);
            removeTransactionCommand.execute();
        }
    }

    @FXML
    private void handleEditAction() {
        Transaction transaction = transactionsTable.getSelectionModel()
                                                   .getSelectedItem();
        if (transaction != null) {
            appController.showTransactionEditDialog(transaction);
        }
    }

    @FXML
    private void handleAddAction() {
        Transaction transaction = Transaction.newTransaction();

        if (appController.showTransactionEditDialog(transaction)) {
            AddTransactionCommand
                    addTransactionCommand =
                    new AddTransactionCommand(transaction, data);
            commandRegistry.executeCommand(addTransactionCommand);
        }
    }

    @FXML
    private void handleUndoAction() {
        commandRegistry.undo();
    }

    @FXML
    private void handleRedoAction() {
        commandRegistry.redo();
    }

    public void setData(Account account) {
        this.data = account;
        transactionsTable.setItems(data.getTransactions());
    }

    public void setAppController(AccountAppController appController) {
        this.appController = appController;
    }

    public void setCommandRegistry(CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
        commandLogView.setItems(commandRegistry.getCommandStack());
        commandLogView.setCellFactory(lv -> new ListCell<>() {
            protected void updateItem(Command item, boolean empty) {
                super.updateItem(item, empty);
                setText((item != null && !empty) ? item.getName() : null);
            }

            ;
        });
    }
}
