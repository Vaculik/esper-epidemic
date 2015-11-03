package cz.muni.fi.event;

import java.util.Objects;

/**
 * Created by vaculik on 30.10.15.
 */
public class DiseaseEvent implements Event{

    private int locationX;
    private int locationY;
    private boolean death;
    private String type;

    public void setLocationX(int x) {
        locationX = x;
    }

    public void setLocationY(int y) {
        locationY = y;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setDeath(boolean death) {
        this.death = death;
    }

    public boolean getDeath() {
        return death;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        int hash = 11;
        hash = 13 * hash + locationX;
        hash = 17 * hash + locationY;
        hash = 19 * hash + (death ? 1 : 0);
        hash = 23 * hash + Objects.hashCode(type);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DiseaseEvent)) {
            return false;
        }
        DiseaseEvent other = (DiseaseEvent) obj;
        if (other.getLocationX() != this.locationX) {
            return false;
        }
        if (other.getLocationY() != this.locationY) {
            return false;
        }
        if (other.getDeath() != this.death) {
            return false;
        }
        if (!Objects.equals(other.getType(), this.type)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DiseaseEvent: X=" + locationX + " Y=" + locationY + " death=" + death + " type=" + type;
    }
}
