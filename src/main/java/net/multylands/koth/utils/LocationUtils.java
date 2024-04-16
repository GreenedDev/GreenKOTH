package net.multylands.koth.utils;

import org.bukkit.Location;

public class LocationUtils {
    public static boolean checkIfIsInBetweenLocations(Location corner1, Location corner2, Location point) {
        double X1 = corner1.getX();
        double Z1 = corner1.getZ();
        double Y1 = corner1.getY();

        double X2 = corner2.getX();
        double Z2 = corner2.getZ();
        double Y2 = corner2.getY();

        double upperX = Math.max(X1, X2);
        double upperZ = Math.max(Z1, Z2);
        double upperY = Math.max(Y1, Y2);

        double lowerX = Math.min(X1, X2);
        double lowerZ = Math.min(Z1, Z2);
        double lowerY = Math.min(Y1, Y2);

        double pX = point.getX();
        double pZ = point.getZ();
        double pY = point.getY();


        return (pX <= upperX && pX >= lowerX && pZ <= upperZ && pZ >= lowerZ && pY <= upperY && pY >= lowerY);
    }
}
