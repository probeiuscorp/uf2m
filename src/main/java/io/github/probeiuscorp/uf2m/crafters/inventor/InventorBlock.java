package io.github.probeiuscorp.uf2m.crafters.inventor;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class InventorBlock extends Block {
	private static final Logger LOGGER = LogUtils.getLogger();
	
	public InventorBlock(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		LOGGER.info("Inventor used");
		
		if(level.isClientSide()) {
			LOGGER.info("Client side");
			Minecraft.getInstance().setScreen(new InventorScreen(Component.literal("test test test")));
		} else {
			LOGGER.info("Server side");
		}
		
		return InteractionResult.SUCCESS;
	}
}
