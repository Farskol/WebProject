package com.finalwebproject.pastrtyshop.controller.factory;

import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.controller.command.impl.*;


import java.util.Optional;

public enum CommandType {
    SIGN_IN(new SignInCommand()),
    SIGN_OUT(new SignOutCommand()),
    ADD_DESSERT(new AddDessertCommand()),
    ADD_CAKE(new AddCakeCommand()),
    DELETE_DESSERT_IN_LIST(new DeleteDessertInListCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    REGISTRATION(new RegistrationCommand()),
    CREATE_ORDER(new CreateOrderCommand()),
    EDIT_DESSERT(new EditDessertCommand()),
    EDIT_DESSERT_PARAMETERS(new EditDessertParametersCommand()),
    CREATE_DESSERT(new CreateDessertCommand()),
    DELETE_ORDER(new DeleteOrderCommand()),
    ORDER_EXTRA(new OrderExtraCommand()),
    CHANGE_ORDER_STATUS(new ChangeOrderStatusCommand()),
    DELETE_CLIENT(new DeleteClientCommand()),
    CLIENT_EXTRA(new ClientExtraCommand()),
    CHANGE_CLIENT_ROLE(new ChangeClientRoleCommand()),
    CHANGE_CLIENT_DISCOUNT(new ChangeClientDiscountCommand()),
    CHANGE_CLIENT_BANNED(new ChangeClientBannedCommand()),
    EDIT_DISCOUNT(new EditDiscountCommand()),
    EDIT_DISCOUNT_PARAMETERS(new EditDiscountParametersCommand()),
    CREATE_DISCOUNT(new CreateDiscountCommand()),
    DELETE_DISCOUNT(new DeleteDiscountCommand()),
    DELETE_DESSERT(new DeleteDessertCommand()),
    CREATE_STUFFING(new CreateStuffingCommand()),
    EDIT_STUFFING(new EditStuffingCommand()),
    EDIT_PARAMETERS_STUFFING(new EditParametersStuffingsCommand()),
    DELETE_STUFFING(new DeleteStuffingCommand());


    private final Command command;

    CommandType (Command command){
        this.command = command;
    }

    public Command getCommand(){
        return this.command;
    }

    public static Optional<Command> provideCommand(String command){
        Optional<Command> resultCommand;
        if( command == null || command.isEmpty()){
            return Optional.empty();
        }
        try{
            CommandType commandType = CommandType.valueOf(command.toUpperCase());
            resultCommand = Optional.of(commandType.getCommand());
        }
        catch (IllegalArgumentException e){
            return Optional.empty();
        }
        return resultCommand;
    }
}
