package com.natamus.simplemenu.mixin;

import com.natamus.simplemenu.config.ConfigHandler;
import com.natamus.simplemenu.data.Buttons;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TitleScreen.class, priority = 1001)
public abstract class TitleScreenMixin extends Screen {
	protected TitleScreenMixin(Component $$0) {
		super($$0);
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
