package controller.command;

import controller.command.user.SignInUserCommand;
import controller.command.user.SignUpUserCommand;

public class CommandProvider {
    public Command getCommand(String command) throws IllegalCommandException {
        switch (command) {
            case "signup":
                return new SignUpUserCommand();

            case "signin":
                return new SignInUserCommand();

            default:
                throw new IllegalCommandException();
        }
    }
}
