package com.douzone.mysite.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Access Control(보안, 인증체크)
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			MvcUtil.redirect("/mysite02/user?a=loginform", request, response);
			return;
		}
		//////////////////////////////////////////////////////
		
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		Long groupNo = Long.parseLong(request.getParameter("groupNo"));
		
		BoardDao dao = new BoardDao();
		List<BoardVo> list = dao.findGroupNo(groupNo);
		
		System.out.println(list.size());
		
		if(list.size()==0) {

			BoardVo vo = new BoardVo();
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setGroupNo(groupNo);
			vo.setOrderNo(0L);
			vo.setDepth(0L);
			vo.setUserNo(authUser.getNo());
			vo.setUserName(contents);
			dao.insert(vo);
		}
		
		

		
		
		MvcUtil.redirect("/mysite02/board?a=list", request, response);

	}

}
