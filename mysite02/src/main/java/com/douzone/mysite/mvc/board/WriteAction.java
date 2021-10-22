package com.douzone.mysite.mvc.board;

import java.io.IOException;
import java.net.URLEncoder;

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
		String pState = request.getParameter("pState");
		String kwd = request.getParameter("kwd");
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		
		BoardVo vo = new BoardVo();
		Long groupNo = null;
		
		if (request.getParameter("no") != "") { // 댓글
			Long no = Long.parseLong(request.getParameter("no"));
			BoardVo parentVo = new BoardDao().findNo(no);
			vo.setGroupNo(parentVo.getGroupNo());
			vo.setOrderNo(parentVo.getOrderNo()+1);
			vo.setDepth(parentVo.getDepth()+1);
			new BoardDao().updateOrderNo(vo);

		}
		
		else { //새글 group_no = ?, order_no = 1, depth = 0; // 첫 글쓰기 
			groupNo = new BoardDao().findMaxGroupNo();
			++groupNo;
			vo.setGroupNo(groupNo);
			vo.setOrderNo(1L);
			vo.setDepth(0L);
		}
		
		if(title.equals("")) { vo.setTitle("[없음]"); }
		else { vo.setTitle(title); }
		vo.setContents(contents);
		vo.setUserNo(authUser.getNo());
		new BoardDao().insert(vo);

		MvcUtil.redirect("/mysite02/board?pState=" + pState + "&kwd=" + URLEncoder.encode( kwd, "utf-8"), request, response);

	}

}
