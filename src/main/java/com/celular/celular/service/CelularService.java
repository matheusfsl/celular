package com.celular.celular.service;


import com.celular.celular.Exception.CelularAlreadyExistsException;
import com.celular.celular.Exception.CelularInsertException;
import com.celular.celular.Exception.CelularUpdateExeception;
import com.celular.celular.dto.request.CelularForm;
import com.celular.celular.utils.CelularMapper;
import com.celular.celular.Exception.CelularNotFoundException;
import com.celular.celular.dto.response.CelularDto;
import com.celular.celular.model.CelularModel;
import com.celular.celular.repository.CelularRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CelularService {
    private final CelularRepository celularRepository;
    private final CelularMapper celularMapper;

    public CelularService(CelularRepository celularRepository, CelularMapper celularMapper) {
        this.celularRepository = celularRepository;
        this.celularMapper = celularMapper;
    }

    public CelularModel getCelularModelByName(String celularName) {
        return celularRepository.findByNameContainingIgnoreCase(celularName)
                .orElseThrow(() -> new CelularNotFoundException(
                        String.format("O Celular '%s' não foi encontrado", celularName)
                ));
    }

    public CelularDto getCelularByName(String celularName) {
        CelularModel celularModel = getCelularModelByName(celularName);
        return celularMapper.modelToDto(celularModel);
    }

    public Set<CelularDto> getAllCelular() {
        Set<CelularModel> celularModelSet = celularRepository.findByIsActiveTrue();
        if (celularModelSet.isEmpty()) {
            throw new CelularNotFoundException("No celular was found");
        }
        return celularMapper.setDtoToSetModel(celularModelSet);
    }

    @Transactional
    public CelularDto createCelularDto(CelularForm celularForm) {
        if (celularRepository.findByNameContainingIgnoreCase(celularForm.getName()).isPresent()) {
            throw new CelularAlreadyExistsException(
                    String.format("O celular '%s' ja está cadastrado ", celularForm.getName())
            );
        }
        try {
            CelularModel newCelularModel = celularMapper.formToModel(celularForm);
            newCelularModel.setActive(true);
            newCelularModel = celularRepository.save(newCelularModel);
            return celularMapper.modelToDto(newCelularModel);
        }catch (DataIntegrityViolationException err){
            throw new CelularInsertException(
                    String.format("Falha ao cadastrar o celular '%s'. Verifique se os dados cadastrados estão corretos.", celularForm.getName())
            );
        }
    }

    @Transactional
    public CelularDto updateCelularDto(String celularName, CelularForm celularForm){
        if (celularRepository.findByNameContainingIgnoreCase(celularName).isEmpty()){
            throw new CelularNotFoundException(
                    String.format("O celular '%s' não foi encontrado", celularName)
            );
        }
        CelularModel celularModel = getCelularModelByName(celularName);
        try{
            CelularModel celularUpdateModel = celularMapper.updateFormToModel(celularModel,celularForm);
            celularRepository.save(celularUpdateModel);
            return celularMapper.modelToDto(celularUpdateModel);
        }catch (ConstraintViolationException | DataIntegrityViolationException | OptimisticLockingFailureException e){
            throw new CelularUpdateExeception(
                    String.format("Falha ao atualizar o celular '%s'. Verifique se os dados estão corretos", celularName)
            );
        }
    }

    @Transactional
    public CelularDto patchCelularByName(String celularName, CelularForm celularForm) {
        if (celularRepository.findByNameContainingIgnoreCase(celularName).isEmpty()){
            throw new CelularNotFoundException(
                    String.format("O celular '%s' não foi encontrado", celularName)
            );
        }

        CelularModel celularModel = getCelularModelByName(celularName);
        try {
            CelularModel celularPatched = celularMapper.patchFormToModel(celularModel, celularForm);
            celularRepository.save(celularPatched);
            return celularMapper.modelToDto(celularPatched);

        } catch (ConstraintViolationException | DataIntegrityViolationException | OptimisticLockingFailureException e) {
            throw new CelularUpdateExeception(
                    String.format("Falha ao atualizar parcialmente o celular '%s'. Verifique os dados enviados.", celularName)
            );
        }
    }

    @Transactional
    public Set<CelularDto> createCelularesLote(List<CelularForm> celularFormList){
        List<CelularModel> celularModels = celularFormList.stream()
                .filter(form -> celularRepository.findByNameContainingIgnoreCase(form.getName()).isEmpty())
                .map(celularMapper::formToModel)
                .peek(model -> model.setActive(true))
                .toList();
        List<CelularModel> saved = celularRepository.saveAll(celularModels);

        return saved.stream()
                .map(celularMapper::modelToDto)
                .collect(Collectors.toSet());
    }

    @Transactional
    public void softDeleteCelularByName(String celularname){
        CelularModel celularModel = getCelularModelByName(celularname);
        celularModel.setActive(false);
        celularRepository.save(celularModel);
    }

}
