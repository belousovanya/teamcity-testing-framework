package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.example.teamcity.ui.pages.BasePage;

public class BuildConfigurationPage extends BasePage {
    private static final String BUILD_TYPE_URL = "/buildConfiguration/%s";

    public static BuildConfigurationPage open(String configId) {
        return Selenide.open(BUILD_TYPE_URL.formatted(configId), BuildConfigurationPage.class);
    }
}
