/*     */ package net.minecraft.network.protocol.game;
/*     */ 
/*     */ import com.google.common.collect.Queues;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.builder.ArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.suggestion.SuggestionProvider;
/*     */ import com.mojang.brigadier.tree.ArgumentCommandNode;
/*     */ import com.mojang.brigadier.tree.CommandNode;
/*     */ import com.mojang.brigadier.tree.LiteralCommandNode;
/*     */ import com.mojang.brigadier.tree.RootCommandNode;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSets;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMaps;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Queue;
/*     */ import java.util.function.BiPredicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.CommandBuildContext;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.synchronization.ArgumentTypeInfo;
/*     */ import net.minecraft.commands.synchronization.ArgumentTypeInfos;
/*     */ import net.minecraft.commands.synchronization.SuggestionProviders;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.PacketListener;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientboundCommandsPacket
/*     */   implements Packet<ClientGamePacketListener>
/*     */ {
/*     */   private static final byte MASK_TYPE = 3;
/*     */   private static final byte FLAG_EXECUTABLE = 4;
/*     */   private static final byte FLAG_REDIRECT = 8;
/*     */   private static final byte FLAG_CUSTOM_SUGGESTIONS = 16;
/*     */   
/*     */   public ClientboundCommandsPacket(RootCommandNode<SharedSuggestionProvider> $$0) {
/*  51 */     Object2IntMap<CommandNode<SharedSuggestionProvider>> $$1 = enumerateNodes($$0);
/*  52 */     this.entries = createEntries($$1);
/*  53 */     this.rootIndex = $$1.getInt($$0);
/*     */   }
/*     */   private static final byte TYPE_ROOT = 0; private static final byte TYPE_LITERAL = 1; private static final byte TYPE_ARGUMENT = 2; private final int rootIndex; private final List<Entry> entries;
/*     */   public ClientboundCommandsPacket(FriendlyByteBuf $$0) {
/*  57 */     this.entries = $$0.readList(ClientboundCommandsPacket::readNode);
/*  58 */     this.rootIndex = $$0.readVarInt();
/*  59 */     validateEntries(this.entries);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/*  64 */     $$0.writeCollection(this.entries, ($$0, $$1) -> $$1.write($$0));
/*  65 */     $$0.writeVarInt(this.rootIndex);
/*     */   }
/*     */   
/*     */   private static void validateEntries(List<Entry> $$0, BiPredicate<Entry, IntSet> $$1) {
/*  69 */     IntOpenHashSet intOpenHashSet = new IntOpenHashSet((IntCollection)IntSets.fromTo(0, $$0.size()));
/*  70 */     while (!intOpenHashSet.isEmpty()) {
/*  71 */       boolean $$3 = intOpenHashSet.removeIf($$3 -> $$0.test($$1.get($$3), $$2));
/*  72 */       if (!$$3) {
/*  73 */         throw new IllegalStateException("Server sent an impossible command tree");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void validateEntries(List<Entry> $$0) {
/*  79 */     validateEntries($$0, Entry::canBuild);
/*  80 */     validateEntries($$0, Entry::canResolve);
/*     */   }
/*     */   
/*     */   private static Object2IntMap<CommandNode<SharedSuggestionProvider>> enumerateNodes(RootCommandNode<SharedSuggestionProvider> $$0) {
/*  84 */     Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
/*  85 */     Queue<CommandNode<SharedSuggestionProvider>> $$2 = Queues.newArrayDeque();
/*  86 */     $$2.add($$0);
/*     */     
/*     */     CommandNode<SharedSuggestionProvider> $$3;
/*  89 */     while (($$3 = $$2.poll()) != null) {
/*  90 */       if (object2IntOpenHashMap.containsKey($$3)) {
/*     */         continue;
/*     */       }
/*  93 */       int $$4 = object2IntOpenHashMap.size();
/*  94 */       object2IntOpenHashMap.put($$3, $$4);
/*  95 */       $$2.addAll($$3.getChildren());
/*  96 */       if ($$3.getRedirect() != null) {
/*  97 */         $$2.add($$3.getRedirect());
/*     */       }
/*     */     } 
/* 100 */     return (Object2IntMap<CommandNode<SharedSuggestionProvider>>)object2IntOpenHashMap;
/*     */   }
/*     */   
/*     */   private static List<Entry> createEntries(Object2IntMap<CommandNode<SharedSuggestionProvider>> $$0) {
/* 104 */     ObjectArrayList<Entry> $$1 = new ObjectArrayList($$0.size());
/* 105 */     $$1.size($$0.size());
/* 106 */     for (ObjectIterator<Object2IntMap.Entry<CommandNode<SharedSuggestionProvider>>> objectIterator = Object2IntMaps.fastIterable($$0).iterator(); objectIterator.hasNext(); ) { Object2IntMap.Entry<CommandNode<SharedSuggestionProvider>> $$2 = objectIterator.next();
/* 107 */       $$1.set($$2.getIntValue(), createEntry((CommandNode<SharedSuggestionProvider>)$$2.getKey(), $$0)); }
/*     */     
/* 109 */     return (List<Entry>)$$1;
/*     */   }
/*     */   
/*     */   private static Entry readNode(FriendlyByteBuf $$0) {
/* 113 */     byte $$1 = $$0.readByte();
/* 114 */     int[] $$2 = $$0.readVarIntArray();
/* 115 */     int $$3 = (($$1 & 0x8) != 0) ? $$0.readVarInt() : 0;
/* 116 */     NodeStub $$4 = read($$0, $$1);
/* 117 */     return new Entry($$4, $$1, $$3, $$2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class LiteralNodeStub
/*     */     implements NodeStub
/*     */   {
/*     */     private final String id;
/*     */ 
/*     */ 
/*     */     
/*     */     LiteralNodeStub(String $$0) {
/* 130 */       this.id = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public ArgumentBuilder<SharedSuggestionProvider, ?> build(CommandBuildContext $$0) {
/* 135 */       return (ArgumentBuilder<SharedSuggestionProvider, ?>)LiteralArgumentBuilder.literal(this.id);
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/* 140 */       $$0.writeUtf(this.id);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ArgumentNodeStub implements NodeStub {
/*     */     private final String id;
/*     */     private final ArgumentTypeInfo.Template<?> argumentType;
/*     */     @Nullable
/*     */     private final ResourceLocation suggestionId;
/*     */     
/*     */     @Nullable
/*     */     private static ResourceLocation getSuggestionId(@Nullable SuggestionProvider<SharedSuggestionProvider> $$0) {
/* 152 */       return ($$0 != null) ? SuggestionProviders.getName($$0) : null;
/*     */     }
/*     */     
/*     */     ArgumentNodeStub(String $$0, ArgumentTypeInfo.Template<?> $$1, @Nullable ResourceLocation $$2) {
/* 156 */       this.id = $$0;
/* 157 */       this.argumentType = $$1;
/* 158 */       this.suggestionId = $$2;
/*     */     }
/*     */     
/*     */     public ArgumentNodeStub(ArgumentCommandNode<SharedSuggestionProvider, ?> $$0) {
/* 162 */       this($$0.getName(), ArgumentTypeInfos.unpack($$0.getType()), getSuggestionId($$0.getCustomSuggestions()));
/*     */     }
/*     */ 
/*     */     
/*     */     public ArgumentBuilder<SharedSuggestionProvider, ?> build(CommandBuildContext $$0) {
/* 167 */       ArgumentType<?> $$1 = this.argumentType.instantiate($$0);
/* 168 */       RequiredArgumentBuilder<SharedSuggestionProvider, ?> $$2 = RequiredArgumentBuilder.argument(this.id, $$1);
/* 169 */       if (this.suggestionId != null) {
/* 170 */         $$2.suggests(SuggestionProviders.getProvider(this.suggestionId));
/*     */       }
/* 172 */       return (ArgumentBuilder<SharedSuggestionProvider, ?>)$$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/* 177 */       $$0.writeUtf(this.id);
/* 178 */       serializeCap($$0, this.argumentType);
/* 179 */       if (this.suggestionId != null) {
/* 180 */         $$0.writeResourceLocation(this.suggestionId);
/*     */       }
/*     */     }
/*     */     
/*     */     private static <A extends ArgumentType<?>> void serializeCap(FriendlyByteBuf $$0, ArgumentTypeInfo.Template<A> $$1) {
/* 185 */       serializeCap($$0, $$1.type(), $$1);
/*     */     }
/*     */ 
/*     */     
/*     */     private static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>> void serializeCap(FriendlyByteBuf $$0, ArgumentTypeInfo<A, T> $$1, ArgumentTypeInfo.Template<A> $$2) {
/* 190 */       $$0.writeVarInt(BuiltInRegistries.COMMAND_ARGUMENT_TYPE.getId($$1));
/* 191 */       $$1.serializeToNetwork($$2, $$0);
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static NodeStub read(FriendlyByteBuf $$0, byte $$1) {
/* 197 */     int $$2 = $$1 & 0x3;
/* 198 */     if ($$2 == 2) {
/* 199 */       String $$3 = $$0.readUtf();
/* 200 */       int $$4 = $$0.readVarInt();
/* 201 */       ArgumentTypeInfo<?, ?> $$5 = (ArgumentTypeInfo<?, ?>)BuiltInRegistries.COMMAND_ARGUMENT_TYPE.byId($$4);
/* 202 */       if ($$5 == null) {
/* 203 */         return null;
/*     */       }
/* 205 */       ArgumentTypeInfo.Template<?> $$6 = $$5.deserializeFromNetwork($$0);
/* 206 */       ResourceLocation $$7 = (($$1 & 0x10) != 0) ? $$0.readResourceLocation() : null;
/* 207 */       return new ArgumentNodeStub($$3, $$6, $$7);
/*     */     } 
/* 209 */     if ($$2 == 1) {
/* 210 */       String $$8 = $$0.readUtf();
/* 211 */       return new LiteralNodeStub($$8);
/*     */     } 
/* 213 */     return null;
/*     */   }
/*     */   private static Entry createEntry(CommandNode<SharedSuggestionProvider> $$0, Object2IntMap<CommandNode<SharedSuggestionProvider>> $$1) {
/*     */     int $$4;
/*     */     NodeStub $$9;
/* 218 */     int $$2 = 0;
/*     */     
/* 220 */     if ($$0.getRedirect() != null) {
/* 221 */       $$2 |= 0x8;
/* 222 */       int $$3 = $$1.getInt($$0.getRedirect());
/*     */     } else {
/* 224 */       $$4 = 0;
/*     */     } 
/* 226 */     if ($$0.getCommand() != null) {
/* 227 */       $$2 |= 0x4;
/*     */     }
/*     */ 
/*     */     
/* 231 */     if ($$0 instanceof RootCommandNode)
/* 232 */     { $$2 |= 0x0;
/* 233 */       NodeStub $$5 = null; }
/* 234 */     else if ($$0 instanceof ArgumentCommandNode) { ArgumentCommandNode<SharedSuggestionProvider, ?> $$6 = (ArgumentCommandNode)$$0;
/* 235 */       NodeStub $$7 = new ArgumentNodeStub($$6);
/* 236 */       $$2 |= 0x2;
/* 237 */       if ($$6.getCustomSuggestions() != null) {
/* 238 */         $$2 |= 0x10;
/*     */       } }
/* 240 */     else if ($$0 instanceof LiteralCommandNode) { LiteralCommandNode $$8 = (LiteralCommandNode)$$0;
/* 241 */       $$9 = new LiteralNodeStub($$8.getLiteral());
/* 242 */       $$2 |= 0x1; }
/*     */     else
/* 244 */     { throw new UnsupportedOperationException("Unknown node type " + $$0); }
/*     */ 
/*     */     
/* 247 */     Objects.requireNonNull($$1); int[] $$11 = $$0.getChildren().stream().mapToInt($$1::getInt).toArray();
/*     */     
/* 249 */     return new Entry($$9, $$2, $$4, $$11);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handle(ClientGamePacketListener $$0) {
/* 254 */     $$0.handleCommands(this);
/*     */   }
/*     */   
/*     */   public RootCommandNode<SharedSuggestionProvider> getRoot(CommandBuildContext $$0) {
/* 258 */     return (RootCommandNode<SharedSuggestionProvider>)(new NodeResolver($$0, this.entries)).resolve(this.rootIndex);
/*     */   }
/*     */   
/*     */   private static class Entry {
/*     */     @Nullable
/*     */     final ClientboundCommandsPacket.NodeStub stub;
/*     */     final int flags;
/*     */     final int redirect;
/*     */     final int[] children;
/*     */     
/*     */     Entry(@Nullable ClientboundCommandsPacket.NodeStub $$0, int $$1, int $$2, int[] $$3) {
/* 269 */       this.stub = $$0;
/* 270 */       this.flags = $$1;
/* 271 */       this.redirect = $$2;
/* 272 */       this.children = $$3;
/*     */     }
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/* 276 */       $$0.writeByte(this.flags);
/* 277 */       $$0.writeVarIntArray(this.children);
/* 278 */       if ((this.flags & 0x8) != 0) {
/* 279 */         $$0.writeVarInt(this.redirect);
/*     */       }
/* 281 */       if (this.stub != null) {
/* 282 */         this.stub.write($$0);
/*     */       }
/*     */     }
/*     */     
/*     */     public boolean canBuild(IntSet $$0) {
/* 287 */       if ((this.flags & 0x8) != 0) {
/* 288 */         return !$$0.contains(this.redirect);
/*     */       }
/* 290 */       return true;
/*     */     }
/*     */     
/*     */     public boolean canResolve(IntSet $$0) {
/* 294 */       for (int $$1 : this.children) {
/* 295 */         if ($$0.contains($$1)) {
/* 296 */           return false;
/*     */         }
/*     */       } 
/* 299 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class NodeResolver {
/*     */     private final CommandBuildContext context;
/*     */     private final List<ClientboundCommandsPacket.Entry> entries;
/*     */     private final List<CommandNode<SharedSuggestionProvider>> nodes;
/*     */     
/*     */     NodeResolver(CommandBuildContext $$0, List<ClientboundCommandsPacket.Entry> $$1) {
/* 309 */       this.context = $$0;
/* 310 */       this.entries = $$1;
/* 311 */       ObjectArrayList<CommandNode<SharedSuggestionProvider>> $$2 = new ObjectArrayList();
/* 312 */       $$2.size($$1.size());
/* 313 */       this.nodes = (List<CommandNode<SharedSuggestionProvider>>)$$2;
/*     */     }
/*     */     
/*     */     public CommandNode<SharedSuggestionProvider> resolve(int $$0) {
/* 317 */       CommandNode<SharedSuggestionProvider> $$5, $$1 = this.nodes.get($$0);
/* 318 */       if ($$1 != null) {
/* 319 */         return $$1;
/*     */       }
/*     */       
/* 322 */       ClientboundCommandsPacket.Entry $$2 = this.entries.get($$0);
/*     */ 
/*     */       
/* 325 */       if ($$2.stub == null) {
/* 326 */         RootCommandNode rootCommandNode = new RootCommandNode();
/*     */       } else {
/* 328 */         ArgumentBuilder<SharedSuggestionProvider, ?> $$4 = $$2.stub.build(this.context);
/* 329 */         if (($$2.flags & 0x8) != 0) {
/* 330 */           $$4.redirect(resolve($$2.redirect));
/*     */         }
/* 332 */         if (($$2.flags & 0x4) != 0) {
/* 333 */           $$4.executes($$0 -> 0);
/*     */         }
/* 335 */         $$5 = $$4.build();
/*     */       } 
/* 337 */       this.nodes.set($$0, $$5);
/*     */       
/* 339 */       for (int $$6 : $$2.children) {
/* 340 */         CommandNode<SharedSuggestionProvider> $$7 = resolve($$6);
/* 341 */         if (!($$7 instanceof RootCommandNode)) {
/* 342 */           $$5.addChild($$7);
/*     */         }
/*     */       } 
/* 345 */       return $$5;
/*     */     }
/*     */   }
/*     */   
/*     */   private static interface NodeStub {
/*     */     ArgumentBuilder<SharedSuggestionProvider, ?> build(CommandBuildContext param1CommandBuildContext);
/*     */     
/*     */     void write(FriendlyByteBuf param1FriendlyByteBuf);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundCommandsPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */