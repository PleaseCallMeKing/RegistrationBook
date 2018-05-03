package com.tsxy.carl.web.rest;

import com.tsxy.carl.RegistrationBookApp;

import com.tsxy.carl.domain.DoctorVisit;
import com.tsxy.carl.repository.DoctorVisitRepository;
import com.tsxy.carl.service.DoctorVisitService;
import com.tsxy.carl.service.dto.DoctorVisitDTO;
import com.tsxy.carl.service.mapper.DoctorVisitMapper;
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
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.tsxy.carl.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DoctorVisitResource REST controller.
 *
 * @see DoctorVisitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RegistrationBookApp.class)
public class DoctorVisitResourceIntTest {

    private static final ZonedDateTime DEFAULT_VISIT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_VISIT_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_VISIT_END_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_VISIT_END_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DoctorVisitRepository doctorVisitRepository;

    @Autowired
    private DoctorVisitMapper doctorVisitMapper;

    @Autowired
    private DoctorVisitService doctorVisitService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDoctorVisitMockMvc;

    private DoctorVisit doctorVisit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DoctorVisitResource doctorVisitResource = new DoctorVisitResource(doctorVisitService);
        this.restDoctorVisitMockMvc = MockMvcBuilders.standaloneSetup(doctorVisitResource)
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
    public static DoctorVisit createEntity(EntityManager em) {
        DoctorVisit doctorVisit = new DoctorVisit()
            .visitData(DEFAULT_VISIT_DATA)
            .visitEndData(DEFAULT_VISIT_END_DATA)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return doctorVisit;
    }

