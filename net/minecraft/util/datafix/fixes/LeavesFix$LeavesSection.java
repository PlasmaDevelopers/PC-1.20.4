/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import com.mojang.datafixers.Typed;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.datafix.PackedBitStorage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LeavesSection
/*     */   extends LeavesFix.Section
/*     */ {
/*     */   private static final String PERSISTENT = "persistent";
/*     */   private static final String DECAYABLE = "decayable";
/*     */   private static final String DISTANCE = "distance";
/*     */   @Nullable
/*     */   private IntSet leaveIds;
/*     */   @Nullable
/*     */   private IntSet logIds;
/*     */   @Nullable
/*     */   private Int2IntMap stateToIdMap;
/*     */   
/*     */   public LeavesSection(Typed<?> $$0, Schema $$1) {
/* 268 */     super($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean skippable() {
/* 273 */     this.leaveIds = (IntSet)new IntOpenHashSet();
/* 274 */     this.logIds = (IntSet)new IntOpenHashSet();
/* 275 */     this.stateToIdMap = (Int2IntMap)new Int2IntOpenHashMap();
/*     */     
/* 277 */     for (int $$0 = 0; $$0 < this.palette.size(); $$0++) {
/* 278 */       Dynamic<?> $$1 = this.palette.get($$0);
/* 279 */       String $$2 = $$1.get("Name").asString("");
/* 280 */       if (LeavesFix.LEAVES.containsKey($$2)) {
/* 281 */         boolean $$3 = Objects.equals($$1.get("Properties").get("decayable").asString(""), "false");
/* 282 */         this.leaveIds.add($$0);
/* 283 */         this.stateToIdMap.put(getStateId($$2, $$3, 7), $$0);
/* 284 */         this.palette.set($$0, makeLeafTag($$1, $$2, $$3, 7));
/*     */       } 
/* 286 */       if (LeavesFix.LOGS.contains($$2)) {
/* 287 */         this.logIds.add($$0);
/*     */       }
/*     */     } 
/*     */     
/* 291 */     return (this.leaveIds.isEmpty() && this.logIds.isEmpty());
/*     */   }
/*     */   
/*     */   private Dynamic<?> makeLeafTag(Dynamic<?> $$0, String $$1, boolean $$2, int $$3) {
/* 295 */     Dynamic<?> $$4 = $$0.emptyMap();
/* 296 */     $$4 = $$4.set("persistent", $$4.createString($$2 ? "true" : "false"));
/* 297 */     $$4 = $$4.set("distance", $$4.createString(Integer.toString($$3)));
/*     */     
/* 299 */     Dynamic<?> $$5 = $$0.emptyMap();
/* 300 */     $$5 = $$5.set("Properties", $$4);
/* 301 */     $$5 = $$5.set("Name", $$5.createString($$1));
/* 302 */     return $$5;
/*     */   }
/*     */   
/*     */   public boolean isLog(int $$0) {
/* 306 */     return this.logIds.contains($$0);
/*     */   }
/*     */   
/*     */   public boolean isLeaf(int $$0) {
/* 310 */     return this.leaveIds.contains($$0);
/*     */   }
/*     */   
/*     */   int getDistance(int $$0) {
/* 314 */     if (isLog($$0)) {
/* 315 */       return 0;
/*     */     }
/* 317 */     return Integer.parseInt(((Dynamic)this.palette.get($$0)).get("Properties").get("distance").asString(""));
/*     */   }
/*     */   
/*     */   void setDistance(int $$0, int $$1, int $$2) {
/* 321 */     Dynamic<?> $$3 = this.palette.get($$1);
/* 322 */     String $$4 = $$3.get("Name").asString("");
/* 323 */     boolean $$5 = Objects.equals($$3.get("Properties").get("persistent").asString(""), "true");
/* 324 */     int $$6 = getStateId($$4, $$5, $$2);
/*     */     
/* 326 */     if (!this.stateToIdMap.containsKey($$6)) {
/* 327 */       int $$7 = this.palette.size();
/* 328 */       this.leaveIds.add($$7);
/* 329 */       this.stateToIdMap.put($$6, $$7);
/* 330 */       this.palette.add(makeLeafTag($$3, $$4, $$5, $$2));
/*     */     } 
/*     */     
/* 333 */     int $$8 = this.stateToIdMap.get($$6);
/* 334 */     if (1 << this.storage.getBits() <= $$8) {
/* 335 */       PackedBitStorage $$9 = new PackedBitStorage(this.storage.getBits() + 1, 4096);
/* 336 */       for (int $$10 = 0; $$10 < 4096; $$10++) {
/* 337 */         $$9.set($$10, this.storage.get($$10));
/*     */       }
/* 339 */       this.storage = $$9;
/*     */     } 
/* 341 */     this.storage.set($$0, $$8);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\LeavesFix$LeavesSection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */