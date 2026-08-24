package net.alternateearth.emeraldisleflora.registry;

/*? if <1.21.11 {*/
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

/**
 * Places a {@link ModBoatEntity}/{@link ModChestBoatEntity} on right-click, standing in for
 * vanilla's own {@code BoatItem} whose {@code createEntity} is hardcoded to vanilla's boat
 * classes - see {@link ModBoatEntity}'s doc-comment for why that matters here.
 */
public class ModBoatItem extends Item {

    private final boolean chest;

    public ModBoatItem(boolean chest, Settings settings) {
        super(settings);
        this.chest = chest;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        HitResult hitResult = this.raycast(world, player, RaycastContext.FluidHandling.ANY);
        if (hitResult.getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(stack);
        }

        Vec3d viewVector = player.getRotationVec(1.0F);
        List<Entity> nearbyEntities = world.getOtherEntities(player, player.getBoundingBox().stretch(viewVector.multiply(5.0)).expand(1.0), EntityPredicates.EXCEPT_SPECTATOR);
        if (!nearbyEntities.isEmpty()) {
            Vec3d eyePos = player.getEyePos();
            for (Entity entity : nearbyEntities) {
                Box box = entity.getBoundingBox().expand(entity.getTargetingMargin());
                if (box.contains(eyePos)) {
                    return TypedActionResult.pass(stack);
                }
            }
        }

        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return TypedActionResult.pass(stack);
        }

        BoatEntity boat = this.chest
                ? new ModChestBoatEntity(world, hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z)
                : new ModBoatEntity(world, hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z);
        boat.setYaw(player.getYaw());

        if (!world.isSpaceEmpty(boat, boat.getBoundingBox())) {
            return TypedActionResult.fail(stack);
        }

        if (!world.isClient()) {
            world.spawnEntity(boat);
            world.emitGameEvent(player, GameEvent.ENTITY_PLACE, hitResult.getPos());
            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }

        player.incrementStat(Stats.USED.getOrCreateStat(this));
        return TypedActionResult.success(stack, world.isClient());
    }
}
/*?}*/
