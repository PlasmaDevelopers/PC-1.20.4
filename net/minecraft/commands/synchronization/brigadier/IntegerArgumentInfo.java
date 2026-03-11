/*    */ package net.minecraft.commands.synchronization.brigadier;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*    */ import net.minecraft.commands.CommandBuildContext;
/*    */ import net.minecraft.commands.synchronization.ArgumentTypeInfo;
/*    */ import net.minecraft.commands.synchronization.ArgumentUtils;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ public class IntegerArgumentInfo
/*    */   implements ArgumentTypeInfo<IntegerArgumentType, IntegerArgumentInfo.Template> {
/*    */   public final class Template
/*    */     implements ArgumentTypeInfo.Template<IntegerArgumentType> {
/*    */     final int min;
/*    */     final int max;
/*    */     
/*    */     Template(int $$1, int $$2) {
/* 19 */       this.min = $$1;
/* 20 */       this.max = $$2;
/*    */     }
/*    */ 
/*    */     
/*    */     public IntegerArgumentType instantiate(CommandBuildContext $$0) {
/* 25 */       return IntegerArgumentType.integer(this.min, this.max);
/*    */     }
/*    */ 
/*    */     
/*    */     public ArgumentTypeInfo<IntegerArgumentType, ?> type() {
/* 30 */       return IntegerArgumentInfo.this;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void serializeToNetwork(Template $$0, FriendlyByteBuf $$1) {
/* 36 */     boolean $$2 = ($$0.min != Integer.MIN_VALUE);
/* 37 */     boolean $$3 = ($$0.max != Integer.MAX_VALUE);
/* 38 */     $$1.writeByte(ArgumentUtils.createNumberFlags($$2, $$3));
/* 39 */     if ($$2) {
/* 40 */       $$1.writeInt($$0.min);
/*    */     }
/* 42 */     if ($$3) {
/* 43 */       $$1.writeInt($$0.max);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Template deserializeFromNetwork(FriendlyByteBuf $$0) {
/* 49 */     byte $$1 = $$0.readByte();
/* 50 */     int $$2 = ArgumentUtils.numberHasMin($$1) ? $$0.readInt() : Integer.MIN_VALUE;
/* 51 */     int $$3 = ArgumentUtils.numberHasMax($$1) ? $$0.readInt() : Integer.MAX_VALUE;
/* 52 */     return new Template($$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public void serializeToJson(Template $$0, JsonObject $$1) {
/* 57 */     if ($$0.min != Integer.MIN_VALUE) {
/* 58 */       $$1.addProperty("min", Integer.valueOf($$0.min));
/*    */     }
/* 60 */     if ($$0.max != Integer.MAX_VALUE) {
/* 61 */       $$1.addProperty("max", Integer.valueOf($$0.max));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Template unpack(IntegerArgumentType $$0) {
/* 67 */     return new Template($$0.getMinimum(), $$0.getMaximum());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\synchronization\brigadier\IntegerArgumentInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */