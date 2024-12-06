package com.natamus.simplemenu.mixin;

import com.natamus.simplemenu.config.ConfigHandler;
import net.minecraft.client.resources.SplashManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;

@Mixin(value = SplashManager.class, priority = 1001)
public class SplashManagerMixin {
	@Inject(method = "prepare(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)Ljava/util/List;", at = @At(value = "HEAD"), cancellable = true)
	protected void prepare(ResourceManager $$0, ProfilerFiller $$1, CallbackInfoReturnable<List<String>> cir) {
		if (ConfigHandler.hideSplashText) {
			cir.setReturnValue(Collections.emptyList());
		}
	}
}
