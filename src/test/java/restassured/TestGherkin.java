package restassured;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dk.obhnothing.persistence.dto.hoteldto;
import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.internal.ResponseSpecificationImpl.HamcrestAssertionClosure;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;

public class TestGherkin
{

    @Test @DisplayName("get")
    void testGet()
    {
        assertThat(1, equalTo(1));
    }

    @Test @DisplayName("create")
    void testCreate()
    {
        assertThat(1, equalTo(1));
    }

}
















