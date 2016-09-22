package quantum.services

import org.junit.Assert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import quantum.Application
import quantum.domain.Movimento
import quantum.domain.VolumeMedio
import spock.lang.Specification

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by xonda on 12/03/2015.
 */
@ContextConfiguration
@SpringBootTest(classes = Application.class)
public class MovimentoReaderServiceSpec extends Specification {

    @Autowired
    private StockReaderService stockReaderService

    void "Deve Ler A Listagem De Movimento"() {
        when:
        Collection<Movimento> movimentos = stockReaderService.list()
        then:
        assert 11 == movimentos.size(), 'A qtd de movimentos deve ser '
    }

    void "Deve Listar Fechamentos Maximo"() {
        when:
        Collection<Movimento> movimentos = stockReaderService.fechamentosMaximo()
        
        then:
        assert 3 == movimentos.size(), 'A Qtd de fechamentos deve ser '
        
        Iterator<Movimento> it = movimentos.iterator()
        assertMovimento(it.next(), "PETR4", data("04/01/2013"), new BigDecimal("20.43"), 36141000L)
        assertMovimento(it.next(), "OGXP3", data("03/01/2013"), new BigDecimal("4.90"), 38143400L)
        assertMovimento(it.next(), "VALE5", data("02/01/2013"), new BigDecimal("42.60"), 18515700L)
    }

    void "deve Listar Fechamentos Minimo"() {
        when:
        Collection<Movimento> movimentos = stockReaderService.fechamentosMinimo()
        
        then:
        assert 3 == movimentos.size(), 'A Qtd de fechamentos deve ser '
        
        Iterator<Movimento> it = movimentos.iterator()
        assertMovimento(it.next(), "PETR4", data("02/01/2013"), new BigDecimal("19.69"), 30182600L)
        assertMovimento(it.next(), "OGXP3", data("01/01/2013"), new BigDecimal("4.38"), 0L)
        assertMovimento(it.next(), "VALE5", data("01/01/2013"), new BigDecimal("40.87"), 0L)
    }

    void "Deve Listar Retorno Maximo"() {
        when:
        Collection<Movimento> movimentos = stockReaderService.retornosMaximo()
        
        then:
        assert 3 == movimentos.size(), 'A Qtd de fechamentos deve ser '
        
        Iterator<Movimento> it = movimentos.iterator()
        assertMovimento(it.next(), "OGXP3", data("02/01/2013"), new BigDecimal("4.76"), 45904000L)
        assertMovimento(it.next(), "PETR4", data("03/01/2013"), new BigDecimal("20.40"), 30552600L)
        assertMovimento(it.next(), "VALE5", data("02/01/2013"), new BigDecimal("42.60"), 18515700L)
    }

    void "Deve Listar Retorno Minimo"() {
        when:
        Collection<Movimento> movimentos = stockReaderService.retornosMinimo()
        
        then:
        assert 3 == movimentos.size(), 'A Qtd de fechamentos deve ser '
        
        Iterator<Movimento> it = movimentos.iterator()
        assertMovimento(it.next(), "OGXP3", data("03/01/2013"), new BigDecimal("4.90"), 38143400L)
        assertMovimento(it.next(), "PETR4", data("07/01/2013"), new BigDecimal("20.08"), 28069600L)
        assertMovimento(it.next(), "VALE5", data("04/01/2013"), new BigDecimal("41.36"), 26351900L)
    }

    void "Deve Listar Volume Medio Cada Acao"() {
        when:
        Collection<VolumeMedio> movimentos = stockReaderService.volumesMedio()
        
        then:
        assert 3 == movimentos.size(), 'A Qtd de fechamentos deve ser '

        Iterator<VolumeMedio> it = movimentos.iterator()
        assertVolumeMedio(it.next(), "PETR4", 31236450d)
        assertVolumeMedio(it.next(), "OGXP3", 42023700d)
        assertVolumeMedio(it.next(), "VALE5", 19956466.6666667d)
    }

    private void assertMovimento(Movimento m, String id, Date date, BigDecimal close, Long volume) {
        assert id == m.getId(), 'O id do movimento deve ser '
        assert date == m.getDate(), 'A data do movimento deve ser '
        assert close == m.getClose(), 'O fechamento deve ser '
        assert volume == m.getVolume(), 'O volume deve ser '
    }

    private void assertVolumeMedio(VolumeMedio v, String id, Double volume) {
        assert id == v.getId(), 'O id do movimento deve ser'
        Assert.assertEquals('O volume deve ser ', volume, v.getVolume(), 0.001d)
    }

    private Date data(String date) {
        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy")
        try {
            return fmt.parse(date)
        } catch (ParseException e) {
            throw new RuntimeException(e)
        }
    }
}
