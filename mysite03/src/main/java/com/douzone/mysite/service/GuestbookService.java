package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	@Autowired
	private GuestbookRepository guestbookRepository;

	public List<GuestbookVo> getGuestbook() {
		return guestbookRepository.findAll();
	}

	public void deleteGuestbook(Long no, String password) {
		guestbookRepository.delete(no, password);
	}

	public void insertGuestbook(GuestbookVo vo) {
		guestbookRepository.insert(vo);
	}
	
}
