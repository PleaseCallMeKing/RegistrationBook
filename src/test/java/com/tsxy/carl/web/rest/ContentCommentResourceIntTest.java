package com.tsxy.carl.web.rest;

import com.tsxy.carl.RegistrationBookApp;

import com.tsxy.carl.domain.ContentComment;
import com.tsxy.carl.repository.ContentCommentRepository;
import com.tsxy.carl.service.ContentCommentService;
import com.tsxy.carl.service.dto.ContentCommentDTO;
import com.tsxy.carl.service.mapper.ContentCommentMapper;
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
 * Test class for the ContentCommentResource REST controller.
 *
 * @see ContentCommentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RegistrationBookApp.class)
public class ContentCommentResourceIntTest {

    private static final String DEFAULT_CONTENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_CONTENT_ID = 1L;
    private static final Long UPDATED_CONTENT_ID = 2L;

    private static final String DEFAULT_CONTENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_FLOOR_NUMBER = 1;
    private static final Integer UPDATED_FLOOR_NUMBER = 2;

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ContentCommentRepository contentCommentRepository;

    @Autowired
    private ContentCommentMapper contentCommentMapper;

    @Autowired
    private ContentCommentService contentCommentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContentCommentMockMvc;

    private ContentComment contentComment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContentCommentResource contentCommentResource = new ContentCommentResource(contentCommentService);
        this.restContentCommentMockMvc = MockMvcBuilders.standaloneSetup(contentCommentResource)
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
    public static ContentComment createEntity(EntityManager em) {
        ContentComment contentComment = new ContentComment()
            .contentType(DEFAULT_CONTENT_TYPE)
            .contentId(DEFAULT_CONTENT_ID)
            .contentName(DEFAULT_CONTENT_NAME)
            .floorNumber(DEFAULT_FLOOR_NUMBER)
            .userId(DEFAULT_USER_ID)
            .userName(DEFAULT_USER_NAME)
            .content(DEFAULT_CONTENT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return contentComment;
    }

    @Before
    public void initTest() {
        contentComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createContentComment() throws Exception {
        int databaseSizeBeforeCreate = contentCommentRepository.findAll().size();

        // Create the ContentComment
        ContentCommentDTO contentCommentDTO = contentCommentMapper.toDto(contentComment);
        restContentCommentMockMvc.perform(post("/api/content-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentCommentDTO)))
            .andExpect(status().isCreated());

        // Validate the ContentComment in the database
        List<ContentComment> contentCommentList = contentCommentRepository.findAll();
        assertThat(contentCommentList).hasSize(databaseSizeBeforeCreate + 1);
        ContentComment testContentComment = contentCommentList.get(contentCommentList.size() - 1);
        assertThat(testContentComment.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testContentComment.getContentId()).isEqualTo(DEFAULT_CONTENT_ID);
        assertThat(testContentComment.getContentName()).isEqualTo(DEFAULT_CONTENT_NAME);
        assertThat(testContentComment.getFloorNumber()).isEqualTo(DEFAULT_FLOOR_NUMBER);
        assertThat(testContentComment.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testContentComment.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testContentComment.getContent()).isEqualTo(DEFAULT_CONTENT);
//        assertThat(testContentComment.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
//        assertThat(testContentComment.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
//        assertThat(testContentComment.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
//        assertThat(testContentComment.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createContentCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contentCommentRepository.findAll().size();

        // Create the ContentComment with an existing ID
        contentComment.setId(1L);
        ContentCommentDTO contentCommentDTO = contentCommentMapper.toDto(contentComment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentCommentMockMvc.perform(post("/api/content-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentCommentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContentComment in the database
        List<ContentComment> contentCommentList = contentCommentRepository.findAll();
        assertThat(contentCommentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentCommentRepository.findAll().size();
        // set the field null
        contentComment.setUserId(null);

        // Create the ContentComment, which fails.
        ContentCommentDTO contentCommentDTO = contentCommentMapper.toDto(contentComment);

        restContentCommentMockMvc.perform(post("/api/content-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentCommentDTO)))
            .andExpect(status().isBadRequest());

        List<ContentComment> contentCommentList = contentCommentRepository.findAll();
        assertThat(contentCommentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentCommentRepository.findAll().size();
        // set the field null
        contentComment.setContent(null);

        // Create the ContentComment, which fails.
        ContentCommentDTO contentCommentDTO = contentCommentMapper.toDto(contentComment);

        restContentCommentMockMvc.perform(post("/api/content-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentCommentDTO)))
            .andExpect(status().isBadRequest());

        List<ContentComment> contentCommentList = contentCommentRepository.findAll();
        assertThat(contentCommentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContentComments() throws Exception {
        // Initialize the database
        contentCommentRepository.saveAndFlush(contentComment);

        // Get all the contentCommentList
        restContentCommentMockMvc.perform(get("/api/content-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contentComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].contentId").value(hasItem(DEFAULT_CONTENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].contentName").value(hasItem(DEFAULT_CONTENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].floorNumber").value(hasItem(DEFAULT_FLOOR_NUMBER)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
//            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
//            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
//            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
//            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getContentComment() throws Exception {
        // Initialize the database
        contentCommentRepository.saveAndFlush(contentComment);

        // Get the contentComment
        restContentCommentMockMvc.perform(get("/api/content-comments/{id}", contentComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contentComment.getId().intValue()))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE.toString()))
            .andExpect(jsonPath("$.contentId").value(DEFAULT_CONTENT_ID.intValue()))
            .andExpect(jsonPath("$.contentName").value(DEFAULT_CONTENT_NAME.toString()))
            .andExpect(jsonPath("$.floorNumber").value(DEFAULT_FLOOR_NUMBER))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()));
//            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
//            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
//            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
//            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContentComment() throws Exception {
        // Get the contentComment
        restContentCommentMockMvc.perform(get("/api/content-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContentComment() throws Exception {
        // Initialize the database
        contentCommentRepository.saveAndFlush(contentComment);
        int databaseSizeBeforeUpdate = contentCommentRepository.findAll().size();

        // Update the contentComment
        ContentComment updatedContentComment = contentCommentRepository.findOne(contentComment.getId());
        updatedContentComment
            .contentType(UPDATED_CONTENT_TYPE)
            .contentId(UPDATED_CONTENT_ID)
            .contentName(UPDATED_CONTENT_NAME)
            .floorNumber(UPDATED_FLOOR_NUMBER)
            .userId(UPDATED_USER_ID)
            .userName(UPDATED_USER_NAME)
            .content(UPDATED_CONTENT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ContentCommentDTO contentCommentDTO = contentCommentMapper.toDto(updatedContentComment);

        restContentCommentMockMvc.perform(put("/api/content-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentCommentDTO)))
            .andExpect(status().isOk());

        // Validate the ContentComment in the database
        List<ContentComment> contentCommentList = contentCommentRepository.findAll();
        assertThat(contentCommentList).hasSize(databaseSizeBeforeUpdate);
        ContentComment testContentComment = contentCommentList.get(contentCommentList.size() - 1);
        assertThat(testContentComment.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testContentComment.getContentId()).isEqualTo(UPDATED_CONTENT_ID);
        assertThat(testContentComment.getContentName()).isEqualTo(UPDATED_CONTENT_NAME);
        assertThat(testContentComment.getFloorNumber()).isEqualTo(UPDATED_FLOOR_NUMBER);
        assertThat(testContentComment.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testContentComment.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testContentComment.getContent()).isEqualTo(UPDATED_CONTENT);
//        assertThat(testContentComment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
//        assertThat(testContentComment.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
//        assertThat(testContentComment.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
//        assertThat(testContentComment.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingContentComment() throws Exception {
        int databaseSizeBeforeUpdate = contentCommentRepository.findAll().size();

        // Create the ContentComment
        ContentCommentDTO contentCommentDTO = contentCommentMapper.toDto(contentComment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContentCommentMockMvc.perform(put("/api/content-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentCommentDTO)))
            .andExpect(status().isCreated());

        // Validate the ContentComment in the database
        List<ContentComment> contentCommentList = contentCommentRepository.findAll();
        assertThat(contentCommentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContentComment() throws Exception {
        // Initialize the database
        contentCommentRepository.saveAndFlush(contentComment);
        int databaseSizeBeforeDelete = contentCommentRepository.findAll().size();

        // Get the contentComment
        restContentCommentMockMvc.perform(delete("/api/content-comments/{id}", contentComment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContentComment> contentCommentList = contentCommentRepository.findAll();
        assertThat(contentCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentComment.class);
        ContentComment contentComment1 = new ContentComment();
        contentComment1.setId(1L);
        ContentComment contentComment2 = new ContentComment();
        contentComment2.setId(contentComment1.getId());
        assertThat(contentComment1).isEqualTo(contentComment2);
        contentComment2.setId(2L);
        assertThat(contentComment1).isNotEqualTo(contentComment2);
        contentComment1.setId(null);
        assertThat(contentComment1).isNotEqualTo(contentComment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentCommentDTO.class);
        ContentCommentDTO contentCommentDTO1 = new ContentCommentDTO();
        contentCommentDTO1.setId(1L);
        ContentCommentDTO contentCommentDTO2 = new ContentCommentDTO();
        assertThat(contentCommentDTO1).isNotEqualTo(contentCommentDTO2);
        contentCommentDTO2.setId(contentCommentDTO1.getId());
        assertThat(contentCommentDTO1).isEqualTo(contentCommentDTO2);
        contentCommentDTO2.setId(2L);
        assertThat(contentCommentDTO1).isNotEqualTo(contentCommentDTO2);
        contentCommentDTO1.setId(null);
        assertThat(contentCommentDTO1).isNotEqualTo(contentCommentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contentCommentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contentCommentMapper.fromId(null)).isNull();
    }
}
