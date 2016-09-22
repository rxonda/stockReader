package quantum.domain
/**
 * Created by xonda on 07/04/2015.
 */
class Retorno {
    private final BigDecimal valor
    private final Movimento corrente
    private final Boolean valid

    Retorno() {
        valor = null
        corrente = null
        valid = false
    }

    private Retorno(Movimento anterior, Movimento corrente) {
        this.corrente = corrente
        this.valid = corrente!=null && anterior != null && corrente.getId().equals(anterior.getId())
        this.valor = isValid() ? corrente.getClose().divide(anterior.getClose(), 2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.ONE) : null
    }

    String getId() {
        verifica()
        corrente.getId()
    }

    Date getDate() {
        verifica()
        corrente.getDate()
    }

    BigDecimal getClose() {
        verifica()
        corrente.getClose()
    }

    Long getVolume() {
        verifica()
        corrente.getVolume()
    }

    BigDecimal getValor() {
        verifica()
        valor
    }

    Retorno setCorrente(Movimento movimento) {
        new Retorno(this.corrente, movimento)
    }

    Boolean isValid() {
        valid
    }

    private void verifica() {
        if(!isValid()) {
            throw new IllegalStateException("Objeto eh invalido")
        }
    }
}
