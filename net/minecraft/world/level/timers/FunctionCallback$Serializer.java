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
/*    */ public class Serializer
/*    */   extends TimerCallback.Serializer<MinecraftServer, FunctionCallback>
/*    */ {
/*    */   public Serializer() {
/* 23 */     super(new ResourceLocation("function"), FunctionCallback.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(CompoundTag $$0, FunctionCallback $$1) {
/* 28 */     $$0.putString("Name", $$1.functionId.toString());
/*    */   }
/*    */ 
/*    */   
/*    */   public FunctionCallback deserialize(CompoundTag $$0) {
/* 33 */     ResourceLocation $$1 = new ResourceLocation($$0.getString("Name"));
/* 34 */     return new FunctionCallback($$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\timers\FunctionCallback$Serializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */