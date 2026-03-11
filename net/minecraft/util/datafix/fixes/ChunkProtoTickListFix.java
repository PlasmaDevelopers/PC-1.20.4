/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import com.google.common.base.Suppliers;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.DSL;
/*     */ import com.mojang.datafixers.DataFix;
/*     */ import com.mojang.datafixers.DataFixUtils;
/*     */ import com.mojang.datafixers.OpticFinder;
/*     */ import com.mojang.datafixers.TypeRewriteRule;
/*     */ import com.mojang.datafixers.Typed;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.datafixers.types.Type;
/*     */ import com.mojang.datafixers.types.templates.List;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import org.apache.commons.lang3.mutable.MutableInt;
/*     */ 
/*     */ public class ChunkProtoTickListFix extends DataFix {
/*     */   private static final int SECTION_WIDTH = 16;
/*  31 */   private static final ImmutableSet<String> ALWAYS_WATERLOGGED = ImmutableSet.of("minecraft:bubble_column", "minecraft:kelp", "minecraft:kelp_plant", "minecraft:seagrass", "minecraft:tall_seagrass");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkProtoTickListFix(Schema $$0) {
/*  40 */     super($$0, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected TypeRewriteRule makeRule() {
/*  45 */     Type<?> $$0 = getInputSchema().getType(References.CHUNK);
/*  46 */     OpticFinder<?> $$1 = $$0.findField("Level");
/*  47 */     OpticFinder<?> $$2 = $$1.type().findField("Sections");
/*  48 */     OpticFinder<?> $$3 = ((List.ListType)$$2.type()).getElement().finder();
/*  49 */     OpticFinder<?> $$4 = $$3.type().findField("block_states");
/*  50 */     OpticFinder<?> $$5 = $$3.type().findField("biomes");
/*  51 */     OpticFinder<?> $$6 = $$4.type().findField("palette");
/*  52 */     OpticFinder<?> $$7 = $$1.type().findField("TileTicks");
/*     */     
/*  54 */     return fixTypeEverywhereTyped("ChunkProtoTickListFix", $$0, $$7 -> $$7.updateTyped($$0, ()));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Dynamic<?> makeTickList(Dynamic<?> $$0, Int2ObjectMap<Supplier<PoorMansPalettedContainer>> $$1, byte $$2, int $$3, int $$4, String $$5, Function<Dynamic<?>, String> $$6) {
/* 106 */     Stream<Dynamic<?>> $$7 = Stream.empty();
/* 107 */     List<? extends Dynamic<?>> $$8 = $$0.get($$5).asList(Function.identity());
/* 108 */     for (int $$9 = 0; $$9 < $$8.size(); $$9++) {
/* 109 */       int $$10 = $$9 + $$2;
/* 110 */       Supplier<PoorMansPalettedContainer> $$11 = (Supplier<PoorMansPalettedContainer>)$$1.get($$10);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 115 */       Stream<? extends Dynamic<?>> $$12 = ((Dynamic)$$8.get($$9)).asStream().mapToInt($$0 -> $$0.asShort((short)-1)).filter($$0 -> ($$0 > 0)).mapToObj($$6 -> createTick($$0, $$1, $$2, $$3, $$4, $$6, $$5));
/*     */       
/* 117 */       $$7 = Stream.concat($$7, $$12);
/*     */     } 
/* 119 */     return $$0.createList($$7);
/*     */   }
/*     */   
/*     */   private static String getBlock(@Nullable Dynamic<?> $$0) {
/* 123 */     return ($$0 != null) ? $$0.get("Name").asString("minecraft:air") : "minecraft:air";
/*     */   }
/*     */   
/*     */   private static String getLiquid(@Nullable Dynamic<?> $$0) {
/* 127 */     if ($$0 == null) {
/* 128 */       return "minecraft:empty";
/*     */     }
/* 130 */     String $$1 = $$0.get("Name").asString("");
/* 131 */     if ("minecraft:water".equals($$1)) {
/* 132 */       return ($$0.get("Properties").get("level").asInt(0) == 0) ? "minecraft:water" : "minecraft:flowing_water";
/*     */     }
/* 134 */     if ("minecraft:lava".equals($$1)) {
/* 135 */       return ($$0.get("Properties").get("level").asInt(0) == 0) ? "minecraft:lava" : "minecraft:flowing_lava";
/*     */     }
/* 137 */     if (ALWAYS_WATERLOGGED.contains($$1) || $$0.get("Properties").get("waterlogged").asBoolean(false)) {
/* 138 */       return "minecraft:water";
/*     */     }
/* 140 */     return "minecraft:empty";
/*     */   }
/*     */   
/*     */   private Dynamic<?> createTick(Dynamic<?> $$0, @Nullable Supplier<PoorMansPalettedContainer> $$1, int $$2, int $$3, int $$4, int $$5, Function<Dynamic<?>, String> $$6) {
/* 144 */     int $$7 = $$5 & 0xF;
/* 145 */     int $$8 = $$5 >>> 4 & 0xF;
/* 146 */     int $$9 = $$5 >>> 8 & 0xF;
/* 147 */     String $$10 = $$6.apply(($$1 != null) ? ((PoorMansPalettedContainer)$$1.get()).get($$7, $$8, $$9) : null);
/* 148 */     return $$0.createMap((Map)ImmutableMap.builder()
/* 149 */         .put($$0.createString("i"), $$0.createString($$10))
/* 150 */         .put($$0.createString("x"), $$0.createInt($$2 * 16 + $$7))
/* 151 */         .put($$0.createString("y"), $$0.createInt($$3 * 16 + $$8))
/* 152 */         .put($$0.createString("z"), $$0.createInt($$4 * 16 + $$9))
/* 153 */         .put($$0.createString("t"), $$0.createInt(0))
/* 154 */         .put($$0.createString("p"), $$0.createInt(0))
/* 155 */         .build());
/*     */   }
/*     */   
/*     */   public static final class PoorMansPalettedContainer
/*     */   {
/*     */     private static final long SIZE_BITS = 4L;
/*     */     private final List<? extends Dynamic<?>> palette;
/*     */     private final long[] data;
/*     */     private final int bits;
/*     */     private final long mask;
/*     */     private final int valuesPerLong;
/*     */     
/*     */     public PoorMansPalettedContainer(List<? extends Dynamic<?>> $$0, long[] $$1) {
/* 168 */       this.palette = $$0;
/* 169 */       this.data = $$1;
/*     */       
/* 171 */       this.bits = Math.max(4, ChunkHeightAndBiomeFix.ceillog2($$0.size()));
/* 172 */       this.mask = (1L << this.bits) - 1L;
/* 173 */       this.valuesPerLong = (char)(64 / this.bits);
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public Dynamic<?> get(int $$0, int $$1, int $$2) {
/* 178 */       int $$3 = this.palette.size();
/* 179 */       if ($$3 < 1) {
/* 180 */         return null;
/*     */       }
/* 182 */       if ($$3 == 1) {
/* 183 */         return this.palette.get(0);
/*     */       }
/*     */       
/* 186 */       int $$4 = getIndex($$0, $$1, $$2);
/* 187 */       int $$5 = $$4 / this.valuesPerLong;
/* 188 */       if ($$5 < 0 || $$5 >= this.data.length) {
/* 189 */         return null;
/*     */       }
/* 191 */       long $$6 = this.data[$$5];
/* 192 */       int $$7 = ($$4 - $$5 * this.valuesPerLong) * this.bits;
/* 193 */       int $$8 = (int)($$6 >> $$7 & this.mask);
/* 194 */       if ($$8 < 0 || $$8 >= $$3) {
/* 195 */         return null;
/*     */       }
/* 197 */       return this.palette.get($$8);
/*     */     }
/*     */     
/*     */     private int getIndex(int $$0, int $$1, int $$2) {
/* 201 */       return ($$1 << 4 | $$2) << 4 | $$0;
/*     */     }
/*     */     
/*     */     public List<? extends Dynamic<?>> palette() {
/* 205 */       return this.palette;
/*     */     }
/*     */     
/*     */     public long[] data() {
/* 209 */       return this.data;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ChunkProtoTickListFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */