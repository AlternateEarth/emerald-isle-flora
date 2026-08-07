package net.alternateearth.emeraldisleflora.registry;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
/*? if <26.2 {*/
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
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
        });

        event.register(ForgeRegistries.Keys.ITEMS, helper -> {
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bells_of_ireland"), new BlockItem(BELLS_OF_IRELAND, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bells_of_ireland"), new BlockItem(GROWN_BELLS_OF_IRELAND, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bog_rosemary"), new BlockItem(BOG_ROSEMARY, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bog_rosemary"), new BlockItem(GROWN_BOG_ROSEMARY, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bulbous_buttercup"), new BlockItem(BULBOUS_BUTTERCUP, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bulbous_buttercup"), new BlockItem(GROWN_BULBOUS_BUTTERCUP, new Item.Settings()));
        });

        registerComposting();
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
        });

        event.register(RegistryKeys.ITEM, helper -> {
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bells_of_ireland"), new BlockItem(BELLS_OF_IRELAND, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bells_of_ireland"), new BlockItem(GROWN_BELLS_OF_IRELAND, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bog_rosemary"), new BlockItem(BOG_ROSEMARY, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bog_rosemary"), new BlockItem(GROWN_BOG_ROSEMARY, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bulbous_buttercup"), new BlockItem(BULBOUS_BUTTERCUP, new Item.Settings()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bulbous_buttercup"), new BlockItem(GROWN_BULBOUS_BUTTERCUP, new Item.Settings()));
        });

        registerComposting();
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
        });

        event.register(RegistryKeys.ITEM, helper -> {
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bells_of_ireland"), new BlockItem(BELLS_OF_IRELAND, new Item.Settings().registryKey(itemId("bells_of_ireland")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bells_of_ireland"), new BlockItem(GROWN_BELLS_OF_IRELAND, new Item.Settings().registryKey(itemId("grown_bells_of_ireland")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bog_rosemary"), new BlockItem(BOG_ROSEMARY, new Item.Settings().registryKey(itemId("bog_rosemary")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bog_rosemary"), new BlockItem(GROWN_BOG_ROSEMARY, new Item.Settings().registryKey(itemId("grown_bog_rosemary")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "bulbous_buttercup"), new BlockItem(BULBOUS_BUTTERCUP, new Item.Settings().registryKey(itemId("bulbous_buttercup")).useBlockPrefixedTranslationKey()));
            helper.register(Identifier.of(EmeraldIsleFlora.MOD_ID, "grown_bulbous_buttercup"), new BlockItem(GROWN_BULBOUS_BUTTERCUP, new Item.Settings().registryKey(itemId("grown_bulbous_buttercup")).useBlockPrefixedTranslationKey()));
        });

        registerComposting();
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
        });

        event.register(Registries.ITEM, helper -> {
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "bells_of_ireland"), new BlockItem(BELLS_OF_IRELAND, new Item.Properties().setId(itemId("bells_of_ireland")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "grown_bells_of_ireland"), new BlockItem(GROWN_BELLS_OF_IRELAND, new Item.Properties().setId(itemId("grown_bells_of_ireland")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "bog_rosemary"), new BlockItem(BOG_ROSEMARY, new Item.Properties().setId(itemId("bog_rosemary")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "grown_bog_rosemary"), new BlockItem(GROWN_BOG_ROSEMARY, new Item.Properties().setId(itemId("grown_bog_rosemary")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "bulbous_buttercup"), new BlockItem(BULBOUS_BUTTERCUP, new Item.Properties().setId(itemId("bulbous_buttercup")).useBlockDescriptionPrefix()));
            helper.register(Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "grown_bulbous_buttercup"), new BlockItem(GROWN_BULBOUS_BUTTERCUP, new Item.Properties().setId(itemId("grown_bulbous_buttercup")).useBlockDescriptionPrefix()));
        });

        registerComposting();
        registerFlowerPotPlants();
    }
    */
    /*?}*/

    private static void registerComposting() {
        /*? if <26.2 {*/
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(BELLS_OF_IRELAND, 0.65f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(GROWN_BELLS_OF_IRELAND, 0.95f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(BOG_ROSEMARY, 0.65f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(GROWN_BOG_ROSEMARY, 0.95f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(BULBOUS_BUTTERCUP, 0.65f);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(GROWN_BULBOUS_BUTTERCUP, 0.95f);
        /*?} else {*/
        /*
        // 26.2: same Object2FloatMap<ItemLike>, field renamed ITEM_TO_LEVEL_INCREASE_CHANCE -> COMPOSTABLES.
        ComposterBlock.COMPOSTABLES.put(BELLS_OF_IRELAND, 0.65f);
        ComposterBlock.COMPOSTABLES.put(GROWN_BELLS_OF_IRELAND, 0.95f);
        ComposterBlock.COMPOSTABLES.put(BOG_ROSEMARY, 0.65f);
        ComposterBlock.COMPOSTABLES.put(GROWN_BOG_ROSEMARY, 0.95f);
        ComposterBlock.COMPOSTABLES.put(BULBOUS_BUTTERCUP, 0.65f);
        ComposterBlock.COMPOSTABLES.put(GROWN_BULBOUS_BUTTERCUP, 0.95f);*/
        /*?}*/
    }

    // NeoForge-only: NeoForge patches FlowerPotBlock away from vanilla's simple
    // construct-time "CONTENT_TO_POTTED.put(content, this)" toward a registry-key-keyed
    // per-instance "fullPots" map, exposed via addPlant(Identifier, Supplier<Block>) on
    // the canonical empty pot (Blocks.FLOWER_POT). The plain FlowerPotBlock(Block,
    // Settings) constructor we use above for POTTED_* *does* try to auto-register into
    // this map, but it does so by looking up the content block's registry key at
    // *construction* time (BuiltInRegistries.BLOCK.getKey(content)) - since our blocks
    // are constructed as eager static fields before ModBlocks.onRegister ever runs,
    // that lookup finds nothing and the auto-registration silently captures the wrong
    // key. Registering explicitly here, after we already know the real ids, fixes it.
    // Confirmed via decompiling NeoForge's actual (Mojmap) shipped FlowerPotBlock class -
    // this method does not exist on vanilla/Fabric's FlowerPotBlock at all.
    //
    // Confirmed still present, same shape, for 26.2 too - decompiled the real patched
    // minecraft-merged jar from a 26.2-neoforge Loom build
    // (neoforge-26.2.0.49-beta-minecraft-merged-deobf-26.2.jar) rather than assuming the
    // patch survived, per the plan's explicit flag that this needed a live build to
    // check (NeoForge ships its vanilla patches as a binary diff applied only during the
    // real NeoForm/Loom pipeline, invisible to static jar-only research). addPlant() and
    // getEmptyPot() are both still there, unchanged shape (just Identifier now being the
    // Mojmap resources.Identifier instead of Yarn's util.Identifier).
    // Split into version variants (rather than one body with a nested Stonecutter
    // conditional) for the same reason onRegister() above is split: this whole method is
    // already one big disabled block comment when neoforge is inactive, and nesting a
    // second /*? ... ?*/ marker inside disabled block-comment text doesn't work - it's
    // both invisible to Stonecutter's scanner and, worse, a literal Java block comment
    // can't nest, so an inner "/*? if ... {*/" would prematurely close the outer comment.
    /*? if neoforge && <26.2 {*/
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
        }
    }*/
    /*?}*/
}
