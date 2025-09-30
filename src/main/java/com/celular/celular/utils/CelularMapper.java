package com.celular.celular.utils;

import com.celular.celular.dto.request.CelularForm;
import com.celular.celular.dto.response.CelularDto;
import com.celular.celular.model.CelularModel;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CelularMapper {
    public CelularModel formToModel (CelularForm celularForm){
        if (celularForm == null){
            return null;
        }
        CelularModel celularModel = new CelularModel();
        celularModel.setName(celularForm.getName());
        celularModel.setDescription(celularForm.getDescription());
        return celularModel;
    }
    public CelularDto modelToDto(CelularModel celularModel){
        if (celularModel==null){
            return null;
        }
        CelularDto celularDTO = new CelularDto();
        celularDTO.setName(celularModel.getName());
        celularDTO.setDescription(celularModel.getDescription());
        return celularDTO;
    }
    public Set<CelularDto> setDtoToSetModel (Set<CelularModel> celularModels){
        if (celularModels==null || celularModels.isEmpty())
            return Collections.emptySet();
        return celularModels.stream()
                .map(this::modelToDto)
                .collect(Collectors.toSet());
    }

    public CelularModel updateFormToModel (CelularModel celularModel, CelularForm celularForm){
        celularModel.setName(celularForm.getName());
        celularModel.setDescription(celularForm.getDescription());
        return celularModel;
    }

    public CelularModel patchFormToModel(CelularModel celularModel, CelularForm celularForm) {
        if (celularForm.getName() != null && !celularForm.getName().isBlank()) {
            celularModel.setName(celularForm.getName());
        }
        if (celularForm.getDescription() != null && !celularForm.getDescription().isBlank()) {
            celularModel.setDescription(celularForm.getDescription());
        }
        return celularModel;
    }


}
