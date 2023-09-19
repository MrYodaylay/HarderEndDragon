package dev.mryodaylay.blighted.mixin.projectile;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DragonFireballEntity.class)
public class MixinDragonFireballEntity extends ExplosiveProjectileEntity {


    protected MixinDragonFireballEntity(EntityType<? extends ExplosiveProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * Prevents the dragon from hitting itself or an obsidian tower by disabling collision for the first 20 ticks
     * of the fireball's lifespan
     */
    @Inject(method = "onCollision", at = @At("HEAD"), cancellable = true)
    private void onCollisionPreventSelfHit(HitResult hitResult, CallbackInfo ci) {

        if (this.age < 20) {
            ci.cancel();
        }

    }

    /**
     * Creates an explosion when the fireball impacts either a PlayerEntity or a block of End Stone.
     */
    @Inject(method = "onCollision", at = @At("TAIL"))
    private void onCollision(HitResult hitResult, CallbackInfo ci) {

        World world = this.getWorld();

        if (hitResult instanceof EntityHitResult) {

            createExplosion(this.getX(), this.getY() - 0.5, this.getZ());

        } else if (hitResult instanceof BlockHitResult) {

            BlockPos blockHitPos = ((BlockHitResult) hitResult).getBlockPos();
            Block blockHit = world.getBlockState(blockHitPos).getBlock();

            if (blockHit == Blocks.END_STONE) {
                world.removeBlock(blockHitPos, false);
                createExplosion(this.getX(), this.getY(), this.getZ());
            }

        }

    }

    @Unique
    private void createExplosion(double x, double y, double z) {
        this.getWorld().createExplosion(this, x, y, z, 8, false, World.ExplosionSourceType.MOB );
    }
}
