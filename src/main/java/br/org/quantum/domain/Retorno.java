package br.org.quantum.domain;

import java.math.BigDecimal;

/**
 * Created by xonda on 07/04/2015.
 */
public class Retorno {
    BigDecimal valor;
    Movimento corrente;
    Movimento anterior;

    public Retorno() {

    }

    public Retorno(Movimento corrente, Movimento anterior) {
        this.corrente = corrente;
        this.anterior = anterior;
        this.valor = corrente.getClose().divide(anterior.getClose(), 2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.ONE);
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Movimento getCorrente() {
        return corrente;
    }

    public Movimento getAnterior() {
        return anterior;
    }

    public Boolean isValid() {
        return corrente!=null && anterior != null && corrente.getId().equals(anterior.getId());
    }
}
