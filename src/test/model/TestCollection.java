package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCollection {
    
    Collection testCollection;

    @BeforeEach
    void runBefore() {
        testCollection = new Collection();
    }

    @Test
    void testConstructor() {
        assertEquals(testCollection.getCollection().size(), 0);
    }
}
