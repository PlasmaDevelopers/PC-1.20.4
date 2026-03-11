/*     */ package net.minecraft.server.bossevents;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collection;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.chat.HoverEvent;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerBossEvent;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.BossEvent;
/*     */ 
/*     */ public class CustomBossEvent extends ServerBossEvent {
/*  22 */   private final Set<UUID> players = Sets.newHashSet(); private final ResourceLocation id;
/*     */   private int value;
/*  24 */   private int max = 100;
/*     */   
/*     */   public CustomBossEvent(ResourceLocation $$0, Component $$1) {
/*  27 */     super($$1, BossEvent.BossBarColor.WHITE, BossEvent.BossBarOverlay.PROGRESS);
/*  28 */     this.id = $$0;
/*  29 */     setProgress(0.0F);
/*     */   }
/*     */   
/*     */   public ResourceLocation getTextId() {
/*  33 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addPlayer(ServerPlayer $$0) {
/*  38 */     super.addPlayer($$0);
/*  39 */     this.players.add($$0.getUUID());
/*     */   }
/*     */   
/*     */   public void addOfflinePlayer(UUID $$0) {
/*  43 */     this.players.add($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removePlayer(ServerPlayer $$0) {
/*  48 */     super.removePlayer($$0);
/*  49 */     this.players.remove($$0.getUUID());
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAllPlayers() {
/*  54 */     super.removeAllPlayers();
/*  55 */     this.players.clear();
/*     */   }
/*     */   
/*     */   public int getValue() {
/*  59 */     return this.value;
/*     */   }
/*     */   
/*     */   public int getMax() {
/*  63 */     return this.max;
/*     */   }
/*     */   
/*     */   public void setValue(int $$0) {
/*  67 */     this.value = $$0;
/*  68 */     setProgress(Mth.clamp($$0 / this.max, 0.0F, 1.0F));
/*     */   }
/*     */   
/*     */   public void setMax(int $$0) {
/*  72 */     this.max = $$0;
/*  73 */     setProgress(Mth.clamp(this.value / $$0, 0.0F, 1.0F));
/*     */   }
/*     */   
/*     */   public final Component getDisplayName() {
/*  77 */     return (Component)ComponentUtils.wrapInSquareBrackets(getName()).withStyle($$0 -> $$0.withColor(getColor().getFormatting()).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal(getTextId().toString()))).withInsertion(getTextId().toString()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setPlayers(Collection<ServerPlayer> $$0) {
/*  85 */     Set<UUID> $$1 = Sets.newHashSet();
/*  86 */     Set<ServerPlayer> $$2 = Sets.newHashSet();
/*     */     
/*  88 */     for (UUID $$3 : this.players) {
/*  89 */       boolean $$4 = false;
/*  90 */       for (ServerPlayer $$5 : $$0) {
/*  91 */         if ($$5.getUUID().equals($$3)) {
/*  92 */           $$4 = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*  96 */       if (!$$4) {
/*  97 */         $$1.add($$3);
/*     */       }
/*     */     } 
/*     */     
/* 101 */     for (ServerPlayer $$6 : $$0) {
/* 102 */       boolean $$7 = false;
/* 103 */       for (UUID $$8 : this.players) {
/* 104 */         if ($$6.getUUID().equals($$8)) {
/* 105 */           $$7 = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 109 */       if (!$$7) {
/* 110 */         $$2.add($$6);
/*     */       }
/*     */     } 
/*     */     
/* 114 */     for (UUID $$9 : $$1) {
/* 115 */       for (ServerPlayer $$10 : getPlayers()) {
/* 116 */         if ($$10.getUUID().equals($$9)) {
/* 117 */           removePlayer($$10);
/*     */           break;
/*     */         } 
/*     */       } 
/* 121 */       this.players.remove($$9);
/*     */     } 
/*     */     
/* 124 */     for (ServerPlayer $$11 : $$2) {
/* 125 */       addPlayer($$11);
/*     */     }
/*     */     
/* 128 */     return (!$$1.isEmpty() || !$$2.isEmpty());
/*     */   }
/*     */   
/*     */   public CompoundTag save() {
/* 132 */     CompoundTag $$0 = new CompoundTag();
/*     */     
/* 134 */     $$0.putString("Name", Component.Serializer.toJson(this.name));
/* 135 */     $$0.putBoolean("Visible", isVisible());
/* 136 */     $$0.putInt("Value", this.value);
/* 137 */     $$0.putInt("Max", this.max);
/* 138 */     $$0.putString("Color", getColor().getName());
/* 139 */     $$0.putString("Overlay", getOverlay().getName());
/* 140 */     $$0.putBoolean("DarkenScreen", shouldDarkenScreen());
/* 141 */     $$0.putBoolean("PlayBossMusic", shouldPlayBossMusic());
/* 142 */     $$0.putBoolean("CreateWorldFog", shouldCreateWorldFog());
/*     */     
/* 144 */     ListTag $$1 = new ListTag();
/* 145 */     for (UUID $$2 : this.players) {
/* 146 */       $$1.add(NbtUtils.createUUID($$2));
/*     */     }
/* 148 */     $$0.put("Players", (Tag)$$1);
/*     */     
/* 150 */     return $$0;
/*     */   }
/*     */   
/*     */   public static CustomBossEvent load(CompoundTag $$0, ResourceLocation $$1) {
/* 154 */     CustomBossEvent $$2 = new CustomBossEvent($$1, (Component)Component.Serializer.fromJson($$0.getString("Name")));
/* 155 */     $$2.setVisible($$0.getBoolean("Visible"));
/* 156 */     $$2.setValue($$0.getInt("Value"));
/* 157 */     $$2.setMax($$0.getInt("Max"));
/* 158 */     $$2.setColor(BossEvent.BossBarColor.byName($$0.getString("Color")));
/* 159 */     $$2.setOverlay(BossEvent.BossBarOverlay.byName($$0.getString("Overlay")));
/* 160 */     $$2.setDarkenScreen($$0.getBoolean("DarkenScreen"));
/* 161 */     $$2.setPlayBossMusic($$0.getBoolean("PlayBossMusic"));
/* 162 */     $$2.setCreateWorldFog($$0.getBoolean("CreateWorldFog"));
/*     */     
/* 164 */     ListTag $$3 = $$0.getList("Players", 11);
/* 165 */     for (Tag $$4 : $$3) {
/* 166 */       $$2.addOfflinePlayer(NbtUtils.loadUUID($$4));
/*     */     }
/*     */     
/* 169 */     return $$2;
/*     */   }
/*     */   
/*     */   public void onPlayerConnect(ServerPlayer $$0) {
/* 173 */     if (this.players.contains($$0.getUUID())) {
/* 174 */       addPlayer($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onPlayerDisconnect(ServerPlayer $$0) {
/* 179 */     super.removePlayer($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\bossevents\CustomBossEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */