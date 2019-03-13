package com.timesheet.util;

//Java imports
import org.apache.log4j.Logger;

public class LoggerUtils {

    private static Logger log = null;

    //Static initializer loads only once when the class loads into memory
    static {
        //System.out.println("Inside LoggerUtils static block");
        log = Logger.getLogger(LoggerUtils.class);
        //System.out.println("LoggerUtils Class Name == " + LoggerUtils.log.getClass().getName());
    }//end of static block

    /**
     *
     */
    public LoggerUtils() {
    }//end of constructor

    /**
     * @param logString
     */
    public static void debug(String logString) {
        log.debug(logString);
    }//end of method - debug

    /**
     * @param logString
     */
    public static void info(String logString) {
        log.info(logString);
    }//end of method - info

    /**
     * @param logString
     */
    public static void warn(String logString) {
        log.warn(logString);
    }//end of method - warn

    /**
     * @param logString
     */
    public static void error(String logString) {
        log.error(logString);
    }//end of method - error

    /**
     * @param logString
     * @param e
     */
    public static void error(String logString, Exception e) {
        error(logString);

        if (e != null) {

            StackTraceElement[] elements = null;
            elements = e.getStackTrace();

            if (elements != null) {
                StringBuffer strBuffer = new StringBuffer("");
                for (int i = 0; i < elements.length; i++) {
                    strBuffer.append(elements[i]).append("\n");
                }
                error("Stack Trace :: \n" + strBuffer);
                strBuffer = null;
            }

            elements = null;

        }//end of if(e != null)


    }//end of method - error

    /**
     * @param logString
     * @param messages
     */
    public static void error(String logString, Object[] messages) {
        error(logString);

        if (messages != null) {

            StringBuffer strBuffer = new StringBuffer("");
            for (int i = 0; i < messages.length; i++) {
                strBuffer.append(messages[i]).append("\n");
            }
            error(strBuffer.toString());
            strBuffer = null;

        }//end of if(messages != null)

    }//end of method - error

    /**
     * @param logString
     */
    public static void fatal(String logString) {
        log.fatal(logString);
    }//end of method - fatal
}//end of class - LoggerUtils
