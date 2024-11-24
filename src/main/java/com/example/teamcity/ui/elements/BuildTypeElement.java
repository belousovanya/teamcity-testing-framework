package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

@Getter
public class BuildTypeElement extends BasePageElement {
    private SelenideElement name;

    public BuildTypeElement(SelenideElement element) {
        super(element);
        this.name = find("span[class*='MiddleEllipsis__searchable--uZ']");
    }
}