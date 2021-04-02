package com.umiskky;

import com.umiskky.model.DateModelManager;
import com.umiskky.model.dto.NetworkCardDto;

import java.util.ArrayList;

/**
 * @author UmiSkky
 */
public class Test {
    public static void main(String[] args) throws Exception{
        DateModelManager dateModelManager = new DateModelManager();
        ArrayList<NetworkCardDto> test = dateModelManager.getAllNetworkCards();
        System.out.println(test);
    }
}
