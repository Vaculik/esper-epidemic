package cz.muni.fi.event;

/**
 * Created by Vaculik on 03/11/2015.
 */
public class NewDiseaseType implements Event{
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
