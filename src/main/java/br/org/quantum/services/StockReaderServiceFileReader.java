package br.org.quantum.services;

import br.org.quantum.domain.Stock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.stream.Stream;

/**
 * Created by xonda on 12/03/2015.
 */
@Service
public class StockReaderServiceFileReader implements StockReaderService {

    private Resource resource = new ClassPathResource("acoes.csv");

    @Override
    public Collection<Stock> list() {
        try {
            DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
            Collection<Stock> resultado = new ArrayList<Stock>();

            URI uri = resource.getURI();

            Files.lines(Paths.get(uri))
            .flatMap(s -> {
                StringTokenizer tokenizer = new StringTokenizer(s, ",");
                String id = tokenizer.nextToken();
                if (id.equals("Acao")) {
                    return null;
                }
                try {
                    Date date = parser.parse(tokenizer.nextToken());
                    BigDecimal close = new BigDecimal(tokenizer.nextToken());
                    Long volume = Long.parseLong(tokenizer.nextToken());
                    return Stream.of(new Stock(id, date, close, volume));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }).forEach(stock -> resultado.add(stock));
            return resultado;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
        }
    }
}
