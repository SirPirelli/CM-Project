package goldenegg.detectivephiladelphia;

public class LocationModel {

    String locationName;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    String Description;

    public LocationModel(String locationName, String description) {
        this.locationName = locationName;
        Description = description;
    }
}
