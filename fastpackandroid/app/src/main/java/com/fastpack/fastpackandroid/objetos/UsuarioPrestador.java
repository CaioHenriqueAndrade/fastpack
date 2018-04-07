package com.fastpack.fastpackandroid.objetos;

/**
 * Created by root on 28/03/18.
 */

public class UsuarioPrestador extends Usuario {


        private Local location ;
        private int raio;

        public Local getLocation() {
            return location;
        }

        public void setLocation(Local location) {
            this.location = location;
        }

        public int getRaio() {
            return raio;
        }

        public void setRaio(int raio) {
            this.raio = raio;
        }


}
