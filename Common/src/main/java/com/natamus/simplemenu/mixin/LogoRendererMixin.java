package com.natamus.simplemenu.mixin;

import com.natamus.simplemenu.config.ConfigHandler;
import com.natamus.simplemenu.data.Variables;
import com.natamus.simplemenu.util.Reference;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LogoRenderer.class, priority = 1001)
public class LogoRendererMixin {
    @Shadow public static @Final @Mutable ResourceLocation MINECRAFT_LOGO;
    @Shadow public static @Final @Mutable ResourceLocation EASTER_EGG_LOGO;
    @Shadow public static @Final @Mutable ResourceLocation MINECRAFT_EDITION;

	@Inject(method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V", at = @At(value = "HEAD"))
	public void renderLogo(GuiGraphics $$0, int $$1, float $$2, int $$3, CallbackInfo ci) {
		if (ConfigHandler.replaceMainMenuLogo && Variables.loadedLogoImage) {
			MINECRAFT_LOGO = new ResourceLocation(Reference.MOD_ID, "logo");
			EASTER_EGG_LOGO = new ResourceLocation(Reference.MOD_ID, "logo");
		}

		if (ConfigHandler.replaceMainMenuEditionLogo && Variables.loadedLogoEditionImage) {
			MINECRAFT_EDITION = new ResourceLocation(Reference.MOD_ID, "edition");
		}
	}
}
