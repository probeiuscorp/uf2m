package io.github.probeiuscorp.uf2m;

import com.mojang.logging.LogUtils;

import io.github.probeiuscorp.uf2m.alloy.AlloyArmorItem;
import io.github.probeiuscorp.uf2m.crafters.inventor.InventorBlock;
import io.github.probeiuscorp.uf2m.data.dimension.RegisterDimensions;
import io.github.probeiuscorp.uf2m.data.UF2MRegistrySets;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(UF2M.MODID)
public class UF2M {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "uf2m";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Block> INVENTOR_BLOCK = BLOCKS.register("inventor", () -> new InventorBlock(BlockBehaviour.Properties.of(Material.STONE)));
    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Item> INVENTOR_BLOCK_ITEM = ITEMS.register("inventor", () -> new BlockItem(INVENTOR_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALLOY_HELMET = ITEMS.register("alloy_helmet", () -> new AlloyArmorItem(ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> ALLOY_CHESTPLATE = ITEMS.register("alloy_chestplate", () -> new AlloyArmorItem(ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> ALLOY_LEGGINGS = ITEMS.register("alloy_leggings", () -> new AlloyArmorItem(ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> ALLOY_BOOTS = ITEMS.register("alloy_boots", () -> new AlloyArmorItem(ArmorItem.Type.BOOTS));

    public UF2M() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::dataSetup);
        modEventBus.addListener(this::registerBiomeSource);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
    }

    public void registerBiomeSource(RegisterEvent event) {
        ResourceKey<? extends Registry> key = event.getRegistryKey();
        if(key.equals(Registries.BIOME_SOURCE)) {
            RegisterDimensions.bootstrapBiomeSources();
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }
    
    private void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        
        LOGGER.info("Should be registering dimensions");
        generator.addProvider(event.includeServer(), new UF2MRegistrySets(packOutput, lookupProvider));
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(INVENTOR_BLOCK);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }

//    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
//    public static class ModEventSubscriber {
//        @SubscribeEvent
//        public static void onRegisterBiomes() {
//
//        }
//    }
}
