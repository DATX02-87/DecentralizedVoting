package se.chalmers.datx02.devmode;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@Tag(value = "integration")
class DevmodeEngineIntegrationTest {


    @Test
    public void test() {
        System.out.println("INTEGRATION TEST");
        assertEquals(false, true);
    }

}