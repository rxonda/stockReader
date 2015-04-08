package br.org.quantum.domain;

import java.math.BigDecimal;

/**
 * Created by xonda on 07/04/2015.
 */
public class Retorno {
    private final BigDecimal valor;
    private final Movimento corrente;
    private final Movimento anterior;

    public Retorno() {
        valor = null;
        anterior = null;
        corrente = null;
    }

    private Retorno(Movimento anterior, Movimento corrente) {
        this.corrente = corrente;
        this.anterior = anterior;
        this.valor = isValid() ? corrente.getClose().divide(anterior.getClose(), 2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.ONE) : null;
    }

    public BigDecimal getValor() {
        verifica();
        return valor;
    }

    public Movimento getCorrente() {
        verifica();
        return corrente;
    }

    public Retorno setCorrente(Movimento movimento) {
        return new Retorno(this.corrente, movimento);
    }

    public Boolean isValid() {
        return corrente!=null && anterior != null && corrente.getId().equals(anterior.getId());
    }

    private void verifica() {
        if(!isValid()) {
            throw new IllegalStateException("Objeto eh invalido");
        }
    }
}
