package com.yang.myProject.service.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * @author Yangjing
 */
public class GlobalCache {
    private static final Logger logger = LoggerFactory.getLogger(GlobalCache.class);
    private static final GlobalCache globalCache = new GlobalCache();

    private GlobalCache() {

    }

    public static GlobalCache getInstance() {
        return globalCache;
    }

    private String version = "Unknown Version";
    private String dbowner = "";
    private StudentCache studentCache = new StudentCache();

    public void loadServerVersion() {
        InputStream inputStream = null;
        Reader reader = null;
        BufferedReader br = null;
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream("VERSION");
            reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            br = new BufferedReader(reader);
            version = br.readLine();
        } catch (Exception e) {
            logger.warn("Unknown version. " + e.getMessage(), false);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (reader != null) {
                    reader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getVersion() {
        return version;
    }

    public StudentCache getStudentCache() {
        return studentCache;
    }

    public String getDbowner() {
        return dbowner;
    }

    public void setDbowner(String dbowner) {
        this.dbowner = dbowner;
    }
}
