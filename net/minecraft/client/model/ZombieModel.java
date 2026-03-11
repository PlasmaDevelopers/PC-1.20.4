/*    */ package net.minecraft.client.model;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.world.entity.monster.Monster;
/*    */ import net.minecraft.world.entity.monster.Zombie;
/*    */ 
/*    */ public class ZombieModel<T extends Zombie> extends AbstractZombieModel<T> {
/*    */   public ZombieModel(ModelPart $$0) {
/*  8 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAggressive(T $$0) {
/* 13 */     return $$0.isAggressive();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\ZombieModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */