package com.natamus.simplemenu.mixin;

import com.natamus.simplemenu.config.ConfigHandler;
import com.natamus.simplemenu.data.Constants;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GuiGraphics.class, priority = 1001)
public class GuiGraphicsMixin {
	@Inject(method = "drawCenteredString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V", at = @At(value = "HEAD"), cancellable = true)
	public void drawCenteredString(Font font, Component component, int x, int y, int a, CallbackInfo ci) {
		if (!(Constants.mc.screen instanceof TitleScreen)) {
			return;
		}

		if (ConfigHandler.removeExperimentalModLoaderText) {
			if (component.toString().contains(".update.beta.")) {
				ci.cancel();
			}
		}
	}

	@Inject(method = "drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I", at = @At(value = "HEAD"), cancellable = true)
	public void drawString(Font font, String text, int x, int y, int a, boolean b, CallbackInfoReturnable<Integer> cir) {
		if (!(Constants.mc.screen instanceof TitleScreen)) {
			return;
		}

		if (ConfigHandler.removeTextBottomLeft) {
			if (Constants.bottomLeftTextIgnore.stream().anyMatch(text::contains)) {
				cir.setReturnValue(0);
			}
		}
	}
}
