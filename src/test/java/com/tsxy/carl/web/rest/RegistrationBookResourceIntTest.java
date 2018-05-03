package com.tsxy.carl.web.rest;

import com.tsxy.carl.RegistrationBookApp;

import com.tsxy.carl.domain.RegistrationBook;
import com.tsxy.carl.repository.RegistrationBookRepository;
import com.tsxy.carl.service.RegistrationBookService;
import com.tsxy.carl.service.dto.RegistrationBookDTO;
import com.tsxy.carl.service.mapper.RegistrationBookMapper;
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
 * Test class for the RegistrationBookResource REST controller.
 *
 * @see RegistrationBookResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RegistrationBookApp.class)
public class RegistrationBookResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_MOBILE_PHONE = 1L;
    private static final Long UPDATED_MOBILE_PHONE = 2L;

    private static final String DEFAULT_ID_CARD = "AAAAAAAAAA";
    private static final String UPDATED_ID_CARD = "BBBBBBBBBB";

    private static final Long DEFAULT_DEPT_ID = 1L;
    private static final Long UPDATED_DEPT_ID = 2L;

    private static final String DEFAULT_DEPT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEPT_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_DOCTOR_ID = 1L;
    private static final Long UPDATED_DOCTOR_ID = 2L;

    private static final String DEFAULT_DOCTOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOCTOR_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_CONSULT_ID = 1L;
    private static final Long UPDATED_CONSULT_ID = 2L;

    private static final String DEFAULT_CONSULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONSULT_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CONSULT_NO = 1;
    private static final Integer UPDATED_CONSULT_NO = 2;

    private static final ZonedDateTime DEFAULT_VISIT_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_VISIT_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private RegistrationBookRepository registrationBookRepository;

    @Autowired
    private RegistrationBookMapper registrationBookMapper;

    @Autowired
    private RegistrationBookService registrationBookService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRegistrationBookMockMvc;

    private RegistrationBook registrationBook;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegistrationBookResource registrationBookResource = new RegistrationBookResource(registrationBookService);
        this.restRegistrationBookMockMvc = MockMvcBuilders.standaloneSetup(registrationBookResource)
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
    public static RegistrationBook createEntity(EntityManager em) {
        RegistrationBook registrationBook = new RegistrationBook()
            .userId(DEFAULT_USER_ID)
            .userName(DEFAULT_USER_NAME)
            .mobilePhone(DEFAULT_MOBILE_PHONE)
            .idCard(DEFAULT_ID_CARD)
            .deptId(DEFAULT_DEPT_ID)
            .deptName(DEFAULT_DEPT_NAME)
            .doctorId(DEFAULT_DOCTOR_ID)
            .doctorName(DEFAULT_DOCTOR_NAME)
            .consultId(DEFAULT_CONSULT_ID)
            .consultName(DEFAULT_CONSULT_NAME)
            .consultNo(DEFAULT_CONSULT_NO)
            .visitDateTime(DEFAULT_VISIT_DATE_TIME)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return registrationBook;
    }

    @Before
    public void initTest() {
        registrationBook = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegistrationBook() throws Exception {
        int databaseSizeBeforeCreate = registrationBookRepository.findAll().size();

        // Create the RegistrationBook
        RegistrationBookDTO registrationBookDTO = registrationBookMapper.toDto(registrationBook);
        restRegistrationBookMockMvc.perform(post("/api/registration-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registrationBookDTO)))
            .andExpect(status().isCreated());

        // Validate the RegistrationBook in the database
        List<RegistrationBook> registrationBookList = registrationBookRepository.findAll();
        assertThat(registrationBookList).hasSize(databaseSizeBeforeCreate + 1);
        RegistrationBook testRegistrationBook = registrationBookList.get(registrationBookList.size() - 1);
        assertThat(testRegistrationBook.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testRegistrationBook.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testRegistrationBook.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testRegistrationBook.getIdCard()).isEqualTo(DEFAULT_ID_CARD);
        assertThat(testRegistrationBook.getDeptId()).isEqualTo(DEFAULT_DEPT_ID);
        assertThat(testRegistrationBook.getDeptName()).isEqualTo(DEFAULT_DEPT_NAME);
        assertThat(testRegistrationBook.getDoctorId()).isEqualTo(DEFAULT_DOCTOR_ID);
        assertThat(testRegistrationBook.getDoctorName()).isEqualTo(DEFAULT_DOCTOR_NAME);
        assertThat(testRegistrationBook.getConsultId()).isEqualTo(DEFAULT_CONSULT_ID);
        assertThat(testRegistrationBook.getConsultName()).isEqualTo(DEFAULT_CONSULT_NAME);
        assertThat(testRegistrationBook.getConsultNo()).isEqualTo(DEFAULT_CONSULT_NO);
        assertThat(testRegistrationBook.getVisitDateTime()).isEqualTo(DEFAULT_VISIT_DATE_TIME);
//        assertThat(testRegistrationBook.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
//        assertThat(testRegistrationBook.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
//        assertThat(testRegistrationBook.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
//        assertThat(testRegistrationBook.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createRegistrationBookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = registrationBookRepository.findAll().size();

        // Create the RegistrationBook with an existing ID
        registrationBook.setId(1L);
        RegistrationBookDTO registrationBookDTO = registrationBookMapper.toDto(registrationBook);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegistrationBookMockMvc.perform(post("/api/registration-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registrationBookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegistrationBook in the database
        List<RegistrationBook> registrationBookList = registrationBookRepository.findAll();
        assertThat(registrationBookList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRegistrationBooks() throws Exception {
        // Initialize the database
        registrationBookRepository.saveAndFlush(registrationBook);

        // Get all the registrationBookList
        restRegistrationBookMockMvc.perform(get("/api/registration-books?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registrationBook.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].idCard").value(hasItem(DEFAULT_ID_CARD.toString())))
            .andExpect(jsonPath("$.[*].deptId").value(hasItem(DEFAULT_DEPT_ID.intValue())))
            .andExpect(jsonPath("$.[*].deptName").value(hasItem(DEFAULT_DEPT_NAME.toString())))
            .andExpect(jsonPath("$.[*].doctorId").value(hasItem(DEFAULT_DOCTOR_ID.intValue())))
            .andExpect(jsonPath("$.[*].doctorName").value(hasItem(DEFAULT_DOCTOR_NAME.toString())))
            .andExpect(jsonPath("$.[*].consultId").value(hasItem(DEFAULT_CONSULT_ID.intValue())))
            .andExpect(jsonPath("$.[*].consultName").value(hasItem(DEFAULT_CONSULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].consultNo").value(hasItem(DEFAULT_CONSULT_NO)))
            .andExpect(jsonPath("$.[*].visitDateTime").value(hasItem(sameInstant(DEFAULT_VISIT_DATE_TIME))));
//            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
//            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
//            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
//            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getRegistrationBook() throws Exception {
        // Initialize the database
        registrationBookRepository.saveAndFlush(registrationBook);

        // Get the registrationBook
        restRegistrationBookMockMvc.perform(get("/api/registration-books/{id}", registrationBook.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(registrationBook.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE.intValue()))
            .andExpect(jsonPath("$.idCard").value(DEFAULT_ID_CARD.toString()))
            .andExpect(jsonPath("$.deptId").value(DEFAULT_DEPT_ID.intValue()))
            .andExpect(jsonPath("$.deptName").value(DEFAULT_DEPT_NAME.toString()))
            .andExpect(jsonPath("$.doctorId").value(DEFAULT_DOCTOR_ID.intValue()))
            .andExpect(jsonPath("$.doctorName").value(DEFAULT_DOCTOR_NAME.toString()))
            .andExpect(jsonPath("$.consultId").value(DEFAULT_CONSULT_ID.intValue()))
            .andExpect(jsonPath("$.consultName").value(DEFAULT_CONSULT_NAME.toString()))
            .andExpect(jsonPath("$.consultNo").value(DEFAULT_CONSULT_NO))
            .andExpect(jsonPath("$.visitDateTime").value(sameInstant(DEFAULT_VISIT_DATE_TIME)));
//            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
//            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
//            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
//            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRegistrationBook() throws Exception {
        // Get the registrationBook
        restRegistrationBookMockMvc.perform(get("/api/registration-books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegistrationBook() throws Exception {
        // Initialize the database
        registrationBookRepository.saveAndFlush(registrationBook);
        int databaseSizeBeforeUpdate = registrationBookRepository.findAll().size();

        // Update the registrationBook
        RegistrationBook updatedRegistrationBook = registrationBookRepository.findOne(registrationBook.getId());
        updatedRegistrationBook
            .userId(UPDATED_USER_ID)
            .userName(UPDATED_USER_NAME)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .idCard(UPDATED_ID_CARD)
            .deptId(UPDATED_DEPT_ID)
            .deptName(UPDATED_DEPT_NAME)
            .doctorId(UPDATED_DOCTOR_ID)
            .doctorName(UPDATED_DOCTOR_NAME)
            .consultId(UPDATED_CONSULT_ID)
            .consultName(UPDATED_CONSULT_NAME)
            .consultNo(UPDATED_CONSULT_NO)
            .visitDateTime(UPDATED_VISIT_DATE_TIME)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        RegistrationBookDTO registrationBookDTO = registrationBookMapper.toDto(updatedRegistrationBook);

        restRegistrationBookMockMvc.perform(put("/api/registration-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registrationBookDTO)))
            .andExpect(status().isOk());

        // Validate the RegistrationBook in the database
        List<RegistrationBook> registrationBookList = registrationBookRepository.findAll();
        assertThat(registrationBookList).hasSize(databaseSizeBeforeUpdate);
        RegistrationBook testRegistrationBook = registrationBookList.get(registrationBookList.size() - 1);
        assertThat(testRegistrationBook.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testRegistrationBook.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testRegistrationBook.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testRegistrationBook.getIdCard()).isEqualTo(UPDATED_ID_CARD);
        assertThat(testRegistrationBook.getDeptId()).isEqualTo(UPDATED_DEPT_ID);
        assertThat(testRegistrationBook.getDeptName()).isEqualTo(UPDATED_DEPT_NAME);
        assertThat(testRegistrationBook.getDoctorId()).isEqualTo(UPDATED_DOCTOR_ID);
        assertThat(testRegistrationBook.getDoctorName()).isEqualTo(UPDATED_DOCTOR_NAME);
        assertThat(testRegistrationBook.getConsultId()).isEqualTo(UPDATED_CONSULT_ID);
        assertThat(testRegistrationBook.getConsultName()).isEqualTo(UPDATED_CONSULT_NAME);
        assertThat(testRegistrationBook.getConsultNo()).isEqualTo(UPDATED_CONSULT_NO);
        assertThat(testRegistrationBook.getVisitDateTime()).isEqualTo(UPDATED_VISIT_DATE_TIME);
//        assertThat(testRegistrationBook.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
//        assertThat(testRegistrationBook.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
//        assertThat(testRegistrationBook.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
//        assertThat(testRegistrationBook.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingRegistrationBook() throws Exception {
        int databaseSizeBeforeUpdate = registrationBookRepository.findAll().size();

        // Create the RegistrationBook
        RegistrationBookDTO registrationBookDTO = registrationBookMapper.toDto(registrationBook);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRegistrationBookMockMvc.perform(put("/api/registration-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registrationBookDTO)))
            .andExpect(status().isCreated());

        // Validate the RegistrationBook in the database
        List<RegistrationBook> registrationBookList = registrationBookRepository.findAll();
        assertThat(registrationBookList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRegistrationBook() throws Exception {
        // Initialize the database
        registrationBookRepository.saveAndFlush(registrationBook);
        int databaseSizeBeforeDelete = registrationBookRepository.findAll().size();

        // Get the registrationBook
        restRegistrationBookMockMvc.perform(delete("/api/registration-books/{id}", registrationBook.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RegistrationBook> registrationBookList = registrationBookRepository.findAll();
        assertThat(registrationBookList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegistrationBook.class);
        RegistrationBook registrationBook1 = new RegistrationBook();
        registrationBook1.setId(1L);
        RegistrationBook registrationBook2 = new RegistrationBook();
        registrationBook2.setId(registrationBook1.getId());
        assertThat(registrationBook1).isEqualTo(registrationBook2);
        registrationBook2.setId(2L);
        assertThat(registrationBook1).isNotEqualTo(registrationBook2);
        registrationBook1.setId(null);
        assertThat(registrationBook1).isNotEqualTo(registrationBook2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegistrationBookDTO.class);
        RegistrationBookDTO registrationBookDTO1 = new RegistrationBookDTO();
        registrationBookDTO1.setId(1L);
        RegistrationBookDTO registrationBookDTO2 = new RegistrationBookDTO();
        assertThat(registrationBookDTO1).isNotEqualTo(registrationBookDTO2);
        registrationBookDTO2.setId(registrationBookDTO1.getId());
        assertThat(registrationBookDTO1).isEqualTo(registrationBookDTO2);
        registrationBookDTO2.setId(2L);
        assertThat(registrationBookDTO1).isNotEqualTo(registrationBookDTO2);
        registrationBookDTO1.setId(null);
        assertThat(registrationBookDTO1).isNotEqualTo(registrationBookDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(registrationBookMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(registrationBookMapper.fromId(null)).isNull();
    }
}
