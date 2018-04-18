package com.tsxy.carl.web.rest;

import com.tsxy.carl.RegistrationBookApp;

import com.tsxy.carl.domain.ContentCommentReply;
import com.tsxy.carl.repository.ContentCommentReplyRepository;
import com.tsxy.carl.service.ContentCommentReplyService;
import com.tsxy.carl.service.dto.ContentCommentReplyDTO;
import com.tsxy.carl.service.mapper.ContentCommentReplyMapper;
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
 * Test class for the ContentCommentReplyResource REST controller.
 *
 * @see ContentCommentReplyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RegistrationBookApp.class)
public class ContentCommentReplyResourceIntTest {

    private static final Long DEFAULT_CONTENT_ID = 1L;
    private static final Long UPDATED_CONTENT_ID = 2L;

    private static final String DEFAULT_CONTENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_NAME = "BBBBBBBBBB";

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
    private ContentCommentReplyRepository contentCommentReplyRepository;

    @Autowired
    private ContentCommentReplyMapper contentCommentReplyMapper;

    @Autowired
    private ContentCommentReplyService contentCommentReplyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContentCommentReplyMockMvc;

    private ContentCommentReply contentCommentReply;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContentCommentReplyResource contentCommentReplyResource = new ContentCommentReplyResource(contentCommentReplyService);
        this.restContentCommentReplyMockMvc = MockMvcBuilders.standaloneSetup(contentCommentReplyResource)
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
    public static ContentCommentReply createEntity(EntityManager em) {
        ContentCommentReply contentCommentReply = new ContentCommentReply()
            .contentId(DEFAULT_CONTENT_ID)
            .contentName(DEFAULT_CONTENT_NAME)
            .userId(DEFAULT_USER_ID)
            .userName(DEFAULT_USER_NAME)
            .content(DEFAULT_CONTENT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return contentCommentReply;
    }

    @Before
    public void initTest() {
        contentCommentReply = createEntity(em);
    }

    @Test
    @Transactional
    public void createContentCommentReply() throws Exception {
        int databaseSizeBeforeCreate = contentCommentReplyRepository.findAll().size();

        // Create the ContentCommentReply
        ContentCommentReplyDTO contentCommentReplyDTO = contentCommentReplyMapper.toDto(contentCommentReply);
        restContentCommentReplyMockMvc.perform(post("/api/content-comment-replies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentCommentReplyDTO)))
            .andExpect(status().isCreated());

        // Validate the ContentCommentReply in the database
        List<ContentCommentReply> contentCommentReplyList = contentCommentReplyRepository.findAll();
        assertThat(contentCommentReplyList).hasSize(databaseSizeBeforeCreate + 1);
        ContentCommentReply testContentCommentReply = contentCommentReplyList.get(contentCommentReplyList.size() - 1);
        assertThat(testContentCommentReply.getContentId()).isEqualTo(DEFAULT_CONTENT_ID);
        assertThat(testContentCommentReply.getContentName()).isEqualTo(DEFAULT_CONTENT_NAME);
        assertThat(testContentCommentReply.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testContentCommentReply.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testContentCommentReply.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testContentCommentReply.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testContentCommentReply.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testContentCommentReply.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testContentCommentReply.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createContentCommentReplyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contentCommentReplyRepository.findAll().size();

        // Create the ContentCommentReply with an existing ID
        contentCommentReply.setId(1L);
        ContentCommentReplyDTO contentCommentReplyDTO = contentCommentReplyMapper.toDto(contentCommentReply);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentCommentReplyMockMvc.perform(post("/api/content-comment-replies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentCommentReplyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContentCommentReply in the database
        List<ContentCommentReply> contentCommentReplyList = contentCommentReplyRepository.findAll();
        assertThat(contentCommentReplyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentCommentReplyRepository.findAll().size();
        // set the field null
        contentCommentReply.setUserId(null);

        // Create the ContentCommentReply, which fails.
        ContentCommentReplyDTO contentCommentReplyDTO = contentCommentReplyMapper.toDto(contentCommentReply);

        restContentCommentReplyMockMvc.perform(post("/api/content-comment-replies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentCommentReplyDTO)))
            .andExpect(status().isBadRequest());

        List<ContentCommentReply> contentCommentReplyList = contentCommentReplyRepository.findAll();
        assertThat(contentCommentReplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentCommentReplyRepository.findAll().size();
        // set the field null
        contentCommentReply.setContent(null);

        // Create the ContentCommentReply, which fails.
        ContentCommentReplyDTO contentCommentReplyDTO = contentCommentReplyMapper.toDto(contentCommentReply);

        restContentCommentReplyMockMvc.perform(post("/api/content-comment-replies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentCommentReplyDTO)))
            .andExpect(status().isBadRequest());

        List<ContentCommentReply> contentCommentReplyList = contentCommentReplyRepository.findAll();
        assertThat(contentCommentReplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContentCommentReplies() throws Exception {
        // Initialize the database
        contentCommentReplyRepository.saveAndFlush(contentCommentReply);

        // Get all the contentCommentReplyList
        restContentCommentReplyMockMvc.perform(get("/api/content-comment-replies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contentCommentReply.getId().intValue())))
            .andExpect(jsonPath("$.[*].contentId").value(hasItem(DEFAULT_CONTENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].contentName").value(hasItem(DEFAULT_CONTENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getContentCommentReply() throws Exception {
        // Initialize the database
        contentCommentReplyRepository.saveAndFlush(contentCommentReply);

        // Get the contentCommentReply
        restContentCommentReplyMockMvc.perform(get("/api/content-comment-replies/{id}", contentCommentReply.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contentCommentReply.getId().intValue()))
            .andExpect(jsonPath("$.contentId").value(DEFAULT_CONTENT_ID.intValue()))
            .andExpect(jsonPath("$.contentName").value(DEFAULT_CONTENT_NAME.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContentCommentReply() throws Exception {
        // Get the contentCommentReply
        restContentCommentReplyMockMvc.perform(get("/api/content-comment-replies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContentCommentReply() throws Exception {
        // Initialize the database
        contentCommentReplyRepository.saveAndFlush(contentCommentReply);
        int databaseSizeBeforeUpdate = contentCommentReplyRepository.findAll().size();

        // Update the contentCommentReply
        ContentCommentReply updatedContentCommentReply = contentCommentReplyRepository.findOne(contentCommentReply.getId());
        updatedContentCommentReply
            .contentId(UPDATED_CONTENT_ID)
            .contentName(UPDATED_CONTENT_NAME)
            .userId(UPDATED_USER_ID)
            .userName(UPDATED_USER_NAME)
            .content(UPDATED_CONTENT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ContentCommentReplyDTO contentCommentReplyDTO = contentCommentReplyMapper.toDto(updatedContentCommentReply);

        restContentCommentReplyMockMvc.perform(put("/api/content-comment-replies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentCommentReplyDTO)))
            .andExpect(status().isOk());

        // Validate the ContentCommentReply in the database
        List<ContentCommentReply> contentCommentReplyList = contentCommentReplyRepository.findAll();
        assertThat(contentCommentReplyList).hasSize(databaseSizeBeforeUpdate);
        ContentCommentReply testContentCommentReply = contentCommentReplyList.get(contentCommentReplyList.size() - 1);
        assertThat(testContentCommentReply.getContentId()).isEqualTo(UPDATED_CONTENT_ID);
        assertThat(testContentCommentReply.getContentName()).isEqualTo(UPDATED_CONTENT_NAME);
        assertThat(testContentCommentReply.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testContentCommentReply.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testContentCommentReply.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testContentCommentReply.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testContentCommentReply.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testContentCommentReply.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testContentCommentReply.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingContentCommentReply() throws Exception {
        int databaseSizeBeforeUpdate = contentCommentReplyRepository.findAll().size();

        // Create the ContentCommentReply
        ContentCommentReplyDTO contentCommentReplyDTO = contentCommentReplyMapper.toDto(contentCommentReply);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContentCommentReplyMockMvc.perform(put("/api/content-comment-replies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentCommentReplyDTO)))
            .andExpect(status().isCreated());

        // Validate the ContentCommentReply in the database
        List<ContentCommentReply> contentCommentReplyList = contentCommentReplyRepository.findAll();
        assertThat(contentCommentReplyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContentCommentReply() throws Exception {
        // Initialize the database
        contentCommentReplyRepository.saveAndFlush(contentCommentReply);
        int databaseSizeBeforeDelete = contentCommentReplyRepository.findAll().size();

        // Get the contentCommentReply
        restContentCommentReplyMockMvc.perform(delete("/api/content-comment-replies/{id}", contentCommentReply.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContentCommentReply> contentCommentReplyList = contentCommentReplyRepository.findAll();
        assertThat(contentCommentReplyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentCommentReply.class);
        ContentCommentReply contentCommentReply1 = new ContentCommentReply();
        contentCommentReply1.setId(1L);
        ContentCommentReply contentCommentReply2 = new ContentCommentReply();
        contentCommentReply2.setId(contentCommentReply1.getId());
        assertThat(contentCommentReply1).isEqualTo(contentCommentReply2);
        contentCommentReply2.setId(2L);
        assertThat(contentCommentReply1).isNotEqualTo(contentCommentReply2);
        contentCommentReply1.setId(null);
        assertThat(contentCommentReply1).isNotEqualTo(contentCommentReply2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentCommentReplyDTO.class);
        ContentCommentReplyDTO contentCommentReplyDTO1 = new ContentCommentReplyDTO();
        contentCommentReplyDTO1.setId(1L);
        ContentCommentReplyDTO contentCommentReplyDTO2 = new ContentCommentReplyDTO();
        assertThat(contentCommentReplyDTO1).isNotEqualTo(contentCommentReplyDTO2);
        contentCommentReplyDTO2.setId(contentCommentReplyDTO1.getId());
        assertThat(contentCommentReplyDTO1).isEqualTo(contentCommentReplyDTO2);
        contentCommentReplyDTO2.setId(2L);
        assertThat(contentCommentReplyDTO1).isNotEqualTo(contentCommentReplyDTO2);
        contentCommentReplyDTO1.setId(null);
        assertThat(contentCommentReplyDTO1).isNotEqualTo(contentCommentReplyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contentCommentReplyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contentCommentReplyMapper.fromId(null)).isNull();
    }
}
