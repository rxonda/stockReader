package quantum.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xonda on 07/04/2015.
 */
public class Retorno {
    private final BigDecimal valor;
    private final Movimento corrente;
    private final Boolean valid;

    public Retorno() {
        valor = null;
        corrente = null;
        valid = false;
    }

    public Retorno(Movimento movimento, BigDecimal valor, Boolean valid) {
        this.corrente = movimento;
        this.valor = valor;
        this.valid = valid;
    }

    private Retorno(Movimento anterior, Movimento corrente) {
        this.corrente = corrente;
        this.valid = corrente!=null && anterior != null && corrente.getId().equals(anterior.getId());
        this.valor = isValid() ? corrente.getClose().divide(anterior.getClose(), 2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.ONE) : null;
    }

    public String getId() {
        verifica();
        return corrente.getId();
    }

    public Date getDate() {
        verifica();
        return corrente.getDate();
    }

    public BigDecimal getClose() {
        verifica();
        return corrente.getClose();
    }

    public Long getVolume() {
        verifica();
        return corrente.getVolume();
    }

    public BigDecimal getValor() {
        verifica();
        return this.valor;
    }

    public Retorno setCorrente(Movimento movimento) {
        return new Retorno(this.corrente, movimento);
    }

    public Boolean isValid() {
        return this.valid;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Retorno)) {
            return false;
        }
        if(obj == this) {
            return true;
        }

        Retorno other = (Retorno)obj;

        return (this.corrente.equals(other.corrente) && this.valor.equals(other.valor) && this.valid.equals(other.valid));
    }

    @Override
    public String toString() {
        return "Retorno{" +
                "valor=" + valor +
                ", corrente=" + corrente +
                ", valid=" + valid +
                '}';
    }

    private void verifica() {
        if(!isValid()) {
            throw new IllegalStateException("Objeto eh invalido");
        }
    }
}
