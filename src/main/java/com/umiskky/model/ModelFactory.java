package com.umiskky.model;

/**
 * @author UmiSkky
 */
public class ModelFactory {

    private DateModel dateModel;

    public DateModel getDateModel(){
        if(dateModel == null) {
            dateModel = new DateModelManager();
        }
        return dateModel;
    }
}
