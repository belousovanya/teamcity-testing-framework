package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class CreateBuildTypePage extends CreateBasePage{
    private static final String BUILD_TYPE_SHOW_MODE = "createBuildTypeMenu";

    private SelenideElement successfulMessage = $("#unprocessed_objectsCreated");
    private SelenideElement errorEmptyBuildTypeName =  $("#error_buildTypeName");

    public static CreateBuildTypePage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, BUILD_TYPE_SHOW_MODE), CreateBuildTypePage.class);

    }

    public CreateBuildTypePage createForm(String url){
        baseCreateForm(url);
        return this;
    }

    public void setupBuildType (String buildTypeName) {
        buildTypeNameInput.val(buildTypeName);
        submitButton.click();
        successfulMessage.should(Condition.visible, BASE_WAITING);
    }

    public void errorMessageEmptyBuildTypeName(String buildTypeName) {
        buildTypeNameInput.val(buildTypeName);
        submitButton.click();
        errorEmptyBuildTypeName.should(Condition.visible);
    }
}