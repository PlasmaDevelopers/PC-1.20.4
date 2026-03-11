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
/*     */ public class Info
/*     */   implements ArgumentTypeInfo<EntityArgument, EntityArgument.Info.Template>
/*     */ {
/*     */   private static final byte FLAG_SINGLE = 1;
/*     */   private static final byte FLAG_PLAYERS_ONLY = 2;
/*     */   
/*     */   public final class Template
/*     */     implements ArgumentTypeInfo.Template<EntityArgument>
/*     */   {
/*     */     final boolean single;
/*     */     final boolean playersOnly;
/*     */     
/*     */     Template(boolean $$1, boolean $$2) {
/* 150 */       this.single = $$1;
/* 151 */       this.playersOnly = $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public EntityArgument instantiate(CommandBuildContext $$0) {
/* 156 */       return new EntityArgument(this.single, this.playersOnly);
/*     */     }
/*     */ 
/*     */     
/*     */     public ArgumentTypeInfo<EntityArgument, ?> type() {
/* 161 */       return EntityArgument.Info.this;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeToNetwork(Template $$0, FriendlyByteBuf $$1) {
/* 167 */     int $$2 = 0;
/* 168 */     if ($$0.single) {
/* 169 */       $$2 |= 0x1;
/*     */     }
/* 171 */     if ($$0.playersOnly) {
/* 172 */       $$2 |= 0x2;
/*     */     }
/* 174 */     $$1.writeByte($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Template deserializeFromNetwork(FriendlyByteBuf $$0) {
/* 179 */     byte $$1 = $$0.readByte();
/* 180 */     return new Template((($$1 & 0x1) != 0), (($$1 & 0x2) != 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeToJson(Template $$0, JsonObject $$1) {
/* 185 */     $$1.addProperty("amount", $$0.single ? "single" : "multiple");
/* 186 */     $$1.addProperty("type", $$0.playersOnly ? "players" : "entities");
/*     */   }
/*     */ 
/*     */   
/*     */   public Template unpack(EntityArgument $$0) {
/* 191 */     return new Template($$0.single, $$0.playersOnly);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\EntityArgument$Info.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */