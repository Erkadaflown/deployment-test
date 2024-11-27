package com.example.chessproject.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChessLogger {
    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }
}
