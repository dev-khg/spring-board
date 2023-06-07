package com.example.board.utils;


import jakarta.servlet.http.HttpSession;

import java.util.NoSuchElementException;

public class SessionUtils {

    private static String sessionAttributeKey = "gsjhgaklsghasgg";

    private SessionUtils() { }

    public static Integer getValue(HttpSession session) {
        Integer value = (Integer) session.getAttribute(sessionAttributeKey);
        if(value == null) {
            throw new NoSuchElementException("세션이 존재하지 않습니다.");
        }
        return value;
    }

    public static void createSession(HttpSession session, Object value) {
        session.setAttribute(sessionAttributeKey, value);
    }

    public static void invalidateSession(HttpSession session) {
        session.invalidate();
    }

}
