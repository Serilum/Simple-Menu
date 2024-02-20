package com.natamus.simplemenu.mixin;

import com.natamus.simplemenu.config.ConfigHandler;
import com.natamus.simplemenu.data.Buttons;
import com.natamus.simplemenu.data.Variables;
import com.natamus.simplemenu.util.Reference;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TitleScreen.class, priority = 1001)
public abstract class TitleScreenMixin extends Screen {
	@Shadow private static @Final @Mutable ResourceLocation PANORAMA_OVERLAY;

	protected TitleScreenMixin(Component $$0) {
		super($$0);
	}

	@Inject(method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At(value = "HEAD"))
	public void render(GuiGraphics $$0, int $$1, int $$2, float $$3, CallbackInfo ci) {
		if (ConfigHandler.setCustomBackground && Variables.loadedBackgroundImage && !Variables.setBackgroundResourceLocation) {
			PANORAMA_OVERLAY = new ResourceLocation(Reference.MOD_ID, "background");
			Variables.setBackgroundResourceLocation = true;
		}
	}

	@Inject(method = "tick()V", at = @At(value = "TAIL"))
	public void tick(CallbackInfo ci) {
		if (Buttons.serverPromoButton != null) {
			if (Buttons.serverPromoButton.isHovered()) {
				Buttons.serverPromoButton.setMessage(Component.literal(ConfigHandler.serverPromoButtonTextOnHover));
			} else {
				Buttons.serverPromoButton.setMessage(Component.literal(ConfigHandler.serverPromoButtonTextDefault));
			}
		}
	}
}
