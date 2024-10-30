package com.examle.teamcity.ui;

import com.codeborne.selenide.Selenide;
import com.examle.teamcity.BaseTest;
import com.example.teamcity.api.config.Config;
import org.testng.annotations.BeforeSuite;

import java.util.Map;

import static com.codeborne.selenide.Configuration.*;

public class BaseUiTest extends BaseTest {
    @BeforeSuite(alwaysRun = true)
    public void setupUiTest() {
        browser = Config.getProperty("browser");
        baseUrl = "https://" + Config.getProperty("host");
        remote = Config.getProperty("remote");
        browserSize = Config.getProperty("browserSize");

        browserCapabilities.setCapability("selenoid:options", Map.of("enableVNC", true, "enableLog", true));
    }

    public void closeWebDriver() {
        Selenide.closeWebDriver();
    }
}
