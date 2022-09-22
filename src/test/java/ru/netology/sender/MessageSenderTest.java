
package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class MessageSenderTest {
    @Mock
    private GeoService geoService;

    @Mock
    private LocalizationService localizationService;

    @DisplayName("Test 'send' method: normal ip-header")
    @ParameterizedTest
    @CsvSource({
            "96.44.183.150, Welcome, USA",
            "172.123.12.19, Добро пожаловать, RUSSIA"
    })
    void sendTest(String ipAddress, String expected, String country) {
        Location location = new Location("Test", Country.valueOf(country), "Test", 1);
        Mockito.when(geoService.byIp(ipAddress)).thenReturn(location);
        Mockito.when(localizationService.locale(Country.valueOf(country))).thenReturn(expected);

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, ipAddress);
        String result = messageSender.send(headers);

        Assertions.assertEquals(expected, result);
    }

    @DisplayName("Test 'send' method: wrong ip-header")
    @ParameterizedTest
    @CsvSource({
            "96.44.183.150, Welcome",
            "172.123.12.19, Welcome"
    })
    void sendTestWrongHeader(String ipAddress, String expected) {
        Mockito.when(localizationService.locale(Country.USA)).thenReturn(expected);

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = Map.of("test", ipAddress);
        String result = messageSender.send(headers);

        Assertions.assertEquals(expected, result);
    }
}
