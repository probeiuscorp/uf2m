package io.github.probeiuscorp.uf2m

import com.mojang.logging.LogUtils
import io.github.probeiuscorp.uf2m.UF2M
import io.github.probeiuscorp.uf2m.alloy.AlloyArmorItem
import io.github.probeiuscorp.uf2m.crafters.inventor.InventorBlock
import io.github.probeiuscorp.uf2m.data.UF2MRegistrySets
import io.github.probeiuscorp.uf2m.data.dimension.RegisterDimensions
import net.minecraft.client.Minecraft
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.event.CreativeModeTabEvent.BuildContents
import net.minecraftforge.event.server.ServerStartingEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegisterEvent
import net.minecraftforge.registries.RegistryObject
import org.slf4j.Logger
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.MOD_CONTEXT

// The value here should match an entry in the META-INF/mods.toml file
@Mod(UF2M.MODID)
object UF2M {
  // Define mod id in a common place for everything to reference
  const val MODID: String = "uf2m"

  // Directly reference a slf4j logger
  private val LOGGER: Logger = LogUtils.getLogger()

  // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
  val BLOCKS: DeferredRegister<Block> = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID)

  // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
  val ITEMS: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, MODID)

  // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
  val INVENTOR_BLOCK: RegistryObject<Block> = BLOCKS.register(
    "inventor"
  ) { InventorBlock(BlockBehaviour.Properties.of(Material.STONE)) }

  // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
  val INVENTOR_BLOCK_ITEM: RegistryObject<Item> = ITEMS.register(
    "inventor"
  ) { BlockItem(INVENTOR_BLOCK.get(), Item.Properties()) }
  val ALLOY_HELMET: RegistryObject<Item> = ITEMS.register(
    "alloy_helmet"
  ) { AlloyArmorItem(ArmorItem.Type.HELMET) }
  val ALLOY_CHESTPLATE: RegistryObject<Item> = ITEMS.register(
    "alloy_chestplate"
  ) { AlloyArmorItem(ArmorItem.Type.CHESTPLATE) }
  val ALLOY_LEGGINGS: RegistryObject<Item> = ITEMS.register(
    "alloy_leggings"
  ) { AlloyArmorItem(ArmorItem.Type.LEGGINGS) }
  val ALLOY_BOOTS: RegistryObject<Item> = ITEMS.register(
    "alloy_boots"
  ) { AlloyArmorItem(ArmorItem.Type.BOOTS) }

  init {
    // Register the commonSetup method for modloading
    MOD_BUS.addListener(::commonSetup)
    MOD_BUS.addListener(::dataSetup)
    MOD_BUS.addListener(::registerBiomeSource)

    // Register the Deferred Register to the mod event bus so blocks get registered
    BLOCKS.register(MOD_BUS)
    // Register the Deferred Register to the mod event bus so items get registered
    ITEMS.register(MOD_BUS)

    // Register ourselves for server and other game events we are interested in
    MinecraftForge.EVENT_BUS.register(this)

    // Register the item to a creative tab
    MOD_BUS.addListener(::addCreative)
  }

  fun registerBiomeSource(event: RegisterEvent) {
    val key = event.registryKey
    if (key == Registries.BIOME_SOURCE) {
      RegisterDimensions.bootstrapBiomeSources()
    }
  }

  private fun commonSetup(event: FMLCommonSetupEvent) {
    // Some common setup code
    LOGGER.info("HELLO FROM COMMON SETUP")
    LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT))
  }

  private fun dataSetup(event: GatherDataEvent) {
    val generator = event.generator
    val packOutput = generator.packOutput
    val lookupProvider = event.lookupProvider

    LOGGER.info("Should be registering dimensions")
    generator.addProvider(event.includeServer(), UF2MRegistrySets(packOutput, lookupProvider))
  }

  private fun addCreative(event: BuildContents) {
    if (event.tab === CreativeModeTabs.BUILDING_BLOCKS) {
      event.accept(INVENTOR_BLOCK)
    }
  }

  // You can use SubscribeEvent and let the Event Bus discover methods to call
  @SubscribeEvent
  fun onServerStarting(event: ServerStartingEvent?) {
    // Do something when the server starts
    LOGGER.info("HELLO from server starting")
  }

  // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
  @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
  object ClientModEvents {
    @SubscribeEvent
    fun onClientSetup(event: FMLClientSetupEvent?) {
      // Some client setup code
      LOGGER.info("HELLO FROM CLIENT SETUP")
      LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().user.name)
    }
  }
}
