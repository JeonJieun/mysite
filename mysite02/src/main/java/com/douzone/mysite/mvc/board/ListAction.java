package com.douzone.mysite.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PageVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PageVo pageVo = new PageVo(1L);
		
		if (isStringLong(request.getParameter("pState"))) {
			Long pIndex = Long.parseLong(request.getParameter("pState"));
			if(pIndex > pageVo.getAblepIndex()) { pageVo.setpIndex(pageVo.getAblepIndex()); }
			else if(pIndex < 1) { pageVo.setpIndex(1L); }
			else { pageVo.setpIndex(pIndex); }
		}
		
		List<BoardVo> list = new BoardDao().findLimit(pageVo.getpIndex(), pageVo.getLines());
		request.setAttribute("list", list);
		request.setAttribute("pageVo", pageVo);
		MvcUtil.forward("board/list", request, response);

	}

	public boolean isStringLong(String s) {
		try {
			Long.parseLong(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
