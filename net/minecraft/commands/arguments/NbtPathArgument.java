/*     */ package net.minecraft.commands.arguments;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.nbt.CollectionTag;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.nbt.TagParser;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import org.apache.commons.lang3.mutable.MutableBoolean;
/*     */ 
/*     */ public class NbtPathArgument implements ArgumentType<NbtPathArgument.NbtPath> {
/*  33 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "foo", "foo.bar", "foo[0]", "[0]", "[]", "{foo=bar}" });
/*  34 */   public static final SimpleCommandExceptionType ERROR_INVALID_NODE = new SimpleCommandExceptionType((Message)Component.translatable("arguments.nbtpath.node.invalid"));
/*  35 */   public static final SimpleCommandExceptionType ERROR_DATA_TOO_DEEP = new SimpleCommandExceptionType((Message)Component.translatable("arguments.nbtpath.too_deep")); public static final DynamicCommandExceptionType ERROR_NOTHING_FOUND; static final DynamicCommandExceptionType ERROR_EXPECTED_LIST; static final DynamicCommandExceptionType ERROR_INVALID_INDEX; private static final char INDEX_MATCH_START = '['; static {
/*  36 */     ERROR_NOTHING_FOUND = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("arguments.nbtpath.nothing_found", new Object[] { $$0 }));
/*  37 */     ERROR_EXPECTED_LIST = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.data.modify.expected_list", new Object[] { $$0 }));
/*  38 */     ERROR_INVALID_INDEX = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.data.modify.invalid_index", new Object[] { $$0 }));
/*     */   }
/*     */   private static final char INDEX_MATCH_END = ']';
/*     */   private static final char KEY_MATCH_START = '{';
/*     */   private static final char KEY_MATCH_END = '}';
/*     */   private static final char QUOTED_KEY_START = '"';
/*     */   private static final char SINGLE_QUOTED_KEY_START = '\'';
/*     */   
/*     */   public static NbtPathArgument nbtPath() {
/*  47 */     return new NbtPathArgument();
/*     */   }
/*     */   
/*     */   public static NbtPath getPath(CommandContext<CommandSourceStack> $$0, String $$1) {
/*  51 */     return (NbtPath)$$0.getArgument($$1, NbtPath.class);
/*     */   }
/*     */   
/*     */   public NbtPath parse(StringReader $$0) throws CommandSyntaxException
/*     */   {
/*  56 */     List<Node> $$1 = Lists.newArrayList();
/*  57 */     int $$2 = $$0.getCursor();
/*     */     
/*  59 */     Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
/*  60 */     boolean $$4 = true;
/*  61 */     while ($$0.canRead() && $$0.peek() != ' ') {
/*  62 */       Node $$5 = parseNode($$0, $$4);
/*  63 */       $$1.add($$5);
/*  64 */       object2IntOpenHashMap.put($$5, $$0.getCursor() - $$2);
/*  65 */       $$4 = false;
/*  66 */       if ($$0.canRead()) {
/*  67 */         char $$6 = $$0.peek();
/*  68 */         if ($$6 != ' ' && $$6 != '[' && $$6 != '{') {
/*  69 */           $$0.expect('.');
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  74 */     return new NbtPath($$0.getString().substring($$2, $$0.getCursor()), $$1.<Node>toArray(new Node[0]), (Object2IntMap<Node>)object2IntOpenHashMap); } private static Node parseNode(StringReader $$0, boolean $$1) throws CommandSyntaxException { CompoundTag $$2;
/*     */     int $$3;
/*     */     CompoundTag $$4;
/*     */     int $$5;
/*  78 */     switch ($$0.peek())
/*     */     { case '{':
/*  80 */         if (!$$1) {
/*  81 */           throw ERROR_INVALID_NODE.createWithContext($$0);
/*     */         }
/*  83 */         $$2 = (new TagParser($$0)).readStruct();
/*     */ 
/*     */       
/*     */       case '[':
/*  87 */         $$0.skip();
/*  88 */         $$3 = $$0.peek();
/*     */         
/*  90 */         $$4 = (new TagParser($$0)).readStruct();
/*  91 */         $$0.expect(']');
/*     */ 
/*     */         
/*  94 */         $$0.skip();
/*     */ 
/*     */ 
/*     */         
/*  98 */         $$5 = $$0.readInt();
/*  99 */         $$0.expect(']');
/* 100 */         return ($$3 == 123) ? new MatchElementNode($$4) : (($$3 == 93) ? AllElementsNode.INSTANCE : new IndexedElementNode($$5));
/*     */       case '"':
/*     */       case '\'':
/* 103 */        }  return readObjectNode($$0, readUnquotedName($$0)); }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Node readObjectNode(StringReader $$0, String $$1) throws CommandSyntaxException {
/* 108 */     if ($$0.canRead() && $$0.peek() == '{') {
/* 109 */       CompoundTag $$2 = (new TagParser($$0)).readStruct();
/* 110 */       return new MatchObjectNode($$1, $$2);
/*     */     } 
/* 112 */     return new CompoundChildNode($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String readUnquotedName(StringReader $$0) throws CommandSyntaxException {
/* 117 */     int $$1 = $$0.getCursor();
/* 118 */     while ($$0.canRead() && isAllowedInUnquotedName($$0.peek())) {
/* 119 */       $$0.skip();
/*     */     }
/* 121 */     if ($$0.getCursor() == $$1) {
/* 122 */       throw ERROR_INVALID_NODE.createWithContext($$0);
/*     */     }
/* 124 */     return $$0.getString().substring($$1, $$0.getCursor());
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getExamples() {
/* 129 */     return EXAMPLES;
/*     */   }
/*     */   
/*     */   private static boolean isAllowedInUnquotedName(char $$0) {
/* 133 */     return ($$0 != ' ' && $$0 != '"' && $$0 != '\'' && $$0 != '[' && $$0 != ']' && $$0 != '.' && $$0 != '{' && $$0 != '}');
/*     */   }
/*     */   
/*     */   public static class NbtPath {
/*     */     private final String original;
/*     */     private final Object2IntMap<NbtPathArgument.Node> nodeToOriginalPosition;
/*     */     private final NbtPathArgument.Node[] nodes;
/*     */     
/*     */     public NbtPath(String $$0, NbtPathArgument.Node[] $$1, Object2IntMap<NbtPathArgument.Node> $$2) {
/* 142 */       this.original = $$0;
/* 143 */       this.nodes = $$1;
/* 144 */       this.nodeToOriginalPosition = $$2;
/*     */     }
/*     */     
/*     */     public List<Tag> get(Tag $$0) throws CommandSyntaxException {
/* 148 */       List<Tag> $$1 = Collections.singletonList($$0);
/* 149 */       for (NbtPathArgument.Node $$2 : this.nodes) {
/* 150 */         $$1 = $$2.get($$1);
/* 151 */         if ($$1.isEmpty()) {
/* 152 */           throw createNotFoundException($$2);
/*     */         }
/*     */       } 
/* 155 */       return $$1;
/*     */     }
/*     */     
/*     */     public int countMatching(Tag $$0) {
/* 159 */       List<Tag> $$1 = Collections.singletonList($$0);
/* 160 */       for (NbtPathArgument.Node $$2 : this.nodes) {
/* 161 */         $$1 = $$2.get($$1);
/* 162 */         if ($$1.isEmpty()) {
/* 163 */           return 0;
/*     */         }
/*     */       } 
/* 166 */       return $$1.size();
/*     */     }
/*     */     
/*     */     private List<Tag> getOrCreateParents(Tag $$0) throws CommandSyntaxException {
/* 170 */       List<Tag> $$1 = Collections.singletonList($$0);
/*     */       
/* 172 */       for (int $$2 = 0; $$2 < this.nodes.length - 1; $$2++) {
/* 173 */         NbtPathArgument.Node $$3 = this.nodes[$$2];
/* 174 */         int $$4 = $$2 + 1;
/* 175 */         Objects.requireNonNull(this.nodes[$$4]); $$1 = $$3.getOrCreate($$1, this.nodes[$$4]::createPreferredParentTag);
/* 176 */         if ($$1.isEmpty()) {
/* 177 */           throw createNotFoundException($$3);
/*     */         }
/*     */       } 
/* 180 */       return $$1;
/*     */     }
/*     */     
/*     */     public List<Tag> getOrCreate(Tag $$0, Supplier<Tag> $$1) throws CommandSyntaxException {
/* 184 */       List<Tag> $$2 = getOrCreateParents($$0);
/*     */       
/* 186 */       NbtPathArgument.Node $$3 = this.nodes[this.nodes.length - 1];
/* 187 */       return $$3.getOrCreate($$2, $$1);
/*     */     }
/*     */     
/*     */     private static int apply(List<Tag> $$0, Function<Tag, Integer> $$1) {
/* 191 */       return ((Integer)$$0.stream().<Integer>map($$1).reduce(Integer.valueOf(0), ($$0, $$1) -> Integer.valueOf($$0.intValue() + $$1.intValue()))).intValue();
/*     */     }
/*     */     
/*     */     public static boolean isTooDeep(Tag $$0, int $$1) {
/* 195 */       if ($$1 >= 512) {
/* 196 */         return true;
/*     */       }
/* 198 */       if ($$0 instanceof CompoundTag) { CompoundTag $$2 = (CompoundTag)$$0;
/* 199 */         for (String $$3 : $$2.getAllKeys()) {
/* 200 */           Tag $$4 = $$2.get($$3);
/* 201 */           if ($$4 != null && 
/* 202 */             isTooDeep($$4, $$1 + 1)) {
/* 203 */             return true;
/*     */           }
/*     */         }
/*     */          }
/* 207 */       else if ($$0 instanceof ListTag) { ListTag $$5 = (ListTag)$$0;
/* 208 */         for (Tag $$6 : $$5) {
/* 209 */           if (isTooDeep($$6, $$1 + 1)) {
/* 210 */             return true;
/*     */           }
/*     */         }  }
/*     */       
/* 214 */       return false;
/*     */     }
/*     */     
/*     */     public int set(Tag $$0, Tag $$1) throws CommandSyntaxException {
/* 218 */       if (isTooDeep($$1, estimatePathDepth())) {
/* 219 */         throw NbtPathArgument.ERROR_DATA_TOO_DEEP.create();
/*     */       }
/* 221 */       Tag $$2 = $$1.copy();
/* 222 */       List<Tag> $$3 = getOrCreateParents($$0);
/* 223 */       if ($$3.isEmpty()) {
/* 224 */         return 0;
/*     */       }
/*     */       
/* 227 */       NbtPathArgument.Node $$4 = this.nodes[this.nodes.length - 1];
/* 228 */       MutableBoolean $$5 = new MutableBoolean(false);
/* 229 */       return apply($$3, $$3 -> Integer.valueOf($$0.setTag($$3, ())));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int estimatePathDepth() {
/* 240 */       return this.nodes.length;
/*     */     }
/*     */     
/*     */     public int insert(int $$0, CompoundTag $$1, List<Tag> $$2) throws CommandSyntaxException {
/* 244 */       List<Tag> $$3 = new ArrayList<>($$2.size());
/* 245 */       for (Tag $$4 : $$2) {
/* 246 */         Tag $$5 = $$4.copy();
/* 247 */         $$3.add($$5);
/* 248 */         if (isTooDeep($$5, estimatePathDepth())) {
/* 249 */           throw NbtPathArgument.ERROR_DATA_TOO_DEEP.create();
/*     */         }
/*     */       } 
/* 252 */       Collection<Tag> $$6 = getOrCreate((Tag)$$1, ListTag::new);
/*     */       
/* 254 */       int $$7 = 0;
/* 255 */       boolean $$8 = false;
/* 256 */       for (Tag $$9 : $$6) {
/* 257 */         if (!($$9 instanceof CollectionTag)) {
/* 258 */           throw NbtPathArgument.ERROR_EXPECTED_LIST.create($$9);
/*     */         }
/* 260 */         CollectionTag<?> $$10 = (CollectionTag)$$9;
/*     */         
/* 262 */         boolean $$11 = false;
/* 263 */         int $$12 = ($$0 < 0) ? ($$10.size() + $$0 + 1) : $$0;
/* 264 */         for (Tag $$13 : $$3) {
/*     */           try {
/* 266 */             if ($$10.addTag($$12, $$8 ? $$13.copy() : $$13)) {
/* 267 */               $$12++;
/* 268 */               $$11 = true;
/*     */             } 
/* 270 */           } catch (IndexOutOfBoundsException $$14) {
/* 271 */             throw NbtPathArgument.ERROR_INVALID_INDEX.create(Integer.valueOf($$12));
/*     */           } 
/*     */         } 
/* 274 */         $$8 = true;
/* 275 */         $$7 += $$11 ? 1 : 0;
/*     */       } 
/*     */       
/* 278 */       return $$7;
/*     */     }
/*     */     
/*     */     public int remove(Tag $$0) {
/* 282 */       List<Tag> $$1 = Collections.singletonList($$0);
/*     */       
/* 284 */       for (int $$2 = 0; $$2 < this.nodes.length - 1; $$2++) {
/* 285 */         $$1 = this.nodes[$$2].get($$1);
/*     */       }
/*     */       
/* 288 */       NbtPathArgument.Node $$3 = this.nodes[this.nodes.length - 1];
/* 289 */       Objects.requireNonNull($$3); return apply($$1, $$3::removeTag);
/*     */     }
/*     */     
/*     */     private CommandSyntaxException createNotFoundException(NbtPathArgument.Node $$0) {
/* 293 */       int $$1 = this.nodeToOriginalPosition.getInt($$0);
/* 294 */       return NbtPathArgument.ERROR_NOTHING_FOUND.create(this.original.substring(0, $$1));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 299 */       return this.original;
/*     */     }
/*     */     
/*     */     public String asString() {
/* 303 */       return this.original;
/*     */     }
/*     */   }
/*     */   
/*     */   static Predicate<Tag> createTagPredicate(CompoundTag $$0) {
/* 308 */     return $$1 -> NbtUtils.compareNbt((Tag)$$0, $$1, true);
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
/*     */   private static interface Node
/*     */   {
/*     */     default List<Tag> get(List<Tag> $$0) {
/* 323 */       return collect($$0, this::getTag);
/*     */     }
/*     */     
/*     */     default List<Tag> getOrCreate(List<Tag> $$0, Supplier<Tag> $$1) {
/* 327 */       return collect($$0, ($$1, $$2) -> getOrCreateTag($$1, $$0, $$2));
/*     */     } void getTag(Tag param1Tag, List<Tag> param1List); void getOrCreateTag(Tag param1Tag, Supplier<Tag> param1Supplier, List<Tag> param1List);
/*     */     Tag createPreferredParentTag();
/*     */     default List<Tag> collect(List<Tag> $$0, BiConsumer<Tag, List<Tag>> $$1) {
/* 331 */       List<Tag> $$2 = Lists.newArrayList();
/*     */       
/* 333 */       for (Tag $$3 : $$0) {
/* 334 */         $$1.accept($$3, $$2);
/*     */       }
/*     */       
/* 337 */       return $$2;
/*     */     }
/*     */     
/*     */     int setTag(Tag param1Tag, Supplier<Tag> param1Supplier);
/*     */     
/*     */     int removeTag(Tag param1Tag); }
/*     */   
/*     */   private static class CompoundChildNode implements Node { public CompoundChildNode(String $$0) {
/* 345 */       this.name = $$0;
/*     */     }
/*     */     private final String name;
/*     */     
/*     */     public void getTag(Tag $$0, List<Tag> $$1) {
/* 350 */       if ($$0 instanceof CompoundTag) {
/* 351 */         Tag $$2 = ((CompoundTag)$$0).get(this.name);
/* 352 */         if ($$2 != null) {
/* 353 */           $$1.add($$2);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void getOrCreateTag(Tag $$0, Supplier<Tag> $$1, List<Tag> $$2) {
/* 360 */       if ($$0 instanceof CompoundTag) { Tag $$5; CompoundTag $$3 = (CompoundTag)$$0;
/*     */         
/* 362 */         if ($$3.contains(this.name)) {
/* 363 */           Tag $$4 = $$3.get(this.name);
/*     */         } else {
/* 365 */           $$5 = $$1.get();
/* 366 */           $$3.put(this.name, $$5);
/*     */         } 
/*     */         
/* 369 */         $$2.add($$5); }
/*     */     
/*     */     }
/*     */ 
/*     */     
/*     */     public Tag createPreferredParentTag() {
/* 375 */       return (Tag)new CompoundTag();
/*     */     }
/*     */ 
/*     */     
/*     */     public int setTag(Tag $$0, Supplier<Tag> $$1) {
/* 380 */       if ($$0 instanceof CompoundTag) { CompoundTag $$2 = (CompoundTag)$$0;
/* 381 */         Tag $$3 = $$1.get();
/* 382 */         Tag $$4 = $$2.put(this.name, $$3);
/* 383 */         if (!$$3.equals($$4)) {
/* 384 */           return 1;
/*     */         } }
/*     */ 
/*     */       
/* 388 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public int removeTag(Tag $$0) {
/* 393 */       if ($$0 instanceof CompoundTag) { CompoundTag $$1 = (CompoundTag)$$0;
/* 394 */         if ($$1.contains(this.name)) {
/* 395 */           $$1.remove(this.name);
/* 396 */           return 1;
/*     */         }  }
/*     */ 
/*     */       
/* 400 */       return 0;
/*     */     } }
/*     */ 
/*     */   
/*     */   private static class IndexedElementNode implements Node {
/*     */     private final int index;
/*     */     
/*     */     public IndexedElementNode(int $$0) {
/* 408 */       this.index = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void getTag(Tag $$0, List<Tag> $$1) {
/* 413 */       if ($$0 instanceof CollectionTag) { CollectionTag<?> $$2 = (CollectionTag)$$0;
/* 414 */         int $$3 = $$2.size();
/* 415 */         int $$4 = (this.index < 0) ? ($$3 + this.index) : this.index;
/*     */         
/* 417 */         if (0 <= $$4 && $$4 < $$3) {
/* 418 */           $$1.add((Tag)$$2.get($$4));
/*     */         } }
/*     */     
/*     */     }
/*     */ 
/*     */     
/*     */     public void getOrCreateTag(Tag $$0, Supplier<Tag> $$1, List<Tag> $$2) {
/* 425 */       getTag($$0, $$2);
/*     */     }
/*     */ 
/*     */     
/*     */     public Tag createPreferredParentTag() {
/* 430 */       return (Tag)new ListTag();
/*     */     }
/*     */ 
/*     */     
/*     */     public int setTag(Tag $$0, Supplier<Tag> $$1) {
/* 435 */       if ($$0 instanceof CollectionTag) { CollectionTag<?> $$2 = (CollectionTag)$$0;
/* 436 */         int $$3 = $$2.size();
/* 437 */         int $$4 = (this.index < 0) ? ($$3 + this.index) : this.index;
/*     */         
/* 439 */         if (0 <= $$4 && $$4 < $$3) {
/* 440 */           Tag $$5 = (Tag)$$2.get($$4);
/* 441 */           Tag $$6 = $$1.get();
/* 442 */           if (!$$6.equals($$5) && $$2.setTag($$4, $$6)) {
/* 443 */             return 1;
/*     */           }
/*     */         }  }
/*     */ 
/*     */       
/* 448 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public int removeTag(Tag $$0) {
/* 453 */       if ($$0 instanceof CollectionTag) { CollectionTag<?> $$1 = (CollectionTag)$$0;
/* 454 */         int $$2 = $$1.size();
/* 455 */         int $$3 = (this.index < 0) ? ($$2 + this.index) : this.index;
/*     */         
/* 457 */         if (0 <= $$3 && $$3 < $$2) {
/* 458 */           $$1.remove($$3);
/* 459 */           return 1;
/*     */         }  }
/*     */ 
/*     */       
/* 463 */       return 0;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MatchElementNode implements Node {
/*     */     private final CompoundTag pattern;
/*     */     private final Predicate<Tag> predicate;
/*     */     
/*     */     public MatchElementNode(CompoundTag $$0) {
/* 472 */       this.pattern = $$0;
/* 473 */       this.predicate = NbtPathArgument.createTagPredicate($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void getTag(Tag $$0, List<Tag> $$1) {
/* 478 */       if ($$0 instanceof ListTag) { ListTag $$2 = (ListTag)$$0;
/* 479 */         Objects.requireNonNull($$1); $$2.stream().filter(this.predicate).forEach($$1::add); }
/*     */     
/*     */     }
/*     */ 
/*     */     
/*     */     public void getOrCreateTag(Tag $$0, Supplier<Tag> $$1, List<Tag> $$2) {
/* 485 */       MutableBoolean $$3 = new MutableBoolean();
/* 486 */       if ($$0 instanceof ListTag) { ListTag $$4 = (ListTag)$$0;
/* 487 */         $$4.stream().filter(this.predicate).forEach($$2 -> {
/*     */               $$0.add($$2);
/*     */               
/*     */               $$1.setTrue();
/*     */             });
/* 492 */         if ($$3.isFalse()) {
/* 493 */           CompoundTag $$5 = this.pattern.copy();
/* 494 */           $$4.add($$5);
/* 495 */           $$2.add($$5);
/*     */         }  }
/*     */     
/*     */     }
/*     */ 
/*     */     
/*     */     public Tag createPreferredParentTag() {
/* 502 */       return (Tag)new ListTag();
/*     */     }
/*     */ 
/*     */     
/*     */     public int setTag(Tag $$0, Supplier<Tag> $$1) {
/* 507 */       int $$2 = 0;
/* 508 */       if ($$0 instanceof ListTag) { ListTag $$3 = (ListTag)$$0;
/* 509 */         int $$4 = $$3.size();
/* 510 */         if ($$4 == 0) {
/* 511 */           $$3.add($$1.get());
/* 512 */           $$2++;
/*     */         } else {
/* 514 */           for (int $$5 = 0; $$5 < $$4; $$5++) {
/* 515 */             Tag $$6 = $$3.get($$5);
/* 516 */             if (this.predicate.test($$6)) {
/* 517 */               Tag $$7 = $$1.get();
/* 518 */               if (!$$7.equals($$6) && $$3.setTag($$5, $$7)) {
/* 519 */                 $$2++;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         }  }
/*     */ 
/*     */       
/* 526 */       return $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public int removeTag(Tag $$0) {
/* 531 */       int $$1 = 0;
/* 532 */       if ($$0 instanceof ListTag) { ListTag $$2 = (ListTag)$$0;
/* 533 */         for (int $$3 = $$2.size() - 1; $$3 >= 0; $$3--) {
/* 534 */           if (this.predicate.test($$2.get($$3))) {
/* 535 */             $$2.remove($$3);
/* 536 */             $$1++;
/*     */           } 
/*     */         }  }
/*     */ 
/*     */       
/* 541 */       return $$1;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class AllElementsNode
/*     */     implements Node
/*     */   {
/* 549 */     public static final AllElementsNode INSTANCE = new AllElementsNode();
/*     */ 
/*     */     
/*     */     public void getTag(Tag $$0, List<Tag> $$1) {
/* 553 */       if ($$0 instanceof CollectionTag) {
/* 554 */         $$1.addAll((Collection<? extends Tag>)$$0);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void getOrCreateTag(Tag $$0, Supplier<Tag> $$1, List<Tag> $$2) {
/* 560 */       if ($$0 instanceof CollectionTag) { CollectionTag<?> $$3 = (CollectionTag)$$0;
/* 561 */         if ($$3.isEmpty()) {
/* 562 */           Tag $$4 = $$1.get();
/* 563 */           if ($$3.addTag(0, $$4)) {
/* 564 */             $$2.add($$4);
/*     */           }
/*     */         } else {
/* 567 */           $$2.addAll((Collection<?>)$$3);
/*     */         }  }
/*     */     
/*     */     }
/*     */ 
/*     */     
/*     */     public Tag createPreferredParentTag() {
/* 574 */       return (Tag)new ListTag();
/*     */     }
/*     */ 
/*     */     
/*     */     public int setTag(Tag $$0, Supplier<Tag> $$1) {
/* 579 */       if ($$0 instanceof CollectionTag) { CollectionTag<?> $$2 = (CollectionTag)$$0;
/* 580 */         int $$3 = $$2.size();
/* 581 */         if ($$3 == 0) {
/* 582 */           $$2.addTag(0, $$1.get());
/* 583 */           return 1;
/*     */         } 
/* 585 */         Tag $$4 = $$1.get();
/* 586 */         Objects.requireNonNull($$4); int $$5 = $$3 - (int)$$2.stream().filter($$4::equals).count();
/* 587 */         if ($$5 == 0) {
/* 588 */           return 0;
/*     */         }
/* 590 */         $$2.clear();
/* 591 */         if (!$$2.addTag(0, $$4)) {
/* 592 */           return 0;
/*     */         }
/* 594 */         for (int $$6 = 1; $$6 < $$3; $$6++) {
/* 595 */           $$2.addTag($$6, $$1.get());
/*     */         }
/*     */         
/* 598 */         return $$5; }
/*     */ 
/*     */       
/* 601 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public int removeTag(Tag $$0) {
/* 606 */       if ($$0 instanceof CollectionTag) { CollectionTag<?> $$1 = (CollectionTag)$$0;
/* 607 */         int $$2 = $$1.size();
/* 608 */         if ($$2 > 0) {
/* 609 */           $$1.clear();
/* 610 */           return $$2;
/*     */         }  }
/*     */ 
/*     */       
/* 614 */       return 0;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MatchObjectNode implements Node {
/*     */     private final String name;
/*     */     private final CompoundTag pattern;
/*     */     private final Predicate<Tag> predicate;
/*     */     
/*     */     public MatchObjectNode(String $$0, CompoundTag $$1) {
/* 624 */       this.name = $$0;
/* 625 */       this.pattern = $$1;
/* 626 */       this.predicate = NbtPathArgument.createTagPredicate($$1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void getTag(Tag $$0, List<Tag> $$1) {
/* 631 */       if ($$0 instanceof CompoundTag) {
/* 632 */         Tag $$2 = ((CompoundTag)$$0).get(this.name);
/* 633 */         if (this.predicate.test($$2)) {
/* 634 */           $$1.add($$2);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void getOrCreateTag(Tag $$0, Supplier<Tag> $$1, List<Tag> $$2) {
/* 641 */       if ($$0 instanceof CompoundTag) { CompoundTag compoundTag1, $$3 = (CompoundTag)$$0;
/* 642 */         Tag $$4 = $$3.get(this.name);
/* 643 */         if ($$4 == null) {
/* 644 */           compoundTag1 = this.pattern.copy();
/* 645 */           $$3.put(this.name, (Tag)compoundTag1);
/* 646 */           $$2.add(compoundTag1);
/* 647 */         } else if (this.predicate.test(compoundTag1)) {
/* 648 */           $$2.add(compoundTag1);
/*     */         }  }
/*     */     
/*     */     }
/*     */ 
/*     */     
/*     */     public Tag createPreferredParentTag() {
/* 655 */       return (Tag)new CompoundTag();
/*     */     }
/*     */ 
/*     */     
/*     */     public int setTag(Tag $$0, Supplier<Tag> $$1) {
/* 660 */       if ($$0 instanceof CompoundTag) { CompoundTag $$2 = (CompoundTag)$$0;
/* 661 */         Tag $$3 = $$2.get(this.name);
/* 662 */         if (this.predicate.test($$3)) {
/* 663 */           Tag $$4 = $$1.get();
/* 664 */           if (!$$4.equals($$3)) {
/* 665 */             $$2.put(this.name, $$4);
/* 666 */             return 1;
/*     */           } 
/*     */         }  }
/*     */ 
/*     */       
/* 671 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public int removeTag(Tag $$0) {
/* 676 */       if ($$0 instanceof CompoundTag) { CompoundTag $$1 = (CompoundTag)$$0;
/* 677 */         Tag $$2 = $$1.get(this.name);
/* 678 */         if (this.predicate.test($$2)) {
/* 679 */           $$1.remove(this.name);
/* 680 */           return 1;
/*     */         }  }
/*     */ 
/*     */       
/* 684 */       return 0;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MatchRootObjectNode implements Node {
/*     */     private final Predicate<Tag> predicate;
/*     */     
/*     */     public MatchRootObjectNode(CompoundTag $$0) {
/* 692 */       this.predicate = NbtPathArgument.createTagPredicate($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void getTag(Tag $$0, List<Tag> $$1) {
/* 697 */       if ($$0 instanceof CompoundTag && this.predicate.test($$0)) {
/* 698 */         $$1.add($$0);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void getOrCreateTag(Tag $$0, Supplier<Tag> $$1, List<Tag> $$2) {
/* 704 */       getTag($$0, $$2);
/*     */     }
/*     */ 
/*     */     
/*     */     public Tag createPreferredParentTag() {
/* 709 */       return (Tag)new CompoundTag();
/*     */     }
/*     */ 
/*     */     
/*     */     public int setTag(Tag $$0, Supplier<Tag> $$1) {
/* 714 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public int removeTag(Tag $$0) {
/* 719 */       return 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\NbtPathArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */