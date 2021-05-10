package com.zaker.clockhall.startgallry;

import com.zaker.clockhall.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yarolegovich on 08.03.2017.
 */

public class WeatherStation  {


    public static WeatherStation get() {
        return new WeatherStation();
    }

    private WeatherStation() {
    }

    public List<Forecast> getForecasts() {
        return Arrays.asList(
                //MeddilArab
                new Forecast(R.string.egypt, R.drawable.egypt, Weather.EGYPT),
                new Forecast(R.string.sudan, R.drawable.sudan, Weather.SUDAN),
                new Forecast(R.string.djibouti, R.drawable.djibouti, Weather.DJIBOUTI),
                new Forecast(R.string.comoros, R.drawable.comoros, Weather.COMOROS),
                new Forecast(R.string.somalia, R.drawable.somalia, Weather.SOMALIA),
                new Forecast(R.string.eritrea, R.drawable.eritrea, Weather.ERITREA),
                // EastArab
                new Forecast(R.string.palestine, R.drawable.palestine, Weather.PALESTINE),
                new Forecast(R.string.lebanon, R.drawable.lebanon, Weather.LEBANON),
                new Forecast(R.string.jordan, R.drawable.jordan, Weather.JORDAN),
                new Forecast(R.string.iraq, R.drawable.iraq, Weather.IRAQ),
                new Forecast(R.string.syria, R.drawable.syria, Weather.SYRIA),
               // WestArab
                new Forecast(R.string.mauritania, R.drawable.mauritania, Weather.MAURITANIA),
                new Forecast(R.string.algeria, R.drawable.algeria, Weather.ALGERIA),
                new Forecast(R.string.libya, R.drawable.libya, Weather.LIBYA),
                new Forecast(R.string.tunis, R.drawable.tunis, Weather.TUNIS),
                new Forecast(R.string.morocco, R.drawable.morocco, Weather.MOROCCO),
                // AlgazeraAlarabia
                new Forecast(R.string.bahrain, R.drawable.bahrain, Weather.BAHRAIN),
                new Forecast(R.string.kuwait, R.drawable.kuwait, Weather.KUWAIT),
                new Forecast(R.string.oman, R.drawable.oman, Weather.OMAN),
                new Forecast(R.string.qatar, R.drawable.qatar, Weather.QATAR),
                new Forecast(R.string.saudi_arabia, R.drawable.saudi_arabia, Weather.SOUDIARAB),
                new Forecast(R.string.uae, R.drawable.uae, Weather.UAE),
                new Forecast(R.string.yemen, R.drawable.yemen, Weather.YEMEN),
                //EastAfrica
                new Forecast(R.string.burundi, R.drawable.burundi, Weather.BURUNDI),
                new Forecast(R.string.ethiopia, R.drawable.ethiopia, Weather.ETHIOPIA),
                new Forecast(R.string.kenya, R.drawable.kenya, Weather.KENYA),
                new Forecast(R.string.madagascar, R.drawable.madagascar, Weather.MADAGASCAR),
                new Forecast(R.string.malawi, R.drawable.malawi, Weather.MALAWI),
                new Forecast(R.string.mauritius, R.drawable.mauritius, Weather.MAURITIUS),
                new Forecast(R.string.mozambique, R.drawable.mozambique, Weather.MOZAMBIQUE),
                new Forecast(R.string.rwanda, R.drawable.rwanda, Weather.RWANDA),
                new Forecast(R.string.seychelles, R.drawable.seychelles, Weather.SEYCHELLES),
                new Forecast(R.string.tanzania, R.drawable.tanzania, Weather.TANZANIA),
                new Forecast(R.string.uganda, R.drawable.uganda, Weather.UGANDA),
                new Forecast(R.string.zambia, R.drawable.zambia, Weather.ZAMBIA),
                new Forecast(R.string.zimbabwe, R.drawable.zimbabwe, Weather.ZIMBABWE),
                //MiddileAfrica
                new Forecast(R.string.angola, R.drawable.angola, Weather.ANGOLA),
                new Forecast(R.string.cameroon, R.drawable.cameroon, Weather.CAMEROON),
                new Forecast(R.string.central_african_republic, R.drawable.central_african, Weather.CENTRAL_AFRICAN_REPUBLIC),
                new Forecast(R.string.chad, R.drawable.chad, Weather.CHAD),
                new Forecast(R.string.democratic_congo, R.drawable.congo_democratic_republic, Weather.DEMOCRATIC_CONGO),
                new Forecast(R.string.equatorial_guinea, R.drawable.equatorial_guinea, Weather.EQUATORIAL_GUINEA),
                new Forecast(R.string.gabon, R.drawable.gabon, Weather.GABON),
                new Forecast(R.string.congo, R.drawable.republic_of_the_congo, Weather.CONGO),
                new Forecast(R.string.são_tomé_and_príncipe, R.drawable.sao_tome_and_prince, Weather.SãO_TOMé_AND_PRíNCIPE),
                 //SouthAfrica
                new Forecast(R.string.botswana, R.drawable.botswana, Weather.BOTSWANA),
                new Forecast(R.string.lesotho, R.drawable.lesotho, Weather.LESOTHO),
                new Forecast(R.string.namibia, R.drawable.namibia, Weather.NAMIBIA),
                new Forecast(R.string.south_africa, R.drawable.south_africa, Weather.SOUTH_AFRICA),
                new Forecast(R.string.swaziland, R.drawable.swaziland, Weather.SWAZILAND),
                //WestAfrica
                new Forecast(R.string.benin, R.drawable.benin, Weather.BENIN),
                new Forecast(R.string.burkina_faso, R.drawable.burkina_faso, Weather.BURKINA_FASO),
                new Forecast(R.string.cape_verde, R.drawable.cape_verde, Weather.CAPE_VERDE),
                new Forecast(R.string.gambia, R.drawable.gambia, Weather.GAMBIA),
                new Forecast(R.string.ghana, R.drawable.ghana, Weather.GHANA),
                new Forecast(R.string.guinea, R.drawable.guinea, Weather.GUINEA),
                new Forecast(R.string.guinea_bissau, R.drawable.guinea_bissau, Weather.GUINEA_bissau),
                new Forecast(R.string.liberia, R.drawable.liberia, Weather.LIBERIA),
                new Forecast(R.string.mali, R.drawable.mali, Weather.MALI),
                new Forecast(R.string.niger, R.drawable.niger, Weather.NIGER),
                new Forecast(R.string.nigeria, R.drawable.nigeria, Weather.NIGERIA),
                new Forecast(R.string.senegal, R.drawable.senegal, Weather.SENEGAL),
                new Forecast(R.string.sierra_leone, R.drawable.sierra_leone, Weather.SIERRA_LEANE),
                new Forecast(R.string.togo, R.drawable.togo, Weather.TOGO),
                //middleAsia
                new Forecast(R.string.kazakhstan, R.drawable.kazakhstan, Weather.KAZAKHSTAN),
                new Forecast(R.string.kyrgyzstan, R.drawable.kyrgyzstan, Weather.KYRGYZSTAN),
                new Forecast(R.string.tajikistan, R.drawable.tajikistan, Weather.TAJIKISTAN),
                new Forecast(R.string.turkmenistan, R.drawable.turkmenistan, Weather.TURKMENISTAN),
                new Forecast(R.string.uzbekistan, R.drawable.uzbekistan, Weather.UZBEKISTAN),
                new Forecast(R.string.afghanistan, R.drawable.afghanistan, Weather.AFGHANISTAN),
                //eastAsia
                new Forecast(R.string.china, R.drawable.china, Weather.CHINA),
                new Forecast(R.string.japan, R.drawable.japan, Weather.JAPAN),
                new Forecast(R.string.north_korea, R.drawable.north_korea, Weather.NORTH_KOREA),
                new Forecast(R.string.korea, R.drawable.korea, Weather.KOREA),
                new Forecast(R.string.mongolia, R.drawable.mongolia, Weather.MANGOLIA),
                //southeast_asia
                new Forecast(R.string.brunei, R.drawable.brunei, Weather.BRUNEI),
                new Forecast(R.string.cambodia, R.drawable.cambodia, Weather.CAMBODIA),
                new Forecast(R.string.east_timor, R.drawable.east_timor, Weather.EAST_TIMOR),
                new Forecast(R.string.indonesia, R.drawable.indonesia, Weather.INDONESIA),
                new Forecast(R.string.laos, R.drawable.laos, Weather.LAOS),
                new Forecast(R.string.malaysia, R.drawable.malaysia, Weather.MALAYSIA),
                new Forecast(R.string.philippines, R.drawable.philippines, Weather.PHILIPPINES),
                new Forecast(R.string.singapore, R.drawable.singapore, Weather.SINGAPORE),
                new Forecast(R.string.thailand, R.drawable.thailand, Weather.THAILAND),
                //south_asia
                new Forecast(R.string.bangladesh, R.drawable.bangladesh, Weather.BANGLDESH),
                new Forecast(R.string.bhutan, R.drawable.bhutan, Weather.BHUTAN),
                new Forecast(R.string.india, R.drawable.india, Weather.INDIA),
                new Forecast(R.string.nepal, R.drawable.nepal, Weather.NEPAL),
                new Forecast(R.string.pakistan, R.drawable.pakistan, Weather.PAKISTAN),
                new Forecast(R.string.sri_lanka, R.drawable.sri_lanka, Weather.SRI_LANKA),
                //west_asia
                new Forecast(R.string.armenia, R.drawable.armenia, Weather.ARMRNIA),
                new Forecast(R.string.azerbaijan, R.drawable.azerbaijan, Weather.AZERBAIJAN),
                new Forecast(R.string.georgia, R.drawable.georgia, Weather.GEORGIA),
                new Forecast(R.string.iran, R.drawable.iran, Weather.IRAN),
                new Forecast(R.string.turkey, R.drawable.turkey, Weather.TURKEY),
                 //north_Europe
                new Forecast(R.string.iceland, R.drawable.iceland, Weather.ICELAND),
                new Forecast(R.string.norway, R.drawable.norway, Weather.NORWAY),
                new Forecast(R.string.sweden, R.drawable.sweden, Weather.SWEDEN),
                new Forecast(R.string.finland, R.drawable.finland, Weather.FINLAND),
                new Forecast(R.string.estonia, R.drawable.estonia, Weather.ESTONIA),
                new Forecast(R.string.latvia, R.drawable.latvia, Weather.LATVIA),
                new Forecast(R.string.lithuania, R.drawable.lithuania, Weather.LITHUANIA),
                new Forecast(R.string.denmark, R.drawable.denmark, Weather.DENMARK),
                new Forecast(R.string.ireland, R.drawable.ireland, Weather.IRELAND),
                new Forecast(R.string.united_kingdom, R.drawable.united_kingdom, Weather.UK),
               //south_Europe
                new Forecast(R.string.portugal, R.drawable.portugal, Weather.PORTUGAL),
                new Forecast(R.string.spain, R.drawable.spain, Weather.SPAIN),
                new Forecast(R.string.malta, R.drawable.malta, Weather.MALTA),
                new Forecast(R.string.italy, R.drawable.italy, Weather.ITALY),
                new Forecast(R.string.greece, R.drawable.greece, Weather.GREECE),
                new Forecast(R.string.albania, R.drawable.albania, Weather.ALBANIA),
                new Forecast(R.string.serbia, R.drawable.serbia, Weather.SERBIA),
                new Forecast(R.string.croatia, R.drawable.croatia, Weather.CROATIA),
                new Forecast(R.string.bosnia_and_herzegovina, R.drawable.bosnia_and_herzegovina, Weather.B_H),
                new Forecast(R.string.slovenia, R.drawable.slovenia, Weather.SLOVENIA),
                //west_Europe
                new Forecast(R.string.france, R.drawable.france, Weather.FRANCE),
                new Forecast(R.string.belgium, R.drawable.belgium, Weather.BELGIUM),
                new Forecast(R.string.luxembourg, R.drawable.luxembourg, Weather.LUXEMBOURG),
                new Forecast(R.string.netherlands, R.drawable.netherlands, Weather.NETHERLANDS),
                new Forecast(R.string.germany, R.drawable.germany, Weather.GERMANY),
                new Forecast(R.string.austria, R.drawable.austria, Weather.AUSTRIA),
                //east_Europe
                new Forecast(R.string.ukraine, R.drawable.ukraine, Weather.UKRAINE),
                new Forecast(R.string.czech, R.drawable.czech, Weather.CZECH),
                new Forecast(R.string.slovakia, R.drawable.slovakia, Weather.SLOVAKIA),
                new Forecast(R.string.russia, R.drawable.russia, Weather.RUSSIA),
                new Forecast(R.string.moldova, R.drawable.moldova, Weather.MOLDOVA),
                new Forecast(R.string.poland, R.drawable.poland, Weather.POLAND),
                new Forecast(R.string.romania, R.drawable.romania, Weather.ROMANIA),
                new Forecast(R.string.bulgaria, R.drawable.bulgaria, Weather.BULGARIA),
                new Forecast(R.string.hungary, R.drawable.hungary, Weather.HUNGARY),
                //north_Amirca
                new Forecast(R.string.canada, R.drawable.canada, Weather.CANADA),
                new Forecast(R.string.mexico, R.drawable.mexico, Weather.MEXICO),
                new Forecast(R.string.usa, R.drawable.usa, Weather.USA),
                //north_Amirca_gzr
                new Forecast(R.string.antigua_and_barbuda, R.drawable.antigua_and_barbuda, Weather.A_B),
                new Forecast(R.string.bahamas, R.drawable.bahamas, Weather.BAHAMAS),
                new Forecast(R.string.barbados, R.drawable.barbados, Weather.BARBADOS),
                new Forecast(R.string.cuba, R.drawable.cuba, Weather.CUBA),
                new Forecast(R.string.dominica, R.drawable.dominica, Weather.DOMINICA),
                new Forecast(R.string.dominican, R.drawable.dominican, Weather.DOMINICAN),
                new Forecast(R.string.grenada, R.drawable.grenada, Weather.GRENADA),
                new Forecast(R.string.haiti, R.drawable.haiti, Weather.HAITI),
                new Forecast(R.string.jamaica, R.drawable.jamaica, Weather.JAMAICA),
                new Forecast(R.string.saint_kitts_and_nevis, R.drawable.saint_kitts_and_nevis, Weather.S_K_N),
                new Forecast(R.string.saint_lucia, R.drawable.saint_lucia, Weather.S_L),
                new Forecast(R.string.saint_vincent_and_the_grenadines, R.drawable.saint_vincent_and_the_grenadines, Weather.S_V_G),
                new Forecast(R.string.trinidad_and_tobago, R.drawable.trinidad_and_tobago, Weather.T_T),
                //middel_Amireca
                new Forecast(R.string.belize, R.drawable.belize, Weather.BELIZE),
                new Forecast(R.string.costa_rica, R.drawable.costa_rica, Weather.COSTA_RICA),
                new Forecast(R.string.el_salvador, R.drawable.el_salvador, Weather.EL_SALVADOR),
                new Forecast(R.string.guatemala, R.drawable.guatemala, Weather.GUATEMALA),
                new Forecast(R.string.honduras, R.drawable.honduras, Weather.HONDURAS),
                new Forecast(R.string.nicaragua, R.drawable.nicaragua, Weather.NICARAGUA),
                new Forecast(R.string.panama, R.drawable.panama, Weather.PANAMA),
                //south_Amirca
                new Forecast(R.string.argentina, R.drawable.argentina, Weather.ARGENTINA),
                new Forecast(R.string.brazil, R.drawable.brazil, Weather.BRAZIL),
                new Forecast(R.string.bolivia, R.drawable.bolivia, Weather.BOLIVIA),
                new Forecast(R.string.colombia, R.drawable.colombia, Weather.COLOMBIA),
                new Forecast(R.string.chile, R.drawable.chile, Weather.CHILE),
                new Forecast(R.string.ecuador, R.drawable.ecuador, Weather.ECUADOR),
                new Forecast(R.string.uruguay, R.drawable.uruguay, Weather.URUGUAY),
                new Forecast(R.string.paraguay, R.drawable.paraguay, Weather.PARAGUAY),
                new Forecast(R.string.suriname, R.drawable.suriname, Weather.SURINAME),
                new Forecast(R.string.venezuela, R.drawable.venezuela, Weather.VENEZUELA),
                //Australia
                new Forecast(R.string.australia, R.drawable.australia, Weather.AUSTRALIA));




    }

}
