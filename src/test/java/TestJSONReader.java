import static org.junit.jupiter.api.Assertions.assertEquals;

import app.foodapp.JSONReader;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

public class TestJSONReader {
@Test
    public void testGetDataByUrl(){
    JSONObject data = (JSONObject)JSONReader.getDataByUrl("https://api.nationalize.io/?name=nathaniel");
    assert data != null;
    assertEquals(data.get("name"), "nathaniel");
    }

    @Test
    public void testGetDataByPath(){
    JSONObject data = (JSONObject)JSONReader.getDataByPath("src/test/test2.json");
        assert data != null;
        assertEquals((long) data.get("id"), 640352);
    }


}
