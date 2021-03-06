package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.dao.UserDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class DeleteAction implements Action {

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

		Long no = Long.parseLong(request.getParameter("no"));
		String password = request.getParameter("password");

		BoardVo vo = new BoardDao().findNo(no);
		UserVo userVo = new UserDao().findByNo(vo.getUserNo());
		
		String state = "비밀번호가 틀렸습니다.";
		
		
		if ( password.equals(userVo.getPassword()) ) {

			if (new BoardDao().deleteUpdate(vo)) {
				state = "삭제되었습니다.";
			} else {
				state = "삭제되지 않았습니다.";
			}
		}

		request.setAttribute("state", state);
		MvcUtil.forward("board/accessState", request, response);

	}

}
