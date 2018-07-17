package quantum

import org.junit.Assert
import quantum.domain.Average
import quantum.domain.Movimento
import quantum.domain.Retorno

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by xonda on 10/12/16.
 */
class TestUtils {
    static void assertMovimento(Movimento m, String id, Date date, BigDecimal close, Long volume) {
        assert id == m.id, 'O id do movimento deve ser'
        assert date == m.date, 'A data do movimento deve ser'
        assert close == m.close, 'O fechamento deve ser '
        assert volume == m.volume, 'O volume deve ser '
    }

    static void assertRetorno(Retorno r, String id, Date date, BigDecimal close, Long volume) {
        assert id == r.id, 'O id do movimento deve ser'
        assert date == r.date, 'A data do movimento deve ser'
        assert close == r.close, 'O fechamento deve ser '
        assert volume == r.volume, 'O volume deve ser'
    }

    static void assertVolumeMedio(Average v, String id, Double volume) {
        assert id == v.id, 'O id do movimento deve ser '
        Assert.assertEquals('O volume deve ser ', volume, v.volume, 0.001d)
    }

    static Date data(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd")
        try {
            return fmt.parse(date)
        } catch (ParseException e) {
            throw new RuntimeException(e)
        }
    }
}
