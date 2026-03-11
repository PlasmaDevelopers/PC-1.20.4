/*    */ package net.minecraft.world.level.storage.loot.parameters;
/*    */ 
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class LootContextParam<T> {
/*    */   private final ResourceLocation name;
/*    */   
/*    */   public LootContextParam(ResourceLocation $$0) {
/*  9 */     this.name = $$0;
/*    */   }
/*    */   
/*    */   public ResourceLocation getName() {
/* 13 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 18 */     return "<parameter " + this.name + ">";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\parameters\LootContextParam.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */