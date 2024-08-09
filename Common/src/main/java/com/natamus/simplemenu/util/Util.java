package com.natamus.simplemenu.util;

import com.mojang.blaze3d.platform.IconSet;
import com.mojang.blaze3d.platform.NativeImage;
import com.natamus.collective.functions.DataFunctions;
import com.natamus.simplemenu.config.ConfigHandler;
import com.natamus.simplemenu.data.Constants;
import com.natamus.simplemenu.data.Variables;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.IoSupplier;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {
	private static final String rootConfigPath = DataFunctions.getConfigDirectory() + File.separator + Reference.MOD_ID;
	private static final String backgroundPath = rootConfigPath + File.separator + "background";
	private static final String iconPath = rootConfigPath + File.separator + "icon";
	private static final String logoPath = rootConfigPath + File.separator + "logo";

	private static final File rootConfigDir = new File(rootConfigPath);
	private static final File backgroundDir = new File(backgroundPath);
	private static final File iconDir = new File(iconPath);
	private static final File logoDir = new File(logoPath);

	public static void init() {
		Constants.logger.info(Constants.logPrefix + "Initiating.");

		if (!rootConfigDir.isDirectory()) { rootConfigDir.mkdirs(); }
		if (!backgroundDir.isDirectory()) { backgroundDir.mkdirs(); }
		if (!iconDir.isDirectory()) { iconDir.mkdirs(); }
		if (!logoDir.isDirectory()) { logoDir.mkdirs(); }
	}

	public static void initTextureData() throws IOException {
		Constants.logger.info(Constants.logPrefix + "Loading texture data.");

		String logoFilePath = logoPath + File.separator + "logo.png";
		File logoFile = new File(logoFilePath);
		if (!logoFile.exists()) {
			InputStream defaultLogoInputStream = Constants.mc.getResourceManager().open(ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/title/minecraft.png"));

			BufferedImage defaultLogoBufferedImage = ImageIO.read(defaultLogoInputStream);
			ImageIO.write(defaultLogoBufferedImage, "png", logoFile);

			Constants.logger.warn(Constants.logPrefix + "Copied the default Minecraft logo to: " + logoFilePath);
		}
		if (ConfigHandler.replaceMainMenuLogo) {
			InputStream logoInputStream = new FileInputStream(logoFile);
			NativeImage logoNativeImage = NativeImage.read(logoInputStream);
			DynamicTexture logoTexture = new DynamicTexture(logoNativeImage);

			Constants.mc.getTextureManager().register(ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, "logo"), logoTexture);

			logoNativeImage.close();

			Variables.loadedLogoImage = true;
		}


		String logoEditionFilePath = logoPath + File.separator + "edition.png";
		File logoEditionFile = new File(logoEditionFilePath);
		if (!logoEditionFile.exists()) {
			InputStream defaultEditionInputStream = Constants.mc.getResourceManager().open(ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/title/edition.png"));

			BufferedImage defaultEditionBufferedImage = ImageIO.read(defaultEditionInputStream);
			ImageIO.write(defaultEditionBufferedImage, "png", logoEditionFile);

			Constants.logger.warn(Constants.logPrefix + "Copied default Edition logo to: " + logoEditionFilePath);
		}
		if (ConfigHandler.replaceMainMenuEditionLogo) {
			InputStream logoEditionInputStream = new FileInputStream(logoEditionFile);
			NativeImage logoEditionNativeImage = NativeImage.read(logoEditionInputStream);
			DynamicTexture logoEditionTexture = new DynamicTexture(logoEditionNativeImage);

			Constants.mc.getTextureManager().register(ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, "edition"), logoEditionTexture);

			logoEditionNativeImage.close();

			Variables.loadedLogoEditionImage = true;
		}


		String backgroundFilePath = backgroundPath + File.separator + "background.png";
		File backgroundFile = new File(backgroundFilePath);
		if (!backgroundFile.exists()) {
			BufferedImage emptyBackgroundImage = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);

			Graphics2D g2d = emptyBackgroundImage.createGraphics();
			g2d.setComposite(AlphaComposite.Clear);
			g2d.fillRect(0, 0, 1920, 1080);

			ImageIO.write(emptyBackgroundImage, "png", backgroundFile);

			Constants.logger.warn(Constants.logPrefix + "Generated a default background in: " + backgroundFilePath);
		}

		if (ConfigHandler.setCustomBackground) {
			InputStream backgroundInputStream = new FileInputStream(backgroundFile);
			NativeImage backgroundNativeImage = NativeImage.read(backgroundInputStream);
			DynamicTexture backgroundTexture = new DynamicTexture(backgroundNativeImage);

			Constants.mc.getTextureManager().register(ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, "background"), backgroundTexture);

			backgroundNativeImage.close();

			Variables.loadedBackgroundImage = true;
		}
	}

	public static void openUrl(String url) {
		try {
			net.minecraft.Util.getPlatform().openUri(new URI(url));
		} catch (URISyntaxException ex) {
			Constants.logger.warn(Constants.logPrefix + "Unable to open custom URL: " + url);
		}
	}

	public static void processCustomWindowIcon() {
		try {
			File icon16x16 = new File(iconPath + File.separator + "/icon_16x16.png");
			File icon32x32 = new File(iconPath + File.separator + "/icon_32x32.png");

			if (!icon16x16.exists() || !icon32x32.exists()) {
				int n = 0;

				List<IoSupplier<InputStream>> defaultIconList = IconSet.RELEASE.getStandardIcons(Constants.mc.getVanillaPackResources());
				for (IoSupplier<InputStream> defaultIcon : defaultIconList) {
					InputStream iconInputStream = defaultIcon.get();

					BufferedImage defaultIconBufferedImage = ImageIO.read(iconInputStream);

					if (n == 0) {
						ImageIO.write(defaultIconBufferedImage, "png", icon16x16);
					}
					else if (n == 1) {
						ImageIO.write(defaultIconBufferedImage, "png", icon32x32);
					}
					else {
						break;
					}

					n+=1;
				}

				Constants.logger.info(Constants.logPrefix + "No custom icons found. Copied default to: " + iconPath);
				return;
			}

			if (!ConfigHandler.setCustomWindowIcon) {
				return;
			}


			BufferedImage bi16x16 = ImageIO.read(icon16x16);
			BufferedImage bi32x32 = ImageIO.read(icon32x32);

			if ((bi16x16.getHeight() != 16) || (bi16x16.getWidth() != 16) || (bi32x32.getHeight() != 32) || (bi32x32.getWidth() != 32)) {
				Constants.logger.warn(Constants.logPrefix + "Custom window icon enabled, but icon images have incorrect dimensions.");
				return;
			}

			List<IoSupplier<InputStream>> iconList = Arrays.asList(IoSupplier.create(icon16x16.toPath()), IoSupplier.create(icon32x32.toPath()));
			List<ByteBuffer> list1 = new ArrayList<>(iconList.size());

			try (MemoryStack memorystack = MemoryStack.stackPush()) {
				GLFWImage.Buffer buffer = GLFWImage.malloc(iconList.size(), memorystack);

				for (int i = 0; i < iconList.size(); ++i) {
					try (NativeImage nativeimage = NativeImage.read(iconList.get(i).get())) {
						ByteBuffer bytebuffer = MemoryUtil.memAlloc(nativeimage.getWidth() * nativeimage.getHeight() * 4);
						list1.add(bytebuffer);
						bytebuffer.asIntBuffer().put(nativeimage.getPixelsRGBA());
						buffer.position(i);
						buffer.width(nativeimage.getWidth());
						buffer.height(nativeimage.getHeight());
						buffer.pixels(bytebuffer);
					}
				}

				GLFW.glfwSetWindowIcon(Constants.mc.getWindow().getWindow(), buffer);
			} finally {
				list1.forEach(MemoryUtil::memFree);
			}

			Variables.loadedIconImage = true;
		}
		catch (IOException ex) {
			Constants.logger.warn(Constants.logPrefix + "IOException when setting the custom window icon.");
		}
	}
}
