package com.yang.myProject.server;

import com.yang.myProject.enums.SQLQueryEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

;

/**
 * @author Yangjing
 */
public class SQLQueryProcessor {
    private static final Logger logger = LoggerFactory.getLogger(SQLQueryProcessor.class);
    private static final Map<SQLQueryEnum, String> sqlMap = new HashMap<>();

    public static String getSql(SQLQueryEnum sqlQueryName) {
        String sql = sqlMap.get(sqlQueryName);
        if (sql == null) {
            logger.error("Undefined SQL for " + sqlQueryName);
        }
        return sql;
    }

    public static void loadXSqlscript(String fileFullPath) {
        int lineNum = 0;
        StringBuilder command = null;
        Pattern pattern = Pattern.compile("^--\\s*//@SQL@\\{(\\S+)\\}$");

        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileFullPath))));
            String line;
            boolean append = false;
            SQLQueryEnum sqlQuery = null;

            while ((line = bReader.readLine()) != null) {
                lineNum++;
                String trimmedLine = line.trim();

                //用两个if语句分别解析以"-- //@SQL@"开头的SQL语句名称 和实际的SQL语句
                //在解析本次的SQL语句名称时，将上一个SQL语句名称和SQL语句对放入sqlMap中，同时得到本次的SQL语句名称
                if (trimmedLine.startsWith("-- //@SQL@")) {
                    Matcher matcher = pattern.matcher(trimmedLine);
                    if (matcher.matches()) {
                        if (sqlQuery != null) {
                            if (command.length() == 0) {
                                logger.error("SQL文件定义错误。" + sqlQuery + "无有效SQL语句定义!");
                            }
                            if (sqlMap.get(sqlQuery) != null) {
                                logger.error("SQL文件定义错误。存在重复SQL定义：" + sqlQuery);
                            }
                            if (command.lastIndexOf(";") == command.length() - 1) {
                                command.deleteCharAt(command.length() - 1);
                            }
                            sqlMap.put(sqlQuery, command.toString());
                            command = null;
                        }

                        sqlQuery = SQLQueryEnum.valueOf(matcher.group(1));
                        logger.info("Loading sql: " + sqlQuery);
                        append = true;
                    } else {
                        logger.error("SQL文件定义错误。Line " + lineNum + "Content: " + trimmedLine);
                    }
                }

                if (command == null) {
                    command = new StringBuilder();
                }

                if (append) {
                    if (!trimmedLine.isEmpty() && !trimmedLine.startsWith("--") && !trimmedLine.startsWith("//")) {
                        if (command.length() > 0) {
                            command.append(" ");
                        }
                        command.append(trimmedLine);
                    }
                }
            }

            //为sqlMap添加最后一个“SQL语句名称和SQL语句”对
            if (sqlQuery != null) {
                if (command.length() == 0) {
                    logger.error("SQL文件定义错误。" + sqlQuery + "无有效SQL语句定义!");
                }
                if (sqlMap.get(sqlQuery) != null) {
                    logger.error("SQL文件定义错误。存在重复SQL定义：" + sqlQuery);
                }
                if (command.lastIndexOf(";") == command.length() - 1) {
                    command.deleteCharAt(command.length() - 1);
                }
                sqlMap.put(sqlQuery, command.toString());
            }

        } catch (IOException e) {
            logger.error("IOException :" + e.getMessage());
        }
    }
}
