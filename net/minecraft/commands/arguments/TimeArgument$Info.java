/*     */ package net.minecraft.commands.arguments;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import net.minecraft.commands.CommandBuildContext;
/*     */ import net.minecraft.commands.synchronization.ArgumentTypeInfo;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Info
/*     */   implements ArgumentTypeInfo<TimeArgument, TimeArgument.Info.Template>
/*     */ {
/*     */   public final class Template
/*     */     implements ArgumentTypeInfo.Template<TimeArgument>
/*     */   {
/*     */     final int min;
/*     */     
/*     */     Template(int $$1) {
/*  93 */       this.min = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public TimeArgument instantiate(CommandBuildContext $$0) {
/*  98 */       return TimeArgument.time(this.min);
/*     */     }
/*     */ 
/*     */     
/*     */     public ArgumentTypeInfo<TimeArgument, ?> type() {
/* 103 */       return TimeArgument.Info.this;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeToNetwork(Template $$0, FriendlyByteBuf $$1) {
/* 109 */     $$1.writeInt($$0.min);
/*     */   }
/*     */ 
/*     */   
/*     */   public Template deserializeFromNetwork(FriendlyByteBuf $$0) {
/* 114 */     int $$1 = $$0.readInt();
/* 115 */     return new Template($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeToJson(Template $$0, JsonObject $$1) {
/* 120 */     $$1.addProperty("min", Integer.valueOf($$0.min));
/*     */   }
/*     */ 
/*     */   
/*     */   public Template unpack(TimeArgument $$0) {
/* 125 */     return new Template($$0.minimum);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\TimeArgument$Info.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */