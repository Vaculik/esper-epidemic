package cz.muni.fi.event;

import java.util.Objects;

/**
 * Created by Vaculik on 03/11/2015.
 */
public class NewDiseaseType {
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NewDiseaseType)) {
            return false;
        }
        NewDiseaseType other = (NewDiseaseType) obj;
        return Objects.equals(other.getType(), this.type);
    }
}
