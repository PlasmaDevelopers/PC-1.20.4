/*    */ package net.minecraft.server.packs.repository;
/*    */ 
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.util.InclusiveRange;
/*    */ 
/*    */ public enum PackCompatibility {
/*  8 */   TOO_OLD("old"),
/*  9 */   TOO_NEW("new"),
/* 10 */   COMPATIBLE("compatible");
/*    */   
/*    */   private final Component description;
/*    */   
/*    */   private final Component confirmation;
/*    */   
/*    */   PackCompatibility(String $$0) {
/* 17 */     this.description = (Component)Component.translatable("pack.incompatible." + $$0).withStyle(ChatFormatting.GRAY);
/* 18 */     this.confirmation = (Component)Component.translatable("pack.incompatible.confirm." + $$0);
/*    */   }
/*    */   
/*    */   public boolean isCompatible() {
/* 22 */     return (this == COMPATIBLE);
/*    */   }
/*    */   
/*    */   public static PackCompatibility forVersion(InclusiveRange<Integer> $$0, int $$1) {
/* 26 */     if (((Integer)$$0.maxInclusive()).intValue() < $$1) {
/* 27 */       return TOO_OLD;
/*    */     }
/* 29 */     if ($$1 < ((Integer)$$0.minInclusive()).intValue()) {
/* 30 */       return TOO_NEW;
/*    */     }
/* 32 */     return COMPATIBLE;
/*    */   }
/*    */   
/*    */   public Component getDescription() {
/* 36 */     return this.description;
/*    */   }
/*    */   
/*    */   public Component getConfirmation() {
/* 40 */     return this.confirmation;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\repository\PackCompatibility.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */