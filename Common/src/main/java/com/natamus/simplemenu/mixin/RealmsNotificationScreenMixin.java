package com.natamus.simplemenu.mixin;

import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;
import com.natamus.simplemenu.config.ConfigHandler;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RealmsNotificationsScreen.class, priority = 1001)
public class RealmsNotificationScreenMixin {
	@Inject(method = "drawIcons(Lnet/minecraft/client/gui/GuiGraphics;)V", at = @At(value = "HEAD"), cancellable = true)
	private void drawIcons(GuiGraphics guiGraphics, CallbackInfo ci) {
		if (ConfigHandler.hideMinecraftRealmsButton || ConfigHandler.enableServerPromoButton) {
			ci.cancel();
		}
	}
}
