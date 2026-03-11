/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import com.mojang.datafixers.DSL;
/*     */ import com.mojang.datafixers.DataFix;
/*     */ import com.mojang.datafixers.OpticFinder;
/*     */ import com.mojang.datafixers.TypeRewriteRule;
/*     */ import com.mojang.datafixers.Typed;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.datafixers.types.Type;
/*     */ import com.mojang.datafixers.types.templates.List;
/*     */ import com.mojang.datafixers.types.templates.TaggedChoice;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ public class TrappedChestBlockEntityFix
/*     */   extends DataFix
/*     */ {
/*  27 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int SIZE = 4096;
/*     */   private static final short SIZE_BITS = 12;
/*     */   
/*     */   public TrappedChestBlockEntityFix(Schema $$0, boolean $$1) {
/*  33 */     super($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeRewriteRule makeRule() {
/*  38 */     Type<?> $$0 = getOutputSchema().getType(References.CHUNK);
/*  39 */     Type<?> $$1 = $$0.findFieldType("Level");
/*  40 */     Type<?> $$2 = $$1.findFieldType("TileEntities");
/*  41 */     if (!($$2 instanceof List.ListType)) {
/*  42 */       throw new IllegalStateException("Tile entity type is not a list type.");
/*     */     }
/*  44 */     List.ListType<?> $$3 = (List.ListType)$$2;
/*     */     
/*  46 */     OpticFinder<? extends List<?>> $$4 = DSL.fieldFinder("TileEntities", (Type)$$3);
/*     */     
/*  48 */     Type<?> $$5 = getInputSchema().getType(References.CHUNK);
/*     */     
/*  50 */     OpticFinder<?> $$6 = $$5.findField("Level");
/*  51 */     OpticFinder<?> $$7 = $$6.type().findField("Sections");
/*  52 */     Type<?> $$8 = $$7.type();
/*  53 */     if (!($$8 instanceof List.ListType)) {
/*  54 */       throw new IllegalStateException("Expecting sections to be a list.");
/*     */     }
/*  56 */     Type<?> $$9 = ((List.ListType)$$8).getElement();
/*  57 */     OpticFinder<?> $$10 = DSL.typeFinder($$9);
/*     */     
/*  59 */     return TypeRewriteRule.seq((new AddNewChoices(
/*  60 */           getOutputSchema(), "AddTrappedChestFix", References.BLOCK_ENTITY)).makeRule(), 
/*  61 */         fixTypeEverywhereTyped("Trapped Chest fix", $$5, $$4 -> $$4.updateTyped($$0, ())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class TrappedChestSection
/*     */     extends LeavesFix.Section
/*     */   {
/*     */     @Nullable
/*     */     private IntSet chestIds;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public TrappedChestSection(Typed<?> $$0, Schema $$1) {
/* 117 */       super($$0, $$1);
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean skippable() {
/* 122 */       this.chestIds = (IntSet)new IntOpenHashSet();
/*     */       
/* 124 */       for (int $$0 = 0; $$0 < this.palette.size(); $$0++) {
/* 125 */         Dynamic<?> $$1 = this.palette.get($$0);
/* 126 */         String $$2 = $$1.get("Name").asString("");
/* 127 */         if (Objects.equals($$2, "minecraft:trapped_chest")) {
/* 128 */           this.chestIds.add($$0);
/*     */         }
/*     */       } 
/*     */       
/* 132 */       return this.chestIds.isEmpty();
/*     */     }
/*     */     
/*     */     public boolean isTrappedChest(int $$0) {
/* 136 */       return this.chestIds.contains($$0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\TrappedChestBlockEntityFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */