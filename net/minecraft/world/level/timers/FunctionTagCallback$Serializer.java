/*    */ package net.minecraft.world.level.timers;
/*    */ 
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Serializer
/*    */   extends TimerCallback.Serializer<MinecraftServer, FunctionTagCallback>
/*    */ {
/*    */   public Serializer() {
/* 30 */     super(new ResourceLocation("function_tag"), FunctionTagCallback.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(CompoundTag $$0, FunctionTagCallback $$1) {
/* 35 */     $$0.putString("Name", $$1.tagId.toString());
/*    */   }
/*    */ 
/*    */   
/*    */   public FunctionTagCallback deserialize(CompoundTag $$0) {
/* 40 */     ResourceLocation $$1 = new ResourceLocation($$0.getString("Name"));
/* 41 */     return new FunctionTagCallback($$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\timers\FunctionTagCallback$Serializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */