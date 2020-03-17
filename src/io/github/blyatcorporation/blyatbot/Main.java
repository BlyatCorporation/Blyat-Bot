package io.github.blyatcorporation.blyatbot;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import com.sun.jdi.event.Event;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import net.dv8tion.jda.api.hooks.EventListener;

public class Main implements Runnable, EventListener{
	
	private static String token;
	private final JDA jda;
	private final Scanner sc = new Scanner(System.in);
	private static boolean running;

	public String prefix = "vodk";
	
	public Main() throws LoginException, IllegalArgumentException, RateLimitedException {
		jda = new JDABuilder(AccountType.BOT).setToken(token).build();
		jda.addEventListener(this);
		System.out.println("[Narrabot] Bot connect� avec succ�s !");
	}
	
	public JDA getJda() {
		return jda;
	}

	
	public void setRunning(boolean running) {
		Main.running = running;
	} 
	
	
	@Override
	public void run() {
		
		running = true;
		
		while(running) {		
			if(sc.hasNextLine()) onConsoleCommand(sc.nextLine()); 
		}
			
		System.out.println("[Narrabot] Arret du bot en cours ...");
		jda.shutdownNow();
		System.out.println("[Narrabot] Bot stopp�");
		System.exit(0);
	}

	public static void main(String[] args) throws IllegalArgumentException, RateLimitedException{
		
		System.out.println("[Narrabot] D�marrage du bot ...");
		
		if (args.length < 1) {
	        System.out.println("[Narrabot] [ERROR] Veuillez indiquer le token du bot");
	        System.exit(0);
	    }
		token = args[0];
		System.out.println("[Narrabot] Connection ...");
		try {
			Main bot = new Main();
			new Thread(null, bot, "bot");
		} catch (LoginException e) {
			e.printStackTrace();
			System.out.println("[Narrabot] [ERROR] Erreur lors de la connection, veuillez v�rifier le token et votre connection internet.");
		}
		
		
		
		

	}

	// Commence ici
	
	public void onConsoleCommand(String cmd) {
		if(cmd.equalsIgnoreCase("stop")) {
			running = false;
		}
	}

	@Override
	public void onEvent(GenericEvent event) {
		if(event instanceof MessageReceivedEvent) onMessage((MessageReceivedEvent)event);
	}
	
	public void onMessage(MessageReceivedEvent event) {
		if (event.getAuthor().equals(event.getJDA().getSelfUser())) return;
		
		String msg = event.getMessage().getContentRaw();
		
		if(msg.startsWith(prefix)) {
			String[] cmd = msg.split(" ");
			System.out.println(cmd);
		}
		return;
		
	}
}
