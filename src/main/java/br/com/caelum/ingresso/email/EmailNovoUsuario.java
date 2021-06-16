package br.com.caelum.ingresso.email;

public class EmailNovoUsuario implements Email {

	private final Token token;

	public EmailNovoUsuario(Token token) {
		this.token = token;
	}

	@Override
	public String getTo() {
		return token.getEmail();
	}

	@Override
	public String getBody() {
		StringBuilder sb = new StringBuilder("<html>");
		sb.append("<body>");
		sb.append("<h2>Bem vindo!</h2>");
		sb.append(String.format("Acesso o <a href=%s>link</a> para para criar seu login no\n"
				+ "sistema de ingressos.", makeURL()));
		sb.append("</body>");
		sb.append("</html>");
		
		return sb.toString();
	}

	@Override
	public String getSubject() {
		return "Cadastro Sistema de Ingressos";
	}

	private String makeURL() {
		return String.format("http://localhost:8080/usuario/validate?uuid=%s", token.getUuid());
	}

}
