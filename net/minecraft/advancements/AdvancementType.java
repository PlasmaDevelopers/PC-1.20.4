/*    */ package net.minecraft.advancements;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum AdvancementType implements StringRepresentable {
/* 11 */   TASK("task", ChatFormatting.GREEN),
/* 12 */   CHALLENGE("challenge", ChatFormatting.DARK_PURPLE),
/* 13 */   GOAL("goal", ChatFormatting.GREEN); public static final Codec<AdvancementType> CODEC;
/*    */   
/*    */   static {
/* 16 */     CODEC = (Codec<AdvancementType>)StringRepresentable.fromEnum(AdvancementType::values);
/*    */   }
/*    */   private final String name;
/*    */   private final ChatFormatting chatColor;
/*    */   private final Component displayName;
/*    */   
/*    */   AdvancementType(String $$0, ChatFormatting $$1) {
/* 23 */     this.name = $$0;
/* 24 */     this.chatColor = $$1;
/* 25 */     this.displayName = (Component)Component.translatable("advancements.toast." + $$0);
/*    */   }
/*    */   
/*    */   public ChatFormatting getChatColor() {
/* 29 */     return this.chatColor;
/*    */   }
/*    */   
/*    */   public Component getDisplayName() {
/* 33 */     return this.displayName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 38 */     return this.name;
/*    */   }
/*    */   
/*    */   public MutableComponent createAnnouncement(AdvancementHolder $$0, ServerPlayer $$1) {
/* 42 */     return Component.translatable("chat.type.advancement." + this.name, new Object[] { $$1.getDisplayName(), Advancement.name($$0) });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\AdvancementType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */