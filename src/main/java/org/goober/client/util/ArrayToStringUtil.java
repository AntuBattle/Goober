package org.goober.client.util;

public class ArrayToStringUtil
{
    public static String mergeArrayElements(String[] array, int startIndex) {
        if (array == null || startIndex < 0 || startIndex >= array.length) {
            throw new IllegalArgumentException("Invalid array or start index");
        }

        // Join elements from startIndex to the end of the array
        StringBuilder sb = new StringBuilder();
        for (int i = startIndex; i < array.length; i++) {
            if (i > startIndex) {
                sb.append(" "); // Add space between elements
            }
            sb.append(array[i]);
        }

        return sb.toString();
    }
}
