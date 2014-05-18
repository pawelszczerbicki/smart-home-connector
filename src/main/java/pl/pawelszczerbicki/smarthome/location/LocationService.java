package pl.pawelszczerbicki.smarthome.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by Pawel on 2014-05-17.
 */
public class LocationService {

    private Context context;

    public LocationService(Context context) {
        this.context = context;
    }

    public Location location() {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        for (String provider : manager.getAllProviders()) {
            Location loc = manager.getLastKnownLocation(provider);
            if (loc != null)
                return loc;
        }
        return null;
    }
}
