package com.natamus.simplemenu;

import com.natamus.collective.globalcallbacks.MainMenuLoadedCallback;
import com.natamus.collective.services.Services;
import com.natamus.simplemenu.config.ConfigHandler;
import com.natamus.simplemenu.data.Constants;
import com.natamus.simplemenu.util.Reference;
import com.natamus.simplemenu.util.Util;

public class ModCommon {

	public static void init() {
		ConfigHandler.initConfig();
		load();
	}

	private static void load() {
		if (!Services.MODLOADER.isClientSide()) {
			return;
		}

		Util.init();

		MainMenuLoadedCallback.MAIN_MENU_LOADED.register(() -> {
			try {
				Util.initTextureData();
			}
			catch (Exception ex) {
				Constants.logger.warn("[" + Reference.NAME + "] Something went wrong while initiating the mod.");
				ex.printStackTrace();
			}
		});
	}
}