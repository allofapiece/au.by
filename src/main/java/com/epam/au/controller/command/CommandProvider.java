package com.epam.au.controller.command;

import com.epam.au.controller.command.account.ConnectBankAccountCommand;
import com.epam.au.controller.command.account.ShowBankAccountCommand;
import com.epam.au.controller.command.lot.AddLotCommand;
import com.epam.au.controller.command.lot.LoadLotsCommand;
import com.epam.au.controller.command.lot.LoadUsersCommand;
import com.epam.au.controller.command.lot.ShowLotsCommand;
import com.epam.au.controller.command.product.*;
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

            case "user-load":
                return new LoadUsersCommand();

            case "account-connect":
                return new ConnectBankAccountCommand();

            case "account-show":
                return new ShowBankAccountCommand();

            case "product-add":
                return new AddProductCommand();

            case "product-add-ajax":
                return new AddProductAjaxCommand();

            case "product-show":
                return new ShowProductsCommand();

            case "product-delete":
                return new DeleteProductCommand();

            case "product-search":
                return new SearchProductsCommand();

            case "lot-delete":
                return new DeleteProductCommand();

            case "lot-add":
                return new AddLotCommand();

            case "lot-show":
                return new ShowLotsCommand();

            case "lot-load":
                return new LoadLotsCommand();


            default:
                throw new IllegalCommandException();
        }
    }
}
