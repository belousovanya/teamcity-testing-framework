package com.example.teamcity.ui;

import com.codeborne.selenide.Selenide;
import com.example.teamcity.BaseTest;
import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.User;
import com.example.teamcity.ui.pages.LoginPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.util.Map;

import static com.codeborne.selenide.Configuration.*;

public class BaseUiTest extends BaseTest {
    @BeforeSuite(alwaysRun = true)
    public void setupUiTest() {
        browser = Config.getProperty("browser");
        baseUrl = "http://" + Config.getProperty("host");
        remote = Config.getProperty("remote");
        browserSize = Config.getProperty("browserSize");

        browserCapabilities.setCapability("selenoid:options", Map.of("enableVNC", true, "enableLog", true));
    }

    protected TestDataStorage testDataStorage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        testDataStorage.deleteCreatedEntities();
        Selenide.closeWebDriver();
    }

    protected void loginAs(User user) {
        superUserCheckRequests.getRequest(Endpoint.USERS).create(testData.getUser());
        LoginPage.open().login(testData.getUser());
    }
}