package com.virgingames.bingotests;

import com.virgingames.constants.EndPoints;
import com.virgingames.constants.Path;
import com.virgingames.testbase.TestBase;
import com.virgingames.teststeps.BingoFeedSteps;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;

@RunWith(SerenityRunner.class)
public class BingoFeedTest extends TestBase {
    @Steps
    BingoFeedSteps bingoFeedSteps;

    @Title("Verify that the default game frequency always be 300000")
    @Test
    public void test001() {
        //Getting all game info from steps method
        ValidatableResponse response = bingoFeedSteps.getBingoFeed();

        //Extracting default frequencies for all games
        List<String> freqList = response.extract().jsonPath().get("bingoLobbyInfoResource.streams.defaultGameFrequency");
        System.out.println(freqList);

        //Getting game's default frequency one by one and verifying if it is not null then has to be 300000
        for (Object freq : freqList) {
            if (freq != null) {
                Assert.assertEquals(freq, 300000);
            }
        }

    }

    @Title("Verify the start time is always be future time stamp")
    @Test
    public void test002() {
        //Getting current time stamp
        long timeStamp = SerenityRest.given()
                .when()
                .get(EndPoints.GET_BINGO_FEED)
                .then().statusCode(200).extract().jsonPath().get("timestamp");

        // Getting start time for all games
        List<Long> startTimes = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_BINGO_FEED)
                .then().statusCode(200).extract().jsonPath().get("bingoLobbyInfoResource.streams.startTime");

        //calling one start at a time and comparing with current time stamp that the start time is greater than current time stamp
        for (long startTime : startTimes) {
            Assert.assertThat(startTime, greaterThan(timeStamp));
        }
    }


}
