package quantum.dao

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Repository
import quantum.domain.Movimento

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.stream.Stream

/**
 * Created by xonda on 15/03/2015.
 */
@Repository
class StockFileReaderDataSource implements StockDataSource {

    private Resource resource = new ClassPathResource("acoes.csv")

    @Override
    Stream<Movimento> list() {
        try {
            DateFormat parser = new SimpleDateFormat("yyyy-MM-dd")

            InputStream inputStream = resource.getInputStream()
            BufferedReader bin = new BufferedReader(new InputStreamReader(inputStream))

            bin.lines()
                    .skip(1L)
                    .map{ s ->
                        StringTokenizer tokenizer = new StringTokenizer(s, ",")
                        String id = tokenizer.nextToken()
                        try {
                            Date date = parser.parse(tokenizer.nextToken())
                            BigDecimal close = new BigDecimal(tokenizer.nextToken())
                            Long volume = Long.parseLong(tokenizer.nextToken())
                            return new Movimento(id, date, close, volume)
                        } catch (ParseException e) {
                            throw new RuntimeException(e)
                        }
                    }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
