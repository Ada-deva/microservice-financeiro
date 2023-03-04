package com.br.microservice.financeiro.client;

import com.br.microservice.financeiro.configuration.EnvConfiguration;
import com.br.microservice.financeiro.model.ViaCEPApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ViaCEPApiCLient {

    private final EnvConfiguration env;
    private final WebClient client = WebClient.create();

    public ViaCEPApi getViaCEPApi(String cep) {

        log.info("---Formatando  CEP do Cliente---");
        cep = cep.replace("-", "");
        String uri = env.getUri();
        uri = uri + cep + "/json/";

        log.info("---Requisitando endereço---");
        Mono<ViaCEPApi> viaCEPApiMono =
                client.get().uri(uri).retrieve().bodyToMono(ViaCEPApi.class);
        return viaCEPApiMono.block();

    }
}
