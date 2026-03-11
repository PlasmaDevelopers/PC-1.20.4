/*    */ package net.minecraft.world.entity.player;
/*    */ 
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public enum PlayerModelPart {
/*  6 */   CAPE(0, "cape"),
/*  7 */   JACKET(1, "jacket"),
/*  8 */   LEFT_SLEEVE(2, "left_sleeve"),
/*  9 */   RIGHT_SLEEVE(3, "right_sleeve"),
/* 10 */   LEFT_PANTS_LEG(4, "left_pants_leg"),
/* 11 */   RIGHT_PANTS_LEG(5, "right_pants_leg"),
/* 12 */   HAT(6, "hat");
/*    */   
/*    */   private final int bit;
/*    */   
/*    */   private final int mask;
/*    */   private final String id;
/*    */   private final Component name;
/*    */   
/*    */   PlayerModelPart(int $$0, String $$1) {
/* 21 */     this.bit = $$0;
/* 22 */     this.mask = 1 << $$0;
/* 23 */     this.id = $$1;
/* 24 */     this.name = (Component)Component.translatable("options.modelPart." + $$1);
/*    */   }
/*    */   
/*    */   public int getMask() {
/* 28 */     return this.mask;
/*    */   }
/*    */   
/*    */   public int getBit() {
/* 32 */     return this.bit;
/*    */   }
/*    */   
/*    */   public String getId() {
/* 36 */     return this.id;
/*    */   }
/*    */   
/*    */   public Component getName() {
/* 40 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\player\PlayerModelPart.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */