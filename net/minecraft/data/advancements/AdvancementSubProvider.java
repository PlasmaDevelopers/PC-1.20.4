/*    */ package net.minecraft.data.advancements;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.advancements.Advancement;
/*    */ import net.minecraft.advancements.AdvancementHolder;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public interface AdvancementSubProvider
/*    */ {
/*    */   void generate(HolderLookup.Provider paramProvider, Consumer<AdvancementHolder> paramConsumer);
/*    */   
/*    */   static AdvancementHolder createPlaceholder(String $$0) {
/* 14 */     return Advancement.Builder.advancement().build(new ResourceLocation($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\advancements\AdvancementSubProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */