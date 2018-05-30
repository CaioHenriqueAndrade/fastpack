package com.fastpack.fastpackandroid.objetos;

import android.content.DialogInterface;
import android.content.res.Resources;

import com.fastpack.fastpackandroid.utils.UtilsConvert;
import com.fastpack.fastpackandroid.utils.UtilsDialog;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

public class Pedido {

    //Quando o pedido e criado e chega ao servidor
    public static final int STATUS_JUST_ENVIADO = 0;

    //quando esta aguardando a entrega, ja selecionado quem ira realizar
    public static final int STATUS_AGUARDE_ENTREGA = 1;

    //quando ja esta entregue
    public static final int STATUS_ENTREGUE		= 2;

    public static final int STATUS_CANCELADO = 3;

    private int id,idUser, idPrestador, idAddressBusca, idAddressEntrega, valor, status;
    private String descPedido, horaPostado, horaPrazo, horaRecebido;

    private Address addressEntrega, addressRetirada;
    private Local local;


    public boolean isJustEnviadoAoServer() {
        return getStatus() == STATUS_JUST_ENVIADO;
    }

    public boolean isAguardandoEntrega() {
        return getStatus() == STATUS_AGUARDE_ENTREGA;
    }

    public boolean isEntregue() {
        return getStatus() == STATUS_ENTREGUE;
    }

    public boolean isCancelado() {
        return getStatus() == STATUS_CANCELADO;
    }

    @NotNull
    public CharSequence getStatusLayout(@NotNull Resources resources) {
        if( isJustEnviadoAoServer() ) {
            return "Aguardando alguém aceitar";
        } else if( isAguardandoEntrega() ){
            return "Pedido aceito";
        } else if( isEntregue() ) {
            return "Pedido entregue";
        } else if( isCancelado() ) {
            return "Pedido cancelado";
        } else throw new IllegalStateException("not implemented " + getStatus() );
    }

    @NotNull
    public CharSequence getLayoutPreco(@NotNull Resources resources) {
        return "Preço R$: " + UtilsConvert.toPreco( getValor() );
    }

    @NotNull
    public CharSequence getLayoutPedidoData(@NotNull Resources resources) {
        return "Pedido de " + UtilsConvert.dataMysqlToData( horaPostado );
    }

    public int getIdPrestador() {
        return idPrestador;
    }
    public void setIdPrestador(int idPrestador) {
        this.idPrestador = idPrestador;
    }
    public int getIdAddressBusca() {
        return idAddressBusca;
    }
    public void setIdAddressBusca(int id) {
        this.idAddressBusca = id;
    }
    public int getIdAddressEntrega() {
        return idAddressEntrega;
    }
    public void setIdAddressEntrega(int idAddressEntrega) {
        this.idAddressEntrega = idAddressEntrega;
    }
    @NotNull
    public String toJson() {
        return new Gson().toJson(this );
    }

    public void notifyAtualizacao(@NotNull Pedido pedido) {
        setStatus( pedido.getStatus() );
        setHoraPostado( pedido.getHoraPostado() );
        setHoraPrazo( pedido.getHoraPrazo() );
        setHoraRecebido( pedido.getHoraRecebido() );
        setIdPrestador( pedido.getIdPrestador() );
    }
    public int getValor() {
        return valor;
    }
    public void setValor(int valor) {
        this.valor = valor;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getDescPedido() {
        return descPedido;
    }
    public void setDescPedido(String descPedido) {
        this.descPedido = descPedido;
    }
    public String getHoraPostado() {
        return horaPostado;
    }
    public void setHoraPostado(String horaPostado) {
        this.horaPostado = horaPostado;
    }

    public String getHoraPrazo() {
        return horaPrazo;
    }

    public void setHoraPrazo(String horaPrazo) {
        this.horaPrazo = horaPrazo;
    }

    public String getHoraRecebido() {
        return horaRecebido;
    }

    public Address getAddressEntrega() {
        return addressEntrega;
    }

    public void setAddressEntrega(Address addressEntrega) {
        this.addressEntrega = addressEntrega;
    }

    public Address getAddressRetirada() {
        return addressRetirada;
    }

    public void setAddressRetirada(Address addressRetirada) {
        this.addressRetirada = addressRetirada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setHoraRecebido(String horaRecebido) {
        this.horaRecebido = horaRecebido;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }
}
