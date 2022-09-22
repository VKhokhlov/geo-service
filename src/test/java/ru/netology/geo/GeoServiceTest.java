package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

public class GeoServiceTest {
    private final GeoServiceImpl geoService = new GeoServiceImpl();
    ;

    @DisplayName("Test 'byIp' method: wrong ip")
    @Test
    void byIpTestWrongIp() {
        Location location = geoService.byIp("192.168.1.1.");
        Assertions.assertNull(location);
    }

    @DisplayName("Test 'byIp' method: normal ip")
    @ParameterizedTest
    @CsvSource({
            "127.0.0.1,,,,0",
            "172.0.32.11, Moscow, RUSSIA, Lenina, 15",
            "96.44.183.149, New York, USA, 10th Avenue, 32",
            "172.1.1.1, Moscow, RUSSIA,, 0",
            "96.1.1.1, New York, USA,, 0"
    })
    void byIpTest(String ipAddress, String city, String country, String street, String building) {
        Location location = geoService.byIp(ipAddress);

        Assertions.assertEquals(city, location.getCity());
        Assertions.assertEquals(country == null ? null : Country.valueOf(country), location.getCountry());
        Assertions.assertEquals(street, location.getStreet());
        Assertions.assertEquals(building, Integer.toString(location.getBuilding()));
    }

}
