package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.ui.pages.BuildTypePage;
import com.example.teamcity.ui.pages.admin.CreateBuildTypePage;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.BUILD_TYPES;
import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
        var createdBuildType = superUserCheckRequests.<BuildType>getRequest(BUILD_TYPES)
                .read("name:" + testData.getBuildType().getName());
        softy.assertNotNull(createdBuildType);
        testDataStorage.addCreatedEntity(BUILD_TYPES, createdBuildType);

        // Проверка состояния UI
        BuildTypePage.open(createdBuildType.getId())
                .title.shouldHave(Condition.exactText(testData.getBuildType().getName()));
    }


    @Test(description = "User should not be able to create buildType without name", groups = {"Negative"})
    public void userCannotCreateBuildTypeWithoutName() {
        // Подготовка окружения
        loginAs(testData.getUser());
        var buildType = generate(BuildType.class);
        var userCheckRequests = new UncheckedRequests(Specifications.authSpec(testData.getUser()));

        // Создание проекта через API
        Response projectCreationResponse = userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());
        assertThat(projectCreationResponse.statusCode(), equalTo(SC_OK));

        // Проверка, что проект действительно создан
        var createdProject = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECTS).read("name:" + testData.getProject().getName());
        softy.assertNotNull(createdProject);

        testDataStorage.addCreatedEntity(Endpoint.PROJECTS, testData.getProject());

        // Взаимодействие с UI
        CreateBuildTypePage.open(testData.getProject().getId())
                .createForm(REPO_URL)
                .errorMessageEmptyBuildTypeName("");

        // Попытка создать BuildType без имени на API уровне
        Response createdBuildTypeResponse = userCheckRequests.getRequest(BUILD_TYPES).create(buildType);

        // Проверка, что BuildType не создался на API уровне
        assertThat(createdBuildTypeResponse.statusCode(), equalTo(SC_NOT_FOUND));
    }
}