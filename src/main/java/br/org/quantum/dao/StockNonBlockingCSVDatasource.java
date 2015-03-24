package br.org.quantum.dao;

import br.org.quantum.domain.Movimento;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import rx.Observable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by xonda on 23/03/2015.
 */
@Repository
public class StockNonBlockingCSVDatasource implements StockNonBlockingDatasource {

    private Resource resource = new ClassPathResource("acoes.csv");

    @Override
    public Observable<Movimento> list() {
        return Observable.<Movimento>create(subscriber -> {

            try {
                DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");

                InputStream in = resource.getInputStream();
                BufferedReader bin = new BufferedReader(new InputStreamReader(in));
                subscriber.onStart();
                bin.lines()
                        .skip(1L)
                        .forEach(s -> {
                            StringTokenizer tokenizer = new StringTokenizer(s, ",");
                            String id = tokenizer.nextToken();
                            try {
                                Date date = parser.parse(tokenizer.nextToken());
                                BigDecimal close = new BigDecimal(tokenizer.nextToken());
                                Long volume = Long.parseLong(tokenizer.nextToken());
                                subscriber.onNext(new Movimento(id, date, close, volume));
                            } catch (ParseException e) {
                                subscriber.onError(e);
                            }
                        });
                subscriber.onCompleted();
            } catch (FileNotFoundException e) {
                subscriber.onError(e);
            } catch (IOException e) {
                subscriber.onError(e);
            }
        });
    }
}
