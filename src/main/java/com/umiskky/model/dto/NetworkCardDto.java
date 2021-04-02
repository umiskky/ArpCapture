package com.umiskky.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author UmiSkky
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NetworkCardDto {

    private String name;
    private String description;
    private String address;
    private String netmask;
    private String broadcastAddr;
    private String linkLayerAddr;

}
