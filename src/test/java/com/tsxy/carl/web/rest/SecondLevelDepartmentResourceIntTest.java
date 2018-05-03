package com.tsxy.carl.web.rest;

import com.tsxy.carl.RegistrationBookApp;

import com.tsxy.carl.domain.SecondLevelDepartment;
import com.tsxy.carl.repository.SecondLevelDepartmentRepository;
import com.tsxy.carl.service.SecondLevelDepartmentService;
import com.tsxy.carl.service.dto.SecondLevelDepartmentDTO;
import com.tsxy.carl.service.mapper.SecondLevelDepartmentMapper;
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
 * Test class for the SecondLevelDepartmentResource REST controller.
 *
 * @see SecondLevelDepartmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RegistrationBookApp.class)
public class SecondLevelDepartmentResourceIntTest {

    private static final String DEFAULT_DEPT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEPT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEPT_ENGLISH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEPT_ENGLISH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SecondLevelDepartmentRepository secondLevelDepartmentRepository;

    @Autowired
    private SecondLevelDepartmentMapper secondLevelDepartmentMapper;

    @Autowired
    private SecondLevelDepartmentService secondLevelDepartmentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSecondLevelDepartmentMockMvc;

    private SecondLevelDepartment secondLevelDepartment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SecondLevelDepartmentResource secondLevelDepartmentResource = new SecondLevelDepartmentResource(secondLevelDepartmentService);
        this.restSecondLevelDepartmentMockMvc = MockMvcBuilders.standaloneSetup(secondLevelDepartmentResource)
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
    public static SecondLevelDepartment createEntity(EntityManager em) {
        SecondLevelDepartment secondLevelDepartment = new SecondLevelDepartment()
            .deptName(DEFAULT_DEPT_NAME)
            .deptEnglishName(DEFAULT_DEPT_ENGLISH_NAME)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return secondLevelDepartment;
    }

    @Before
    public void initTest() {
        secondLevelDepartment = createEntity(em);
    }

