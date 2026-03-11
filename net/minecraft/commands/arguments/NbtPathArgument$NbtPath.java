/*     */ package net.minecraft.commands.arguments;
/*     */ 
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.nbt.CollectionTag;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import org.apache.commons.lang3.mutable.MutableBoolean;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NbtPath
/*     */ {
/*     */   private final String original;
/*     */   private final Object2IntMap<NbtPathArgument.Node> nodeToOriginalPosition;
/*     */   private final NbtPathArgument.Node[] nodes;
/*     */   
/*     */   public NbtPath(String $$0, NbtPathArgument.Node[] $$1, Object2IntMap<NbtPathArgument.Node> $$2) {
/* 142 */     this.original = $$0;
/* 143 */     this.nodes = $$1;
/* 144 */     this.nodeToOriginalPosition = $$2;
/*     */   }
/*     */   
/*     */   public List<Tag> get(Tag $$0) throws CommandSyntaxException {
/* 148 */     List<Tag> $$1 = Collections.singletonList($$0);
/* 149 */     for (NbtPathArgument.Node $$2 : this.nodes) {
/* 150 */       $$1 = $$2.get($$1);
/* 151 */       if ($$1.isEmpty()) {
/* 152 */         throw createNotFoundException($$2);
/*     */       }
/*     */     } 
/* 155 */     return $$1;
/*     */   }
/*     */   
/*     */   public int countMatching(Tag $$0) {
/* 159 */     List<Tag> $$1 = Collections.singletonList($$0);
/* 160 */     for (NbtPathArgument.Node $$2 : this.nodes) {
/* 161 */       $$1 = $$2.get($$1);
/* 162 */       if ($$1.isEmpty()) {
/* 163 */         return 0;
/*     */       }
/*     */     } 
/* 166 */     return $$1.size();
/*     */   }
/*     */   
/*     */   private List<Tag> getOrCreateParents(Tag $$0) throws CommandSyntaxException {
/* 170 */     List<Tag> $$1 = Collections.singletonList($$0);
/*     */     
/* 172 */     for (int $$2 = 0; $$2 < this.nodes.length - 1; $$2++) {
/* 173 */       NbtPathArgument.Node $$3 = this.nodes[$$2];
/* 174 */       int $$4 = $$2 + 1;
/* 175 */       Objects.requireNonNull(this.nodes[$$4]); $$1 = $$3.getOrCreate($$1, this.nodes[$$4]::createPreferredParentTag);
/* 176 */       if ($$1.isEmpty()) {
/* 177 */         throw createNotFoundException($$3);
/*     */       }
/*     */     } 
/* 180 */     return $$1;
/*     */   }
/*     */   
/*     */   public List<Tag> getOrCreate(Tag $$0, Supplier<Tag> $$1) throws CommandSyntaxException {
/* 184 */     List<Tag> $$2 = getOrCreateParents($$0);
/*     */     
/* 186 */     NbtPathArgument.Node $$3 = this.nodes[this.nodes.length - 1];
/* 187 */     return $$3.getOrCreate($$2, $$1);
/*     */   }
/*     */   
/*     */   private static int apply(List<Tag> $$0, Function<Tag, Integer> $$1) {
/* 191 */     return ((Integer)$$0.stream().<Integer>map($$1).reduce(Integer.valueOf(0), ($$0, $$1) -> Integer.valueOf($$0.intValue() + $$1.intValue()))).intValue();
/*     */   }
/*     */   
/*     */   public static boolean isTooDeep(Tag $$0, int $$1) {
/* 195 */     if ($$1 >= 512) {
/* 196 */       return true;
/*     */     }
/* 198 */     if ($$0 instanceof CompoundTag) { CompoundTag $$2 = (CompoundTag)$$0;
/* 199 */       for (String $$3 : $$2.getAllKeys()) {
/* 200 */         Tag $$4 = $$2.get($$3);
/* 201 */         if ($$4 != null && 
/* 202 */           isTooDeep($$4, $$1 + 1)) {
/* 203 */           return true;
/*     */         }
/*     */       }
/*     */        }
/* 207 */     else if ($$0 instanceof ListTag) { ListTag $$5 = (ListTag)$$0;
/* 208 */       for (Tag $$6 : $$5) {
/* 209 */         if (isTooDeep($$6, $$1 + 1)) {
/* 210 */           return true;
/*     */         }
/*     */       }  }
/*     */     
/* 214 */     return false;
/*     */   }
/*     */   
/*     */   public int set(Tag $$0, Tag $$1) throws CommandSyntaxException {
/* 218 */     if (isTooDeep($$1, estimatePathDepth())) {
/* 219 */       throw NbtPathArgument.ERROR_DATA_TOO_DEEP.create();
/*     */     }
/* 221 */     Tag $$2 = $$1.copy();
/* 222 */     List<Tag> $$3 = getOrCreateParents($$0);
/* 223 */     if ($$3.isEmpty()) {
/* 224 */       return 0;
/*     */     }
/*     */     
/* 227 */     NbtPathArgument.Node $$4 = this.nodes[this.nodes.length - 1];
/* 228 */     MutableBoolean $$5 = new MutableBoolean(false);
/* 229 */     return apply($$3, $$3 -> Integer.valueOf($$0.setTag($$3, ())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int estimatePathDepth() {
/* 240 */     return this.nodes.length;
/*     */   }
/*     */   
/*     */   public int insert(int $$0, CompoundTag $$1, List<Tag> $$2) throws CommandSyntaxException {
/* 244 */     List<Tag> $$3 = new ArrayList<>($$2.size());
/* 245 */     for (Tag $$4 : $$2) {
/* 246 */       Tag $$5 = $$4.copy();
/* 247 */       $$3.add($$5);
/* 248 */       if (isTooDeep($$5, estimatePathDepth())) {
/* 249 */         throw NbtPathArgument.ERROR_DATA_TOO_DEEP.create();
/*     */       }
/*     */     } 
/* 252 */     Collection<Tag> $$6 = getOrCreate((Tag)$$1, ListTag::new);
/*     */     
/* 254 */     int $$7 = 0;
/* 255 */     boolean $$8 = false;
/* 256 */     for (Tag $$9 : $$6) {
/* 257 */       if (!($$9 instanceof CollectionTag)) {
/* 258 */         throw NbtPathArgument.ERROR_EXPECTED_LIST.create($$9);
/*     */       }
/* 260 */       CollectionTag<?> $$10 = (CollectionTag)$$9;
/*     */       
/* 262 */       boolean $$11 = false;
/* 263 */       int $$12 = ($$0 < 0) ? ($$10.size() + $$0 + 1) : $$0;
/* 264 */       for (Tag $$13 : $$3) {
/*     */         try {
/* 266 */           if ($$10.addTag($$12, $$8 ? $$13.copy() : $$13)) {
/* 267 */             $$12++;
/* 268 */             $$11 = true;
/*     */           } 
/* 270 */         } catch (IndexOutOfBoundsException $$14) {
/* 271 */           throw NbtPathArgument.ERROR_INVALID_INDEX.create(Integer.valueOf($$12));
/*     */         } 
/*     */       } 
/* 274 */       $$8 = true;
/* 275 */       $$7 += $$11 ? 1 : 0;
/*     */     } 
/*     */     
/* 278 */     return $$7;
/*     */   }
/*     */   
/*     */   public int remove(Tag $$0) {
/* 282 */     List<Tag> $$1 = Collections.singletonList($$0);
/*     */     
/* 284 */     for (int $$2 = 0; $$2 < this.nodes.length - 1; $$2++) {
/* 285 */       $$1 = this.nodes[$$2].get($$1);
/*     */     }
/*     */     
/* 288 */     NbtPathArgument.Node $$3 = this.nodes[this.nodes.length - 1];
/* 289 */     Objects.requireNonNull($$3); return apply($$1, $$3::removeTag);
/*     */   }
/*     */   
/*     */   private CommandSyntaxException createNotFoundException(NbtPathArgument.Node $$0) {
/* 293 */     int $$1 = this.nodeToOriginalPosition.getInt($$0);
/* 294 */     return NbtPathArgument.ERROR_NOTHING_FOUND.create(this.original.substring(0, $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 299 */     return this.original;
/*     */   }
/*     */   
/*     */   public String asString() {
/* 303 */     return this.original;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\NbtPathArgument$NbtPath.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */