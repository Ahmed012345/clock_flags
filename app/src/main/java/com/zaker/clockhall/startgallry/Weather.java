package com.zaker.clockhall.startgallry;

import com.zaker.clockhall.R;

/**
 * Created by yarolegovich on 08.03.2017.
 */

public enum Weather {

    EGYPT(R.string.egypt), SUDAN(R.string.sudan), DJIBOUTI(R.string.djibouti), COMOROS(R.string.comoros), SOMALIA(R.string.somalia),
    ERITREA(R.string.eritrea), MAURITANIA(R.string.mauritania), ALGERIA(R.string.algeria), LIBYA(R.string.libya),
    TUNIS(R.string.tunis), MOROCCO(R.string.morocco), PALESTINE(R.string.palestine), LEBANON(R.string.lebanon),
    JORDAN(R.string.jordan), IRAQ(R.string.iraq), SYRIA(R.string.syria), BAHRAIN(R.string.bahrain), KUWAIT(R.string.kuwait),
    OMAN(R.string.oman), QATAR(R.string.qatar), SOUDIARAB(R.string.saudi_arabia), UAE(R.string.uae), YEMEN(R.string.yemen),
    BURUNDI(R.string.burundi), ETHIOPIA(R.string.ethiopia), KENYA(R.string.kenya), MADAGASCAR(R.string.madagascar),
    MALAWI(R.string.malawi), MAURITIUS(R.string.mauritius), MOZAMBIQUE(R.string.mozambique),
    RWANDA(R.string.rwanda), SEYCHELLES(R.string.seychelles), TANZANIA(R.string.tanzania), UGANDA(R.string.uganda),
    ZAMBIA(R.string.zambia), ZIMBABWE(R.string.zimbabwe), ANGOLA(R.string.angola), CAMEROON(R.string.cameroon),
    CENTRAL_AFRICAN_REPUBLIC(R.string.central_african_republic), CHAD(R.string.chad), DEMOCRATIC_CONGO(R.string.democratic_congo),
    CONGO(R.string.congo), SãO_TOMé_AND_PRíNCIPE(R.string.são_tomé_and_príncipe), BOTSWANA(R.string.botswana),
    LESOTHO(R.string.lesotho), NAMIBIA(R.string.namibia), SOUTH_AFRICA(R.string.south_africa), SWAZILAND(R.string.swaziland),
    BENIN(R.string.benin), BURKINA_FASO(R.string.burkina_faso), CAPE_VERDE(R.string.cape_verde), GAMBIA(R.string.gambia),
    GHANA(R.string.ghana), GUINEA(R.string.guinea), GUINEA_bissau(R.string.guinea_bissau), LIBERIA(R.string.liberia),
    MALI(R.string.mali), NIGER(R.string.niger), NIGERIA(R.string.nigeria), SENEGAL(R.string.senegal),
    SIERRA_LEANE(R.string.sierra_leone), TOGO(R.string.togo), KAZAKHSTAN(R.string.kazakhstan),
    KYRGYZSTAN(R.string.kyrgyzstan), TAJIKISTAN(R.string.tajikistan), TURKMENISTAN(R.string.turkmenistan),
    UZBEKISTAN(R.string.uzbekistan), AFGHANISTAN(R.string.afghanistan), CHINA(R.string.china), JAPAN(R.string.japan),
    NORTH_KOREA(R.string.north_korea), KOREA(R.string.korea), MANGOLIA(R.string.mongolia), BRUNEI(R.string.brunei),
    CAMBODIA(R.string.cambodia), EAST_TIMOR(R.string.east_timor), INDONESIA(R.string.indonesia), LAOS(R.string.laos),
    MALAYSIA(R.string.malaysia), PHILIPPINES(R.string.philippines), SINGAPORE(R.string.singapore), THAILAND(R.string.thailand),
    BANGLDESH(R.string.bangladesh), BHUTAN(R.string.bhutan), INDIA(R.string.india), NEPAL(R.string.nepal), PAKISTAN(R.string.pakistan),
    SRI_LANKA(R.string.sri_lanka), EQUATORIAL_GUINEA(R.string.equatorial_guinea), GABON(R.string.gabon),
    ARMRNIA(R.string.armenia), AZERBAIJAN(R.string.azerbaijan), GEORGIA(R.string.georgia), IRAN(R.string.iran),
    TURKEY(R.string.turkey), ICELAND(R.string.iceland), NORWAY(R.string.norway), SWEDEN(R.string.sweden),
    FINLAND(R.string.finland), ESTONIA(R.string.estonia), LATVIA(R.string.latvia), LITHUANIA(R.string.lithuania),
    DENMARK(R.string.denmark), IRELAND(R.string.ireland), UK(R.string.united_kingdom), PORTUGAL(R.string.portugal),
    SPAIN(R.string.spain), MALTA(R.string.malta), ITALY(R.string.italy), GREECE(R.string.greece), ALBANIA(R.string.albania),
    SERBIA(R.string.serbia), CROATIA(R.string.croatia), B_H(R.string.bosnia_and_herzegovina), SLOVENIA(R.string.slovenia),
    FRANCE(R.string.france), BELGIUM(R.string.belgium), LUXEMBOURG(R.string.luxembourg), NETHERLANDS(R.string.netherlands),
    GERMANY(R.string.germany), AUSTRIA(R.string.austria), UKRAINE(R.string.ukraine), CZECH(R.string.czech),
    SLOVAKIA(R.string.slovakia), RUSSIA(R.string.russia), MOLDOVA(R.string.moldova), POLAND(R.string.poland),
    BULGARIA(R.string.bulgaria), HUNGARY(R.string.hungary), ROMANIA(R.string.romania), CANADA(R.string.canada),
    MEXICO(R.string.mexico), USA(R.string.usa), A_B(R.string.antigua_and_barbuda), BAHAMAS(R.string.bahamas),
    BARBADOS(R.string.barbados), CUBA(R.string.cuba), DOMINICA(R.string.dominica), DOMINICAN(R.string.dominican),
    GRENADA(R.string.grenada), HAITI(R.string.haiti), JAMAICA(R.string.jamaica), S_K_N(R.string.saint_kitts_and_nevis),
    S_L(R.string.saint_lucia), S_V_G(R.string.saint_vincent_and_the_grenadines), T_T(R.string.trinidad_and_tobago),
    BELIZE(R.string.belize), COSTA_RICA(R.string.costa_rica), EL_SALVADOR(R.string.el_salvador),
    GUATEMALA(R.string.guatemala), HONDURAS(R.string.honduras), NICARAGUA(R.string.nicaragua),
    PANAMA(R.string.panama), ARGENTINA(R.string.argentina), BRAZIL(R.string.brazil), BOLIVIA(R.string.bolivia),
    COLOMBIA(R.string.colombia), CHILE(R.string.chile), ECUADOR(R.string.ecuador), URUGUAY(R.string.uruguay),
    PARAGUAY(R.string.paraguay), SURINAME(R.string.suriname), VENEZUELA(R.string.venezuela),
    AUSTRALIA(R.string.australia);

    private int displayName;

    Weather(int displayName) {
        this.displayName = displayName;
    }

    public int getDisplayName() {
        return displayName;
    }

}
