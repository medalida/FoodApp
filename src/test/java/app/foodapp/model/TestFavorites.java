package app.foodapp.model;

import static org.junit.jupiter.api.Assertions.*;

import app.foodapp.JSONReader;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.assertions.api.Assertions.assertThat;


public class TestFavorites {
    private Receipt receipt = new Receipt(1212);

    @Test
    public void testAddToFavourites()  {
        Favourites.addToFavourites(new Receipt(716429));
        assertThat((long) ((JSONObject)((JSONObject)JSONReader.getDataByPath(EndPoints.FAVORITESJsonFILE)).get("716429")).get("id")).isEqualTo(716429);
    }
    @Test
    public void testRemoveFromFavourites() {
        Favourites.addToFavourites(this.receipt);
        JSONObject jsonObject = (JSONObject) JSONReader.getDataByPath(EndPoints.FAVORITESJsonFILE);

        assertThat(jsonObject.containsKey(Long.toString(receipt.getId()))).isTrue();

        Favourites.removeFromFavourites(this.receipt);
        jsonObject = (JSONObject) JSONReader.getDataByPath(EndPoints.FAVORITESJsonFILE);

        assertThat(jsonObject.containsKey(Long.toString(receipt.getId()))).isFalse();
    }
}
