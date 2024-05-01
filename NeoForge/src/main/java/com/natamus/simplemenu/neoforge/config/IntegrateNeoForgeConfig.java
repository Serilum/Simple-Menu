package com.natamus.simplemenu.neoforge.config;

import com.natamus.collective.config.DuskConfig;
import com.natamus.simplemenu.util.Reference;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class IntegrateNeoForgeConfig {
	public static void registerScreen(ModLoadingContext modLoadingContext) {
		modLoadingContext.registerExtensionPoint(IConfigScreenFactory.class, () -> (mc, screen) -> {
			return DuskConfig.DuskConfigScreen.getScreen(screen, Reference.MOD_ID);
		});
	}
}
