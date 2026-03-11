/*    */ package net.minecraft.world.level.block.state.properties;
/*    */ 
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum StructureMode implements StringRepresentable {
/*  7 */   SAVE("save"),
/*  8 */   LOAD("load"),
/*  9 */   CORNER("corner"),
/* 10 */   DATA("data");
/*    */   
/*    */   private final String name;
/*    */   
/*    */   private final Component displayName;
/*    */   
/*    */   StructureMode(String $$0) {
/* 17 */     this.name = $$0;
/* 18 */     this.displayName = (Component)Component.translatable("structure_block.mode_info." + $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 23 */     return this.name;
/*    */   }
/*    */   
/*    */   public Component getDisplayName() {
/* 27 */     return this.displayName;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\properties\StructureMode.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */