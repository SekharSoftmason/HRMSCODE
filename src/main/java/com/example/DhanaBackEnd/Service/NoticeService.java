package com.example.DhanaBackEnd.Service;
import java.util.List;

import com.example.DhanaBackEnd.Configuration.JWTService;
import com.example.DhanaBackEnd.Configuration.ResponseGlobal;
import com.example.DhanaBackEnd.DTO.NoticeDto;
import com.example.DhanaBackEnd.Repo.AdminRepo;
import com.example.DhanaBackEnd.Repo.NoticeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.DhanaBackEnd.Model.Admin;
import com.example.DhanaBackEnd.Model.Notice;
@Service
public class NoticeService {
    @Autowired
    private NoticeRepo noticerepo;
    @Autowired
    private AdminRepo adminrepo;
    @Autowired
    private JWTService jwtService;
    public ResponseGlobal<?> createNotice(NoticeDto dto) {
        try {
            if (dto.getAdminId() == null) {
                return ResponseGlobal.onError("Admin ID is required");
            }
            if (dto.getNoticeTitle() == null || dto.getNoticeTitle().isBlank()) {
                return ResponseGlobal.onError("Notice title is required");
            }
            if (dto.getNoticeTitle() == null) {
                return ResponseGlobal.onError("Notice date is required");
            }
            Admin admin = adminrepo.findById(dto.getAdminId()).orElse(null);
            if (admin == null) {
                return ResponseGlobal.onError("Admin not found");
            }
            Notice notice = new Notice();
            notice.setNoticeTitle(dto.getNoticeTitle());
            notice.setNoticeName(dto.getNoticeName());
            notice.setNoticeDescription(dto.getNoticeDescription());
            notice.setAdmin(admin);
            Notice saved = noticerepo.save(notice);
            return ResponseGlobal.onSuccess("Notice created successfully", saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGlobal.onError("Failed to create notice: " + e.getMessage());
        }
    }
    public ResponseGlobal<?> getAllNotices(String token) {
        String getEmailfromToken=jwtService.getEmailFromToken(token);
        try {
            List<Notice> notices = noticerepo.findAll();
            return ResponseGlobal.onSuccess("Notices retrieved successfully", notices);
        } catch (Exception e) {
            return ResponseGlobal.onError("Failed to retrieve notices: " + e.getMessage());
        }
    }
    public ResponseGlobal<?> getNoticeById(Long id,String token) {
        String getEmailfromToken=jwtService.getEmailFromToken(token);
        return noticerepo.findById(id)
                .<ResponseGlobal<?>>map(notice ->
                        ResponseGlobal.onSuccess("Notice retrieved successfully", notice))
                .orElseGet(() -> ResponseGlobal.onError("Notice not found"));
    }
    public ResponseGlobal<?> updateNotice(Long id, NoticeDto dto,String token) {
        String getEmailfromToken=jwtService.getEmailFromToken(token);
        try {
            Notice notice = noticerepo.findById(id).orElse(null);
            if (notice == null) {
                return ResponseGlobal.onError("Notice not found");
            }
            if (dto.getNoticeTitle() != null && !dto.getNoticeTitle().isBlank()) {
                notice.setNoticeTitle(dto.getNoticeTitle());
            }
            if (dto.getNoticeName() != null) {
                notice.setNoticeName(dto.getNoticeName());
            }
            if (dto.getNoticeDescription() != null) {
                notice.setNoticeDescription(dto.getNoticeDescription());
            }
            if (dto.getAdminId() != null) {
                Admin admin = adminrepo.findById(dto.getAdminId()).orElse(null);
                if (admin == null) {
                    return ResponseGlobal.onError("Admin not found");
                }
                notice.setAdmin(admin);
            }
            Notice updated = noticerepo.save(notice);
            return ResponseGlobal.onSuccess("Notice updated successfully", updated);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGlobal.onError("Failed to update notice: " + e.getMessage());
        }
    }
    public ResponseGlobal<?> deleteNotice(Long id,String token) {
        String getEmailfromToken=jwtService.getEmailFromToken(token);
        try {
            Notice notice = noticerepo.findById(id).orElse(null);
            if (notice == null) {
                return ResponseGlobal.onError("Notice not found");
            }
            noticerepo.delete(notice);
            return ResponseGlobal.onSuccess("Notice deleted successfully", null);
        } catch (Exception e) {
            return ResponseGlobal.onError("Failed to delete notice: " + e.getMessage());
        }
    }
}