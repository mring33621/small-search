package com.mattring.smallsearch;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {

    @org.junit.jupiter.api.Test
    void find() {
        String testTxt = "UPS just delivered my new Apple iPhone 14! " +
                "I can't wait to take some great pictures of my family. " +
                "I hope I don't drop it in the toilet.";
        String[] testQueries = {"apple", "pear", "\"drop toilet\"~3", "\"picture kids\"~3"};
        List<Object> hits = Search.find(Arrays.asList(testQueries), testTxt);
        assertEquals("[1, 0, 1, 0]", hits.toString());
    }
}