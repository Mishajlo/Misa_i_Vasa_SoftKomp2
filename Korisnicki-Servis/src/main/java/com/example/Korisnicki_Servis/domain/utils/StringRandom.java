package com.example.Korisnicki_Servis.domain.utils;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;
@Configuration
@AllArgsConstructor
public class StringRandom {

    @Bean
    public String generateRandomString() {
       String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
       StringBuilder salt = new StringBuilder();
       Random rnd = new Random();
       while (salt.length() < 18) { // length of the random string.
           int index = (int) (rnd.nextFloat() * SALTCHARS.length());
           salt.append(SALTCHARS.charAt(index));
       }
       String saltStr = salt.toString();
       return saltStr;
    }



}
