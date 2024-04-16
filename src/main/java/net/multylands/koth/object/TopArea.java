package net.multylands.koth.object;

import org.bukkit.Location;

public class TopArea {
    public String ID;
    public Location corner1;
    public Location corner2;
    public String kingUUID = null;

    public TopArea(Location corner1, Location corner2, String ID) {
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.ID = ID;
    }

    public Location getFirstCorner() {
        return corner1;
    }

    public Location getSecondCorner() {
        return corner2;
    }

    public void setKingUUID(String kingUUID) {
        this.kingUUID = kingUUID;
    }

    public void setNoKing() {
        this.kingUUID = null;
    }

    public boolean isThereAKing() {
        return !(kingUUID == null);
    }

    public String getKingUUID() {
        return kingUUID;
    }

    public String getID() {
        return ID;
    }
}
