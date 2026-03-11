/*    */ package net.minecraft.world.entity.ambient;
/*    */ 
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public abstract class AmbientCreature extends Mob {
/*    */   protected AmbientCreature(EntityType<? extends AmbientCreature> $$0, Level $$1) {
/* 10 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBeLeashed(Player $$0) {
/* 15 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ambient\AmbientCreature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */