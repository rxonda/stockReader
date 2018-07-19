package quantum.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import quantum.domain.Movimento;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by xonda on 15/03/2015.
 */
public class StockFileReaderDataSource implements StockDataSource {

    private static final Logger log = LoggerFactory.getLogger(StockFileReaderDataSource.class);

    @Override
    public Stream<Movimento> list() {
        return list(new HashMap());
    }

    @Override
    public Stream<Movimento> list(Map<String, Object> params) {
        return new Stream.Builder<Movimento>() {
            @Override
            public void accept(Movimento o) {}

            @Override
            public Stream<Movimento> build() {
                try {
                    Stream<String> s = source().lines().skip(1L);
                    if(params.containsKey("start")) {
                        s = s.skip((Integer) params.get("start"));
                    }
                    if(params.containsKey("offset")) {
                        s = s.limit((Integer) params.get("offset"));
                    }
                    return s.map(transformer());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }.build();
    }

    private Function<String, Movimento> transformer() {
        return (line) -> {
            try {
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                String id = tokenizer.nextToken();
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(tokenizer.nextToken());
                BigDecimal close = new BigDecimal(tokenizer.nextToken());
                Long volume = Long.parseLong(tokenizer.nextToken());

               if(log.isTraceEnabled()) log.trace("Creating Movimento");

                return new Movimento(id, date, close, volume);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        };
    }

    private BufferedReader source() throws IOException {
        Resource resource = new ClassPathResource("acoes.csv");
        InputStream inputStream = resource.getInputStream();
        BufferedReader bin = new BufferedReader(new InputStreamReader(inputStream));
        return bin;
    }
}
