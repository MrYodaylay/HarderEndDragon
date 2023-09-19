package dev.mryodaylay.blighted.mixin.dragon;

import dev.mryodaylay.blighted.entity.dragon.IEnderDragonEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EnderDragonEntity.class)
public class MixinEnderDragonEntity extends MobEntity implements IEnderDragonEntity {

    @Shadow
    public EndCrystalEntity connectedCrystal;
    @Final
    @Shadow
    public EnderDragonPart head;

    @Unique
    private boolean shielded = false;
    @Unique
    private Vec3d lastPosition = null;
    @Unique
    private LivingEntity target = null;
    @Unique
    private int remainingFireballCount = 0;


    protected MixinEnderDragonEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }


    @ModifyConstant(
            method = "createEnderDragonAttributes",
            constant = @Constant(doubleValue = 200.0),
            require = 1
    )
    private static double modifyDragonHealth(double constant) {
        return 600.0;
    }

    /**
     * @author MrYodaylay
     * @reason End crystals now grant the dragon invincibility, not health regen
     */
    @Overwrite
    private void tickWithEndCrystals() {
        if (this.connectedCrystal != null) {

            if (this.connectedCrystal.isRemoved()) {
                this.connectedCrystal = null;
                return;
            }

            this.shielded = true;

        } else {
            this.shielded = false;
        }

        // If shielded, make the dragon invulnerable
        this.setInvulnerable(this.shielded);

        // Connect to nearest crystal, adapted from EnderDragonEntity.java
        List<EndCrystalEntity> nearbyCrystals = this.getWorld().getNonSpectatingEntities(EndCrystalEntity.class, this.getBoundingBox().expand(40.0));

        if (nearbyCrystals.isEmpty()) {
            this.connectedCrystal = null;
            return;
        }

        EndCrystalEntity nearestCrystal = null;
        double nearestDistance = Double.MAX_VALUE;

        for (EndCrystalEntity tempCrystal : nearbyCrystals) {
            double tempDistance = tempCrystal.distanceTo(this);
            if (tempDistance < nearestDistance) {
                nearestDistance = tempDistance;
                nearestCrystal = tempCrystal;
            }
        }

        this.connectedCrystal = nearestCrystal;


    }

    @Inject(method = "tickMovement", at = @At(value = "HEAD"))
    public void tickMovement(CallbackInfo ci) {

        World world = getWorld();
        Random random = world.getRandom();
        MinecraftServer server = world.getServer();

        if (server == null) {
            return;
        }

        if (target != null && remainingFireballCount > 0 && server.getTicks() % 5 == 0) {

            // Fireball spawning adapted from StrafePlayerPhase.java
            Vec3d vec3d3 = this.getRotationVec(1.0F);
            double headXPos = this.head.getX() - vec3d3.x;
            double headYPos = this.head.getBodyY(0.5) + 0.5;
            double headZPos = this.head.getZ() - vec3d3.z;

            // Offset target based on dragon movement so that fireballs hit in a line pattern
            double headXPosDelta = (headXPos - lastPosition.x) / 2.0;
            double headZPosDelta = (headZPos - lastPosition.z) / 2.0;

            double dx = (this.target.getX() + headXPosDelta) - headXPos + (random.nextDouble());
            double dy = this.target.getBodyY(0.5) - headYPos;
            double dz = (this.target.getZ() + headZPosDelta) - headZPos + (random.nextDouble());

            DragonFireballEntity dragonFireballEntity = new DragonFireballEntity(this.getWorld(), this, dx, dy, dz);
            dragonFireballEntity.refreshPositionAndAngles(headXPos, headYPos, headZPos, 0.0F, 0.0F);

            world.spawnEntity(dragonFireballEntity);

            if (!this.isSilent()) {
                this.getWorld().syncWorldEvent((PlayerEntity)null, WorldEvents.ENDER_DRAGON_SHOOTS, this.getBlockPos(), 0);
            }

            remainingFireballCount--;

        }

    }

    @Override
    public void blighted$setFireballTarget(LivingEntity target, int fireballCount) {
        this.lastPosition = this.head.getPos();
        this.target = target;
        this.remainingFireballCount = fireballCount;
    }
}
