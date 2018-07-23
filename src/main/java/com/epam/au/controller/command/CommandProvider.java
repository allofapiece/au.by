package com.epam.au.controller.command;

import com.epam.au.controller.command.account.ConnectBankAccountCommand;
import com.epam.au.controller.command.account.ShowBankAccountCommand;
import com.epam.au.controller.command.product.AddProductCommand;
import com.epam.au.controller.command.product.DeleteProductCommand;
import com.epam.au.controller.command.product.ShowProductsCommand;
import com.epam.au.controller.command.user.LogOutCommand;
import com.epam.au.controller.command.user.SignInUserCommand;
import com.epam.au.controller.command.user.SignUpUserCommand;

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

            case "product-add":
                return new AddProductCommand();

            case "product-show":
                return new ShowProductsCommand();

            case "product-delete":
                return new DeleteProductCommand();

            default:
                throw new IllegalCommandException();
        }
    }
}
