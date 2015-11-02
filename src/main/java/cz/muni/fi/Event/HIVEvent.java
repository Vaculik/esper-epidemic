package cz.muni.fi.event;

import java.util.Objects;

/**
 * Created by vaculik on 30.10.15.
 */
public class HIVEvent implements Event{
    private int locationX;
    private int locationY;
    private HIVStage stage;

    public void setLocationX(int x) {
        locationX = x;
    }

    public void setLocationY(int y) {
        locationY = y;
    }

    public void setStage(HIVStage stage) {
        this.stage = stage;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public HIVStage getStage() {
        return stage;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 23 * hash + locationX;
        hash = 31 * hash + locationY;
        hash = 19 * hash + Objects.hashCode(stage);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof HIVEvent)) {
            return false;
        }
        HIVEvent other = (HIVEvent) obj;
        if (other.getLocationX() != this.locationX) {
            return false;
        }
        if (other.getLocationY() != this.locationY) {
            return false;
        }
        if (!Objects.equals(other.getStage(), this.stage)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HIVEvent: X=" + locationX + " Y=" + locationY + " stage=" + stage;
    }
}
