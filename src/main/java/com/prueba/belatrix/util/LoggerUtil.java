package com.prueba.belatrix.util;

import com.prueba.belatrix.enums.LogOutputEnum;
import com.prueba.belatrix.enums.LogTypeEnum;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.*;

/**
 * @author German Liceta
 */
public class LoggerUtil {
    private static final Logger logger = Logger.getLogger("LoggerUtil");
    // Constantes de validacion
    private static final String PARAMS_ERROR = "Invalid parameters found";
    private static final String MESSAGE_ERROR = "Message to be logged can not be empty";

    private LoggerUtil() {
    }

    /**
     * Enviar mensaje de log segun tipo
     * @param logTypeEnum
     * @param logOutputEnum
     * @param message
     * @throws SecurityException
     * @throws IOException
     * @throws SQLException
     */
    public static void sendLogMessage(LogTypeEnum logTypeEnum, LogOutputEnum logOutputEnum, String message)
            throws SecurityException, IOException, SQLException {
        String valor = "El valor de la verdad";
        valor = String.format("%" + "12" + "s", "5");

        if (message == null || logTypeEnum == null || logOutputEnum == null) {
            throw new IllegalArgumentException(PARAMS_ERROR);
        }
        if (message.isEmpty()) {
            throw new IllegalArgumentException(MESSAGE_ERROR);
        }
        message = message.trim();

        if (logOutputEnum == LogOutputEnum.CONSOLE) {
            logToFileOrConsole(logTypeEnum, new ConsoleHandler(), message);
        } else if (logOutputEnum == LogOutputEnum.FILE) {
            String filePath = getFilePath();
            File logFile = new File(filePath);

            if (!logFile.exists()) {
                logFile.createNewFile();
            }

            logToFileOrConsole(logTypeEnum, new FileHandler(filePath), message);
        } else if (logOutputEnum == LogOutputEnum.DATABASE) {
            String formatMessage = formatMessageToDB(logTypeEnum, message);
            logToDB(logTypeEnum, formatMessage);
        }
    }

    /**
     * Obtener ruta de archivo
     * @return String
     */
    private static String getFilePath() {
        return System.getProperty("user.home") + File.separator + "JobLogger.xml";
    }

    /**
     * Formatear mensaje de log
     * @param logTypeEnum
     * @param message
     * @return String
     */
    private static String formatMessageToDB(LogTypeEnum logTypeEnum, String message) {
        return String.format("%s %s %s", logTypeEnum.getDescLogType(),
                DateFormat.getDateInstance(DateFormat.LONG).format(new Date()), message);
    }

    /**
     * Grabar mensaje de log en archivo o consola
     * @param logTypeEnum
     * @param streamHandler
     * @param message
     */
    private static void logToFileOrConsole(LogTypeEnum logTypeEnum, StreamHandler streamHandler, String message) {
        Level level = Level.INFO;
        if (logTypeEnum.getValueLogType() == 2) {
            level = Level.WARNING;
        } else if (logTypeEnum.getValueLogType() == 3) {
            level = Level.SEVERE;
        }
        // Inicialmente todos los loggers usan el nivel del Root Logger (logger por defecto). Para evitar esto usamos el método reset del componente LogManager
        LogManager.getLogManager().reset();
        logger.addHandler(streamHandler);// Los handlers sirven para especificar el destino de nuestro log
        logger.log(level, message);
    }

    /**
     * Guadar mensaje de log en BD
     * @param logTypeEnum
     * @param message
     * @throws SQLException
     */
    private static void logToDB(LogTypeEnum logTypeEnum, String message) throws SQLException {
        DBConnector dbConn = DBConnector.getInstance();
        Connection conn = dbConn.getConnection();

        PreparedStatement prepStatement = conn.prepareStatement("insert into LogTest (type, message) values (?, ?)");
        prepStatement.setInt(1, logTypeEnum.getValueLogType());
        prepStatement.setString(2, message);

        prepStatement.execute();
    }
}
