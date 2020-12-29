package com.assignment.csp.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public final class TestUtils {

    private TestUtils() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate the class");
    }
    /**
     * Reads file and returns {@link InputStream} instance.
     *
     * @param fileName name of the file
     * @return {@code InputStream} instance
     */
    public static InputStream readFile(final String fileName) {
       // final ClassLoader classLoader = getClass().getClassLoader();
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        //return classLoader.getResourceAsStream(fileName);
    }
    /*
     * converts a Java object into JSON representation
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
