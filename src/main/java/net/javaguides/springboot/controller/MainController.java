package net.javaguides.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import cn.apiclub.captcha.Captcha;
import net.javaguides.springboot.dto.UserRegistrationDto;
import net.javaguides.springboot.model.User;
import net.javaguides.utils.CaptchaUtils;

@Controller
public class MainController {
	

	
	
	@ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }
	
	private void setupCaptcha() {
		User user = new User();
		Captcha captcha = CaptchaUtils.createCaptcha(200, 50);
		user.setHidden(captcha.getAnswer());
		user.setCaptcha("");
		user.setImage(CaptchaUtils.encodeBase64(captcha));
	}
	
	@GetMapping("/login")
	public String login() {
		setupCaptcha();
		return "login";
	}
	
	@PostMapping("/login")
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
		String page="";
		if(registrationDto.getCaptcha().equals(registrationDto.getHidden()))
		{
			page ="redirect:/registration?success";
		} else {
			setupCaptcha();
			return "login";
		}
		return page;
		//return "redirect:/registration?success";
	}
}