    @Before
    public void initTest() {
        doctorVisit = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoctorVisit() throws Exception {
        int databaseSizeBeforeCreate = doctorVisitRepository.findAll().size();

        // Create the DoctorVisit
        DoctorVisitDTO doctorVisitDTO = doctorVisitMapper.toDto(doctorVisit);
        restDoctorVisitMockMvc.perform(post("/api/doctor-visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorVisitDTO)))
            .andExpect(status().isCreated());

        // Validate the DoctorVisit in the database
        List<DoctorVisit> doctorVisitList = doctorVisitRepository.findAll();
        assertThat(doctorVisitList).hasSize(databaseSizeBeforeCreate + 1);
        DoctorVisit testDoctorVisit = doctorVisitList.get(doctorVisitList.size() - 1);
        assertThat(testDoctorVisit.getVisitData()).isEqualTo(DEFAULT_VISIT_DATA);
        assertThat(testDoctorVisit.getVisitEndData()).isEqualTo(DEFAULT_VISIT_END_DATA);
//        assertThat(testDoctorVisit.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
//        assertThat(testDoctorVisit.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
//        assertThat(testDoctorVisit.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
//        assertThat(testDoctorVisit.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createDoctorVisitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doctorVisitRepository.findAll().size();

        // Create the DoctorVisit with an existing ID
        doctorVisit.setId(1L);
        DoctorVisitDTO doctorVisitDTO = doctorVisitMapper.toDto(doctorVisit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorVisitMockMvc.perform(post("/api/doctor-visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorVisitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DoctorVisit in the database
        List<DoctorVisit> doctorVisitList = doctorVisitRepository.findAll();
        assertThat(doctorVisitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDoctorVisits() throws Exception {
        // Initialize the database
        doctorVisitRepository.saveAndFlush(doctorVisit);

        // Get all the doctorVisitList
        restDoctorVisitMockMvc.perform(get("/api/doctor-visits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctorVisit.getId().intValue())))
            .andExpect(jsonPath("$.[*].visitData").value(hasItem(sameInstant(DEFAULT_VISIT_DATA))))
            .andExpect(jsonPath("$.[*].visitEndData").value(hasItem(sameInstant(DEFAULT_VISIT_END_DATA))));
//            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
//            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
//            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
//            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getDoctorVisit() throws Exception {
        // Initialize the database
        doctorVisitRepository.saveAndFlush(doctorVisit);

        // Get the doctorVisit
        restDoctorVisitMockMvc.perform(get("/api/doctor-visits/{id}", doctorVisit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doctorVisit.getId().intValue()))
            .andExpect(jsonPath("$.visitData").value(sameInstant(DEFAULT_VISIT_DATA)))
            .andExpect(jsonPath("$.visitEndData").value(sameInstant(DEFAULT_VISIT_END_DATA)));
//            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
//            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
//            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
//            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDoctorVisit() throws Exception {
        // Get the doctorVisit
        restDoctorVisitMockMvc.perform(get("/api/doctor-visits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoctorVisit() throws Exception {
        // Initialize the database
        doctorVisitRepository.saveAndFlush(doctorVisit);
        int databaseSizeBeforeUpdate = doctorVisitRepository.findAll().size();

        // Update the doctorVisit
        DoctorVisit updatedDoctorVisit = doctorVisitRepository.findOne(doctorVisit.getId());
        updatedDoctorVisit
            .visitData(UPDATED_VISIT_DATA)
            .visitEndData(UPDATED_VISIT_END_DATA)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        DoctorVisitDTO doctorVisitDTO = doctorVisitMapper.toDto(updatedDoctorVisit);

        restDoctorVisitMockMvc.perform(put("/api/doctor-visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorVisitDTO)))
            .andExpect(status().isOk());

        // Validate the DoctorVisit in the database
        List<DoctorVisit> doctorVisitList = doctorVisitRepository.findAll();
        assertThat(doctorVisitList).hasSize(databaseSizeBeforeUpdate);
        DoctorVisit testDoctorVisit = doctorVisitList.get(doctorVisitList.size() - 1);
        assertThat(testDoctorVisit.getVisitData()).isEqualTo(UPDATED_VISIT_DATA);
        assertThat(testDoctorVisit.getVisitEndData()).isEqualTo(UPDATED_VISIT_END_DATA);
//        assertThat(testDoctorVisit.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
//        assertThat(testDoctorVisit.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
//        assertThat(testDoctorVisit.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
//        assertThat(testDoctorVisit.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDoctorVisit() throws Exception {
        int databaseSizeBeforeUpdate = doctorVisitRepository.findAll().size();

        // Create the DoctorVisit
        DoctorVisitDTO doctorVisitDTO = doctorVisitMapper.toDto(doctorVisit);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDoctorVisitMockMvc.perform(put("/api/doctor-visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctorVisitDTO)))
            .andExpect(status().isCreated());

        // Validate the DoctorVisit in the database
        List<DoctorVisit> doctorVisitList = doctorVisitRepository.findAll();
        assertThat(doctorVisitList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDoctorVisit() throws Exception {
        // Initialize the database
        doctorVisitRepository.saveAndFlush(doctorVisit);
        int databaseSizeBeforeDelete = doctorVisitRepository.findAll().size();

        // Get the doctorVisit
        restDoctorVisitMockMvc.perform(delete("/api/doctor-visits/{id}", doctorVisit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DoctorVisit> doctorVisitList = doctorVisitRepository.findAll();
        assertThat(doctorVisitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoctorVisit.class);
        DoctorVisit doctorVisit1 = new DoctorVisit();
        doctorVisit1.setId(1L);
        DoctorVisit doctorVisit2 = new DoctorVisit();
        doctorVisit2.setId(doctorVisit1.getId());
        assertThat(doctorVisit1).isEqualTo(doctorVisit2);
        doctorVisit2.setId(2L);
        assertThat(doctorVisit1).isNotEqualTo(doctorVisit2);
        doctorVisit1.setId(null);
        assertThat(doctorVisit1).isNotEqualTo(doctorVisit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoctorVisitDTO.class);
        DoctorVisitDTO doctorVisitDTO1 = new DoctorVisitDTO();
        doctorVisitDTO1.setId(1L);
        DoctorVisitDTO doctorVisitDTO2 = new DoctorVisitDTO();
        assertThat(doctorVisitDTO1).isNotEqualTo(doctorVisitDTO2);
        doctorVisitDTO2.setId(doctorVisitDTO1.getId());
        assertThat(doctorVisitDTO1).isEqualTo(doctorVisitDTO2);
        doctorVisitDTO2.setId(2L);
        assertThat(doctorVisitDTO1).isNotEqualTo(doctorVisitDTO2);
        doctorVisitDTO1.setId(null);
        assertThat(doctorVisitDTO1).isNotEqualTo(doctorVisitDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(doctorVisitMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(doctorVisitMapper.fromId(null)).isNull();
    }
}
