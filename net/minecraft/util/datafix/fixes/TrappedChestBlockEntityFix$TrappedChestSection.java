/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import com.mojang.datafixers.Typed;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TrappedChestSection
/*     */   extends LeavesFix.Section
/*     */ {
/*     */   @Nullable
/*     */   private IntSet chestIds;
/*     */   
/*     */   public TrappedChestSection(Typed<?> $$0, Schema $$1) {
/* 117 */     super($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean skippable() {
/* 122 */     this.chestIds = (IntSet)new IntOpenHashSet();
/*     */     
/* 124 */     for (int $$0 = 0; $$0 < this.palette.size(); $$0++) {
/* 125 */       Dynamic<?> $$1 = this.palette.get($$0);
/* 126 */       String $$2 = $$1.get("Name").asString("");
/* 127 */       if (Objects.equals($$2, "minecraft:trapped_chest")) {
/* 128 */         this.chestIds.add($$0);
/*     */       }
/*     */     } 
/*     */     
/* 132 */     return this.chestIds.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean isTrappedChest(int $$0) {
/* 136 */     return this.chestIds.contains($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\TrappedChestBlockEntityFix$TrappedChestSection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */