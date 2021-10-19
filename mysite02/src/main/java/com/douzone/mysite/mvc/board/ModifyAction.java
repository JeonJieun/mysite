package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Long no = Long.parseLong(request.getParameter("no"));
		BoardVo vo = new BoardDao().findNo(no);
		
		String title = request.getParameter("title");
		String contents = request.getParameter("content");

		vo.setTitle(title);
		vo.setContents(contents);
		new BoardDao().update(vo);
		
		String pState = request.getParameter("pState");

		MvcUtil.redirect("/mysite02/board?a=view&no=" + vo.getNo()+"&pState=" + pState, request, response);
	}

}
