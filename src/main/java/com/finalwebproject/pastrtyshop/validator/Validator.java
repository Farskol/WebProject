package com.finalwebproject.pastrtyshop.validator;

import java.util.Map;

import static com.finalwebproject.pastrtyshop.controller.Parameters.*;

public class Validator {
    private static final String NAME_PATTERN = "^[A-Za-zА-Яа-я]{3,50}$";
    private static final String CLIENT_LOGIN_PATTERN = "^[A-Za-zА-Яа-я0-9_]{4,16}$";
    private static final String CLIENT_PASSWORD_PATTERN = "^[A-Za-zА-Яа-я0-9\\.]{5,40}$";
    private static final String CLIENT_MAIL_PATTERN = "^[A-Za-z0-9\\.]{1,30}@[a-z]{2,7}\\.[a-z]{2,4}$";
    private static final String CLIENT_PHONE_NUMBER_PATTERN = "(29|25|44)\\d{7}";
    private static final String DESSERT_NAME_PATTERN = "^[A-Za-zА-Яа-я0-9_-]{3,30}$";
    private static final String DESSERT_DESCRIPTION_PATTERN = "^[A-Za-zА-Яа-я0-9_\\.\\,\\:\\s-]{3,1000}$";
    private static final String DESSERT_TYPE_PATTERN = "[A-Za-zА-Яа-я0-9_-]{3,30}$";
    private static final String DESSERT_COST_PATTERN = "^([0-9]{1,6})+(.[0-9]{1,2})?$";
    private static final String DISCOUNT_NAME_PATTERN = "^[A-Za-zА-Яа-я0-9_\\.\\,\\:\\s-]{3,50}$";
    private static final String DISCOUNT_VALUE_PATTERN = "^[0-9]{1,2}$";
    private static final String STUFFING_NAME_PATTERN = "^[A-Za-zА-Яа-я0-9_\\.\\,\\:\\s-]{3,50}$";
    private static final String STUFFING_DESCRIPTION_PATTERN = "^[A-Za-zА-Яа-я0-9_\\.\\,\\:\\s-]{3,1000}$";

    public boolean isCorrectName(String name){
        return isNotNull(name) && name.matches(NAME_PATTERN);
    }

    public boolean isCorrectSecondName (String secondName){
        if (!isNotNull(secondName)){
            return true;
        }
        return secondName.matches(NAME_PATTERN);
    }

    public boolean isCorrectLogin(String login){
        return isNotNull(login) && login.matches(CLIENT_LOGIN_PATTERN);
    }

    public boolean isCorrectPassword(String password){
        return isNotNull(password) && password.matches(CLIENT_PASSWORD_PATTERN);
    }

    public boolean isCorrectMail(String mail){
        if (!isNotNull(mail)){
            return true;
        }
        return mail.matches(CLIENT_MAIL_PATTERN);
    }

    public boolean isCorrectPhoneNumber(String phoneNumber){
        return isNotNull(phoneNumber) && phoneNumber.matches(CLIENT_PHONE_NUMBER_PATTERN);
    }

    public boolean isCorrectDessertName(String name){
        return name.matches(DESSERT_NAME_PATTERN);
    }

    public boolean isCorrectDessertDescription(String description){
        return description.matches(DESSERT_DESCRIPTION_PATTERN);
    }

    public boolean isCorrectDessertType(String type){
        return type.matches(DESSERT_TYPE_PATTERN);
    }

    public boolean isCorrectDessertCost(String cost){
        return cost.matches(DESSERT_COST_PATTERN);
    }

    public boolean isCorrectDiscountName(String discountName) {
        return discountName.matches(DISCOUNT_NAME_PATTERN);
    }

    public boolean isCorrectDiscountValue(String discountValue){
        return discountValue.matches(DISCOUNT_VALUE_PATTERN);
    }

    public boolean isCorrectStuffingName(String stuffingName){
        return stuffingName.matches(STUFFING_NAME_PATTERN);
    }

    public boolean isCorrectStuffingDescription(String stuffingDescription){
        return stuffingDescription.matches(STUFFING_DESCRIPTION_PATTERN);
    }

