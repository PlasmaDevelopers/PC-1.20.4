/*    */ package net.minecraft.world.entity;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.level.EntityGetter;
/*    */ 
/*    */ public interface OwnableEntity
/*    */ {
/*    */   @Nullable
/*    */   UUID getOwnerUUID();
/*    */   
/*    */   EntityGetter level();
/*    */   
/*    */   @Nullable
/*    */   default LivingEntity getOwner() {
/* 16 */     UUID $$0 = getOwnerUUID();
/* 17 */     if ($$0 == null) {
/* 18 */       return null;
/*    */     }
/* 20 */     return (LivingEntity)level().getPlayerByUUID($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\OwnableEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */