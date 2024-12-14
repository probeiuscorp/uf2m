package io.github.probeiuscorp.uf2m.crafters.inventor

import com.mojang.logging.LogUtils
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import org.slf4j.Logger

class InventorBlock(properties: Properties) : Block(properties) {
  override fun use(
    state: BlockState,
    level: Level,
    pos: BlockPos,
    player: Player,
    hand: InteractionHand,
    hit: BlockHitResult
  ): InteractionResult {
    LOGGER.info("Inventor used")

    if (level.isClientSide()) {
      LOGGER.info("Client side")
      Minecraft.getInstance().setScreen(InventorScreen(Component.literal("test test test")))
    } else {
      LOGGER.info("Server side")
    }

    return InteractionResult.SUCCESS
  }

  companion object {
    private val LOGGER: Logger = LogUtils.getLogger()
  }
}
