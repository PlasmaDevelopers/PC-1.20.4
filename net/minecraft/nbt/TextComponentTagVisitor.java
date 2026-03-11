/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteOpenHashSet;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class TextComponentTagVisitor
/*     */   implements TagVisitor {
/*  22 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   private static final int INLINE_LIST_THRESHOLD = 8;
/*  24 */   private static final ByteCollection INLINE_ELEMENT_TYPES = (ByteCollection)new ByteOpenHashSet(Arrays.asList(new Byte[] { Byte.valueOf((byte)1), Byte.valueOf((byte)2), Byte.valueOf((byte)3), Byte.valueOf((byte)4), Byte.valueOf((byte)5), Byte.valueOf((byte)6) }));
/*     */   
/*  26 */   private static final ChatFormatting SYNTAX_HIGHLIGHTING_KEY = ChatFormatting.AQUA;
/*  27 */   private static final ChatFormatting SYNTAX_HIGHLIGHTING_STRING = ChatFormatting.GREEN;
/*  28 */   private static final ChatFormatting SYNTAX_HIGHLIGHTING_NUMBER = ChatFormatting.GOLD;
/*  29 */   private static final ChatFormatting SYNTAX_HIGHLIGHTING_NUMBER_TYPE = ChatFormatting.RED;
/*     */   
/*  31 */   private static final Pattern SIMPLE_VALUE = Pattern.compile("[A-Za-z0-9._+-]+");
/*     */   
/*  33 */   private static final String NAME_VALUE_SEPARATOR = String.valueOf(':');
/*  34 */   private static final String ELEMENT_SEPARATOR = String.valueOf(',');
/*     */   
/*     */   private static final String LIST_OPEN = "[";
/*     */   
/*     */   private static final String LIST_CLOSE = "]";
/*     */   private static final String LIST_TYPE_SEPARATOR = ";";
/*     */   private static final String ELEMENT_SPACING = " ";
/*     */   private static final String STRUCT_OPEN = "{";
/*     */   private static final String STRUCT_CLOSE = "}";
/*     */   private static final String NEWLINE = "\n";
/*     */   private final String indentation;
/*     */   private final int depth;
/*  46 */   private Component result = CommonComponents.EMPTY;
/*     */   
/*     */   public TextComponentTagVisitor(String $$0, int $$1) {
/*  49 */     this.indentation = $$0;
/*  50 */     this.depth = $$1;
/*     */   }
/*     */   
/*     */   public Component visit(Tag $$0) {
/*  54 */     $$0.accept(this);
/*     */     
/*  56 */     return this.result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitString(StringTag $$0) {
/*  61 */     String $$1 = StringTag.quoteAndEscape($$0.getAsString());
/*  62 */     String $$2 = $$1.substring(0, 1);
/*  63 */     MutableComponent mutableComponent = Component.literal($$1.substring(1, $$1.length() - 1)).withStyle(SYNTAX_HIGHLIGHTING_STRING);
/*  64 */     this.result = (Component)Component.literal($$2).append((Component)mutableComponent).append($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitByte(ByteTag $$0) {
/*  69 */     MutableComponent mutableComponent = Component.literal("b").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
/*  70 */     this.result = (Component)Component.literal(String.valueOf($$0.getAsNumber())).append((Component)mutableComponent).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitShort(ShortTag $$0) {
/*  75 */     MutableComponent mutableComponent = Component.literal("s").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
/*  76 */     this.result = (Component)Component.literal(String.valueOf($$0.getAsNumber())).append((Component)mutableComponent).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitInt(IntTag $$0) {
/*  81 */     this.result = (Component)Component.literal(String.valueOf($$0.getAsNumber())).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLong(LongTag $$0) {
/*  86 */     MutableComponent mutableComponent = Component.literal("L").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
/*  87 */     this.result = (Component)Component.literal(String.valueOf($$0.getAsNumber())).append((Component)mutableComponent).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitFloat(FloatTag $$0) {
/*  92 */     MutableComponent mutableComponent = Component.literal("f").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
/*  93 */     this.result = (Component)Component.literal(String.valueOf($$0.getAsFloat())).append((Component)mutableComponent).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitDouble(DoubleTag $$0) {
/*  98 */     MutableComponent mutableComponent = Component.literal("d").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
/*  99 */     this.result = (Component)Component.literal(String.valueOf($$0.getAsDouble())).append((Component)mutableComponent).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitByteArray(ByteArrayTag $$0) {
/* 104 */     MutableComponent mutableComponent1 = Component.literal("B").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
/* 105 */     MutableComponent $$2 = Component.literal("[").append((Component)mutableComponent1).append(";");
/*     */     
/* 107 */     byte[] $$3 = $$0.getAsByteArray();
/* 108 */     for (int $$4 = 0; $$4 < $$3.length; $$4++) {
/* 109 */       MutableComponent $$5 = Component.literal(String.valueOf($$3[$$4])).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
/*     */       
/* 111 */       $$2.append(" ").append((Component)$$5).append((Component)mutableComponent1);
/*     */       
/* 113 */       if ($$4 != $$3.length - 1) {
/* 114 */         $$2.append(ELEMENT_SEPARATOR);
/*     */       }
/*     */     } 
/*     */     
/* 118 */     $$2.append("]");
/* 119 */     this.result = (Component)$$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitIntArray(IntArrayTag $$0) {
/* 124 */     MutableComponent mutableComponent1 = Component.literal("I").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
/* 125 */     MutableComponent $$2 = Component.literal("[").append((Component)mutableComponent1).append(";");
/*     */     
/* 127 */     int[] $$3 = $$0.getAsIntArray();
/* 128 */     for (int $$4 = 0; $$4 < $$3.length; $$4++) {
/* 129 */       $$2.append(" ").append((Component)Component.literal(String.valueOf($$3[$$4])).withStyle(SYNTAX_HIGHLIGHTING_NUMBER));
/* 130 */       if ($$4 != $$3.length - 1) {
/* 131 */         $$2.append(ELEMENT_SEPARATOR);
/*     */       }
/*     */     } 
/*     */     
/* 135 */     $$2.append("]");
/* 136 */     this.result = (Component)$$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLongArray(LongArrayTag $$0) {
/* 141 */     MutableComponent mutableComponent1 = Component.literal("L").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
/* 142 */     MutableComponent $$2 = Component.literal("[").append((Component)mutableComponent1).append(";");
/*     */     
/* 144 */     long[] $$3 = $$0.getAsLongArray();
/* 145 */     for (int $$4 = 0; $$4 < $$3.length; $$4++) {
/* 146 */       MutableComponent mutableComponent = Component.literal(String.valueOf($$3[$$4])).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
/* 147 */       $$2.append(" ").append((Component)mutableComponent).append((Component)mutableComponent1);
/* 148 */       if ($$4 != $$3.length - 1) {
/* 149 */         $$2.append(ELEMENT_SEPARATOR);
/*     */       }
/*     */     } 
/*     */     
/* 153 */     $$2.append("]");
/* 154 */     this.result = (Component)$$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitList(ListTag $$0) {
/* 159 */     if ($$0.isEmpty()) {
/* 160 */       this.result = (Component)Component.literal("[]");
/*     */       
/*     */       return;
/*     */     } 
/* 164 */     if (INLINE_ELEMENT_TYPES.contains($$0.getElementType()) && $$0.size() <= 8) {
/* 165 */       String $$1 = ELEMENT_SEPARATOR + " ";
/* 166 */       MutableComponent $$2 = Component.literal("[");
/* 167 */       for (int $$3 = 0; $$3 < $$0.size(); $$3++) {
/* 168 */         if ($$3 != 0) {
/* 169 */           $$2.append($$1);
/*     */         }
/* 171 */         $$2.append((new TextComponentTagVisitor(this.indentation, this.depth)).visit($$0.get($$3)));
/*     */       } 
/* 173 */       $$2.append("]");
/* 174 */       this.result = (Component)$$2;
/*     */       
/*     */       return;
/*     */     } 
/* 178 */     MutableComponent $$4 = Component.literal("[");
/* 179 */     if (!this.indentation.isEmpty()) {
/* 180 */       $$4.append("\n");
/*     */     }
/* 182 */     for (int $$5 = 0; $$5 < $$0.size(); $$5++) {
/* 183 */       MutableComponent $$6 = Component.literal(Strings.repeat(this.indentation, this.depth + 1));
/* 184 */       $$6.append((new TextComponentTagVisitor(this.indentation, this.depth + 1)).visit($$0.get($$5)));
/* 185 */       if ($$5 != $$0.size() - 1) {
/* 186 */         $$6.append(ELEMENT_SEPARATOR).append(this.indentation.isEmpty() ? " " : "\n");
/*     */       }
/* 188 */       $$4.append((Component)$$6);
/*     */     } 
/* 190 */     if (!this.indentation.isEmpty()) {
/* 191 */       $$4.append("\n").append(Strings.repeat(this.indentation, this.depth));
/*     */     }
/* 193 */     $$4.append("]");
/*     */     
/* 195 */     this.result = (Component)$$4;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitCompound(CompoundTag $$0) {
/* 200 */     if ($$0.isEmpty()) {
/* 201 */       this.result = (Component)Component.literal("{}");
/*     */       
/*     */       return;
/*     */     } 
/* 205 */     MutableComponent $$1 = Component.literal("{");
/*     */     
/* 207 */     Collection<String> $$2 = $$0.getAllKeys();
/* 208 */     if (LOGGER.isDebugEnabled()) {
/* 209 */       List<String> $$3 = Lists.newArrayList($$0.getAllKeys());
/* 210 */       Collections.sort($$3);
/* 211 */       $$2 = $$3;
/*     */     } 
/*     */     
/* 214 */     if (!this.indentation.isEmpty()) {
/* 215 */       $$1.append("\n");
/*     */     }
/*     */     
/* 218 */     for (Iterator<String> $$4 = $$2.iterator(); $$4.hasNext(); ) {
/* 219 */       String $$5 = $$4.next();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 224 */       MutableComponent $$6 = Component.literal(Strings.repeat(this.indentation, this.depth + 1)).append(handleEscapePretty($$5)).append(NAME_VALUE_SEPARATOR).append(" ").append((new TextComponentTagVisitor(this.indentation, this.depth + 1)).visit($$0.get($$5)));
/*     */       
/* 226 */       if ($$4.hasNext()) {
/* 227 */         $$6.append(ELEMENT_SEPARATOR).append(this.indentation.isEmpty() ? " " : "\n");
/*     */       }
/* 229 */       $$1.append((Component)$$6);
/*     */     } 
/* 231 */     if (!this.indentation.isEmpty()) {
/* 232 */       $$1.append("\n").append(Strings.repeat(this.indentation, this.depth));
/*     */     }
/* 234 */     $$1.append("}");
/* 235 */     this.result = (Component)$$1;
/*     */   }
/*     */   
/*     */   protected static Component handleEscapePretty(String $$0) {
/* 239 */     if (SIMPLE_VALUE.matcher($$0).matches()) {
/* 240 */       return (Component)Component.literal($$0).withStyle(SYNTAX_HIGHLIGHTING_KEY);
/*     */     }
/*     */     
/* 243 */     String $$1 = StringTag.quoteAndEscape($$0);
/* 244 */     String $$2 = $$1.substring(0, 1);
/* 245 */     MutableComponent mutableComponent = Component.literal($$1.substring(1, $$1.length() - 1)).withStyle(SYNTAX_HIGHLIGHTING_KEY);
/* 246 */     return (Component)Component.literal($$2).append((Component)mutableComponent).append($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd(EndTag $$0) {
/* 251 */     this.result = CommonComponents.EMPTY;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\TextComponentTagVisitor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */