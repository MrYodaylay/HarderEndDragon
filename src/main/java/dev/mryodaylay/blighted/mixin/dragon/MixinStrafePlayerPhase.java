package dev.mryodaylay.blighted.mixin.dragon;

import dev.mryodaylay.blighted.entity.dragon.IEnderDragonEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractPhase;
import net.minecraft.entity.boss.dragon.phase.StrafePlayerPhase;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StrafePlayerPhase.class)
public abstract class MixinStrafePlayerPhase extends AbstractPhase {

    @Shadow
    private LivingEntity target;


    public MixinStrafePlayerPhase(EnderDragonEntity dragon) {
        super(dragon);
    }


    @Redirect(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
    public boolean cancelSpawnFireball(World instance, Entity entity) {
        return false;
    }


    @Inject(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z", shift = At.Shift.AFTER))
    public void spawnFireballs(CallbackInfo ci) {
        ((IEnderDragonEntity) dragon).blighted$setFireballTarget(this.target, dragon.getWorld().getRandom().nextBetween(3, 5));
    }
}
