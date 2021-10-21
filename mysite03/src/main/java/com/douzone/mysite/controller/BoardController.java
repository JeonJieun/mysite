package com.douzone.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PageVo;

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
	public String list(Model model, @PathVariable(value = "pIndex", required=false) Long pIndex) {
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
			@CookieValue(value = , required = false) Cookie coookie,
			HttpServletResponse response) {
		
		//BoardVo vo = boardService.findNo(no);
		String COOKIE_NAME = "visitView" + no;
		boardService.getView(no);

		return null;
	}
	 */
}
