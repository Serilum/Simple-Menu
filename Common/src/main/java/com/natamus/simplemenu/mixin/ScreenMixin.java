package com.natamus.simplemenu.mixin;

import com.natamus.simplemenu.config.ConfigHandler;
import com.natamus.simplemenu.data.Buttons;
import com.natamus.simplemenu.data.Constants;
import com.natamus.simplemenu.util.Util;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Screen.class, priority = 1001)
public abstract class ScreenMixin {
	@Shadow protected abstract <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T $$0);

	@Inject(method = "addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;", at = @At(value = "HEAD"))
	protected <T extends GuiEventListener & Renderable & NarratableEntry> void addRenderableWidget(T renderableWidget, CallbackInfoReturnable<T> cir) {
		Screen screen = (Screen)(Object)this;
		if (!(screen instanceof TitleScreen)) {
			return;
		}

		if (renderableWidget instanceof Button) {
			Button button = (Button)renderableWidget;
			if ((button.getMessage().equals(Constants.realmsButtonComponent))) {
				if (ConfigHandler.hideMinecraftRealmsButton) {
					button.visible = false;
				}

				if (ConfigHandler.enableServerPromoButton) {
					Buttons.serverPromoButton = (Button)addRenderableWidget(Button.builder(Component.literal(ConfigHandler.serverPromoButtonTextDefault), (f) -> {
						Util.openUrl(ConfigHandler.serverPromoButtonClickURL);
					}).bounds(button.getX(), button.getY(), button.getWidth(), button.getHeight()).build());
				}
			}
		}
	}
}
