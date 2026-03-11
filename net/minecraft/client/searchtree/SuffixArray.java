/*     */ package net.minecraft.client.searchtree;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.Arrays;
/*     */ import it.unimi.dsi.fastutil.Swapper;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntComparator;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class SuffixArray<T>
/*     */ {
/*  20 */   private static final boolean DEBUG_COMPARISONS = Boolean.parseBoolean(System.getProperty("SuffixArray.printComparisons", "false"));
/*  21 */   private static final boolean DEBUG_ARRAY = Boolean.parseBoolean(System.getProperty("SuffixArray.printArray", "false"));
/*     */   
/*  23 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int END_OF_TEXT_MARKER = -1;
/*     */   private static final int END_OF_DATA = -2;
/*  27 */   protected final List<T> list = Lists.newArrayList();
/*     */   
/*  29 */   private final IntList chars = (IntList)new IntArrayList();
/*  30 */   private final IntList wordStarts = (IntList)new IntArrayList();
/*  31 */   private IntList suffixToT = (IntList)new IntArrayList();
/*  32 */   private IntList offsets = (IntList)new IntArrayList();
/*     */   private int maxStringLength;
/*     */   
/*     */   public void add(T $$0, String $$1) {
/*  36 */     this.maxStringLength = Math.max(this.maxStringLength, $$1.length());
/*  37 */     int $$2 = this.list.size();
/*  38 */     this.list.add($$0);
/*     */     
/*  40 */     this.wordStarts.add(this.chars.size());
/*  41 */     for (int $$3 = 0; $$3 < $$1.length(); $$3++) {
/*  42 */       this.suffixToT.add($$2);
/*  43 */       this.offsets.add($$3);
/*  44 */       this.chars.add($$1.charAt($$3));
/*     */     } 
/*  46 */     this.suffixToT.add($$2);
/*  47 */     this.offsets.add($$1.length());
/*  48 */     this.chars.add(-1);
/*     */   }
/*     */   
/*     */   public void generate() {
/*  52 */     int $$0 = this.chars.size();
/*     */     
/*  54 */     int[] $$1 = new int[$$0];
/*     */     
/*  56 */     int[] $$2 = new int[$$0];
/*  57 */     int[] $$3 = new int[$$0];
/*  58 */     int[] $$4 = new int[$$0];
/*     */     
/*  60 */     IntComparator $$5 = ($$2, $$3) -> ($$0[$$2] == $$0[$$3]) ? Integer.compare($$1[$$2], $$1[$$3]) : Integer.compare($$0[$$2], $$0[$$3]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  67 */     Swapper $$6 = ($$3, $$4) -> {
/*     */         if ($$3 != $$4) {
/*     */           int $$5 = $$0[$$3];
/*     */           
/*     */           $$0[$$3] = $$0[$$4];
/*     */           
/*     */           $$0[$$4] = $$5;
/*     */           
/*     */           $$5 = $$1[$$3];
/*     */           $$1[$$3] = $$1[$$4];
/*     */           $$1[$$4] = $$5;
/*     */           $$5 = $$2[$$3];
/*     */           $$2[$$3] = $$2[$$4];
/*     */           $$2[$$4] = $$5;
/*     */         } 
/*     */       };
/*  83 */     for (int $$7 = 0; $$7 < $$0; $$7++) {
/*  84 */       $$1[$$7] = this.chars.getInt($$7);
/*     */     }
/*     */     
/*  87 */     int $$8 = 1;
/*     */     
/*  89 */     int $$9 = Math.min($$0, this.maxStringLength);
/*  90 */     while ($$8 * 2 < $$9) {
/*  91 */       for (int $$10 = 0; $$10 < $$0; $$10++) {
/*  92 */         $$2[$$10] = $$1[$$10];
/*  93 */         $$3[$$10] = ($$10 + $$8 < $$0) ? $$1[$$10 + $$8] : -2;
/*  94 */         $$4[$$10] = $$10;
/*     */       } 
/*     */       
/*  97 */       Arrays.quickSort(0, $$0, $$5, $$6);
/*     */       
/*  99 */       for (int $$11 = 0; $$11 < $$0; $$11++) {
/* 100 */         if ($$11 > 0 && $$2[$$11] == $$2[$$11 - 1] && $$3[$$11] == $$3[$$11 - 1]) {
/* 101 */           $$1[$$4[$$11]] = $$1[$$4[$$11 - 1]];
/*     */         } else {
/* 103 */           $$1[$$4[$$11]] = $$11;
/*     */         } 
/*     */       } 
/*     */       
/* 107 */       $$8 *= 2;
/*     */     } 
/*     */     
/* 110 */     IntList $$12 = this.suffixToT;
/* 111 */     IntList $$13 = this.offsets;
/*     */     
/* 113 */     this.suffixToT = (IntList)new IntArrayList($$12.size());
/* 114 */     this.offsets = (IntList)new IntArrayList($$13.size());
/* 115 */     for (int $$14 = 0; $$14 < $$0; $$14++) {
/* 116 */       int $$15 = $$4[$$14];
/* 117 */       this.suffixToT.add($$12.getInt($$15));
/* 118 */       this.offsets.add($$13.getInt($$15));
/*     */     } 
/* 120 */     if (DEBUG_ARRAY) {
/* 121 */       print();
/*     */     }
/*     */   }
/*     */   
/*     */   private void print() {
/* 126 */     for (int $$0 = 0; $$0 < this.suffixToT.size(); $$0++) {
/* 127 */       LOGGER.debug("{} {}", Integer.valueOf($$0), getString($$0));
/*     */     }
/* 129 */     LOGGER.debug("");
/*     */   }
/*     */   
/*     */   private String getString(int $$0) {
/* 133 */     int $$1 = this.offsets.getInt($$0);
/* 134 */     int $$2 = this.wordStarts.getInt(this.suffixToT.getInt($$0));
/*     */     
/* 136 */     StringBuilder $$3 = new StringBuilder();
/* 137 */     for (int $$4 = 0; $$2 + $$4 < this.chars.size(); $$4++) {
/* 138 */       if ($$4 == $$1) {
/* 139 */         $$3.append('^');
/*     */       }
/* 141 */       int $$5 = this.chars.getInt($$2 + $$4);
/* 142 */       if ($$5 == -1) {
/*     */         break;
/*     */       }
/* 145 */       $$3.append((char)$$5);
/*     */     } 
/* 147 */     return $$3.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private int compare(String $$0, int $$1) {
/* 152 */     int $$2 = this.wordStarts.getInt(this.suffixToT.getInt($$1));
/* 153 */     int $$3 = this.offsets.getInt($$1);
/*     */     
/* 155 */     for (int $$4 = 0; $$4 < $$0.length(); $$4++) {
/* 156 */       int $$5 = this.chars.getInt($$2 + $$3 + $$4);
/* 157 */       if ($$5 == -1) {
/* 158 */         return 1;
/*     */       }
/*     */       
/* 161 */       char $$6 = $$0.charAt($$4);
/* 162 */       char $$7 = (char)$$5;
/* 163 */       if ($$6 < $$7)
/* 164 */         return -1; 
/* 165 */       if ($$6 > $$7) {
/* 166 */         return 1;
/*     */       }
/*     */     } 
/*     */     
/* 170 */     return 0;
/*     */   }
/*     */   
/*     */   public List<T> search(String $$0) {
/* 174 */     int $$1 = this.suffixToT.size();
/*     */ 
/*     */ 
/*     */     
/* 178 */     int $$2 = 0;
/* 179 */     int $$3 = $$1;
/*     */     
/* 181 */     while ($$2 < $$3) {
/* 182 */       int $$4 = $$2 + ($$3 - $$2) / 2;
/* 183 */       int $$5 = compare($$0, $$4);
/* 184 */       if (DEBUG_COMPARISONS) {
/* 185 */         LOGGER.debug("comparing lower \"{}\" with {} \"{}\": {}", new Object[] { $$0, Integer.valueOf($$4), getString($$4), Integer.valueOf($$5) });
/*     */       }
/* 187 */       if ($$5 > 0) {
/* 188 */         $$2 = $$4 + 1; continue;
/*     */       } 
/* 190 */       $$3 = $$4;
/*     */     } 
/*     */ 
/*     */     
/* 194 */     if ($$2 < 0 || $$2 >= $$1) {
/* 195 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 198 */     int $$6 = $$2;
/*     */     
/* 200 */     $$3 = $$1;
/* 201 */     while ($$2 < $$3) {
/* 202 */       int $$7 = $$2 + ($$3 - $$2) / 2;
/* 203 */       int $$8 = compare($$0, $$7);
/* 204 */       if (DEBUG_COMPARISONS) {
/* 205 */         LOGGER.debug("comparing upper \"{}\" with {} \"{}\": {}", new Object[] { $$0, Integer.valueOf($$7), getString($$7), Integer.valueOf($$8) });
/*     */       }
/* 207 */       if ($$8 >= 0) {
/* 208 */         $$2 = $$7 + 1; continue;
/*     */       } 
/* 210 */       $$3 = $$7;
/*     */     } 
/*     */ 
/*     */     
/* 214 */     int $$9 = $$2;
/*     */ 
/*     */     
/* 217 */     IntOpenHashSet intOpenHashSet = new IntOpenHashSet();
/* 218 */     for (int $$11 = $$6; $$11 < $$9; $$11++) {
/* 219 */       intOpenHashSet.add(this.suffixToT.getInt($$11));
/*     */     }
/*     */     
/* 222 */     int[] $$12 = intOpenHashSet.toIntArray();
/* 223 */     Arrays.sort($$12);
/*     */     
/* 225 */     Set<T> $$13 = Sets.newLinkedHashSet();
/* 226 */     for (int $$14 : $$12) {
/* 227 */       $$13.add(this.list.get($$14));
/*     */     }
/* 229 */     return Lists.newArrayList($$13);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\searchtree\SuffixArray.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */