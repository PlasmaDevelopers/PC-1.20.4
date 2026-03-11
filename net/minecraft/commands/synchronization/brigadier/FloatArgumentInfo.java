/*    */ package net.minecraft.commands.synchronization.brigadier;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.FloatArgumentType;
/*    */ import net.minecraft.commands.CommandBuildContext;
/*    */ import net.minecraft.commands.synchronization.ArgumentTypeInfo;
/*    */ import net.minecraft.commands.synchronization.ArgumentUtils;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ public class FloatArgumentInfo
/*    */   implements ArgumentTypeInfo<FloatArgumentType, FloatArgumentInfo.Template> {
/*    */   public final class Template
/*    */     implements ArgumentTypeInfo.Template<FloatArgumentType> {
/*    */     final float min;
/*    */     final float max;
/*    */     
/*    */     Template(float $$1, float $$2) {
/* 19 */       this.min = $$1;
/* 20 */       this.max = $$2;
/*    */     }
/*    */ 
/*    */     
/*    */     public FloatArgumentType instantiate(CommandBuildContext $$0) {
/* 25 */       return FloatArgumentType.floatArg(this.min, this.max);
/*    */     }
/*    */ 
/*    */     
/*    */     public ArgumentTypeInfo<FloatArgumentType, ?> type() {
/* 30 */       return FloatArgumentInfo.this;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void serializeToNetwork(Template $$0, FriendlyByteBuf $$1) {
/* 36 */     boolean $$2 = ($$0.min != -3.4028235E38F);
/* 37 */     boolean $$3 = ($$0.max != Float.MAX_VALUE);
/* 38 */     $$1.writeByte(ArgumentUtils.createNumberFlags($$2, $$3));
/* 39 */     if ($$2) {
/* 40 */       $$1.writeFloat($$0.min);
/*    */     }
/* 42 */     if ($$3) {
/* 43 */       $$1.writeFloat($$0.max);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Template deserializeFromNetwork(FriendlyByteBuf $$0) {
/* 49 */     byte $$1 = $$0.readByte();
/* 50 */     float $$2 = ArgumentUtils.numberHasMin($$1) ? $$0.readFloat() : -3.4028235E38F;
/* 51 */     float $$3 = ArgumentUtils.numberHasMax($$1) ? $$0.readFloat() : Float.MAX_VALUE;
/* 52 */     return new Template($$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public void serializeToJson(Template $$0, JsonObject $$1) {
/* 57 */     if ($$0.min != -3.4028235E38F) {
/* 58 */       $$1.addProperty("min", Float.valueOf($$0.min));
/*    */     }
/* 60 */     if ($$0.max != Float.MAX_VALUE) {
/* 61 */       $$1.addProperty("max", Float.valueOf($$0.max));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Template unpack(FloatArgumentType $$0) {
/* 67 */     return new Template($$0.getMinimum(), $$0.getMaximum());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\synchronization\brigadier\FloatArgumentInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */