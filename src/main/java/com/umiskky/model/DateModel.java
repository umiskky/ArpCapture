package com.umiskky.model;

import com.umiskky.model.dto.NetworkCardDto;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author UmiSkky
 */
public interface DateModel {

    HashMap<String, NetworkCardDto> getAllNetworkCards() throws Exception;

}
