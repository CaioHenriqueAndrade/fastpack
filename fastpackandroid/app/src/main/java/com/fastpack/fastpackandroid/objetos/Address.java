package com.fastpack.fastpackandroid.objetos;

import android.content.res.Resources;

import org.jetbrains.annotations.NotNull;

/**
 * Created by root on 28/03/18.
 */

public class Address extends ObjectBasic {

    private int idCriador;

    private String street = "";

    private String complementary = "";

    private String streetNumber = "";

    private String neighborhood = "";

    private String city = "";

    private String state = "";

    private String zipcode = "";

    private String country = "";


    private int valido = VALIDADE_FALSE;

    private Local local;

    public static final int VALIDADE_FALSE = 0;
    public static final int VALIDADE_TRUE = 1;


    @NotNull
    public String format() {
        String formated = getNeighborhood() + " - " + getCity() ;

        if(getComplementary() != null && !getComplementary().isEmpty()) {
            formated += "\n" + getComplementary();
        }

        return formated;
    }

    public String validate(Resources res) {

        if (street.replaceAll(" ", "").length() < 5)
            return "Precisamos que informe sua rua";

        if (streetNumber.replaceAll(" ", "").equals(""))
            return "Informa o número da residência";

        if (city.replaceAll(" ", "").length() < 2)
            return "Informe sua cidade";

        /*
        if (state.equals(res.getString(R.string.toque_para_selecionar)))
            return "Informe seu estado";
*/
        if (zipcode.equals(""))
            return "Informe seu CEP";

        if (neighborhood.replaceAll(" ", "").equals("")) {
            return "Informe o bairro!";
        }

        if (zipcode.length() < 7)
            return "CEP esta incorreto.";


        return null;
    }

    public boolean existsLocal() {
        return getLocal() != null && getLocal().getLatitude() != 0;
    }

    public int getIdCriador() {
        return idCriador;
    }

    public void setIdCriador(int idCriador) {
        this.idCriador = idCriador;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getComplementary() {
        return complementary;
    }

    public void setComplementary(String complementary) {
        this.complementary = complementary;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getValido() {
        return valido;
    }

    public void setValido(int valido) {
        this.valido = valido;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }
}
