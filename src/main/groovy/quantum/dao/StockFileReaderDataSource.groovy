package quantum.dao

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Repository
import quantum.domain.Movimento

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.stream.Stream

/**
 * Created by xonda on 15/03/2015.
 */
@Repository
class StockFileReaderDataSource implements StockDataSource {

    @Override
    Stream<Movimento> list(Map params = [:]) {
        Stream s = source().lines().skip(1L)
        if(params.start) {
            s = s.skip(params.start)
        }
        if(params.offset) {
            s = s.limit(params.offset)
        }

        s.map (deserialize)
    }

    private BufferedReader source() {
        Resource resource = new ClassPathResource("acoes.csv")
        InputStream inputStream = resource.getInputStream()
        BufferedReader bin = new BufferedReader(new InputStreamReader(inputStream))
        bin
    }

    private Closure deserialize = { String line ->
        DateFormat parser = new SimpleDateFormat("yyyy-MM-dd")
        StringTokenizer tokenizer = new StringTokenizer(line, ",")
        String id = tokenizer.nextToken()
        Date date = parser.parse(tokenizer.nextToken())
        BigDecimal close = new BigDecimal(tokenizer.nextToken())
        Long volume = Long.parseLong(tokenizer.nextToken())
        new Movimento([id: id, date: date, close: close, volume: volume])
    }
}
