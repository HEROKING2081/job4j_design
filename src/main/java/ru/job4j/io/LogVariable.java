package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogVariable {

    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        byte bt = 1;
        short sht = 2;
        int in = 3;
        long lng = 4;
        boolean bln = true;
        float flt = 5.4F;
        double dbl = 34.5D;
        char chr = 'g';
        LOG.info("Info byte: {}", bt);
        LOG.info("Info short: {}", sht);
        LOG.info("Info int: {}", in);
        LOG.info("Info long: {}", lng);
        LOG.info("Info boolean: {}", bln);
        LOG.info("Info float: {}", flt);
        LOG.info("Info double: {}", dbl);
        LOG.info("Info char: {}", chr);
    }
}
