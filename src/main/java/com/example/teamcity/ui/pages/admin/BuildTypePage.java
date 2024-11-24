package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class BuildTypePage extends BasePage{
    private static final String BUILD_TYPE_URL = "/admin/discoverRunners.html?init=1&id=buildType:%s";

    public SelenideElement title = $("div[class*='selected buildType']");

    public static BuildTypePage open(String buildTypeId) {
        return Selenide.open(BUILD_TYPE_URL.formatted(buildTypeId), BuildTypePage.class);
    }
}