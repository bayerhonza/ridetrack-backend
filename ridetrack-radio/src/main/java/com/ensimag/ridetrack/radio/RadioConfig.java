package com.ensimag.ridetrack.radio;

import com.ensimag.ridetrack.radio.services.RadioServiceProviderManager;
import com.ridetrack.ridetrack.radio.RadioType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RadioConfig {

    @Bean
    RadioServiceProviderManager providerManager() {
        RadioServiceProviderManager providerManager = new RadioServiceProviderManager();
        providerManager.registerRadioService(RadioType.LORAWAN);
        return providerManager;
    }
}
