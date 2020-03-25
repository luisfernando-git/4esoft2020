package observer.cofre;

import java.util.ArrayList;
import java.util.List;

public class Cofre {

	private boolean aberto;
	private int senha;
	private List<CofreListenerAberto> listenersCofreAberto = new ArrayList<>();
	private List<CofreListenerFechado> listenersCofreFechado = new ArrayList<>();
	private List<CofreListenerSenha> listenersSenhaIncorreta = new ArrayList<>();
	
	public Cofre(int senha) {
		this.senha = senha;
		this.aberto = true;
	}
	
	public void abrir(int senha) {
		
		if(this.senha == senha) {
			this.aberto = true;
			this.listenersCofreAberto.forEach(l -> l.cofreFoiAberto());
		} else {
			
			this.listenersSenhaIncorreta.forEach(l -> l.senhaIncorreta());
		}
	}
	
	public void fechar() {
		this.listenersCofreFechado.forEach(l -> l.cofreFoiFechado());
		this.aberto = false;
	}
	
	public Boolean isAberto() {
		return this.aberto;
	}
	
	public void addListenerAberto(CofreListenerAberto listener) {
		this.listenersCofreAberto.add(listener);
	}
	
	public void addListenerFechado(CofreListenerFechado listener) {
		this.listenersCofreFechado.add(listener);
	}
	
	public void addListenerSenha(CofreListenerSenha listener) {
		this.listenersSenhaIncorreta.add(listener);
	}
}
