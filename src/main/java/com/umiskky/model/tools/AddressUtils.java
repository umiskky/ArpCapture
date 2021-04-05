package com.umiskky.model.tools;

public class AddressUtils {

    /**
     * @author UmiSkky
     * @apiNote this method is used to check mac address is valid or not
     * @param macAddr mac address
     * @return boolean
     */
    public static boolean isValidMacAddress(String macAddr) {
        if (macAddr == null || "".equals(macAddr)) {
            return false;
        }
        String macAddressRule = "([A-Fa-f0-9]{2}[-,:]){5}[A-Fa-f0-9]{2}";
        return macAddr.matches(macAddressRule);
    }

    public static boolean isValidIPAddress(String ipAddr) {
        if (ipAddr == null || "".equals(ipAddr)) {
            return false;
        }
        String ipAddressRule = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        return ipAddr.matches(ipAddressRule);
    }
}