    @Test
    @Transactional
    public void createSecondLevelDepartment() throws Exception {
        int databaseSizeBeforeCreate = secondLevelDepartmentRepository.findAll().size();

        // Create the SecondLevelDepartment
        SecondLevelDepartmentDTO secondLevelDepartmentDTO = secondLevelDepartmentMapper.toDto(secondLevelDepartment);
        restSecondLevelDepartmentMockMvc.perform(post("/api/second-level-departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secondLevelDepartmentDTO)))
            .andExpect(status().isCreated());

        // Validate the SecondLevelDepartment in the database
        List<SecondLevelDepartment> secondLevelDepartmentList = secondLevelDepartmentRepository.findAll();
        assertThat(secondLevelDepartmentList).hasSize(databaseSizeBeforeCreate + 1);
        SecondLevelDepartment testSecondLevelDepartment = secondLevelDepartmentList.get(secondLevelDepartmentList.size() - 1);
        assertThat(testSecondLevelDepartment.getDeptName()).isEqualTo(DEFAULT_DEPT_NAME);
        assertThat(testSecondLevelDepartment.getDeptEnglishName()).isEqualTo(DEFAULT_DEPT_ENGLISH_NAME);
//        assertThat(testSecondLevelDepartment.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
//        assertThat(testSecondLevelDepartment.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
//        assertThat(testSecondLevelDepartment.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
//        assertThat(testSecondLevelDepartment.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createSecondLevelDepartmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = secondLevelDepartmentRepository.findAll().size();

        // Create the SecondLevelDepartment with an existing ID
        secondLevelDepartment.setId(1L);
        SecondLevelDepartmentDTO secondLevelDepartmentDTO = secondLevelDepartmentMapper.toDto(secondLevelDepartment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSecondLevelDepartmentMockMvc.perform(post("/api/second-level-departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secondLevelDepartmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SecondLevelDepartment in the database
        List<SecondLevelDepartment> secondLevelDepartmentList = secondLevelDepartmentRepository.findAll();
        assertThat(secondLevelDepartmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSecondLevelDepartments() throws Exception {
        // Initialize the database
        secondLevelDepartmentRepository.saveAndFlush(secondLevelDepartment);

        // Get all the secondLevelDepartmentList
        restSecondLevelDepartmentMockMvc.perform(get("/api/second-level-departments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(secondLevelDepartment.getId().intValue())))
            .andExpect(jsonPath("$.[*].deptName").value(hasItem(DEFAULT_DEPT_NAME.toString())))
            .andExpect(jsonPath("$.[*].deptEnglishName").value(hasItem(DEFAULT_DEPT_ENGLISH_NAME.toString())));
//            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
//            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
//            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
//            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getSecondLevelDepartment() throws Exception {
        // Initialize the database
        secondLevelDepartmentRepository.saveAndFlush(secondLevelDepartment);

        // Get the secondLevelDepartment
        restSecondLevelDepartmentMockMvc.perform(get("/api/second-level-departments/{id}", secondLevelDepartment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(secondLevelDepartment.getId().intValue()))
            .andExpect(jsonPath("$.deptName").value(DEFAULT_DEPT_NAME.toString()))
            .andExpect(jsonPath("$.deptEnglishName").value(DEFAULT_DEPT_ENGLISH_NAME.toString()));
//            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
//            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
//            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
//            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSecondLevelDepartment() throws Exception {
        // Get the secondLevelDepartment
        restSecondLevelDepartmentMockMvc.perform(get("/api/second-level-departments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSecondLevelDepartment() throws Exception {
        // Initialize the database
        secondLevelDepartmentRepository.saveAndFlush(secondLevelDepartment);
        int databaseSizeBeforeUpdate = secondLevelDepartmentRepository.findAll().size();

        // Update the secondLevelDepartment
        SecondLevelDepartment updatedSecondLevelDepartment = secondLevelDepartmentRepository.findOne(secondLevelDepartment.getId());
        updatedSecondLevelDepartment
            .deptName(UPDATED_DEPT_NAME)
            .deptEnglishName(UPDATED_DEPT_ENGLISH_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        SecondLevelDepartmentDTO secondLevelDepartmentDTO = secondLevelDepartmentMapper.toDto(updatedSecondLevelDepartment);

        restSecondLevelDepartmentMockMvc.perform(put("/api/second-level-departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secondLevelDepartmentDTO)))
            .andExpect(status().isOk());

        // Validate the SecondLevelDepartment in the database
        List<SecondLevelDepartment> secondLevelDepartmentList = secondLevelDepartmentRepository.findAll();
        assertThat(secondLevelDepartmentList).hasSize(databaseSizeBeforeUpdate);
        SecondLevelDepartment testSecondLevelDepartment = secondLevelDepartmentList.get(secondLevelDepartmentList.size() - 1);
        assertThat(testSecondLevelDepartment.getDeptName()).isEqualTo(UPDATED_DEPT_NAME);
        assertThat(testSecondLevelDepartment.getDeptEnglishName()).isEqualTo(UPDATED_DEPT_ENGLISH_NAME);
//        assertThat(testSecondLevelDepartment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
//        assertThat(testSecondLevelDepartment.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
//        assertThat(testSecondLevelDepartment.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
//        assertThat(testSecondLevelDepartment.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSecondLevelDepartment() throws Exception {
        int databaseSizeBeforeUpdate = secondLevelDepartmentRepository.findAll().size();

        // Create the SecondLevelDepartment
        SecondLevelDepartmentDTO secondLevelDepartmentDTO = secondLevelDepartmentMapper.toDto(secondLevelDepartment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSecondLevelDepartmentMockMvc.perform(put("/api/second-level-departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secondLevelDepartmentDTO)))
            .andExpect(status().isCreated());

        // Validate the SecondLevelDepartment in the database
        List<SecondLevelDepartment> secondLevelDepartmentList = secondLevelDepartmentRepository.findAll();
        assertThat(secondLevelDepartmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSecondLevelDepartment() throws Exception {
        // Initialize the database
        secondLevelDepartmentRepository.saveAndFlush(secondLevelDepartment);
        int databaseSizeBeforeDelete = secondLevelDepartmentRepository.findAll().size();

        // Get the secondLevelDepartment
        restSecondLevelDepartmentMockMvc.perform(delete("/api/second-level-departments/{id}", secondLevelDepartment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SecondLevelDepartment> secondLevelDepartmentList = secondLevelDepartmentRepository.findAll();
        assertThat(secondLevelDepartmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecondLevelDepartment.class);
        SecondLevelDepartment secondLevelDepartment1 = new SecondLevelDepartment();
        secondLevelDepartment1.setId(1L);
        SecondLevelDepartment secondLevelDepartment2 = new SecondLevelDepartment();
        secondLevelDepartment2.setId(secondLevelDepartment1.getId());
        assertThat(secondLevelDepartment1).isEqualTo(secondLevelDepartment2);
        secondLevelDepartment2.setId(2L);
        assertThat(secondLevelDepartment1).isNotEqualTo(secondLevelDepartment2);
        secondLevelDepartment1.setId(null);
        assertThat(secondLevelDepartment1).isNotEqualTo(secondLevelDepartment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecondLevelDepartmentDTO.class);
        SecondLevelDepartmentDTO secondLevelDepartmentDTO1 = new SecondLevelDepartmentDTO();
        secondLevelDepartmentDTO1.setId(1L);
        SecondLevelDepartmentDTO secondLevelDepartmentDTO2 = new SecondLevelDepartmentDTO();
        assertThat(secondLevelDepartmentDTO1).isNotEqualTo(secondLevelDepartmentDTO2);
        secondLevelDepartmentDTO2.setId(secondLevelDepartmentDTO1.getId());
        assertThat(secondLevelDepartmentDTO1).isEqualTo(secondLevelDepartmentDTO2);
        secondLevelDepartmentDTO2.setId(2L);
        assertThat(secondLevelDepartmentDTO1).isNotEqualTo(secondLevelDepartmentDTO2);
        secondLevelDepartmentDTO1.setId(null);
        assertThat(secondLevelDepartmentDTO1).isNotEqualTo(secondLevelDepartmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(secondLevelDepartmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(secondLevelDepartmentMapper.fromId(null)).isNull();
    }
}
