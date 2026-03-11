/*    */ package net.minecraft.world.level.timers;
/*    */ 
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Serializer<T, C extends TimerCallback<T>>
/*    */ {
/*    */   private final ResourceLocation id;
/*    */   private final Class<?> cls;
/*    */   
/*    */   public Serializer(ResourceLocation $$0, Class<?> $$1) {
/* 15 */     this.id = $$0;
/* 16 */     this.cls = $$1;
/*    */   }
/*    */   
/*    */   public ResourceLocation getId() {
/* 20 */     return this.id;
/*    */   }
/*    */   
/*    */   public Class<?> getCls() {
/* 24 */     return this.cls;
/*    */   }
/*    */   
/*    */   public abstract void serialize(CompoundTag paramCompoundTag, C paramC);
/*    */   
/*    */   public abstract C deserialize(CompoundTag paramCompoundTag);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\timers\TimerCallback$Serializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */