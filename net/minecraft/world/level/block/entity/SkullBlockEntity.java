/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.google.common.cache.CacheLoader;
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.yggdrasil.ProfileResult;
/*     */ import java.time.Duration;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.Services;
/*     */ import net.minecraft.util.StringUtil;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.SkullBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public class SkullBlockEntity extends BlockEntity {
/*     */   public static final String TAG_SKULL_OWNER = "SkullOwner";
/*     */   public static final String TAG_NOTE_BLOCK_SOUND = "note_block_sound";
/*     */   @Nullable
/*     */   private static Executor mainThreadExecutor;
/*     */   @Nullable
/*     */   private static LoadingCache<String, CompletableFuture<Optional<GameProfile>>> profileCache;
/*     */   
/*     */   static {
/*  40 */     CHECKED_MAIN_THREAD_EXECUTOR = ($$0 -> {
/*     */         Executor $$1 = mainThreadExecutor;
/*     */         if ($$1 != null)
/*     */           $$1.execute($$0); 
/*     */       });
/*     */   }
/*     */   private static final Executor CHECKED_MAIN_THREAD_EXECUTOR;
/*     */   @Nullable
/*     */   private GameProfile owner;
/*     */   @Nullable
/*     */   private ResourceLocation noteBlockSound;
/*     */   private int animationTickCount;
/*     */   private boolean isAnimating;
/*     */   
/*     */   public SkullBlockEntity(BlockPos $$0, BlockState $$1) {
/*  55 */     super(BlockEntityType.SKULL, $$0, $$1);
/*     */   }
/*     */   
/*     */   public static void setup(final Services services, Executor $$1) {
/*  59 */     mainThreadExecutor = $$1;
/*     */ 
/*     */     
/*  62 */     final BooleanSupplier invalidated = () -> (profileCache == null);
/*     */ 
/*     */ 
/*     */     
/*  66 */     profileCache = CacheBuilder.newBuilder().expireAfterAccess(Duration.ofMinutes(10L)).maximumSize(256L).build(new CacheLoader<String, CompletableFuture<Optional<GameProfile>>>()
/*     */         {
/*     */           public CompletableFuture<Optional<GameProfile>> load(String $$0) {
/*  69 */             if (invalidated.getAsBoolean()) {
/*  70 */               return CompletableFuture.completedFuture(Optional.empty());
/*     */             }
/*  72 */             return SkullBlockEntity.loadProfile($$0, services, invalidated);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static void clear() {
/*  78 */     mainThreadExecutor = null;
/*  79 */     profileCache = null;
/*     */   }
/*     */   
/*     */   static CompletableFuture<Optional<GameProfile>> loadProfile(String $$0, Services $$1, BooleanSupplier $$2) {
/*  83 */     return $$1.profileCache().getAsync($$0)
/*  84 */       .thenApplyAsync($$2 -> {
/*     */           if ($$2.isPresent() && !$$0.getAsBoolean()) {
/*     */             UUID $$3 = ((GameProfile)$$2.get()).getId();
/*     */             
/*     */             ProfileResult $$4 = $$1.sessionService().fetchProfile($$3, true);
/*     */             
/*     */             return ($$4 != null) ? Optional.ofNullable($$4.profile()) : $$2;
/*     */           } 
/*     */           
/*     */           return Optional.empty();
/*  94 */         }Util.backgroundExecutor());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/*  99 */     super.saveAdditional($$0);
/*     */     
/* 101 */     if (this.owner != null) {
/* 102 */       CompoundTag $$1 = new CompoundTag();
/* 103 */       NbtUtils.writeGameProfile($$1, this.owner);
/* 104 */       $$0.put("SkullOwner", (Tag)$$1);
/*     */     } 
/* 106 */     if (this.noteBlockSound != null) {
/* 107 */       $$0.putString("note_block_sound", this.noteBlockSound.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/* 113 */     super.load($$0);
/*     */     
/* 115 */     if ($$0.contains("SkullOwner", 10)) {
/* 116 */       setOwner(NbtUtils.readGameProfile($$0.getCompound("SkullOwner")));
/* 117 */     } else if ($$0.contains("ExtraType", 8)) {
/* 118 */       String $$1 = $$0.getString("ExtraType");
/* 119 */       if (!StringUtil.isNullOrEmpty($$1)) {
/* 120 */         setOwner(new GameProfile(Util.NIL_UUID, $$1));
/*     */       }
/*     */     } 
/* 123 */     if ($$0.contains("note_block_sound", 8)) {
/* 124 */       this.noteBlockSound = ResourceLocation.tryParse($$0.getString("note_block_sound"));
/*     */     }
/*     */   }
/*     */   
/*     */   public static void animation(Level $$0, BlockPos $$1, BlockState $$2, SkullBlockEntity $$3) {
/* 129 */     if ($$2.hasProperty((Property)SkullBlock.POWERED) && ((Boolean)$$2.getValue((Property)SkullBlock.POWERED)).booleanValue()) {
/* 130 */       $$3.isAnimating = true;
/* 131 */       $$3.animationTickCount++;
/*     */     } else {
/* 133 */       $$3.isAnimating = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getAnimation(float $$0) {
/* 138 */     if (this.isAnimating) {
/* 139 */       return this.animationTickCount + $$0;
/*     */     }
/* 141 */     return this.animationTickCount;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public GameProfile getOwnerProfile() {
/* 146 */     return this.owner;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ResourceLocation getNoteBlockSound() {
/* 151 */     return this.noteBlockSound;
/*     */   }
/*     */ 
/*     */   
/*     */   public ClientboundBlockEntityDataPacket getUpdatePacket() {
/* 156 */     return ClientboundBlockEntityDataPacket.create(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag getUpdateTag() {
/* 161 */     return saveWithoutMetadata();
/*     */   }
/*     */   
/*     */   public void setOwner(@Nullable GameProfile $$0) {
/* 165 */     synchronized (this) {
/* 166 */       this.owner = $$0;
/*     */     } 
/* 168 */     updateOwnerProfile();
/*     */   }
/*     */   
/*     */   private void updateOwnerProfile() {
/* 172 */     if (this.owner == null || Util.isBlank(this.owner.getName()) || hasTextures(this.owner)) {
/* 173 */       setChanged();
/*     */       return;
/*     */     } 
/* 176 */     fetchGameProfile(this.owner.getName()).thenAcceptAsync($$0 -> { this.owner = $$0.orElse(this.owner); setChanged(); }CHECKED_MAIN_THREAD_EXECUTOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static GameProfile getOrResolveGameProfile(CompoundTag $$0) {
/* 184 */     if ($$0.contains("SkullOwner", 10))
/* 185 */       return NbtUtils.readGameProfile($$0.getCompound("SkullOwner")); 
/* 186 */     if ($$0.contains("SkullOwner", 8)) {
/* 187 */       String $$1 = $$0.getString("SkullOwner");
/* 188 */       if (!Util.isBlank($$1)) {
/* 189 */         $$0.remove("SkullOwner");
/* 190 */         resolveGameProfile($$0, $$1);
/*     */       } 
/*     */     } 
/* 193 */     return null;
/*     */   }
/*     */   
/*     */   public static void resolveGameProfile(CompoundTag $$0) {
/* 197 */     String $$1 = $$0.getString("SkullOwner");
/* 198 */     if (!Util.isBlank($$1)) {
/* 199 */       resolveGameProfile($$0, $$1);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void resolveGameProfile(CompoundTag $$0, String $$1) {
/* 204 */     fetchGameProfile($$1).thenAccept($$2 -> $$0.put("SkullOwner", (Tag)NbtUtils.writeGameProfile(new CompoundTag(), $$2.orElse(new GameProfile(Util.NIL_UUID, $$1)))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static CompletableFuture<Optional<GameProfile>> fetchGameProfile(String $$0) {
/* 210 */     LoadingCache<String, CompletableFuture<Optional<GameProfile>>> $$1 = profileCache;
/* 211 */     if ($$1 != null && Player.isValidUsername($$0)) {
/* 212 */       return (CompletableFuture<Optional<GameProfile>>)$$1.getUnchecked($$0);
/*     */     }
/* 214 */     return CompletableFuture.completedFuture(Optional.empty());
/*     */   }
/*     */   
/*     */   private static boolean hasTextures(GameProfile $$0) {
/* 218 */     return $$0.getProperties().containsKey("textures");
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\SkullBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */