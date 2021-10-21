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

	public boolean insertGuestbook(GuestbookVo vo) {
		return guestbookRepository.insert(vo);
	}

	public Boolean deleteGuestbook(Long no, String password) {
		GuestbookVo vo = new GuestbookVo();
		vo.setNo(no);
		vo.setPassword(password);
		
		return guestbookRepository.delete(vo);
	}
	
}
