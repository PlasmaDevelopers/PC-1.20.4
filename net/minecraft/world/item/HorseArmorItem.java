/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class HorseArmorItem
/*    */   extends Item {
/*    */   private static final String TEX_FOLDER = "textures/entity/horse/";
/*    */   private final int protection;
/*    */   private final String texture;
/*    */   
/*    */   public HorseArmorItem(int $$0, String $$1, Item.Properties $$2) {
/* 12 */     super($$2);
/* 13 */     this.protection = $$0;
/* 14 */     this.texture = "textures/entity/horse/armor/horse_armor_" + $$1 + ".png";
/*    */   }
/*    */   
/*    */   public ResourceLocation getTexture() {
/* 18 */     return new ResourceLocation(this.texture);
/*    */   }
/*    */   
/*    */   public int getProtection() {
/* 22 */     return this.protection;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\HorseArmorItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */