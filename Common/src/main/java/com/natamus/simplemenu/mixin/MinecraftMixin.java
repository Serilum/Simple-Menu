package com.natamus.simplemenu.mixin;

import com.natamus.simplemenu.config.ConfigHandler;
import com.natamus.simplemenu.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Minecraft.class, priority = 1001)
public class MinecraftMixin {
	@Inject(method = "<init>(Lnet/minecraft/client/main/GameConfig;)V", at = @At(value = "RETURN"))
	private void init(GameConfig gameConfig, CallbackInfo ci) {
		Util.processCustomWindowIcon();
	}

	@Inject(method = "createTitle()Ljava/lang/String;", at = @At(value = "HEAD"), cancellable = true)
	private void createTitle(CallbackInfoReturnable<String> cir) {
		if (ConfigHandler.setCustomWindowTitle) {
			cir.setReturnValue(ConfigHandler.customWindowTitle);
		}
	}
}
