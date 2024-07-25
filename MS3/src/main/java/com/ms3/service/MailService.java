package com.ms3.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.ms3.dto.MailDTO;
import com.ms3.mapper.MailMapper;

@Service
public class MailService {

    private final MailMapper mapper;

    public MailService(MailMapper mapper) {
        this.mapper = mapper;
    }

    public List<MailDTO> selectMail(String userId) {
        return mapper.selectMail(userId);
    }

    public int sendMail(MailDTO mailDTO) {
        return mapper.sendMail(mailDTO);
    }

    public int deleteMail(int mailNo) {
        return mapper.deleteMail(mailNo);
    }

    public MailDTO selectMailDetail(int mailNo, String userId) {
        return mapper.selectMailDetail(mailNo, userId);
    }
}