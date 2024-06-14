package com.natamus.simplemenu.config;

import com.natamus.collective.config.DuskConfig;
import com.natamus.simplemenu.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry public static boolean setCustomWindowTitle = false;
	@Entry public static String customWindowTitle = "Custom Window Title";

	@Entry public static boolean setCustomWindowIcon = false;

	@Entry public static boolean replaceMainMenuLogo = false;
	@Entry public static boolean replaceMainMenuEditionLogo = false;

	@Entry public static boolean setCustomBackground = false;

	@Entry public static boolean hideMinecraftRealmsButton = true;
	@Entry public static boolean enableServerPromoButton = false;
	@Entry public static String serverPromoButtonTextDefault = "Need a Server?";
	@Entry public static String serverPromoButtonTextOnHover = "Use code PROMO for 25% off";
	@Entry public static String serverPromoButtonClickURL = "https://localhost/exampleurl";

	@Entry public static boolean hideSplashText = false;
	@Entry public static boolean removeExperimentalModLoaderText = false;
	@Entry public static boolean removeTextBottomLeft = false;

	public static void initConfig() {
		configMetaData.put("setCustomWindowTitle", Arrays.asList(
			"Whether a custom window title should be set. Uses the value set in 'customWindowTitle'."
		));
		configMetaData.put("customWindowTitle", Arrays.asList(
			"The custom window title that's used when 'setCustomWindowTitle' is enabled."
		));

		configMetaData.put("setCustomWindowIcon", Arrays.asList(
			"If a custom window icon should be set. Two files are needed. Both in PNG format. One a 16 pixel square called 'icon_16x16.png', and one a 32 pixel square called 'icon_32x32.png'. Located in './config/simplemenu/icon'."
		));

		configMetaData.put("replaceMainMenuLogo", Arrays.asList(
			"If a custom main menu logo should be rendered. This will replace the 'Minecraft' text. A 'logo.png' file is needed. Located in './config/simplemenu/logo'. By default the image dimensions are 1024x256. To remove it completely, use an empty .png file."
		));
		configMetaData.put("replaceMainMenuEditionLogo", Arrays.asList(
			"If a custom main menu 'edition' logo should be rendered. This will replace the 'Java Edition' text. An 'edition.png' file is needed. Located in './config/simplemenu/logo'. By default the image dimensions are 512x64. To remove it completely, use an empty .png file."
		));

		configMetaData.put("setCustomBackground", Arrays.asList(
			"If a custom background should be rendered. A 'background.png' file is needed. Located in './config/simplemenu/background/'."
		));

		configMetaData.put("hideMinecraftRealmsButton", Arrays.asList(
			"If the Minecraft Realms button should be hidden from the main menu. Enabled by default, since modded Minecraft is incompatible with it."
		));
		configMetaData.put("enableServerPromoButton", Arrays.asList(
			"Whether the Minecraft Realms button should be replaced by a serverhosting promo button."
		));
		configMetaData.put("serverPromoButtonTextDefault", Arrays.asList(
			"The default server promo button text."
		));
		configMetaData.put("serverPromoButtonTextOnHover", Arrays.asList(
			"The server promo button text shown when hovering it."
		));
		configMetaData.put("serverPromoButtonClickURL", Arrays.asList(
			"The URL that's being navigated to when clicking the server promo button."
		));

		configMetaData.put("hideSplashText", Arrays.asList(
			"Whether the splash text should be hidden on the main menu."
		));
		configMetaData.put("removeExperimentalModLoaderText", Arrays.asList(
			"Whether experimental/beta mod loader text should be removed from the main menu."
		));
		configMetaData.put("removeTextBottomLeft", Arrays.asList(
			"Whether the text in the bottom left should be removed from the main menu."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}