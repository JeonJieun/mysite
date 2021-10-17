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
		
		PageVo pageVo = new PageVo();
		
		if(request.getParameter("pState") != null) {
			if(request.getParameter("pState").equals("prev")) {
				pageVo.setpIndex(pageVo.getStartPage()-1);
			}
			else if(request.getParameter("pState").equals("next")) {
				pageVo.setpIndex(pageVo.getEndPage()+1);
			}
			else {
				Long pIndex = Long.parseLong(request.getParameter("pState"));
				pageVo.setpIndex(pIndex);
			}
		}
		
		List<BoardVo> list = new BoardDao().findLimit(pageVo.getpIndex(), 5L);
		request.setAttribute("list", list);
		request.setAttribute("pageVo", pageVo);
		MvcUtil.forward("board/list", request, response);

	}

}
