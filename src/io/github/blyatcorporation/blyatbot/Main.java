package io.github.blyatcorporation.blyatbot;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
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
		System.out.println("[BlyatBot] Bot connecté avec succès !");
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
		
		System.out.println("[BlyatBot] Démarrage du bot ...");
		
		if (args.length < 1) {
	        System.out.println("[BlyatBot] [ERROR] Veuillez indiquer le token du bot");
	        System.exit(0);
	    }
		token = args[0];
		System.out.println("[BlyatBot] Connection ...");
		try {
			Main bot = new Main();
			new Thread(bot, "bot").start();
		} catch (LoginException e) {
			e.printStackTrace();
			System.out.println("[BlyatBot] [ERROR] Erreur lors de la connection, veuillez vérifier le token et votre connection internet.");
		}
		
		
		
		

	}

	// Commence ici
	
	public void onConsoleCommand(String cmd) {
		if(cmd.equalsIgnoreCase("stop")) {
			running = false;
		}
		return;
	}

	@Override
	public void onEvent(GenericEvent event) {
		if(event instanceof MessageReceivedEvent) onMessage((MessageReceivedEvent)event);
	}
	
	public void onMessage(MessageReceivedEvent event) {
		if (event.getAuthor().equals(event.getJDA().getSelfUser())) return;
		
		String msg = event.getMessage().getContentRaw();
		
		if(msg.startsWith(prefix)) { //Le message commence par le préfix -> commande pour le bot
			
			MessageChannel channel = event.getChannel();
			Member sender = event.getMember();
			
			String[] cmd = msg.split(" ");
			
			if(cmd[1].equalsIgnoreCase("urss")) {
				channel.sendMessage(sender.getAsMention() + " L'hymne de la mère patrie : https://www.youtube.com/watch?v=Rm6q_3WGy9M").queue();
				return;
			}	
			
			if(cmd[1].equalsIgnoreCase("Bonjour")) {
				channel.sendMessage(sender.getAsMention() + " привет собрат!").queue();
				return;
			}
			
			if(cmd[1].equalsIgnoreCase("SOS")) {
				channel.sendMessage(sender.getAsMention() + " Confirmer votre position avec vodk here :").queue();
				return;
			}
			
			if(cmd[1].equalsIgnoreCase("here")) {
				channel.sendMessage(sender.getAsMention() + "https://www.slate.fr/sites/default/files/giphy%20(55).gif").queue();
				return;
			
			}
			
			if(cmd[1].equalsIgnoreCase("OpenPlaneDoor")) {
				channel.sendMessage(sender.getAsMention() + "Nan on avait pas dit le porte avion ...").queue();
				return;
			}
			
			if(cmd[1].equalsIgnoreCase("answer")) {
				channel.sendMessage(sender.getAsMention() + " OUI + NON sqrt(2) VRAI").queue();
				return;
			}
			
			if(cmd[1].equalsIgnoreCase("CallKGB")) {
				channel.sendMessage(sender.getAsMention() + " Ca va pas être possible.").queue();
				return;
			}
			
			if(cmd[1].equalsIgnoreCase("Chaharaoui")) {
				channel.sendMessage(sender.getAsMention() + " Exercice 3!").queue();
				return;
			}
			
			channel.sendMessage("Cette commande n'existe pas !").queue();
			
			
		}
		return;
		
	}
}
