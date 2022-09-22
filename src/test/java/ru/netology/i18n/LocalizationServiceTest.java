package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.netology.entity.Country;

public class LocalizationServiceTest {

    @DisplayName("Test 'locale' method")
    @ParameterizedTest
    @EnumSource(Country.class)
    void locale(Country country) {
        LocalizationService localizationService = new LocalizationServiceImpl();

        String result = localizationService.locale(country);

        if (country == Country.RUSSIA) {
            Assertions.assertEquals("Добро пожаловать", result);
        } else {
            Assertions.assertEquals("Welcome", result);
        }
    }
}
