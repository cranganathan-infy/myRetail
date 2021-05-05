package com.assessment.myRetail.application.logging

class KeyValueLogger {
    static final List<String> CHARACTERS_REQUIRING_QUOTES = [' ', '=', ',', ':', '"', ';', '$', '|']

    static String log(String message, Map<String, Object> params = null) {
        List<String> result = []
        result << formatKeyValue('message', message)
        params?.each { String key, Object value ->
            result << formatKeyValue(key, value)
        }
        return result.join(', ')
    }

    private static String formatKeyValue(String key, Object value) {
        return "${key}=${wrapString(value)}"
    }

    private static String wrapString(Object messageObject) {
        if (messageObject == null) {
            return ''
        }
        String message = messageObject as String
        String messageWithQuotesEscaped = message.replaceAll('"', '\\\\"')
        boolean needsQuotes = CHARACTERS_REQUIRING_QUOTES.any { String characterRequiringQuote ->
            return messageWithQuotesEscaped.contains(characterRequiringQuote)
        }
        if (needsQuotes) {
            return "\"${messageWithQuotesEscaped}\""
        }
        return message
    }
}