    public boolean checkStuffingEditForm(Map<String, String> stuffingData){
        boolean flag = true;
        boolean isFormNull = true;
        if(isNotNull(stuffingData.get(STUFFING_NAME))){
            if(!isCorrectStuffingName(stuffingData.get(STUFFING_NAME))){
                stuffingData.put(STUFFING_NAME, INVALID_STUFFING_NAME);
                flag = false;
            }
         isFormNull =  false;
        }

        if(isNotNull(stuffingData.get(STUFFING_DESCRIPTION))){
            if (!isCorrectStuffingDescription(stuffingData.get(STUFFING_DESCRIPTION))){
                stuffingData.put(STUFFING_DESCRIPTION, INVALID_STUFFING_DESCRIPTION);
                flag = false;
            }
            isFormNull = false;
        }

        if (isFormNull){
            flag = false;
            stuffingData.put(EDIT_STUFFING_FORM_IS_NULL, EDIT_STUFFING_FORM_IS_NULL);
        }

        return flag;
    }
    public boolean checkStuffingCreateForm(Map<String, String> stuffingData){
        boolean flag = true;

        if(isNotNull(stuffingData.get(STUFFING_NAME))){
            if(!isCorrectStuffingName(stuffingData.get(STUFFING_NAME))){
                stuffingData.put(STUFFING_NAME, INVALID_STUFFING_NAME);
                flag = false;
            }
        }
        else {
            flag = false;
        }

        if(isNotNull(stuffingData.get(STUFFING_DESCRIPTION))){
            if (!isCorrectStuffingDescription(stuffingData.get(STUFFING_DESCRIPTION))){
                stuffingData.put(STUFFING_DESCRIPTION, INVALID_STUFFING_DESCRIPTION);
                flag = false;
            }
        }
        else {
            flag = false;
        }

        return flag;
    }

    public boolean checkDiscountEditForm(Map<String, String> discountData){
        boolean flag = true;
        boolean isFormNull = true;

        if(isNotNull(discountData.get(DISCOUNT_NAME))) {
            if (!isCorrectDiscountName(discountData.get(DISCOUNT_NAME))) {
                discountData.put(DISCOUNT_NAME, INVALID_DISCOUNT_NAME);
                flag = false;
            }
            isFormNull = false;
        }

        if (isNotNull(discountData.get(DISCOUNT_VALUE))){
                if (!isCorrectDiscountValue(discountData.get(DISCOUNT_VALUE))) {
                    discountData.put(DISCOUNT_VALUE, INVALID_DISCOUNT_VALUE);
                    flag = false;
                }
                else {
                    int value = Integer.parseInt(discountData.get(DISCOUNT_VALUE));
                    if (value == 0) {
                        discountData.put(DISCOUNT_VALUE, INVALID_DISCOUNT_VALUE);
                        flag = false;
                    }
                }
            isFormNull = false;
        }

        if (isFormNull) {
            flag = false;
            discountData.put(EDIT_DISCOUNT_FORM, EDIT_DISCOUNT_FORM_IS_NULL);
        }

        return flag;
    }

    public boolean checkCreateDiscountForm(Map<String, String> discountData){
        boolean flag = true;

        if(isNotNull(discountData.get(DISCOUNT_NAME))) {
            if (!isCorrectDiscountName(discountData.get(DISCOUNT_NAME))) {
                discountData.put(DISCOUNT_NAME, INVALID_DISCOUNT_NAME);
                flag = false;
            }
        }
        else {
            flag = false;
        }

        if (isNotNull(discountData.get(DISCOUNT_VALUE))){
            if (!isCorrectDiscountValue(discountData.get(DISCOUNT_VALUE))) {
                discountData.put(DISCOUNT_VALUE, INVALID_DISCOUNT_VALUE);
                flag = false;
            }
            else {
                int value = Integer.parseInt(discountData.get(DISCOUNT_VALUE));
                if (value == 0) {
                    discountData.put(DISCOUNT_VALUE, INVALID_DISCOUNT_VALUE);
                    flag = false;
                }
            }
        }
        else {
            flag = false;
        }

        return flag;
    }

