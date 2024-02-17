package com.java.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.Arrays;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserJson {

    public static void main(String[] args) throws FileNotFoundException {
        // Check if the user has provided a file name as an argument
        if (args.length == 0) {
            System.out.println("Usage: java ReadFile <filename>");
            System.exit(1);
        }

        // Get the file name from the argument
        String fileName = args[0];

        // Create a File object for the file
        File file = new File(fileName);

        // Check if the file exists
        if (!file.exists()) {
            System.out.println("The file " + fileName + " does not exist.");
            System.exit(1);
        }

        Scanner scanner = new Scanner(file);

        String jsonStr = "";
        // Read the file line by line
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            jsonStr += line;
        }

        scanner.close();

        boolean isValidJson = checkIsValidJson(jsonStr);

        if (isValidJson) {
            System.out.printf("%s is a valid json", fileName);
        } else {
            System.out.printf("%s is not a valid json", fileName);
        }
    }

    private static boolean checkIsValidJson(String jsonStr) throws FileNotFoundException {
        return isValidparenthesis(jsonStr) && checkForKeysAndValues(jsonStr);
    }

    private static boolean checkForKeysAndValues(String jsonStr) throws FileNotFoundException {
        if (jsonStr.length() == 2)
            return true;
        String withoutParanthesisJson = jsonStr.substring(1, jsonStr.length() - 1);
        if (withoutParanthesisJson.endsWith(","))
            return false;
        List<String> keyValuePairs = Arrays.asList(withoutParanthesisJson.split(","));
        for (String keyValuePair : keyValuePairs) {

            int index = keyValuePair.indexOf(":");
            String key = keyValuePair.substring(0, index).trim().replaceAll(" ", "");
            String value = keyValuePair.substring(index + 1).trim().replaceAll(" ", "");
            if (!(isString(key))) {
                return false;
            }

            if (!((isString(value)) || (isInt(value)) || (isBoolean(value)) || (isNull(value))
                    || (isArray(value)) || (checkIsValidJson(value)))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isArray(String value) throws FileNotFoundException {
        if (value.length() == 2)
            return true;
        boolean paranthesisCheck = value.length() >= 2 && value.charAt(0) == '['
                && value.charAt(value.length() - 1) == ']';
        if (!paranthesisCheck)
            return false;

        String withoutParenthesis = value.substring(1, value.length() - 1);
        if (withoutParenthesis.endsWith(","))
            return false;
        List<String> values = Arrays.asList(withoutParenthesis.split(","));

        for (String val : values) {
            String trimmedVal = val.trim().replaceAll(" ", "");
            if (!((isString(trimmedVal)) || (isInt(trimmedVal)) ||
                    (isBoolean(trimmedVal)) || (isNull(trimmedVal))
                    || isArray(trimmedVal) || checkIsValidJson(trimmedVal))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isBoolean(String value) {
        return value.equals("true") || value.equals("false");
    }

    private static boolean isNull(String value) {
        return value.equals("null");
    }

    private static boolean isInt(String value) {
        String regex = "[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?";
        Pattern p = Pattern.compile(regex);

        // Creates a matcher that will match input1 against regex
        Matcher m = p.matcher(value);
        // If match found and equal to input1
        return m.find() && m.group().equals(value);
    }

    private static boolean isString(String value) {

        String regex = "^\"[a-zA-Z\"\\-\b\f\n\r\t]*\"$";
        Pattern p = Pattern.compile(regex);

        // Creates a matcher that will match input1 against regex
        Matcher m = p.matcher(value);

        // If match found and equal to input1
        return m.find() && m.group().equals(value);

    }

    private static boolean isValidparenthesis(String jsonStr) {
        return jsonStr.length() >= 2 && jsonStr.charAt(0) == '{' && jsonStr.charAt(jsonStr.length() - 1) == '}';
    }

}
