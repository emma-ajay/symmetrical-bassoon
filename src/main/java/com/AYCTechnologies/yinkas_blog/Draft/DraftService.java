package com.AYCTechnologies.yinkas_blog.Draft;

import com.AYCTechnologies.yinkas_blog.Exceptions.BadRequestException;
import com.AYCTechnologies.yinkas_blog.PostCover.PostCover;
import com.AYCTechnologies.yinkas_blog.Response.PagedResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class DraftService {

    @Autowired
    DraftRepository draftRepository;

    @Autowired
    ModelMapper modelMapper;

    public Draft createDraft(DraftDTO draftDTO, String userName, Long userId){

        Draft draft = new Draft();
        draft = modelMapper.map(draftDTO, Draft.class);
        draft.setUserId(userId);
        draft.setCreatedBy(userName);
        draft.setIsPublished(Boolean.FALSE);
        draft.setIsHidden(Boolean.FALSE);

        return draftRepository.save(draft);

    }
    public Draft getDraftById(Long id) {
        Draft draft = draftRepository.findDraftByDraftId(id);
        if(Objects.isNull(draft)) throw new BadRequestException("Error finding draft");
        return draft;
    }

    public void  updatePublishedStatus (Long draftId, String publishedDate){
        Draft draft = getDraftById(draftId);
        draft.setDraftId(draftId);
        draft.setIsPublished(Boolean.TRUE);
        draft.setLastModifiedDate(publishedDate);
        Draft rs = draftRepository.save(draft);
    }

    public PagedResponse<?> getAllDrafts(int page, int size, String sort) {
        if (Objects.equals(sort, "DESC")) {
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "last_modified_date");
            Page<Draft> drafts = draftRepository.findDrafts(pageable);
            if (drafts.getTotalElements() == 0) {
                return new PagedResponse<>(Collections.emptyList(), drafts.getNumber(),
                        drafts.getSize(), drafts.getTotalElements(), drafts.getTotalPages(), drafts.isLast());
            }
            List<Draft> draftList = drafts.toList();

            return new PagedResponse<>(draftList, drafts.getNumber(),
                    drafts.getSize(), drafts.getTotalElements(), drafts.getTotalPages(), drafts.isLast());
        } else {
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "last_modified_date");
            Page<Draft> drafts = draftRepository.findDrafts(pageable);
            if (drafts.getTotalElements() == 0) {
                return new PagedResponse<>(Collections.emptyList(), drafts.getNumber(),
                        drafts.getSize(), drafts.getTotalElements(), drafts.getTotalPages(), drafts.isLast());
            }
            List<Draft> draftList = drafts.toList();

            return new PagedResponse<>(draftList, drafts.getNumber(),
                    drafts.getSize(), drafts.getTotalElements(), drafts.getTotalPages(), drafts.isLast());

        }
    }

    public PagedResponse<?> getDraftsByUser(int page, int size, String sort,Long userId) {
        if (Objects.equals(sort, "DESC")) {
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "last_modified_date");
            Page<Draft> drafts = draftRepository.findDraftByUserId(pageable,userId);
            if (drafts.getTotalElements() == 0) {
                return new PagedResponse<>(Collections.emptyList(), drafts.getNumber(),
                        drafts.getSize(), drafts.getTotalElements(), drafts.getTotalPages(), drafts.isLast());
            }
            List<Draft> draftList = drafts.toList();

            return new PagedResponse<>(draftList, drafts.getNumber(),
                    drafts.getSize(), drafts.getTotalElements(), drafts.getTotalPages(), drafts.isLast());
        } else {
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "last_modified_date");
            Page<Draft> drafts = draftRepository.findDraftByUserId(pageable,userId);
            if (drafts.getTotalElements() == 0) {
                return new PagedResponse<>(Collections.emptyList(), drafts.getNumber(),
                        drafts.getSize(), drafts.getTotalElements(), drafts.getTotalPages(), drafts.isLast());
            }
            List<Draft> draftList = drafts.toList();

            return new PagedResponse<>(draftList, drafts.getNumber(),
                    drafts.getSize(), drafts.getTotalElements(), drafts.getTotalPages(), drafts.isLast());

        }
    }
}
