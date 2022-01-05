package com.ascii274.webrequest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
public class EntitySportCAT_DTO {
//    @JsonProperty("n_m_registre")
    public String n_m_registre;
    public String nom_entitat;
    public String adre_a;
    public String cp;
    public String municipi;
    public String comarca;
    public String tel_fon_fax;
    public String tipus_entitat;
    public String modalitats;
    public String correu_electr_nic;
}
