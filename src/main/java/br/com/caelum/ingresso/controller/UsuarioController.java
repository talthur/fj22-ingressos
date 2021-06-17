package br.com.caelum.ingresso.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.email.ConfirmacaoLoginForm;
import br.com.caelum.ingresso.email.EmailNovoUsuario;
import br.com.caelum.ingresso.email.Mailer;
import br.com.caelum.ingresso.email.Token;
import br.com.caelum.ingresso.helper.TokenHelper;

@Controller
public class UsuarioController {
	
	@Autowired
	private Mailer mailer;
	
	@Autowired
	private TokenHelper tokenHelper;

	@GetMapping("/usuario/request")
	public ModelAndView formSolicitacaoDeAcesso() {
		return new ModelAndView("usuario/form-email");
	}

	@PostMapping("/usuario/request")
	public ModelAndView solicitacaoDeAcesso(String email) {
		ModelAndView view = new ModelAndView("usuario/adicionado");
		Token token = tokenHelper.generateFrom(email);
		mailer.send(new EmailNovoUsuario(token));
		return view;
	}
	
	@GetMapping("/usuario/validate")
	public ModelAndView validaLink(@RequestParam("uuid") String uuid) {
		
		Optional<Token> optionalToken = tokenHelper.getTokenFrom(uuid);
		
		if(!optionalToken.isPresent()) {
			ModelAndView view = new ModelAndView("redirect:/login");
			view.addObject("msg", "O token do link utilizado não foi encontrado!");
			return view;
		}
		
		Token token = optionalToken.get();
		ConfirmacaoLoginForm confirmacaoLogin = new ConfirmacaoLoginForm(token);
		ModelAndView view = new ModelAndView("/usuario/confirmacao");
		view.addObject("confirmacaoLogin", confirmacaoLogin);
		return view;

	}
	
	@PostMapping("/usuario/cadastra")
	public ModelAndView cadastrar(ConfirmacaoLoginForm confirmacaoLoginForm) {
		ModelAndView view = new ModelAndView("redirect:/login");
		if (confirmacaoLoginForm.isValid()) {
			
		}
		
		view.addObject("msg", "O token do link utilizado não foi encontrado!");
		return view;
	}
}
