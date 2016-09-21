package quantum.dao

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Repository
import quantum.domain.Movimento
import rx.Observable

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by xonda on 23/03/2015.
 */
@Repository
public class StockNonBlockingCSVDatasource implements StockNonBlockingDatasource {

    private Resource resource = new ClassPathResource("acoes.csv")

    @Override
    public Observable<Movimento> list() {
        return Observable.<Movimento>create({ subscriber ->

            try {
                DateFormat parser = new SimpleDateFormat("yyyy-MM-dd")

                InputStream inputStream = resource.getInputStream()
                BufferedReader bin = new BufferedReader(new InputStreamReader(inputStream))
                subscriber.onStart()
                bin.lines()
                        .skip(1L)
                        .forEach({ s ->
                            StringTokenizer tokenizer = new StringTokenizer(s, ",")
                            String id = tokenizer.nextToken()
                            try {
                                Date date = parser.parse(tokenizer.nextToken())
                                BigDecimal close = new BigDecimal(tokenizer.nextToken())
                                Long volume = Long.parseLong(tokenizer.nextToken())
                                subscriber.onNext(new Movimento(id, date, close, volume))
                            } catch (ParseException e) {
                                subscriber.onError(e)
                            }
                        })
                subscriber.onCompleted()
            } catch (FileNotFoundException e) {
                subscriber.onError(e)
            } catch (IOException e) {
                subscriber.onError(e)
            }
        })
    }
}
