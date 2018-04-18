package com.tsxy.carl.service;

import com.tsxy.carl.domain.ConsultRoom;
import com.tsxy.carl.repository.ConsultRoomRepository;
import com.tsxy.carl.service.dto.ConsultRoomDTO;
import com.tsxy.carl.service.mapper.ConsultRoomMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ConsultRoom.
 */
@Service
@Transactional
public class ConsultRoomService {

    private final Logger log = LoggerFactory.getLogger(ConsultRoomService.class);

    private final ConsultRoomRepository consultRoomRepository;

    private final ConsultRoomMapper consultRoomMapper;

    public ConsultRoomService(ConsultRoomRepository consultRoomRepository, ConsultRoomMapper consultRoomMapper) {
        this.consultRoomRepository = consultRoomRepository;
        this.consultRoomMapper = consultRoomMapper;
    }

    /**
     * Save a consultRoom.
     *
     * @param consultRoomDTO the entity to save
     * @return the persisted entity
     */
    public ConsultRoomDTO save(ConsultRoomDTO consultRoomDTO) {
        log.debug("Request to save ConsultRoom : {}", consultRoomDTO);
        ConsultRoom consultRoom = consultRoomMapper.toEntity(consultRoomDTO);
        consultRoom = consultRoomRepository.save(consultRoom);
        return consultRoomMapper.toDto(consultRoom);
    }

    /**
     *  Get all the consultRooms.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ConsultRoomDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConsultRooms");
        return consultRoomRepository.findAll(pageable)
            .map(consultRoomMapper::toDto);
    }

    /**
     *  Get one consultRoom by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ConsultRoomDTO findOne(Long id) {
        log.debug("Request to get ConsultRoom : {}", id);
        ConsultRoom consultRoom = consultRoomRepository.findOne(id);
        return consultRoomMapper.toDto(consultRoom);
    }

    /**
     *  Delete the  consultRoom by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ConsultRoom : {}", id);
        consultRoomRepository.delete(id);
    }
}
