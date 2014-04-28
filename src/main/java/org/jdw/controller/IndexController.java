package org.jdw.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.jdw.domain.Message;
import org.jdw.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(IndexController.INDEX_CONTROLLER_MAPPING)
public class IndexController {

	private static final Logger logger = Logger.getLogger(IndexController.class.getSimpleName());

	public static final String INDEX_CONTROLLER_MAPPING = "/";

	@Autowired
	private TimelineService timelineService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(Model model) throws IOException {
		logger.info("Loading index.");

		model.addAttribute("message", new Message());
		model.addAttribute("resultText", "");
		return "index";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String submitText(Model model, @Valid Message message, BindingResult result) throws IOException {
		logger.info("Submitting text.");
		String resultText;

		if (result.hasErrors()) {
			resultText = "Cannot submit a blank message or a message over " + Message.MAX_TEXT_LENGTH + " characters long.";
		} else {
			String text = message.getText();
			timelineService.submitText(text);
			resultText = "Submitted: " + text;
		}

		logger.info("Text submission result is: " + resultText);
		model.addAttribute("message", message);
		model.addAttribute("resultText", resultText);
		return "index";

	}

}