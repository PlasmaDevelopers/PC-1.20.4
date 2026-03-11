/*    */ package net.minecraft.world.level.block.state.pattern;
/*    */ 
/*    */ import com.google.common.base.Joiner;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.lang.reflect.Array;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.function.Predicate;
/*    */ import org.apache.commons.lang3.ArrayUtils;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ public class BlockPatternBuilder
/*    */ {
/* 15 */   private static final Joiner COMMA_JOINED = Joiner.on(",");
/*    */   
/* 17 */   private final List<String[]> pattern = Lists.newArrayList();
/* 18 */   private final Map<Character, Predicate<BlockInWorld>> lookup = Maps.newHashMap();
/*    */   private int height;
/*    */   private int width;
/*    */   
/*    */   private BlockPatternBuilder() {
/* 23 */     this.lookup.put(Character.valueOf(' '), $$0 -> true);
/*    */   }
/*    */   
/*    */   public BlockPatternBuilder aisle(String... $$0) {
/* 27 */     if (ArrayUtils.isEmpty((Object[])$$0) || StringUtils.isEmpty($$0[0])) {
/* 28 */       throw new IllegalArgumentException("Empty pattern for aisle");
/*    */     }
/*    */     
/* 31 */     if (this.pattern.isEmpty()) {
/* 32 */       this.height = $$0.length;
/* 33 */       this.width = $$0[0].length();
/*    */     } 
/*    */     
/* 36 */     if ($$0.length != this.height) {
/* 37 */       throw new IllegalArgumentException("Expected aisle with height of " + this.height + ", but was given one with a height of " + $$0.length + ")");
/*    */     }
/*    */     
/* 40 */     for (String $$1 : $$0) {
/* 41 */       if ($$1.length() != this.width) {
/* 42 */         throw new IllegalArgumentException("Not all rows in the given aisle are the correct width (expected " + this.width + ", found one with " + $$1.length() + ")");
/*    */       }
/* 44 */       for (char $$2 : $$1.toCharArray()) {
/* 45 */         if (!this.lookup.containsKey(Character.valueOf($$2))) {
/* 46 */           this.lookup.put(Character.valueOf($$2), null);
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 51 */     this.pattern.add($$0);
/*    */     
/* 53 */     return this;
/*    */   }
/*    */   
/*    */   public static BlockPatternBuilder start() {
/* 57 */     return new BlockPatternBuilder();
/*    */   }
/*    */   
/*    */   public BlockPatternBuilder where(char $$0, Predicate<BlockInWorld> $$1) {
/* 61 */     this.lookup.put(Character.valueOf($$0), $$1);
/*    */     
/* 63 */     return this;
/*    */   }
/*    */   
/*    */   public BlockPattern build() {
/* 67 */     return new BlockPattern(createPattern());
/*    */   }
/*    */ 
/*    */   
/*    */   private Predicate<BlockInWorld>[][][] createPattern() {
/* 72 */     ensureAllCharactersMatched();
/*    */     
/* 74 */     Predicate[][][] arrayOfPredicate = (Predicate[][][])Array.newInstance(Predicate.class, new int[] { this.pattern.size(), this.height, this.width });
/*    */     
/* 76 */     for (int $$1 = 0; $$1 < this.pattern.size(); $$1++) {
/* 77 */       for (int $$2 = 0; $$2 < this.height; $$2++) {
/* 78 */         for (int $$3 = 0; $$3 < this.width; $$3++) {
/* 79 */           arrayOfPredicate[$$1][$$2][$$3] = this.lookup.get(Character.valueOf(((String[])this.pattern.get($$1))[$$2].charAt($$3)));
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 84 */     return (Predicate<BlockInWorld>[][][])arrayOfPredicate;
/*    */   }
/*    */   
/*    */   private void ensureAllCharactersMatched() {
/* 88 */     List<Character> $$0 = Lists.newArrayList();
/*    */     
/* 90 */     for (Map.Entry<Character, Predicate<BlockInWorld>> $$1 : this.lookup.entrySet()) {
/* 91 */       if ($$1.getValue() == null) {
/* 92 */         $$0.add($$1.getKey());
/*    */       }
/*    */     } 
/*    */     
/* 96 */     if (!$$0.isEmpty())
/* 97 */       throw new IllegalStateException("Predicates for character(s) " + COMMA_JOINED.join($$0) + " are missing"); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\pattern\BlockPatternBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */