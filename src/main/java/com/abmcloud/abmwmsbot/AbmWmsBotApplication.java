package com.abmcloud.abmwmsbot;

import javafx.embed.swing.JFXPanel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class AbmWmsBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbmWmsBotApplication.class, args);
	}

}
