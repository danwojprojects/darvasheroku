package com.wojtkowski.darvasheroku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Controller
public class StocksDownloader {
    Logger logger = LoggerFactory.getLogger(DarvasherokuApplication.class);

    @GetMapping(value = "/tradingview")
    public ModelAndView traidingView() throws IOException {
        ModelAndView mav = new ModelAndView();
        mav.addObject("now", getDOM52weekHighStocks());
        mav.setViewName("tradingview");

        return mav;
    }

    private String getDOM52weekHighStocks() throws IOException {
        PropertiesFile file = PropertiesFile.INSTANCE;
        String urlString = file.getProperties("stocks52WeeksHighURL");
        logger.info("Getting stocks from: "+ urlString);
        URL url = new URL(urlString);
        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        String encoding = con.getContentEncoding();
        encoding = encoding == null ? file.getProperties("stocks52WeeksHighEncoding") : encoding;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in,encoding));
        return bufferedReader.lines().reduce("", String::concat);
    }
}