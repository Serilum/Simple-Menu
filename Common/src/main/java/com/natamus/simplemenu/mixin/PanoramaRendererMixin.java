package com.natamus.simplemenu.mixin;

import com.natamus.simplemenu.config.ConfigHandler;
import com.natamus.simplemenu.data.Variables;
import com.natamus.simplemenu.util.Reference;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PanoramaRenderer.class, priority = 1001)
public abstract class PanoramaRendererMixin {
	@Shadow public static @Final @Mutable ResourceLocation PANORAMA_OVERLAY;

	@Inject(method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIFF)V", at = @At(value = "HEAD"))
	public void render(GuiGraphics $$0, int $$1, int $$2, float $$3, float $$4, CallbackInfo ci) {
		if (ConfigHandler.setCustomBackground && Variables.loadedBackgroundImage && !Variables.setBackgroundResourceLocation) {
			PanoramaRendererMixin.PANORAMA_OVERLAY = ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, "background");
			Variables.setBackgroundResourceLocation = true;
		}
	}
}
