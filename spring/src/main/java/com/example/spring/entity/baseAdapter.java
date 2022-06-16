package com.example.spring.entity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author wanjun
 * @create 2022-06-17 0:03
 */
public abstract class baseAdapter {

    public abstract <T> T parse(Object o, Class<T> type);

}


