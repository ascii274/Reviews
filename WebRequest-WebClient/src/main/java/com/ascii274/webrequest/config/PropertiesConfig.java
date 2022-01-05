package com.ascii274.webrequest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("url") // application.prperties
public class PropertiesConfig {

    private String dataBaseUrl;
    private String dataVehicles;
    private String dataEntitatEsportivaCAT;

    public String getDataBaseUrl() {
        return dataBaseUrl;
    }

    public void setDataBaseUrl(String dataBaseUrl) {
        this.dataBaseUrl = dataBaseUrl;
    }

    public String getDataVehicles() {
        return dataVehicles;
    }

    public void setDataVehicles(String dataVehicles) {
        this.dataVehicles = dataVehicles;
    }

    public String getDataEntitatEsportivaCAT() {
        return dataEntitatEsportivaCAT;
    }

    public void setDataEntitatEsportivaCAT(String dataEntitatEsportivaCAT) {
        this.dataEntitatEsportivaCAT = dataEntitatEsportivaCAT;
    }
}
