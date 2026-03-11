/*    */ package net.minecraft.world.level.timers;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.functions.CommandFunction;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.server.ServerFunctionManager;
/*    */ 
/*    */ public class FunctionTagCallback
/*    */   implements TimerCallback<MinecraftServer> {
/*    */   final ResourceLocation tagId;
/*    */   
/*    */   public FunctionTagCallback(ResourceLocation $$0) {
/* 16 */     this.tagId = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(MinecraftServer $$0, TimerQueue<MinecraftServer> $$1, long $$2) {
/* 21 */     ServerFunctionManager $$3 = $$0.getFunctions();
/* 22 */     Collection<CommandFunction<CommandSourceStack>> $$4 = $$3.getTag(this.tagId);
/* 23 */     for (CommandFunction<CommandSourceStack> $$5 : $$4)
/* 24 */       $$3.execute($$5, $$3.getGameLoopSender()); 
/*    */   }
/*    */   
/*    */   public static class Serializer
/*    */     extends TimerCallback.Serializer<MinecraftServer, FunctionTagCallback> {
/*    */     public Serializer() {
/* 30 */       super(new ResourceLocation("function_tag"), FunctionTagCallback.class);
/*    */     }
/*    */ 
/*    */     
/*    */     public void serialize(CompoundTag $$0, FunctionTagCallback $$1) {
/* 35 */       $$0.putString("Name", $$1.tagId.toString());
/*    */     }
/*    */ 
/*    */     
/*    */     public FunctionTagCallback deserialize(CompoundTag $$0) {
/* 40 */       ResourceLocation $$1 = new ResourceLocation($$0.getString("Name"));
/* 41 */       return new FunctionTagCallback($$1);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\timers\FunctionTagCallback.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */