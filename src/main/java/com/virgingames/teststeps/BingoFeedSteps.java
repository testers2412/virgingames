package com.virgingames.teststeps;

import com.virgingames.constants.EndPoints;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

public class BingoFeedSteps {

    @Step("Get all bingo feed")
    public ValidatableResponse getBingoFeed(){

     return    SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_BINGO_FEED)
                .then().statusCode(200).log().all();
    }

}
