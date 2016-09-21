package quantum.domain
/**
 * Created by xonda on 07/04/2015.
 */
public class Retorno {
    private final BigDecimal valor
    private final Movimento corrente
    private final Boolean valid

    public Retorno() {
        valor = null
        corrente = null
        valid = false
    }

    private Retorno(Movimento anterior, Movimento corrente) {
        this.corrente = corrente
        this.valid = corrente!=null && anterior != null && corrente.getId().equals(anterior.getId())
        this.valor = isValid() ? corrente.getClose().divide(anterior.getClose(), 2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.ONE) : null
    }

    public String getId() {
        verifica()
        return corrente.getId()
    }

    public Date getDate() {
        verifica()
        return corrente.getDate()
    }

    public BigDecimal getClose() {
        verifica()
        return corrente.getClose()
    }

    public Long getVolume() {
        verifica()
        return corrente.getVolume()
    }

    public BigDecimal getValor() {
        verifica()
        return valor
    }

    public Retorno setCorrente(Movimento movimento) {
        return new Retorno(this.corrente, movimento)
    }

    public Boolean isValid() {
        return valid
    }

    private void verifica() {
        if(!isValid()) {
            throw new IllegalStateException("Objeto eh invalido")
        }
    }
}
