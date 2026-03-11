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
/*     */   implements ArgumentTypeInfo<ScoreHolderArgument, ScoreHolderArgument.Info.Template>
/*     */ {
/*     */   private static final byte FLAG_MULTIPLE = 1;
/*     */   
/*     */   public final class Template
/*     */     implements ArgumentTypeInfo.Template<ScoreHolderArgument>
/*     */   {
/*     */     final boolean multiple;
/*     */     
/*     */     Template(boolean $$1) {
/* 198 */       this.multiple = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public ScoreHolderArgument instantiate(CommandBuildContext $$0) {
/* 203 */       return new ScoreHolderArgument(this.multiple);
/*     */     }
/*     */ 
/*     */     
/*     */     public ArgumentTypeInfo<ScoreHolderArgument, ?> type() {
/* 208 */       return ScoreHolderArgument.Info.this;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeToNetwork(Template $$0, FriendlyByteBuf $$1) {
/* 214 */     int $$2 = 0;
/* 215 */     if ($$0.multiple) {
/* 216 */       $$2 |= 0x1;
/*     */     }
/* 218 */     $$1.writeByte($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Template deserializeFromNetwork(FriendlyByteBuf $$0) {
/* 223 */     byte $$1 = $$0.readByte();
/* 224 */     boolean $$2 = (($$1 & 0x1) != 0);
/* 225 */     return new Template($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeToJson(Template $$0, JsonObject $$1) {
/* 230 */     $$1.addProperty("amount", $$0.multiple ? "multiple" : "single");
/*     */   }
/*     */ 
/*     */   
/*     */   public Template unpack(ScoreHolderArgument $$0) {
/* 235 */     return new Template($$0.multiple);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\ScoreHolderArgument$Info.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */