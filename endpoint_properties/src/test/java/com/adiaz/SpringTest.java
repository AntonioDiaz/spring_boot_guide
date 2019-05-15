package com.adiaz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;

public class SpringTest {

    @Test
    public void formatTest() {
        assertEquals("toma 01 que toma 02", String.format("toma %s que toma %s", "01", "02"));

    }
}
