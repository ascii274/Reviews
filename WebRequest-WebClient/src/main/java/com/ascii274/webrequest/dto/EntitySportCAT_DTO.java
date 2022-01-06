package com.ascii274.webrequest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
public class EntitySportCAT_DTO {
    @JsonProperty("n_m_registre")
    public String n_m_registre;
    @JsonProperty("nom_entitat")
    public String nom_entitat;
    @JsonProperty("adre_a")
    public String adre_a;
    @JsonProperty("cp")
    public String cp;
    @JsonProperty("municipi")
    public String municipi;
    @JsonProperty("comarca")
    public String comarca;
    @JsonProperty("tel_fon_fax")
    public String tel_fon_fax;
    @JsonProperty("tipus_entitat")
    public String tipus_entitat;
    @JsonProperty("modalitats")
    public String modalitats;
    @JsonProperty("correu_electr_nic")
    public String correu_electr_nic;

}
