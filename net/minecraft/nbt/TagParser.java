/*     */ package net.minecraft.nbt;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.network.chat.Component;
/*     */ 
/*     */ public class TagParser {
/*  19 */   public static final SimpleCommandExceptionType ERROR_TRAILING_DATA = new SimpleCommandExceptionType((Message)Component.translatable("argument.nbt.trailing"));
/*  20 */   public static final SimpleCommandExceptionType ERROR_EXPECTED_KEY = new SimpleCommandExceptionType((Message)Component.translatable("argument.nbt.expected.key")); public static final Dynamic2CommandExceptionType ERROR_INSERT_MIXED_LIST;
/*  21 */   public static final SimpleCommandExceptionType ERROR_EXPECTED_VALUE = new SimpleCommandExceptionType((Message)Component.translatable("argument.nbt.expected.value")); public static final Dynamic2CommandExceptionType ERROR_INSERT_MIXED_ARRAY; static {
/*  22 */     ERROR_INSERT_MIXED_LIST = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("argument.nbt.list.mixed", new Object[] { $$0, $$1 }));
/*  23 */     ERROR_INSERT_MIXED_ARRAY = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("argument.nbt.array.mixed", new Object[] { $$0, $$1 }));
/*  24 */     ERROR_INVALID_ARRAY = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("argument.nbt.array.invalid", new Object[] { $$0 }));
/*     */   }
/*     */   public static final DynamicCommandExceptionType ERROR_INVALID_ARRAY;
/*     */   public static final char ELEMENT_SEPARATOR = ',';
/*     */   public static final char NAME_VALUE_SEPARATOR = ':';
/*     */   private static final char LIST_OPEN = '[';
/*     */   private static final char LIST_CLOSE = ']';
/*     */   private static final char STRUCT_CLOSE = '}';
/*     */   private static final char STRUCT_OPEN = '{';
/*  33 */   private static final Pattern DOUBLE_PATTERN_NOSUFFIX = Pattern.compile("[-+]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?", 2);
/*  34 */   private static final Pattern DOUBLE_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", 2);
/*  35 */   private static final Pattern FLOAT_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?f", 2);
/*  36 */   private static final Pattern BYTE_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)b", 2);
/*  37 */   private static final Pattern LONG_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)l", 2);
/*  38 */   private static final Pattern SHORT_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)s", 2);
/*  39 */   private static final Pattern INT_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)"); public static final Codec<CompoundTag> AS_CODEC; private final StringReader reader;
/*     */   static {
/*  41 */     AS_CODEC = Codec.STRING.comapFlatMap($$0 -> {
/*     */           try {
/*     */             return DataResult.success((new TagParser(new StringReader($$0))).readSingleStruct(), Lifecycle.stable());
/*  44 */           } catch (CommandSyntaxException $$1) {
/*     */             Objects.requireNonNull($$1);
/*     */             return DataResult.error($$1::getMessage);
/*     */           } 
/*     */         }CompoundTag::toString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompoundTag parseTag(String $$0) throws CommandSyntaxException {
/*  54 */     return (new TagParser(new StringReader($$0))).readSingleStruct();
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   CompoundTag readSingleStruct() throws CommandSyntaxException {
/*  59 */     CompoundTag $$0 = readStruct();
/*     */     
/*  61 */     this.reader.skipWhitespace();
/*     */     
/*  63 */     if (this.reader.canRead()) {
/*  64 */       throw ERROR_TRAILING_DATA.createWithContext(this.reader);
/*     */     }
/*  66 */     return $$0;
/*     */   }
/*     */   
/*     */   public TagParser(StringReader $$0) {
/*  70 */     this.reader = $$0;
/*     */   }
/*     */   
/*     */   protected String readKey() throws CommandSyntaxException {
/*  74 */     this.reader.skipWhitespace();
/*     */     
/*  76 */     if (!this.reader.canRead()) {
/*  77 */       throw ERROR_EXPECTED_KEY.createWithContext(this.reader);
/*     */     }
/*     */     
/*  80 */     return this.reader.readString();
/*     */   }
/*     */   
/*     */   protected Tag readTypedValue() throws CommandSyntaxException {
/*  84 */     this.reader.skipWhitespace();
/*  85 */     int $$0 = this.reader.getCursor();
/*     */     
/*  87 */     if (StringReader.isQuotedStringStart(this.reader.peek())) {
/*  88 */       return StringTag.valueOf(this.reader.readQuotedString());
/*     */     }
/*     */     
/*  91 */     String $$1 = this.reader.readUnquotedString();
/*  92 */     if ($$1.isEmpty()) {
/*  93 */       this.reader.setCursor($$0);
/*  94 */       throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
/*     */     } 
/*  96 */     return type($$1);
/*     */   }
/*     */   
/*     */   private Tag type(String $$0) {
/*     */     try {
/* 101 */       if (FLOAT_PATTERN.matcher($$0).matches()) {
/* 102 */         return FloatTag.valueOf(Float.parseFloat($$0.substring(0, $$0.length() - 1)));
/*     */       }
/* 104 */       if (BYTE_PATTERN.matcher($$0).matches()) {
/* 105 */         return ByteTag.valueOf(Byte.parseByte($$0.substring(0, $$0.length() - 1)));
/*     */       }
/* 107 */       if (LONG_PATTERN.matcher($$0).matches()) {
/* 108 */         return LongTag.valueOf(Long.parseLong($$0.substring(0, $$0.length() - 1)));
/*     */       }
/* 110 */       if (SHORT_PATTERN.matcher($$0).matches()) {
/* 111 */         return ShortTag.valueOf(Short.parseShort($$0.substring(0, $$0.length() - 1)));
/*     */       }
/* 113 */       if (INT_PATTERN.matcher($$0).matches()) {
/* 114 */         return IntTag.valueOf(Integer.parseInt($$0));
/*     */       }
/* 116 */       if (DOUBLE_PATTERN.matcher($$0).matches()) {
/* 117 */         return DoubleTag.valueOf(Double.parseDouble($$0.substring(0, $$0.length() - 1)));
/*     */       }
/* 119 */       if (DOUBLE_PATTERN_NOSUFFIX.matcher($$0).matches()) {
/* 120 */         return DoubleTag.valueOf(Double.parseDouble($$0));
/*     */       }
/* 122 */       if ("true".equalsIgnoreCase($$0)) {
/* 123 */         return ByteTag.ONE;
/*     */       }
/* 125 */       if ("false".equalsIgnoreCase($$0)) {
/* 126 */         return ByteTag.ZERO;
/*     */       }
/* 128 */     } catch (NumberFormatException numberFormatException) {}
/*     */ 
/*     */     
/* 131 */     return StringTag.valueOf($$0);
/*     */   }
/*     */   
/*     */   public Tag readValue() throws CommandSyntaxException {
/* 135 */     this.reader.skipWhitespace();
/*     */     
/* 137 */     if (!this.reader.canRead()) {
/* 138 */       throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
/*     */     }
/*     */     
/* 141 */     char $$0 = this.reader.peek();
/* 142 */     if ($$0 == '{')
/* 143 */       return readStruct(); 
/* 144 */     if ($$0 == '[') {
/* 145 */       return readList();
/*     */     }
/* 147 */     return readTypedValue();
/*     */   }
/*     */   
/*     */   protected Tag readList() throws CommandSyntaxException {
/* 151 */     if (this.reader.canRead(3) && 
/* 152 */       !StringReader.isQuotedStringStart(this.reader.peek(1)) && this.reader.peek(2) == ';') {
/* 153 */       return readArrayTag();
/*     */     }
/*     */     
/* 156 */     return readListTag();
/*     */   }
/*     */   
/*     */   public CompoundTag readStruct() throws CommandSyntaxException {
/* 160 */     expect('{');
/*     */     
/* 162 */     CompoundTag $$0 = new CompoundTag();
/*     */     
/* 164 */     this.reader.skipWhitespace();
/* 165 */     while (this.reader.canRead() && this.reader.peek() != '}') {
/* 166 */       int $$1 = this.reader.getCursor();
/* 167 */       String $$2 = readKey();
/* 168 */       if ($$2.isEmpty()) {
/* 169 */         this.reader.setCursor($$1);
/* 170 */         throw ERROR_EXPECTED_KEY.createWithContext(this.reader);
/*     */       } 
/*     */       
/* 173 */       expect(':');
/*     */       
/* 175 */       $$0.put($$2, readValue());
/*     */       
/* 177 */       if (!hasElementSeparator())
/*     */         break; 
/* 179 */       if (!this.reader.canRead()) {
/* 180 */         throw ERROR_EXPECTED_KEY.createWithContext(this.reader);
/*     */       }
/*     */     } 
/* 183 */     expect('}');
/*     */     
/* 185 */     return $$0;
/*     */   }
/*     */   
/*     */   private Tag readListTag() throws CommandSyntaxException {
/* 189 */     expect('[');
/*     */     
/* 191 */     this.reader.skipWhitespace();
/*     */     
/* 193 */     if (!this.reader.canRead()) {
/* 194 */       throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
/*     */     }
/*     */     
/* 197 */     ListTag $$0 = new ListTag();
/*     */     
/* 199 */     TagType<?> $$1 = null;
/* 200 */     while (this.reader.peek() != ']') {
/* 201 */       int $$2 = this.reader.getCursor();
/* 202 */       Tag $$3 = readValue();
/*     */       
/* 204 */       TagType<?> $$4 = $$3.getType();
/* 205 */       if ($$1 == null) {
/* 206 */         $$1 = $$4;
/* 207 */       } else if ($$4 != $$1) {
/* 208 */         this.reader.setCursor($$2);
/* 209 */         throw ERROR_INSERT_MIXED_LIST.createWithContext(this.reader, $$4.getPrettyName(), $$1.getPrettyName());
/*     */       } 
/*     */       
/* 212 */       $$0.add($$3);
/*     */       
/* 214 */       if (!hasElementSeparator())
/*     */         break; 
/* 216 */       if (!this.reader.canRead()) {
/* 217 */         throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
/*     */       }
/*     */     } 
/* 220 */     expect(']');
/*     */     
/* 222 */     return $$0;
/*     */   }
/*     */   
/*     */   private Tag readArrayTag() throws CommandSyntaxException {
/* 226 */     expect('[');
/* 227 */     int $$0 = this.reader.getCursor();
/* 228 */     char $$1 = this.reader.read();
/* 229 */     this.reader.read();
/*     */     
/* 231 */     this.reader.skipWhitespace();
/*     */     
/* 233 */     if (!this.reader.canRead()) {
/* 234 */       throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
/*     */     }
/*     */     
/* 237 */     if ($$1 == 'B')
/* 238 */       return new ByteArrayTag(readArray(ByteArrayTag.TYPE, ByteTag.TYPE)); 
/* 239 */     if ($$1 == 'L')
/* 240 */       return new LongArrayTag(readArray(LongArrayTag.TYPE, LongTag.TYPE)); 
/* 241 */     if ($$1 == 'I') {
/* 242 */       return new IntArrayTag(readArray(IntArrayTag.TYPE, IntTag.TYPE));
/*     */     }
/* 244 */     this.reader.setCursor($$0);
/* 245 */     throw ERROR_INVALID_ARRAY.createWithContext(this.reader, String.valueOf($$1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private <T extends Number> List<T> readArray(TagType<?> $$0, TagType<?> $$1) throws CommandSyntaxException {
/* 251 */     List<T> $$2 = Lists.newArrayList();
/*     */     
/* 253 */     while (this.reader.peek() != ']') {
/* 254 */       int $$3 = this.reader.getCursor();
/* 255 */       Tag $$4 = readValue();
/*     */       
/* 257 */       TagType<?> $$5 = $$4.getType();
/* 258 */       if ($$5 != $$1) {
/* 259 */         this.reader.setCursor($$3);
/* 260 */         throw ERROR_INSERT_MIXED_ARRAY.createWithContext(this.reader, $$5.getPrettyName(), $$0.getPrettyName());
/*     */       } 
/*     */       
/* 263 */       if ($$1 == ByteTag.TYPE) {
/* 264 */         $$2.add((T)Byte.valueOf(((NumericTag)$$4).getAsByte()));
/* 265 */       } else if ($$1 == LongTag.TYPE) {
/* 266 */         $$2.add((T)Long.valueOf(((NumericTag)$$4).getAsLong()));
/*     */       } else {
/* 268 */         $$2.add((T)Integer.valueOf(((NumericTag)$$4).getAsInt()));
/*     */       } 
/*     */       
/* 271 */       if (!hasElementSeparator())
/*     */         break; 
/* 273 */       if (!this.reader.canRead()) {
/* 274 */         throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
/*     */       }
/*     */     } 
/* 277 */     expect(']');
/*     */     
/* 279 */     return $$2;
/*     */   }
/*     */   
/*     */   private boolean hasElementSeparator() {
/* 283 */     this.reader.skipWhitespace();
/* 284 */     if (this.reader.canRead() && this.reader.peek() == ',') {
/* 285 */       this.reader.skip();
/* 286 */       this.reader.skipWhitespace();
/* 287 */       return true;
/*     */     } 
/* 289 */     return false;
/*     */   }
/*     */   
/*     */   private void expect(char $$0) throws CommandSyntaxException {
/* 293 */     this.reader.skipWhitespace();
/*     */     
/* 295 */     this.reader.expect($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\TagParser.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */