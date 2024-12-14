package io.github.probeiuscorp.uf2m.crafters.inventor

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import io.github.probeiuscorp.uf2m.UF2M
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class InventorScreen(title: Component) : Screen(title) {
  override fun render(pPoseStack: PoseStack, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
    this.renderBackground(pPoseStack)
    RenderSystem.setShaderTexture(0, BACKGROUND)
    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)

    val xSize = 256
    val ySize = 222
    val x = (this.width - xSize) / 2
    val y = (this.height - ySize) / 2

    blit(pPoseStack, x, y, 0, 0, xSize, ySize)

    super.render(pPoseStack, pMouseX, pMouseY, pPartialTick)
  }

  companion object {
    private val BACKGROUND = ResourceLocation(UF2M.MODID, "textures/gui/container/inventor.png")
  }
}
