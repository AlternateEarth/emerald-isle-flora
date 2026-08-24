package net.alternateearth.emeraldisleflora.registry;

/*
 * Only needed on targets whose vanilla BoatEntity still uses the closed 9-value Type enum
 * (<1.21.11) - see ModEntities' doc-comment. At >=1.21.11 vanilla's own BoatEntity/Boat takes
 * an arbitrary Supplier<Item>, so no subclass is needed there and this whole file compiles to
 * nothing.
 */
/*? if <1.21.11 {*/
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.world.World;

/**
 * A single-wood-type boat, standing in for vanilla's {@link BoatEntity} whose own
 * {@link BoatEntity.Type} is a closed 9-value enum modders can't add to. Tracks its own
 * variant in a private {@link TrackedData} rather than touching vanilla's, since vanilla's
 * {@code BOAT_TYPE} tracked data resolves through {@code BoatEntity.Type}'s own (also closed)
 * ordinal table - reusing it here would let an out-of-range ordinal desync client and server.
 */
public class ModBoatEntity extends BoatEntity {

    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(ModBoatEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public ModBoatEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    public ModBoatEntity(World world, double x, double y, double z) {
        this(ModEntities.YEW_BOAT, world);
        this.setPos(x, y, z);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
    }

    @Override
    public Item asItem() {
        return ModItems.YEW_BOAT;
    }

    /*? if <1.21 {*/
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, Variant.YEW.ordinal());
    }
    /*?} else {*/
    /*@Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, Variant.YEW.ordinal());
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
            this.setModVariant(Variant.byName(nbt.getString("Type")));
        }
    }

    public void setModVariant(Variant variant) {
        this.dataTracker.set(VARIANT, variant.ordinal());
    }

    public Variant getModVariant() {
        return Variant.byId(this.dataTracker.get(VARIANT));
    }

    /** Only one constant for now - mirrors vanilla's own {@code BoatEntity.Type} shape so a future second wood type is a one-line addition. */
    public enum Variant implements StringIdentifiable {
        YEW("yew");

        private final String name;

        Variant(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.name;
        }

        public static Variant byId(int id) {
            Variant[] values = values();
            return id >= 0 && id < values.length ? values[id] : YEW;
        }

        public static Variant byName(String name) {
            for (Variant variant : values()) {
                if (variant.name.equals(name)) {
                    return variant;
                }
            }
            return YEW;
        }
    }
}
/*?}*/