    public boolean checkRegistration(Map<String,String> clientData){
        boolean flag = true;
        if (!isCorrectName(clientData.get(FIRST_NAME))){
            clientData.put(FIRST_NAME,INVALID_FIRST_NAME);
            flag = false;
        }
        if (!isCorrectSecondName(clientData.get(SECOND_NAME))){
            clientData.put(SECOND_NAME,INVALID_SECOND_NAME);
            flag = false;
        }
        if (!isCorrectLogin(clientData.get(LOGIN))){
            clientData.put(LOGIN,INVALID_LOGIN);
            flag = false;
        }
        if (!isCorrectPassword(clientData.get(PASSWORD))){
            clientData.put(PASSWORD, INVALID_PASSWORD);
            flag = false;
        }
        if (!isCorrectMail(clientData.get(EMAIL))){
            clientData.put(EMAIL, INVALID_EMAIL);
            flag = false;
        }
        if (!isCorrectPhoneNumber(clientData.get(PHONE_NUMBER))){
            clientData.put(PHONE_NUMBER, INVALID_PHONE_NUMBER);
            flag = false;
        }
        return flag;
    }

    public boolean checkOrderForm(Map<String,String> clientData){
        boolean flag = true;

        if (!isCorrectName(clientData.get(FIRST_NAME))){
            clientData.put(FIRST_NAME,INVALID_FIRST_NAME);
            flag = false;
        }
        if (!isCorrectSecondName(clientData.get(SECOND_NAME))){
            clientData.put(SECOND_NAME,INVALID_SECOND_NAME);
            flag = false;
        }
        if (!isCorrectPhoneNumber(clientData.get(PHONE_NUMBER))){
            clientData.put(PHONE_NUMBER, INVALID_PHONE_NUMBER);
            flag = false;
        }
        return flag;
    }

    public boolean checkDessertEditForm(Map<String, String> dessertData){
        boolean flag = true;
        boolean isFormNull = true;

        if (isNotNull(dessertData.get(DESSERT_NAME))){
            if(!isCorrectDessertName(dessertData.get(DESSERT_NAME))){
                dessertData.put(DESSERT_NAME, INVALID_DESSERT_NAME);
                flag = false;
            }
            isFormNull = false;
        }
        if (isNotNull(dessertData.get(DESSERT_DESCRIPTION))){
            if(!isCorrectDessertDescription(dessertData.get(DESSERT_DESCRIPTION))){
                dessertData.put(DESSERT_DESCRIPTION, INVALID_DESSERT_DESCRIPTION);
                flag = false;
            }
            isFormNull = false;
        }
        if (isNotNull(dessertData.get(TYPE_OF_DESSERT))){
            if(!isCorrectDessertType(dessertData.get(TYPE_OF_DESSERT))){
                dessertData.put(TYPE_OF_DESSERT, INVALID_DESSERT_TYPE);
                flag = false;
            }
            isFormNull = false;
        }
        if (isNotNull(dessertData.get(COST_OF_DESSERT))){
            if(!isCorrectDessertCost(dessertData.get(COST_OF_DESSERT))) {
                dessertData.put(COST_OF_DESSERT, INVALID_DESSERT_COST);
                flag = false;
            }
            isFormNull = false;
        }

        if (isFormNull){
            flag = false;
            dessertData.put(EDIT_DESSERT_FORM, EDIT_DESSERT_FORM_IS_NULL);
        }

        return flag;
    }

    public boolean checkCreateDessertForm(Map<String, String> dessertData){
        boolean flag = true;

        if (isNotNull(dessertData.get(DESSERT_NAME))){
            if(!isCorrectDessertName(dessertData.get(DESSERT_NAME))){
                dessertData.put(DESSERT_NAME, INVALID_DESSERT_NAME);
                flag = false;
            }
        }
        else {
            flag = false;
        }

      if (isNotNull(dessertData.get(DESSERT_DESCRIPTION))){
            if(!isCorrectDessertDescription(dessertData.get(DESSERT_DESCRIPTION))){
                dessertData.put(DESSERT_DESCRIPTION, INVALID_DESSERT_DESCRIPTION);
                flag = false;
            }
        }
        else {
            flag = false;
        }

        if (isNotNull(dessertData.get(TYPE_OF_DESSERT))){
            if(!isCorrectDessertType(dessertData.get(TYPE_OF_DESSERT))){
                dessertData.put(TYPE_OF_DESSERT, INVALID_DESSERT_TYPE);
                flag = false;
            }
        }
        else {
            flag = false;
        }

        if (isNotNull(dessertData.get(COST_OF_DESSERT))){
            if(!isCorrectDessertCost(dessertData.get(COST_OF_DESSERT))) {
                dessertData.put(COST_OF_DESSERT, INVALID_DESSERT_COST);
                flag = false;
            }
        }
        else {
            flag = false;
        }


        return flag;
    }
    private boolean isNotNull(String string){
        return string != null && !string.isEmpty();
    }
}
