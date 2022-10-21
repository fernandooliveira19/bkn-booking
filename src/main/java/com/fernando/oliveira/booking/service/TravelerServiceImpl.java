package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Traveler;
import com.fernando.oliveira.booking.domain.enums.StatusEnum;
import com.fernando.oliveira.booking.domain.mapper.TravelerMapper;
import com.fernando.oliveira.booking.domain.request.CreateTravelerRequest;
import com.fernando.oliveira.booking.domain.request.UpdateTravelerRequest;
import com.fernando.oliveira.booking.domain.response.BookingTravelerResponse;
import com.fernando.oliveira.booking.domain.response.TravelerDetailResponse;
import com.fernando.oliveira.booking.exception.TravelerException;
import com.fernando.oliveira.booking.repository.TravelerRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.fernando.oliveira.booking.domain.enums.StatusEnum.ACTIVE;
import static com.fernando.oliveira.booking.utils.FormatterUtils.*;

@Service
public class TravelerServiceImpl implements TravelerService {


    @Autowired
    private TravelerRepository repository;

    @Autowired
    private TravelerMapper travelerMapper;


    public TravelerDetailResponse createTraveler(CreateTravelerRequest request) {

        Traveler traveler = travelerMapper.requestToCreateTraveler(request);

        formatFields(traveler);
        validate(traveler);
        traveler.setStatus(ACTIVE.getCode());
        traveler.setInsertDate(LocalDateTime.now());

        return travelerMapper.travelerToTravelerDetailResponse(repository.save(traveler))   ;
    }

    private void validate(Traveler traveler) {
        List<Traveler> travelers = findTravelersByNameOrEmail(traveler.getName(), traveler.getEmail());

        if (!travelers.isEmpty()) {

            if (traveler.getId() == null) {
                throw new TravelerException("Já existe outro viajante cadastrado com mesmo nome ou email");
            } else {
                validateUpdateTraveler(traveler, travelers);
            }
        }

    }

    private void validateUpdateTraveler(Traveler traveler, List<Traveler> travelers) {

        for (Traveler t : travelers) {
            if (!t.getId().equals(traveler.getId())) {
                throw new TravelerException("Já existe outro viajante cadastrado com mesmo nome ou email");
            }
        }

    }

    @Override
    public List<Traveler> findTravelersByNameOrEmail(String name, String email) {

        return repository.findByNameOrEmail(name, email);

    }

    @Override
    public Traveler findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TravelerException("Nenhum viajante encontrado pelo id: " + id));
    }

    @Override
    public TravelerDetailResponse getTravelerDetail(Long id) {
        return travelerMapper.travelerToTravelerDetailResponse(this.findById(id));
    }

    @Override
    public List<TravelerDetailResponse> findAll() {

        return repository
                .findAll()
                .stream()
                .map(e -> travelerMapper.travelerToTravelerDetailResponse(e))
                .collect(Collectors.toList());

    }

    @Override
    public TravelerDetailResponse updateTraveler(Long id, UpdateTravelerRequest request) {
        Traveler travelerToUpdate = findById(id);
        travelerToUpdate.setName(request.getName());
        travelerToUpdate.setEmail(request.getEmail());
        travelerToUpdate.setDocument(request.getDocument());
        travelerToUpdate.setStatus(request.getStatus());
        travelerToUpdate.setPrefixPhone(request.getPrefixPhone());
        travelerToUpdate.setNumberPhone(request.getNumberPhone());
        formatFields(travelerToUpdate);
        validate(travelerToUpdate);
        travelerToUpdate.setLastUpdateDate(LocalDateTime.now());
        return travelerMapper
                .travelerToTravelerDetailResponse(repository.save(travelerToUpdate));
    }

    @Override
    public List<TravelerDetailResponse> findByNameContainingOrderByNameAsc(String name) {

        return repository.findByNameContainingIgnoreCaseOrderByNameAsc(name)
                .stream()
                .map(traveler -> travelerMapper.travelerToTravelerDetailResponse(traveler))
                .collect(Collectors.toList());

    }

    @Override
    public void inactivateTraveler(Long id) {
        Traveler traveler = findById(id);
        traveler.setStatus(StatusEnum.INACTIVE.getCode());
        repository.save(traveler);
    }

    @Override
    public List<TravelerDetailResponse> findActiveTravelers() {
        return repository
                .findActiveTravelers()
                .stream()
                .map(traveler -> travelerMapper.travelerToTravelerDetailResponse(traveler))
                .collect(Collectors.toList());
    }

    private void formatFields(Traveler traveler) {

        if (!StringUtils.isEmpty(traveler.getDocument())){
            traveler.setDocument(formatCpf(traveler.getDocument()));
        }

        traveler.setNumberPhone(formatPhoneNumber(traveler.getNumberPhone()));
    }

}
