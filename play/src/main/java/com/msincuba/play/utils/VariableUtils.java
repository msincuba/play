package com.msincuba.play.utils;

import org.apache.commons.lang3.StringUtils;

public class VariableUtils {

    private VariableUtils() {
    }

    public static String variableToWord(String variable) {
        if (StringUtils.isBlank(variable)) {
            return variable;
        }
        String[] words = StringUtils.splitByCharacterTypeCamelCase(variable);
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (StringUtils.isAllUpperCase(word)) {
                sb.append(word).append(" ");
            } else {
                sb.append(word.toLowerCase()).append(" ");
            }
            
        }
        return sb.toString().trim();
    }
}
