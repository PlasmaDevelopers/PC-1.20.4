/*    */ package net.minecraft.world.level.timers;
/*    */ 
/*    */ import net.minecraft.commands.functions.CommandFunction;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.server.ServerFunctionManager;
/*    */ 
/*    */ public class FunctionCallback
/*    */   implements TimerCallback<MinecraftServer> {
/*    */   public FunctionCallback(ResourceLocation $$0) {
/* 12 */     this.functionId = $$0;
/*    */   }
/*    */   final ResourceLocation functionId;
/*    */   
/*    */   public void handle(MinecraftServer $$0, TimerQueue<MinecraftServer> $$1, long $$2) {
/* 17 */     ServerFunctionManager $$3 = $$0.getFunctions();
/* 18 */     $$3.get(this.functionId).ifPresent($$1 -> $$0.execute($$1, $$0.getGameLoopSender()));
/*    */   }
/*    */   
/*    */   public static class Serializer extends TimerCallback.Serializer<MinecraftServer, FunctionCallback> {
/*    */     public Serializer() {
/* 23 */       super(new ResourceLocation("function"), FunctionCallback.class);
/*    */     }
/*    */ 
/*    */     
/*    */     public void serialize(CompoundTag $$0, FunctionCallback $$1) {
/* 28 */       $$0.putString("Name", $$1.functionId.toString());
/*    */     }
/*    */ 
/*    */     
/*    */     public FunctionCallback deserialize(CompoundTag $$0) {
/* 33 */       ResourceLocation $$1 = new ResourceLocation($$0.getString("Name"));
/* 34 */       return new FunctionCallback($$1);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\timers\FunctionCallback.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */