package com.blgroup.jkl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 供应商控制器
 * @author JZM48
 *
 */
@Controller
public class SupplierController {
	private static Logger log = LoggerFactory.getLogger(SupplierController.class);
	@RequestMapping(value="/supplier")
	public ModelAndView sweepCodePurchasing(){
		ModelAndView mv = new ModelAndView();
		mv.getModelMap().put("appId", "wxba2a86a096369cce");
		mv.setViewName("/SweepCodePurchasing");
		return mv;
	}
}
