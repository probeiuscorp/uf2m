package io.github.probeiuscorp.uf2m.crafters.inventor;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import io.github.probeiuscorp.uf2m.UF2M;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class InventorScreen extends Screen {
	private static final ResourceLocation BACKGROUND = new ResourceLocation(UF2M.MODID, "textures/gui/container/inventor.png");
	
	public InventorScreen(Component title) {
		super(title);
	}

	@Override
	public void render(@Nonnull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
		this.renderBackground(pPoseStack);
		RenderSystem.setShaderTexture(0, BACKGROUND);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		
		int xSize = 256;
		int ySize = 222;
		int x = (this.width - xSize) / 2;
		int y = (this.height - ySize) / 2;
		
		this.blit(pPoseStack, x, y, 0, 0, xSize, ySize);
		
		super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
	}
}
