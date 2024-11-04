package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.ui.pages.BuildTypePage;
import com.example.teamcity.ui.pages.admin.CreateBuildTypePage;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.PROJECTS;

@Test(groups = {"Regression"})
public class CreateBuildTypeTest extends BaseUiTest {
    private static final String REPO_URL = "https://github.com/belousovanya/file-parsing-tests";

    @Test(description = "User should be able to create buildType", groups = {"Positive"})
    public void userCreatesBuildType() {
        // Подготовка окружения
        loginAs(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        // Создание проекта через API
        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());
        testDataStorage.addCreatedEntity(Endpoint.PROJECTS, testData.getProject());

        // Взаимодействие с UI
        CreateBuildTypePage.open(testData.getProject().getId())
                .createForm(REPO_URL)
                .setupBuildType(testData.getBuildType().getName());

        // Получение созданного BuildType через API
        var createdBuildType = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES)
                .read("name:" + testData.getBuildType().getName());
        softy.assertNotNull(createdBuildType);
        testDataStorage.addCreatedEntity(Endpoint.BUILD_TYPES, createdBuildType);

        // Проверка состояния UI
        BuildTypePage.open(createdBuildType.getId())
                .title.shouldHave(Condition.exactText(testData.getBuildType().getName()));
    }

    @Test(description = "User should not be able to create buildType without name", groups = {"Negative"})
    public void userCannotCreateBuildTypeWithoutName() {
        // Подготовка окружения
        loginAs(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        // Создание проекта через API
        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());
        testDataStorage.addCreatedEntity(Endpoint.PROJECTS, testData.getProject());

        // Взаимодействие с UI
        CreateBuildTypePage.open(testData.getProject().getId())
                .createForm(REPO_URL)
                .errorMessageEmptyBuildTypeName("");
    }
}