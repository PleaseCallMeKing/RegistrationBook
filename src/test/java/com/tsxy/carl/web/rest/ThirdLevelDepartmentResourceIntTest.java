package com.tsxy.carl.web.rest;

import com.tsxy.carl.RegistrationBookApp;

import com.tsxy.carl.domain.ThirdLevelDepartment;
import com.tsxy.carl.repository.ThirdLevelDepartmentRepository;
import com.tsxy.carl.service.ThirdLevelDepartmentService;
import com.tsxy.carl.service.dto.ThirdLevelDepartmentDTO;
import com.tsxy.carl.service.mapper.ThirdLevelDepartmentMapper;
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
 * Test class for the ThirdLevelDepartmentResource REST controller.
 *
 * @see ThirdLevelDepartmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RegistrationBookApp.class)
public class ThirdLevelDepartmentResourceIntTest {

    private static final String DEFAULT_DEPT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEPT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEPT_ENGLISH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEPT_ENGLISH_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_APPOINTMENTABLE = false;
    private static final Boolean UPDATED_APPOINTMENTABLE = true;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ThirdLevelDepartmentRepository thirdLevelDepartmentRepository;

    @Autowired
    private ThirdLevelDepartmentMapper thirdLevelDepartmentMapper;

    @Autowired
    private ThirdLevelDepartmentService thirdLevelDepartmentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restThirdLevelDepartmentMockMvc;

