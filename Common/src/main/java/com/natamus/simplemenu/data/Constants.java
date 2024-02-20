package com.natamus.simplemenu.data;

import com.mojang.logging.LogUtils;
import com.natamus.simplemenu.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;

public class Constants {
	public static final Logger logger = LogUtils.getLogger();
	public static final String logPrefix = "[" + Reference.NAME + "] ";

	public static final Minecraft mc = Minecraft.getInstance();

	public static final Component realmsButtonComponent = Component.translatable("menu.online");
	public static final List<String> bottomLeftTextIgnore = Arrays.asList("Minecraft", "Fabric", "Forge", "NeoForge", "MCP", "mods");
}
