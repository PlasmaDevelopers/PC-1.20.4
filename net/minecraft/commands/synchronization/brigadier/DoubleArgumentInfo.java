/*    */ package net.minecraft.commands.synchronization.brigadier;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.DoubleArgumentType;
/*    */ import net.minecraft.commands.CommandBuildContext;
/*    */ import net.minecraft.commands.synchronization.ArgumentTypeInfo;
/*    */ import net.minecraft.commands.synchronization.ArgumentUtils;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ public class DoubleArgumentInfo
/*    */   implements ArgumentTypeInfo<DoubleArgumentType, DoubleArgumentInfo.Template> {
/*    */   public final class Template
/*    */     implements ArgumentTypeInfo.Template<DoubleArgumentType> {
/*    */     final double min;
/*    */     final double max;
/*    */     
/*    */     Template(double $$1, double $$2) {
/* 19 */       this.min = $$1;
/* 20 */       this.max = $$2;
/*    */     }
/*    */ 
/*    */     
/*    */     public DoubleArgumentType instantiate(CommandBuildContext $$0) {
/* 25 */       return DoubleArgumentType.doubleArg(this.min, this.max);
/*    */     }
/*    */ 
/*    */     
/*    */     public ArgumentTypeInfo<DoubleArgumentType, ?> type() {
/* 30 */       return DoubleArgumentInfo.this;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void serializeToNetwork(Template $$0, FriendlyByteBuf $$1) {
/* 36 */     boolean $$2 = ($$0.min != -1.7976931348623157E308D);
/* 37 */     boolean $$3 = ($$0.max != Double.MAX_VALUE);
/* 38 */     $$1.writeByte(ArgumentUtils.createNumberFlags($$2, $$3));
/* 39 */     if ($$2) {
/* 40 */       $$1.writeDouble($$0.min);
/*    */     }
/* 42 */     if ($$3) {
/* 43 */       $$1.writeDouble($$0.max);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Template deserializeFromNetwork(FriendlyByteBuf $$0) {
/* 49 */     byte $$1 = $$0.readByte();
/* 50 */     double $$2 = ArgumentUtils.numberHasMin($$1) ? $$0.readDouble() : -1.7976931348623157E308D;
/* 51 */     double $$3 = ArgumentUtils.numberHasMax($$1) ? $$0.readDouble() : Double.MAX_VALUE;
/* 52 */     return new Template($$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public void serializeToJson(Template $$0, JsonObject $$1) {
/* 57 */     if ($$0.min != -1.7976931348623157E308D) {
/* 58 */       $$1.addProperty("min", Double.valueOf($$0.min));
/*    */     }
/* 60 */     if ($$0.max != Double.MAX_VALUE) {
/* 61 */       $$1.addProperty("max", Double.valueOf($$0.max));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Template unpack(DoubleArgumentType $$0) {
/* 67 */     return new Template($$0.getMinimum(), $$0.getMaximum());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\synchronization\brigadier\DoubleArgumentInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */