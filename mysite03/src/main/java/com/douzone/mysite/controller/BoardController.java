package com.douzone.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PageVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	/*
	 * if("modifyform".equals(actionName)) { action = new ModifyFormAction(); } else
	 * if("modify".equals(actionName)) { action = new ModifyAction(); } else
	 * if("view".equals(actionName)) { action = new ViewAction(); } else
	 * if("writeform".equals(actionName)) { action = new WriteFormAction(); } else
	 * if("write".equals(actionName)) { action = new WriteAction(); } else
	 * if("deleteform".equals(actionName)) { action = new DeleteFormAction(); } else
	 * if("delete".equals(actionName)) { action = new DeleteAction(); }
	 */

	@Autowired
	private BoardService boardService;

	@RequestMapping(value = { "", "/{pIndex}" }, method = RequestMethod.GET)
	public String list(Model model, @PathVariable(value = "pIndex", required = false) Long pIndex) {
		PageVo pageVo = new PageVo(1L);

		if (pIndex != null) {

			if (pIndex > pageVo.getAblepIndex()) {
				pageVo.setpIndex(pageVo.getAblepIndex());
			} else if (pIndex < 1) {
				pageVo.setpIndex(1L);
			} else {
				pageVo.setpIndex(pIndex);
			}
		}

		List<BoardVo> list = boardService.getBoard(pageVo.getpIndex(), pageVo.getLines());
		model.addAttribute("list", list);
		model.addAttribute("pageVo", pageVo);
		return "board/list";
	}
	/*
	  @RequestMapping(value = {"/view/{no}/{pIndex}"}, method = RequestMethod.GET)
	  public String view(Model model,
	  
	  @PathVariable(value = "no", required=false) Long no,
	  
	  @PathVariable(value = "pIndex", required=false) Long pIndex,
	  
	  @CookieValue(value = , required = false) Cookie coookie, HttpServletResponse
	  response) {
	  
	  //BoardVo vo = boardService.findNo(no); String COOKIE_NAME = "visitView" +
	  no; boardService.getView(no);
	  
	  return null; }
	 */
	
	@RequestMapping(value = "/write/{pIndex}", method = RequestMethod.GET)
	public String write(@PathVariable("pIndex") Long pIndex, Model model) {
		model.addAttribute("pIndex", pIndex) {
			return "board/write";
		}
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(HttpSession session, Model model, BoardVo vo,
			@PathVariable(value = "pIndex", required = false) Long pIndex) {
		// 접근제어(Access Control List)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/user/login";
		}
		//////////////////////////////////////////////////////////

		Long groupNo = null;

		if (vo.getNo() != null) {
			BoardVo parentVo = boardService.findNo(vo.no);
			vo.setGroupNo(parentVo.getGroupNo());

			if (parentVo.getOrderNo() != 0) { // group_no = parent_group_no, order_no = parent_order_no, depth = 2; //
												// 대댓글
				vo.setOrderNo(parentVo.getOrderNo());
				vo.setDepth(2L);
		//		new boardService.updateDepth(vo);

			} else { // group_no = parent_group_no, order_no = 1, depth = 1; // 댓글
				vo.setOrderNo(1L);
				vo.setDepth(1L);
			//	new boardService.updateOrderNo(vo);
			}
		}

		else { // 새글 group_no = ?, order_no = 0, depth = 0; // 첫 글쓰기
		//	groupNo = boardService.findMaxGroupNo();
			++groupNo;
			vo.setGroupNo(groupNo);
			vo.setOrderNo(0L);
			vo.setDepth(0L);
		}

		if (vo.getTitle().equals("")) {
			vo.setTitle("[없음]");
		}
		vo.setUserNo(authUser.getNo());
	//	boardService.insert(vo);

		return "redirect:/board/" + pIndex;

	}

//	@RequestMapping(value = { "/view/{no}/{pIndex}" }, method = RequestMethod.GET)
//	public String modify() {

		
//	}

//	@RequestMapping(value = { "/view/{no}/{pIndex}" }, method = RequestMethod.GET)
//	public String delete() {

		
//	}
}
