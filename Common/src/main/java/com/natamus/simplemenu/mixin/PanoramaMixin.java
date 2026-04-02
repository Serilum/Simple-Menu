package com.natamus.simplemenu.mixin;

import com.natamus.simplemenu.config.ConfigHandler;
import com.natamus.simplemenu.data.Variables;
import com.natamus.simplemenu.util.Reference;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.Panorama;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Panorama.class, priority = 1001)
public abstract class PanoramaMixin {
	@Shadow public static @Final @Mutable Identifier PANORAMA_OVERLAY;

	@Inject(method = "extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIZ)V", at = @At(value = "HEAD"))
	public void render(GuiGraphicsExtractor guiGraphics, int i, int j, boolean b, CallbackInfo ci) {
		if (ConfigHandler.setCustomBackground && Variables.loadedBackgroundImage && !Variables.setBackgroundIdentifier) {
			PanoramaMixin.PANORAMA_OVERLAY = Identifier.fromNamespaceAndPath(Reference.MOD_ID, "background");
			Variables.setBackgroundIdentifier = true;
		}
	}
}
