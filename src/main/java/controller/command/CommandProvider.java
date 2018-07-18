package controller.command;

import controller.command.account.ConnectBankAccountCommand;
import controller.command.account.ShowBankAccountCommand;
import controller.command.user.LogOutCommand;
import controller.command.user.SignInUserCommand;
import controller.command.user.SignUpUserCommand;

public class CommandProvider {
    public Command getCommand(String command) throws IllegalCommandException {
        switch (command) {
            case "signup":
                return new SignUpUserCommand();

            case "signin":
                return new SignInUserCommand();

            case "logout":
                return new LogOutCommand();

            case "account-connect":
                return new ConnectBankAccountCommand();

            case "account-show":
                return new ShowBankAccountCommand();

            default:
                throw new IllegalCommandException();
        }
    }
}
