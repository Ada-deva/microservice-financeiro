package com.br.microservice.financeiro.validation;

import com.br.microservice.financeiro.enums.TipoFornecedor;
import com.br.microservice.financeiro.model.Fornecedor;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class ClienteGroupSequenceProvider implements DefaultGroupSequenceProvider<Fornecedor> {
    public List<Class<?>> getValidationGroups(Fornecedor entity) {
        List<Class<?>> groups = new ArrayList<>();

        groups.add(Fornecedor.class);

        if (entity != null) {
            if (TipoFornecedor.PF.equals(entity.getTipoFornecedor())) {
                groups.add(PessoaFisica.class);
            } else if (TipoFornecedor.PJ.equals(entity.getTipoFornecedor())) {
                groups.add(PessoaJuridica.class);
            }
        }

        return groups;
    }
}
