package cz.muni.fi.Event;

/**
 * Created by vaculik on 30.10.15.
 */
public class SARSEvent implements Event{

    private int locationX;
    private int locationY;
    private boolean death;

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

    @Override
    public int hashCode() {
        int hash = 11;
        hash = 13 * hash + locationX;
        hash = 17 * hash + locationY;
        hash = 19 * hash + (death ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SARSEvent)) {
            return false;
        }
        SARSEvent other = (SARSEvent) obj;
        if (other.getLocationX() != this.locationX) {
            return false;
        }
        if (other.getLocationY() != this.locationY) {
            return false;
        }
        if (other.getDeath() != this.death) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SARSEvent: X=" + locationX + " Y=" + locationY + " death=" + death;
    }
}
