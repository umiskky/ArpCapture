package com.umiskky.model;

import com.umiskky.model.dto.NetworkCardDto;

import java.util.ArrayList;

/**
 * @author UmiSkky
 */
public interface DateModel {

    ArrayList<NetworkCardDto> getAllNetworkCards() throws Exception;

}
