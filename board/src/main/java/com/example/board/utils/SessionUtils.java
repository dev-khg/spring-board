package com.example.board.utils;


import jakarta.servlet.http.HttpSession;

import java.util.NoSuchElementException;

public class SessionUtils {

    private static String sessionAttributeKey = "gsjhgaklsghasgg";

    private SessionUtils() { }

    public static Long getSessionValue(HttpSession session) {
        Long value = (Long) session.getAttribute(sessionAttributeKey);
        if(value == null) {
            throw new NoSuchElementException("세션이 존재하지 않습니다.");
        }
        return value;
    }

    public static void createSession(HttpSession session, Long value) {
        session.setAttribute(sessionAttributeKey, value);
    }

    public static void invalidateSession(HttpSession session) {
        session.invalidate();
    }

}
