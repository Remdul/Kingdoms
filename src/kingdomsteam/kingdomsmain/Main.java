package kingdomsteam.kingdomsmain;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import kingdomsteam.kingdomsmain.Economy.EconomyHelper;
import kingdomsteam.kingdomsmain.Util.Util;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {
	
	static String name, displayname, version, description;
	static Main plugin;
    static Commands commands;
    static int len;
    static byte[] buf;
	
	//Database files
    static File playersFile;
    static File kingdomsFile;
    static File landControlFile;
    public static FileConfiguration players;
    public static FileConfiguration kingdoms;
    public static FileConfiguration landcontrol;
    
    //Config files
	static File mainConfigFile;
    static File economyConfigFile;
	static File levelUpsAndPowersConfigFile;
	static File chatConfigFile;
	static File politicsAndWarConfigFile;
    static File landControlConfigFile;
    public static FileConfiguration mainConfig;
    public static FileConfiguration economyConfig;
    public static FileConfiguration levelUpsAndPowersConfig;
    public static FileConfiguration chatConfig;
    public static FileConfiguration politicsAndWarConfig;
    public static FileConfiguration landControlConfig;
    
    public static Map<String, Long> loginTimeMillis;
    public static Map<String, Long> logoutTimeMillis;
    public static Map<String, Long> lastKingdomInteractions;
    public static Permission permission = null;
    public static Economy economy = null;
    public static Chat chat = null;
    
	public void onEnable(){
		plugin = this;
		name = getDescription().getName();
		displayname = getDescription().getFullName();
		version = getDescription().getVersion();
		description = getDescription().getDescription();

		//Database files
		playersFile = new File(getDataFolder(), "Players.yml");
		kingdomsFile = new File(getDataFolder(), "Kingdoms.yml");
		landControlFile = new File(getDataFolder(), "LandControl.yml");
		
		//Config files
		mainConfigFile = new File(getDataFolder(), "Configs" + File.separator + "Main_Config.yml");
		economyConfigFile = new File(getDataFolder(), "Configs" + File.separator + "Economy_Config.yml");
		levelUpsAndPowersConfigFile = new File(getDataFolder(), "Configs" + File.separator + "Level_Ups_And_Powers_Config.yml");
		chatConfigFile = new File(getDataFolder(), "Configs" + File.separator + "Chat_Config.yml");
		politicsAndWarConfigFile = new File(getDataFolder(), "Configs" + File.separator + "Politics_And_War_Config.yml");
		landControlConfigFile = new File(getDataFolder(), "Configs" + File.separator + "Land_Control_Config.yml");
		
	    try {
	        firstRun();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    //Database files
		players = new YamlConfiguration();
		kingdoms = new YamlConfiguration();
		landcontrol = new YamlConfiguration();
		
		//Config files
		mainConfig = new YamlConfiguration();
		economyConfig = new YamlConfiguration();
		levelUpsAndPowersConfig = new YamlConfiguration();
		chatConfig = new YamlConfiguration();
		politicsAndWarConfig = new YamlConfiguration();
		landControlConfig = new YamlConfiguration();
	    reloadYamls();
	    
		commands = new Commands();
		
		getCommand("k").setExecutor(commands);
		getCommand("khome").setExecutor(commands);
		getCommand("ksethome").setExecutor(commands);
		getCommand("kcompass").setExecutor(commands);
		getCommand("kchat").setExecutor(commands);
		getCommand("kversion").setExecutor(commands);
		getCommand("kclaim").setExecutor(commands);
		getCommand("kunclaim").setExecutor(commands);
		
		getServer().getPluginManager().registerEvents(new kingdomsteam.kingdomsmain.Listeners.ChatListener(),
				this);
		getServer().getPluginManager().registerEvents(new kingdomsteam.kingdomsmain.Listeners.LoginListener(),
				this);
		getServer().getPluginManager().registerEvents(new kingdomsteam.kingdomsmain.Listeners.LogoutListener(),
				this);
		getServer().getPluginManager().registerEvents(new kingdomsteam.kingdomsmain.Listeners.BlockListener(),
				this);
		getServer().getPluginManager().registerEvents(new kingdomsteam.kingdomsmain.Listeners.PlayerMoveListener(),
				this);
		getServer().getPluginManager().registerEvents(new kingdomsteam.kingdomsmain.Listeners.DamageListener(),
				this);
		getServer().getPluginManager().registerEvents(new EconomyHelper(), this);

		getLogger().info("[###### Kingdoms Enabled Successfully ######]");
		
		
		
		if(Main.mainConfig.getConfigurationSection("General") == null){
			Util.generateMainConfig();
		}
		if(Main.economyConfig.getConfigurationSection("Economy") == null){
			Util.generateEconomyConfig();
		}
		if(Main.levelUpsAndPowersConfig.getConfigurationSection("Level_Unlocks") == null){
			Util.generateLevelUpsAndPowersConfig();
		}
		if(Main.chatConfig.getConfigurationSection("Chat") == null){
			Util.generateChatConfig();
		}
		if(Main.politicsAndWarConfig.getConfigurationSection("Politics") == null){
			Util.generatePoliticsAndWarConfig();
		}
		if(Main.landControlConfig.getConfigurationSection("Land_Control") == null){
			Util.generateLandControlConfig();
		}

		getLogger().info("[-- ATTEMPTING TO SET UP ECONOMY NOW --]");
		if (!setupEconomy())
		{
			getLogger().info("[###### Kingdoms was unable to hook into Vault Economy ######]");
			economyConfig.set("Economy.Currency_Enabled", "false");
			saveEconomyConfigYaml();
			return;
		}
		if (!setupPermissions())
		{
			getLogger().info("[###### Kingdoms was unable to hook into Vault Permissions ######]");
			Main.levelUpsAndPowersConfig.set("Permission_Node_Unlocks.Enabled", "false");
			return;
		}
	}
	
	public void onDisable(){
		saveAllYamls();
		getLogger().info("[###### Kingdoms Disabled Successfully ######]");
	}
	
	public static void saveAllYamls(){
		try{
			//Database files
			players.save(playersFile);
			kingdoms.save(kingdomsFile);
			landcontrol.save(landControlFile);
			
			//Config files
			mainConfig.save(mainConfigFile);
			economyConfig.save(economyConfigFile);
			levelUpsAndPowersConfig.save(levelUpsAndPowersConfigFile);
			chatConfig.save(chatConfigFile);
			politicsAndWarConfig.save(politicsAndWarConfigFile);
			landControlConfig.save(landControlConfigFile);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void savePlayersYaml(){
		try{
			players.save(playersFile);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void saveKingdomsYaml(){
		try{
			kingdoms.save(kingdomsFile);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void saveLandControlYaml(){
		try{
			landcontrol.save(landControlFile);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void saveMainConfigYaml(){
		try{
			mainConfig.save(mainConfigFile);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void saveEconomyConfigYaml(){
		try{
			economyConfig.save(economyConfigFile);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void saveLevelUpsAndPowersConfigYaml(){
		try{
			levelUpsAndPowersConfig.save(levelUpsAndPowersConfigFile);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void saveChatConfigYaml(){
		try{
			chatConfig.save(chatConfigFile);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void savePoliticsAndWarConfigYaml(){
		try{
			politicsAndWarConfig.save(politicsAndWarConfigFile);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void saveLandControlConfigYaml(){
		try{
			landControlConfig.save(landControlConfigFile);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void reloadYamls(){
		try{
			players.load(playersFile);
			kingdoms.load(kingdomsFile);
			landcontrol.load(landControlFile);
			mainConfig.load(mainConfigFile);
			economyConfig.load(economyConfigFile);
			levelUpsAndPowersConfig.load(levelUpsAndPowersConfigFile);
			chatConfig.load(chatConfigFile);
			politicsAndWarConfig.load(politicsAndWarConfigFile);
			landControlConfig.load(landControlConfigFile);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void firstRun() throws Exception{
	    if(!playersFile.exists()){
	        playersFile.getParentFile().mkdirs();
	        copy(getResource("Players.yml"), playersFile);
	    }
	    if(!kingdomsFile.exists()){
	        kingdomsFile.getParentFile().mkdirs();
	        copy(getResource("Kingdoms.yml"), kingdomsFile);
	    }
	    if(!landControlFile.exists()){
	        landControlFile.getParentFile().mkdirs();
	        copy(getResource("LandControl.yml"), landControlFile);
	    }
	    if(!mainConfigFile.exists()){
	        mainConfigFile.getParentFile().mkdirs();
	        copy(getResource("Main_Config.yml"), mainConfigFile);
	    }
	    if(!economyConfigFile.exists()){
	        economyConfigFile.getParentFile().mkdirs();
	        copy(getResource("Economy_Config.yml"), economyConfigFile);
	    }
	    if(!levelUpsAndPowersConfigFile.exists()){
	    	levelUpsAndPowersConfigFile.getParentFile().mkdirs();
	        copy(getResource("Level_Ups_And_Powers_Config.yml"), levelUpsAndPowersConfigFile);
	    }
	    if(!chatConfigFile.exists()){
	    	chatConfigFile.getParentFile().mkdirs();
	        copy(getResource("Chat_Config.yml"), chatConfigFile);
	    }
	    if(!politicsAndWarConfigFile.exists()){
	    	politicsAndWarConfigFile.getParentFile().mkdirs();
	        copy(getResource("Politics_And_War_Config.yml"), politicsAndWarConfigFile);
	    }
	    if(!landControlConfigFile.exists()){
	    	landControlConfigFile.getParentFile().mkdirs();
	        copy(getResource("Land_Control_Config.yml"), landControlConfigFile);
	    }
	}
	
	private void copy(InputStream in, File file) {
	    try {
	        OutputStream out = new FileOutputStream(file);
	        buf = new byte[1024];
	        while((len=in.read(buf))>0){
	            out.write(buf,0,len);
	        }
	        out.close();
	        in.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

    private boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }
    private boolean setupEconomy()
	{
		if (getServer().getPluginManager().getPlugin("Vault") == null)
		{
			getLogger().info("[-- VAULT RETURNED NULL, IT DOESNT EXIST, NOT SETTING UP ECONOMY --]");
			return false;
		}
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider == null)
		{
			getLogger().info("[-- ECONOMYPROVIDER RETURNED NULL, IT DOESNT EXIST, NOT SETTING UP ECONOMY --]");
			return false;
		}
		economy = economyProvider.getProvider();
		return economy != null;
	}
}