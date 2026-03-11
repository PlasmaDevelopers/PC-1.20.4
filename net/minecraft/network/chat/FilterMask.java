/*     */ package net.minecraft.network.chat;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.BitSet;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ public class FilterMask {
/*  15 */   public static final Codec<FilterMask> CODEC = StringRepresentable.fromEnum(Type::values).dispatch(FilterMask::type, Type::codec);
/*     */   
/*  17 */   public static final FilterMask FULLY_FILTERED = new FilterMask(new BitSet(0), Type.FULLY_FILTERED);
/*  18 */   public static final FilterMask PASS_THROUGH = new FilterMask(new BitSet(0), Type.PASS_THROUGH);
/*  19 */   public static final Style FILTERED_STYLE = Style.EMPTY.withColor(ChatFormatting.DARK_GRAY).withHoverEvent(new HoverEvent((HoverEvent.Action)HoverEvent.Action.SHOW_TEXT, (T)Component.translatable("chat.filtered")));
/*     */   
/*  21 */   static final Codec<FilterMask> PASS_THROUGH_CODEC = Codec.unit(PASS_THROUGH);
/*  22 */   static final Codec<FilterMask> FULLY_FILTERED_CODEC = Codec.unit(FULLY_FILTERED);
/*  23 */   static final Codec<FilterMask> PARTIALLY_FILTERED_CODEC = ExtraCodecs.BIT_SET.xmap(FilterMask::new, FilterMask::mask);
/*     */   
/*     */   private static final char HASH = '#';
/*     */   
/*     */   private final BitSet mask;
/*     */   private final Type type;
/*     */   
/*     */   private FilterMask(BitSet $$0, Type $$1) {
/*  31 */     this.mask = $$0;
/*  32 */     this.type = $$1;
/*     */   }
/*     */   
/*     */   private FilterMask(BitSet $$0) {
/*  36 */     this.mask = $$0;
/*  37 */     this.type = Type.PARTIALLY_FILTERED;
/*     */   }
/*     */   
/*     */   public FilterMask(int $$0) {
/*  41 */     this(new BitSet($$0), Type.PARTIALLY_FILTERED);
/*     */   }
/*     */   
/*     */   private Type type() {
/*  45 */     return this.type;
/*     */   }
/*     */   
/*     */   private BitSet mask() {
/*  49 */     return this.mask;
/*     */   }
/*     */   
/*     */   public static FilterMask read(FriendlyByteBuf $$0) {
/*  53 */     Type $$1 = (Type)$$0.readEnum(Type.class);
/*  54 */     switch ($$1) { default: throw new IncompatibleClassChangeError();case PASS_THROUGH: case FULLY_FILTERED: case PARTIALLY_FILTERED: break; }  return 
/*     */ 
/*     */       
/*  57 */       new FilterMask($$0.readBitSet(), Type.PARTIALLY_FILTERED);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void write(FriendlyByteBuf $$0, FilterMask $$1) {
/*  62 */     $$0.writeEnum($$1.type);
/*  63 */     if ($$1.type == Type.PARTIALLY_FILTERED) {
/*  64 */       $$0.writeBitSet($$1.mask);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setFiltered(int $$0) {
/*  69 */     this.mask.set($$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String apply(String $$0) {
/*  74 */     switch (this.type) { default: throw new IncompatibleClassChangeError();
/*     */       case FULLY_FILTERED: 
/*     */       case PASS_THROUGH: 
/*     */       case PARTIALLY_FILTERED:
/*  78 */         break; }  char[] $$1 = $$0.toCharArray();
/*  79 */     for (int $$2 = 0; $$2 < $$1.length && $$2 < this.mask.length(); $$2++) {
/*  80 */       if (this.mask.get($$2)) {
/*  81 */         $$1[$$2] = '#';
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Component applyWithFormatting(String $$0) {
/*  91 */     switch (this.type) { default: throw new IncompatibleClassChangeError();
/*     */       case FULLY_FILTERED: 
/*     */       case PASS_THROUGH: 
/*     */       case PARTIALLY_FILTERED:
/*  95 */         break; }  MutableComponent $$1 = Component.empty();
/*  96 */     int $$2 = 0;
/*  97 */     boolean $$3 = this.mask.get(0);
/*     */     while (true) {
/*  99 */       int $$4 = $$3 ? this.mask.nextClearBit($$2) : this.mask.nextSetBit($$2);
/* 100 */       $$4 = ($$4 < 0) ? $$0.length() : $$4;
/* 101 */       if ($$4 == $$2) {
/*     */         break;
/*     */       }
/* 104 */       if ($$3) {
/* 105 */         $$1.append(Component.literal(StringUtils.repeat('#', $$4 - $$2)).withStyle(FILTERED_STYLE));
/*     */       } else {
/* 107 */         $$1.append($$0.substring($$2, $$4));
/*     */       } 
/* 109 */       $$3 = !$$3;
/* 110 */       $$2 = $$4;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 118 */     return (this.type == Type.PASS_THROUGH);
/*     */   }
/*     */   
/*     */   public boolean isFullyFiltered() {
/* 122 */     return (this.type == Type.FULLY_FILTERED);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 127 */     if (this == $$0) {
/* 128 */       return true;
/*     */     }
/* 130 */     if ($$0 == null || getClass() != $$0.getClass()) {
/* 131 */       return false;
/*     */     }
/*     */     
/* 134 */     FilterMask $$1 = (FilterMask)$$0;
/*     */     
/* 136 */     return (this.mask.equals($$1.mask) && this.type == $$1.type);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 141 */     int $$0 = this.mask.hashCode();
/* 142 */     $$0 = 31 * $$0 + this.type.hashCode();
/* 143 */     return $$0;
/*     */   }
/*     */   
/*     */   private enum Type implements StringRepresentable {
/* 147 */     PASS_THROUGH("pass_through", () -> FilterMask.PASS_THROUGH_CODEC),
/* 148 */     FULLY_FILTERED("fully_filtered", () -> FilterMask.FULLY_FILTERED_CODEC),
/* 149 */     PARTIALLY_FILTERED("partially_filtered", () -> FilterMask.PARTIALLY_FILTERED_CODEC);
/*     */     
/*     */     private final String serializedName;
/*     */     
/*     */     private final Supplier<Codec<FilterMask>> codec;
/*     */     
/*     */     Type(String $$0, Supplier<Codec<FilterMask>> $$1) {
/* 156 */       this.serializedName = $$0;
/* 157 */       this.codec = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSerializedName() {
/* 162 */       return this.serializedName;
/*     */     }
/*     */     
/*     */     private Codec<FilterMask> codec() {
/* 166 */       return this.codec.get();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\FilterMask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */