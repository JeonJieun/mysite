package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no = Long.parseLong(request.getParameter("no"));
		BoardVo vo = new BoardDao().findNo(no);
		
		String COOKIE_NAME = "visitView" + no;
		
		int visitState = 0; // 없음
		
		//쿠키 읽기
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null&& cookies.length>0) {
			for(Cookie cookie : cookies) {
				if(COOKIE_NAME.equals(cookie.getName())) {
					visitState = 1;
				}
			}
		}
		
		if(visitState == 0) {
			new BoardDao().updateHit(no);
			//쿠키 쓰기
			Cookie cookie = new Cookie(COOKIE_NAME, String.valueOf(1));
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(5*60); // 5분
			response.addCookie(cookie);
		}
		
		request.setAttribute("vo", vo);
		MvcUtil.forward("board/view", request, response);

	}

}
