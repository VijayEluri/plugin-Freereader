package plugins.Freereader;

import plugins.Freereader.models.Config;
import plugins.Freereader.web.WebInterface;

import freenet.pluginmanager.*;
import freenet.clients.http.PageMaker;
import freenet.support.Logger;
import freenet.l10n.BaseL10n.LANGUAGE;

import java.util.Timer;

/**
 * Freereader
 * Freenet Plugin
 * 
 * @author Mario Volke
 */
public class Freereader implements FredPlugin, FredPluginThreadless, FredPluginVersioned, FredPluginRealVersioned, FredPluginL10n 
{
	private PluginRespirator pluginRespirator;
	private WebInterface webInterface;
	
	private Config config;
	
	@SuppressWarnings("unused")
	private LANGUAGE language;
	
	public static final String pluginUri = "/plugins/plugins.Freereader.Freereader";
	public static final String pluginName = "Freereader";
	
	public void runPlugin(PluginRespirator pr) 
	{
		pluginRespirator = pr;
		
		config = Config.getInstance(this);
		
		webInterface = new WebInterface(this, pluginRespirator.getHLSimpleClient(), pluginRespirator.getToadletContainer());
		webInterface.load();

		Timer timer = new Timer();
		timer.schedule(new UpdateTask(this), 1000, config.getUpdateInterval() * 1000);
	}
	
	public String getVersion() 
	{
		return Version.getVersion() + " r" + Version.getSvnRevision();
	}
	
	public long getRealVersion() 
	{
		return Version.getVersion();
	}	
	
	public PluginRespirator getPluginRespirator()
	{
		return pluginRespirator;
	}
	
	public PageMaker getPageMaker()
	{
		return pluginRespirator.getPageMaker();
	}
	
	public void terminate() 
	{
		webInterface.unload();
		
		Store.destruct();
		
		Logger.normal(this, pluginName + " terminated.");
	}
	
	public String getString(String key) {
		// TODO return a translated string
		return key;
	}
	
	public void setLanguage(LANGUAGE newLanguage) {
		language = newLanguage;
	}
}
