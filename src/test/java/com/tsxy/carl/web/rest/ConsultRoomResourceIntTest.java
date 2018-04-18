package com.tsxy.carl.web.rest;

import com.tsxy.carl.RegistrationBookApp;

import com.tsxy.carl.domain.ConsultRoom;
import com.tsxy.carl.repository.ConsultRoomRepository;
import com.tsxy.carl.service.ConsultRoomService;
import com.tsxy.carl.service.dto.ConsultRoomDTO;
import com.tsxy.carl.service.mapper.ConsultRoomMapper;
import com.tsxy.carl.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ConsultRoomResource REST controller.
 *
 * @see ConsultRoomResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RegistrationBookApp.class)
public class ConsultRoomResourceIntTest {

    private static final String DEFAULT_CONSULT_ROOM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONSULT_ROOM_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CONSULT_ROOM_NO = 1;
    private static final Integer UPDATED_CONSULT_ROOM_NO = 2;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ConsultRoomRepository consultRoomRepository;

    @Autowired
    private ConsultRoomMapper consultRoomMapper;

    @Autowired
    private ConsultRoomService consultRoomService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConsultRoomMockMvc;

    private ConsultRoom consultRoom;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsultRoomResource consultRoomResource = new ConsultRoomResource(consultRoomService);
        this.restConsultRoomMockMvc = MockMvcBuilders.standaloneSetup(consultRoomResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConsultRoom createEntity(EntityManager em) {
        ConsultRoom consultRoom = new ConsultRoom()
            .consultRoomName(DEFAULT_CONSULT_ROOM_NAME)
            .consultRoomNo(DEFAULT_CONSULT_ROOM_NO)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return consultRoom;
    }

    @Before
    public void initTest() {
        consultRoom = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsultRoom() throws Exception {
        int databaseSizeBeforeCreate = consultRoomRepository.findAll().size();

        // Create the ConsultRoom
        ConsultRoomDTO consultRoomDTO = consultRoomMapper.toDto(consultRoom);
        restConsultRoomMockMvc.perform(post("/api/consult-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultRoomDTO)))
            .andExpect(status().isCreated());

        // Validate the ConsultRoom in the database
        List<ConsultRoom> consultRoomList = consultRoomRepository.findAll();
        assertThat(consultRoomList).hasSize(databaseSizeBeforeCreate + 1);
        ConsultRoom testConsultRoom = consultRoomList.get(consultRoomList.size() - 1);
        assertThat(testConsultRoom.getConsultRoomName()).isEqualTo(DEFAULT_CONSULT_ROOM_NAME);
        assertThat(testConsultRoom.getConsultRoomNo()).isEqualTo(DEFAULT_CONSULT_ROOM_NO);
        assertThat(testConsultRoom.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testConsultRoom.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testConsultRoom.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testConsultRoom.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createConsultRoomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consultRoomRepository.findAll().size();

        // Create the ConsultRoom with an existing ID
        consultRoom.setId(1L);
        ConsultRoomDTO consultRoomDTO = consultRoomMapper.toDto(consultRoom);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultRoomMockMvc.perform(post("/api/consult-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultRoomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConsultRoom in the database
        List<ConsultRoom> consultRoomList = consultRoomRepository.findAll();
        assertThat(consultRoomList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConsultRooms() throws Exception {
        // Initialize the database
        consultRoomRepository.saveAndFlush(consultRoom);

        // Get all the consultRoomList
        restConsultRoomMockMvc.perform(get("/api/consult-rooms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultRoom.getId().intValue())))
            .andExpect(jsonPath("$.[*].consultRoomName").value(hasItem(DEFAULT_CONSULT_ROOM_NAME.toString())))
            .andExpect(jsonPath("$.[*].consultRoomNo").value(hasItem(DEFAULT_CONSULT_ROOM_NO)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getConsultRoom() throws Exception {
        // Initialize the database
        consultRoomRepository.saveAndFlush(consultRoom);

        // Get the consultRoom
        restConsultRoomMockMvc.perform(get("/api/consult-rooms/{id}", consultRoom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consultRoom.getId().intValue()))
            .andExpect(jsonPath("$.consultRoomName").value(DEFAULT_CONSULT_ROOM_NAME.toString()))
            .andExpect(jsonPath("$.consultRoomNo").value(DEFAULT_CONSULT_ROOM_NO))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConsultRoom() throws Exception {
        // Get the consultRoom
        restConsultRoomMockMvc.perform(get("/api/consult-rooms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsultRoom() throws Exception {
        // Initialize the database
        consultRoomRepository.saveAndFlush(consultRoom);
        int databaseSizeBeforeUpdate = consultRoomRepository.findAll().size();

        // Update the consultRoom
        ConsultRoom updatedConsultRoom = consultRoomRepository.findOne(consultRoom.getId());
        updatedConsultRoom
            .consultRoomName(UPDATED_CONSULT_ROOM_NAME)
            .consultRoomNo(UPDATED_CONSULT_ROOM_NO)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ConsultRoomDTO consultRoomDTO = consultRoomMapper.toDto(updatedConsultRoom);

        restConsultRoomMockMvc.perform(put("/api/consult-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultRoomDTO)))
            .andExpect(status().isOk());

        // Validate the ConsultRoom in the database
        List<ConsultRoom> consultRoomList = consultRoomRepository.findAll();
        assertThat(consultRoomList).hasSize(databaseSizeBeforeUpdate);
        ConsultRoom testConsultRoom = consultRoomList.get(consultRoomList.size() - 1);
        assertThat(testConsultRoom.getConsultRoomName()).isEqualTo(UPDATED_CONSULT_ROOM_NAME);
        assertThat(testConsultRoom.getConsultRoomNo()).isEqualTo(UPDATED_CONSULT_ROOM_NO);
        assertThat(testConsultRoom.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testConsultRoom.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testConsultRoom.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testConsultRoom.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingConsultRoom() throws Exception {
        int databaseSizeBeforeUpdate = consultRoomRepository.findAll().size();

        // Create the ConsultRoom
        ConsultRoomDTO consultRoomDTO = consultRoomMapper.toDto(consultRoom);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConsultRoomMockMvc.perform(put("/api/consult-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultRoomDTO)))
            .andExpect(status().isCreated());

        // Validate the ConsultRoom in the database
        List<ConsultRoom> consultRoomList = consultRoomRepository.findAll();
        assertThat(consultRoomList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConsultRoom() throws Exception {
        // Initialize the database
        consultRoomRepository.saveAndFlush(consultRoom);
        int databaseSizeBeforeDelete = consultRoomRepository.findAll().size();

        // Get the consultRoom
        restConsultRoomMockMvc.perform(delete("/api/consult-rooms/{id}", consultRoom.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ConsultRoom> consultRoomList = consultRoomRepository.findAll();
        assertThat(consultRoomList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsultRoom.class);
        ConsultRoom consultRoom1 = new ConsultRoom();
        consultRoom1.setId(1L);
        ConsultRoom consultRoom2 = new ConsultRoom();
        consultRoom2.setId(consultRoom1.getId());
        assertThat(consultRoom1).isEqualTo(consultRoom2);
        consultRoom2.setId(2L);
        assertThat(consultRoom1).isNotEqualTo(consultRoom2);
        consultRoom1.setId(null);
        assertThat(consultRoom1).isNotEqualTo(consultRoom2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsultRoomDTO.class);
        ConsultRoomDTO consultRoomDTO1 = new ConsultRoomDTO();
        consultRoomDTO1.setId(1L);
        ConsultRoomDTO consultRoomDTO2 = new ConsultRoomDTO();
        assertThat(consultRoomDTO1).isNotEqualTo(consultRoomDTO2);
        consultRoomDTO2.setId(consultRoomDTO1.getId());
        assertThat(consultRoomDTO1).isEqualTo(consultRoomDTO2);
        consultRoomDTO2.setId(2L);
        assertThat(consultRoomDTO1).isNotEqualTo(consultRoomDTO2);
        consultRoomDTO1.setId(null);
        assertThat(consultRoomDTO1).isNotEqualTo(consultRoomDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(consultRoomMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(consultRoomMapper.fromId(null)).isNull();
    }
}
