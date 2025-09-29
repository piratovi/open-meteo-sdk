package com.kolosov.openmeteosdk;

import com.kolosov.openmeteosdk.api.WeatherDayData;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.SortedSet;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest()
class OpenMeteoServiceTest {

    @Autowired
    OpenMeteoService service;

    @SneakyThrows
    @Test
    public void getWeekForecast() {
        // setup

        // act
        SortedSet<WeatherDayData> weekForecast = service.getWeekForecast(Location.pickleball(), "Asia/Bangkok");
        assertThat(weekForecast).hasSize(7);
        System.out.println(formatForecast(weekForecast));

        // verify

    }

    private String formatForecast(SortedSet<WeatherDayData> forecast) {
        StringBuilder sb = new StringBuilder();
        for (WeatherDayData dayData : forecast) {
            sb.append("Date: ").append(dayData.day()).append("\n");
            for (WeatherDayData.WeatherHourData hourData : dayData.weatherHourData()) {
                sb.append(String.format("%-8s", hourData.time())).append("  ")
                        .append(String.format("%-15s", "Temperature: " + hourData.temperature() + "°C")).append("  ")
                        .append(String.format("%-20s", "Feels like: " + hourData.apparentTemperature() + "°C")).append("  ")
                        .append(String.format("%-15s", "Precipitation: " + hourData.precipitation() + "mm")).append("  ")
                        .append(String.format("%-15s", "Wind: " + hourData.windSpeed() + "m/s")).append("\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}