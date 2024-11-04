package com.example.teamcity;

import com.example.teamcity.api.models.TestData;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import static com.example.teamcity.api.generators.TestDataGenerator.generate;

public class BaseTest {
    protected SoftAssert softy;
    protected CheckedRequests superUserCheckRequests = new CheckedRequests(Specifications.superUserSpec());
    protected CheckedRequests superUserUncheckRequests;
    protected TestData testData;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        softy = new SoftAssert();
        testData = generate();
        var userAuth = Specifications.authSpec(testData.getUser());
        superUserUncheckRequests = new CheckedRequests(userAuth);
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest() {
        softy.assertAll();
    }
}
