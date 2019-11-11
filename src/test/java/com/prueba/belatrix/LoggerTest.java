package com.prueba.belatrix;

import com.prueba.belatrix.enums.LogOutputEnum;
import com.prueba.belatrix.enums.LogTypeEnum;
import com.prueba.belatrix.util.LoggerUtil;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author German Liceta
 * Clase Test para probar funcionalidad
 */
public class LoggerTest extends TestCase {

    @Test
    public void testLogMessageToConsole() throws SecurityException, IOException, SQLException {
//        LoggerUtil.sendLogMessage(LogTypeEnum.MESSAGE, LogOutputEnum.CONSOLE, "Log tipo Message en Consola");
    }

    @Test
    public void testLogMessageToFile() throws SecurityException, IOException, SQLException {
//		LoggerUtil.sendLogMessage(LogTypeEnum.WARNING, LogOutputEnum.FILE, "Log tipo Warning en File");
    }

    @Test
    public void testLogMessageToDB() throws SecurityException, IOException, SQLException {
		LoggerUtil.sendLogMessage(LogTypeEnum.ERROR, LogOutputEnum.DATABASE, "Log tipo Error en Database");
    }
}