    private ThirdLevelDepartment thirdLevelDepartment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ThirdLevelDepartmentResource thirdLevelDepartmentResource = new ThirdLevelDepartmentResource(thirdLevelDepartmentService);
        this.restThirdLevelDepartmentMockMvc = MockMvcBuilders.standaloneSetup(thirdLevelDepartmentResource)
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
    public static ThirdLevelDepartment createEntity(EntityManager em) {
        ThirdLevelDepartment thirdLevelDepartment = new ThirdLevelDepartment()
            .deptName(DEFAULT_DEPT_NAME)
            .deptEnglishName(DEFAULT_DEPT_ENGLISH_NAME)
            .appointmentable(DEFAULT_APPOINTMENTABLE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return thirdLevelDepartment;
    }

    @Before
    public void initTest() {
        thirdLevelDepartment = createEntity(em);
    }

    @Test
    @Transactional
    public void createThirdLevelDepartment() throws Exception {
        int databaseSizeBeforeCreate = thirdLevelDepartmentRepository.findAll().size();

        // Create the ThirdLevelDepartment
        ThirdLevelDepartmentDTO thirdLevelDepartmentDTO = thirdLevelDepartmentMapper.toDto(thirdLevelDepartment);
        restThirdLevelDepartmentMockMvc.perform(post("/api/third-level-departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdLevelDepartmentDTO)))
            .andExpect(status().isCreated());

        // Validate the ThirdLevelDepartment in the database
        List<ThirdLevelDepartment> thirdLevelDepartmentList = thirdLevelDepartmentRepository.findAll();
        assertThat(thirdLevelDepartmentList).hasSize(databaseSizeBeforeCreate + 1);
        ThirdLevelDepartment testThirdLevelDepartment = thirdLevelDepartmentList.get(thirdLevelDepartmentList.size() - 1);
        assertThat(testThirdLevelDepartment.getDeptName()).isEqualTo(DEFAULT_DEPT_NAME);
        assertThat(testThirdLevelDepartment.getDeptEnglishName()).isEqualTo(DEFAULT_DEPT_ENGLISH_NAME);
        assertThat(testThirdLevelDepartment.isAppointmentable()).isEqualTo(DEFAULT_APPOINTMENTABLE);
//        assertThat(testThirdLevelDepartment.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
//        assertThat(testThirdLevelDepartment.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
//        assertThat(testThirdLevelDepartment.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
//        assertThat(testThirdLevelDepartment.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createThirdLevelDepartmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = thirdLevelDepartmentRepository.findAll().size();

        // Create the ThirdLevelDepartment with an existing ID
        thirdLevelDepartment.setId(1L);
        ThirdLevelDepartmentDTO thirdLevelDepartmentDTO = thirdLevelDepartmentMapper.toDto(thirdLevelDepartment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThirdLevelDepartmentMockMvc.perform(post("/api/third-level-departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdLevelDepartmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ThirdLevelDepartment in the database
        List<ThirdLevelDepartment> thirdLevelDepartmentList = thirdLevelDepartmentRepository.findAll();
        assertThat(thirdLevelDepartmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeptNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = thirdLevelDepartmentRepository.findAll().size();
        // set the field null
        thirdLevelDepartment.setDeptName(null);

        // Create the ThirdLevelDepartment, which fails.
        ThirdLevelDepartmentDTO thirdLevelDepartmentDTO = thirdLevelDepartmentMapper.toDto(thirdLevelDepartment);

        restThirdLevelDepartmentMockMvc.perform(post("/api/third-level-departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdLevelDepartmentDTO)))
            .andExpect(status().isBadRequest());

        List<ThirdLevelDepartment> thirdLevelDepartmentList = thirdLevelDepartmentRepository.findAll();
        assertThat(thirdLevelDepartmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThirdLevelDepartments() throws Exception {
        // Initialize the database
        thirdLevelDepartmentRepository.saveAndFlush(thirdLevelDepartment);

        // Get all the thirdLevelDepartmentList
        restThirdLevelDepartmentMockMvc.perform(get("/api/third-level-departments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thirdLevelDepartment.getId().intValue())))
            .andExpect(jsonPath("$.[*].deptName").value(hasItem(DEFAULT_DEPT_NAME.toString())))
            .andExpect(jsonPath("$.[*].deptEnglishName").value(hasItem(DEFAULT_DEPT_ENGLISH_NAME.toString())))
            .andExpect(jsonPath("$.[*].appointmentable").value(hasItem(DEFAULT_APPOINTMENTABLE.booleanValue())));
//            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
//            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
//            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
//            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getThirdLevelDepartment() throws Exception {
        // Initialize the database
        thirdLevelDepartmentRepository.saveAndFlush(thirdLevelDepartment);

        // Get the thirdLevelDepartment
        restThirdLevelDepartmentMockMvc.perform(get("/api/third-level-departments/{id}", thirdLevelDepartment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(thirdLevelDepartment.getId().intValue()))
            .andExpect(jsonPath("$.deptName").value(DEFAULT_DEPT_NAME.toString()))
            .andExpect(jsonPath("$.deptEnglishName").value(DEFAULT_DEPT_ENGLISH_NAME.toString()))
            .andExpect(jsonPath("$.appointmentable").value(DEFAULT_APPOINTMENTABLE.booleanValue()));
//            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
//            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
//            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
//            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingThirdLevelDepartment() throws Exception {
        // Get the thirdLevelDepartment
        restThirdLevelDepartmentMockMvc.perform(get("/api/third-level-departments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThirdLevelDepartment() throws Exception {
        // Initialize the database
        thirdLevelDepartmentRepository.saveAndFlush(thirdLevelDepartment);
        int databaseSizeBeforeUpdate = thirdLevelDepartmentRepository.findAll().size();

        // Update the thirdLevelDepartment
        ThirdLevelDepartment updatedThirdLevelDepartment = thirdLevelDepartmentRepository.findOne(thirdLevelDepartment.getId());
        updatedThirdLevelDepartment
            .deptName(UPDATED_DEPT_NAME)
            .deptEnglishName(UPDATED_DEPT_ENGLISH_NAME)
            .appointmentable(UPDATED_APPOINTMENTABLE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ThirdLevelDepartmentDTO thirdLevelDepartmentDTO = thirdLevelDepartmentMapper.toDto(updatedThirdLevelDepartment);

        restThirdLevelDepartmentMockMvc.perform(put("/api/third-level-departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdLevelDepartmentDTO)))
            .andExpect(status().isOk());

        // Validate the ThirdLevelDepartment in the database
        List<ThirdLevelDepartment> thirdLevelDepartmentList = thirdLevelDepartmentRepository.findAll();
        assertThat(thirdLevelDepartmentList).hasSize(databaseSizeBeforeUpdate);
        ThirdLevelDepartment testThirdLevelDepartment = thirdLevelDepartmentList.get(thirdLevelDepartmentList.size() - 1);
        assertThat(testThirdLevelDepartment.getDeptName()).isEqualTo(UPDATED_DEPT_NAME);
        assertThat(testThirdLevelDepartment.getDeptEnglishName()).isEqualTo(UPDATED_DEPT_ENGLISH_NAME);
        assertThat(testThirdLevelDepartment.isAppointmentable()).isEqualTo(UPDATED_APPOINTMENTABLE);
//        assertThat(testThirdLevelDepartment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
//        assertThat(testThirdLevelDepartment.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
//        assertThat(testThirdLevelDepartment.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
//        assertThat(testThirdLevelDepartment.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingThirdLevelDepartment() throws Exception {
        int databaseSizeBeforeUpdate = thirdLevelDepartmentRepository.findAll().size();

        // Create the ThirdLevelDepartment
        ThirdLevelDepartmentDTO thirdLevelDepartmentDTO = thirdLevelDepartmentMapper.toDto(thirdLevelDepartment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restThirdLevelDepartmentMockMvc.perform(put("/api/third-level-departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdLevelDepartmentDTO)))
            .andExpect(status().isCreated());

        // Validate the ThirdLevelDepartment in the database
        List<ThirdLevelDepartment> thirdLevelDepartmentList = thirdLevelDepartmentRepository.findAll();
        assertThat(thirdLevelDepartmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteThirdLevelDepartment() throws Exception {
        // Initialize the database
        thirdLevelDepartmentRepository.saveAndFlush(thirdLevelDepartment);
        int databaseSizeBeforeDelete = thirdLevelDepartmentRepository.findAll().size();

        // Get the thirdLevelDepartment
        restThirdLevelDepartmentMockMvc.perform(delete("/api/third-level-departments/{id}", thirdLevelDepartment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ThirdLevelDepartment> thirdLevelDepartmentList = thirdLevelDepartmentRepository.findAll();
        assertThat(thirdLevelDepartmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThirdLevelDepartment.class);
        ThirdLevelDepartment thirdLevelDepartment1 = new ThirdLevelDepartment();
        thirdLevelDepartment1.setId(1L);
        ThirdLevelDepartment thirdLevelDepartment2 = new ThirdLevelDepartment();
        thirdLevelDepartment2.setId(thirdLevelDepartment1.getId());
        assertThat(thirdLevelDepartment1).isEqualTo(thirdLevelDepartment2);
        thirdLevelDepartment2.setId(2L);
        assertThat(thirdLevelDepartment1).isNotEqualTo(thirdLevelDepartment2);
        thirdLevelDepartment1.setId(null);
        assertThat(thirdLevelDepartment1).isNotEqualTo(thirdLevelDepartment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThirdLevelDepartmentDTO.class);
        ThirdLevelDepartmentDTO thirdLevelDepartmentDTO1 = new ThirdLevelDepartmentDTO();
        thirdLevelDepartmentDTO1.setId(1L);
        ThirdLevelDepartmentDTO thirdLevelDepartmentDTO2 = new ThirdLevelDepartmentDTO();
        assertThat(thirdLevelDepartmentDTO1).isNotEqualTo(thirdLevelDepartmentDTO2);
        thirdLevelDepartmentDTO2.setId(thirdLevelDepartmentDTO1.getId());
        assertThat(thirdLevelDepartmentDTO1).isEqualTo(thirdLevelDepartmentDTO2);
        thirdLevelDepartmentDTO2.setId(2L);
        assertThat(thirdLevelDepartmentDTO1).isNotEqualTo(thirdLevelDepartmentDTO2);
        thirdLevelDepartmentDTO1.setId(null);
        assertThat(thirdLevelDepartmentDTO1).isNotEqualTo(thirdLevelDepartmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(thirdLevelDepartmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(thirdLevelDepartmentMapper.fromId(null)).isNull();
    }
}
