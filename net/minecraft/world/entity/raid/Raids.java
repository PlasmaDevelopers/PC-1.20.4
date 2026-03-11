/*     */ package net.minecraft.world.entity.raid;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
/*     */ import net.minecraft.network.protocol.game.DebugPackets;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.tags.PoiTypeTags;
/*     */ import net.minecraft.util.datafix.DataFixTypes;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiManager;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiRecord;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ import net.minecraft.world.level.saveddata.SavedData;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class Raids extends SavedData {
/*     */   private static final String RAID_FILE_ID = "raids";
/*  37 */   private final Map<Integer, Raid> raidMap = Maps.newHashMap();
/*     */   
/*     */   private final ServerLevel level;
/*     */   
/*     */   private int nextAvailableID;
/*     */   private int tick;
/*     */   
/*     */   public static SavedData.Factory<Raids> factory(ServerLevel $$0) {
/*  45 */     return new SavedData.Factory(() -> new Raids($$0), $$1 -> load($$0, $$1), DataFixTypes.SAVED_DATA_RAIDS);
/*     */   }
/*     */   
/*     */   public Raids(ServerLevel $$0) {
/*  49 */     this.level = $$0;
/*  50 */     this.nextAvailableID = 1;
/*  51 */     setDirty();
/*     */   }
/*     */   
/*     */   public Raid get(int $$0) {
/*  55 */     return this.raidMap.get(Integer.valueOf($$0));
/*     */   }
/*     */   
/*     */   public void tick() {
/*  59 */     this.tick++;
/*  60 */     Iterator<Raid> $$0 = this.raidMap.values().iterator();
/*     */     
/*  62 */     while ($$0.hasNext()) {
/*  63 */       Raid $$1 = $$0.next();
/*  64 */       if (this.level.getGameRules().getBoolean(GameRules.RULE_DISABLE_RAIDS)) {
/*  65 */         $$1.stop();
/*     */       }
/*  67 */       if ($$1.isStopped()) {
/*  68 */         $$0.remove();
/*  69 */         setDirty();
/*     */         
/*     */         continue;
/*     */       } 
/*  73 */       $$1.tick();
/*     */     } 
/*     */ 
/*     */     
/*  77 */     if (this.tick % 200 == 0) {
/*  78 */       setDirty();
/*     */     }
/*     */     
/*  81 */     DebugPackets.sendRaids(this.level, this.raidMap.values());
/*     */   }
/*     */   
/*     */   public static boolean canJoinRaid(Raider $$0, Raid $$1) {
/*  85 */     if ($$0 != null && $$1 != null && $$1.getLevel() != null) {
/*  86 */       return ($$0.isAlive() && $$0.canJoinRaid() && $$0.getNoActionTime() <= 2400 && $$0.level().dimensionType() == $$1.getLevel().dimensionType());
/*     */     }
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Raid createOrExtendRaid(ServerPlayer $$0) {
/*     */     BlockPos $$9;
/*  97 */     if ($$0.isSpectator()) {
/*  98 */       return null;
/*     */     }
/*     */     
/* 101 */     if (this.level.getGameRules().getBoolean(GameRules.RULE_DISABLE_RAIDS)) {
/* 102 */       return null;
/*     */     }
/*     */     
/* 105 */     DimensionType $$1 = $$0.level().dimensionType();
/* 106 */     if (!$$1.hasRaids()) {
/* 107 */       return null;
/*     */     }
/*     */     
/* 110 */     BlockPos $$2 = $$0.blockPosition();
/*     */ 
/*     */ 
/*     */     
/* 114 */     List<PoiRecord> $$3 = this.level.getPoiManager().getInRange($$0 -> $$0.is(PoiTypeTags.VILLAGE), $$2, 64, PoiManager.Occupancy.IS_OCCUPIED).toList();
/* 115 */     int $$4 = 0;
/* 116 */     Vec3 $$5 = Vec3.ZERO;
/* 117 */     for (PoiRecord $$6 : $$3) {
/* 118 */       BlockPos $$7 = $$6.getPos();
/* 119 */       $$5 = $$5.add($$7.getX(), $$7.getY(), $$7.getZ());
/* 120 */       $$4++;
/*     */     } 
/*     */     
/* 123 */     if ($$4 > 0) {
/*     */       
/* 125 */       $$5 = $$5.scale(1.0D / $$4);
/* 126 */       BlockPos $$8 = BlockPos.containing((Position)$$5);
/*     */     } else {
/*     */       
/* 129 */       $$9 = $$2;
/*     */     } 
/*     */     
/* 132 */     Raid $$10 = getOrCreateRaid($$0.serverLevel(), $$9);
/*     */     
/* 134 */     boolean $$11 = false;
/* 135 */     if (!$$10.isStarted()) {
/* 136 */       if (!this.raidMap.containsKey(Integer.valueOf($$10.getId()))) {
/* 137 */         this.raidMap.put(Integer.valueOf($$10.getId()), $$10);
/*     */       }
/* 139 */       $$11 = true;
/*     */     }
/* 141 */     else if ($$10.getBadOmenLevel() < $$10.getMaxBadOmenLevel()) {
/* 142 */       $$11 = true;
/*     */     } else {
/*     */       
/* 145 */       $$0.removeEffect(MobEffects.BAD_OMEN);
/* 146 */       $$0.connection.send((Packet)new ClientboundEntityEventPacket((Entity)$$0, (byte)43));
/*     */     } 
/*     */ 
/*     */     
/* 150 */     if ($$11) {
/* 151 */       $$10.absorbBadOmen((Player)$$0);
/* 152 */       $$0.connection.send((Packet)new ClientboundEntityEventPacket((Entity)$$0, (byte)43));
/*     */       
/* 154 */       if (!$$10.hasFirstWaveSpawned()) {
/* 155 */         $$0.awardStat(Stats.RAID_TRIGGER);
/* 156 */         CriteriaTriggers.BAD_OMEN.trigger($$0);
/*     */       } 
/*     */     } 
/*     */     
/* 160 */     setDirty();
/*     */     
/* 162 */     return $$10;
/*     */   }
/*     */   
/*     */   private Raid getOrCreateRaid(ServerLevel $$0, BlockPos $$1) {
/* 166 */     Raid $$2 = $$0.getRaidAt($$1);
/* 167 */     return ($$2 != null) ? $$2 : new Raid(getUniqueId(), $$0, $$1);
/*     */   }
/*     */   
/*     */   public static Raids load(ServerLevel $$0, CompoundTag $$1) {
/* 171 */     Raids $$2 = new Raids($$0);
/* 172 */     $$2.nextAvailableID = $$1.getInt("NextAvailableID");
/* 173 */     $$2.tick = $$1.getInt("Tick");
/*     */     
/* 175 */     ListTag $$3 = $$1.getList("Raids", 10);
/* 176 */     for (int $$4 = 0; $$4 < $$3.size(); $$4++) {
/* 177 */       CompoundTag $$5 = $$3.getCompound($$4);
/* 178 */       Raid $$6 = new Raid($$0, $$5);
/* 179 */       $$2.raidMap.put(Integer.valueOf($$6.getId()), $$6);
/*     */     } 
/* 181 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag save(CompoundTag $$0) {
/* 186 */     $$0.putInt("NextAvailableID", this.nextAvailableID);
/* 187 */     $$0.putInt("Tick", this.tick);
/*     */     
/* 189 */     ListTag $$1 = new ListTag();
/* 190 */     for (Raid $$2 : this.raidMap.values()) {
/* 191 */       CompoundTag $$3 = new CompoundTag();
/* 192 */       $$2.save($$3);
/* 193 */       $$1.add($$3);
/*     */     } 
/* 195 */     $$0.put("Raids", (Tag)$$1);
/* 196 */     return $$0;
/*     */   }
/*     */   
/*     */   public static String getFileId(Holder<DimensionType> $$0) {
/* 200 */     if ($$0.is(BuiltinDimensionTypes.END)) {
/* 201 */       return "raids_end";
/*     */     }
/* 203 */     return "raids";
/*     */   }
/*     */   
/*     */   private int getUniqueId() {
/* 207 */     return ++this.nextAvailableID;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Raid getNearbyRaid(BlockPos $$0, int $$1) {
/* 212 */     Raid $$2 = null;
/* 213 */     double $$3 = $$1;
/* 214 */     for (Raid $$4 : this.raidMap.values()) {
/* 215 */       double $$5 = $$4.getCenter().distSqr((Vec3i)$$0);
/* 216 */       if (!$$4.isActive()) {
/*     */         continue;
/*     */       }
/* 219 */       if ($$5 < $$3) {
/* 220 */         $$2 = $$4;
/* 221 */         $$3 = $$5;
/*     */       } 
/*     */     } 
/* 224 */     return $$2;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\raid\Raids.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */