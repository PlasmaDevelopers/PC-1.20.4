/*    */ package net.minecraft.world.ticks;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.nbt.ListTag;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ 
/*    */ public class ProtoChunkTicks<T> implements SerializableTickContainer<T>, TickContainerAccess<T> {
/* 16 */   private final List<SavedTick<T>> ticks = Lists.newArrayList();
/*    */ 
/*    */   
/* 19 */   private final Set<SavedTick<?>> ticksPerPosition = (Set<SavedTick<?>>)new ObjectOpenCustomHashSet(SavedTick.UNIQUE_TICK_HASH);
/*    */ 
/*    */ 
/*    */   
/*    */   public void schedule(ScheduledTick<T> $$0) {
/* 24 */     SavedTick<T> $$1 = new SavedTick<>($$0.type(), $$0.pos(), 0, $$0.priority());
/* 25 */     schedule($$1);
/*    */   }
/*    */   
/*    */   private void schedule(SavedTick<T> $$0) {
/* 29 */     if (this.ticksPerPosition.add($$0)) {
/* 30 */       this.ticks.add($$0);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasScheduledTick(BlockPos $$0, T $$1) {
/* 36 */     return this.ticksPerPosition.contains(SavedTick.probe($$1, $$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public int count() {
/* 41 */     return this.ticks.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public Tag save(long $$0, Function<T, String> $$1) {
/* 46 */     ListTag $$2 = new ListTag();
/* 47 */     for (SavedTick<T> $$3 : this.ticks) {
/* 48 */       $$2.add($$3.save($$1));
/*    */     }
/* 50 */     return (Tag)$$2;
/*    */   }
/*    */   
/*    */   public List<SavedTick<T>> scheduledTicks() {
/* 54 */     return List.copyOf(this.ticks);
/*    */   }
/*    */   
/*    */   public static <T> ProtoChunkTicks<T> load(ListTag $$0, Function<String, Optional<T>> $$1, ChunkPos $$2) {
/* 58 */     ProtoChunkTicks<T> $$3 = new ProtoChunkTicks<>();
/* 59 */     Objects.requireNonNull($$3); SavedTick.loadTickList($$0, $$1, $$2, $$3::schedule);
/* 60 */     return $$3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\ticks\ProtoChunkTicks.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */