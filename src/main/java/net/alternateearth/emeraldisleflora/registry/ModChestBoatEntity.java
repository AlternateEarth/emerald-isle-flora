package net.alternateearth.emeraldisleflora.registry;

/*? if <1.21.11 {*/
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.world.World;

/** Chest-boat counterpart to {@link ModBoatEntity} - see its doc-comment for why this subclass exists at all. */
public class ModChestBoatEntity extends ChestBoatEntity {

    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(ModChestBoatEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public ModChestBoatEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    public ModChestBoatEntity(World world, double x, double y, double z) {
        this(ModEntities.YEW_CHEST_BOAT, world);
        // setPosition (not setPos) also recalculates the bounding box - without it the boat spawns
        // with a stale (default-origin) collision box and visually sinks into whatever it's placed on.
        this.setPosition(x, y, z);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
    }

    @Override
    public Item asItem() {
        return ModItems.YEW_CHEST_BOAT;
    }

    /*? if <1.21 {*/
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, ModBoatEntity.Variant.YEW.ordinal());
    }
    /*?} else {*/
    /*@Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, ModBoatEntity.Variant.YEW.ordinal());
    }*/
    /*?}*/

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("Type", this.getModVariant().asString());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Type", NbtElement.STRING_TYPE)) {
            this.setModVariant(ModBoatEntity.Variant.byName(nbt.getString("Type")));
        }
    }

    public void setModVariant(ModBoatEntity.Variant variant) {
        this.dataTracker.set(VARIANT, variant.ordinal());
    }

    public ModBoatEntity.Variant getModVariant() {
        return ModBoatEntity.Variant.byId(this.dataTracker.get(VARIANT));
    }
}
/*?}*/
