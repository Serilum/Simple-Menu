package com.natamus.simplemenu.mixin;

import com.natamus.simplemenu.config.ConfigHandler;
import com.natamus.simplemenu.data.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiGraphicsExtractor.class, priority = 1001)
public class GuiGraphicsExtractorMixin {
	@Inject(method = "centeredText(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V", at = @At(value = "HEAD"), cancellable = true)
	public void centeredText(Font font, Component component, int x, int y, int color, CallbackInfo ci) {
		if (!(Minecraft.getInstance().screen instanceof TitleScreen)) {
			return;
		}

		if (ConfigHandler.removeExperimentalModLoaderText) {
			if (component.toString().contains(".update.beta.")) {
				ci.cancel();
			}
		}
	}

	@Inject(method = "text(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)V", at = @At(value = "HEAD"), cancellable = true)
	public void text(Font font, String text, int x, int y, int color, boolean dropShadow, CallbackInfo ci) {
		if (!(Minecraft.getInstance().screen instanceof TitleScreen)) {
			return;
		}

		if (ConfigHandler.removeTextBottomLeft) {
			if (Constants.bottomLeftTextIgnore.stream().anyMatch(text::contains)) {
				ci.cancel();
			}
		}
	}
}
