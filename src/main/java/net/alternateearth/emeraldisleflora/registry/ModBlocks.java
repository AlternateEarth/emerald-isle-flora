package net.alternateearth.emeraldisleflora.registry;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
/*? if <26.2 {*/
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.WoodType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HangingSignItem;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
/*?} else {*/
/*import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
*/
/*?}*/
/*? if forge {*/
/*import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
*/
/*?}*/
/*? if neoforge {*/
/*import net.neoforged.neoforge.registries.RegisterEvent;
*/
/*?}*/
/*? if fabric {*/
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
/*?}*/

/*? if <26.2 {*/
import static net.minecraft.block.Blocks.*;
/*?} else {*/
/*import static net.minecraft.world.level.block.Blocks.*;*/
/*?}*/

public final class ModBlocks {

    //---------------------------Flowers---------------------------
    /*? if <26.2 {*/
    public static final Block BELLS_OF_IRELAND = new FlowerBlock(StatusEffects.REGENERATION, 100, AbstractBlock.Settings.copy(DANDELION)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("bells_of_ireland"))*/
            /*?}*/
    );
    public static final Block GROWN_BELLS_OF_IRELAND = new GrowableFlower(StatusEffects.REGENERATION, 100, AbstractBlock.Settings.copy(BELLS_OF_IRELAND)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("grown_bells_of_ireland"))*/
            /*?}*/
    );

    public static final Block BOG_ROSEMARY = new FlowerBlock(StatusEffects.NAUSEA, 150, AbstractBlock.Settings.copy(DANDELION)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("bog_rosemary"))*/
            /*?}*/
    );
    public static final Block GROWN_BOG_ROSEMARY = new GrowableFlower(StatusEffects.NAUSEA, 150, AbstractBlock.Settings.copy(BOG_ROSEMARY)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("grown_bog_rosemary"))*/
            /*?}*/
    );

    public static final Block BULBOUS_BUTTERCUP = new FlowerBlock(StatusEffects.NAUSEA, 100, AbstractBlock.Settings.copy(DANDELION)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("bulbous_buttercup"))*/
            /*?}*/
    );
    public static final Block GROWN_BULBOUS_BUTTERCUP = new GrowableFlower(StatusEffects.NAUSEA, 100, AbstractBlock.Settings.copy(BULBOUS_BUTTERCUP)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("grown_bulbous_buttercup"))*/
            /*?}*/
    );

    public static final Block BLUEBELL = new FlowerBlock(StatusEffects.JUMP_BOOST, 100, AbstractBlock.Settings.copy(DANDELION)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("bluebell"))*/
            /*?}*/
    );
    public static final Block GROWN_BLUEBELL = new GrowableFlower(StatusEffects.JUMP_BOOST, 100, AbstractBlock.Settings.copy(BLUEBELL)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("grown_bluebell"))*/
            /*?}*/
    );

    //-----------------------Potted Flowers------------------------
    public static final Block POTTED_BELLS_OF_IRELAND = new FlowerPotBlock(BELLS_OF_IRELAND, AbstractBlock.Settings.copy(POTTED_DANDELION)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("potted_bells_of_ireland"))*/
            /*?}*/
    );
    public static final Block POTTED_GROWN_BELLS_OF_IRELAND = new FlowerPotBlock(GROWN_BELLS_OF_IRELAND, AbstractBlock.Settings.copy(POTTED_BELLS_OF_IRELAND)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("potted_grown_bells_of_ireland"))*/
            /*?}*/
    );

    public static final Block POTTED_BOG_ROSEMARY = new FlowerPotBlock(BOG_ROSEMARY, AbstractBlock.Settings.copy(POTTED_DANDELION)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("potted_bog_rosemary"))*/
            /*?}*/
    );
    public static final Block POTTED_GROWN_BOG_ROSEMARY = new FlowerPotBlock(GROWN_BOG_ROSEMARY, AbstractBlock.Settings.copy(POTTED_BOG_ROSEMARY)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("potted_grown_bog_rosemary"))*/
            /*?}*/
    );

    public static final Block POTTED_BULBOUS_BUTTERCUP = new FlowerPotBlock(BULBOUS_BUTTERCUP, AbstractBlock.Settings.copy(POTTED_DANDELION)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("potted_bulbous_buttercup"))*/
            /*?}*/
    );
    public static final Block POTTED_GROWN_BULBOUS_BUTTERCUP = new FlowerPotBlock(GROWN_BULBOUS_BUTTERCUP, AbstractBlock.Settings.copy(POTTED_BULBOUS_BUTTERCUP)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("potted_grown_bulbous_buttercup"))*/
            /*?}*/
    );

    public static final Block POTTED_BLUEBELL = new FlowerPotBlock(BLUEBELL, AbstractBlock.Settings.copy(POTTED_DANDELION)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("potted_bluebell"))*/
            /*?}*/
    );
    public static final Block POTTED_GROWN_BLUEBELL = new FlowerPotBlock(GROWN_BLUEBELL, AbstractBlock.Settings.copy(POTTED_BLUEBELL)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("potted_grown_bluebell"))*/
            /*?}*/
    );

    //-----------------------------Wood------------------------------
    public static final Block STRIPPED_YEW_LOG = new ModPillarBlock(AbstractBlock.Settings.copy(STRIPPED_OAK_LOG)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("stripped_yew_log"))*/
            /*?}*/
            , 5, 5, null
    );
    public static final Block STRIPPED_YEW_WOOD = new ModPillarBlock(AbstractBlock.Settings.copy(STRIPPED_OAK_WOOD)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("stripped_yew_wood"))*/
            /*?}*/
            , 5, 5, null
    );
    public static final Block YEW_LOG = new ModPillarBlock(AbstractBlock.Settings.copy(OAK_LOG)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("yew_log"))*/
            /*?}*/
            , 5, 5, () -> STRIPPED_YEW_LOG
    );
    public static final Block YEW_WOOD = new ModPillarBlock(AbstractBlock.Settings.copy(OAK_WOOD)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("yew_wood"))*/
            /*?}*/
            , 5, 5, () -> STRIPPED_YEW_WOOD
    );
    public static final Block YEW_PLANKS = new ModFlammableBlock(AbstractBlock.Settings.copy(OAK_PLANKS)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("yew_planks"))*/
            /*?}*/
            , 5, 20
    );
    public static final Block YEW_LEAVES = new ModLeavesBlock(0.1f, AbstractBlock.Settings.copy(OAK_LEAVES)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("yew_leaves"))*/
            /*?}*/
            , 30, 60
    );
    public static final Block YEW_SAPLING = new ModSaplingBlock(AbstractBlock.Settings.copy(OAK_SAPLING)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("yew_sapling"))*/
            /*?}*/
    );
    public static final Block YEW_STAIRS = new ModStairsBlock(YEW_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(YEW_PLANKS)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("yew_stairs"))*/
            /*?}*/
            , 5, 20
    );
    public static final Block YEW_SLAB = new ModSlabBlock(AbstractBlock.Settings.copy(YEW_PLANKS)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("yew_slab"))*/
            /*?}*/
            , 5, 20
    );
    public static final Block YEW_FENCE = new ModFenceBlock(AbstractBlock.Settings.copy(YEW_PLANKS)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("yew_fence"))*/
            /*?}*/
            , 5, 20
    );

    // Shared by the fence gate/door/trapdoor/pressure plate/button/sign below - reuses
    // vanilla OAK's BlockSetType (interaction sounds only, purely cosmetic) rather than
    // building a custom one. Properly registered via ModWoodTypes (see its own doc
    // comment) rather than just constructed, so signs' 3D post model can find its wood
    // texture too, not just the other blocks' interaction sounds.
    public static final WoodType YEW_WOOD_TYPE = ModWoodTypes.register("yew");

    public static final Block YEW_FENCE_GATE = new ModFenceGateBlock(YEW_WOOD_TYPE, AbstractBlock.Settings.copy(YEW_PLANKS)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("yew_fence_gate"))*/
            /*?}*/
            , 5, 20
    );
    public static final Block YEW_DOOR = new ModDoorBlock(BlockSetType.OAK, AbstractBlock.Settings.copy(OAK_DOOR)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("yew_door"))*/
            /*?}*/
            , 5, 20
    );
    public static final Block YEW_TRAPDOOR = new ModTrapdoorBlock(BlockSetType.OAK, AbstractBlock.Settings.copy(OAK_TRAPDOOR)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("yew_trapdoor"))*/
            /*?}*/
            , 5, 20
    );
    public static final Block YEW_PRESSURE_PLATE = new ModPressurePlateBlock(BlockSetType.OAK, AbstractBlock.Settings.copy(YEW_PLANKS)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("yew_pressure_plate"))*/
            /*?}*/
            , 5, 20
    );
    public static final Block YEW_BUTTON = new ModButtonBlock(BlockSetType.OAK, AbstractBlock.Settings.copy(YEW_PLANKS)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("yew_button"))*/
            /*?}*/
            , 5, 20
    );
    public static final Block YEW_SIGN = new ModSignBlock(YEW_WOOD_TYPE, AbstractBlock.Settings.copy(OAK_SIGN)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("yew_sign"))*/
            /*?}*/
            , 5, 20
    );
    public static final Block YEW_WALL_SIGN = new ModWallSignBlock(YEW_WOOD_TYPE, AbstractBlock.Settings.copy(YEW_SIGN)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("yew_wall_sign"))*/
            /*?}*/
            , 5, 20
    );
    public static final Block YEW_HANGING_SIGN = new ModHangingSignBlock(YEW_WOOD_TYPE, AbstractBlock.Settings.copy(OAK_HANGING_SIGN)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("yew_hanging_sign"))*/
            /*?}*/
            , 5, 20
    );
    public static final Block YEW_WALL_HANGING_SIGN = new ModWallHangingSignBlock(YEW_WOOD_TYPE, AbstractBlock.Settings.copy(YEW_HANGING_SIGN)
            /*? if >=1.21.11 {*/
            /*.registryKey(blockId("yew_wall_hanging_sign"))*/
            /*?}*/
            , 5, 20
    );

    // Minecraft 1.21.11 added a hard requirement that AbstractBlock.Settings/Item.Settings
    // carry a registryKey() before the Block/Item is constructed at all (NullPointerException:
    // "Block id not set" otherwise) - these two helpers are used by the .registryKey(...)
    // calls chained onto the Settings above and onto the Item.Settings() calls below.
    /*? if >=1.21.11 {*/
    /*private static RegistryKey<Block> blockId(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(EmeraldIsleFlora.MOD_ID, name));
    }

    private static RegistryKey<Item> itemId(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(EmeraldIsleFlora.MOD_ID, name));
    }*/
    /*?}*/
    /*?} else {*/
    /*
    // 26.2: same shape as the code above (Mojmap names), and the same >1.21.11-era
    // requirement that a Block/Item registry id be set via the Settings/Properties
    // builder before construction, carried forward - see the comment above blockId()
    // below.
    public static final Block BELLS_OF_IRELAND = new FlowerBlock(MobEffects.REGENERATION, 100, BlockBehaviour.Properties.ofFullCopy(DANDELION)
            .setId(blockId("bells_of_ireland")));
    public static final Block GROWN_BELLS_OF_IRELAND = new GrowableFlower(MobEffects.REGENERATION, 100, BlockBehaviour.Properties.ofFullCopy(BELLS_OF_IRELAND)
            .setId(blockId("grown_bells_of_ireland")));

    public static final Block BOG_ROSEMARY = new FlowerBlock(MobEffects.NAUSEA, 150, BlockBehaviour.Properties.ofFullCopy(DANDELION)
            .setId(blockId("bog_rosemary")));
    public static final Block GROWN_BOG_ROSEMARY = new GrowableFlower(MobEffects.NAUSEA, 150, BlockBehaviour.Properties.ofFullCopy(BOG_ROSEMARY)
            .setId(blockId("grown_bog_rosemary")));

    public static final Block BULBOUS_BUTTERCUP = new FlowerBlock(MobEffects.NAUSEA, 100, BlockBehaviour.Properties.ofFullCopy(DANDELION)
            .setId(blockId("bulbous_buttercup")));
    public static final Block GROWN_BULBOUS_BUTTERCUP = new GrowableFlower(MobEffects.NAUSEA, 100, BlockBehaviour.Properties.ofFullCopy(BULBOUS_BUTTERCUP)
            .setId(blockId("grown_bulbous_buttercup")));

    public static final Block BLUEBELL = new FlowerBlock(MobEffects.JUMP_BOOST, 100, BlockBehaviour.Properties.ofFullCopy(DANDELION)
            .setId(blockId("bluebell")));
    public static final Block GROWN_BLUEBELL = new GrowableFlower(MobEffects.JUMP_BOOST, 100, BlockBehaviour.Properties.ofFullCopy(BLUEBELL)
            .setId(blockId("grown_bluebell")));

    //-----------------------Potted Flowers------------------------
    public static final Block POTTED_BELLS_OF_IRELAND = new FlowerPotBlock(BELLS_OF_IRELAND, BlockBehaviour.Properties.ofFullCopy(POTTED_DANDELION)
            .setId(blockId("potted_bells_of_ireland")));
    public static final Block POTTED_GROWN_BELLS_OF_IRELAND = new FlowerPotBlock(GROWN_BELLS_OF_IRELAND, BlockBehaviour.Properties.ofFullCopy(POTTED_BELLS_OF_IRELAND)
            .setId(blockId("potted_grown_bells_of_ireland")));

    public static final Block POTTED_BOG_ROSEMARY = new FlowerPotBlock(BOG_ROSEMARY, BlockBehaviour.Properties.ofFullCopy(POTTED_DANDELION)
            .setId(blockId("potted_bog_rosemary")));
    public static final Block POTTED_GROWN_BOG_ROSEMARY = new FlowerPotBlock(GROWN_BOG_ROSEMARY, BlockBehaviour.Properties.ofFullCopy(POTTED_BOG_ROSEMARY)
            .setId(blockId("potted_grown_bog_rosemary")));

    public static final Block POTTED_BULBOUS_BUTTERCUP = new FlowerPotBlock(BULBOUS_BUTTERCUP, BlockBehaviour.Properties.ofFullCopy(POTTED_DANDELION)
            .setId(blockId("potted_bulbous_buttercup")));
    public static final Block POTTED_GROWN_BULBOUS_BUTTERCUP = new FlowerPotBlock(GROWN_BULBOUS_BUTTERCUP, BlockBehaviour.Properties.ofFullCopy(POTTED_BULBOUS_BUTTERCUP)
            .setId(blockId("potted_grown_bulbous_buttercup")));

    public static final Block POTTED_BLUEBELL = new FlowerPotBlock(BLUEBELL, BlockBehaviour.Properties.ofFullCopy(POTTED_DANDELION)
            .setId(blockId("potted_bluebell")));
    public static final Block POTTED_GROWN_BLUEBELL = new FlowerPotBlock(GROWN_BLUEBELL, BlockBehaviour.Properties.ofFullCopy(POTTED_BLUEBELL)
            .setId(blockId("potted_grown_bluebell")));

    //-----------------------------Wood------------------------------
    public static final Block STRIPPED_YEW_LOG = new ModPillarBlock(BlockBehaviour.Properties.ofFullCopy(STRIPPED_OAK_LOG)
            .setId(blockId("stripped_yew_log")), 5, 5, null);
    public static final Block STRIPPED_YEW_WOOD = new ModPillarBlock(BlockBehaviour.Properties.ofFullCopy(STRIPPED_OAK_WOOD)
            .setId(blockId("stripped_yew_wood")), 5, 5, null);
    public static final Block YEW_LOG = new ModPillarBlock(BlockBehaviour.Properties.ofFullCopy(OAK_LOG)
            .setId(blockId("yew_log")), 5, 5, () -> STRIPPED_YEW_LOG);
    public static final Block YEW_WOOD = new ModPillarBlock(BlockBehaviour.Properties.ofFullCopy(OAK_WOOD)
            .setId(blockId("yew_wood")), 5, 5, () -> STRIPPED_YEW_WOOD);
    public static final Block YEW_PLANKS = new ModFlammableBlock(BlockBehaviour.Properties.ofFullCopy(OAK_PLANKS)
            .setId(blockId("yew_planks")), 5, 20);
    public static final Block YEW_LEAVES = new ModLeavesBlock(0.1f, BlockBehaviour.Properties.ofFullCopy(OAK_LEAVES)
            .setId(blockId("yew_leaves")), 30, 60);
    public static final Block YEW_SAPLING = new ModSaplingBlock(BlockBehaviour.Properties.ofFullCopy(OAK_SAPLING)
            .setId(blockId("yew_sapling")));
    public static final Block YEW_STAIRS = new ModStairsBlock(YEW_PLANKS.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(YEW_PLANKS)
            .setId(blockId("yew_stairs")), 5, 20);
    public static final Block YEW_SLAB = new ModSlabBlock(BlockBehaviour.Properties.ofFullCopy(YEW_PLANKS)
            .setId(blockId("yew_slab")), 5, 20);
    public static final Block YEW_FENCE = new ModFenceBlock(BlockBehaviour.Properties.ofFullCopy(YEW_PLANKS)
            .setId(blockId("yew_fence")), 5, 20);

    public static final WoodType YEW_WOOD_TYPE = ModWoodTypes.register("yew");

    public static final Block YEW_FENCE_GATE = new ModFenceGateBlock(YEW_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(YEW_PLANKS)
            .setId(blockId("yew_fence_gate")), 5, 20);
    public static final Block YEW_DOOR = new ModDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(OAK_DOOR)
            .setId(blockId("yew_door")), 5, 20);
    public static final Block YEW_TRAPDOOR = new ModTrapdoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(OAK_TRAPDOOR)
            .setId(blockId("yew_trapdoor")), 5, 20);
    public static final Block YEW_PRESSURE_PLATE = new ModPressurePlateBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(YEW_PLANKS)
            .setId(blockId("yew_pressure_plate")), 5, 20);
    public static final Block YEW_BUTTON = new ModButtonBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(YEW_PLANKS)
            .setId(blockId("yew_button")), 5, 20);
    public static final Block YEW_SIGN = new ModSignBlock(YEW_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(OAK_SIGN)
            .setId(blockId("yew_sign")), 5, 20);
    public static final Block YEW_WALL_SIGN = new ModWallSignBlock(YEW_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(YEW_SIGN)
            .setId(blockId("yew_wall_sign")), 5, 20);
    public static final Block YEW_HANGING_SIGN = new ModHangingSignBlock(YEW_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(OAK_HANGING_SIGN)
            .setId(blockId("yew_hanging_sign")), 5, 20);
    public static final Block YEW_WALL_HANGING_SIGN = new ModWallHangingSignBlock(YEW_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(YEW_HANGING_SIGN)
            .setId(blockId("yew_wall_hanging_sign")), 5, 20);

    // Carried forward from the >=1.21.11 requirement that a Block/Item registry id be
    // set via the Settings/Properties builder before construction (NullPointerException/
    // IllegalStateException otherwise, thrown lazily the first time the id is needed -
    // e.g. resolving a model or description key at registration time).
    private static ResourceKey<Block> blockId(String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, name));
    }

    private static ResourceKey<Item> itemId(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, name));
    }*/
    /*?}*/

    /** Fabric-only: registers directly. Forge instead uses {@link #onRegister}, deferred to Forge's own RegisterEvent. */
    public static void register() {
        /*? if fabric {*/
        EmeraldIsleFlora.LOGGER.info("Registering Blocks for " + EmeraldIsleFlora.MOD_ID);

        register("bells_of_ireland", BELLS_OF_IRELAND, true);
        register("grown_bells_of_ireland", GROWN_BELLS_OF_IRELAND, true);
        register("potted_bells_of_ireland", POTTED_BELLS_OF_IRELAND, false);
        register("potted_grown_bells_of_ireland", POTTED_GROWN_BELLS_OF_IRELAND, false);

        register("bog_rosemary", BOG_ROSEMARY, true);
        register("grown_bog_rosemary", GROWN_BOG_ROSEMARY, true);
        register("potted_bog_rosemary", POTTED_BOG_ROSEMARY, false);
        register("potted_grown_bog_rosemary", POTTED_GROWN_BOG_ROSEMARY, false);

        register("bulbous_buttercup", BULBOUS_BUTTERCUP, true);
        register("grown_bulbous_buttercup", GROWN_BULBOUS_BUTTERCUP, true);
        register("potted_bulbous_buttercup", POTTED_BULBOUS_BUTTERCUP, false);
        register("potted_grown_bulbous_buttercup", POTTED_GROWN_BULBOUS_BUTTERCUP, false);

        register("bluebell", BLUEBELL, true);
        register("grown_bluebell", GROWN_BLUEBELL, true);
        register("potted_bluebell", POTTED_BLUEBELL, false);
        register("potted_grown_bluebell", POTTED_GROWN_BLUEBELL, false);

        register("yew_log", YEW_LOG, true);
        FlammableBlockRegistry.getDefaultInstance().add(YEW_LOG, 5, 5);

        register("yew_wood", YEW_WOOD, true);
        FlammableBlockRegistry.getDefaultInstance().add(YEW_WOOD, 5, 5);

        register("stripped_yew_log", STRIPPED_YEW_LOG, true);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_YEW_LOG, 5, 5);
        StrippableBlockRegistry.register(YEW_LOG, STRIPPED_YEW_LOG);

        register("stripped_yew_wood", STRIPPED_YEW_WOOD, true);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_YEW_WOOD, 5, 5);
        StrippableBlockRegistry.register(YEW_WOOD, STRIPPED_YEW_WOOD);

        register("yew_planks", YEW_PLANKS, true);
        FlammableBlockRegistry.getDefaultInstance().add(YEW_PLANKS, 5, 20);

        register("yew_leaves", YEW_LEAVES, true);
        FlammableBlockRegistry.getDefaultInstance().add(YEW_LEAVES, 30, 60);

        register("yew_sapling", YEW_SAPLING, true);

        register("yew_stairs", YEW_STAIRS, true);
        FlammableBlockRegistry.getDefaultInstance().add(YEW_STAIRS, 5, 20);

        register("yew_slab", YEW_SLAB, true);
        FlammableBlockRegistry.getDefaultInstance().add(YEW_SLAB, 5, 20);

        register("yew_fence", YEW_FENCE, true);
        FlammableBlockRegistry.getDefaultInstance().add(YEW_FENCE, 5, 20);

        register("yew_fence_gate", YEW_FENCE_GATE, true);
        FlammableBlockRegistry.getDefaultInstance().add(YEW_FENCE_GATE, 5, 20);

        register("yew_door", YEW_DOOR, true);
        FlammableBlockRegistry.getDefaultInstance().add(YEW_DOOR, 5, 20);

        register("yew_trapdoor", YEW_TRAPDOOR, true);
        FlammableBlockRegistry.getDefaultInstance().add(YEW_TRAPDOOR, 5, 20);

        register("yew_pressure_plate", YEW_PRESSURE_PLATE, true);
        FlammableBlockRegistry.getDefaultInstance().add(YEW_PRESSURE_PLATE, 5, 20);

        register("yew_button", YEW_BUTTON, true);
        FlammableBlockRegistry.getDefaultInstance().add(YEW_BUTTON, 5, 20);

        // Signs share one item between the standing and wall variants (SignItem knows
        // both blocks and picks the right one to place based on where you're aiming), so
        // both blocks are registered with includeItem=false and the SignItem is
        // registered by hand under the standing sign's own identifier - not the usual
        // register(name, block, true) helper shape every other block here uses.
        register("yew_sign", YEW_SIGN, false);
        register("yew_wall_sign", YEW_WALL_SIGN, false);
        FlammableBlockRegistry.getDefaultInstance().add(YEW_SIGN, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(YEW_WALL_SIGN, 5, 20);
        /*? if <26.2 {*/
        Registry.register(Registries.ITEM, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_sign"), new SignItem(
                /*? if <1.21.11 {*/
                new Item.Settings(), YEW_SIGN, YEW_WALL_SIGN
                /*?} else {*/
                /*YEW_SIGN, YEW_WALL_SIGN, new Item.Settings().registryKey(itemId("yew_sign")).useBlockPrefixedTranslationKey()*/
                /*?}*/
        ));
        /*?} else {*/
        /*Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_sign"), new SignItem(
                YEW_SIGN, YEW_WALL_SIGN, new Item.Properties().setId(itemId("yew_sign")).useBlockDescriptionPrefix()
        ));*/
        /*?}*/

        // Hanging signs share one item between the standing and wall variants too, via a
        // dedicated HangingSignItem (not SignItem) - see ModHangingSignBlock's doc
        // comment. Unlike SignItem, HangingSignItem's Properties argument is last on
        // every target (confirmed via javap - no <1.21.11/>=1.21.11 flip here).
        register("yew_hanging_sign", YEW_HANGING_SIGN, false);
        register("yew_wall_hanging_sign", YEW_WALL_HANGING_SIGN, false);
        FlammableBlockRegistry.getDefaultInstance().add(YEW_HANGING_SIGN, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(YEW_WALL_HANGING_SIGN, 5, 20);
        /*? if <26.2 {*/
        Registry.register(Registries.ITEM, Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign"), new HangingSignItem(
                YEW_HANGING_SIGN, YEW_WALL_HANGING_SIGN,
                /*? if <1.21.11 {*/
                new Item.Settings()
                /*?} else {*/
                /*new Item.Settings().registryKey(itemId("yew_hanging_sign")).useBlockPrefixedTranslationKey()*/
                /*?}*/
        ));
        /*?} else {*/
        /*Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign"), new HangingSignItem(
                YEW_HANGING_SIGN, YEW_WALL_HANGING_SIGN, new Item.Properties().setId(itemId("yew_hanging_sign")).useBlockDescriptionPrefix()
        ));*/
        /*?}*/

        registerComposting();

        EmeraldIsleFlora.LOGGER.info("Finished registering Blocks for " + EmeraldIsleFlora.MOD_ID);
        /*?}*/
    }

    /*? if fabric {*/
    private static void register(String name, Block block, boolean includeItem) {
        /*? if <26.2 {*/
        Identifier identifier = Identifier.of(EmeraldIsleFlora.MOD_ID, name);
        Registry.register(Registries.BLOCK, identifier, block);

        if(includeItem) {
            Registry.register(Registries.ITEM, identifier, new BlockItem(block, new Item.Settings()
                    /*? if >=1.21.11 {*/
                    /*.registryKey(itemId(name))
                    // 1.21.11 changed the item translation key default from block-prefixed
                    // (delegating to the block's own lang entry, which is all this mod's
                    // lang file has) to item-prefixed - BlockItem no longer overrides this
                    // itself, so it has to be requested explicitly or item names show as
                    // raw untranslated keys.
                    .useBlockPrefixedTranslationKey()*/
                    /*?}*/
            ));
        }
        /*?} else {*/
        /*Identifier identifier = Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, name);
        Registry.register(BuiltInRegistries.BLOCK, identifier, block);

        if(includeItem) {
            // 26.2: Item.Properties#useBlockPrefixedTranslationKey() was itself renamed
            // again (confirmed via javap - not present at all on 26.2's Item.Properties;
            // the real method is useBlockDescriptionPrefix()), same "delegate to the
            // block's own lang entry" behavior this mod's lang file already relies on.
            Registry.register(BuiltInRegistries.ITEM, identifier, new BlockItem(block, new Item.Properties()
                    .setId(itemId(name))
                    .useBlockDescriptionPrefix()
            ));
        }
        */
        /*?}*/
    }
    /*?}*/

    /*? if forge {*/
    /*
    // Forge: blocks/items are already-built singletons (same instances Fabric uses),
    // just registered via the RegisterEvent helper instead of a direct Registry.register
    // call, since Forge's registries aren't open for modification until this event fires.
    public static void onRegister(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.BLOCKS, helper -> {
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bells_of_ireland"), BELLS_OF_IRELAND);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bells_of_ireland"), GROWN_BELLS_OF_IRELAND);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_bells_of_ireland"), POTTED_BELLS_OF_IRELAND);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_grown_bells_of_ireland"), POTTED_GROWN_BELLS_OF_IRELAND);

            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bog_rosemary"), BOG_ROSEMARY);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bog_rosemary"), GROWN_BOG_ROSEMARY);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_bog_rosemary"), POTTED_BOG_ROSEMARY);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_grown_bog_rosemary"), POTTED_GROWN_BOG_ROSEMARY);

            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bulbous_buttercup"), BULBOUS_BUTTERCUP);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bulbous_buttercup"), GROWN_BULBOUS_BUTTERCUP);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_bulbous_buttercup"), POTTED_BULBOUS_BUTTERCUP);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_grown_bulbous_buttercup"), POTTED_GROWN_BULBOUS_BUTTERCUP);

            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bluebell"), BLUEBELL);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bluebell"), GROWN_BLUEBELL);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_bluebell"), POTTED_BLUEBELL);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_grown_bluebell"), POTTED_GROWN_BLUEBELL);

            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_log"), YEW_LOG);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_wood"), YEW_WOOD);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "stripped_yew_log"), STRIPPED_YEW_LOG);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "stripped_yew_wood"), STRIPPED_YEW_WOOD);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_planks"), YEW_PLANKS);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_leaves"), YEW_LEAVES);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_sapling"), YEW_SAPLING);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_stairs"), YEW_STAIRS);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_slab"), YEW_SLAB);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_fence"), YEW_FENCE);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_fence_gate"), YEW_FENCE_GATE);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_door"), YEW_DOOR);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_trapdoor"), YEW_TRAPDOOR);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_pressure_plate"), YEW_PRESSURE_PLATE);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_button"), YEW_BUTTON);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_sign"), YEW_SIGN);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_wall_sign"), YEW_WALL_SIGN);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign"), YEW_HANGING_SIGN);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_wall_hanging_sign"), YEW_WALL_HANGING_SIGN);
        });

        event.register(ForgeRegistries.Keys.ITEMS, helper -> {
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bells_of_ireland"), new BlockItem(BELLS_OF_IRELAND, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bells_of_ireland"), new BlockItem(GROWN_BELLS_OF_IRELAND, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bog_rosemary"), new BlockItem(BOG_ROSEMARY, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bog_rosemary"), new BlockItem(GROWN_BOG_ROSEMARY, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bulbous_buttercup"), new BlockItem(BULBOUS_BUTTERCUP, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bulbous_buttercup"), new BlockItem(GROWN_BULBOUS_BUTTERCUP, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bluebell"), new BlockItem(BLUEBELL, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bluebell"), new BlockItem(GROWN_BLUEBELL, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_log"), new BlockItem(YEW_LOG, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_wood"), new BlockItem(YEW_WOOD, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "stripped_yew_log"), new BlockItem(STRIPPED_YEW_LOG, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "stripped_yew_wood"), new BlockItem(STRIPPED_YEW_WOOD, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_planks"), new BlockItem(YEW_PLANKS, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_leaves"), new BlockItem(YEW_LEAVES, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_sapling"), new BlockItem(YEW_SAPLING, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_stairs"), new BlockItem(YEW_STAIRS, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_slab"), new BlockItem(YEW_SLAB, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_fence"), new BlockItem(YEW_FENCE, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_fence_gate"), new BlockItem(YEW_FENCE_GATE, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_door"), new BlockItem(YEW_DOOR, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_trapdoor"), new BlockItem(YEW_TRAPDOOR, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_pressure_plate"), new BlockItem(YEW_PRESSURE_PLATE, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_button"), new BlockItem(YEW_BUTTON, new Item.Settings()));
            // Shared between the standing and wall sign, registered under the standing
            // sign's own identifier - see the matching comment on the Fabric path.
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_sign"), new SignItem(new Item.Settings(), YEW_SIGN, YEW_WALL_SIGN));
            // Same sharing pattern for the hanging sign, via HangingSignItem instead of
            // SignItem - see ModHangingSignBlock's doc comment.
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign"), new HangingSignItem(YEW_HANGING_SIGN, YEW_WALL_HANGING_SIGN, new Item.Settings()));

            // registerComposting() must run in here, not after both event.register(...)
            // calls at the outer method level - see the long comment on registerComposting()
            // itself for why: Block.asItem() lazily caches its result forever on first call,
            // and RegisterEvent fires once per registry (this whole onRegister method runs
            // multiple times total, once per registry Forge processes), so calling it at the
            // outer level ran it during earlier, unrelated dispatches too - before any
            // BlockItem existed - permanently caching the wrong (air) item and silently
            // breaking composting no matter how many times it was harmlessly called again
            // afterward. Confirmed via real-install testing: this was still broken after the
            // .asItem() fix alone fixed Fabric (which registers everything in one linear,
            // non-event-driven pass, never hitting this trap).
            registerComposting();
        });

        registerFlowerPotPlants();
    }
    */
    /*?}*/

    // NeoForge: same shape as Forge's onRegister, but registered via vanilla
    // RegistryKeys.BLOCK/ITEM instead of Forge's own ForgeRegistries.Keys - NeoForge's
    // RegisterEvent takes a plain vanilla registry key, it doesn't have its own
    // block/item registry key holder the way Forge does.
    //
    // Split into version variants (rather than one body with a nested Stonecutter
    // conditional) because this whole method is already one big disabled block comment
    // when neoforge is inactive - Stonecutter's own scanner treats block-comment content
    // as opaque, so a nested /*? ... *?/ marker inside it would never be seen. The
    // >=1.21.11 variant is additionally bounded with "&& <26.2" - 26.2 also satisfies a
    // raw ">=1.21.11" comparison (26 > 1), so without that upper bound this block and the
    // new >=26.2 block below would both be active simultaneously for a 26.2 target.
    /*? if neoforge && <1.21.11 {*/
    /*
    public static void onRegister(RegisterEvent event) {
        event.register(RegistryKeys.BLOCK, helper -> {
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bells_of_ireland"), BELLS_OF_IRELAND);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bells_of_ireland"), GROWN_BELLS_OF_IRELAND);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_bells_of_ireland"), POTTED_BELLS_OF_IRELAND);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_grown_bells_of_ireland"), POTTED_GROWN_BELLS_OF_IRELAND);

            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bog_rosemary"), BOG_ROSEMARY);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bog_rosemary"), GROWN_BOG_ROSEMARY);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_bog_rosemary"), POTTED_BOG_ROSEMARY);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_grown_bog_rosemary"), POTTED_GROWN_BOG_ROSEMARY);

            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bulbous_buttercup"), BULBOUS_BUTTERCUP);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bulbous_buttercup"), GROWN_BULBOUS_BUTTERCUP);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_bulbous_buttercup"), POTTED_BULBOUS_BUTTERCUP);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_grown_bulbous_buttercup"), POTTED_GROWN_BULBOUS_BUTTERCUP);

            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bluebell"), BLUEBELL);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bluebell"), GROWN_BLUEBELL);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_bluebell"), POTTED_BLUEBELL);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_grown_bluebell"), POTTED_GROWN_BLUEBELL);

            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_log"), YEW_LOG);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_wood"), YEW_WOOD);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "stripped_yew_log"), STRIPPED_YEW_LOG);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "stripped_yew_wood"), STRIPPED_YEW_WOOD);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_planks"), YEW_PLANKS);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_leaves"), YEW_LEAVES);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_sapling"), YEW_SAPLING);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_stairs"), YEW_STAIRS);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_slab"), YEW_SLAB);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_fence"), YEW_FENCE);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_fence_gate"), YEW_FENCE_GATE);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_door"), YEW_DOOR);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_trapdoor"), YEW_TRAPDOOR);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_pressure_plate"), YEW_PRESSURE_PLATE);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_button"), YEW_BUTTON);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_sign"), YEW_SIGN);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_wall_sign"), YEW_WALL_SIGN);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign"), YEW_HANGING_SIGN);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_wall_hanging_sign"), YEW_WALL_HANGING_SIGN);
        });

        event.register(RegistryKeys.ITEM, helper -> {
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bells_of_ireland"), new BlockItem(BELLS_OF_IRELAND, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bells_of_ireland"), new BlockItem(GROWN_BELLS_OF_IRELAND, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bog_rosemary"), new BlockItem(BOG_ROSEMARY, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bog_rosemary"), new BlockItem(GROWN_BOG_ROSEMARY, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bulbous_buttercup"), new BlockItem(BULBOUS_BUTTERCUP, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bulbous_buttercup"), new BlockItem(GROWN_BULBOUS_BUTTERCUP, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bluebell"), new BlockItem(BLUEBELL, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bluebell"), new BlockItem(GROWN_BLUEBELL, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_log"), new BlockItem(YEW_LOG, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_wood"), new BlockItem(YEW_WOOD, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "stripped_yew_log"), new BlockItem(STRIPPED_YEW_LOG, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "stripped_yew_wood"), new BlockItem(STRIPPED_YEW_WOOD, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_planks"), new BlockItem(YEW_PLANKS, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_leaves"), new BlockItem(YEW_LEAVES, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_sapling"), new BlockItem(YEW_SAPLING, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_stairs"), new BlockItem(YEW_STAIRS, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_slab"), new BlockItem(YEW_SLAB, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_fence"), new BlockItem(YEW_FENCE, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_fence_gate"), new BlockItem(YEW_FENCE_GATE, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_door"), new BlockItem(YEW_DOOR, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_trapdoor"), new BlockItem(YEW_TRAPDOOR, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_pressure_plate"), new BlockItem(YEW_PRESSURE_PLATE, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_button"), new BlockItem(YEW_BUTTON, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_sign"), new SignItem(new Item.Settings(), YEW_SIGN, YEW_WALL_SIGN));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign"), new HangingSignItem(YEW_HANGING_SIGN, YEW_WALL_HANGING_SIGN, new Item.Settings()));

            // Must run in here, not after both event.register(...) calls at the outer
            // method level - see the comment on registerComposting() itself.
            registerComposting();
        });

        registerFlowerPotPlants();
    }
    */
    /*?}*/
    /*? if neoforge && >=1.21.11 && <26.2 {*/
    /*
    public static void onRegister(RegisterEvent event) {
        event.register(RegistryKeys.BLOCK, helper -> {
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bells_of_ireland"), BELLS_OF_IRELAND);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bells_of_ireland"), GROWN_BELLS_OF_IRELAND);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_bells_of_ireland"), POTTED_BELLS_OF_IRELAND);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_grown_bells_of_ireland"), POTTED_GROWN_BELLS_OF_IRELAND);

            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bog_rosemary"), BOG_ROSEMARY);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bog_rosemary"), GROWN_BOG_ROSEMARY);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_bog_rosemary"), POTTED_BOG_ROSEMARY);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_grown_bog_rosemary"), POTTED_GROWN_BOG_ROSEMARY);

            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bulbous_buttercup"), BULBOUS_BUTTERCUP);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bulbous_buttercup"), GROWN_BULBOUS_BUTTERCUP);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_bulbous_buttercup"), POTTED_BULBOUS_BUTTERCUP);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_grown_bulbous_buttercup"), POTTED_GROWN_BULBOUS_BUTTERCUP);

            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bluebell"), BLUEBELL);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bluebell"), GROWN_BLUEBELL);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_bluebell"), POTTED_BLUEBELL);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "potted_grown_bluebell"), POTTED_GROWN_BLUEBELL);

            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_log"), YEW_LOG);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_wood"), YEW_WOOD);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "stripped_yew_log"), STRIPPED_YEW_LOG);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "stripped_yew_wood"), STRIPPED_YEW_WOOD);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_planks"), YEW_PLANKS);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_leaves"), YEW_LEAVES);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_sapling"), YEW_SAPLING);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_stairs"), YEW_STAIRS);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_slab"), YEW_SLAB);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_fence"), YEW_FENCE);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_fence_gate"), YEW_FENCE_GATE);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_door"), YEW_DOOR);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_trapdoor"), YEW_TRAPDOOR);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_pressure_plate"), YEW_PRESSURE_PLATE);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_button"), YEW_BUTTON);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_sign"), YEW_SIGN);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_wall_sign"), YEW_WALL_SIGN);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign"), YEW_HANGING_SIGN);
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_wall_hanging_sign"), YEW_WALL_HANGING_SIGN);
        });

        event.register(RegistryKeys.ITEM, helper -> {
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bells_of_ireland"), new BlockItem(BELLS_OF_IRELAND, new Item.Settings().registryKey(itemId("bells_of_ireland")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bells_of_ireland"), new BlockItem(GROWN_BELLS_OF_IRELAND, new Item.Settings().registryKey(itemId("grown_bells_of_ireland")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bog_rosemary"), new BlockItem(BOG_ROSEMARY, new Item.Settings().registryKey(itemId("bog_rosemary")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bog_rosemary"), new BlockItem(GROWN_BOG_ROSEMARY, new Item.Settings().registryKey(itemId("grown_bog_rosemary")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bulbous_buttercup"), new BlockItem(BULBOUS_BUTTERCUP, new Item.Settings().registryKey(itemId("bulbous_buttercup")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bulbous_buttercup"), new BlockItem(GROWN_BULBOUS_BUTTERCUP, new Item.Settings().registryKey(itemId("grown_bulbous_buttercup")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bluebell"), new BlockItem(BLUEBELL, new Item.Settings().registryKey(itemId("bluebell")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bluebell"), new BlockItem(GROWN_BLUEBELL, new Item.Settings().registryKey(itemId("grown_bluebell")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_log"), new BlockItem(YEW_LOG, new Item.Settings().registryKey(itemId("yew_log")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_wood"), new BlockItem(YEW_WOOD, new Item.Settings().registryKey(itemId("yew_wood")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "stripped_yew_log"), new BlockItem(STRIPPED_YEW_LOG, new Item.Settings().registryKey(itemId("stripped_yew_log")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "stripped_yew_wood"), new BlockItem(STRIPPED_YEW_WOOD, new Item.Settings().registryKey(itemId("stripped_yew_wood")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_planks"), new BlockItem(YEW_PLANKS, new Item.Settings().registryKey(itemId("yew_planks")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_leaves"), new BlockItem(YEW_LEAVES, new Item.Settings().registryKey(itemId("yew_leaves")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_sapling"), new BlockItem(YEW_SAPLING, new Item.Settings().registryKey(itemId("yew_sapling")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_stairs"), new BlockItem(YEW_STAIRS, new Item.Settings().registryKey(itemId("yew_stairs")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_slab"), new BlockItem(YEW_SLAB, new Item.Settings().registryKey(itemId("yew_slab")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_fence"), new BlockItem(YEW_FENCE, new Item.Settings().registryKey(itemId("yew_fence")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_fence_gate"), new BlockItem(YEW_FENCE_GATE, new Item.Settings().registryKey(itemId("yew_fence_gate")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_door"), new BlockItem(YEW_DOOR, new Item.Settings().registryKey(itemId("yew_door")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_trapdoor"), new BlockItem(YEW_TRAPDOOR, new Item.Settings().registryKey(itemId("yew_trapdoor")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_pressure_plate"), new BlockItem(YEW_PRESSURE_PLATE, new Item.Settings().registryKey(itemId("yew_pressure_plate")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_button"), new BlockItem(YEW_BUTTON, new Item.Settings().registryKey(itemId("yew_button")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_sign"), new SignItem(YEW_SIGN, YEW_WALL_SIGN, new Item.Settings().registryKey(itemId("yew_sign")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign"), new HangingSignItem(YEW_HANGING_SIGN, YEW_WALL_HANGING_SIGN, new Item.Settings().registryKey(itemId("yew_hanging_sign")).useBlockPrefixedTranslationKey()));

            // Must run in here, not after both event.register(...) calls at the outer
            // method level - see the comment on registerComposting() itself.
            registerComposting();
        });

        registerFlowerPotPlants();
    }
    */
    /*?}*/
    /*? if neoforge && >=26.2 {*/
    /*
    // 26.2: same shape as the >=1.21.11 variant above, Mojmap names - Registries here is
    // the key-constants class (Mojmap's Registries == Yarn's RegistryKeys, not Yarn's
    // Registries - see the field-declarations block above for BuiltInRegistries, the
    // actual registry-instances class).
    public static void onRegister(RegisterEvent event) {
        event.register(Registries.BLOCK, helper -> {
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "bells_of_ireland"), BELLS_OF_IRELAND);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "grown_bells_of_ireland"), GROWN_BELLS_OF_IRELAND);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "potted_bells_of_ireland"), POTTED_BELLS_OF_IRELAND);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "potted_grown_bells_of_ireland"), POTTED_GROWN_BELLS_OF_IRELAND);

            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "bog_rosemary"), BOG_ROSEMARY);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "grown_bog_rosemary"), GROWN_BOG_ROSEMARY);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "potted_bog_rosemary"), POTTED_BOG_ROSEMARY);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "potted_grown_bog_rosemary"), POTTED_GROWN_BOG_ROSEMARY);

            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "bulbous_buttercup"), BULBOUS_BUTTERCUP);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "grown_bulbous_buttercup"), GROWN_BULBOUS_BUTTERCUP);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "potted_bulbous_buttercup"), POTTED_BULBOUS_BUTTERCUP);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "potted_grown_bulbous_buttercup"), POTTED_GROWN_BULBOUS_BUTTERCUP);

            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "bluebell"), BLUEBELL);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "grown_bluebell"), GROWN_BLUEBELL);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "potted_bluebell"), POTTED_BLUEBELL);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "potted_grown_bluebell"), POTTED_GROWN_BLUEBELL);

            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_log"), YEW_LOG);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_wood"), YEW_WOOD);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "stripped_yew_log"), STRIPPED_YEW_LOG);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "stripped_yew_wood"), STRIPPED_YEW_WOOD);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_planks"), YEW_PLANKS);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_leaves"), YEW_LEAVES);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_sapling"), YEW_SAPLING);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_stairs"), YEW_STAIRS);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_slab"), YEW_SLAB);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_fence"), YEW_FENCE);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_fence_gate"), YEW_FENCE_GATE);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_door"), YEW_DOOR);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_trapdoor"), YEW_TRAPDOOR);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_pressure_plate"), YEW_PRESSURE_PLATE);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_button"), YEW_BUTTON);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_sign"), YEW_SIGN);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_wall_sign"), YEW_WALL_SIGN);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign"), YEW_HANGING_SIGN);
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_wall_hanging_sign"), YEW_WALL_HANGING_SIGN);
        });

        event.register(Registries.ITEM, helper -> {
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "bells_of_ireland"), new BlockItem(BELLS_OF_IRELAND, new Item.Properties().setId(itemId("bells_of_ireland")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "grown_bells_of_ireland"), new BlockItem(GROWN_BELLS_OF_IRELAND, new Item.Properties().setId(itemId("grown_bells_of_ireland")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "bog_rosemary"), new BlockItem(BOG_ROSEMARY, new Item.Properties().setId(itemId("bog_rosemary")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "grown_bog_rosemary"), new BlockItem(GROWN_BOG_ROSEMARY, new Item.Properties().setId(itemId("grown_bog_rosemary")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "bulbous_buttercup"), new BlockItem(BULBOUS_BUTTERCUP, new Item.Properties().setId(itemId("bulbous_buttercup")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "grown_bulbous_buttercup"), new BlockItem(GROWN_BULBOUS_BUTTERCUP, new Item.Properties().setId(itemId("grown_bulbous_buttercup")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "bluebell"), new BlockItem(BLUEBELL, new Item.Properties().setId(itemId("bluebell")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "grown_bluebell"), new BlockItem(GROWN_BLUEBELL, new Item.Properties().setId(itemId("grown_bluebell")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_log"), new BlockItem(YEW_LOG, new Item.Properties().setId(itemId("yew_log")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_wood"), new BlockItem(YEW_WOOD, new Item.Properties().setId(itemId("yew_wood")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "stripped_yew_log"), new BlockItem(STRIPPED_YEW_LOG, new Item.Properties().setId(itemId("stripped_yew_log")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "stripped_yew_wood"), new BlockItem(STRIPPED_YEW_WOOD, new Item.Properties().setId(itemId("stripped_yew_wood")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_planks"), new BlockItem(YEW_PLANKS, new Item.Properties().setId(itemId("yew_planks")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_leaves"), new BlockItem(YEW_LEAVES, new Item.Properties().setId(itemId("yew_leaves")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_sapling"), new BlockItem(YEW_SAPLING, new Item.Properties().setId(itemId("yew_sapling")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_stairs"), new BlockItem(YEW_STAIRS, new Item.Properties().setId(itemId("yew_stairs")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_slab"), new BlockItem(YEW_SLAB, new Item.Properties().setId(itemId("yew_slab")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_fence"), new BlockItem(YEW_FENCE, new Item.Properties().setId(itemId("yew_fence")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_fence_gate"), new BlockItem(YEW_FENCE_GATE, new Item.Properties().setId(itemId("yew_fence_gate")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_door"), new BlockItem(YEW_DOOR, new Item.Properties().setId(itemId("yew_door")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_trapdoor"), new BlockItem(YEW_TRAPDOOR, new Item.Properties().setId(itemId("yew_trapdoor")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_pressure_plate"), new BlockItem(YEW_PRESSURE_PLATE, new Item.Properties().setId(itemId("yew_pressure_plate")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_button"), new BlockItem(YEW_BUTTON, new Item.Properties().setId(itemId("yew_button")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_sign"), new SignItem(YEW_SIGN, YEW_WALL_SIGN, new Item.Properties().setId(itemId("yew_sign")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "yew_hanging_sign"), new HangingSignItem(YEW_HANGING_SIGN, YEW_WALL_HANGING_SIGN, new Item.Properties().setId(itemId("yew_hanging_sign")).useBlockDescriptionPrefix()));

            // Must run in here, not after both event.register(...) calls at the outer
            // method level - see the comment on registerComposting() itself.
            registerComposting();
        });

        registerFlowerPotPlants();
    }
    */
    /*?}*/

    // Fabric and (old, 1.20.1) Forge both still read straight from this vanilla static
    // field at composting time (confirmed via decompiling ComposterBlock.useOnBlock/
    // insertItem/addItem in both those loaders' real jars - neither patches it), so this
    // registerComposting() call is a real, working fix there. NeoForge (all three
    // versions this mod targets: 1.21.1, 1.21.11, 26.2) is different: NeoForge patches
    // ComposterBlock to deprecate this field and read exclusively from its own
    // "neoforge:compostables" data map instead (confirmed via decompiling NeoForge's own
    // ComposterBlock.java.patch for each version - identical patch content across all
    // three, redirecting every COMPOSTABLES.containsKey/getFloat call site to a new
    // getValue(ItemStack) that reads item.getData(NeoForgeDataMaps.COMPOSTABLES) and
    // ignores this field entirely). The .put(...) calls below are therefore harmless
    // but functionally dead on NeoForge - the real fix for that loader is the data map
    // JSON at src/main/resources/data/neoforge/data_maps/item/compostables.json, which
    // is left in the shared (all-loader) resources dir since it's simply inert,
    // unreferenced data on Fabric/Forge (same freeform-data reasoning as this project's
    // vanilla tag files).
    // ITEM_TO_LEVEL_INCREASE_CHANCE/COMPOSTABLES is an Object2FloatMap<ItemConvertible>
    // (Yarn)/<ItemLike> (Mojmap), and .put(...) is the plain generic map method - it does
    // NOT call .asItem() for you, it just stores whatever object reference you hand it as
    // the raw hash key. Vanilla's own bootstrap (ComposterBlock.registerCompostableItem,
    // private, confirmed via decompiling the real class) always calls item.asItem() before
    // putting - e.g. it registers Items.OAK_LEAVES, an Item, not Blocks.OAK_LEAVES, a
    // Block. The actual lookup during composting always keys off itemStack.getItem(), an
    // Item. Passing a Block reference directly (as this method did previously) silently
    // inserts a dead entry keyed on the Block object, which a Block and an Item are never
    // equal/same-hashCode for (neither overrides Object's identity equals/hashCode) - so
    // the real per-item lookup at composting time never matches it. This was a real,
    // pre-existing bug independent of the whole multi-version migration, confirmed by
    // real-install testing: NeoForge (whose composting is driven by the data map JSON
    // above, not this field at all) worked; Fabric and Forge 1.20.1 (both still driven by
    // this exact field) did not, until .asItem() was added below.
    //
    // .asItem() alone was still not enough to fix Forge 1.20.1, even after it fixed
    // Fabric - a second, separate bug, also confirmed by decompiling (Block.asItem() /
    // m_5456_ in the real 1.20.1 jar): it's a LAZILY CACHED getter (backed by a private
    // cachedItem field, computed once via Item.fromBlock(this) on first call and never
    // recomputed after). Fabric's register() is one linear, synchronous pass - every
    // block+item is constructed and registered before registerComposting() ever runs
    // once at the end, so .asItem() resolves correctly the first (and only) time it's
    // called. Forge/NeoForge's onRegister(RegisterEvent) is different: RegisterEvent
    // fires once per registry, so this same onRegister method actually runs multiple
    // times total (once when the event is for BLOCKS, once for ITEMS, etc.) - and
    // event.register(key, ...) is a silent no-op whenever the dispatch's registry
    // doesn't match. Calling registerComposting() unconditionally after both
    // event.register(...) calls (the previous shape) meant it also ran during every
    // *other* registry's dispatch too - including, on at least one loader/version,
    // one that fires before ITEMS - permanently caching Items.AIR into asItem() before
    // any BlockItem existed, before the later, correct ITEMS dispatch ever got a
    // chance. Fixed by moving the registerComposting() call to inside the ITEMS-registry
    // helper lambda itself, in every onRegister() variant below (Forge and all three
    // NeoForge version branches) - guaranteeing it only ever runs once, and only after
    // this method's own BlockItem construction has already happened in that same call.
    private static void registerComposting() {
        /*? if <26.2 {*/
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(BELLS_OF_IRELAND.asItem(), 0.65f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(GROWN_BELLS_OF_IRELAND.asItem(), 0.95f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(BOG_ROSEMARY.asItem(), 0.65f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(GROWN_BOG_ROSEMARY.asItem(), 0.95f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(BULBOUS_BUTTERCUP.asItem(), 0.65f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(GROWN_BULBOUS_BUTTERCUP.asItem(), 0.95f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(BLUEBELL.asItem(), 0.65f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(GROWN_BLUEBELL.asItem(), 0.95f);
        /*?} else {*/
        /*
        // 26.2: same Object2FloatMap<ItemLike>, field renamed ITEM_TO_LEVEL_INCREASE_CHANCE -> COMPOSTABLES.
        ComposterBlock.COMPOSTABLES.put(BELLS_OF_IRELAND.asItem(), 0.65f);
        ComposterBlock.COMPOSTABLES.put(GROWN_BELLS_OF_IRELAND.asItem(), 0.95f);
        ComposterBlock.COMPOSTABLES.put(BOG_ROSEMARY.asItem(), 0.65f);
        ComposterBlock.COMPOSTABLES.put(GROWN_BOG_ROSEMARY.asItem(), 0.95f);
        ComposterBlock.COMPOSTABLES.put(BULBOUS_BUTTERCUP.asItem(), 0.65f);
        ComposterBlock.COMPOSTABLES.put(GROWN_BULBOUS_BUTTERCUP.asItem(), 0.95f);
        ComposterBlock.COMPOSTABLES.put(BLUEBELL.asItem(), 0.65f);
        ComposterBlock.COMPOSTABLES.put(GROWN_BLUEBELL.asItem(), 0.95f);*/
        /*?}*/
    }

    // ForgeLike-only (Forge and NeoForge both): both patch FlowerPotBlock away from
    // vanilla's simple construct-time "CONTENT_TO_POTTED.put(content, this)" toward a
    // registry-key-keyed per-instance "fullPots" map, exposed via
    // addPlant(Identifier, Supplier<Block>) on the canonical empty pot (Blocks.FLOWER_POT).
    // The plain FlowerPotBlock(Block, Settings) constructor we use above for POTTED_*
    // *does* try to auto-register into this map, but it does so by looking up the content
    // block's registry key at *construction* time (e.g.
    // ForgeRegistries.BLOCKS.getKey(content) on Forge, BuiltInRegistries.BLOCK.getKey(content)
    // on NeoForge) - since our blocks are constructed as eager static fields before
    // ModBlocks.onRegister ever runs, that lookup finds nothing and the auto-registration
    // silently captures the wrong key. Registering explicitly here, after we already know
    // the real ids, fixes it. Confirmed via decompiling both loaders' actual shipped
    // FlowerPotBlock class (Forge 1.20.1-47.4.10's real patched+Yarn-mapped
    // minecraft-merged jar, and NeoForge's - see below) - this method does not exist on
    // vanilla/Fabric's FlowerPotBlock at all. Found on Forge only after a real-install
    // regression report: 1.20.1-forge could place vanilla flowers in pots but not this
    // mod's, since this method was originally added NeoForge-only and never extended to
    // plain Forge, despite Forge having the exact same patch.
    //
    // Confirmed still present, same shape, for 26.2 too - decompiled the real patched
    // minecraft-merged jar from a 26.2-neoforge Loom build
    // (neoforge-26.2.0.49-beta-minecraft-merged-deobf-26.2.jar) rather than assuming the
    // patch survived, per the plan's explicit flag that this needed a live build to
    // check (NeoForge ships its vanilla patches as a binary diff applied only during the
    // real NeoForm/Loom pipeline, invisible to static jar-only research). addPlant() and
    // getEmptyPot() are both still there, unchanged shape (just Identifier now being the
    // Mojmap resources.Identifier instead of Yarn's util.Identifier). Forge never reaches
    // 26.2 at all in this project's matrix, so only the NeoForge side needed a >=26.2
    // variant.
    // Split into version variants (rather than one body with a nested Stonecutter
    // conditional) for the same reason onRegister() above is split: this whole method is
    // already one big disabled block comment when forgeLike is inactive, and nesting a
    // second /*? ... ?*/ marker inside disabled block-comment text doesn't work - it's
    // both invisible to Stonecutter's scanner and, worse, a literal Java block comment
    // can't nest, so an inner "/*? if ... {*/" would prematurely close the outer comment.
    /*? if forgeLike && <26.2 {*/
    /*private static void registerFlowerPotPlants() {
        // Key is the registry id of the HELD ITEM's block (the plain flower); the
        // supplier is the resulting block placed in the world, which must be the
        // *potted* variant, not the plain flower again.
        if (FLOWER_POT instanceof FlowerPotBlock emptyPot) {
            emptyPot.addPlant(Identifier.of(EmeraldIsleFlora.MOD_ID, "bells_of_ireland"), () -> POTTED_BELLS_OF_IRELAND);
            emptyPot.addPlant(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bells_of_ireland"), () -> POTTED_GROWN_BELLS_OF_IRELAND);
            emptyPot.addPlant(Identifier.of(EmeraldIsleFlora.MOD_ID, "bog_rosemary"), () -> POTTED_BOG_ROSEMARY);
            emptyPot.addPlant(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bog_rosemary"), () -> POTTED_GROWN_BOG_ROSEMARY);
            emptyPot.addPlant(Identifier.of(EmeraldIsleFlora.MOD_ID, "bulbous_buttercup"), () -> POTTED_BULBOUS_BUTTERCUP);
            emptyPot.addPlant(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bulbous_buttercup"), () -> POTTED_GROWN_BULBOUS_BUTTERCUP);
            emptyPot.addPlant(Identifier.of(EmeraldIsleFlora.MOD_ID, "bluebell"), () -> POTTED_BLUEBELL);
            emptyPot.addPlant(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bluebell"), () -> POTTED_GROWN_BLUEBELL);
        }
    }*/
    /*?}*/
    /*? if neoforge && >=26.2 {*/
    /*private static void registerFlowerPotPlants() {
        if (FLOWER_POT instanceof FlowerPotBlock emptyPot) {
            emptyPot.addPlant(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "bells_of_ireland"), () -> POTTED_BELLS_OF_IRELAND);
            emptyPot.addPlant(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "grown_bells_of_ireland"), () -> POTTED_GROWN_BELLS_OF_IRELAND);
            emptyPot.addPlant(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "bog_rosemary"), () -> POTTED_BOG_ROSEMARY);
            emptyPot.addPlant(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "grown_bog_rosemary"), () -> POTTED_GROWN_BOG_ROSEMARY);
            emptyPot.addPlant(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "bulbous_buttercup"), () -> POTTED_BULBOUS_BUTTERCUP);
            emptyPot.addPlant(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "grown_bulbous_buttercup"), () -> POTTED_GROWN_BULBOUS_BUTTERCUP);
            emptyPot.addPlant(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "bluebell"), () -> POTTED_BLUEBELL);
            emptyPot.addPlant(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "grown_bluebell"), () -> POTTED_GROWN_BLUEBELL);
        }
    }*/
    /*?}*/
}
