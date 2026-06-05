package icu.gensoukyo.neo_mystias_izakaya.compat.tlm;

import com.github.tartaricacid.touhoulittlemaid.entity.ai.brain.task.MaidCheckRateTask;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitBrains;
import com.google.common.collect.ImmutableMap;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.DiningTableBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.content.customer.CustomerMap;
import icu.gensoukyo.neo_mystias_izakaya.content.customer.RareCustomer;
import icu.gensoukyo.neo_mystias_izakaya.content.customer.RareCustomerHolder;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaOrder;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIMemoryTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

import java.util.List;
import java.util.Random;

public class MaidMealTask extends MaidCheckRateTask {
    private static final Random random = new Random();
    private static final int checkRate = 50;
    private static final int showTime = 2000;

    public MaidMealTask() {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                InitBrains.TARGET_POS.get(), MemoryStatus.VALUE_ABSENT));
        this.setMaxCheckRate(checkRate);
    }

    @Override
    protected void start(ServerLevel level, EntityMaid maid, long gameTimeIn) {
        maid.getBrain().getMemory(NMIMemoryTypes.TARGET_POS.get()).ifPresent(targetPos -> {
            if (level.getBlockEntity(targetPos) instanceof DiningTableBlockEntity diningTableBlock) {
                if (!diningTableBlock.isOccupied()) {
                    Identifier maidModel = Identifier.parse(maid.getModelId());
                    Identifier maidID = NeoMystiasIzakaya.id("customer/" + maidModel.getPath());

                    CustomerMap customerMap = NMIDataAccessor.server().getCustomerMap();
                    // 尝试匹配特定稀客，匹配不到则随机选一个
                    RareCustomerHolder holder = customerMap.getRareCustomerMap().get(maidID);
                    if (holder == null) {
                        List<RareCustomerHolder> rareList = customerMap.getRareCustomers();
                        if (!rareList.isEmpty()) {
                            holder = rareList.get(level.getRandom().nextInt(rareList.size()));
                        }
                    }
                    if (holder != null) {
                        RareCustomer customer = holder.customer();
                        List<Identifier> likes = customer.likes();
                        List<Identifier> beverages = customer.beverage();
                        if (!likes.isEmpty() && !beverages.isEmpty()) {
                            Identifier cuisineId = likes.get(level.getRandom().nextInt(likes.size()));
                            Identifier beverageId = beverages.get(level.getRandom().nextInt(beverages.size()));
                            IzakayaOrder order = new IzakayaOrder(cuisineId, beverageId, holder.key(), true);
                            diningTableBlock.seatCustomer(order);
                        }
                    }
                }
            }
        });


//        maid.getBrain().getMemory(NMIMemoryTypes.TARGET_POS.get()).ifPresent(targetPos -> {
//            BlockEntity blockEntity = level.getBlockEntity(targetPos.currentBlockPosition());
//            if (blockEntity instanceof MystiaSeatTE mystiaSeatTE) {
//                ItemStack item = mystiaSeatTE.getItem();
//                if (mystiaSeatTE.targetTags.isEmpty()) {
//                    int tagNum;
//                    String modelId = maid.getModelId();
//                    tagNum = modelId.equals("touhou_little_maid:saigyouji_yuyuko") ? 3 : 2;
//                    mystiaSeatTE.targetTags = ServerUtilMethod.getRandomTags(LocalMealList.getInstance().getFoodTags(), tagNum);
//                }
//                if (!mystiaSeatTE.targetTags.isEmpty()) {
//                    if (item.getItem() instanceof CookedMealItem cookedMealItem) {
//                        List<Byte> tagEnums;
//                        FoodTagComponent foodTagComponent = item.get(ComponentRegistry.FOOD_TAG);
//                        if (foodTagComponent != null) {
//                            IntList intList = foodTagComponent.intList();
//                            tagEnums = intList.intStream().mapToObj(integer -> (byte) integer).toList();
//                        } else {
//                            tagEnums = cookedMealItem.positiveTag.stream().map(foodTagEnum -> (byte) foodTagEnum.ordinal()).toList();
//                        }
//
//                        int count = 0;
//                        for (Byte element : mystiaSeatTE.targetTags) {
//                            if (tagEnums.contains(element)) {
//                                count++;
//                            }
//                        }
//
//                        switch (count) {
//                            case 0 ->
//                                    maid.addChatBubble(System.currentTimeMillis() + showTime, new ChatText(ChatTextType.TEXT, EMPTY_ICON_PATH, "简直是黑暗料理"));
//                            case 1 ->
//                                    maid.addChatBubble(System.currentTimeMillis() + showTime, new ChatText(ChatTextType.TEXT, EMPTY_ICON_PATH, bubbles1.get(random.nextInt(bubbles1.size() - 1))));
//                            case 2 ->
//                                    maid.addChatBubble(System.currentTimeMillis() + showTime, new ChatText(ChatTextType.TEXT, EMPTY_ICON_PATH, bubbles2.get(random.nextInt(bubbles2.size() - 1))));
//                            case 3 ->
//                                    maid.addChatBubble(System.currentTimeMillis() + showTime, new ChatText(ChatTextType.TEXT, EMPTY_ICON_PATH, "还是很怀念小脆骨呢"));
//                            default ->
//                                    maid.addChatBubble(System.currentTimeMillis() + showTime, new ChatText(ChatTextType.TEXT, EMPTY_ICON_PATH, "我是谁，我在哪里"));
//                        }
//
//                        if (count >= 2) {
//                            if (random.nextInt() < 0.1) {
//                                level.addFreshEntity(new ItemEntity(level, mystiaSeatTE.getBlockPos().getX(), mystiaSeatTE.getBlockPos().getY(), mystiaSeatTE.getBlockPos().getZ(), ItemRegistry.ChromeBall.toStack()));
//                            }
//                        }
//
//                        mystiaSeatTE.clearItem();
//                        mystiaSeatTE.targetTags = new ArrayList<>();
//                    }
//                }
//                level.sendBlockUpdated(mystiaSeatTE.getBlockPos(), mystiaSeatTE.getBlockState(), mystiaSeatTE.getBlockState(), Block.UPDATE_CLIENTS);
//                if (!mystiaSeatTE.targetTags.isEmpty()){
//                    StringBuilder sb = new StringBuilder();
//                    for (int i = 0; i < mystiaSeatTE.targetTags.size(); i++) {
//                        sb.append(LocalMealList.getInstance().getFoodTags()[mystiaSeatTE.targetTags.get(i)].getCn()).append(" ");
//                    }
//                    maid.addChatBubble(System.currentTimeMillis() + (checkRate - 1) * 50, new ChatText(ChatTextType.TEXT, EMPTY_ICON_PATH, sb.toString()));
//                }
//            } else {
//                maid.getBrain().eraseMemory(InitEntities.TARGET_POS.get());
//                maid.stopRiding();
//            }
//        });
    }
}
