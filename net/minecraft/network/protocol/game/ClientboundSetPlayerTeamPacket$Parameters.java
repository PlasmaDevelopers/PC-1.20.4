/*     */ package net.minecraft.network.protocol.game;
/*     */ 
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.world.scores.PlayerTeam;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Parameters
/*     */ {
/*     */   private final Component displayName;
/*     */   private final Component playerPrefix;
/*     */   private final Component playerSuffix;
/*     */   private final String nametagVisibility;
/*     */   private final String collisionRule;
/*     */   private final ChatFormatting color;
/*     */   private final int options;
/*     */   
/*     */   public Parameters(PlayerTeam $$0) {
/* 160 */     this.displayName = $$0.getDisplayName();
/* 161 */     this.options = $$0.packOptions();
/* 162 */     this.nametagVisibility = ($$0.getNameTagVisibility()).name;
/* 163 */     this.collisionRule = ($$0.getCollisionRule()).name;
/* 164 */     this.color = $$0.getColor();
/* 165 */     this.playerPrefix = $$0.getPlayerPrefix();
/* 166 */     this.playerSuffix = $$0.getPlayerSuffix();
/*     */   }
/*     */   
/*     */   public Parameters(FriendlyByteBuf $$0) {
/* 170 */     this.displayName = $$0.readComponentTrusted();
/* 171 */     this.options = $$0.readByte();
/* 172 */     this.nametagVisibility = $$0.readUtf(40);
/* 173 */     this.collisionRule = $$0.readUtf(40);
/* 174 */     this.color = (ChatFormatting)$$0.readEnum(ChatFormatting.class);
/* 175 */     this.playerPrefix = $$0.readComponentTrusted();
/* 176 */     this.playerSuffix = $$0.readComponentTrusted();
/*     */   }
/*     */   
/*     */   public Component getDisplayName() {
/* 180 */     return this.displayName;
/*     */   }
/*     */   
/*     */   public int getOptions() {
/* 184 */     return this.options;
/*     */   }
/*     */   
/*     */   public ChatFormatting getColor() {
/* 188 */     return this.color;
/*     */   }
/*     */   
/*     */   public String getNametagVisibility() {
/* 192 */     return this.nametagVisibility;
/*     */   }
/*     */   
/*     */   public String getCollisionRule() {
/* 196 */     return this.collisionRule;
/*     */   }
/*     */   
/*     */   public Component getPlayerPrefix() {
/* 200 */     return this.playerPrefix;
/*     */   }
/*     */   
/*     */   public Component getPlayerSuffix() {
/* 204 */     return this.playerSuffix;
/*     */   }
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/* 208 */     $$0.writeComponent(this.displayName);
/* 209 */     $$0.writeByte(this.options);
/* 210 */     $$0.writeUtf(this.nametagVisibility);
/* 211 */     $$0.writeUtf(this.collisionRule);
/* 212 */     $$0.writeEnum((Enum)this.color);
/* 213 */     $$0.writeComponent(this.playerPrefix);
/* 214 */     $$0.writeComponent(this.playerSuffix);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetPlayerTeamPacket$Parameters.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */