/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Set;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ItemDisplayBuilder
/*     */   implements CreativeModeTab.Output
/*     */ {
/* 229 */   public final Collection<ItemStack> tabContents = ItemStackLinkedSet.createTypeAndTagSet();
/* 230 */   public final Set<ItemStack> searchTabContents = ItemStackLinkedSet.createTypeAndTagSet();
/*     */   private final CreativeModeTab tab;
/*     */   private final FeatureFlagSet featureFlagSet;
/*     */   
/*     */   public ItemDisplayBuilder(CreativeModeTab $$0, FeatureFlagSet $$1) {
/* 235 */     this.tab = $$0;
/* 236 */     this.featureFlagSet = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(ItemStack $$0, CreativeModeTab.TabVisibility $$1) {
/* 241 */     if ($$0.getCount() != 1) {
/* 242 */       throw new IllegalArgumentException("Stack size must be exactly 1");
/*     */     }
/*     */ 
/*     */     
/* 246 */     boolean $$2 = (this.tabContents.contains($$0) && $$1 != CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY);
/*     */     
/* 248 */     if ($$2) {
/* 249 */       throw new IllegalStateException("Accidentally adding the same item stack twice " + $$0
/* 250 */           .getDisplayName().getString() + " to a Creative Mode Tab: " + this.tab
/*     */           
/* 252 */           .getDisplayName().getString());
/*     */     }
/*     */     
/* 255 */     if ($$0.getItem().isEnabled(this.featureFlagSet))
/* 256 */       switch (CreativeModeTab.null.$SwitchMap$net$minecraft$world$item$CreativeModeTab$TabVisibility[$$1.ordinal()]) {
/*     */         case 1:
/* 258 */           this.tabContents.add($$0);
/* 259 */           this.searchTabContents.add($$0); break;
/*     */         case 2:
/* 261 */           this.tabContents.add($$0); break;
/* 262 */         case 3: this.searchTabContents.add($$0);
/*     */           break;
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\CreativeModeTab$ItemDisplayBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */