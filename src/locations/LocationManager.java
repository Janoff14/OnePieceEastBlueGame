package locations;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import locations.Location;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationManager {

    public Map<String, Location> getLocationMap() {
        return locationMap;
    }

    private Map<String, Location> locationMap = new HashMap<>();

    public LocationManager(String filePath){
        loadLocations(filePath);
    }

    private void loadLocations(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            CSVParser parser = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader() // Use the first line as the header; skips it automatically
                    .parse(reader);

            for (CSVRecord record : parser) {
                String name = record.get("Name").trim();
                String description = record.get("Description").trim();
                String imagePath = record.get("ImagePath").trim();

                Location location = new Location(name, description, imagePath);
//                System.out.println(location.getName());
                locationMap.put(location.getName(), location);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // Other methods...
}
